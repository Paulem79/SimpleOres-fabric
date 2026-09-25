package net.paulem.simpleores.world;

public class ModWorldGeneration {
    public static void generateModWorldGen() {
        // On NeoForge the ores are added by the biome modifiers of the data pack, see ModNeoForgeBiomeModifierProvider
        //? if fabric
        ModOreGeneration.generateOres();
    }
}
