package net.paulem.simpleores.tooltip;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/**
 * Interface for items that have a tooltip.<br>
 * This method is used on the client side. See mod client initializer.
 */
public interface TooltipItem {
    void appendClientTooltip(ItemStack stack, TooltipAccept tooltips);

    record TooltipAccept(
            // if >1.21.6
            //Consumer<Component> tooltips
            // if <=1.21.5
            List<Component> tooltips
    ) {
        public void accept(Component component) {
            // if >1.21.6
            //tooltips.accept(component);
            // if <=1.21.5
            tooltips.add(component);
        }
    }
}
