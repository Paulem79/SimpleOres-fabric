package ovh.paulem.simpleores.items.custom.advanced;

//? if <1.21.5
import net.minecraft.item.PickaxeItem;

import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;

/**
 * Placeholder class just for checks and compatibility.
 */
public class AdvancedPickaxeItem extends
        //? if >=1.21.5 {
        /*Item
        *///?} else {
        PickaxeItem
        //?}
        implements AdvancedToolItem {
    public AdvancedPickaxeItem(ToolMaterial material, Settings settings) {
        //? if >=1.21.5 {
        /*super(settings.pickaxe(material, 1.0F, -2.8F));
        *///?} else if 1.21 {
        /*super(material, settings.attributeModifiers(PickaxeItem.createAttributeModifiers(material, 1.0F, -2.8F)));
        *///?} else if >1.20.4 {
        super(material, 1.0F, -2.8F, settings);
        //?} else {
        /*super(material, 1, -2.8F, settings);
        *///?}
    }
}
