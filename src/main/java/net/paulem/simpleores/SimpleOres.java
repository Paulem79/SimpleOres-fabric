package net.paulem.simpleores;

import net.legacyfabric.fabric.api.logger.v1.Logger;
import net.fabricmc.loader.api.FabricLoader;
import net.paulem.simpleores.blocks.ModBlocks;
import net.paulem.simpleores.config.Config;
import net.paulem.simpleores.config.loader.ConfigLoader;
import net.paulem.simpleores.furnaces.ModFurnaces;
import net.paulem.simpleores.furnaces.ModFurnacesEntities;
import net.paulem.simpleores.world.ModWorldGeneration;
import net.paulem.simpleores.items.ModItems;
import net.paulem.simpleores.villagers.ModLegacyVillagersTrades;
import net.fabricmc.api.ModInitializer;

public class SimpleOres implements ModInitializer {
	public static final String MOD_ID = "simpleores";
	public static final Logger LOGGER = Logger.get(MOD_ID);
	public static Config CONFIG;

	@Override
	public void onInitialize() {
		LOGGER.info("Simple Ores has been initialized!");

		ConfigLoader<?> configLoader = ConfigLoader.getLoader(FabricLoader.getInstance().isModLoaded("cloth-config2"));
		configLoader.load();
		CONFIG = configLoader.getConfig();

		ModBlocks.init();
		ModItems.init();
        ModFurnaces.init();
        ModFurnacesEntities.init();

		ModWorldGeneration.generateModWorldGen();

		// If trades are enabled
		if(SimpleOres.CONFIG.enableTrades()) {
			ModLegacyVillagersTrades.registerCustomTrades();
		}
	}
}
