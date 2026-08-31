package net.paulem.simpleores.world;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.paulem.simpleores.SimpleOres;
import net.paulem.simpleores.config.BaseSimpleOresConfig;
import net.paulem.simpleores.stonecutter.SCId;

//? if >26.2 {
/*import net.minecraft.world.level.levelgen.feature.Feature;
*///?} else {
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
 //?}

// TODO : More affiliated to base gen
public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> TIN_ORE_PLACED_KEY = registerKey("ore_tin");
    public static final ResourceKey<PlacedFeature> TIN_VEIN_PLACED_KEY = registerKey("tin_vein");
    public static final ResourceKey<PlacedFeature> MYTHRIL_ORE_PLACED_KEY = registerKey("ore_mythril");
    public static final ResourceKey<PlacedFeature> ADAMANTIUM_ORE_PLACED_KEY = registerKey("ore_adamantium");
    public static final ResourceKey<PlacedFeature> ONYX_ORE_PLACED_KEY = registerKey("ore_onyx");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter< //$ if >26.2 'Feature' else 'ConfiguredFeature<?, ?>'
                ConfiguredFeature<?, ?>
                > HolderLookup = context.lookup( //$ if >26.2 'Registries.FEATURE' else 'Registries.CONFIGURED_FEATURE'
                Registries.CONFIGURED_FEATURE
        );

        Holder< //$ if >26.2 'Feature' else 'ConfiguredFeature<?, ?>'
                ConfiguredFeature<?, ?>
                > tinEntry = HolderLookup.getOrThrow(ModConfiguredFeatures.TIN_ORE_KEY);

        PlacementUtils.register(
                context,
                TIN_ORE_PLACED_KEY,
                tinEntry,
                ModOrePlacement.modifiersWithCount(BaseSimpleOresConfig.NotEditable.tinOreVeinPerChunks, // Vein per chunk
                        HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(108), VerticalAnchor.belowTop(236)))
        );

        Holder< //$ if >26.2 'Feature' else 'ConfiguredFeature<?, ?>'
                ConfiguredFeature<?, ?>
                > tinVeinEntry = HolderLookup.getOrThrow(ModConfiguredFeatures.TIN_VEIN_KEY);

        PlacementUtils.register(
                context,
                TIN_VEIN_PLACED_KEY,
                tinVeinEntry,
                ModOrePlacement.modifiersWithCount(BaseSimpleOresConfig.NotEditable.tinVeinVeinPerChunks, // Vein per chunk
                        HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(24), VerticalAnchor.aboveBottom(64)))
        );

        Holder< //$ if >26.2 'Feature' else 'ConfiguredFeature<?, ?>'
                ConfiguredFeature<?, ?>
                > mythrilEntry = HolderLookup.getOrThrow(ModConfiguredFeatures.MYTHRIL_ORE_KEY);

        PlacementUtils.register(
                context,
                MYTHRIL_ORE_PLACED_KEY,
                mythrilEntry,
                ModOrePlacement.modifiersWithCount(BaseSimpleOresConfig.NotEditable.mythrilVeinPerChunks, // Vein per chunk
                        HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(1), VerticalAnchor.aboveBottom(96)))
        );

        Holder< //$ if >26.2 'Feature' else 'ConfiguredFeature<?, ?>'
                ConfiguredFeature<?, ?>
                > adamantiumEntry = HolderLookup.getOrThrow(ModConfiguredFeatures.ADAMANTIUM_ORE_KEY);

        PlacementUtils.register(
                context,
                ADAMANTIUM_ORE_PLACED_KEY,
                adamantiumEntry,
                ModOrePlacement.modifiersWithCount(BaseSimpleOresConfig.NotEditable.adamantiumVeinPerChunks, // Vein per chunk
                        HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(1), VerticalAnchor.aboveBottom(48)))
        );

        Holder< //$ if >26.2 'Feature' else 'ConfiguredFeature<?, ?>'
                ConfiguredFeature<?, ?>
                > onyxEntry = HolderLookup.getOrThrow(ModConfiguredFeatures.ONYX_ORE_KEY);

        PlacementUtils.register(
                context,
                ONYX_ORE_PLACED_KEY,
                onyxEntry,
                ModOrePlacement.modifiersWithCount(BaseSimpleOresConfig.NotEditable.onyxVeinPerChunks, // Vein per chunk
                        HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(10), VerticalAnchor.belowTop(10)))
        );
    }

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, SCId.of(SimpleOres.MOD_ID, name));
    }
}