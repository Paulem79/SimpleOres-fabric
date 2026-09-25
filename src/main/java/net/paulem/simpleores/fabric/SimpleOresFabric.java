package net.paulem.simpleores.fabric;

//? if afterDeobf {
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
//?} else {
/*import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
*///?}
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.paulem.simpleores.SimpleOres;
import net.paulem.simpleores.advancements.ModVanillaAdvancements;
import net.paulem.simpleores.items.ItemGroups;
import net.paulem.simpleores.utils.MatchToolShears;
import net.paulem.simpleores.villagers.ModLegacyVillagersTrades;
import net.paulem.simpleores.world.ModWorldGeneration;

public class SimpleOresFabric implements ModInitializer {
	@Override
	public void onInitialize() {
		SimpleOres.loadConfig();
		SimpleOres.registerContent();

		//? if afterDeobf {
		CreativeModeTabEvents.modifyOutputEvent
		//?} else {
		/*ItemGroupEvents.modifyEntriesEvent
		*///?}
				(//? if >1.19.4 {
				SimpleOres.ITEM_GROUP_KEY
				//?} else {
				/*ItemGroups.SIMPLEORES
				*///?}
		).register(SimpleOres::fillItemGroup);

		CommonLifecycleEvents.TAGS_LOADED.register((registries, client) -> MatchToolShears.onTagsLoaded(client));

		ModWorldGeneration.generateModWorldGen();

		ModVanillaAdvancements.init();

		// If trades are enabled
		if(SimpleOres.CONFIG.enableTrades()) {
			ModLegacyVillagersTrades.registerCustomTrades();
		}
	}
}
