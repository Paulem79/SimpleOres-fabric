package io.github.paulem.simpleores.datagen.providers;

import io.github.paulem.simpleores.SimpleOres;
import io.github.paulem.simpleores.blocks.ModBlocks;
import io.github.paulem.simpleores.items.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class AdvancementsProvider extends FabricAdvancementProvider {
    public AdvancementsProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateAdvancement(Consumer<Advancement> consumer) {
        Advancement rootAdvancement = buildAdvancement(
                null,
                consumer,
                ModItems.COPPER_PICKAXE,
                Text.translatable("advancements.welcome"),
                Text.translatable("advancements.welcome"),
                new Identifier(Identifier.DEFAULT_NAMESPACE, "textures/gui/advancements/backgrounds/stone.png"),
                AdvancementFrame.TASK,
                false,
                false,
                false,
                null,
                "root"
        );

        Advancement neitherOrNeitherIron = buildAdvancement(
                rootAdvancement,
                consumer,
                Blocks.COPPER_ORE,
                Text.translatable("advancements.copper_ach"),
                Text.translatable("advancements.copper_ach.desc"),
                Items.RAW_COPPER
        );

        Advancement pickaxeCopper = buildAdvancement(
                neitherOrNeitherIron,
                consumer,
                ModItems.COPPER_PICKAXE,
                Text.translatable("advancements.copper_pick_ach"),
                Text.translatable("advancements.copper_pick_ach.desc")
        );

        Advancement copperBucket = buildAdvancement(
                neitherOrNeitherIron,
                consumer,
                ModItems.COPPER_BUCKET,
                Text.translatable("advancements.copper_bucket_ach"),
                Text.translatable("advancements.copper_bucket_ach.desc")
        );

        Advancement tinAdvancement = buildAdvancement(
                neitherOrNeitherIron,
                consumer,
                ModBlocks.TIN_ORE,
                Text.translatable("advancements.tin_ach"),
                Text.translatable("advancements.tin_ach.desc"),
                ModItems.RAW_TIN
        );

        Advancement tinShearsAdvancement = buildAdvancement(
                tinAdvancement,
                consumer,
                ModItems.TIN_SHEARS,
                Text.translatable("advancements.tin_shears_ach"),
                Text.translatable("advancements.tin_shears_ach.desc")
        );

        Advancement mythrilAdvancement = buildAdvancement(
                tinAdvancement,
                consumer,
                ModBlocks.MYTHRIL_ORE,
                Text.translatable("advancements.mythril_ach"),
                Text.translatable("advancements.mythril_ach.desc"),
                ModItems.RAW_MYTHRIL
        );

        Advancement tinChestplate = buildAdvancement(
                tinAdvancement,
                consumer,
                ModItems.TIN_CHESTPLATE,
                Text.translatable("advancements.tin_chestplate_ach"),
                Text.translatable("advancements.tin_chestplate_ach.desc")
        );

        Advancement adamantiumAdvancement = buildAdvancement(
                mythrilAdvancement,
                consumer,
                ModBlocks.ADAMANTIUM_ORE,
                Text.translatable("advancements.adamantium_ach"),
                Text.translatable("advancements.adamantium_ach.desc"),
                ModItems.RAW_ADAMANTIUM
        );

        Advancement bowMythril = buildAdvancement(
                mythrilAdvancement,
                consumer,
                ModItems.MYTHRIL_BOW,
                Text.translatable("advancements.mythril_bow_ach"),
                Text.translatable("advancements.mythril_bow_ach.desc")
        );

        Advancement axeMythril = buildAdvancement(
                mythrilAdvancement,
                consumer,
                ModItems.MYTHRIL_AXE,
                Text.translatable("advancements.mythril_axe_ach"),
                Text.translatable("advancements.mythril_axe_ach.desc")
        );

        Advancement shearsAdamantium = buildAdvancement(
                adamantiumAdvancement,
                consumer,
                ModItems.ADAMANTIUM_SHEARS,
                Text.translatable("advancements.adamantium_shears_ach"),
                Text.translatable("advancements.adamantium_shears_ach.desc")
        );

        Advancement leggingsAdamantium = buildAdvancement(
                adamantiumAdvancement,
                consumer,
                ModItems.ADAMANTIUM_LEGGINGS,
                Text.translatable("advancements.adamantium_legs_ach"),
                Text.translatable("advancements.adamantium_legs_ach.desc")
        );

        Advancement onyxAdvancement = buildAdvancement(
                adamantiumAdvancement,
                consumer,
                ModBlocks.ONYX_ORE,
                Text.translatable("advancements.onyx_ach"),
                Text.translatable("advancements.onyx_ach.desc"),
                ModItems.ONYX_GEM
        );

        Advancement onyxBow = buildAdvancement(
                onyxAdvancement,
                consumer,
                ModItems.ONYX_BOW,
                Text.translatable("advancements.onyx_bow_ach"),
                Text.translatable("advancements.onyx_bow_ach.desc")
        );

        Advancement onyxSword = buildAdvancement(
                onyxAdvancement,
                consumer,
                ModItems.ONYX_SWORD,
                Text.translatable("advancements.onyx_sword_ach"),
                Text.translatable("advancements.onyx_sword_ach.desc")
        );
    }


    public static Advancement buildAdvancement(@Nullable Advancement parent, Consumer<Advancement> consumer, @Nullable Item requiredItem,
                                                    Text title,
                                                    Text description) {
        return buildAdvancement(parent, consumer, requiredItem, title, description, null, AdvancementFrame.TASK, true, true, false, requiredItem, Registries.ITEM.getId(requiredItem).getPath());
    }

    public static Advancement buildAdvancement(@Nullable Advancement parent, Consumer<Advancement> consumer, ItemConvertible icon,
                                                    Text title,
                                                    Text description,
                                                    @Nullable Item requiredItem) {
        return buildAdvancement(parent, consumer, icon, title, description, null, AdvancementFrame.TASK, true, true, false, requiredItem, Registries.ITEM.getId(requiredItem).getPath());
    }

    public static Advancement buildAdvancement(@Nullable Advancement parent, Consumer<Advancement> consumer, ItemConvertible icon,
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
