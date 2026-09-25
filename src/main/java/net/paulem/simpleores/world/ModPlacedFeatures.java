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
    public static final ResourceKey<PlacedFeature> TIN_INTRUSION_PLACED_KEY = registerKey("ore_tin_intrusion");
    public static final ResourceKey<PlacedFeature> TIN_PLACER_PLACED_KEY = registerKey("ore_tin_placer");
    public static final ResourceKey<PlacedFeature> TIN_VEIN_PLACED_KEY = registerKey("ore_tin_vein");
    public static final ResourceKey<PlacedFeature> MYTHRIL_DEPOSIT_PLACED_KEY = registerKey("ore_mythril_deposit");
    public static final ResourceKey<PlacedFeature> MYTHRIL_INTRUSION_PLACED_KEY = registerKey("ore_mythril_intrusion");
    public static final ResourceKey<PlacedFeature> ADAMANTIUM_DEPOSIT_PLACED_KEY = registerKey("ore_adamantium_deposit");
    public static final ResourceKey<PlacedFeature> ADAMANTIUM_INTRUSION_PLACED_KEY = registerKey("ore_adamantium_intrusion");
    public static final ResourceKey<PlacedFeature> ONYX_BLACKSTONE_PLACED_KEY = registerKey("ore_onyx_blackstone");
    public static final ResourceKey<PlacedFeature> ONYX_BASALT_PLACED_KEY = registerKey("ore_onyx_basalt");
    public static final ResourceKey<PlacedFeature> ONYX_ALL_PLACED_KEY = registerKey("ore_onyx_all");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter< //$ if >26.2 'Feature' else 'ConfiguredFeature<?, ?>'
                ConfiguredFeature<?, ?>
                > holderLookup = context.lookup( //$ if >26.2 'Registries.FEATURE' else 'Registries.CONFIGURED_FEATURE'
                Registries.CONFIGURED_FEATURE
        );

        Holder< //$ if >26.2 'Feature' else 'ConfiguredFeature<?, ?>'
                ConfiguredFeature<?, ?>
                > tinEntry = holderLookup.getOrThrow(ModConfiguredFeatures.TIN_ORE_KEY);
        Holder< //$ if >26.2 'Feature' else 'ConfiguredFeature<?, ?>'
                ConfiguredFeature<?, ?>
                > tinVeinEntry = holderLookup.getOrThrow(ModConfiguredFeatures.TIN_VEIN_KEY);
        Holder< //$ if >26.2 'Feature' else 'ConfiguredFeature<?, ?>'
                ConfiguredFeature<?, ?>
                > mythrilEntry = holderLookup.getOrThrow(ModConfiguredFeatures.MYTHRIL_ORE_KEY);
        Holder< //$ if >26.2 'Feature' else 'ConfiguredFeature<?, ?>'
                ConfiguredFeature<?, ?>
                > adamantiumEntry = holderLookup.getOrThrow(ModConfiguredFeatures.ADAMANTIUM_ORE_KEY);
        Holder< //$ if >26.2 'Feature' else 'ConfiguredFeature<?, ?>'
                ConfiguredFeature<?, ?>
                > onyxEntry = holderLookup.getOrThrow(ModConfiguredFeatures.ONYX_ORE_KEY);
        Holder< //$ if >26.2 'Feature' else 'ConfiguredFeature<?, ?>'
                ConfiguredFeature<?, ?>
                > onyxBlackstoneEntry = holderLookup.getOrThrow(ModConfiguredFeatures.ONYX_BLACKSTONE_KEY);
        Holder< //$ if >26.2 'Feature' else 'ConfiguredFeature<?, ?>'
                ConfiguredFeature<?, ?>
                > onyxBasaltEntry = holderLookup.getOrThrow(ModConfiguredFeatures.ONYX_BASALT_KEY);

        // Same values as the base mod (SimpleOres2)
        PlacementUtils.register(context, TIN_INTRUSION_PLACED_KEY, tinEntry,
                ModOrePlacement.modifiersWithCount(BaseSimpleOresConfig.NotEditable.tinIntrusionVeinPerChunks,
                        HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(64), VerticalAnchor.aboveBottom(136))));
        PlacementUtils.register(context, TIN_PLACER_PLACED_KEY, tinEntry,
                ModOrePlacement.modifiersWithCount(BaseSimpleOresConfig.NotEditable.tinOreVeinPerChunks,
                        HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(108), VerticalAnchor.belowTop(236))));
        PlacementUtils.register(context, TIN_VEIN_PLACED_KEY, tinVeinEntry,
                ModOrePlacement.modifiersWithCount(BaseSimpleOresConfig.NotEditable.tinVeinVeinPerChunks,
                        HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(24), VerticalAnchor.aboveBottom(64))));

        PlacementUtils.register(context, MYTHRIL_DEPOSIT_PLACED_KEY, mythrilEntry,
                ModOrePlacement.modifiersWithCount(BaseSimpleOresConfig.NotEditable.mythrilVeinPerChunks,
                        HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(1), VerticalAnchor.aboveBottom(96))));
        PlacementUtils.register(context, MYTHRIL_INTRUSION_PLACED_KEY, mythrilEntry,
                ModOrePlacement.modifiersWithCount(BaseSimpleOresConfig.NotEditable.mythrilIntrusionVeinPerChunks,
                        HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(1), VerticalAnchor.aboveBottom(99))));

        PlacementUtils.register(context, ADAMANTIUM_DEPOSIT_PLACED_KEY, adamantiumEntry,
                ModOrePlacement.modifiersWithCount(BaseSimpleOresConfig.NotEditable.adamantiumVeinPerChunks,
                        HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(1), VerticalAnchor.aboveBottom(48))));
        PlacementUtils.register(context, ADAMANTIUM_INTRUSION_PLACED_KEY, adamantiumEntry,
                ModOrePlacement.modifiersWithCount(BaseSimpleOresConfig.NotEditable.adamantiumIntrusionVeinPerChunks,
                        HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(1), VerticalAnchor.aboveBottom(84))));

        PlacementUtils.register(context, ONYX_BLACKSTONE_PLACED_KEY, onyxBlackstoneEntry,
                ModOrePlacement.modifiersWithCount(BaseSimpleOresConfig.NotEditable.onyxVeinPerChunks,
                        HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(4), VerticalAnchor.belowTop(4))));
        PlacementUtils.register(context, ONYX_BASALT_PLACED_KEY, onyxBasaltEntry,
                ModOrePlacement.modifiersWithCount(BaseSimpleOresConfig.NotEditable.onyxVeinPerChunks,
                        HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(4), VerticalAnchor.belowTop(4))));
        PlacementUtils.register(context, ONYX_ALL_PLACED_KEY, onyxEntry,
                ModOrePlacement.modifiersWithCount(BaseSimpleOresConfig.NotEditable.onyxVeinPerChunks,
                        HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(10), VerticalAnchor.belowTop(10))));
    }

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, SCId.of(SimpleOres.MOD_ID, name));
    }
}