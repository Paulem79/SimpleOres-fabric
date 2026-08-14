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
}
