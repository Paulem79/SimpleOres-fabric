package ovh.paulem.simpleores.items.custom.advanced;

import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentType;
import ovh.paulem.simpleores.SimpleOres;
import net.minecraft.item.Item;
import ovh.paulem.simpleores.armors.ModArmorMaterials;

public class AdvancedArmorItem extends Item {
    private final ArmorMaterial material;
    private final EquipmentType type;

    public AdvancedArmorItem(ArmorMaterial material, EquipmentType type, Settings settings) {
        super(settings.armor(material, type));

        this.material = material;
        this.type = type;
    }

    public ArmorMaterial getMaterial() {
        return material;
    }

    public EquipmentType getType() {
        return type;
    }
}