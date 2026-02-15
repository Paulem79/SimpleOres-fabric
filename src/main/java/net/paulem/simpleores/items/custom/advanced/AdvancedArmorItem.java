package net.paulem.simpleores.items.custom.advanced;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.paulem.simpleores.SimpleOres;
import net.paulem.simpleores.stonecutter.SCArmor;
import net.paulem.simpleores.armors.ModArmorMaterials;
//? if <1.21.5
//import net.minecraft.world.item.ArmorItem;
//? if <=1.21
//import net.minecraft.core.Holder;

public class AdvancedArmorItem extends
        //? if >=1.21.5 {
        Item
        //?} else {
        /*ArmorItem
        *///?}
    {

    public AdvancedArmorItem(//$ armorRegistry
                             net.minecraft.world.item.equipment.ArmorMaterial
                    material,
        SCArmor.ArmorEquipmentType type, Properties settings) {
        /*? if >=1.21.5 {*/
        super(settings.humanoidArmor(material, type.getType()));
        /*?} else {*/
        /*super(material, type.getType(),
                settings.durability(getDurability(material, type)));
         *//*?}*/

        this.scType = type;

        //? if >1.21 {
        this.material = material;
        //?}
    }

    public static int getDurability(//$ armorRegistry
            net.minecraft.world.item.equipment.ArmorMaterial
                    material, SCArmor.ArmorEquipmentType type) {
        //? if >1.20.4 {
        if (material == ModArmorMaterials.COPPER){
            return type.getType().getDurability(SimpleOres.CONFIG.copperArmorDurability());
        } else if(material == ModArmorMaterials.TIN) {
            return type.getType().getDurability(SimpleOres.CONFIG.tinArmorDurability());
        } else if(material == ModArmorMaterials.MYTHRIL) {
            return type.getType().getDurability(SimpleOres.CONFIG.mythrilArmorDurability());
        } else if(material == ModArmorMaterials.ADAMANTIUM) {
            return type.getType().getDurability(SimpleOres.CONFIG.adamantiumArmorDurability());
        } else if(material == ModArmorMaterials.ONYX) {
            return type.getType().getDurability(SimpleOres.CONFIG.onyxArmorDurability());
        } else {
            return type.getType().getDurability(SimpleOres.CONFIG.copperArmorDurability());
        }
        //?} else {
        /*return material.getDurabilityForType(type.getType());
        *///?}
    }

    private final SCArmor.ArmorEquipmentType scType;

    public SCArmor.ArmorEquipmentType getSCType() {
            return scType;
        }

    //? if >1.20.4 && != 1.21 {
    private final ArmorMaterial material;

    public ArmorMaterial getMaterial() {
        return material;
    }
    //?}
}
