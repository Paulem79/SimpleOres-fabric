package io.github.paulem.simpleores;

import io.github.paulem.simpleores.blocks.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.PaneBlock;
import net.minecraft.client.render.RenderLayer;

public class SimpleOresClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModModelPredicateProvider.registerModModels();

		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		ModBlocks.registeredBlockItems.forEach((identifier, blockItem) -> {
			Block block = blockItem.getBlock();

			// Make doors non opaque on rendering
			if(block instanceof DoorBlock || block instanceof PaneBlock)
				BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
		});
	}
}