package net.paulem.simpleores.migration;

//? hasCopperTools {
/*import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.paulem.simpleores.SimpleOres;
import net.paulem.simpleores.stonecutter.SCIdentifier;

import java.util.Set;
import java.util.stream.Collectors;
*///?}

public class CopperMigration {
    //? hasCopperTools {
    /*private static final Set<Identifier> MIGRATE_ITEMS = Set.of(
            "copper_sword",
            "copper_pickaxe",
            "copper_axe",
            "copper_shovel",
            "copper_hoe",
            "copper_helmet",
            "copper_chestplate",
            "copper_leggings",
            "copper_boots"
    ).stream().map(s -> SCIdentifier.of(SimpleOres.MOD_ID, s)).collect(Collectors.toUnmodifiableSet());

    private static final Set<Identifier> MIGRATE_BLOCKS = Set.of(
            "copper_bars"
    ).stream().map(s -> SCIdentifier.of(SimpleOres.MOD_ID, s)).collect(Collectors.toUnmodifiableSet());

    public static void migrate() {
        MIGRATE_ITEMS.forEach(identifier -> {
            Registries.ITEM.addAlias(identifier, SCIdentifier.ofVanilla(identifier.getPath()));
        });

        MIGRATE_BLOCKS.forEach(identifier -> {
            Registries.BLOCK.addAlias(identifier, SCIdentifier.ofVanilla(identifier.getPath()));
            Registries.ITEM.addAlias(identifier, SCIdentifier.ofVanilla(identifier.getPath()));
        });
    }
    *///?}
}
