package ovh.paulem.simpleores;

import ovh.paulem.simpleores.blocks.ModBlocks;
import ovh.paulem.simpleores.config.SimpleOresConfig;
import ovh.paulem.simpleores.world.ModWorldGeneration;
import ovh.paulem.simpleores.items.ItemGroups;
import ovh.paulem.simpleores.items.ModItems;
import ovh.paulem.simpleores.villagers.ModCustomTrades;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleOres implements ModInitializer {
	public static final String MOD_ID = "simpleores";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static SimpleOresConfig CONFIG;

	@Override
	public void onInitialize() {
		LOGGER.info("Simple Ores has been initialized!");

		AutoConfig.register(SimpleOresConfig.class, Toml4jConfigSerializer::new);

		CONFIG = AutoConfig.getConfigHolder(SimpleOresConfig.class).getConfig();

		try {
			CONFIG.validatePostLoad();
		} catch (ConfigData.ValidationException e) {
			LOGGER.info("Config validation failed");
		}

		ModBlocks.init();
		ModItems.init();

		Registry.register(Registries.ITEM_GROUP, Identifier.of(MOD_ID, "itemgroup.global"), ItemGroups.SIMPLEORES);

		ModWorldGeneration.generateModWorldGen();

		// If trades are enabled
		if(SimpleOres.CONFIG.enableTrades) {
			ModCustomTrades.registerCustomTrades();
		}
	}
}