package net.paulem.simpleores.utils;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.paulem.simpleores.armors.ModArmorMaterials;
import net.paulem.simpleores.items.ModItems;
import net.paulem.simpleores.items.ModToolMaterials;
import org.apache.commons.lang3.tuple.Pair;
import org.jspecify.annotations.Nullable;

public class MaterialUtils {
    private MaterialUtils(){}

    public static Pair<Float, Float> getStrength(//$ armorRegistry
            net.minecraft.world.item.equipment.@org.jspecify.annotations.Nullable ArmorMaterial
                                                 material
    ) {
        if(material == ModArmorMaterials.TIN) {
            return Pair.of(4.0F, 6.0F);
        } else if(material == ModArmorMaterials.MYTHRIL) {
            return Pair.of(7.0F, 8.0F);
        } else if(material == ModArmorMaterials.ADAMANTIUM) {
            return Pair.of(7.0F, 12.0F);
        } else if(material == ModArmorMaterials.ONYX) {
            return Pair.of(20.0F, 100.0F);
        }

        return Pair.of(3.0F, 6.0F);
    }

    @Nullable
    public static String getName(//$ armorRegistry
                                 net.minecraft.world.item.equipment.@org.jspecify.annotations.Nullable ArmorMaterial
                                         material
    ) {
        if(material == ModArmorMaterials.COPPER) {
            return "copper";
        } else if(material == ModArmorMaterials.TIN) {
            return "tin";
        } else if(material == ModArmorMaterials.MYTHRIL) {
            return "mythril";
        } else if(material == ModArmorMaterials.ADAMANTIUM) {
            return "adamantium";
        } else if(material == ModArmorMaterials.ONYX) {
            return "onyx";
        }

        return null;
    }

    @Nullable
    public static Item getMaterialItem(String material) {
        return switch (material) {
            case "copper" -> Items.COPPER_INGOT;
            case "tin" -> ModItems.TIN_INGOT;
            case "mythril" -> ModItems.MYTHRIL_INGOT;
            case "adamantium" -> ModItems.ADAMANTIUM_INGOT;
            case "onyx" -> ModItems.ONYX_GEM;
            default -> null;
        };
    }
    

    public static //$ armorRegistry
    net.minecraft.world.item.equipment.@org.jspecify.annotations.Nullable ArmorMaterial
    toArmor(ToolMaterial material) {
        if(material == ModToolMaterials.TIN) {
            return ModArmorMaterials.TIN;
        } else if(material == ModToolMaterials.MYTHRIL) {
            return ModArmorMaterials.MYTHRIL;
        } else if(material == ModToolMaterials.ADAMANTIUM) {
            return ModArmorMaterials.ADAMANTIUM;
        } else if(material == ModToolMaterials.ONYX) {
            return ModArmorMaterials.ONYX;
        }
        
        return null;
    }
}
