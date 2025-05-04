package ovh.paulem.simpleores.items.custom.advanced;

import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;

/**
 * Placeholder class just for checks and compatibility.
 */
public class AdvancedPickaxeItem extends Item implements AdvancedToolItem {
    public AdvancedPickaxeItem(ToolMaterial material, Settings settings) {
        super(settings.pickaxe(material, 1.0F, -2.8F));
    }
}
