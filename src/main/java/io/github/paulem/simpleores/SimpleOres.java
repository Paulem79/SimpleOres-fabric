package io.github.paulem.simpleores;

import de.cech12.bucketlib.api.BucketLibApi;
import de.cech12.bucketlib.api.item.UniversalBucketItem;
import io.github.paulem.simpleores.blocks.ModBlocks;
import io.github.paulem.simpleores.datagen.world.gen.ModWorldGeneration;
import io.github.paulem.simpleores.items.ItemGroups;
import io.github.paulem.simpleores.items.ModItems;
import io.github.paulem.simpleores.villagers.ModCustomTrades;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleOres implements ModInitializer {
	public static final String MOD_ID = "simpleores";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Simple Ores has been initialized!");

		ModBlocks.init();
		ModItems.init();

		// Register custom buckets
		ModItems.registeredItems.forEach((identifier, item) -> {
			if(item instanceof UniversalBucketItem) {
				BucketLibApi.registerBucket(identifier);
			}
		});

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.SIMPLEORES).register(content -> {
			content.addAll(ModBlocks.registeredBlockItems.values()
					.stream()
					.map(blockItem -> new ItemStack(blockItem.asItem()))
					.toList());
			content.addAll(ModItems.registeredItems.values()
					.stream()
					.map(ItemStack::new)
					.toList());
		});

		ModWorldGeneration.generateModWorldGen();

		ModCustomTrades.registerCustomTrades();
	}
}