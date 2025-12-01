package net.paulem.simpleores.migration;

//? hasBucketlib && >1.21.3 {
/*import net.minecraft.registry.Registries;
import net.minecraft.resources.ResourceLocation;
import net.paulem.simpleores.SimpleOres;
import net.paulem.simpleores.stonecutter.SCId;

import java.util.Set;
import java.util.stream.Collectors;
*///?}

public class CopperBucketMigration {
    //? hasBucketlib && >1.21.3 {
    /*private static final Set<ResourceLocation> MIGRATE_ITEMS = Set.of(
            "copper_water_bucket",
            "copper_lava_bucket",

            // Some other mods
            "copper_dirty_water_bucket", // Energized Power
            "copper_liquid_null_bucket" // Biomes O' Plenty'
    ).stream().map(s -> SCId.of(SimpleOres.MOD_ID, s)).collect(Collectors.toUnmodifiableSet());

    public static void migrate() {
        MIGRATE_ITEMS.forEach(identifier -> {
            String path = identifier.getPath();
            if (path.matches(".+_.+_bucket")) {
                String aliasPath = path.replaceFirst("_(.+)_bucket$", "_bucket");
                Registries.ITEM.addAlias(identifier, SCId.ofVanilla(aliasPath));
            }
        });
    }
    *///?}
}
