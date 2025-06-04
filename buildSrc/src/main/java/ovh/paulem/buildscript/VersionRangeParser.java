package ovh.paulem.buildscript;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class VersionRangeParser {

    public static Iterable<? extends String> parseVersionRange(String min_version_range, String max_version_range) {
        List<String> allVersions = getAllMinecraftVersions();

        int startElement = allVersions.contains(min_version_range) ? allVersions.indexOf(min_version_range) : 0;
        int endElement = !allVersions.contains(max_version_range) ? allVersions.size() - 1 : allVersions.indexOf(max_version_range);

        return allVersions.stream()
                .filter(element -> allVersions.indexOf(element) >= startElement && allVersions.indexOf(element) <= endElement)
                .collect(Collectors.toList());
    }

    private static List<String> getAllMinecraftVersions() {
        List<String> versions = new ArrayList<>();
        try {
            URL url = new URI("https://piston-meta.mojang.com/mc/game/version_manifest_v2.json").toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            try (InputStreamReader reader = new InputStreamReader(connection.getInputStream())) {
                Gson gson = new Gson();
                JsonObject json = gson.fromJson(reader, JsonObject.class);
                JsonArray versionsArray = json.getAsJsonArray("versions");

                versions = revRange(0, versionsArray.size())
                        .mapToObj(i -> versionsArray.get(i).getAsJsonObject()
                                .get("id").getAsString()
                        )
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versions;
    }

    private static IntStream revRange(int from, int to) {
        return IntStream.range(from, to)
                .map(i -> to - i + from - 1);
    }
}