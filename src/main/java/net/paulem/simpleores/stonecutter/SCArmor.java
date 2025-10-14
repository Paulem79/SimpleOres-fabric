package net.paulem.simpleores.stonecutter;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.item.Item;
import net.paulem.simpleores.armors.ModArmorMaterials;
import net.paulem.simpleores.items.custom.advanced.AdvancedArmorItem;
//? if <1.21.5
/*import net.minecraft.item.ArmorItem;*/
//? if 1.21 {
/*import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.item.equipment.ArmorMaterial;
*///?}
//? if >1.20.4 && != 1.21 {
import net.minecraft.item.equipment.ArmorMaterial;
//?}
import net.paulem.simpleores.tags.ModTags;
import java.util.EnumMap;

public class SCArmor {
    public static AdvancedArmorItem get(SOArmorMaterial material, ArmorEquipmentType equipmentType, Item.Settings settings) {
        return new AdvancedArmorItem(material.getMaterial(), equipmentType, settings);
    }

    public static class EnumProtection extends EnumMap<ArmorEquipmentType, Integer> {
        public EnumProtection(Class<ArmorEquipmentType> keyType) {
            super(keyType);
        }

        public EnumMap<//$ armorType
                net.minecraft.item.equipment.EquipmentType
                , Integer> convert() {
            EnumMap<//$ armorType
                    net.minecraft.item.equipment.EquipmentType
                    , Integer> map = new EnumMap<>(//$ armorType
                    net.minecraft.item.equipment.EquipmentType
                            .class);
            for (Entry<ArmorEquipmentType, Integer> entry : this.entrySet()) {
                map.put(entry.getKey().getType(), entry.getValue());
            }
            return map;
        }
    }

    public static //$ tagOrIngredient
    net.minecraft.registry.tag.TagKey<net.minecraft.item.Item>
    repairTagOrIngredient(String name) {
        switch (name) {
            case "copper" -> {
                return //? if <=1.21
                        /*() -> Ingredient.fromTag(*/
                        ConventionalItemTags.COPPER_INGOTS
                        //? if <=1.21
                        /*)*/
                        ;
            }
            case "tin" -> {
                return //? if <=1.21
                        /*() -> Ingredient.fromTag(*/
                        ModTags.Items.REPAIRS_TIN_ITEMS
                        //? if <=1.21
                        /*)*/
                        ;
            }
            case "mythril" -> {
                return //? if <=1.21
                        /*() -> Ingredient.fromTag(*/
                        ModTags.Items.REPAIRS_MYTHRIL_ITEMS
                        //? if <=1.21
                        /*)*/
                        ;
            }
            case "adamantium" -> {
                return //? if <=1.21
                        /*() -> Ingredient.fromTag(*/
                        ModTags.Items.REPAIRS_ADAMANTIUM_ITEMS
                        //? if <=1.21
                        /*)*/
                        ;
            }
            case "onyx" -> {
                return //? if <=1.21
                        /*() -> Ingredient.fromTag(*/
                        ModTags.Items.REPAIRS_ONYX_ITEMS
                        //? if <=1.21
                        /*)*/
                        ;
            }
            default -> throw new IllegalArgumentException("Unknown armor material: " + name);
        }
    }

    public enum SOArmorMaterial {
        COPPER(ModArmorMaterials.COPPER),
        TIN(ModArmorMaterials.TIN),
        MYTHRIL(ModArmorMaterials.MYTHRIL),
        ADAMANTIUM(ModArmorMaterials.ADAMANTIUM),
        ONYX(ModArmorMaterials.ONYX);

        private final
        //? if 1.21 {
        /*RegistryEntry<ArmorMaterial>
        *///?} else if >1.20.4 {
        ArmorMaterial
        //?} else if <=1.20.4 {
        /*ModArmorMaterials
        *///?}
                material;

        SOArmorMaterial(
                //? if 1.21 {
                /*RegistryEntry<ArmorMaterial>
                *///?} else if >1.20.4 {
                ArmorMaterial
                        //?} else if <=1.20.4 {
                        /*ModArmorMaterials
                        *///?}
                material) {
            this.material = material;
        }

        public
            //? if 1.21 {
            /*RegistryEntry<ArmorMaterial>
            *///?} else if >1.20.4 {
        ArmorMaterial
        //?} else if <=1.20.4 {
        /*ModArmorMaterials
        *///?}
        getMaterial() {
            return material;
        }
    }

    public enum ArmorEquipmentType {
        HELMET(//$ armorType
                net.minecraft.item.equipment.EquipmentType
                        .HELMET),

        CHESTPLATE(//$ armorType
                net.minecraft.item.equipment.EquipmentType
                        .CHESTPLATE),

        LEGGINGS(//$ armorType
                net.minecraft.item.equipment.EquipmentType
                        .LEGGINGS),

        BOOTS(//$ armorType
                net.minecraft.item.equipment.EquipmentType
                        .BOOTS)

        //? if >1.20.4 {
        ,

        BODY(//$ armorType
                net.minecraft.item.equipment.EquipmentType
                        .BODY)
        //?}
        ;

        private final //$ armorType
        net.minecraft.item.equipment.EquipmentType
        type;

        ArmorEquipmentType(//$ armorType
                           net.minecraft.item.equipment.EquipmentType
                type) {
            this.type = type;
        }

        public //$ armorType
        net.minecraft.item.equipment.EquipmentType
        getType() {
            return type;
        }
    }
}
