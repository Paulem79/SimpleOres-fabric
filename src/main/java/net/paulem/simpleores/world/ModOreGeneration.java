package net.paulem.simpleores.world;

//? if fabric {
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
//?}

public class ModOreGeneration {
    //? if fabric {
    public static void generateOres() {
        overworld(ModPlacedFeatures.TIN_INTRUSION_PLACED_KEY);
        overworld(ModPlacedFeatures.TIN_PLACER_PLACED_KEY);
        overworld(ModPlacedFeatures.TIN_VEIN_PLACED_KEY);
        overworld(ModPlacedFeatures.MYTHRIL_INTRUSION_PLACED_KEY);
        overworld(ModPlacedFeatures.MYTHRIL_DEPOSIT_PLACED_KEY);
        overworld(ModPlacedFeatures.ADAMANTIUM_INTRUSION_PLACED_KEY);
        overworld(ModPlacedFeatures.ADAMANTIUM_DEPOSIT_PLACED_KEY);

        // Onyx replaces blackstone and basalt in every Nether biome, and netherrack in the basalt deltas only
        BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(),
                GenerationStep.Decoration.UNDERGROUND_DECORATION, ModPlacedFeatures.ONYX_BLACKSTONE_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(),
                GenerationStep.Decoration.UNDERGROUND_DECORATION, ModPlacedFeatures.ONYX_BASALT_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.BASALT_DELTAS),
                GenerationStep.Decoration.UNDERGROUND_DECORATION, ModPlacedFeatures.ONYX_ALL_PLACED_KEY);
    }

    private static void overworld(ResourceKey<PlacedFeature> feature) {
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                GenerationStep.Decoration.UNDERGROUND_ORES, feature);
    }
    //?}
}
