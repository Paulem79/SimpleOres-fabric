package net.paulem.simpleores.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.paulem.simpleores.SimpleOres;
import net.paulem.simpleores.blocks.ModBlocks;
import net.paulem.simpleores.items.ModItems;
import org.jetbrains.annotations.Nullable;
import net.paulem.simpleores.stonecutter.SCId;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AdvancementsProvider extends FabricAdvancementProvider {
    public AdvancementsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output //? if >1.20.4
                , registryLookup
        );
    }

    @Override
    public void generateAdvancement(//? if >1.20.4
            HolderLookup.Provider registryLookup,
                                    Consumer<//$ advancementEntry
                                            net.minecraft.advancements.AdvancementHolder
                                            > consumer) {
        //$ advancementEntry
        net.minecraft.advancements.AdvancementHolder
                rootAdvancement = buildAdvancement(
                null,
                consumer,
                //? if !hasCopperTools {
                ModItems.COPPER_PICKAXE,
                //?} else {
                /*ModItems.MYTHRIL_PICKAXE,
                *///?}
                Component.translatable("advancements.welcome"),
                Component.translatable("advancements.welcome"),
                //? if !hasCopperTools {
                SCId.ofVanilla("textures/gui/advancements/backgrounds/stone.png"),
                //?} else {
                 /*SCId.ofVanilla("gui/advancements/backgrounds/stone"),
                *///?}
                AdvancementType.TASK,
                false,
                false,
                false,
                null,
                "root"
        );

        //$ advancementEntry
        net.minecraft.advancements.AdvancementHolder
        neitherOrNeitherIron = buildAdvancement(
                rootAdvancement,
                consumer,
                Blocks.COPPER_ORE,
                Component.translatable("advancements.copper_ach"),
                Component.translatable("advancements.copper_ach.desc"),
                Items.RAW_COPPER
        );

        //? if !hasCopperTools {
            //$ advancementEntry
            net.minecraft.advancements.AdvancementHolder
             pickaxeCopper = buildAdvancement(
                    neitherOrNeitherIron,
                    consumer,
                    ModItems.COPPER_PICKAXE,
                    Component.translatable("advancements.copper_pick_ach"),
                    Component.translatable("advancements.copper_pick_ach.desc")
            );
        //?}

        //$ advancementEntry
        net.minecraft.advancements.AdvancementHolder
                copperBucket = buildAdvancement(
                neitherOrNeitherIron,
                consumer,
                ModItems.COPPER_BUCKET,
                Component.translatable("advancements.copper_bucket_ach"),
                Component.translatable("advancements.copper_bucket_ach.desc")
        );

        //$ advancementEntry
        net.minecraft.advancements.AdvancementHolder
         tinAdvancement = buildAdvancement(
                neitherOrNeitherIron,
                consumer,
                ModBlocks.TIN_ORE,
                Component.translatable("advancements.tin_ach"),
                Component.translatable("advancements.tin_ach.desc"),
                ModItems.RAW_TIN
        );

        //$ advancementEntry
        net.minecraft.advancements.AdvancementHolder
         tinShearsAdvancement = buildAdvancement(
                tinAdvancement,
                consumer,
                ModItems.TIN_SHEARS,
                Component.translatable("advancements.tin_shears_ach"),
                Component.translatable("advancements.tin_shears_ach.desc")
        );

        //$ advancementEntry
        net.minecraft.advancements.AdvancementHolder
         mythrilAdvancement = buildAdvancement(
                tinAdvancement,
                consumer,
                ModBlocks.MYTHRIL_ORE,
                Component.translatable("advancements.mythril_ach"),
                Component.translatable("advancements.mythril_ach.desc"),
                ModItems.RAW_MYTHRIL
        );

        //$ advancementEntry
        net.minecraft.advancements.AdvancementHolder
         tinChestplate = buildAdvancement(
                tinAdvancement,
                consumer,
                ModItems.TIN_CHESTPLATE,
                Component.translatable("advancements.tin_chestplate_ach"),
                Component.translatable("advancements.tin_chestplate_ach.desc")
        );

        //$ advancementEntry
        net.minecraft.advancements.AdvancementHolder
         adamantiumAdvancement = buildAdvancement(
                mythrilAdvancement,
                consumer,
                ModBlocks.ADAMANTIUM_ORE,
                Component.translatable("advancements.adamantium_ach"),
                Component.translatable("advancements.adamantium_ach.desc"),
                ModItems.RAW_ADAMANTIUM
        );

        //$ advancementEntry
        net.minecraft.advancements.AdvancementHolder
         bowMythril = buildAdvancement(
                mythrilAdvancement,
                consumer,
                ModItems.MYTHRIL_BOW,
                Component.translatable("advancements.mythril_bow_ach"),
                Component.translatable("advancements.mythril_bow_ach.desc")
        );

        //$ advancementEntry
        net.minecraft.advancements.AdvancementHolder
         axeMythril = buildAdvancement(
                mythrilAdvancement,
                consumer,
                ModItems.MYTHRIL_AXE,
                Component.translatable("advancements.mythril_axe_ach"),
                Component.translatable("advancements.mythril_axe_ach.desc")
        );

        //$ advancementEntry
        net.minecraft.advancements.AdvancementHolder
         shearsAdamantium = buildAdvancement(
                adamantiumAdvancement,
                consumer,
                ModItems.ADAMANTIUM_SHEARS,
                Component.translatable("advancements.adamantium_shears_ach"),
                Component.translatable("advancements.adamantium_shears_ach.desc")
        );

        //$ advancementEntry
        net.minecraft.advancements.AdvancementHolder
         leggingsAdamantium = buildAdvancement(
                adamantiumAdvancement,
                consumer,
                ModItems.ADAMANTIUM_LEGGINGS,
                Component.translatable("advancements.adamantium_legs_ach"),
                Component.translatable("advancements.adamantium_legs_ach.desc")
        );

        //$ advancementEntry
        net.minecraft.advancements.AdvancementHolder
         onyxAdvancement = buildAdvancement(
                adamantiumAdvancement,
                consumer,
                ModBlocks.ONYX_ORE,
                Component.translatable("advancements.onyx_ach"),
                Component.translatable("advancements.onyx_ach.desc"),
                ModItems.ONYX_GEM
        );

        //$ advancementEntry
        net.minecraft.advancements.AdvancementHolder
         onyxBow = buildAdvancement(
                onyxAdvancement,
                consumer,
                ModItems.ONYX_BOW,
                Component.translatable("advancements.onyx_bow_ach"),
                Component.translatable("advancements.onyx_bow_ach.desc")
        );

        //$ advancementEntry
        net.minecraft.advancements.AdvancementHolder
         onyxSword = buildAdvancement(
                onyxAdvancement,
                consumer,
                ModItems.ONYX_SWORD,
                Component.translatable("advancements.onyx_sword_ach"),
                Component.translatable("advancements.onyx_sword_ach.desc")
        );
    }


    public static //$ advancementEntry
        net.minecraft.advancements.AdvancementHolder
         buildAdvancement(@Nullable //$ advancementEntry
        net.minecraft.advancements.AdvancementHolder
         parent, Consumer<//$ advancementEntry
        net.minecraft.advancements.AdvancementHolder
        > consumer, @Nullable Item requiredItem,
                                                    Component title,
                                                    Component description) {
        return buildAdvancement(parent, consumer, requiredItem, title, description, null, AdvancementType.TASK, true, true, false, requiredItem, BuiltInRegistries.ITEM.getKey(requiredItem).getPath());
    }

    public static //$ advancementEntry
        net.minecraft.advancements.AdvancementHolder
         buildAdvancement(@Nullable //$ advancementEntry
        net.minecraft.advancements.AdvancementHolder
         parent, Consumer<//$ advancementEntry
        net.minecraft.advancements.AdvancementHolder
        > consumer, ItemLike icon,
                                                    Component title,
                                                    Component description,
                                                    @Nullable Item requiredItem) {
        return buildAdvancement(parent, consumer, icon, title, description, null, AdvancementType.TASK, true, true, false, requiredItem, BuiltInRegistries.ITEM.getKey(requiredItem).getPath());
    }

    public static //$ advancementEntry
        net.minecraft.advancements.AdvancementHolder
         buildAdvancement(@Nullable //$ advancementEntry
        net.minecraft.advancements.AdvancementHolder
         parent, Consumer<//$ advancementEntry
        net.minecraft.advancements.AdvancementHolder
        > consumer, ItemLike icon,
                                                    Component title,
                                                    Component description,
                                                    @Nullable ResourceLocation background,
                                                    AdvancementType frame,
                                                    boolean showToast,
                                                    boolean announceToChat,
                                                    boolean hidden, @Nullable Item requiredItem, String path) {
        Advancement.Builder builder = Advancement.Builder.advancement();

        if(parent != null) builder
                .parent(parent);

        builder.display(
                icon,
                title,
                description,
                background,
                frame,
                showToast,
                announceToChat,
                hidden
        );

        if(requiredItem != null) builder
                .addCriterion(path, InventoryChangeTrigger.TriggerInstance.hasItems(requiredItem));
        else builder
                .addCriterion("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.CRAFTING_TABLE));

        return builder.save(consumer, SimpleOres.MOD_ID + "/" + path);
    }
}
