package io.github.paulem.simpleores.datagen.world;

import io.github.paulem.simpleores.SimpleOres;
import io.github.paulem.simpleores.config.SimpleOresConfig;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;

// TODO : More affiliated to base gen
public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> TIN_ORE_PLACED_KEY = registerKey("ore_tin");
    public static final RegistryKey<PlacedFeature> TIN_VEIN_PLACED_KEY = registerKey("tin_vein");
    public static final RegistryKey<PlacedFeature> MYTHRIL_ORE_PLACED_KEY = registerKey("ore_mythril");
    public static final RegistryKey<PlacedFeature> ADAMANTIUM_ORE_PLACED_KEY = registerKey("ore_adamantium");
    public static final RegistryKey<PlacedFeature> ONYX_ORE_PLACED_KEY = registerKey("ore_onyx");


    public static void bootstrap(Registerable<PlacedFeature> context) {
        RegistryEntryLookup<ConfiguredFeature<?, ?>> registryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        RegistryEntry<ConfiguredFeature<?, ?>> tinEntry = registryEntryLookup.getOrThrow(ModConfiguredFeatures.TIN_ORE_KEY);

        PlacedFeatures.register(
                context,
                TIN_ORE_PLACED_KEY,
                tinEntry,
                ModOrePlacement.modifiersWithCount(SimpleOresConfig.NotEditable.tinOreVeinPerChunks, // Vein per chunk
                        HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(108), YOffset.belowTop(236)))
        );

        RegistryEntry<ConfiguredFeature<?, ?>> tinVeinEntry = registryEntryLookup.getOrThrow(ModConfiguredFeatures.TIN_ORE_KEY);

        PlacedFeatures.register(
                context,
                TIN_VEIN_PLACED_KEY,
                tinVeinEntry,
                ModOrePlacement.modifiersWithCount(SimpleOresConfig.NotEditable.tinVeinVeinPerChunks, // Vein per chunk
                        HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(24), YOffset.aboveBottom(64)))
        );

        RegistryEntry<ConfiguredFeature<?, ?>> mythrilEntry = registryEntryLookup.getOrThrow(ModConfiguredFeatures.MYTHRIL_ORE_KEY);

        PlacedFeatures.register(
                context,
                MYTHRIL_ORE_PLACED_KEY,
                mythrilEntry,
                ModOrePlacement.modifiersWithCount(SimpleOresConfig.NotEditable.mythrilVeinPerChunks, // Vein per chunk
                        HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(1), YOffset.aboveBottom(96)))
        );

        RegistryEntry<ConfiguredFeature<?, ?>> adamantiumEntry = registryEntryLookup.getOrThrow(ModConfiguredFeatures.ADAMANTIUM_ORE_KEY);

        PlacedFeatures.register(
                context,
                ADAMANTIUM_ORE_PLACED_KEY,
                adamantiumEntry,
                ModOrePlacement.modifiersWithCount(SimpleOresConfig.NotEditable.adamantiumVeinPerChunks, // Vein per chunk
                        HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(1), YOffset.aboveBottom(48)))
        );

        RegistryEntry<ConfiguredFeature<?, ?>> onyxEntry = registryEntryLookup.getOrThrow(ModConfiguredFeatures.ONYX_ORE_KEY);

        PlacedFeatures.register(
                context,
                ONYX_ORE_PLACED_KEY,
                onyxEntry,
                ModOrePlacement.modifiersWithCount(SimpleOresConfig.NotEditable.onyxVeinPerChunks, // Vein per chunk
                        HeightRangePlacementModifier.uniform(YOffset.aboveBottom(10), YOffset.belowTop(10)))
        );
    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(SimpleOres.MOD_ID, name));
    }
}