package io.github.paulem.simpleores.armors;

import io.github.paulem.simpleores.SimpleOres;
import mod.alexndr.simplecorelib.api.content.content.SimpleOresTiers;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {
    COPPER("copper", new int[] { 88, 128, 120, 104 }, new int[] { 1, 2, 3, 2 },
            SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 0, 0, SimpleOresTiers.COPPER),

    TIN("tin", new int[] { 99, 144, 135, 117 }, new int[] { 1, 2, 3, 2 },
            SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 0, 0, SimpleOresTiers.TIN),

    MYTHRIL("mythril", new int[] { 242, 352, 330, 286 }, new int[] { 3, 4, 5, 3 },
            SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0, 0, SimpleOresTiers.MYTHRIL),

    ADAMANTIUM("adamantium", new int[] { 308, 448, 420, 364 }, new int[] { 2, 6, 8, 3 },
            SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1, 0, SimpleOresTiers.ADAMANTIUM),

    ONYX("onyx", new int[] { 495, 720, 675, 585 }, new int[] { 5, 6, 8, 5 },
            SoundEvents.ITEM_ARMOR_EQUIP_TURTLE, 0, 0, SimpleOresTiers.ONYX);

    private final String name;
    private final int[] protectionAmounts;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final float thougness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;
    private final int[] durability;

    ModArmorMaterials(String name, int[] durability, int[] protectionAmounts, SoundEvent equipSound, float thougness, float knockbackResistance, SimpleOresTiers simpleOresTiers) {
        this.name = name;
        this.durability = durability;
        this.protectionAmounts = protectionAmounts;
        this.enchantability = simpleOresTiers.getEnchantability();
        this.equipSound = equipSound;
        this.thougness = thougness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = simpleOresTiers::getRepairIngredient;
    }

    @Override
    public int getDurability(ArmorItem.Type type) {
        return durability[type.ordinal()];
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