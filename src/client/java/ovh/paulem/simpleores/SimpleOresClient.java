package ovh.paulem.simpleores;

//? if >=1.21.6 {
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.render.BlockRenderLayer;
//?} else {
/*import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
*///?}
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
//? if !hasBucketlib
import net.minecraft.client.render.item.tint.TintSourceTypes;
import ovh.paulem.simpleores.blocks.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.block.Block;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.PaneBlock;
import ovh.paulem.simpleores.bucket.tint.handler.BucketLayerTintSource;
import ovh.paulem.simpleores.stonecutter.SCIdentifier;
import ovh.paulem.simpleores.bucket.tint.ChildrenBucketTintSource;
import ovh.paulem.simpleores.tooltip.TooltipItem;

public class SimpleOresClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModModelPredicateProvider.registerModModels();

		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		ModBlocks.registeredBlockItems.forEach((identifier, blockItem) -> {
			Block block = blockItem.getBlock();

			// Make doors non opaque on rendering
			if(block instanceof DoorBlock || block instanceof PaneBlock) {
				//? if >=1.21.6 {
				BlockRenderLayerMap.putBlock(block, BlockRenderLayer.CUTOUT);
				//?} else {
				/*BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
				*///?}
			}
		});

		ItemTooltipCallback.EVENT.register((itemStack, tooltipContext,
											//? if <=1.20.4 {
											/*list
											*///?} else {
											tooltipType,
											list
											//?}
		) -> {
			if(itemStack.getItem() instanceof TooltipItem tooltipItem) {
				tooltipItem.appendClientTooltip(itemStack, new TooltipItem.TooltipAccept(list));
			}
		});

        //? if !hasBucketlib {
        TintSourceTypes.ID_MAPPER.put(SCIdentifier.of(SimpleOres.MOD_ID, "bucketsource"), ChildrenBucketTintSource.CODEC);
        TintSourceTypes.ID_MAPPER.put(SCIdentifier.of(SimpleOres.MOD_ID, "bucketlayersource"), BucketLayerTintSource.CODEC);
        //?}
	}
}