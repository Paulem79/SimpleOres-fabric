package net.paulem.simpleores.items.custom.advanced;

import net.paulem.sclib.item.SCArmorItem;
import net.paulem.sclib.item.SCArmorMaterial;
import net.paulem.sclib.item.SCArmorSlot;

public class AdvancedArmorItem extends SCArmorItem {
    public AdvancedArmorItem(SCArmorMaterial material, SCArmorSlot slot, Properties settings) {
        super(material, slot, settings);
    }

    //? if >1.20.4 && (!= 1.20.6 && != 1.21) {
    public net.minecraft.world.item.equipment.ArmorMaterial getMaterial() {
        return scMaterial().vanilla();
    }
    //?}
}
