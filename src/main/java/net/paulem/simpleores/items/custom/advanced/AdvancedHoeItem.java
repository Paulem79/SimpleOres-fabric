package net.paulem.simpleores.items.custom.advanced;

import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ToolMaterial;

public class AdvancedHoeItem extends HoeItem implements AdvancedToolItem {
    public AdvancedHoeItem(ToolMaterial material, float attackDamage, float attackSpeed, Properties settings) {
        //? if >=1.21.5 {
        super(material, attackDamage, attackSpeed,
                settings.hoe(material, attackDamage, attackSpeed));
        //?} else if 1.21 {
        /*super(material, settings.attributes(HoeItem.createAttributes(material, attackDamage, attackSpeed)));
        *///?} else if >1.20.4 {
        /*super(material, attackDamage, attackSpeed, settings);
        *///?} else {
        /*super(material, (int) attackDamage, attackSpeed, settings);
        *///?}
    }
}
