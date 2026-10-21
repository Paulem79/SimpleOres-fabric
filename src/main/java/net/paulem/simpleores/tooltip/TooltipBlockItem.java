package net.paulem.simpleores.tooltip;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

/**
 * Represents a block item that has a tooltip.<br>
 * Basically redirects the tooltip to the corresponding block extending {@link TooltipItem} in the registry.
 */
public class TooltipBlockItem extends BlockItem implements TooltipItem {
    private final TooltipBlock tooltipBlock;

    public TooltipBlockItem(TooltipBlock block, Properties settings) {
        super((Block) block, settings);
        this.tooltipBlock = block;
    }

    @Override
    public void appendClientTooltip(ItemStack stack, TooltipAccept tooltips) {
        tooltipBlock.appendClientTooltip(stack, tooltips);
    }
}
