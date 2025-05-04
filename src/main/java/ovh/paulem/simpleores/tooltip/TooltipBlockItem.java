package ovh.paulem.simpleores.tooltip;

import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * Represents a block item that has a tooltip.<br>
 * Basically redirects the tooltip to the corresponding block extending {@link TooltipItem} in the registry.
 */
public class TooltipBlockItem extends BlockItem implements TooltipItem {
    public TooltipBlockItem(TooltipBlock block, Settings settings) {
        super((Block) block, settings);
    }

    @Override
    public void appendClientTooltip(ItemStack stack, TooltipContext context, Consumer<Text> tooltips, TooltipType type) {
        // TODO : Maybe item model isn't the best way to get the block?
        Identifier value = stack.getItem().getComponents().get(DataComponentTypes.ITEM_MODEL);
        @Nullable Block block = Registries.BLOCK.get(RegistryKey.of(Registries.BLOCK.getKey(), value));

        if(block instanceof TooltipItem tooltipItem) {
            tooltipItem.appendClientTooltip(stack, context, tooltips, type);
        }
    }
}
