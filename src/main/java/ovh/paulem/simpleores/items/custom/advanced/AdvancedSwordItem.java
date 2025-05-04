package ovh.paulem.simpleores.items.custom.advanced;

import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;

/**
 * Placeholder class just for checks and compatibility.
 */
public class AdvancedSwordItem extends Item {
    public AdvancedSwordItem(ToolMaterial material, Item.Settings settings) {
        super(settings.sword(material, 3, -2.4F));
    }
}
