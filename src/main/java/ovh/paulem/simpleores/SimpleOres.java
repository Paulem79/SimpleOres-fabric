package ovh.paulem.simpleores;

import ovh.paulem.simpleores.blocks.ModBlocks;
import ovh.paulem.simpleores.config.SimpleOresConfig;
import net.minecraft.util.Identifier;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.fluid.Fluid;
import ovh.paulem.simpleores.stonecutter.SCIdentifier;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//? hasCopperTools
//import ovh.paulem.simpleores.migration.CopperMigration;
//? !hasBucketlib
import ovh.paulem.simpleores.items.custom.bucket.CustomParentBucketItem;

//? hasBucketlib && >=1.21.3
/*import ovh.paulem.simpleores.migration.CopperBucketMigration;*/

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
        *///?} else {
        RegistryEntryAddedCallback.allEntries(Registries.FLUID, fluidReference -> {
            Identifier identifier = fluidReference.registryKey().getValue();
            Fluid modFluid = fluidReference.value();

            ModItems.registeredItems.values().forEach(item -> {
                if(item instanceof CustomParentBucketItem bucketItem) {
                    bucketItem.registerFluid(identifier, modFluid);
                }
            });
        });
        //?}


        //? hasCopperTools
        /*CopperMigration.migrate();*/

        //? hasBucketlib && >1.21.3
        /*CopperBucketMigration.migrate();*/

		Registry.register(Registries.ITEM_GROUP, SCIdentifier.of(MOD_ID, "itemgroup.global"), ItemGroups.SIMPLEORES);

		ModWorldGeneration.generateModWorldGen();

		// If trades are enabled
		if(SimpleOres.CONFIG.enableTrades) {
			ModCustomTrades.registerCustomTrades();
		}
	}
}