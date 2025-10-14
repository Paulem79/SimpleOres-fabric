package net.paulem.simpleores.tooltip;

//? if >=1.20.5
import net.minecraft.component.DataComponentTypes;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a block item that has a tooltip.<br>
 * Basically redirects the tooltip to the corresponding block extending {@link TooltipItem} in the registry.
 */
public class TooltipBlockItem extends BlockItem implements TooltipItem {
    public TooltipBlockItem(TooltipBlock block, Settings settings) {
        super((Block) block, settings);
    }

    @Override
    public void appendClientTooltip(ItemStack stack, TooltipAccept tooltips) {
        // TODO : Maybe item model isn't the best way to get the block?
        //? if >1.21 {
        Identifier value = stack.getItem().getComponents().get(DataComponentTypes.ITEM_MODEL);
        //?} else {
        /*Identifier value = Registries.ITEM.getId(stack.getItem());
        *///?}
        @Nullable Block block = Registries.BLOCK.get(RegistryKey.of(Registries.BLOCK.getKey(), value));

        if(block instanceof TooltipItem tooltipItem) {
            tooltipItem.appendClientTooltip(stack, tooltips);
        }
    }
}
