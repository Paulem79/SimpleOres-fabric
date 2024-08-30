package io.github.paulem.simpleores.items;

import com.google.common.base.Suppliers;
import io.github.paulem.simpleores.SimpleOres;
import io.github.paulem.simpleores.config.SimpleOresConfig;
import net.minecraft.block.Block;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;

import java.util.function.Supplier;

/**
 * Holds declarations for tool material tiers.
 * @author Sinhika, Paulem
 *
 */
public enum SimpleOresTiers implements ToolMaterial {
	COPPER(SimpleOres.CONFIG.copperTools, () -> Ingredient.ofItems(Items.COPPER_INGOT)),
	TIN(SimpleOres.CONFIG.tinTools, () -> Ingredient.ofItems(ModItems.TIN_INGOT)),
	MYTHRIL(SimpleOres.CONFIG.mythrilTools, () -> Ingredient.ofItems(ModItems.MYTHRIL_INGOT)),
	ADAMANTIUM(SimpleOres.CONFIG.adamantiumTools, () -> Ingredient.ofItems(ModItems.ADAMANTIUM_INGOT)),
	ONYX(SimpleOres.CONFIG.onyxTools, () -> Ingredient.ofItems(ModItems.ONYX_GEM));

	private final TagKey<Block> inverseTag;
	private final int itemDurability;
	private final float miningSpeed;
	private final float attackDamage;
	private final int enchantability;
	private final Supplier<Ingredient> repairIngredient;

	SimpleOresTiers(
			final TagKey<Block> inverseTag,
			final int itemDurability,
			final float miningSpeed,
			final float attackDamage,
			final int enchantability,
			final Supplier<Ingredient> repairIngredient
	) {
		this.inverseTag = inverseTag;
		this.itemDurability = itemDurability;
		this.miningSpeed = miningSpeed;
		this.attackDamage = attackDamage;
		this.enchantability = enchantability;
		this.repairIngredient = Suppliers.memoize(repairIngredient::get);
	}

	SimpleOresTiers(SimpleOresConfig.ToolsProperties toolsProperties, Supplier<Ingredient> repairIngredient) {
		this.inverseTag = switch (toolsProperties.miningLevel()){
			case WOOD -> BlockTags.INCORRECT_FOR_WOODEN_TOOL;
			case STONE -> BlockTags.INCORRECT_FOR_STONE_TOOL;
			case IRON -> BlockTags.INCORRECT_FOR_IRON_TOOL;
			case DIAMOND -> BlockTags.INCORRECT_FOR_DIAMOND_TOOL;
			case NETHERITE -> BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
		};

		this.itemDurability = toolsProperties.itemDurability();
		this.miningSpeed = toolsProperties.miningSpeed();
		this.attackDamage = toolsProperties.attackDamage();
		this.enchantability = toolsProperties.enchantability();
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
	public TagKey<Block> getInverseTag() {
		return this.inverseTag;
	}

	@Override
	public int getEnchantability() {
		return this.enchantability;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return this.repairIngredient.get();
	}

	public enum MiningLevels {
		WOOD,
		STONE,
		IRON,
		DIAMOND,
		NETHERITE;
	}
}
