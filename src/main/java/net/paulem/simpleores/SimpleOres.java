package net.paulem.simpleores;

import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.paulem.simpleores.blocks.ModBlocks;
import net.paulem.simpleores.config.Config;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.paulem.simpleores.config.loader.ConfigLoader;
import net.paulem.simpleores.furnaces.ModFurnaces;
import net.paulem.simpleores.furnaces.ModFurnacesEntities;
import net.paulem.simpleores.items.ModComponents;
//? if !hasBucketlib && containsBucket {
/*import net.paulem.simpleores.items.custom.bucket.BucketTabVariants;
import net.paulem.simpleores.items.custom.bucket.CustomBucketItem;
*///?}
//? >1.21
import net.paulem.simpleores.migration.CopperDoorMigration;
import net.paulem.simpleores.stonecutter.SCId;
import net.paulem.simpleores.items.ItemGroups;
import net.paulem.simpleores.items.ModItems;
import net.paulem.simpleores.platform.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//? hasCopperTools
import net.paulem.simpleores.migration.CopperMigration;

//? hasBucketlib && fabric {
import de.cech12.bucketlib.api.BucketLibApi;
import de.cech12.bucketlib.api.item.UniversalBucketItem;
//?}

public class SimpleOres {
	public static final String MOD_ID = "simpleores";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static Config CONFIG;

	public static final Identifier ITEM_GROUP_ID = SCId.of(MOD_ID, "itemgroup.global");

	//? if >1.19.4
	public static final ResourceKey<CreativeModeTab> ITEM_GROUP_KEY = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), ITEM_GROUP_ID);

	/**
	 * Loads the config. Has to be called before the content is registered, as the items read it.
	 */
	public static void loadConfig() {
		LOGGER.info("Simple Ores has been initialized!");

		ConfigLoader<? extends Config> configLoader = ConfigLoader.getLoader(Platform.isModLoaded("midnightlib"));
		configLoader.load();
		CONFIG = configLoader.getConfig();
	}

	/**
	 * Registers the blocks, items, furnaces, components and the creative tab.
	 * Each loader calls it at the moment its registries are open.
	 */
	public static void registerContent() {
        //? >1.20.4
        ModComponents.init();
		ModBlocks.init();
		ModItems.init();
        ModFurnaces.init();
        ModFurnacesEntities.init();

		// Register custom buckets, NeoForge does it when the capabilities are registered
        //? hasBucketlib && fabric {
		ModItems.registeredItems.forEach((identifier, item) -> {
			if(item instanceof UniversalBucketItem) {
				BucketLibApi.registerBucket(identifier);
			}
		});
        //?}


        //? hasCopperTools
        CopperMigration.migrate();

        //? >1.21
        CopperDoorMigration.migrate();

        //? if >1.19.4
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ITEM_GROUP_ID, ItemGroups.SIMPLEORES);
	}

	/**
	 * Fills the creative tab, called by the loader when the tab contents are built.
	 */
	public static void fillItemGroup(CreativeModeTab.Output content) {
		for (BlockItem blockItem : ModBlocks.registeredBlockItems.values()) {
			content.accept(new ItemStack(blockItem.asItem()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
		}
		for (Item item : ModItems.registeredItems.values()) {
			content.accept(new ItemStack(item), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

			//? if !hasBucketlib && containsBucket {
			/*// Every filled variant of the bucket goes right after the empty one
			if(item instanceof CustomBucketItem customBucket) {
				for (ItemStack variant : BucketTabVariants.build(customBucket)) {
					content.accept(variant, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
				}
			}
			*///?}
		}
	}
}
