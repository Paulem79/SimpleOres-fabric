package io.github.paulem.simpleores;

import de.cech12.bucketlib.api.BucketLibApi;
import de.cech12.bucketlib.api.item.UniversalBucketItem;
import io.github.paulem.simpleores.blocks.ModBlocks;
import io.github.paulem.simpleores.config.SimpleOresConfig;
import io.github.paulem.simpleores.datagen.world.gen.ModWorldGeneration;
import io.github.paulem.simpleores.items.ItemGroups;
import io.github.paulem.simpleores.items.ModItems;
import io.github.paulem.simpleores.villagers.ModCustomTrades;
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
            LOGGER.warn("Config validation failed");
        }

        ModBlocks.init();
		ModItems.init();

		// Register custom buckets
		ModItems.registeredItems.forEach((identifier, item) -> {
			if(item instanceof UniversalBucketItem) {
				BucketLibApi.registerBucket(identifier);
			}
		});

		Registry.register(Registries.ITEM_GROUP, Identifier.of(MOD_ID, "itemgroup.global"), ItemGroups.SIMPLEORES);

		ModWorldGeneration.generateModWorldGen();

		// If trades are enabled
		if(SimpleOres.CONFIG.enableTrades) {
			ModCustomTrades.registerCustomTrades();
		}
	}
}