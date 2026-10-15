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
 * Generates the changelog published on GitHub / Modrinth / CurseForge from the commits
 * made since the last successful "publish" workflow.
 *
 * <p>Every Stonecutter subproject asks for the changelog, so it is cached in memory (the buildSrc
 * classloader is shared by the whole build) then on disk, to only hit the GitHub API once per build.</p>
 *
 * <p>The API cost is constant (~3 requests) instead of growing with the number of commits.</p>
 */
public final class NewGithubChangelog {
    public static final String NO_CHANGELOG = "No changelog was specified.";

    private static final String DEFAULT_REPOSITORY = "Paulem79/SimpleOres-fabric";
    private static final String DEFAULT_BRANCH = "stonecutter";
    private static final String HEADER = "This version is uploaded automatically by GitHub Actions.";

    /** Lifetime of the disk cache. */
    private static final long CACHE_TTL_MILLIS = 30 * 60 * 1000L;
    /** Maximum number of listed commits, beyond that it links to the GitHub diff. */
    private static final int MAX_COMMITS = 50;
    /** CurseForge and Modrinth don't accept unlimited changelogs. */
    private static final int MAX_LENGTH = 8_000;
    /** Marker to exclude a commit from the changelog. */
    private static final String SKIP_MARKER = "[skip changelog]";

    /** Shared by every subproject of a build. */
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
        // Atomic write, subprojects can be configured in parallel
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

    // --- Generation --------------------------------------------------------

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
            // getCompare returns the commits from oldest to newest
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
     * Finds the SHA of the last successfully published commit, with a single paginated request
     * on the successful workflow runs.
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
                // The current commit is the one being published
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

                // First line only, the body doesn't belong in a changelog
                String subject = message.lines().findFirst().orElse("").trim();
                if (subject.isEmpty()) continue;
                if (subject.toLowerCase(Locale.ROOT).contains(SKIP_MARKER)) continue;
                // Merge commits add nothing useful
                if (subject.startsWith("Merge branch ")
                        || subject.startsWith("Merge remote-tracking branch ")
                        || subject.startsWith("Merge pull request ")) continue;
                // A cherry-picked fix should only appear once
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
     * Commits from {@code getCompare} have no {@code getCommitShortInfo()} (null) but a
     * {@code getCommit()}, the ones from {@code queryCommits} are the opposite.
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

        // LinkedHashMap keeps the category declaration order
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
            changelog.append("\n**Full diff:** ").append(diffUrl).append("\n");
        }

        return truncate(changelog.toString().stripTrailing(), diffUrl);
    }

    private static String truncate(String changelog, String diffUrl) {
        if (changelog.length() <= MAX_LENGTH) return changelog;

        String footer = "\n\n… changelog truncated"
                + (diffUrl != null ? ", see the full diff: " + diffUrl : ".");
        int cut = changelog.lastIndexOf('\n', MAX_LENGTH - footer.length());
        if (cut < 0) cut = MAX_LENGTH - footer.length();
        return changelog.substring(0, cut).stripTrailing() + footer;
    }

    /** Categories of <a href="https://www.conventionalcommits.org">conventional commits</a>. */
    private enum Category {
        FEATURE("✨ Features", "feat", "feature"),
        FIX("🐛 Bug fixes", "fix", "bugfix", "hotfix"),
        PERFORMANCE("⚡ Performance", "perf"),
        REFACTOR("♻️ Refactoring", "refactor", "style"),
        DOCUMENTATION("📚 Documentation", "docs", "doc"),
        TEST("🧪 Tests", "test", "tests"),
        CHORE("🔧 Maintenance", "chore", "build", "ci", "deps"),
        OTHER("📦 Miscellaneous");

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

    /** A commit ready to render, with its conventional type, scope and description split. */
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
