package io.github.paulem.simpleores.armors;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;

public class AdvancedArmorItem extends ArmorItem {
    public AdvancedArmorItem(ModArmorMaterials material, Type type) {
        super(material, ArmorItem.Type.HELMET,
                new Item.Settings().maxDamage(material.getDurability(type)));
    }
}
