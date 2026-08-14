package net.paulem.buildscript;

import com.github.zafarkhaja.semver.Version;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class VersionRangeParserTest {

    @Test
    void testNormalize() {
        assertEquals("1.21.0", VersionRangeParser.normalize("1.21"));
        assertEquals("1.21.1", VersionRangeParser.normalize("1.21.1"));
        assertEquals("1.21.6-beta.1", VersionRangeParser.normalize("1.21.6-pre1"));
        assertEquals("26.1.1-rc.1", VersionRangeParser.normalize("26.1.1-rc-1"));
        assertEquals("26.1.1-rc.1", VersionRangeParser.normalize("26.1.1-rc1"));
    }

    @Test
    void testIsSnapshotId() {
        // Pré-versions : c'est ce qui remplace la détection par nom de dossier "-snapshot".
        assertTrue(VersionRangeParser.isSnapshotId("26.3-snapshot-8"));
        assertTrue(VersionRangeParser.isSnapshotId("26.2-rc-2"));
        assertTrue(VersionRangeParser.isSnapshotId("26.2-pre-1"));
        assertTrue(VersionRangeParser.isSnapshotId("1.21.6-pre1"));
        assertTrue(VersionRangeParser.isSnapshotId("1.20-exp1"));
        assertTrue(VersionRangeParser.isSnapshotId("25w14a"));
        assertTrue(VersionRangeParser.isSnapshotId("24w14potato"));

        // Versions stables
        assertFalse(VersionRangeParser.isSnapshotId("26.2"));
        assertFalse(VersionRangeParser.isSnapshotId("1.21.11"));
        assertFalse(VersionRangeParser.isSnapshotId("1.19.4"));
        assertFalse(VersionRangeParser.isSnapshotId(""));
        assertFalse(VersionRangeParser.isSnapshotId(null));
    }

    @Test
    void testToCurseforgeVersion() {
        // CurseForge n'a qu'une entrée "<version>-snapshot" par cycle de développement.
        assertEquals("26.3-snapshot", VersionRangeParser.toCurseforgeVersion("26.3-snapshot-8"));
        assertEquals("26.3-snapshot", VersionRangeParser.toCurseforgeVersion("26.3-snapshot-1"));
        assertEquals("26.2-snapshot", VersionRangeParser.toCurseforgeVersion("26.2-rc-2"));
        assertEquals("26.2-snapshot", VersionRangeParser.toCurseforgeVersion("26.2-pre-1"));
        assertEquals("1.21.6-snapshot", VersionRangeParser.toCurseforgeVersion("1.21.6-pre1"));

        // Ancien schéma : identifiant conservé tel quel.
        assertEquals("25w14a", VersionRangeParser.toCurseforgeVersion("25w14a"));

        // Versions stables : inchangées.
        assertEquals("26.2", VersionRangeParser.toCurseforgeVersion("26.2"));
        assertEquals("1.21.11", VersionRangeParser.toCurseforgeVersion("1.21.11"));
    }

    @Test
    void testSemverSatisfies() {
        assertTrue(Version.parse("26.1", false).satisfies("~26.1"));
        assertTrue(Version.parse("26.1.1-rc.1").satisfies("~26.1"));
        assertFalse(Version.parse("26.2.0").satisfies("~26.1"));
        
        assertTrue(Version.parse("1.21.6").satisfies(">=1.21.6 & <=1.21.8"));
        assertTrue(Version.parse("1.21.8").satisfies(">=1.21.6 & <=1.21.8"));
        assertFalse(Version.parse("1.21.9").satisfies(">=1.21.6 & <=1.21.8"));
    }

    /**
     * Documente pourquoi {@code ~X.Y-} est traité à part dans le parseur : la bibliothèque ne
     * connaît pas cette convention et lève une exception, ce qui vidait silencieusement la liste
     * des versions publiées (CurseForge répondait alors « You must select at least one version »).
     */
    @Test
    void testTrailingHyphenIsNotSupportedBySemver() {
        assertThrows(Exception.class, () -> Version.parse("26.1.2", false).satisfies("~26.1-"));
    }

    /**
     * Un encadrement semver ne peut pas remplacer {@code ~X.Y-} : les préversions du cycle suivant
     * sont inférieures à sa version stable, donc {@code <26.2} les ramènerait à tort.
     */
    @Test
    void testNextCycleSnapshotsSortBelowTheirRelease() {
        assertTrue(Version.parse(VersionRangeParser.normalize("26.2-snapshot-1"), false)
                .satisfies(">=26.1 & <26.2"));
    }

    private static VersionRangeParser.CompiledVersions versions(String... ids) {
        List<VersionRangeParser.MinecraftVersion> list = new java.util.ArrayList<>();
        for (String id : ids) {
            String type = VersionRangeParser.isSnapshotId(id) ? "snapshot" : "release";
            list.add(new VersionRangeParser.MinecraftVersion(id, type, null, null, null, null, 0));
        }
        return new VersionRangeParser.CompiledVersions(list);
    }

    /** Un cycle encore en développement ({@code ~26.3-}) ne doit pas produire une liste vide. */
    @Test
    void testVersionLineKeepsSnapshotsOnlyCycle() {
        assertEquals(
                List.of("26.3-snapshot-1", "26.3-snapshot-8"),
                VersionRangeParser.commonVersionExtract(
                        "~26.3-",
                        versions("26.2", "26.2-rc-1", "26.3-snapshot-1", "26.3-snapshot-8")));
    }

    /** {@code ~26.1-} couvre 26.1, ses correctifs et leurs préversions, jamais le cycle suivant. */
    @Test
    void testVersionLineCoversPatchesAndPreReleases() {
        assertEquals(
                List.of("26.1-snapshot-1", "26.1-rc-1", "26.1", "26.1.1-rc-1", "26.1.1", "26.1.2"),
                VersionRangeParser.commonVersionExtract(
                        "~26.1-",
                        versions("1.21.11", "26.1-snapshot-1", "26.1-rc-1", "26.1",
                                "26.1.1-rc-1", "26.1.1", "26.1.2", "26.2-snapshot-1", "26.2")));
    }
}
