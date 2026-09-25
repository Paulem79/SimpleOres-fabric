package net.paulem.simpleores.fabric;

import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.LootPool;
import net.paulem.simpleores.SimpleOres;
import net.paulem.simpleores.stonecutter.SCId;

import java.util.Map;

//? if <=1.20.6 {
/*import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
*///?} else {
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
//?}
//? if <=1.20.4 {
/*import net.minecraft.world.level.storage.loot.entries.LootTableReference;
*///?} else {
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
//?}

/**
 * Adds the loot of the base mod (SimpleOres2) to the structure chests, by adding a pool that rolls
 * the matching table of {@code data/simpleores/loot_table(s)/chest/}. The tables are the ones of the
 * base mod, with the vanilla copper equipment when it exists in the Minecraft version.
 */
public class ModChestLoot {
    /** Vanilla loot table path -> table of the mod. Like in the base mod, a few structures share a table. */
    private static final Map<String, String> INJECTED_TABLES = Map.ofEntries(
            Map.entry("chests/abandoned_mineshaft", "abandoned_mineshaft"),
            Map.entry("chests/bastion_bridge", "bastion"),
            Map.entry("chests/bastion_hoglin_stable", "bastion"),
            Map.entry("chests/bastion_other", "bastion"),
            Map.entry("chests/bastion_treasure", "bastion"),
            Map.entry("chests/nether_bridge", "bastion"),
            Map.entry("chests/buried_treasure", "buried_treasure"),
            Map.entry("chests/desert_pyramid", "desert_pyramid"),
            Map.entry("chests/igloo_chest", "igloo_chest"),
            Map.entry("chests/jungle_temple", "jungle_temple"),
            Map.entry("chests/ruined_portal", "ruined_portal"),
            Map.entry("chests/simple_dungeon", "simple_dungeon"),
            Map.entry("chests/shipwreck_map", "simple_dungeon"),
            Map.entry("chests/shipwreck_supply", "simple_dungeon"),
            Map.entry("chests/shipwreck_treasure", "simple_dungeon"),
            Map.entry("chests/stronghold_corridor", "simple_dungeon"),
            Map.entry("chests/stronghold_crossing", "simple_dungeon"),
            Map.entry("chests/underwater_ruin_big", "simple_dungeon"),
            Map.entry("chests/underwater_ruin_small", "simple_dungeon"),
            Map.entry("chests/spawn_bonus_chest", "spawn_bonus_chest"),
            Map.entry("chests/village/village_armorer", "village_armorer"),
            Map.entry("chests/village/village_fletcher", "village_fletcher"),
            Map.entry("chests/village/village_mason", "village_mason"),
            Map.entry("chests/village/village_shepherd", "village_shepherd"),
            Map.entry("chests/village/village_toolsmith", "village_toolsmith"),
            Map.entry("chests/village/village_weaponsmith", "village_weaponsmith")
    );

    public static void register() {
        LootTableEvents.MODIFY.register((
                //? if <=1.20.4 {
                /*resourceManager, lootManager, id, tableBuilder, source
                *///?} else if <=1.20.6 {
                /*key, tableBuilder, source
                *///?} else {
                key, tableBuilder, source, registries
                //?}
        ) -> {
            //? if <=1.20.4 {
            /*Identifier target = id;
            *///?} else if <=1.21.10 {
            /*Identifier target = key.location();
            *///?} else {
            Identifier target = key.identifier();
            //?}
            if (!"minecraft".equals(target.getNamespace())) return;

            String table = INJECTED_TABLES.get(target.getPath());
            if (table == null) return;

            Identifier tableId = SCId.of(SimpleOres.MOD_ID, "chest/" + table);
            tableBuilder.pool(LootPool.lootPool()
                    //? if <=1.20.4 {
                    /*.add(LootTableReference.lootTableReference(tableId))
                    *///?} else {
                    .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, tableId)))
                    //?}
                    .build());
        });
    }
}
