package ovh.paulem.simpleores.items.custom.advanced;

//? if <1.21.5
/*import net.minecraft.item.ArmorItem;*/
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentType;
import ovh.paulem.simpleores.SimpleOres;
import net.minecraft.item.Item;
import ovh.paulem.simpleores.armors.ModArmorMaterials;

public class AdvancedArmorItem extends
        /*? if >=1.21.5 {*/
        Item
        /*?} else {*/
        /*ArmorItem
         *//*?}*/{
    private final ArmorMaterial material;
    private final EquipmentType type;

    public AdvancedArmorItem(ArmorMaterial material, EquipmentType type, Settings settings) {
        /*? if >=1.21.5 {*/
        super(settings.armor(material, type));
        /*?} else {*/
        /*super(material, type,
                settings.maxDamage(getDurability(material, type)));
         *//*?}*/

        this.material = material;
        this.type = type;
    }

    public static int getDurability(ArmorMaterial material, EquipmentType type) {
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

    public ArmorMaterial getMaterial() {
        return material;
    }

    public EquipmentType getType() {
        return type;
    }
}