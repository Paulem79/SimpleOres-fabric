package net.paulem.simpleores.neoforge;

//? if neoforge {
/*import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.TagsUpdatedEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
//? if hasBucketlib {
import de.cech12.bucketlib.api.BucketLibApi;
import de.cech12.bucketlib.api.item.UniversalBucketItem;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.paulem.simpleores.items.ModItems;
//?}
import net.paulem.simpleores.SimpleOres;
import net.paulem.simpleores.utils.MatchToolShears;
import net.paulem.simpleores.villagers.ModLegacyVillagersTrades;

@Mod(SimpleOres.MOD_ID)
public class SimpleOresNeoForge {
	private static boolean contentRegistered = false;

	public SimpleOresNeoForge(IEventBus modBus) {
		SimpleOres.loadConfig();

		// The vanilla registries are only open while RegisterEvent is fired
		modBus.addListener(RegisterEvent.class, event -> {
			if(contentRegistered || !event.getRegistryKey().equals(Registries.BLOCK)) return;

			contentRegistered = true;
			SimpleOres.registerContent();

			//? if <26.1 {
			/^// The trades reference the items, so they can only be generated once these are registered
			if(SimpleOres.CONFIG.enableTrades()) ModLegacyVillagersTrades.registerCustomTrades();
			^///?}
		});

		//? if hasBucketlib {
		modBus.addListener(RegisterCapabilitiesEvent.class, event ->
				ModItems.registeredItems.forEach((identifier, item) -> {
					if(item instanceof UniversalBucketItem) {
						BucketLibApi.registerBucket(event, identifier);
					}
				}));
		//?}

		modBus.addListener(BuildCreativeModeTabContentsEvent.class, event -> {
			if(event.getTabKey().equals(SimpleOres.ITEM_GROUP_KEY)) {
				SimpleOres.fillItemGroup(event);
			}
		});

		// The client flag only matters on the versions without NeoForge
		NeoForge.EVENT_BUS.addListener(TagsUpdatedEvent.class, event -> MatchToolShears.onTagsLoaded(false));

		//? if <26.1 {
		/^if(SimpleOres.CONFIG.enableTrades()) {
			NeoForge.EVENT_BUS.addListener(net.neoforged.neoforge.event.village.VillagerTradesEvent.class, ModLegacyVillagersTrades::onVillagerTrades);
		}
		^///?}
	}
}
*///?}
