package net.paulem.buildscript;

import com.github.zafarkhaja.semver.Version;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class VersionRangeParser {

    /** Snapshots hebdomadaires historiques : {@code 25w14a}, {@code 24w14potato}. */
    private static final java.util.regex.Pattern WEEKLY_SNAPSHOT =
            java.util.regex.Pattern.compile("^\\d{2}w\\d{2}[a-z~].*$", java.util.regex.Pattern.CASE_INSENSITIVE);

    /** Pré-versions : {@code 26.3-snapshot-8}, {@code 1.21.6-pre1}, {@code 26.2-rc-2}, {@code 1.20-exp1}. */
    private static final java.util.regex.Pattern PRE_RELEASE =
            java.util.regex.Pattern.compile("^.+-(snapshot|pre|rc|exp|alpha|beta)[-.]?\\d*$",
                    java.util.regex.Pattern.CASE_INSENSITIVE);

    /**
     * Indique si un identifiant Mojang désigne une version de développement, sans contacter
     * le manifeste. Permet de déduire l'état « snapshot » de {@code deps.minecraft} plutôt
     * que du nom du dossier Stonecutter.
     */
    public static boolean isSnapshotId(String id) {
        if (id == null || id.isBlank()) return false;
        String trimmed = id.trim();
        return WEEKLY_SNAPSHOT.matcher(trimmed).matches() || PRE_RELEASE.matcher(trimmed).matches();
    }

    /**
     * Traduit un identifiant Mojang vers le nom utilisé par CurseForge, qui ne référence pas
     * chaque snapshot individuellement mais un unique {@code <version>-snapshot} par cycle :
     * {@code 26.3-snapshot-8} et {@code 26.3-rc-1} deviennent tous deux {@code 26.3-snapshot}.
     *
     * <p>Les snapshots hebdomadaires historiques ({@code 25w14a}) et les versions stables sont
     * renvoyés tels quels.</p>
     */
    public static String toCurseforgeVersion(String id) {
        if (!isSnapshotId(id)) return id;

        String trimmed = id.trim();
        int separator = trimmed.indexOf('-');
        // Ancien schéma (25w14a) : pas de préfixe de version, CurseForge utilise l'id brut.
        if (separator <= 0) return trimmed;

        return trimmed.substring(0, separator) + "-snapshot";
    }

    public static List<String> parseVersionRange(String range) {
        CompiledVersions allVersions = new CompiledVersions(getAllMinecraftVersions());
        return commonVersionExtract(range, allVersions);
    }

    public static List<String> parseVersionRange(java.util.Map<String, ?> properties) {
        Object range = properties.get("version_range");
        if (range != null && !range.toString().isEmpty()) {
            return parseVersionRange(range.toString());
        }
        Object min = properties.get("min_version_range");
        Object max = properties.get("max_version_range");
        if (min != null || max != null) {
            return parseVersionRange(min != null ? min.toString() : null, max != null ? max.toString() : null);
        }
        return new ArrayList<>();
    }

    public static List<String> parseVersionRange(String min_version_range, String max_version_range) {
        if (min_version_range == null || min_version_range.isEmpty()) {
            return parseVersionRange(max_version_range);
        }
        if (max_version_range == null || max_version_range.isEmpty()) {
            return parseVersionRange(min_version_range);
        }

        String combinedRange = ">=" + min_version_range + " <=" + max_version_range;
        return parseVersionRange(combinedRange);
    }

    public static List<String> parseVersionRange(java.util.Map<String, ?> properties, CompiledVersions.VersionType versionType) {
        Object range = properties.get("version_range");
        if (range != null && !range.toString().isEmpty()) {
            CompiledVersions allVersions = new CompiledVersions(new CompiledVersions(getAllMinecraftVersions()).from(versionType));
            return commonVersionExtract(range.toString(), allVersions);
        }
        Object min = properties.get("min_version_range");
        Object max = properties.get("max_version_range");
        return parseVersionRange(min != null ? min.toString() : null, max != null ? max.toString() : null, versionType);
    }

    public static List<String> parseVersionRange(String min_version_range, String max_version_range, CompiledVersions.VersionType versionType) {
        CompiledVersions allVersions = new CompiledVersions(new CompiledVersions(getAllMinecraftVersions()).from(versionType));
        String combinedRange = ">=" + min_version_range + " <=" + max_version_range;
        return commonVersionExtract(combinedRange, allVersions);
    }

    /**
     * Ligne de versions complète : {@code ~26.1-} désigne 26.1 et tous ses correctifs, y compris
     * leurs préversions ({@code 26.1.1-rc-1}), mais jamais celles du cycle suivant.
     */
    private static final java.util.regex.Pattern VERSION_LINE =
            java.util.regex.Pattern.compile("^~(\\d+\\.\\d+)-$");

    private static List<String> commonVersionExtract(String rangeExpression, VersionRangeParser.CompiledVersions allVersions) {
        if (rangeExpression == null || rangeExpression.isEmpty()) {
            return new ArrayList<>();
        }

        // java-semver ignore la convention « -» (inclure les préversions) et lève une exception,
        // ce qui faisait retomber le filtre sur une égalité stricte d'identifiant, donc sur une
        // liste vide. Un encadrement semver ne convient pas non plus ici : 26.2-snapshot-1 étant
        // inférieur à 26.2, « <26.2 » ramènerait les snapshots du cycle suivant. On compare donc
        // directement le préfixe de la ligne de versions.
        java.util.regex.Matcher versionLine = VERSION_LINE.matcher(rangeExpression.trim());
        if (versionLine.matches()) {
            String line = versionLine.group(1);
            return allVersions.stream()
                    .map(MinecraftVersion::id)
                    .filter(id -> id.equals(line) || id.startsWith(line + ".") || id.startsWith(line + "-"))
                    .collect(Collectors.toList());
        }

        // Nettoyage de l'expression de range pour SemVer
        String semverRange = rangeExpression
                .replace("-rc", "-rc.")
                .replace("-pre", "-beta.")
                .replaceAll("-rc\\.\\.", "-rc.")
                .replaceAll("-beta\\.\\.", "-beta.");

        if (semverRange.contains(" ") && !semverRange.contains("&") && !semverRange.contains("|")) {
            semverRange = semverRange.trim().replaceAll("\\s+", " & ");
        }

        final String finalRange = semverRange;
        return allVersions.stream()
                .filter(v -> {
                    try {
                        String normalized = normalize(v.id());
                        return Version.parse(normalized, false).satisfies(finalRange);
                    } catch (Exception e) {
                        // Fallback match exact
                        return v.id().equalsIgnoreCase(rangeExpression);
                    }
                })
                .map(MinecraftVersion::id)
                .collect(Collectors.toList());
    }

    public static String normalize(String version) {
        if (version == null) return null;
        String normalized = version.trim();
        if (normalized.startsWith("v")) normalized = normalized.substring(1);

        // x.y -> x.y.0
        if (normalized.matches("^\\d+\\.\\d+$")) {
            normalized += ".0";
        }

        // 1.21.1-pre2 -> 1.21.1-beta.2
        normalized = normalized.replaceAll("-rc(\\d+)", "-rc.$1");
        normalized = normalized.replaceAll("-pre(\\d+)", "-beta.$1");
        normalized = normalized.replaceAll("-rc-(\\d+)", "-rc.$1");

        return normalized;
    }

    private static String findVersionId(CompiledVersions versions, String inputId) {
        if (versions.contains(inputId)) return inputId;

        // Try replacing "-pre." with "-pre-" or "-rc." with "-rc-" (e.g. 26.1-pre.1 -> 26.1-pre-1)
        String dashed = inputId.replaceAll("-(pre|rc|snapshot)\\.", "-$1-");
        if (versions.contains(dashed)) return dashed;

        // Try removing the separator (e.g. 1.19.4-pre.1 -> 1.19.4-pre1 for older versions)
        String combined = inputId.replaceAll("-(pre|rc|snapshot)\\.", "-$1");
        if (versions.contains(combined)) return combined;

        return inputId;
    }

    private static MinecraftVersion getReleaseFromSnapshot(String snapshot) {
        CompiledVersions allVersions = new CompiledVersions(getAllMinecraftVersions());

        int snapshotIndex = allVersions.indexOf(snapshot);

        if(snapshotIndex == -1) {
            // Return newest snapshot if snapshot not found
            return allVersions.snapshots().reversed().getFirst();
        }

        // Parcours vers l'avant pour trouver la prochaine version RELEASE
        for (int i = snapshotIndex + 1; i < allVersions.size(); i++) {
            MinecraftVersion candidate = allVersions.get(i);
            if (candidate.getType() == CompiledVersions.VersionType.RELEASE) {
                return candidate;
            }
        }
        // Si aucune version RELEASE n'est trouvée après, retourne null ou lève une exception
        throw new RuntimeException("No release version found for snapshot " + snapshot);
    }

    public record CompiledVersions(List<MinecraftVersion> versions) {
        public List<MinecraftVersion> from(VersionType versionType) {
            return versions.stream()
                    .filter(versionType::matches)
                    .collect(Collectors.toList());
        }

        public List<MinecraftVersion> releases() {
            return versions.stream()
                    .filter(version -> version.getType() == VersionType.RELEASE)
                    .collect(Collectors.toList());
        }

        public List<MinecraftVersion> snapshots() {
            return versions.stream()
                    .filter(version -> version.getType() == VersionType.SNAPSHOT)
                    .collect(Collectors.toList());
        }

        public List<String> get() {
            return versions.stream()
                    .map(MinecraftVersion::id)
                    .collect(Collectors.toList());
        }

        public boolean contains(String version) {
            return versions.stream().anyMatch(v -> v.id.equalsIgnoreCase(version));
        }

        public int indexOf(String version) {
            return versions.stream()
                    .map(MinecraftVersion::id)
                    .toList()
                    .indexOf(version);
        }

        public MinecraftVersion get(int index) {
            return versions.get(index);
        }

        public int indexOf(MinecraftVersion version) {
            return versions.indexOf(version);
        }

        public int size() {
            return versions.size();
        }

        public Stream<MinecraftVersion> stream() {
            return versions.stream();
        }

        public enum VersionType {
            RELEASE("release"),
            SNAPSHOT("snapshot"),
            OLD_BETA("old_beta"),
            OLD_ALPHA("old_alpha");

            private final String name;

            VersionType(String name) {
                this.name = name;
            }

            public static VersionType from(String name) {
                return Arrays.stream(VersionType.values())
                        .filter(type -> type.name.equalsIgnoreCase(name))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Unknown version type: " + name));
            }

            public boolean matches(MinecraftVersion version) {
                return version.getType().name.equalsIgnoreCase(name);
            }
        }
    }

    private static List<MinecraftVersion> getAllMinecraftVersions() {
        MinecraftVersion[] versions = new MinecraftVersion[0];
        try {
            URL url = new URI("https://piston-meta.mojang.com/mc/game/version_manifest_v2.json").toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            try (InputStreamReader reader = new InputStreamReader(connection.getInputStream())) {
                Gson gson = new Gson();
                JsonObject json = gson.fromJson(reader, JsonObject.class);
                JsonArray versionsArray = json.getAsJsonArray("versions");

                versions = gson.fromJson(versionsArray, MinecraftVersion[].class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Arrays.stream(versions).toList().reversed();
    }

    public record MinecraftVersion(
            String id,
            String type,
            String url,
            String time,
            String releaseTime,
            String sha1,
            int complianceLevel
    ) {
        public CompiledVersions.VersionType getType() {
            return CompiledVersions.VersionType.from(type);
        }
    }

    private static IntStream revRange(int from, int to) {
        return IntStream.range(from, to)
                .map(i -> to - i + from - 1);
    }
}