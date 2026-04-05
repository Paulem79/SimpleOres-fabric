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
    void testSemverSatisfies() {
        assertTrue(Version.parse("26.1", false).satisfies("~26.1"));
        assertTrue(Version.parse("26.1.1-rc.1").satisfies("~26.1"));
        assertFalse(Version.parse("26.2.0").satisfies("~26.1"));
        
        assertTrue(Version.parse("1.21.6").satisfies(">=1.21.6 & <=1.21.8"));
        assertTrue(Version.parse("1.21.8").satisfies(">=1.21.6 & <=1.21.8"));
        assertFalse(Version.parse("1.21.9").satisfies(">=1.21.6 & <=1.21.8"));
    }
}
