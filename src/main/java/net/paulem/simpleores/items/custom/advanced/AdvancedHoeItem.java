package net.paulem.simpleores.items.custom.advanced;

//? if <26.2 {
/*import net.minecraft.world.item.HoeItem;
 *///?} else {
import net.minecraft.world.item.Item;
//?}
import net.minecraft.world.item.ToolMaterial;

public class AdvancedHoeItem extends //$ if >=26.2 'Item' else 'HoeItem'
        Item
        implements AdvancedToolItem {
    public AdvancedHoeItem(ToolMaterial material, float attackDamage, float attackSpeed, Properties settings) {
        //? if >=26.2 {
        super(settings.hoe(material, attackDamage, attackSpeed));
        //?} else if >=1.21.5 {
        /*super(material, attackDamage, attackSpeed,
                settings.hoe(material, attackDamage, attackSpeed));
        *///?} else if 1.20.6 || 1.21 {
        /*super(material, settings.attributes(HoeItem.createAttributes(material, attackDamage, attackSpeed)));
         *///?} else if >1.20.4 {
        /*super(material, attackDamage, attackSpeed, settings);
         *///?} else {
        /*super(material, (int) attackDamage, attackSpeed, settings);
         *///?}
    }
}