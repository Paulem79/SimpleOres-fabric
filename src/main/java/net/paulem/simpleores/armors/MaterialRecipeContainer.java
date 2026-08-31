package net.paulem.simpleores.armors;

import net.paulem.simpleores.items.custom.advanced.AdvancedArmorItem;
import net.paulem.simpleores.items.custom.advanced.AdvancedPickaxeItem;
import net.paulem.simpleores.items.custom.advanced.AdvancedShearsItem;
import net.paulem.simpleores.blocks.custom.MultifunctionPressurePlateBlock;
import net.minecraft.world.item.*;
import net.minecraft.tags.TagKey;
//? if <26.2 {
/*import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ShovelItem;
*///?}
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import org.jspecify.annotations.Nullable;
import net.paulem.simpleores.items.custom.advanced.AdvancedSwordItem;

public record MaterialRecipeContainer(
        @Nullable AdvancedSwordItem sword,
        @Nullable AdvancedPickaxeItem pickaxe,
        @Nullable //$ if >=26.2 'Item' else 'AxeItem'
        Item
        axe,
        @Nullable //$ if >=26.2 'Item' else 'ShovelItem'
        Item
        shovel,
        @Nullable //$ if >=26.2 'Item' else 'HoeItem'
        Item
        hoe,
        @Nullable AdvancedArmorItem helmet,
        @Nullable AdvancedArmorItem chesplate,
        @Nullable AdvancedArmorItem leggings,
        @Nullable AdvancedArmorItem boots,
        @Nullable AdvancedShearsItem shears,
        @Nullable TagKey<Item> ores,
        @Nullable Block block,
        @Nullable Block rawBlock,
        @Nullable TagKey<Item> raw,
        @Nullable ItemLike baseRawItem,
        @Nullable ItemLike nugget,
        @Nullable DoorBlock door,
        @Nullable IronBarsBlock bars,
        @Nullable MultifunctionPressurePlateBlock pressurePlate,
        @Nullable Block cut,
        @Nullable SlabBlock cutSlab,
        @Nullable StairBlock stairs,
        //? containsBucket
        @Nullable Item bucket,
        @Nullable Float smeltXp,
        boolean excludeSmeltCreation
) {
}
