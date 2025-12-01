package net.paulem.simpleores.armors;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.equipment.ArmorMaterial;
//? if >1.21.3
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.paulem.simpleores.SimpleOres;
import net.paulem.simpleores.config.SimpleOresConfig;
import net.paulem.simpleores.stonecutter.SCArmor;
import net.paulem.simpleores.stonecutter.SCId;
//? if <=1.20.4 {
/*import java.util.EnumMap;
import net.paulem.simpleores.items.ModToolMaterials;
import net.minecraft.Util;
import net.minecraft.world.item.crafting.Ingredient;
import java.util.function.Supplier;*/
//?}
//? if 1.21 {
/*import java.util.List;
import net.minecraft.core.registries.BuiltInRegistries;
 */
//?}

//? if >1.20.4 {
public final class ModArmorMaterials
{
    public static final //$ armorRegistry
    net.minecraft.world.item.equipment.ArmorMaterial
            COPPER;
    public static final //$ armorRegistry
    net.minecraft.world.item.equipment.ArmorMaterial
            TIN;
    public static final //$ armorRegistry
    net.minecraft.world.item.equipment.ArmorMaterial
            MYTHRIL;
    public static final //$ armorRegistry
    net.minecraft.world.item.equipment.ArmorMaterial
            ADAMANTIUM;
    public static final //$ armorRegistry
    net.minecraft.world.item.equipment.ArmorMaterial
            ONYX;

    static {
        COPPER = register("copper", SimpleOres.CONFIG.copperArmorDurability, SimpleOres.CONFIG.copperArmorProtection, SoundEvents.ARMOR_EQUIP_CHAIN);

        TIN = register("tin", SimpleOres.CONFIG.tinArmorDurability,
                SimpleOres.CONFIG.tinArmorProtection,
                SoundEvents.ARMOR_EQUIP_CHAIN);

        MYTHRIL = register("mythril", SimpleOres.CONFIG.mythrilArmorDurability, SimpleOres.CONFIG.mythrilArmorProtection, SoundEvents.ARMOR_EQUIP_GOLD);

        ADAMANTIUM = register("adamantium", SimpleOres.CONFIG.adamantiumArmorDurability, SimpleOres.CONFIG.adamantiumArmorProtection, SoundEvents.ARMOR_EQUIP_IRON);

        ONYX = register("onyx", SimpleOres.CONFIG.onyxArmorDurability, SimpleOres.CONFIG.onyxArmorProtection, SoundEvents.ARMOR_EQUIP_TURTLE);
    }

    private static //$ armorRegistry
    net.minecraft.world.item.equipment.ArmorMaterial
    register(String name, int durabilityMultiplier, SimpleOresConfig.ArmorProtection armorProtection, Holder<SoundEvent> equipSound) {
        return register(name, durabilityMultiplier, armorProtection.setProtectionAmount(), armorProtection.enchantability(), equipSound, armorProtection.thoughness(), armorProtection.knockbackProtection(), SCArmor.repairTagOrIngredient(name));
    }

    /**
     * @param typeProtections       The amount of protection per slot
     * @param enchantability        The higher the number, the more likely better enchantments will be applied when using the enchanting table
     * @param toughness             Toughness for netherite armor
     * @param knockbackResistance   The knockback resistance for armor
     * @return Registered armor material
     */
    private static //$ armorRegistry
    net.minecraft.world.item.equipment.ArmorMaterial
    register(String name, int durability, SCArmor.EnumProtection typeProtections, int enchantability, Holder<SoundEvent> equipSound, float toughness, float knockbackResistance,
             //$ tagOrIngredient
             net.minecraft.tags.TagKey<net.minecraft.world.item.Item>
             repairIngredient)
    {
        ResourceLocation loc = SCId.of(SimpleOres.MOD_ID, name);

        //? if >1.21.3 {
        return new ArmorMaterial(durability, typeProtections.convert(), enchantability, equipSound, toughness, knockbackResistance, repairIngredient, getAssetKey(name));
        //?} else if 1.21 {
        
        /*List<ArmorMaterial.Layer> layers = List.of(new ArmorMaterial.Layer(loc));

        return Registry.registerForHolder(BuiltInRegistries.ARMOR_MATERIAL, loc, new ArmorMaterial(typeProtections.convert(), enchantability, equipSound, repairIngredient, layers, toughness, knockbackResistance/10));
         
        *///?} else if <=1.21.3 {
        
        /*return new ArmorMaterial(durability, typeProtections.convert(), enchantability, equipSound, toughness, knockbackResistance, repairIngredient, loc);
         *///?}
    }

    //? if >1.21.3 {
    private static ResourceKey<EquipmentAsset> getAssetKey(String name) {
        ResourceKey<Registry<EquipmentAsset>> equipmentAsset = ResourceKey.createRegistryKey(SCId.ofVanilla("equipment_asset"));
        return ResourceKey.create(equipmentAsset, SCId.of(SimpleOres.MOD_ID, name));
    }
    //?}

}
//?} else if 1.20.4 {
/*import net.minecraft.world.item.ArmorItem;
public enum ModArmorMaterials implements ArmorMaterial {
    COPPER("copper", SimpleOres.CONFIG.copperArmorDurability, SimpleOres.CONFIG.copperArmorProtection,
            SoundEvents.ARMOR_EQUIP_CHAIN, ModToolMaterials.COPPER),

    TIN("tin", SimpleOres.CONFIG.tinArmorDurability, SimpleOres.CONFIG.tinArmorProtection,
            SoundEvents.ARMOR_EQUIP_CHAIN, ModToolMaterials.TIN),

    MYTHRIL("mythril", SimpleOres.CONFIG.mythrilArmorDurability, SimpleOres.CONFIG.mythrilArmorProtection,
            SoundEvents.ARMOR_EQUIP_GOLD, ModToolMaterials.MYTHRIL),

    ADAMANTIUM("adamantium", SimpleOres.CONFIG.adamantiumArmorDurability, SimpleOres.CONFIG.adamantiumArmorProtection,
            SoundEvents.ARMOR_EQUIP_IRON, ModToolMaterials.ADAMANTIUM),

    ONYX("onyx", SimpleOres.CONFIG.onyxArmorDurability, SimpleOres.CONFIG.onyxArmorProtection,
            SoundEvents.ARMOR_EQUIP_TURTLE, ModToolMaterials.ONYX);

    private static final EnumMap<ArmorItem.Type, Integer> BASE_DURABILITY = Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 13);
        map.put(ArmorItem.Type.LEGGINGS, 15);
        map.put(ArmorItem.Type.CHESTPLATE, 16);
        map.put(ArmorItem.Type.HELMET, 11);
    });

    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final float thougness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    ModArmorMaterials(String name, int durabilityMultiplier, int[] protectionAmounts, SoundEvent equipSound, float thougness, float knockbackResistance, ModToolMaterials simpleOresTiers) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantability = simpleOresTiers.getEnchantmentValue();
        this.equipSound = equipSound;
        this.thougness = thougness;
        this.knockbackResistance = knockbackResistance/10;
        this.repairIngredient = simpleOresTiers.getRepairIngredientSupplier();
    }

    ModArmorMaterials(String name, int durabilityMultiplier, SimpleOresConfig.ArmorProtection armorProtection, SoundEvent equipSound, ModToolMaterials simpleOresTiers) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = armorProtection.getProtectionAmount();
        this.enchantability = armorProtection.enchantability();
        this.equipSound = equipSound;
        this.thougness = armorProtection.thoughness();
        this.knockbackResistance = armorProtection.knockbackProtection()/10;
        this.repairIngredient = simpleOresTiers.getRepairIngredientSupplier();
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return BASE_DURABILITY.get(type) * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return protectionAmounts[type.ordinal()];
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return SimpleOres.MOD_ID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.thougness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
*///?} else {

/*import net.paulem.simpleores.SimpleOres;
import net.paulem.simpleores.config.SimpleOresConfig;
import net.paulem.simpleores.items.ModToolMaterials;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.Util;

import java.util.EnumMap;
import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {
    COPPER("copper", SimpleOres.CONFIG.copperArmorDurability, SimpleOres.CONFIG.copperArmorProtection,
            SoundEvents.ARMOR_EQUIP_CHAIN, ModToolMaterials.COPPER),

    TIN("tin", SimpleOres.CONFIG.tinArmorDurability, SimpleOres.CONFIG.tinArmorProtection,
            SoundEvents.ARMOR_EQUIP_CHAIN, ModToolMaterials.TIN),

    MYTHRIL("mythril", SimpleOres.CONFIG.mythrilArmorDurability, SimpleOres.CONFIG.mythrilArmorProtection,
            SoundEvents.ARMOR_EQUIP_GOLD, ModToolMaterials.MYTHRIL),

    ADAMANTIUM("adamantium", SimpleOres.CONFIG.adamantiumArmorDurability, SimpleOres.CONFIG.adamantiumArmorProtection,
            SoundEvents.ARMOR_EQUIP_IRON, ModToolMaterials.ADAMANTIUM),

    ONYX("onyx", SimpleOres.CONFIG.onyxArmorDurability, SimpleOres.CONFIG.onyxArmorProtection,
            SoundEvents.ARMOR_EQUIP_TURTLE, ModToolMaterials.ONYX);

    private static final EnumMap<ArmorItem.Type, Integer> BASE_DURABILITY = Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 13);
        map.put(ArmorItem.Type.LEGGINGS, 15);
        map.put(ArmorItem.Type.CHESTPLATE, 16);
        map.put(ArmorItem.Type.HELMET, 11);
    });

    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final float thougness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    ModArmorMaterials(String name, int durabilityMultiplier, int[] protectionAmounts, SoundEvent equipSound, float thougness, float knockbackResistance, ModToolMaterials simpleOresTiers) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantability = simpleOresTiers.getEnchantmentValue();
        this.equipSound = equipSound;
        this.thougness = thougness;
        this.knockbackResistance = knockbackResistance/10;
        this.repairIngredient = simpleOresTiers.getRepairIngredientSupplier();
    }

    ModArmorMaterials(String name, int durabilityMultiplier, SimpleOresConfig.ArmorProtection armorProtection, SoundEvent equipSound, ModToolMaterials simpleOresTiers) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = armorProtection.getProtectionAmount();
        this.enchantability = armorProtection.enchantability();
        this.equipSound = equipSound;
        this.thougness = armorProtection.thoughness();
        this.knockbackResistance = armorProtection.knockbackProtection()/10;
        this.repairIngredient = simpleOresTiers.getRepairIngredientSupplier();
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return BASE_DURABILITY.get(type) * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return protectionAmounts[type.ordinal()];
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return SimpleOres.MOD_ID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.thougness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
 
*///?}
