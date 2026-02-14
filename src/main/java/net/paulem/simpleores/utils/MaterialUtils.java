package net.paulem.simpleores.utils;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.paulem.simpleores.armors.ModArmorMaterials;
import net.paulem.simpleores.items.ModItems;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;

public class MaterialUtils {
    private MaterialUtils(){}

    public static Pair<Float, Float> getStrength(//$ armorRegistry
            net.minecraft.world.item.equipment.ArmorMaterial
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
                                 net.minecraft.world.item.equipment.ArmorMaterial
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
            case "copper": yield Items.COPPER_INGOT;
            case "tin": yield ModItems.TIN_INGOT;
            case "mythril": yield ModItems.MYTHRIL_INGOT;
            case "adamantium": yield ModItems.ADAMANTIUM_INGOT;
            case "onyx": yield ModItems.ONYX_GEM;
            default: yield null;
        };
    }
}
