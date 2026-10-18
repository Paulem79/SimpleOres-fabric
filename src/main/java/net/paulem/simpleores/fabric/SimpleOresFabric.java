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
//? if hasFaststats {
import dev.faststats.ErrorTracker;
import dev.faststats.Metrics;
import dev.faststats.fabric.FabricContext;
//?}

public class SimpleOresFabric implements ModInitializer {
	//? if hasFaststats {
	public static final ErrorTracker ERROR_TRACKER = ErrorTracker.contextAware();

	private final FabricContext faststats = new FabricContext.Factory(SimpleOres.MOD_ID, SimpleOres.FASTSTATS_TOKEN)
			.requireConsent()
			.errorTrackerService(ERROR_TRACKER)
			.metrics(Metrics.Factory::create)
			.create();
	//?}

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

		ModChestLoot.register();

		ModVanillaAdvancements.init();

		// If trades are enabled
		if(SimpleOres.CONFIG.enableTrades()) {
			ModLegacyVillagersTrades.registerCustomTrades();
		}
	}
}
