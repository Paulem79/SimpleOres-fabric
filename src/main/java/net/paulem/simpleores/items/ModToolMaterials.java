package net.paulem.simpleores.items;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.paulem.sclib.item.SCToolStats;
import net.paulem.simpleores.SimpleOres;
import net.paulem.simpleores.config.BaseSimpleOresConfig;
import net.paulem.simpleores.tags.ModTags;

public final class ModToolMaterials {
	public static final SCToolStats COPPER = stats(SimpleOres.CONFIG.copperTools(), ModTags.Items.Conventional.COPPER_INGOTS);
	public static final SCToolStats TIN = stats(SimpleOres.CONFIG.tinTools(), repairs(ModTags.Items.REPAIRS_TIN_ITEMS, ModTags.Items.Conventional.TIN_INGOTS));
	public static final SCToolStats MYTHRIL = stats(SimpleOres.CONFIG.mythrilTools(), repairs(ModTags.Items.REPAIRS_MYTHRIL_ITEMS, ModTags.Items.Conventional.MYTHRIL_INGOTS));
	public static final SCToolStats ADAMANTIUM = stats(SimpleOres.CONFIG.adamantiumTools(), repairs(ModTags.Items.REPAIRS_ADAMANTIUM_ITEMS, ModTags.Items.Conventional.ADAMANTIUM_INGOTS));
	public static final SCToolStats ONYX = stats(SimpleOres.CONFIG.onyxTools(), repairs(ModTags.Items.REPAIRS_ONYX_ITEMS, ModTags.Items.Conventional.ONYX_GEMS));

	private static SCToolStats stats(BaseSimpleOresConfig.ToolsProperties p, TagKey<Item> repairItems) {
		return new SCToolStats(p.miningLevel(), p.itemDurability(), p.miningSpeed(), p.attackDamage(), p.enchantability(), repairItems);
	}

	/** Before 1.21.2 the repair ingredient was the conventional ingot tag. */
	private static TagKey<Item> repairs(TagKey<Item> repairsTag, TagKey<Item> conventional) {
		//? if >1.21 {
		return repairsTag;
		//?} else {
		/*return conventional;
		*///?}
	}
}
