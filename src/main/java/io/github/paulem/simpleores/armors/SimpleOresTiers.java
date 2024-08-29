package io.github.paulem.simpleores.armors;

import com.google.common.base.Suppliers;
import io.github.paulem.simpleores.items.ModItems;
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
	COPPER(BlockTags.INCORRECT_FOR_STONE_TOOL, 185, 4.0f, 1.0f, 8, () -> Ingredient.ofItems(Items.COPPER_INGOT)),
	TIN(BlockTags.INCORRECT_FOR_STONE_TOOL, 220, 3.5F, 1.0F, 8, () -> Ingredient.ofItems(ModItems.TIN_INGOT)),
	MYTHRIL(BlockTags.INCORRECT_FOR_IRON_TOOL, 800, 8.0F, 3.0F, 12, () -> Ingredient.ofItems(ModItems.MYTHRIL_INGOT)),
	ADAMANTIUM(BlockTags.INCORRECT_FOR_IRON_TOOL, 1150, 14.0F, 3.0F, 3, () -> Ingredient.ofItems(ModItems.ADAMANTIUM_INGOT)),
	ONYX(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 3280, 10.0F, 5.0F, 15, () -> Ingredient.ofItems(ModItems.ONYX_GEM));

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
}