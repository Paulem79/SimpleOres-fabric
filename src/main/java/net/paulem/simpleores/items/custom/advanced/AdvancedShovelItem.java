package net.paulem.simpleores.items.custom.advanced;

import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;

public class AdvancedShovelItem extends ShovelItem implements AdvancedToolItem {
    public AdvancedShovelItem(Tier material, Properties settings) {
        //? if >=1.21.5 {
        /*super(material, 1.5F, -3.0F,
                settings.shovel(material, 1.5F, -3.0F));
        *///?} else if 1.21 {
        /*super(material, settings.attributes(ShovelItem.createAttributes(material, 1.5F, -3.0F)));
        *///?} else {
        super(material, 1.5F, -3.0F, settings);
        //?}
    }
}
