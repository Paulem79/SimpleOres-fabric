package ovh.paulem.simpleores.tooltip;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.function.Consumer;

/**
 * Interface for items that have a tooltip.<br>
 * This method is used on the client side. See mod client initializer.
 */
public interface TooltipItem {
    void appendClientTooltip(ItemStack stack, Item.TooltipContext context, Consumer<Text> tooltips, TooltipType type);
}
