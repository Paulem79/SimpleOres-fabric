package io.github.paulem.simpleores.armors;

import io.github.paulem.simpleores.SimpleOres;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.registry.entry.RegistryEntry;

public class AdvancedArmorItem extends ArmorItem {
    public AdvancedArmorItem(RegistryEntry<ArmorMaterial> material, Type type) {
        super(material, type,
                new Item.Settings().maxDamage(getDurability(material, type)));
    }

    private static int getDurability(RegistryEntry<ArmorMaterial> material, Type type) {
        if (material == ModArmorMaterials.COPPER){
            return type.getMaxDamage(SimpleOres.CONFIG.copperArmorDurability);
        } else if(material == ModArmorMaterials.TIN) {
            return type.getMaxDamage(SimpleOres.CONFIG.tinArmorDurability);
        } else if(material == ModArmorMaterials.MYTHRIL) {
            return type.getMaxDamage(SimpleOres.CONFIG.mythrilArmorDurability);
        } else if(material == ModArmorMaterials.ADAMANTIUM) {
            return type.getMaxDamage(SimpleOres.CONFIG.adamantiumArmorDurability);
        } else if(material == ModArmorMaterials.ONYX) {
            return type.getMaxDamage(SimpleOres.CONFIG.onyxArmorDurability);
        } else {
            return type.getMaxDamage(SimpleOres.CONFIG.copperArmorDurability);
        }
    }
}