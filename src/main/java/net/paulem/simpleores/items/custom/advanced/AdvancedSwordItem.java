package net.paulem.simpleores.items.custom.advanced;

//?if <1.21.5
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;

/**
 * Placeholder class just for checks and compatibility.
 */
public class AdvancedSwordItem extends
        //? if >=1.21.5 {
        /*Item
        *///?} else {
        SwordItem
        //?}
{
    public AdvancedSwordItem(Tier material, Properties settings) {
        /*? if >=1.21.5 {*/
        /*super(settings.sword(material, 3, -2.4F));
        *///?} else if 1.21 {
        /*super(material, settings.attributes(SwordItem.createAttributes(material, 3, -2.4F)));
        *///?} else {
        super(material, 3, -2.4F, settings);
        //?}
    }
}
