package net.paulem.simpleores.world;

import net.minecraft.core.registries.Registries;
import net.minecraft/*FIXME: IMPORTANT TO AVOID IT BEING REPLACED*/.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.paulem.simpleores.SimpleOres;
import net.paulem.simpleores.blocks.ModBlocks;
import net.paulem.simpleores.config.SimpleOresConfig;
import net.paulem.simpleores.stonecutter.SCId;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> TIN_ORE_KEY = registerKey("ore_tin");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TIN_VEIN_KEY = registerKey("tin_vein");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MYTHRIL_ORE_KEY = registerKey("ore_mythril");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ADAMANTIUM_ORE_KEY = registerKey("ore_adamantium");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ONYX_ORE_KEY = registerKey("ore_onyx");


    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        addOre(context, TIN_ORE_KEY, ModBlocks.TIN_ORE, ModBlocks.DEEPSLATE_TIN_ORE, SimpleOresConfig.NotEditable.tinOreBlocksPerVeins);
        addOre(context, TIN_VEIN_KEY, ModBlocks.TIN_ORE, ModBlocks.DEEPSLATE_TIN_ORE, SimpleOresConfig.NotEditable.tinVeinBlocksPerVeins);
        addOre(context, MYTHRIL_ORE_KEY, ModBlocks.MYTHRIL_ORE, ModBlocks.DEEPSLATE_MYTHRIL_ORE, SimpleOresConfig.NotEditable.mythrilBlocksPerVeins);
        addOre(context, ADAMANTIUM_ORE_KEY, ModBlocks.ADAMANTIUM_ORE, ModBlocks.DEEPSLATE_ADAMANTIUM_ORE, SimpleOresConfig.NotEditable.adamantiumBlocksPerVeins);
        addOreNether(context, ONYX_ORE_KEY, ModBlocks.ONYX_ORE, SimpleOresConfig.NotEditable.onyxBlocksPerVeins);
    }

    private static void addOre(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, Block stoneOre, Block deepslateOre, int count){
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> overworldOres =
                List.of(OreConfiguration.target(stoneReplaceables, stoneOre.defaultBlockState()),
                        OreConfiguration.target(deepslateReplaceables, deepslateOre.defaultBlockState()));

        register(context, key, Feature.ORE, new OreConfiguration(overworldOres, count));
    }

    private static void addOreNether(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, Block stoneOre, int count){
        RuleTest netherrackReplaceables = new BlockMatchTest(Blocks.NETHERRACK);

        List<OreConfiguration.TargetBlockState> netherrackOres =
                List.of(OreConfiguration.target(netherrackReplaceables, stoneOre.defaultBlockState()));

        register(context, key, Feature.ORE, new OreConfiguration(netherrackOres, count));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, SCId.of(SimpleOres.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                   ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
