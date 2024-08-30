package io.github.paulem.simpleores.items;

import io.github.paulem.simpleores.SimpleOres;
import io.github.paulem.simpleores.config.SimpleOresConfig;
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
	COPPER(SimpleOres.CONFIG.copperTools, () -> Ingredient.ofItems(Items.COPPER_INGOT)),
	TIN(SimpleOres.CONFIG.tinTools, () -> Ingredient.ofItems(ModItems.TIN_INGOT)),
	MYTHRIL(SimpleOres.CONFIG.mythrilTools, () -> Ingredient.ofItems(ModItems.MYTHRIL_INGOT)),
	ADAMANTIUM(SimpleOres.CONFIG.adamantiumTools, () -> Ingredient.ofItems(ModItems.ADAMANTIUM_INGOT)),
	ONYX(SimpleOres.CONFIG.onyxTools, () -> Ingredient.ofItems(ModItems.ONYX_GEM));

	private final int miningLevel;
	private final int itemDurability;
	private final float miningSpeed;
	private final float attackDamage;
	private final int enchantability;
	private final Supplier<Ingredient> repairIngredient;

	SimpleOresTiers(MiningLevels miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Supplier<Ingredient> repairIngredient) {
		this.miningLevel = miningLevel.getLevel();
		this.itemDurability = itemDurability;
		this.miningSpeed = miningSpeed;
		this.attackDamage = attackDamage;
		this.enchantability = enchantability;
		this.repairIngredient = repairIngredient;
	}

	SimpleOresTiers(SimpleOresConfig.ToolsProperties toolsProperties, Supplier<Ingredient> repairIngredient) {
		this.miningLevel = toolsProperties.miningLevel().getLevel();
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

	/**
	 Picked up from {@link net.fabricmc.yarn.constants.MiningLevels}
	 **/
	public enum MiningLevels {
		/**
		 * Blocks with this level do not require a tool to harvest.
		 * <br>This is the default level for blocks and items.
		 */
		HAND(-1),

		/**
		 * Blocks with this level require a Wooden tool or better to harvest.
		 * <br>In addition to Wooden Tools, Golden Tools also use this level.
		 */
		WOOD(0),

		/**
		 * Blocks with this level require a Stone tool or better to harvest.
		 */
		STONE(1),

		/**
		 * Blocks with this level require an Iron tool or better to harvest.
		 */
		IRON(2),

		/**
		 * Blocks with this level require a Diamond tool or better to harvest.
		 */
		DIAMOND(3),

		/**
		 * Blocks with this level require a Netherite tool or better to harvest.
		 */
		NETHERITE(4);

		private final int level;

		MiningLevels(int level) {
			this.level = level;
		}

		public int getLevel() {
			return level;
		}
	}
}