package net.paulem.simpleores.utils;

import net.minecraft.world.item.equipment.ArmorMaterial;
import net.paulem.simpleores.armors.ModArmorMaterials;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;

public class MaterialUtils {
    public static Pair<Float, Float> getStrength(//$ armorRegistry
            ArmorMaterial
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
                                 ArmorMaterial
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
}
