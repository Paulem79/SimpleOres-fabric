package net.paulem.buildscript;

import org.gradle.api.JavaVersion;
import org.gradle.api.Project;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.credentials.PasswordCredentials;
import org.gradle.authentication.http.BasicAuthentication;
import org.gradle.api.file.Directory;
import org.gradle.api.plugins.BasePluginExtension;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.provider.Provider;
import org.gradle.api.publish.PublishingExtension;
import org.gradle.api.publish.maven.MavenPublication;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.api.tasks.compile.JavaCompile;
import org.gradle.jvm.tasks.Jar;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Setup shared by the Fabric and NeoForge build scripts, so each script only keeps what is loader specific.
 */
public final class ModBuild {
    public static final String PAULEM_MAVEN = "https://maven.paulem.net/releases";

    private static final Pattern COPPER_TOOLS =
            Pattern.compile("simpleores:copper_(axe|boots|chestplate|helmet|leggings|pickaxe|shovel|sword)");

    private ModBuild() {
    }

    /** Group, version and archive name, the same on every node. */
    public static void configureBase(Project project, String modId, String modVersion, String group, String nodeName) {
        project.setVersion(modVersion + "-" + nodeName);
        project.setGroup(group);
        project.getExtensions().getByType(BasePluginExtension.class).getArchivesName().set(modId);
    }

    /** Repositories used by both loaders, the loader specific ones are given as {@code extraUrls}. */
    public static void repositories(RepositoryHandler repos, String... extraUrls) {
        repos.mavenCentral();
        repos.mavenLocal();
        for (String url : extraUrls) {
            repos.maven(repo -> repo.setUrl(url));
        }
        repos.maven(repo -> repo.setUrl("https://api.modrinth.com/maven"));
        repos.maven(repo -> repo.setUrl("https://jitpack.io"));
        repos.maven(repo -> repo.setUrl("https://maven.shedaniel.me/"));
        repos.maven(repo -> repo.setUrl("https://maven.midnightdust.eu/releases"));
        repos.maven(repo -> repo.setUrl("https://repo.faststats.dev/releases"));
        // Pre-releases, needed for the client consent screen
        repos.maven(repo -> repo.setUrl("https://repo.faststats.dev/snapshots"));
        repos.maven(repo -> {
            repo.setName("paulemReleases");
            repo.setUrl(PAULEM_MAVEN);
        });
    }

    /** Java version, release flag and encoding of every compilation. */
    public static void configureJava(Project project, JavaVersion version) {
        JavaPluginExtension java = project.getExtensions().getByType(JavaPluginExtension.class);
        java.setSourceCompatibility(version);
        java.setTargetCompatibility(version);

        int release = Integer.parseInt(version.toString());
        project.getTasks().withType(JavaCompile.class).configureEach(task -> {
            task.getOptions().getRelease().set(release);
            task.getOptions().setEncoding("UTF-8");
        });
    }

    /** Ships the licence in the jar, suffixed with the mod id. */
    public static void licenseInJar(Project project) {
        String archivesName = project.getExtensions().getByType(BasePluginExtension.class).getArchivesName().get();
        project.getTasks().named("jar", Jar.class).configure(jar ->
                jar.from("LICENSE", spec -> spec.rename(name -> name + "_" + archivesName)));
    }

    /**
     * Chest loot of the base mod: one template set, adapted to the version
     * (folder name and vanilla copper equipment), added to the main resources.
     */
    public static void registerChestLoot(Project project, boolean singularFolder, boolean vanillaCopperTools) {
        Provider<Directory> chestLootDir = project.getLayout().getBuildDirectory().dir("generated/chest-loot");
        String folder = singularFolder ? "loot_table" : "loot_tables";

        TaskProvider<Copy> generate = project.getTasks().register("generateChestLoot", Copy.class, copy -> {
            copy.from(project.getRootProject().file("sc-resources/chest-loot"));
            copy.into(chestLootDir.map(dir -> dir.dir("data/simpleores/" + folder + "/chest")));
            if (vanillaCopperTools) {
                copy.filter(line -> COPPER_TOOLS.matcher(line).replaceAll("minecraft:copper_$1"));
            }
        });

        project.getExtensions().getByType(SourceSetContainer.class).named("main", main ->
                main.getResources().srcDir(chestLootDir));
        project.getTasks().named("processResources").configure(task -> task.dependsOn(generate));
    }

    /** A property or environment variable, blank values (unset Actions secrets) count as missing. */
    public static String secret(Project project, String name) {
        Object property = project.findProperty(name);
        String value = property != null ? property.toString() : System.getenv(name);
        return value == null || value.isBlank() ? null : value;
    }

    /**
     * Changelog of the release, fetched only when a publish task needs it.
     * The Actions GITHUB_TOKEN has a low shared quota, so GH_RELEASE_TOKEN wins when provided.
     */
    public static Provider<String> changelog(Project project) {
        return project.provider(() -> {
            String token = secret(project, "GH_RELEASE_TOKEN");
            if (token == null) token = secret(project, "GITHUB_TOKEN");
            try {
                return NewGithubChangelog.getChangelog(project.getRootDir().toPath(), token);
            } catch (Exception e) {
                // If the GitHub API is down, publish with a placeholder instead of failing
                project.getLogger().warn("Impossible de récupérer le changelog de GitHub : {}", e.getMessage());
                return "Changelog is currently unavailable.";
            }
        });
    }

    /** The mod jar published to the Paulem maven. */
    public static void configurePublishing(Project project, String artifactId) {
        PublishingExtension publishing = project.getExtensions().getByType(PublishingExtension.class);

        publishing.getRepositories().maven(repo -> {
            repo.setName("paulem");
            repo.setUrl(PAULEM_MAVEN);
            repo.credentials(PasswordCredentials.class);
            repo.authentication(auth -> auth.create("basic", BasicAuthentication.class));
        });

        publishing.getPublications().create("maven", MavenPublication.class, publication -> {
            publication.setGroupId(project.getGroup().toString());
            publication.setArtifactId(artifactId);
            publication.setVersion(project.getVersion().toString());
            publication.from(project.getComponents().getByName("java"));
        });
    }

    /** CI: -PprebuiltJarsDir=&lt;dir&gt; publishes already built jars without compiling. */
    public static File prebuiltJar(Project project) {
        Object dir = project.findProperty("prebuiltJarsDir");
        if (dir == null) return null;
        String archivesName = project.getExtensions().getByType(BasePluginExtension.class).getArchivesName().get();
        return new File(dir.toString(), archivesName + "-" + project.getVersion() + ".jar");
    }

    /** The "gradle.properties" view {@link VersionRangeParser} expects, from the version range keys of the node. */
    public static Map<String, String> versionRangeProperties(String versionRange, String minVersionRange, String maxVersionRange) {
        Map<String, String> properties = new LinkedHashMap<>();
        if (versionRange != null) properties.put("version_range", versionRange);
        if (minVersionRange != null) properties.put("min_version_range", minVersionRange);
        if (maxVersionRange != null) properties.put("max_version_range", maxVersionRange);
        return properties;
    }

    /**
     * Minecraft versions listed on CurseForge. It only lists one "&lt;version&gt;-snapshot" per cycle, so
     * 26.3-snapshot-8 is published as "26.3-snapshot". {@code override} (comma-separated) replaces the list.
     */
    public static List<String> curseforgeVersions(String override, boolean snapshot, String minecraftVersion,
                                                  Map<String, String> versionRangeProperties) {
        if (override != null) {
            List<String> versions = java.util.Arrays.stream(override.split(","))
                    .map(String::trim)
                    .filter(version -> !version.isEmpty())
                    .toList();
            if (!versions.isEmpty()) return versions;
        }

        if (snapshot) {
            return List.of(VersionRangeParser.toCurseforgeVersion(minecraftVersion));
        }
        return VersionRangeParser.parseVersionRange(
                versionRangeProperties, VersionRangeParser.CompiledVersions.VersionType.RELEASE);
    }
}
