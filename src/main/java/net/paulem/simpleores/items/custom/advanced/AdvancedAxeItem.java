package net.paulem.simpleores.items.custom.advanced;

//? if <26.2 {
/*import net.minecraft.world.item.AxeItem;
 *///?} else {
import net.minecraft.world.item.Item;
//?}
import net.minecraft.world.item.ToolMaterial;

public class AdvancedAxeItem extends //$ if >=26.2 'Item' else 'AxeItem'
        Item
        implements AdvancedToolItem {
    public AdvancedAxeItem(ToolMaterial material, float attackDamage, float attackSpeed, Properties settings) {
        //? if >=26.2 {
        super(settings.axe(material, attackDamage, attackSpeed));
        //?} else if >=1.21.5 {
        /*super(material, attackDamage, attackSpeed,
                settings.axe(material, attackDamage, attackSpeed));
        *///?} else if 1.20.6 || 1.21 {
        /*super(material, settings.attributes(AxeItem.createAttributes(material, attackDamage, attackSpeed)));
         *///?} else {
        /*super(material, attackDamage, attackSpeed, settings);
        *///?}
    }
}
