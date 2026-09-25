package net.paulem.simpleores.neoforge;

//? if neoforge {
/*import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.paulem.simpleores.ModModelPredicateProvider;
import net.paulem.simpleores.SimpleOres;
import net.paulem.simpleores.tooltip.TooltipItem;
//? if containsBucket && !hasBucketlib {
/^import net.neoforged.neoforge.client.event.RegisterItemModelsEvent;
import net.paulem.simpleores.bucket.renderer.CopperBucketItemSpecialRenderer;
import net.paulem.simpleores.bucket.renderer.CopperEmptyBucketItemSpecialRenderer;
import net.paulem.simpleores.stonecutter.SCId;
^///?}

// The config screen of MidnightLib is registered by MidnightLib itself
@Mod(value = SimpleOres.MOD_ID, dist = Dist.CLIENT)
public class SimpleOresNeoForgeClient {
	public SimpleOresNeoForgeClient(IEventBus modBus) {
		// Item properties are not thread safe
		modBus.addListener(FMLClientSetupEvent.class, event -> event.enqueueWork(ModModelPredicateProvider::registerModModels));

		//? if containsBucket && !hasBucketlib {
		/^modBus.addListener(RegisterItemModelsEvent.class, event -> {
			event.register(SCId.of(SimpleOres.MOD_ID, "copper_bucket_renderer"), CopperBucketItemSpecialRenderer.Unbaked.MAP_CODEC);
			event.register(SCId.of(SimpleOres.MOD_ID, "empty_copper_bucket_renderer"), CopperEmptyBucketItemSpecialRenderer.Unbaked.MAP_CODEC);
		});
		^///?}

		NeoForge.EVENT_BUS.addListener(ItemTooltipEvent.class, event -> {
			if(event.getItemStack().getItem() instanceof TooltipItem tooltipItem) {
				tooltipItem.appendClientTooltip(event.getItemStack(), new TooltipItem.TooltipAccept(event.getToolTip()));
			}
		});
	}
}
*///?}
