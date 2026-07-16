package net.paulem.simpleores.world;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.paulem.simpleores.SimpleOres;
import net.paulem.simpleores.blocks.ModBlocks;
import net.paulem.simpleores.config.BaseSimpleOresConfig;
import net.paulem.simpleores.stonecutter.SCId;

import java.util.List;

//? if >26.2 {
import net.minecraft.world.level.levelgen.feature.BlockReplacement;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.HeightMatchTest;
//?} else {
/*import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
*///?}

public class ModConfiguredFeatures {
    public static final ResourceKey< //$ if >26.2 'Feature' else 'ConfiguredFeature<?, ?>'
            Feature
            >
            TIN_ORE_KEY = registerKey("ore_tin");
    public static final ResourceKey< //$ if >26.2 'Feature' else 'ConfiguredFeature<?, ?>'
            Feature
            >
            TIN_VEIN_KEY = registerKey("tin_vein");
    public static final ResourceKey< //$ if >26.2 'Feature' else 'ConfiguredFeature<?, ?>'
            Feature
            >
            MYTHRIL_ORE_KEY = registerKey("ore_mythril");
    public static final ResourceKey< //$ if >26.2 'Feature' else 'ConfiguredFeature<?, ?>'
            Feature
            >
            ADAMANTIUM_ORE_KEY = registerKey("ore_adamantium");
    public static final ResourceKey< //$ if >26.2 'Feature' else 'ConfiguredFeature<?, ?>'
            Feature
            >
            ONYX_ORE_KEY = registerKey("ore_onyx");

    public static void bootstrap(BootstrapContext< //$ if >26.2 'Feature' else 'ConfiguredFeature<?, ?>'
            Feature
            > context) {
        addOre(context, TIN_ORE_KEY, ModBlocks.TIN_ORE, ModBlocks.DEEPSLATE_TIN_ORE, BaseSimpleOresConfig.NotEditable.tinOreBlocksPerVeins);
        addOre(context, TIN_VEIN_KEY, ModBlocks.TIN_ORE, ModBlocks.DEEPSLATE_TIN_ORE, BaseSimpleOresConfig.NotEditable.tinVeinBlocksPerVeins);
        addOre(context, MYTHRIL_ORE_KEY, ModBlocks.MYTHRIL_ORE, ModBlocks.DEEPSLATE_MYTHRIL_ORE, BaseSimpleOresConfig.NotEditable.mythrilBlocksPerVeins);
        addOre(context, ADAMANTIUM_ORE_KEY, ModBlocks.ADAMANTIUM_ORE, ModBlocks.DEEPSLATE_ADAMANTIUM_ORE, BaseSimpleOresConfig.NotEditable.adamantiumBlocksPerVeins);
        addOreNether(context, ONYX_ORE_KEY, ModBlocks.ONYX_ORE, BaseSimpleOresConfig.NotEditable.onyxBlocksPerVeins);
    }

    //? if >26.2 {
    private static void addOre(BootstrapContext<Feature> context, ResourceKey<Feature> key, Block stoneOre, Block deepslateOre, int count) {
        RuleTest stoneReplaceables = RuleTest.either(
                new TagMatchTest(BlockTags.HEIGHT_SPECIFIC_ORE_REPLACEABLES), HeightMatchTest.min(0), new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES)
        );
        RuleTest deepslateReplaceables = RuleTest.either(
                new TagMatchTest(BlockTags.HEIGHT_SPECIFIC_ORE_REPLACEABLES), HeightMatchTest.max(8), new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES)
        );

        List<BlockReplacement> overworldOres = List.of(
                BlockReplacement.replace(stoneReplaceables, stoneOre.defaultBlockState()),
                BlockReplacement.replace(deepslateReplaceables, deepslateOre.defaultBlockState())
        );

        context.register(key, new OreFeature(overworldOres, count));
    }

    private static void addOreNether(BootstrapContext<Feature> context, ResourceKey<Feature> key, Block stoneOre, int count) {
        RuleTest netherrackReplaceables = new BlockMatchTest(Blocks.NETHERRACK);

        List<BlockReplacement> netherrackOres = List.of(
                BlockReplacement.replace(netherrackReplaceables, stoneOre.defaultBlockState())
        );

        context.register(key, new OreFeature(netherrackOres, count));
    }
    //?} else {
    /*private static void addOre(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, Block stoneOre, Block deepslateOre, int count){
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

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
    *///?}

    public static ResourceKey< //$ if >26.2 'Feature' else 'ConfiguredFeature<?, ?>'
            Feature
            > registerKey(String name) {
        return ResourceKey.create( //$ if >26.2 'Registries.FEATURE' else 'Registries.CONFIGURED_FEATURE'
                Registries.FEATURE
                , SCId.of(SimpleOres.MOD_ID, name));
    }
}