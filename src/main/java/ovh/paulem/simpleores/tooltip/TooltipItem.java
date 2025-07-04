package ovh.paulem.simpleores.tooltip;

import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import java.util.List;
import java.util.function.Consumer;

/**
 * Interface for items that have a tooltip.<br>
 * This method is used on the client side. See mod client initializer.
 */
public interface TooltipItem {
    void appendClientTooltip(ItemStack stack, TooltipAccept tooltips);

    record TooltipAccept(
            // TODO: Why
            // if >1.21.6
            //Consumer<Text> tooltips
            // if <=1.21.5
            List<Text> tooltips
    ) {
        public void accept(Text text) {
            // TODO: Why
            // if >1.21.6
            //tooltips.accept(text);
            // if <=1.21.5
            tooltips.add(text);
        }
    }
}
