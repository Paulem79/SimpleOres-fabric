package ovh.paulem.simpleores.stonecutter;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import ovh.paulem.simpleores.tags.ModTags;

public class SCTag {
    public static TagKey<Item> forOre(String material) {
        //? if >1.20.4 {
            return ModTags.Items.Conventional.createTag("ores/" + material);
        //?} else {
            /*return ModTags.Items.Conventional.createTag(material + "_ores");
        *///?}
    }

    public static TagKey<Item> forRawOre(String material) {
        //? if >1.20.4 {
            return ModTags.Items.Conventional.createTag("raw_materials/" + material);
        //?} else {
            /*return ModTags.Items.Conventional.createTag("raw_" + material + "_ores");
        *///?}
    }

    public static TagKey<Item> forIngot(String material) {
        //? if >1.20.4 {
        return ModTags.Items.Conventional.createTag("ingots/" + material);
        //?} else {
        /*return ModTags.Items.Conventional.createTag(material + "_ingots");
        *///?}
    }

    public static TagKey<Item> forGem(String material) {
        //? if >1.20.4 {
        return ModTags.Items.Conventional.createTag("gems/" + material);
        //?} else {
        /*return ModTags.Items.Conventional.createTag(material + "_gems");
        *///?}
    }

    public static TagKey<Item> forRod(String material) {
        //? if >1.20.4 {
        return ModTags.Items.Conventional.createTag("rods/" + material);
        //?} else {
        /*return ModTags.Items.Conventional.createTag(material + "_rods");
        *///?}
    }

    public static TagKey<Item> forNugget(String material) {
        //? if >1.20.4 {
        return ModTags.Items.Conventional.createTag("nuggets/" + material);
        //?} else {
        /*return ModTags.Items.Conventional.createTag(material + "_nuggets");
        *///?}
    }

    public static TagKey<Item> forDust(String material) {
        //? if >1.20.4 {
        return ModTags.Items.Conventional.createTag("dusts/" + material);
        //?} else {
        /*return ModTags.Items.Conventional.createTag(material + "_dusts");
        *///?}
    }

    public static TagKey<Block> forBlocks(String material) {
        //? if >1.20.4 {
        return ModTags.Blocks.Conventional.createTag("storage_blocks/" + material);
        //?} else {
        /*return ModTags.Blocks.Conventional.createTag(material + "_blocks");
        *///?}
    }

    public static TagKey<Block> forOres(String material) {
        //? if >1.20.4 {
        return ModTags.Blocks.Conventional.createTag("ores/" + material);
        //?} else {
        /*return ModTags.Blocks.Conventional.createTag(material + "_ores");
        *///?}
    }
}
