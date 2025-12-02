package net.paulem.simpleores;

//? <=1.19.4
//import net.minecraft.world.item.CreativeModeTab;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.paulem.simpleores.blocks.ModBlocks;
import net.paulem.simpleores.config.SimpleOresConfig;
//? containsBucket && !hasBucketlib
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.paulem.simpleores.migration.CopperDoorMigration;
import net.paulem.simpleores.stonecutter.SCId;
import net.paulem.simpleores.world.ModWorldGeneration;
import net.paulem.simpleores.items.ItemGroups;
import net.paulem.simpleores.items.ModItems;
import net.paulem.simpleores.villagers.ModCustomTrades;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//? hasCopperTools
//import net.paulem.simpleores.migration.CopperMigration;
//? containsBucket && !hasBucketlib
import net.paulem.simpleores.items.custom.bucket.CustomParentBucketItem;

//? hasBucketlib && >=1.21.3
/*import net.paulem.simpleores.migration.CopperBucketMigration;*/

//? hasBucketlib {
/*import de.cech12.bucketlib.api.BucketLibApi;
import de.cech12.bucketlib.api.item.UniversalBucketItem;
*///?}

public class SimpleOres implements ModInitializer {
	public static final String MOD_ID = "simpleores";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static SimpleOresConfig CONFIG;

	@Override
	public void onInitialize() {
		LOGGER.info("Simple Ores has been initialized!");

		AutoConfig.register(SimpleOresConfig.class, Toml4jConfigSerializer::new);

		CONFIG = new SimpleOresConfig();
		AutoConfig.getConfigHolder(SimpleOresConfig.class).getConfig();

		try {
			CONFIG.validatePostLoad();
		} catch (ConfigData.ValidationException e) {
			LOGGER.info("Config validation failed");
		}

		ModBlocks.init();
		ModItems.init();

		// Register custom buckets
        //? hasBucketlib {
		/*ModItems.registeredItems.forEach((identifier, item) -> {
			if(item instanceof UniversalBucketItem) {
				BucketLibApi.registerBucket(identifier);
			}
		});
        *///?} else if >1.19.4 {
        RegistryEntryAddedCallback.allEntries(BuiltInRegistries.FLUID, fluidReference -> {
            ResourceLocation identifier = fluidReference.key() //$location
                    .location(
            );
            Fluid modFluid = fluidReference.value();

            ModItems.registeredItems.values().forEach(item -> {
                if(item instanceof CustomParentBucketItem bucketItem) {
                    bucketItem.registerFluid(identifier, modFluid);
                }
            });
        });
        //?}


        //? hasCopperTools
        //CopperMigration.migrate();

        //? >1.21
        CopperDoorMigration.migrate();

        //? hasBucketlib && >1.21.3
        /*CopperBucketMigration.migrate();*/

        //? if >1.19.4
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, SCId.of(MOD_ID, "itemgroup.global"), ItemGroups.SIMPLEORES);

        ItemGroupEvents.modifyEntriesEvent(//? if >1.19.4 {
                ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), SCId.of(MOD_ID, "itemgroup.global"))
                //?} else {
                //ItemGroups.SIMPLEORES
                //?}
        ).register(content -> {
            for (BlockItem blockItem : ModBlocks.registeredBlockItems.values()) {
                content.accept(new ItemStack(blockItem.asItem()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            }
            for (Item item : ModItems.registeredItems.values()) {
                content.accept(new ItemStack(item), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            }
		});

		ModWorldGeneration.generateModWorldGen();

		// If trades are enabled
		if(SimpleOres.CONFIG.enableTrades) {
			ModCustomTrades.registerCustomTrades();
		}
	}
}
