package net.paulem.simpleores.stonecutter;

//? if <=1.21 {
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.core.Holder;
//?}
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ArmorMaterial;
import net.paulem.simpleores.armors.ModArmorMaterials;
import net.paulem.simpleores.items.custom.advanced.AdvancedArmorItem;
import net.paulem.simpleores.tags.ModTags;
import java.util.EnumMap;

public class SCArmor {
    public static AdvancedArmorItem get(SOArmorMaterial material, ArmorEquipmentType equipmentType, Item.Properties settings) {
        return new AdvancedArmorItem(material.getMaterial(), equipmentType, settings);
    }

    public static class EnumProtection extends EnumMap<ArmorEquipmentType, Integer> {
        public EnumProtection(Class<ArmorEquipmentType> keyType) {
            super(keyType);
        }

        public EnumMap<
                net.minecraft.world.item.ArmorItem.Type
                , Integer> convert() {
            EnumMap<
                    net.minecraft.world.item.ArmorItem.Type
                    , Integer> map = new EnumMap<>(
                    net.minecraft.world.item.ArmorItem.Type
                            .class);
            for (Entry<ArmorEquipmentType, Integer> entry : this.entrySet()) {
                map.put(entry.getKey().getType(), entry.getValue());
            }
            return map;
        }
    }

    public static 
    java.util.function.Supplier<net.minecraft.world.item.crafting.Ingredient>
    repairTagOrIngredient(String name) {
        switch (name) {
            case "copper" -> {
                return //? if <=1.21
                        () -> Ingredient.of(
                        ConventionalItemTags.COPPER_INGOTS
                        //? if <=1.21
                        )
                        ;
            }
            case "tin" -> {
                return //? if <=1.21
                        () -> Ingredient.of(
                        ModTags.Items.REPAIRS_TIN_ITEMS
                        //? if <=1.21
                        )
                        ;
            }
            case "mythril" -> {
                return //? if <=1.21
                        () -> Ingredient.of(
                        ModTags.Items.REPAIRS_MYTHRIL_ITEMS
                        //? if <=1.21
                        )
                        ;
            }
            case "adamantium" -> {
                return //? if <=1.21
                        () -> Ingredient.of(
                        ModTags.Items.REPAIRS_ADAMANTIUM_ITEMS
                        //? if <=1.21
                        )
                        ;
            }
            case "onyx" -> {
                return //? if <=1.21
                        () -> Ingredient.of(
                        ModTags.Items.REPAIRS_ONYX_ITEMS
                        //? if <=1.21
                        )
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
        /*Holder<ArmorMaterial>
        *///?} else if >1.20.4 {
        /*ArmorMaterial
        *///?} else if <=1.20.4 {
        ModArmorMaterials
        //?}
                material;

        SOArmorMaterial(
                //? if 1.21 {
                /*Holder<ArmorMaterial>
                *///?} else if >1.20.4 {
                /*ArmorMaterial
                        *///?} else if <=1.20.4 {
                        ModArmorMaterials
                        //?}
                material) {
            this.material = material;
        }

        public
            //? if 1.21 {
            /*Holder<ArmorMaterial>
            *///?} else if >1.20.4 {
        /*ArmorMaterial
        *///?} else if <=1.20.4 {
        ModArmorMaterials
        //?}
        getMaterial() {
            return material;
        }
    }

    public enum ArmorEquipmentType {
        HELMET(
                net.minecraft.world.item.ArmorItem.Type
                        .HELMET),

        CHESTPLATE(
                net.minecraft.world.item.ArmorItem.Type
                        .CHESTPLATE),

        LEGGINGS(
                net.minecraft.world.item.ArmorItem.Type
                        .LEGGINGS),

        BOOTS(
                net.minecraft.world.item.ArmorItem.Type
                        .BOOTS)

        //? if >1.20.4 {
        /*,

        BODY(
                net.minecraft.world.item.ArmorItem.Type
                        .BODY)
        *///?}
        ;

        private final 
        net.minecraft.world.item.ArmorItem.Type
        type;

        ArmorEquipmentType(
                           net.minecraft.world.item.ArmorItem.Type
                type) {
            this.type = type;
        }

        public 
        net.minecraft.world.item.ArmorItem.Type
        getType() {
            return type;
        }
    }
}
