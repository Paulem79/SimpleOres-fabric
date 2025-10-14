package net.paulem.simpleores.items.custom.advanced;

import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;

public class AdvancedShovelItem extends ShovelItem implements AdvancedToolItem {
    public AdvancedShovelItem(ToolMaterial material, Settings settings) {
        //? if >=1.21.5 {
        super(material, 1.5F, -3.0F,
                settings.shovel(material, 1.5F, -3.0F));
        //?} else if 1.21 {
        /*super(material, settings.attributeModifiers(ShovelItem.createAttributeModifiers(material, 1.5F, -3.0F)));
        *///?} else {
        /*super(material, 1.5F, -3.0F, settings);
        *///?}
    }
}
