package net.paulem.simpleores.migration;

//? >1.21 {
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.paulem.simpleores.SimpleOres;
import net.paulem.simpleores.stonecutter.SCId;

import java.util.Set;
import java.util.stream.Collectors;
//?}

public class CopperDoorMigration {
    //? >1.21 {
    private static final Set<Identifier> MIGRATE_BLOCKS = Set.of(
            "copper_door"
    ).stream().map(s -> SCId.of(SimpleOres.MOD_ID, s)).collect(Collectors.toUnmodifiableSet());

    public static void migrate() {
        MIGRATE_BLOCKS.forEach(identifier -> {
            BuiltInRegistries.BLOCK.addAlias(identifier, SCId.ofVanilla(identifier.getPath()));
            BuiltInRegistries.ITEM.addAlias(identifier, SCId.ofVanilla(identifier.getPath()));
        });
    }
    //?}
}
