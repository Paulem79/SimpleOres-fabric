package ovh.paulem.simpleores.items;

import net.minecraft.registry.tag.BlockTags;

//? if >1.21 {
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.item.Item;
import ovh.paulem.simpleores.SimpleOres;
import ovh.paulem.simpleores.config.SimpleOresConfig;
import net.minecraft.block.Block;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.TagKey;
import ovh.paulem.simpleores.tags.ModTags;

public final class ModToolMaterials {
	public static final ToolMaterial COPPER = material(SimpleOres.CONFIG.copperTools, ConventionalItemTags.COPPER_INGOTS);
	public static final ToolMaterial TIN = material(SimpleOres.CONFIG.tinTools, ModTags.Items.REPAIRS_TIN_ITEMS);
	public static final ToolMaterial MYTHRIL = material(SimpleOres.CONFIG.mythrilTools, ModTags.Items.REPAIRS_MYTHRIL_ITEMS);
	public static final ToolMaterial ADAMANTIUM = material(SimpleOres.CONFIG.adamantiumTools, ModTags.Items.REPAIRS_ADAMANTIUM_ITEMS);
	public static final ToolMaterial ONYX = material(SimpleOres.CONFIG.onyxTools, ModTags.Items.REPAIRS_ONYX_ITEMS);

	private static ToolMaterial material(SimpleOresConfig.ToolsProperties toolsProperties, TagKey<Item> repairItems) {
		TagKey<Block> incorrectBlocksForDrops = switch (toolsProperties.miningLevel()){
			case WOOD -> BlockTags.INCORRECT_FOR_WOODEN_TOOL;
			case STONE -> BlockTags.INCORRECT_FOR_STONE_TOOL;
			case IRON -> BlockTags.INCORRECT_FOR_IRON_TOOL;
			case DIAMOND -> BlockTags.INCORRECT_FOR_DIAMOND_TOOL;
			case NETHERITE -> BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
		};

		return material(incorrectBlocksForDrops, toolsProperties.itemDurability(), toolsProperties.miningSpeed(), toolsProperties.attackDamage(), toolsProperties.enchantability(), repairItems);
	}

	private static ToolMaterial material(TagKey<Block> incorrectBlocksForDrops, int durability, float speed, float attackDamageBonus, int enchantability, TagKey<Item> repairItems)
	{
		return new ToolMaterial(incorrectBlocksForDrops, durability, speed, attackDamageBonus, enchantability, repairItems);
	}

	public enum MiningLevels {
		WOOD,
		STONE,
		IRON,
		DIAMOND,
		NETHERITE;
	}
}
//?} else {

/*import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import ovh.paulem.simpleores.SimpleOres;
import ovh.paulem.simpleores.config.SimpleOresConfig;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.block.Block;
import net.minecraft.recipe.Ingredient;
import ovh.paulem.simpleores.tags.ModTags;
import net.minecraft.registry.tag.TagKey;

import java.util.function.Supplier;

public enum ModToolMaterials implements ToolMaterial {
	COPPER(SimpleOres.CONFIG.copperTools, () -> Ingredient.fromTag(ConventionalItemTags.COPPER_INGOTS)),
	TIN(SimpleOres.CONFIG.tinTools, () -> Ingredient.fromTag(ModTags.Items.Conventional.TIN_INGOTS)),
	MYTHRIL(SimpleOres.CONFIG.mythrilTools, () -> Ingredient.fromTag(ModTags.Items.Conventional.MYTHRIL_INGOTS)),
	ADAMANTIUM(SimpleOres.CONFIG.adamantiumTools, () -> Ingredient.fromTag(ModTags.Items.Conventional.ADAMANTIUM_INGOTS)),
	ONYX(SimpleOres.CONFIG.onyxTools, () -> Ingredient.fromTag(ModTags.Items.Conventional.ONYX_GEMS));

	//? if 1.21 {
	/^private final TagKey<Block> inverseTag;
	^///?} else {
	private final int miningLevel;
	//?}
	private final int itemDurability;
	private final float miningSpeed;
	private final float attackDamage;
	private final int enchantability;
	private final Supplier<Ingredient> repairIngredient;

	ModToolMaterials(
	//? if 1.21 {
	/^TagKey<Block> inverseTag
	^///?} else {
	MiningLevels miningLevel
	//?}
	, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Supplier<Ingredient> repairIngredient) {
		//? if 1.21 {
		/^this.inverseTag = inverseTag;
		^///?} else {
		this.miningLevel = miningLevel.getLevel();
		//?}
		this.itemDurability = itemDurability;
		this.miningSpeed = miningSpeed;
		this.attackDamage = attackDamage;
		this.enchantability = enchantability;
		this.repairIngredient = repairIngredient;
	}

	ModToolMaterials(SimpleOresConfig.ToolsProperties toolsProperties, Supplier<Ingredient> repairIngredient) {
		//? if 1.21 {
		/^this.inverseTag = switch (toolsProperties.miningLevel()){
			case WOOD -> BlockTags.INCORRECT_FOR_WOODEN_TOOL;
			case STONE -> BlockTags.INCORRECT_FOR_STONE_TOOL;
			case IRON -> BlockTags.INCORRECT_FOR_IRON_TOOL;
			case DIAMOND -> BlockTags.INCORRECT_FOR_DIAMOND_TOOL;
			case NETHERITE -> BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
			default -> throw new IllegalArgumentException("Unknown mining level: " + toolsProperties.miningLevel());
		};
		^///?} else {
		this.miningLevel = toolsProperties.miningLevel().getLevel();
		//?}
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

	//? if 1.21 {
	/^@Override
	public TagKey<Block> getInverseTag() {
		return this.inverseTag;
	}
	^///?} else {
	@Override
	public int getMiningLevel() {
		return this.miningLevel;
	}
	//?}

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


	public enum MiningLevels {
		HAND(-1),


		WOOD(0),

		STONE(1),


		IRON(2),


		DIAMOND(3),


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
 
*///?}