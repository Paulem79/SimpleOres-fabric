package net.paulem.simpleores.tooltip;

//? if fabric
import net.fabricmc.fabric.api.block.v1.FabricBlock;

/**
 * Interface for blocks that have a tooltip.
 * This method is used in the {@link TooltipBlockItem} class.
 */
public interface TooltipBlock extends TooltipItem
        //? if fabric
        , FabricBlock
{
}
