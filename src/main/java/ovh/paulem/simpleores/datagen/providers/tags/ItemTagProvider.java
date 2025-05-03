package ovh.paulem.simpleores.datagen.providers.tags;

import de.cech12.bucketlib.api.item.UniversalBucketItem;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.block.Block;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.item.*;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import ovh.paulem.simpleores.items.custom.advanced.AdvancedArmorItem;
import ovh.paulem.simpleores.blocks.ModBlocks;
import ovh.paulem.simpleores.items.ModItems;
import ovh.paulem.simpleores.items.custom.advanced.AdvancedShearsItem;
import ovh.paulem.simpleores.tags.ModTags;

import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        ModItems.registeredItems.forEach(((identifier, item) -> {
            if (item instanceof SwordItem) {
                // Swords
                this.getOrCreateTagBuilder(ModTags.Items.SWORDS)
                        .add(item);
            } else if (item instanceof PickaxeItem) {
                // Pickaxes
                this.getOrCreateTagBuilder(ModTags.Items.PICKAXES)
                        .add(item);
            } else if (item instanceof AxeItem) {
                // Axes
                this.getOrCreateTagBuilder(ModTags.Items.AXES)
                        .add(item);
            } else if (item instanceof ShovelItem) {
                // Shovels
                this.getOrCreateTagBuilder(ModTags.Items.SHOVELS)
                        .add(item);
            } else if (item instanceof HoeItem) {
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
            } else if (item instanceof UniversalBucketItem) {
                // Buckets
                this.getOrCreateTagBuilder(ModTags.Items.BUCKETS)
                        .add(item);
            } else if (item instanceof BowItem) {
                // Bows
                this.getOrCreateTagBuilder(ModTags.Items.BOWS)
                        .add(item);
            } else if(identifier.getPath().contains("_nugget")) {
                // Nuggets
                this.getOrCreateTagBuilder(ModTags.Items.NUGGETS)
                        .add(item);
            } else if(identifier.getPath().contains("_dust")) {
                // Dusts
                this.getOrCreateTagBuilder(ModTags.Items.DUSTS)
                        .add(item);
            } else if(identifier.getPath().contains("crushed_") && identifier.getPath().contains("_ore")) {
                // Crushed Ore
                this.getOrCreateTagBuilder(ModTags.Items.CRUSHED_ORES)
                        .add(item);
            } else if(identifier.getPath().contains("_ingot")) {
                // Ingots
                this.getOrCreateTagBuilder(ModTags.Items.INGOTS)
                        .add(item);
            } else if(identifier.getPath().contains("_gem")) {
                // Gems
                this.getOrCreateTagBuilder(ModTags.Items.GEMS)
                        .add(item);
            } else if(identifier.getPath().contains("raw_")) {
                this.getOrCreateTagBuilder(ConventionalItemTags.RAW_MATERIALS)
                        .add(item);
            } else if(identifier.getPath().contains("_rod")) {
                this.getOrCreateTagBuilder(ConventionalItemTags.RODS)
                        .add(item);
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

        // ------------------- BUCKETS -------------------
        this.getOrCreateTagBuilder(ConventionalItemTags.BUCKETS)
                .addTag(ModTags.Items.BUCKETS);

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
