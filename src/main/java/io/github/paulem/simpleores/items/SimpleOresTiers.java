package io.github.paulem.simpleores.items;

import net.fabricmc.yarn.constants.MiningLevels;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

import java.util.function.Supplier;

/**
 * Holds declarations for tool material tiers.
 * @author Sinhika, Paulem
 *
 */
public enum SimpleOresTiers implements ToolMaterial {
	COPPER(MiningLevels.STONE, 185, 4.0f, 1.0f, 8, () -> Ingredient.ofItems(Items.COPPER_INGOT)),
	TIN(MiningLevels.STONE, 220, 3.5F, 1.0F, 8, () -> Ingredient.ofItems(ModItems.TIN_INGOT)),
	MYTHRIL(MiningLevels.IRON, 800, 8.0F, 3.0F, 12, () -> Ingredient.ofItems(ModItems.MYTHRIL_INGOT)),
	ADAMANTIUM(MiningLevels.IRON, 1150, 14.0F, 3.0F, 3, () -> Ingredient.ofItems(ModItems.ADAMANTIUM_INGOT)),
	ONYX(MiningLevels.NETHERITE, 3280, 10.0F, 5.0F, 15, () -> Ingredient.ofItems(ModItems.ONYX_GEM));

	private final int miningLevel;
	private final int itemDurability;
	private final float miningSpeed;
	private final float attackDamage;
	private final int enchantability;
	private final Supplier<Ingredient> repairIngredient;

	SimpleOresTiers(int miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Supplier<Ingredient> repairIngredient) {
		this.miningLevel = miningLevel;
		this.itemDurability = itemDurability;
		this.miningSpeed = miningSpeed;
		this.attackDamage = attackDamage;
		this.enchantability = enchantability;
		this.repairIngredient = repairIngredient;
	}

	@Override
	public int getDurability() {
		return this.itemDurability;
	}

	@Override
	public float getMiningSpeedMultiplier() {
		return this.miningSpeed;
	}

	@Override
	public float getAttackDamage() {
		return this.attackDamage;
	}

	@Override
	public int getMiningLevel() {
		return this.miningLevel;
	}

	@Override
	public int getEnchantability() {
		return this.enchantability;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return this.repairIngredient.get();
	}

	public Supplier<Ingredient> getRepairIngredientSupplier(){
		return this.repairIngredient;
	}
}