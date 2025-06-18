package ovh.paulem.buildscript;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class VersionRangeParser {

    public static List<String> parseVersionRange(String min_version_range, String max_version_range) {
        CompiledVersions allVersions = new CompiledVersions(getAllMinecraftVersions());

        return commonVersionExtract(min_version_range, max_version_range, allVersions);
    }

    public static List<String> parseVersionRange(String min_version_range, String max_version_range, CompiledVersions.VersionType versionType) {
        CompiledVersions allVersions = new CompiledVersions(new CompiledVersions(getAllMinecraftVersions()).from(versionType));

        return commonVersionExtract(min_version_range, max_version_range, allVersions);
    }

    private static List<String> commonVersionExtract(String min_version_range, String max_version_range, VersionRangeParser.CompiledVersions allVersions) {
        int startElement = allVersions.contains(min_version_range) ? allVersions.indexOf(min_version_range) : 0;
        int endElement = !allVersions.contains(max_version_range) ? allVersions.size() - 1 : allVersions.indexOf(max_version_range);

        return allVersions.stream()
                .filter(element -> allVersions.indexOf(element) >= startElement && allVersions.indexOf(element) <= endElement)
                .map(MinecraftVersion::id)
                .collect(Collectors.toList());
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
            return versions.stream().anyMatch(v -> v.id.equals(version));
        }

        public int indexOf(String version) {
            return versions.stream()
                    .map(MinecraftVersion::id)
                    .toList()
                    .indexOf(version);
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
                        .filter(type -> type.name.equals(name))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Unknown version type: " + name));
            }

            public boolean matches(MinecraftVersion version) {
                return version.getType().name.equals(name);
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

                /*versions = revRange(0, versionsArray.size())
                        .mapToObj(i -> versionsArray.get(i).getAsJsonObject()
                                .get("id").getAsString()
                        )
                        .collect(Collectors.toList());*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Arrays.stream(versions).toList();
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