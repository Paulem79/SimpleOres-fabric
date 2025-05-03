package ovh.paulem.simpleores.items;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.item.Item;
import ovh.paulem.simpleores.SimpleOres;
import ovh.paulem.simpleores.config.SimpleOresConfig;
import net.minecraft.block.Block;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import ovh.paulem.simpleores.tags.ModTags;

/**
 * Holds declarations for tool material tiers.
 * @author Sinhika, Paulem
 *
 */
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
