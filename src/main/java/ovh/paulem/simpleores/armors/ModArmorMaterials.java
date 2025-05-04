package ovh.paulem.simpleores.armors;

import net.minecraft.item.Item;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import ovh.paulem.simpleores.SimpleOres;
import ovh.paulem.simpleores.config.SimpleOresConfig;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import ovh.paulem.simpleores.tags.ModTags;

import java.util.EnumMap;

public final class ModArmorMaterials
{
    public static final ArmorMaterial COPPER;
    public static final ArmorMaterial TIN;
    public static final ArmorMaterial MYTHRIL;
    public static final ArmorMaterial ADAMANTIUM;
    public static final ArmorMaterial ONYX;

    static {
        COPPER = register("copper", SimpleOres.CONFIG.copperArmorDurability, SimpleOres.CONFIG.copperArmorProtection, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, ConventionalItemTags.COPPER_INGOTS);

        TIN = register("tin", SimpleOres.CONFIG.tinArmorDurability,
                SimpleOres.CONFIG.tinArmorProtection,
                SoundEvents.ITEM_ARMOR_EQUIP_CHAIN,
                ModTags.Items.REPAIRS_TIN_ITEMS);

        MYTHRIL = register("mythril", SimpleOres.CONFIG.mythrilArmorDurability, SimpleOres.CONFIG.mythrilArmorProtection, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, ModTags.Items.REPAIRS_MYTHRIL_ITEMS);

        ADAMANTIUM = register("adamantium", SimpleOres.CONFIG.adamantiumArmorDurability, SimpleOres.CONFIG.adamantiumArmorProtection, SoundEvents.ITEM_ARMOR_EQUIP_IRON, ModTags.Items.REPAIRS_ADAMANTIUM_ITEMS);

        ONYX = register("onyx", SimpleOres.CONFIG.onyxArmorDurability, SimpleOres.CONFIG.onyxArmorProtection, SoundEvents.ITEM_ARMOR_EQUIP_TURTLE, ModTags.Items.REPAIRS_ONYX_ITEMS);
    }

    private static ArmorMaterial register(String name, int durabilityMultiplier, SimpleOresConfig.ArmorProtection armorProtection, RegistryEntry<SoundEvent> equipSound, TagKey<Item> repairIngredient) {
        return register(name, durabilityMultiplier, armorProtection.setProtectionAmount(), armorProtection.enchantability(), equipSound, armorProtection.thoughness(), armorProtection.knockbackProtection(), repairIngredient);
    }

    /**
     * @param typeProtections       The amount of protection per slot
     * @param enchantability        The higher the number, the more likely better enchantments will be applied when using the enchanting table
     * @param toughness             Toughness for netherite armor
     * @param knockbackResistance   The knockback resistance for armor
     * @return Registered armor material
     */
    private static ArmorMaterial register(String name, int durability, EnumMap<EquipmentType, Integer> typeProtections, int enchantability, RegistryEntry<SoundEvent> equipSound, float toughness, float knockbackResistance, TagKey<Item> repairIngredient)
    {
        return new ArmorMaterial(durability, typeProtections, enchantability, equipSound, toughness, knockbackResistance, repairIngredient, getAssetKey(name));
    }

    private static RegistryKey<EquipmentAsset> getAssetKey(String name) {
        RegistryKey<Registry<EquipmentAsset>> equipmentAsset = RegistryKey.ofRegistry(Identifier.ofVanilla("equipment_asset"));
        return RegistryKey.of(equipmentAsset, Identifier.of(SimpleOres.MOD_ID, name));
    }

} // end class
