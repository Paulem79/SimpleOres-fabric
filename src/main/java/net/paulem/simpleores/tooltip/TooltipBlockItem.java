package net.paulem.simpleores.tooltip;

//? if >=1.20.5
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a block item that has a tooltip.<br>
 * Basically redirects the tooltip to the corresponding block extending {@link TooltipItem} in the registry.
 */
public class TooltipBlockItem extends BlockItem implements TooltipItem {
    public TooltipBlockItem(TooltipBlock block, Properties settings) {
        super((Block) block, settings);
    }

    @Override
    public void appendClientTooltip(ItemStack stack, TooltipAccept tooltips) {
        // TODO : Maybe item model isn't the best way to get the block?
        //? if >1.21 {
        Identifier value = stack.getItem().components().get(DataComponents.ITEM_MODEL);
        //?} else {
        /*ResourceLocation value = BuiltInRegistries.ITEM.getKey(stack.getItem());
         *///?}
        @Nullable Block block = BuiltInRegistries.BLOCK.//? if >1.21 {
                getValue
                //?} else {
                /*get
                *///?}
                        (ResourceKey.create(BuiltInRegistries.BLOCK.key(), value));

        if(block instanceof TooltipItem tooltipItem) {
            tooltipItem.appendClientTooltip(stack, tooltips);
        }
    }
}
