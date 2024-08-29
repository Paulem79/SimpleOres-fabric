package io.github.paulem.simpleores.datagen.world;

import io.github.paulem.simpleores.SimpleOres;
import io.github.paulem.simpleores.blocks.ModBlocks;
import io.github.paulem.simpleores.config.SimpleOresConfig;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.List;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> TIN_ORE_KEY = registerKey("ore_tin");
    public static final RegistryKey<ConfiguredFeature<?, ?>> TIN_VEIN_KEY = registerKey("tin_vein");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MYTHRIL_ORE_KEY = registerKey("ore_mythril");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ADAMANTIUM_ORE_KEY = registerKey("ore_adamantium");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ONYX_ORE_KEY = registerKey("ore_onyx");


    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        addOre(context, TIN_ORE_KEY, ModBlocks.TIN_ORE, ModBlocks.DEEPSLATE_TIN_ORE, SimpleOresConfig.NotEditable.tinOreBlocksPerVeins);
        addOre(context, TIN_VEIN_KEY, ModBlocks.TIN_ORE, ModBlocks.DEEPSLATE_TIN_ORE, SimpleOresConfig.NotEditable.tinVeinBlocksPerVeins);
        addOre(context, MYTHRIL_ORE_KEY, ModBlocks.MYTHRIL_ORE, ModBlocks.DEEPSLATE_MYTHRIL_ORE, SimpleOresConfig.NotEditable.mythrilBlocksPerVeins);
        addOre(context, ADAMANTIUM_ORE_KEY, ModBlocks.ADAMANTIUM_ORE, ModBlocks.DEEPSLATE_ADAMANTIUM_ORE, SimpleOresConfig.NotEditable.adamantiumBlocksPerVeins);
        addOreNether(context, ONYX_ORE_KEY, ModBlocks.ONYX_ORE, SimpleOresConfig.NotEditable.onyxBlocksPerVeins);
    }

    private static void addOre(Registerable<ConfiguredFeature<?, ?>> context, RegistryKey<ConfiguredFeature<?, ?>> key, Block stoneOre, Block deepslateOre, int count){
        RuleTest stoneReplaceables = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreFeatureConfig.Target> overworldOres =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables, stoneOre.getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplaceables, deepslateOre.getDefaultState()));

        register(context, key, Feature.ORE, new OreFeatureConfig(overworldOres, count));
    }

    private static void addOreNether(Registerable<ConfiguredFeature<?, ?>> context, RegistryKey<ConfiguredFeature<?, ?>> key, Block stoneOre, int count){
        RuleTest netherrackReplaceables = new BlockMatchRuleTest(Blocks.NETHERRACK);

        List<OreFeatureConfig.Target> netherrackOres =
                List.of(OreFeatureConfig.createTarget(netherrackReplaceables, stoneOre.getDefaultState()));

        register(context, key, Feature.ORE, new OreFeatureConfig(netherrackOres, count));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(SimpleOres.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}