package io.github.paulem.simpleores.armors;

import io.github.paulem.simpleores.SimpleOres;
import io.github.paulem.simpleores.items.SimpleOresTiers;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;

import java.util.EnumMap;
import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {
    COPPER("copper", SimpleOres.CONFIG.copperArmorDurability, SimpleOres.CONFIG.copperArmorProtection.getProtectionAmount(),
            SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, SimpleOres.CONFIG.copperArmorProtection.thoughness(), SimpleOres.CONFIG.copperArmorProtection.knockbackProtection(), SimpleOresTiers.COPPER),

    TIN("tin", SimpleOres.CONFIG.tinArmorDurability, SimpleOres.CONFIG.tinArmorProtection.getProtectionAmount(),
            SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, SimpleOres.CONFIG.tinArmorProtection.thoughness(), SimpleOres.CONFIG.tinArmorProtection.knockbackProtection(), SimpleOresTiers.TIN),

    MYTHRIL("mythril", SimpleOres.CONFIG.mythrilArmorDurability, SimpleOres.CONFIG.mythrilArmorProtection.getProtectionAmount(),
            SoundEvents.ITEM_ARMOR_EQUIP_GOLD, SimpleOres.CONFIG.mythrilArmorProtection.thoughness(), SimpleOres.CONFIG.mythrilArmorProtection.knockbackProtection(), SimpleOresTiers.MYTHRIL),

    ADAMANTIUM("adamantium", SimpleOres.CONFIG.adamantiumArmorDurability, SimpleOres.CONFIG.adamantiumArmorProtection.getProtectionAmount(),
            SoundEvents.ITEM_ARMOR_EQUIP_IRON, SimpleOres.CONFIG.adamantiumArmorProtection.thoughness(), SimpleOres.CONFIG.adamantiumArmorProtection.knockbackProtection(), SimpleOresTiers.ADAMANTIUM),

    ONYX("onyx", SimpleOres.CONFIG.onyxArmorDurability, SimpleOres.CONFIG.onyxArmorProtection.getProtectionAmount(),
            SoundEvents.ITEM_ARMOR_EQUIP_TURTLE, SimpleOres.CONFIG.onyxArmorProtection.thoughness(), SimpleOres.CONFIG.onyxArmorProtection.knockbackProtection(), SimpleOresTiers.ONYX);

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

    ModArmorMaterials(String name, int durabilityMultiplier, int[] protectionAmounts, SoundEvent equipSound, float thougness, float knockbackResistance, SimpleOresTiers simpleOresTiers) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantability = simpleOresTiers.getEnchantability();
        this.equipSound = equipSound;
        this.thougness = thougness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = simpleOresTiers::getRepairIngredient;
    }

    @Override
    public int getDurability(ArmorItem.Type type) {
        return BASE_DURABILITY.get(type) * this.durabilityMultiplier;
    }

    @Override
    public int getProtection(ArmorItem.Type type) {
        return protectionAmounts[type.ordinal()];
    }

    @Override
    public int getEnchantability() {
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