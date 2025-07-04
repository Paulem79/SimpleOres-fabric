package ovh.paulem.simpleores.items.custom.advanced;

import ovh.paulem.simpleores.SimpleOres;
import net.minecraft.item.Item;
//? if <1.21.5
import net.minecraft.item.ArmorItem;

//? if 1.21 {
/*import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.item.equipment.ArmorMaterial;
*///?}
//? if >1.20.4 && != 1.21 {
import net.minecraft.item.equipment.ArmorMaterial;
//?}
import ovh.paulem.simpleores.stonecutter.SCArmor;
import ovh.paulem.simpleores.armors.ModArmorMaterials;

public class AdvancedArmorItem extends
        //? if >=1.21.5 {
        /*Item
        *///?} else {
        ArmorItem
        //?}
    {

    //? if >1.20.4 {
    public AdvancedArmorItem(
            //? if 1.21 {
            /*RegistryEntry<ArmorMaterial>
            *///?} else {
            ArmorMaterial
            //?}
                    material,
        SCArmor.ArmorEquipmentType type, Settings settings) {
    //?} else {
    /*public AdvancedArmorItem(ModArmorMaterials material, SCArmor.ArmorEquipmentType type, Settings settings) {
    *///?}
        /*? if >=1.21.5 {*/
        /*super(settings.armor(material, type.getType()));
        *//*?} else {*/
        super(material, type.getType(),
                settings.maxDamage(getDurability(material, type)));
         /*?}*/

        this.scType = type;

        //? if >1.21 {
        this.material = material;
        //?}
    }

    //? if 1.21 {
    /*public static int getDurability(RegistryEntry<ArmorMaterial> material, SCArmor.ArmorEquipmentType type) {
    *///?} else if >1.20.4 {
    public static int getDurability(ArmorMaterial material, SCArmor.ArmorEquipmentType type) {
    //?} else if <=1.20.4 {
    /*public static int getDurability(ModArmorMaterials material, SCArmor.ArmorEquipmentType type) {
    *///?}
        //? if >1.20.4 {
        if (material == ModArmorMaterials.COPPER){
            return type.getType().getMaxDamage(SimpleOres.CONFIG.copperArmorDurability);
        } else if(material == ModArmorMaterials.TIN) {
            return type.getType().getMaxDamage(SimpleOres.CONFIG.tinArmorDurability);
        } else if(material == ModArmorMaterials.MYTHRIL) {
            return type.getType().getMaxDamage(SimpleOres.CONFIG.mythrilArmorDurability);
        } else if(material == ModArmorMaterials.ADAMANTIUM) {
            return type.getType().getMaxDamage(SimpleOres.CONFIG.adamantiumArmorDurability);
        } else if(material == ModArmorMaterials.ONYX) {
            return type.getType().getMaxDamage(SimpleOres.CONFIG.onyxArmorDurability);
        } else {
            return type.getType().getMaxDamage(SimpleOres.CONFIG.copperArmorDurability);
        }
        //?} else {
        /*return material.getDurability(type.getType());
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