package net.paulem.simpleores.gametest;

//? if >=26.2 && <=26.2 {
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.paulem.simpleores.blocks.ModBlocks;
import net.paulem.simpleores.world.ModConfiguredFeatures;
import net.paulem.simpleores.world.ModPlacedFeatures;

import java.util.List;

/**
 * Checks the world generation and the chest loot against the base mod (SimpleOres2).
 */
public class WorldGenAndLootTest {
    private static final int ORES = GenerationStep.Decoration.UNDERGROUND_ORES.ordinal();
    private static final int DECORATION = GenerationStep.Decoration.UNDERGROUND_DECORATION.ordinal();

    @GameTest
    public void featuresAreAddedToTheRightBiomes(GameTestHelper helper) {
        List<ResourceKey<PlacedFeature>> overworld = List.of(
                ModPlacedFeatures.TIN_INTRUSION_PLACED_KEY, ModPlacedFeatures.TIN_PLACER_PLACED_KEY, ModPlacedFeatures.TIN_VEIN_PLACED_KEY,
                ModPlacedFeatures.MYTHRIL_DEPOSIT_PLACED_KEY, ModPlacedFeatures.MYTHRIL_INTRUSION_PLACED_KEY,
                ModPlacedFeatures.ADAMANTIUM_DEPOSIT_PLACED_KEY, ModPlacedFeatures.ADAMANTIUM_INTRUSION_PLACED_KEY);

        for (ResourceKey<PlacedFeature> feature : overworld) {
            assertFeature(helper, Biomes.PLAINS, ORES, feature, true);
            assertFeature(helper, Biomes.NETHER_WASTES, ORES, feature, false);
        }

        // Onyx: blackstone and basalt everywhere in the Nether, netherrack in the basalt deltas only
        for (ResourceKey<Biome> biome : List.of(Biomes.NETHER_WASTES, Biomes.CRIMSON_FOREST, Biomes.SOUL_SAND_VALLEY, Biomes.BASALT_DELTAS)) {
            assertFeature(helper, biome, DECORATION, ModPlacedFeatures.ONYX_BLACKSTONE_PLACED_KEY, true);
            assertFeature(helper, biome, DECORATION, ModPlacedFeatures.ONYX_BASALT_PLACED_KEY, true);
            assertFeature(helper, biome, ORES, ModPlacedFeatures.ONYX_ALL_PLACED_KEY, false);
            assertFeature(helper, biome, DECORATION, ModPlacedFeatures.ONYX_ALL_PLACED_KEY, biome == Biomes.BASALT_DELTAS);
        }
        assertFeature(helper, Biomes.PLAINS, DECORATION, ModPlacedFeatures.ONYX_BASALT_PLACED_KEY, false);
        helper.succeed();
    }

    @GameTest
    public void oresReplaceTheirTargets(GameTestHelper helper) {
        assertOre(helper, ModConfiguredFeatures.TIN_ORE_KEY, Blocks.STONE, ModBlocks.TIN_ORE);
        assertOre(helper, ModConfiguredFeatures.TIN_VEIN_KEY, Blocks.DEEPSLATE, ModBlocks.DEEPSLATE_TIN_ORE);
        assertOre(helper, ModConfiguredFeatures.MYTHRIL_ORE_KEY, Blocks.STONE, ModBlocks.MYTHRIL_ORE);
        assertOre(helper, ModConfiguredFeatures.ADAMANTIUM_ORE_KEY, Blocks.DEEPSLATE, ModBlocks.DEEPSLATE_ADAMANTIUM_ORE);
        assertOre(helper, ModConfiguredFeatures.ONYX_ORE_KEY, Blocks.NETHERRACK, ModBlocks.ONYX_ORE);
        assertOre(helper, ModConfiguredFeatures.ONYX_BLACKSTONE_KEY, Blocks.BLACKSTONE, ModBlocks.ONYX_ORE);
        assertOre(helper, ModConfiguredFeatures.ONYX_BASALT_KEY, Blocks.BASALT, ModBlocks.BASALT_ONYX_ORE);
        helper.succeed();
    }

    @GameTest
    public void structureChestsGetTheModLoot(GameTestHelper helper) {
        for (String path : List.of(
                "abandoned_mineshaft", "bastion_bridge", "bastion_hoglin_stable", "bastion_other", "bastion_treasure", "nether_bridge",
                "buried_treasure", "desert_pyramid", "igloo_chest", "jungle_temple", "ruined_portal", "simple_dungeon",
                "shipwreck_map", "shipwreck_supply", "shipwreck_treasure", "stronghold_corridor", "stronghold_crossing",
                "underwater_ruin_big", "underwater_ruin_small", "spawn_bonus_chest",
                "village/village_armorer", "village/village_fletcher", "village/village_mason", "village/village_shepherd",
                "village/village_toolsmith", "village/village_weaponsmith")) {
            boolean found = false;
            for (int i = 0; i < 600 && !found; i++) {
                for (ItemStack stack : roll(helper, Identifier.withDefaultNamespace("chests/" + path))) {
                    if (isModItem(stack)) found = true;
                }
            }
            helper.assertTrue(found, "minecraft:chests/" + path + " should sometimes contain items of the mod");
        }
        helper.succeed();
    }

    @GameTest
    public void modChestTablesLoad(GameTestHelper helper) {
        for (String table : List.of("abandoned_mineshaft", "bastion", "buried_treasure", "desert_pyramid", "igloo_chest", "jungle_temple",
                "ruined_portal", "simple_dungeon", "spawn_bonus_chest", "village_armorer", "village_fletcher", "village_mason",
                "village_shepherd", "village_toolsmith", "village_weaponsmith")) {
            boolean found = false;
            for (int i = 0; i < 200 && !found; i++) {
                found = !roll(helper, Identifier.fromNamespaceAndPath("simpleores", "chest/" + table)).isEmpty();
            }
            helper.assertTrue(found, "simpleores:chest/" + table + " should drop something");
        }
        helper.succeed();
    }

    private static boolean isModItem(ItemStack stack) {
        Identifier id = BuiltInRegistries.ITEM.getKey(stack.getItem());
        return id.getNamespace().equals("simpleores") || id.getPath().startsWith("tin_") || id.getPath().startsWith("mythril_")
                || id.getPath().startsWith("adamantium_") || id.getPath().startsWith("onyx_");
    }

    private static List<ItemStack> roll(GameTestHelper helper, Identifier id) {
        ServerLevel level = helper.getLevel();
        LootTable table = level.getServer().reloadableRegistries().getLootTable(ResourceKey.create(Registries.LOOT_TABLE, id));
        LootParams params = new LootParams.Builder(level).withParameter(LootContextParams.ORIGIN, Vec3.ZERO).create(LootContextParamSets.CHEST);
        return table.getRandomItems(params);
    }

    private static void assertFeature(GameTestHelper helper, ResourceKey<Biome> biomeKey, int step, ResourceKey<PlacedFeature> feature, boolean expected) {
        Biome biome = helper.getLevel().registryAccess().lookupOrThrow(Registries.BIOME).getOrThrow(biomeKey).value();
        List<HolderSet<PlacedFeature>> steps = biome.getGenerationSettings().features();
        boolean present = step < steps.size() && steps.get(step).stream().anyMatch(holder -> holder.is(feature));
        helper.assertTrue(present == expected, feature.identifier() + (expected ? " should be" : " should not be")
                + " in step " + step + " of " + biomeKey.identifier());
    }

    /** Places the feature in a cube of the block it replaces and expects at least one ore. */
    private static void assertOre(GameTestHelper helper, ResourceKey<net.minecraft.world.level.levelgen.feature.ConfiguredFeature<?, ?>> key, Block base, Block ore) {
        ServerLevel level = helper.getLevel();
        BlockPos center = helper.absolutePos(new BlockPos(0, 20, 0));
        for (BlockPos pos : BlockPos.betweenClosed(center.offset(-12, -12, -12), center.offset(12, 12, 12))) {
            level.setBlock(pos, base.defaultBlockState(), Block.UPDATE_CLIENTS);
        }

        Holder<net.minecraft.world.level.levelgen.feature.ConfiguredFeature<?, ?>> feature = level.registryAccess()
                .lookupOrThrow(Registries.CONFIGURED_FEATURE).getOrThrow(key);
        // Small veins sometimes end up placing nothing, so a few attempts are allowed
        boolean placed = false;
        for (int attempt = 0; attempt < 20 && !placed; attempt++) {
            placed = feature.value().place(level, level.getChunkSource().getGenerator(), level.getRandom(), center);
        }
        helper.assertTrue(placed, key.identifier() + " should be placed in " + base);

        int count = 0;
        for (BlockPos pos : BlockPos.betweenClosed(center.offset(-12, -12, -12), center.offset(12, 12, 12))) {
            if (level.getBlockState(pos).is(ore)) count++;
        }
        helper.assertTrue(count > 0, key.identifier() + " should place " + ore + " into " + base);
    }
}
//?} else {
/*// Only checked on 26.2, see BucketAdvancementsTest
public class WorldGenAndLootTest {
}
*///?}
