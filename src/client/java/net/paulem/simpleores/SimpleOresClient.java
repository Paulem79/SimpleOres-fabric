package net.paulem.simpleores;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.special.SpecialModelRenderers;
import net.paulem.simpleores.bucket.renderer.CopperBucketSpecialRenderer;
import net.paulem.simpleores.bucket.tint.handler.BucketLayerTintSource;
import net.paulem.simpleores.stonecutter.SCId;
import net.paulem.simpleores.tooltip.TooltipItem;

//? if >=1.21.6 && !afterDeobf {
/*import net.fabricmc.fabric.api.client.rendering.v1.ChunkSectionLayerMap;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
*///?} else !afterDeobf {
/*import net.fabricmc.fabric.api.blockrenderlayer.v1.ChunkSectionLayerMap;
import net.minecraft.client.renderer.RenderType;
*///?}
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
//? if containsBucket && !hasBucketlib
import net.minecraft.client.color.item.ItemTintSources;

//? !afterDeobf {
/*import net.paulem.simpleores.blocks.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.IronBarsBlock;
*///?}

public class SimpleOresClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModModelPredicateProvider.registerModModels();

		//? !afterDeobf {
		/*ModBlocks.registeredBlockItems.forEach((identifier, blockItem) -> {
			Block block = blockItem.getBlock();

			// Make doors non opaque on rendering
			if(block instanceof DoorBlock || block instanceof IronBarsBlock) {
				//? if >=1.21.6 {
				ChunkSectionLayerMap.putBlock(block, ChunkSectionLayer.CUTOUT);
				//?} else {
				/^ChunkSectionLayerMap.INSTANCE.putBlock(block, RenderType.cutout());
				^///?}
			}
		});
		*///?}

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

        //? if containsBucket && !hasBucketlib {
        ItemTintSources.ID_MAPPER.put(SCId.of(SimpleOres.MOD_ID, "bucketlayersource"), BucketLayerTintSource.CODEC);
        //?}

		SpecialModelRenderers.ID_MAPPER.put(
				SCId.of(SimpleOres.MOD_ID, "copper_bucket_special_renderer"),
				CopperBucketSpecialRenderer.Unbaked.MAP_CODEC
		);
	}
}
