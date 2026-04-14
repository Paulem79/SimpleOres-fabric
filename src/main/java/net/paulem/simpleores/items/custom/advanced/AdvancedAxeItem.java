package net.paulem.simpleores.items.custom.advanced;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ToolMaterial;

public class AdvancedAxeItem extends AxeItem implements AdvancedToolItem {
    public AdvancedAxeItem(ToolMaterial material, float attackDamage, float attackSpeed, Properties settings) {
        //? if >=1.21.5 {
        super(material, attackDamage, attackSpeed,
                settings.axe(material, attackDamage, attackSpeed));
        //?} else if 1.20.6 || 1.21 {
        /*super(material, settings.attributes(AxeItem.createAttributes(material, attackDamage, attackSpeed)));
        *///?} else {
        /*super(material, attackDamage, attackSpeed, settings);
        *///?}
    }
}
