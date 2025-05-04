package ovh.paulem.simpleores.datagen.providers.tags;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.fabricmc.fabric.impl.tag.convention.v2.TagRegistration;
import net.minecraft.block.Block;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.item.*;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import ovh.paulem.simpleores.items.custom.advanced.*;
import ovh.paulem.simpleores.blocks.ModBlocks;
import ovh.paulem.simpleores.items.ModItems;
import ovh.paulem.simpleores.tags.ModTags;

import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        ModItems.registeredItems.forEach(((identifier, item) -> {
            if (item instanceof AdvancedSwordItem) {
                // Swords
                this.getOrCreateTagBuilder(ModTags.Items.SWORDS)
                        .add(item);
            } else if (item instanceof AdvancedPickaxeItem) {
                // Pickaxes
                this.getOrCreateTagBuilder(ModTags.Items.PICKAXES)
                        .add(item);
            } else if (item instanceof AdvancedAxeItem) {
                // Axes
                this.getOrCreateTagBuilder(ModTags.Items.AXES)
                        .add(item);
            } else if (item instanceof AdvancedShovelItem) {
                // Shovels
                this.getOrCreateTagBuilder(ModTags.Items.SHOVELS)
                        .add(item);
            } else if (item instanceof AdvancedHoeItem) {
                // Hoes
                this.getOrCreateTagBuilder(ModTags.Items.HOES)
                        .add(item);
            } else if (item instanceof AdvancedArmorItem armorItem) {
                // Armors
                this.getOrCreateTagBuilder(ModTags.Items.ARMORS)
                        .add(item);

                if(armorItem.getType() == EquipmentType.HELMET) {
                    // Helmets
                    this.getOrCreateTagBuilder(ItemTags.HEAD_ARMOR)
                            .add(item);
                } else if(armorItem.getType() == EquipmentType.CHESTPLATE) {
                    // Chestplates
                    this.getOrCreateTagBuilder(ItemTags.CHEST_ARMOR)
                            .add(item);
                } else if(armorItem.getType() == EquipmentType.LEGGINGS) {
                    // Leggings
                    this.getOrCreateTagBuilder(ItemTags.LEG_ARMOR)
                            .add(item);
                } else if(armorItem.getType() == EquipmentType.BOOTS) {
                    // Boots
                    this.getOrCreateTagBuilder(ItemTags.FOOT_ARMOR)
                            .add(item);
                }

            } else if (item instanceof AdvancedShearsItem) {
                // Shears
                this.getOrCreateTagBuilder(ModTags.Items.SHEARS)
                        .add(item);
            } else if (item instanceof BowItem) {
                // Bows
                this.getOrCreateTagBuilder(ModTags.Items.BOWS)
                        .add(item);
            } else if(identifier.getPath().contains("_nugget")) {
                // Nuggets
                this.getOrCreateTagBuilder(ModTags.Items.NUGGETS)
                        .add(item);

                // MOD COMPAT
                String material = identifier.getPath().replace("_nugget", "");

                TagKey<Item> tag = TagRegistration.ITEM_TAG.registerC("nuggets/" + material);
                this.getOrCreateTagBuilder(tag)
                        .add(item);
            } else if(identifier.getPath().contains("_dust")) {
                // Dusts
                this.getOrCreateTagBuilder(ModTags.Items.DUSTS)
                        .add(item);

                // MOD COMPAT
                String material = identifier.getPath().replace("_dust", "");

                TagKey<Item> tag = TagRegistration.ITEM_TAG.registerC("dusts/" + material);
                this.getOrCreateTagBuilder(tag)
                        .add(item);

                this.getOrCreateTagBuilder(ConventionalItemTags.DUSTS)
                        .addTag(tag);
            } else if(identifier.getPath().contains("crushed_") && identifier.getPath().contains("_ore")) {
                // Crushed Ore
                this.getOrCreateTagBuilder(ModTags.Items.CRUSHED_ORES)
                        .add(item);
            } else if(identifier.getPath().contains("_ingot")) {
                // Ingots
                this.getOrCreateTagBuilder(ModTags.Items.INGOTS)
                        .add(item);

                // MOD COMPAT
                String material = identifier.getPath().replace("_ingot", "");

                TagKey<Item> tag = TagRegistration.ITEM_TAG.registerC("ingots/" + material);
                this.getOrCreateTagBuilder(tag)
                        .add(item);
            } else if(identifier.getPath().contains("_gem")) {
                // Gems
                this.getOrCreateTagBuilder(ModTags.Items.GEMS)
                        .add(item);

                // MOD COMPAT
                String material = identifier.getPath().replace("_gem", "");

                TagKey<Item> tag = TagRegistration.ITEM_TAG.registerC("gems/" + material);
                this.getOrCreateTagBuilder(tag)
                        .add(item);

                this.getOrCreateTagBuilder(ConventionalItemTags.GEMS)
                        .addTag(tag);
            } else if(identifier.getPath().contains("raw_")) {
                // MOD COMPAT
                String material = identifier.getPath().replace("raw_", "");

                TagKey<Item> tag = TagRegistration.ITEM_TAG.registerC("raw_materials/" + material);
                this.getOrCreateTagBuilder(tag)
                        .add(item);

                this.getOrCreateTagBuilder(ConventionalItemTags.RAW_MATERIALS)
                        .addTag(tag);
            } else if(identifier.getPath().contains("_rod")) {
                // Rods
                this.getOrCreateTagBuilder(ModTags.Items.RODS)
                        .add(item);

                // MOD COMPAT
                String material = identifier.getPath().replace("_rod", "");

                TagKey<Item> tag = TagRegistration.ITEM_TAG.registerC("rods/" + material);
                this.getOrCreateTagBuilder(tag)
                        .add(item);

                this.getOrCreateTagBuilder(ConventionalItemTags.RODS)
                        .addTag(tag);
            }
        }));

        // ------------------- ARMORS -------------------
        this.getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
                .addTag(ModTags.Items.ARMORS);

        // ------------------- SWORDS -------------------
        this.getOrCreateTagBuilder(ItemTags.SWORDS)
                .addTag(ModTags.Items.SWORDS);

        // ------------------- AXES -------------------
        this.getOrCreateTagBuilder(ItemTags.AXES)
                .addTag(ModTags.Items.AXES);

        // ------------------- MELEE -------------------
        this.getOrCreateTagBuilder(ConventionalItemTags.MELEE_WEAPON_TOOLS)
                .addTag(ModTags.Items.SWORDS)
                .addTag(ModTags.Items.AXES);

        // ------------------- SHOVELS -------------------
        this.getOrCreateTagBuilder(ItemTags.SHOVELS)
                .addTag(ModTags.Items.SHOVELS);

        // ------------------- HOES -------------------
        this.getOrCreateTagBuilder(ItemTags.HOES)
                .addTag(ModTags.Items.HOES);

        // ------------------- PICKAXES -------------------
        this.getOrCreateTagBuilder(ItemTags.PICKAXES)
                .addTag(ModTags.Items.PICKAXES);
        this.getOrCreateTagBuilder(ItemTags.CLUSTER_MAX_HARVESTABLES)
                .addTag(ModTags.Items.PICKAXES);
        this.getOrCreateTagBuilder(ConventionalItemTags.MINING_TOOL_TOOLS)
                .addTag(ModTags.Items.PICKAXES);

        // ------------------- SHEARS -------------------
        this.getOrCreateTagBuilder(ConventionalItemTags.SHEAR_TOOLS)
                .addTag(ModTags.Items.SHEARS);
        this.getOrCreateTagBuilder(ItemTags.MINING_ENCHANTABLE)
                .addTag(ModTags.Items.SHEARS);

        // ------------------- NUGGETS -------------------
        this.getOrCreateTagBuilder(ConventionalItemTags.NUGGETS)
                .addTag(ModTags.Items.NUGGETS);

        // ------------------- DUSTS -------------------
        this.getOrCreateTagBuilder(ConventionalItemTags.DUSTS)
                .addTag(ModTags.Items.DUSTS);

        // ------------------- DURABILITY ENCHANTABLE -------------------
        this.getOrCreateTagBuilder(ItemTags.DURABILITY_ENCHANTABLE)
                .addTag(ModTags.Items.SHEARS)
                .addTag(ModTags.Items.BOWS);

        // ------------------- BOWS -------------------
        this.getOrCreateTagBuilder(ConventionalItemTags.BOW_TOOLS)
                .addTag(ModTags.Items.BOWS);
        this.getOrCreateTagBuilder(ConventionalItemTags.RANGED_WEAPON_TOOLS)
                .addTag(ModTags.Items.BOWS);
        this.getOrCreateTagBuilder(ItemTags.BOW_ENCHANTABLE)
                .addTag(ModTags.Items.BOWS);

        // ------------------- INGOTS/GEMS -------------------
        this.getOrCreateTagBuilder(ConventionalItemTags.INGOTS)
                .addTag(ModTags.Items.INGOTS);
        this.getOrCreateTagBuilder(ConventionalItemTags.GEMS)
                .addTag(ModTags.Items.GEMS);

        this.getOrCreateTagBuilder(ItemTags.BEACON_PAYMENT_ITEMS)
                .addTag(ModTags.Items.INGOTS)
                .addTag(ModTags.Items.GEMS);

        // ------------------- RODS -------------------
        this.getOrCreateTagBuilder(ConventionalItemTags.RODS)
                .addTag(ModTags.Items.RODS);

        // ------------------- REPAIR -------------------
        this.getOrCreateTagBuilder(ModTags.Items.REPAIRS_TIN_ITEMS)
                .add(ModItems.TIN_INGOT);

        this.getOrCreateTagBuilder(ModTags.Items.REPAIRS_MYTHRIL_ITEMS)
                .add(ModItems.MYTHRIL_INGOT);

        this.getOrCreateTagBuilder(ModTags.Items.REPAIRS_ADAMANTIUM_ITEMS)
                .add(ModItems.ADAMANTIUM_INGOT);

        this.getOrCreateTagBuilder(ModTags.Items.REPAIRS_ONYX_ITEMS)
                .add(ModItems.ONYX_GEM);

        ModBlocks.registeredBlockItems.forEach((identifier, blockItem) -> {
            Block block = blockItem.getBlock();

            if(block instanceof DoorBlock)
                getOrCreateTagBuilder(ItemTags.DOORS)
                        .add(blockItem);
            else if(block instanceof SlabBlock)
                getOrCreateTagBuilder(ItemTags.SLABS)
                        .add(blockItem);
            else if(block instanceof StairsBlock)
                getOrCreateTagBuilder(ItemTags.STAIRS)
                        .add(blockItem);
        });
    }
}
