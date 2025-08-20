package ovh.paulem.simpleores.items.custom.advanced;

//? if <1.21.5
/*import net.minecraft.item.SwordItem;*/

import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;

/**
 * Placeholder class just for checks and compatibility.
 */
public class AdvancedSwordItem extends
        //? if >=1.21.5 {
        Item
        //?} else {
        /*SwordItem
        *///?}
{
    public AdvancedSwordItem(ToolMaterial material, Settings settings) {
        /*? if >=1.21.5 {*/
        super(settings.sword(material, 3, -2.4F));
        //?} else if 1.21 {
        /*super(material, settings.attributeModifiers(SwordItem.createAttributeModifiers(material, 3, -2.4F)));
        *///?} else {
        /*super(material, 3, -2.4F, settings);
        *///?}
    }
}
