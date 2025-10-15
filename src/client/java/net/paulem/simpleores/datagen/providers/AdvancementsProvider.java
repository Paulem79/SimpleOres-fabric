package net.paulem.simpleores.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.paulem.simpleores.SimpleOres;
import net.paulem.simpleores.blocks.ModBlocks;
import net.paulem.simpleores.items.ModItems;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import net.paulem.simpleores.stonecutter.SCIdentifier;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AdvancementsProvider extends FabricAdvancementProvider {
    public AdvancementsProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output //? if >1.20.4
                , registryLookup
        );
    }

    @Override
    public void generateAdvancement(//? if >1.20.4
            RegistryWrapper.WrapperLookup registryLookup,
                                    Consumer<//$ advancementEntry
                                            net.minecraft.advancement.AdvancementEntry
                                            > consumer) {
        //$ advancementEntry
        net.minecraft.advancement.AdvancementEntry
                rootAdvancement = buildAdvancement(
                null,
                consumer,
                //? if !hasCopperTools {
                ModItems.COPPER_PICKAXE,
                //?} else {
                /*ModItems.MYTHRIL_PICKAXE,
                *///?}
                Text.translatable("advancements.welcome"),
                Text.translatable("advancements.welcome"),
                //? if !hasCopperTools {
                SCIdentifier.ofVanilla("textures/gui/advancements/backgrounds/stone.png"),
                //?} else {
                 /*SCIdentifier.ofVanilla("gui/advancements/backgrounds/stone"),
                *///?}
                AdvancementFrame.TASK,
                false,
                false,
                false,
                null,
                "root"
        );

        //$ advancementEntry
        net.minecraft.advancement.AdvancementEntry
        neitherOrNeitherIron = buildAdvancement(
                rootAdvancement,
                consumer,
                Blocks.COPPER_ORE,
                Text.translatable("advancements.copper_ach"),
                Text.translatable("advancements.copper_ach.desc"),
                Items.RAW_COPPER
        );

        //? if !hasCopperTools {
            //$ advancementEntry
            net.minecraft.advancement.AdvancementEntry
             pickaxeCopper = buildAdvancement(
                    neitherOrNeitherIron,
                    consumer,
                    ModItems.COPPER_PICKAXE,
                    Text.translatable("advancements.copper_pick_ach"),
                    Text.translatable("advancements.copper_pick_ach.desc")
            );
        //?}

        //$ advancementEntry
        net.minecraft.advancement.AdvancementEntry
                copperBucket = buildAdvancement(
                neitherOrNeitherIron,
                consumer,
                ModItems.COPPER_BUCKET,
                Text.translatable("advancements.copper_bucket_ach"),
                Text.translatable("advancements.copper_bucket_ach.desc")
        );

        //$ advancementEntry
        net.minecraft.advancement.AdvancementEntry
         tinAdvancement = buildAdvancement(
                neitherOrNeitherIron,
                consumer,
                ModBlocks.TIN_ORE,
                Text.translatable("advancements.tin_ach"),
                Text.translatable("advancements.tin_ach.desc"),
                ModItems.RAW_TIN
        );

        //$ advancementEntry
        net.minecraft.advancement.AdvancementEntry
         tinShearsAdvancement = buildAdvancement(
                tinAdvancement,
                consumer,
                ModItems.TIN_SHEARS,
                Text.translatable("advancements.tin_shears_ach"),
                Text.translatable("advancements.tin_shears_ach.desc")
        );

        //$ advancementEntry
        net.minecraft.advancement.AdvancementEntry
         mythrilAdvancement = buildAdvancement(
                tinAdvancement,
                consumer,
                ModBlocks.MYTHRIL_ORE,
                Text.translatable("advancements.mythril_ach"),
                Text.translatable("advancements.mythril_ach.desc"),
                ModItems.RAW_MYTHRIL
        );

        //$ advancementEntry
        net.minecraft.advancement.AdvancementEntry
         tinChestplate = buildAdvancement(
                tinAdvancement,
                consumer,
                ModItems.TIN_CHESTPLATE,
                Text.translatable("advancements.tin_chestplate_ach"),
                Text.translatable("advancements.tin_chestplate_ach.desc")
        );

        //$ advancementEntry
        net.minecraft.advancement.AdvancementEntry
         adamantiumAdvancement = buildAdvancement(
                mythrilAdvancement,
                consumer,
                ModBlocks.ADAMANTIUM_ORE,
                Text.translatable("advancements.adamantium_ach"),
                Text.translatable("advancements.adamantium_ach.desc"),
                ModItems.RAW_ADAMANTIUM
        );

        //$ advancementEntry
        net.minecraft.advancement.AdvancementEntry
         bowMythril = buildAdvancement(
                mythrilAdvancement,
                consumer,
                ModItems.MYTHRIL_BOW,
                Text.translatable("advancements.mythril_bow_ach"),
                Text.translatable("advancements.mythril_bow_ach.desc")
        );

        //$ advancementEntry
        net.minecraft.advancement.AdvancementEntry
         axeMythril = buildAdvancement(
                mythrilAdvancement,
                consumer,
                ModItems.MYTHRIL_AXE,
                Text.translatable("advancements.mythril_axe_ach"),
                Text.translatable("advancements.mythril_axe_ach.desc")
        );

        //$ advancementEntry
        net.minecraft.advancement.AdvancementEntry
         shearsAdamantium = buildAdvancement(
                adamantiumAdvancement,
                consumer,
                ModItems.ADAMANTIUM_SHEARS,
                Text.translatable("advancements.adamantium_shears_ach"),
                Text.translatable("advancements.adamantium_shears_ach.desc")
        );

        //$ advancementEntry
        net.minecraft.advancement.AdvancementEntry
         leggingsAdamantium = buildAdvancement(
                adamantiumAdvancement,
                consumer,
                ModItems.ADAMANTIUM_LEGGINGS,
                Text.translatable("advancements.adamantium_legs_ach"),
                Text.translatable("advancements.adamantium_legs_ach.desc")
        );

        //$ advancementEntry
        net.minecraft.advancement.AdvancementEntry
         onyxAdvancement = buildAdvancement(
                adamantiumAdvancement,
                consumer,
                ModBlocks.ONYX_ORE,
                Text.translatable("advancements.onyx_ach"),
                Text.translatable("advancements.onyx_ach.desc"),
                ModItems.ONYX_GEM
        );

        //$ advancementEntry
        net.minecraft.advancement.AdvancementEntry
         onyxBow = buildAdvancement(
                onyxAdvancement,
                consumer,
                ModItems.ONYX_BOW,
                Text.translatable("advancements.onyx_bow_ach"),
                Text.translatable("advancements.onyx_bow_ach.desc")
        );

        //$ advancementEntry
        net.minecraft.advancement.AdvancementEntry
         onyxSword = buildAdvancement(
                onyxAdvancement,
                consumer,
                ModItems.ONYX_SWORD,
                Text.translatable("advancements.onyx_sword_ach"),
                Text.translatable("advancements.onyx_sword_ach.desc")
        );
    }


    public static //$ advancementEntry
        net.minecraft.advancement.AdvancementEntry
         buildAdvancement(@Nullable //$ advancementEntry
        net.minecraft.advancement.AdvancementEntry
         parent, Consumer<//$ advancementEntry
        net.minecraft.advancement.AdvancementEntry
        > consumer, @Nullable Item requiredItem,
                                                    Text title,
                                                    Text description) {
        return buildAdvancement(parent, consumer, requiredItem, title, description, null, AdvancementFrame.TASK, true, true, false, requiredItem, Registries.ITEM.getId(requiredItem).getPath());
    }

    public static //$ advancementEntry
        net.minecraft.advancement.AdvancementEntry
         buildAdvancement(@Nullable //$ advancementEntry
        net.minecraft.advancement.AdvancementEntry
         parent, Consumer<//$ advancementEntry
        net.minecraft.advancement.AdvancementEntry
        > consumer, ItemConvertible icon,
                                                    Text title,
                                                    Text description,
                                                    @Nullable Item requiredItem) {
        return buildAdvancement(parent, consumer, icon, title, description, null, AdvancementFrame.TASK, true, true, false, requiredItem, Registries.ITEM.getId(requiredItem).getPath());
    }

    public static //$ advancementEntry
        net.minecraft.advancement.AdvancementEntry
         buildAdvancement(@Nullable //$ advancementEntry
        net.minecraft.advancement.AdvancementEntry
         parent, Consumer<//$ advancementEntry
        net.minecraft.advancement.AdvancementEntry
        > consumer, ItemConvertible icon,
                                                    Text title,
                                                    Text description,
                                                    @Nullable Identifier background,
                                                    AdvancementFrame frame,
                                                    boolean showToast,
                                                    boolean announceToChat,
                                                    boolean hidden, @Nullable Item requiredItem, String path) {
        Advancement.Builder builder = Advancement.Builder.create();

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
                .criterion(path, InventoryChangedCriterion.Conditions.items(requiredItem));
        else builder
                .criterion("crafting_table", InventoryChangedCriterion.Conditions.items(Blocks.CRAFTING_TABLE));

        return builder.build(consumer, SimpleOres.MOD_ID + "/" + path);
    }
}
