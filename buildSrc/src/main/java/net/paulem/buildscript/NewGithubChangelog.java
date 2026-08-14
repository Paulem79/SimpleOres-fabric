package net.paulem.buildscript;


import org.kohsuke.github.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Génère le changelog publié sur GitHub / Modrinth / CurseForge à partir des commits
 * réalisés depuis le dernier workflow "publish" réussi.
 *
 * <p>Le build Stonecutter contient une quinzaine de sous-projets qui demandent tous le
 * changelog : la génération est donc mémorisée en mémoire (le classloader de buildSrc est
 * partagé par tout le build) puis sur disque, afin de ne contacter l'API GitHub qu'une
 * seule fois par build.</p>
 *
 * <p>Le coût API est constant (~3 requêtes) au lieu d'être proportionnel au nombre de
 * commits, ce qui évite les erreurs de rate limit.</p>
 */
public final class NewGithubChangelog {
    public static final String NO_CHANGELOG = "No changelog was specified.";

    private static final String DEFAULT_REPOSITORY = "Paulem79/SimpleOres-fabric";
    private static final String DEFAULT_BRANCH = "stonecutter";
    private static final String HEADER = "This version is uploaded automatically by GitHub Actions.";

    /** Durée de validité du cache disque. */
    private static final long CACHE_TTL_MILLIS = 30 * 60 * 1000L;
    /** Nombre de commits maximum listés (au-delà, on renvoie vers le diff GitHub). */
    private static final int MAX_COMMITS = 50;
    /** CurseForge et Modrinth n'acceptent pas des changelogs illimités. */
    private static final int MAX_LENGTH = 8_000;
    /** Marqueur permettant d'exclure un commit du changelog. */
    private static final String SKIP_MARKER = "[skip changelog]";

    /** Mémoire partagée par tous les sous-projets d'un même build. */
    private static final AtomicReference<String> MEMOIZED = new AtomicReference<>();

    private NewGithubChangelog() {
    }

    public static String getChangelog(Path projectPath, String githubToken) {
        return getChangelog(projectPath, githubToken, DEFAULT_REPOSITORY, DEFAULT_BRANCH);
    }

    public static String getChangelog(Path projectPath,
                                      String githubToken,
                                      String repository,
                                      String branch) {
        String memoized = MEMOIZED.get();
        if (memoized != null) return memoized;

        Path cachePath = projectPath.resolve("changelog.txt");

        String cached = readCache(cachePath);
        if (cached != null) {
            MEMOIZED.set(cached);
            return cached;
        }

        String changelog;
        try {
            changelog = doChangelog(githubToken, repository, branch);
        } catch (Exception e) {
            System.err.println("[changelog] Génération impossible : " + e.getMessage());
            changelog = NO_CHANGELOG;
        }

        if (!NO_CHANGELOG.equals(changelog)) {
            writeCache(cachePath, changelog);
        }

        MEMOIZED.set(changelog);
        return changelog;
    }

    // --- Cache -------------------------------------------------------------

    private static String readCache(Path cachePath) {
        try {
            if (!Files.exists(cachePath)) return null;

            long age = System.currentTimeMillis() - Files.getLastModifiedTime(cachePath).toMillis();
            if (age > CACHE_TTL_MILLIS) {
                System.out.println("[changelog] Cache expiré (" + (age / 1000) + "s), régénération.");
                Files.deleteIfExists(cachePath);
                return null;
            }

            String content = Files.readString(cachePath, StandardCharsets.UTF_8);
            return content.isBlank() ? null : content;
        } catch (IOException e) {
            return null;
        }
    }

    private static void writeCache(Path cachePath, String changelog) {
        // Écriture atomique : les sous-projets peuvent être configurés en parallèle.
        try {
            Path tmp = Files.createTempFile(cachePath.getParent(), "changelog", ".tmp");
            Files.writeString(tmp, changelog, StandardCharsets.UTF_8);
            try {
                Files.move(tmp, cachePath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (IOException atomicUnsupported) {
                Files.move(tmp, cachePath, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            System.err.println("[changelog] Impossible d'écrire le cache : " + e.getMessage());
        }
    }

    // --- Génération --------------------------------------------------------

    private static String doChangelog(String githubToken, String repositoryName, String branch)
            throws IOException {
        System.out.println("[changelog] Récupération des commits depuis " + repositoryName + "@" + branch + "...");

        GitHubBuilder builder = new GitHubBuilder()
                .withRateLimitHandler(GitHubRateLimitHandler.FAIL);
        if (githubToken != null && !githubToken.isEmpty()) {
            builder = builder.withOAuthToken(githubToken);
        } else {
            System.err.println("[changelog] Aucun token GitHub : utilisation de l'API anonyme.");
        }

        GitHub gitHub = builder.build();
        GHRepository repository = gitHub.getRepository(repositoryName);

        String headSha = repository.getBranch(branch).getSHA1();
        String lastPublishedSha = findLastPublishedSha(repository, branch, headSha);

        List<Entry> entries;
        String diffUrl = null;

        if (lastPublishedSha != null) {
            GHCompare compare = repository.getCompare(lastPublishedSha, headSha);
            entries = toEntries(Arrays.asList(compare.getCommits()));
            // getCompare renvoie les commits du plus ancien au plus récent.
            Collections.reverse(entries);
            diffUrl = compare.getHtmlUrl().toString();
        } else {
            System.out.println("[changelog] Aucune publication précédente trouvée, "
                    + "utilisation des " + MAX_COMMITS + " derniers commits.");
            List<GHCommit> recent = new ArrayList<>();
            for (GHCommit commit : repository.queryCommits().from(headSha).pageSize(MAX_COMMITS).list()) {
                recent.add(commit);
                if (recent.size() >= MAX_COMMITS) break;
            }
            entries = toEntries(recent);
        }

        System.out.println("[changelog] " + entries.size() + " commit(s) retenu(s).");
        return format(entries, diffUrl);
    }

    /**
     * Trouve le SHA du dernier commit publié avec succès. Une seule requête paginée sur les
     * exécutions de workflow réussies, au lieu d'une requête par commit.
     */
    private static String findLastPublishedSha(GHRepository repository, String branch, String headSha) {
        try {
            for (GHWorkflowRun run : repository.queryWorkflowRuns()
                    .branch(branch)
                    .status(GHWorkflowRun.Status.COMPLETED)
                    .list()
                    .withPageSize(50)) {
                if (run.getConclusion() != GHWorkflowRun.Conclusion.SUCCESS) continue;
                if (run.getName() == null || !run.getName().toLowerCase(Locale.ROOT).contains("publish")) continue;
                // Le commit courant est celui qu'on est en train de publier.
                if (headSha.equalsIgnoreCase(run.getHeadSha())) continue;
                return run.getHeadSha();
            }
        } catch (Exception e) {
            System.err.println("[changelog] Impossible de lire les workflow runs : " + e.getMessage());
        }
        return null;
    }

    private static List<Entry> toEntries(List<? extends GHCommit> commits) {
        List<Entry> entries = new ArrayList<>();
        Set<String> seenMessages = new HashSet<>();

        for (GHCommit commit : commits) {
            try {
                String message = messageOf(commit);
                if (message == null || message.isBlank()) continue;

                // Première ligne uniquement : le corps du commit n'a pas sa place dans un changelog.
                String subject = message.lines().findFirst().orElse("").trim();
                if (subject.isEmpty()) continue;
                if (subject.toLowerCase(Locale.ROOT).contains(SKIP_MARKER)) continue;
                // Les commits de merge n'apportent pas d'information utile.
                if (subject.startsWith("Merge branch ")
                        || subject.startsWith("Merge remote-tracking branch ")
                        || subject.startsWith("Merge pull request ")) continue;
                // Un même correctif cherry-pické ne doit apparaître qu'une fois.
                if (!seenMessages.add(subject.toLowerCase(Locale.ROOT))) continue;

                entries.add(Entry.of(commit.getSHA1(), commit.getHtmlUrl().toString(), subject));
                if (entries.size() >= MAX_COMMITS) break;
            } catch (Exception e) {
                System.err.println("[changelog] Commit ignoré : " + e.getMessage());
            }
        }
        return entries;
    }

    /**
     * Les commits renvoyés par {@code getCompare} n'exposent pas {@code getCommitShortInfo()}
     * (null) mais {@code getCommit()} ; ceux de {@code queryCommits} font l'inverse.
     */
    private static String messageOf(GHCommit commit) throws IOException {
        if (commit instanceof GHCompare.Commit compared) {
            var inner = compared.getCommit();
            if (inner != null && inner.getMessage() != null) return inner.getMessage();
        }
        GHCommit.ShortInfo info = commit.getCommitShortInfo();
        return info == null ? null : info.getMessage();
    }

    // --- Mise en forme -----------------------------------------------------

    private static String format(List<Entry> entries, String diffUrl) {
        if (entries.isEmpty()) return NO_CHANGELOG;

        StringBuilder changelog = new StringBuilder(HEADER).append("\n");

        // LinkedHashMap : on conserve l'ordre de déclaration des catégories.
        Map<Category, List<Entry>> grouped = new LinkedHashMap<>();
        for (Category category : Category.values()) {
            for (Entry entry : entries) {
                if (entry.category() == category) {
                    grouped.computeIfAbsent(category, k -> new ArrayList<>()).add(entry);
                }
            }
        }

        grouped.forEach((category, categoryEntries) -> {
            changelog.append("\n### ").append(category.title).append("\n");
            for (Entry entry : categoryEntries) {
                changelog.append("- ").append(entry.render()).append("\n");
            }
        });

        if (diffUrl != null) {
            changelog.append("\n**Diff complet :** ").append(diffUrl).append("\n");
        }

        return truncate(changelog.toString().stripTrailing(), diffUrl);
    }

    private static String truncate(String changelog, String diffUrl) {
        if (changelog.length() <= MAX_LENGTH) return changelog;

        String footer = "\n\n… changelog tronqué"
                + (diffUrl != null ? ", voir le diff complet : " + diffUrl : ".");
        int cut = changelog.lastIndexOf('\n', MAX_LENGTH - footer.length());
        if (cut < 0) cut = MAX_LENGTH - footer.length();
        return changelog.substring(0, cut).stripTrailing() + footer;
    }

    /** Catégories de <a href="https://www.conventionalcommits.org">conventional commits</a>. */
    private enum Category {
        FEATURE("✨ Nouveautés", "feat", "feature"),
        FIX("🐛 Corrections", "fix", "bugfix", "hotfix"),
        PERFORMANCE("⚡ Performances", "perf"),
        REFACTOR("♻️ Refactorisation", "refactor", "style"),
        DOCUMENTATION("📚 Documentation", "docs", "doc"),
        TEST("🧪 Tests", "test", "tests"),
        CHORE("🔧 Maintenance", "chore", "build", "ci", "deps"),
        OTHER("📦 Divers");

        private final String title;
        private final Set<String> prefixes;

        Category(String title, String... prefixes) {
            this.title = title;
            this.prefixes = Set.of(prefixes);
        }

        static Category from(String type) {
            if (type == null) return OTHER;
            String normalized = type.toLowerCase(Locale.ROOT);
            for (Category category : values()) {
                if (category.prefixes.contains(normalized)) return category;
            }
            return OTHER;
        }
    }

    /** Un commit prêt à être rendu : type conventionnel, scope et description séparés. */
    private record Entry(String sha, String url, Category category, String scope, String description) {
        /** {@code type(scope)!: description} */
        private static final java.util.regex.Pattern CONVENTIONAL =
                java.util.regex.Pattern.compile("^(?<type>[a-zA-Z]+)(?:\\((?<scope>[^)]*)\\))?!?:\\s*(?<desc>.+)$");

        static Entry of(String sha, String url, String subject) {
            var matcher = CONVENTIONAL.matcher(subject);
            if (!matcher.matches()) {
                return new Entry(sha, url, Category.OTHER, null, capitalize(subject));
            }
            return new Entry(
                    sha,
                    url,
                    Category.from(matcher.group("type")),
                    matcher.group("scope"),
                    capitalize(matcher.group("desc").trim())
            );
        }

        String render() {
            StringBuilder line = new StringBuilder("[`")
                    .append(sha, 0, Math.min(7, sha.length()))
                    .append("`](")
                    .append(url)
                    .append(") ");
            if (scope != null && !scope.isBlank()) {
                line.append("**").append(scope).append("** — ");
            }
            return line.append(description).toString();
        }

        private static String capitalize(String text) {
            if (text.isEmpty()) return text;
            return Character.toUpperCase(text.charAt(0)) + text.substring(1);
        }
    }
}
