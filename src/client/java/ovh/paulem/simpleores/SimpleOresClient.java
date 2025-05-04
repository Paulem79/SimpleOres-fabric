package ovh.paulem.simpleores;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import ovh.paulem.simpleores.blocks.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.PaneBlock;
import net.minecraft.client.render.RenderLayer;
import ovh.paulem.simpleores.tooltip.TooltipItem;

public class SimpleOresClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		ModBlocks.registeredBlockItems.forEach((identifier, blockItem) -> {
			Block block = blockItem.getBlock();

			// Make doors non opaque on rendering
			if(block instanceof DoorBlock || block instanceof PaneBlock)
				BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
		});

		ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipType, list) -> {
			if(itemStack.getItem() instanceof TooltipItem tooltipItem) {
				tooltipItem.appendClientTooltip(itemStack, tooltipContext, list::add, tooltipType);
			}
		});
	}
}