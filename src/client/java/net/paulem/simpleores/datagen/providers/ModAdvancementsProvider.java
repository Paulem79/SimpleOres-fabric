package net.paulem.simpleores.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.paulem.simpleores.SimpleOres;
import net.paulem.simpleores.blocks.ModBlocks;
import net.paulem.simpleores.items.ModItems;
import net.paulem.sclib.SCId;
import net.paulem.sclib.datagen.SCAdvancementProvider;
import net.paulem.sclib.datagen.SCAdvancements;

import java.util.concurrent.CompletableFuture;

public class ModAdvancementsProvider extends SCAdvancementProvider {
    public ModAdvancementsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup, SimpleOres.MOD_ID);
    }

    @Override
    protected void advancements(SCAdvancements advancements) {
        var rootAdvancement = advancements.root(
                //? if !hasCopperTools {
                /*ModItems.COPPER_PICKAXE,
                *///?} else {
                ModItems.MYTHRIL_PICKAXE,
                //?}
                Component.translatable("advancements.welcome"),
                Component.translatable("advancements.welcome"),
                //? if !hasCopperTools {
                /*SCId.vanilla("textures/gui/advancements/backgrounds/stone.png"),
                *///?} else {
                 SCId.vanilla("gui/advancements/backgrounds/stone"),
                //?}
                "root"
        );

        var neitherOrNeitherIron = advancements.task(
                rootAdvancement,
                Blocks.COPPER_ORE,
                Component.translatable("advancements.copper_ach"),
                Component.translatable("advancements.copper_ach.desc"),
                Items.RAW_COPPER
        );

        //? if !hasCopperTools {
            /*var pickaxeCopper = advancements.task(
                neitherOrNeitherIron,
                    ModItems.COPPER_PICKAXE,
                    Component.translatable("advancements.copper_pick_ach"),
                    Component.translatable("advancements.copper_pick_ach.desc")
            );
        *///?}

        //? if containsBucket {
        var copperBucket = advancements.task(
                neitherOrNeitherIron,
                ModItems.COPPER_BUCKET,
                Component.translatable("advancements.copper_bucket_ach"),
                Component.translatable("advancements.copper_bucket_ach.desc")
        );
        //?}

        var tinAdvancement = advancements.task(
                neitherOrNeitherIron,
                ModBlocks.TIN_ORE,
                Component.translatable("advancements.tin_ach"),
                Component.translatable("advancements.tin_ach.desc"),
                ModItems.RAW_TIN
        );

        var tinShearsAdvancement = advancements.task(
                tinAdvancement,
                ModItems.TIN_SHEARS,
                Component.translatable("advancements.tin_shears_ach"),
                Component.translatable("advancements.tin_shears_ach.desc")
        );

        var mythrilAdvancement = advancements.task(
                tinAdvancement,
                ModBlocks.MYTHRIL_ORE,
                Component.translatable("advancements.mythril_ach"),
                Component.translatable("advancements.mythril_ach.desc"),
                ModItems.RAW_MYTHRIL
        );

        var tinChestplate = advancements.task(
                tinAdvancement,
                ModItems.TIN_CHESTPLATE,
                Component.translatable("advancements.tin_chestplate_ach"),
                Component.translatable("advancements.tin_chestplate_ach.desc")
        );

        var adamantiumAdvancement = advancements.task(
                mythrilAdvancement,
                ModBlocks.ADAMANTIUM_ORE,
                Component.translatable("advancements.adamantium_ach"),
                Component.translatable("advancements.adamantium_ach.desc"),
                ModItems.RAW_ADAMANTIUM
        );

        var bowMythril = advancements.task(
                mythrilAdvancement,
                ModItems.MYTHRIL_BOW,
                Component.translatable("advancements.mythril_bow_ach"),
                Component.translatable("advancements.mythril_bow_ach.desc")
        );

        var axeMythril = advancements.task(
                mythrilAdvancement,
                ModItems.MYTHRIL_AXE,
                Component.translatable("advancements.mythril_axe_ach"),
                Component.translatable("advancements.mythril_axe_ach.desc")
        );

        var shearsAdamantium = advancements.task(
                adamantiumAdvancement,
                ModItems.ADAMANTIUM_SHEARS,
                Component.translatable("advancements.adamantium_shears_ach"),
                Component.translatable("advancements.adamantium_shears_ach.desc")
        );

        var leggingsAdamantium = advancements.task(
                adamantiumAdvancement,
                ModItems.ADAMANTIUM_LEGGINGS,
                Component.translatable("advancements.adamantium_legs_ach"),
                Component.translatable("advancements.adamantium_legs_ach.desc")
        );

        var onyxAdvancement = advancements.task(
                adamantiumAdvancement,
                ModBlocks.ONYX_ORE,
                Component.translatable("advancements.onyx_ach"),
                Component.translatable("advancements.onyx_ach.desc"),
                ModItems.ONYX_GEM
        );

        var onyxBow = advancements.task(
                onyxAdvancement,
                ModItems.ONYX_BOW,
                Component.translatable("advancements.onyx_bow_ach"),
                Component.translatable("advancements.onyx_bow_ach.desc")
        );

        var onyxSword = advancements.task(
                onyxAdvancement,
                ModItems.ONYX_SWORD,
                Component.translatable("advancements.onyx_sword_ach"),
                Component.translatable("advancements.onyx_sword_ach.desc")
        );
    }
}
