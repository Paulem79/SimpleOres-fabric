package net.paulem.simpleores.migration;

//? hasCopperTools {
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.paulem.simpleores.SimpleOres;
import net.paulem.simpleores.stonecutter.SCId;

import java.util.Set;
import java.util.stream.Collectors;
//?}

public class CopperMigration {
    //? hasCopperTools {
    private static final Set<Identifier> MIGRATE_ITEMS = Set.of(
            "copper_sword",
            "copper_pickaxe",
            "copper_axe",
            "copper_shovel",
            "copper_hoe",
            "copper_helmet",
            "copper_chestplate",
            "copper_leggings",
            "copper_boots"
    ).stream().map(s -> SCId.of(SimpleOres.MOD_ID, s)).collect(Collectors.toUnmodifiableSet());

    private static final Set<Identifier> MIGRATE_BLOCKS = Set.of(
            "copper_bars"
    ).stream().map(s -> SCId.of(SimpleOres.MOD_ID, s)).collect(Collectors.toUnmodifiableSet());

    public static void migrate() {
        MIGRATE_ITEMS.forEach(identifier -> {
            BuiltInRegistries.ITEM.addAlias(identifier, SCId.ofVanilla(identifier.getPath()));
        });

        MIGRATE_BLOCKS.forEach(identifier -> {
            BuiltInRegistries.BLOCK.addAlias(identifier, SCId.ofVanilla(identifier.getPath()));
            BuiltInRegistries.ITEM.addAlias(identifier, SCId.ofVanilla(identifier.getPath()));
        });
    }
    //?}
}
