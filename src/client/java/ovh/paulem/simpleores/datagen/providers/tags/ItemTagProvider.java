package ovh.paulem.simpleores.datagen.providers.tags;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.fabricmc.fabric.impl.tag.convention.v2.TagRegistration;
import net.minecraft.block.Block;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.data.tag.ProvidedTagBuilder;
import net.minecraft.item.*;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
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
                build(ModTags.Items.SWORDS, item);
            } else if (item instanceof AdvancedPickaxeItem) {
                // Pickaxes
                build(ModTags.Items.PICKAXES, item);
            } else if (item instanceof AdvancedAxeItem) {
                // Axes
                build(ModTags.Items.AXES, item);
            } else if (item instanceof AdvancedShovelItem) {
                // Shovels
                build(ModTags.Items.SHOVELS, item);
            } else if (item instanceof AdvancedHoeItem) {
                // Hoes
                build(ModTags.Items.HOES, item);
            } else if (item instanceof AdvancedArmorItem armorItem) {
                // Armors
                build(ModTags.Items.ARMORS, item);

                if(armorItem.getType() == EquipmentType.HELMET) {
                    // Helmets
                    build(ItemTags.HEAD_ARMOR, item);
                } else if(armorItem.getType() == EquipmentType.CHESTPLATE) {
                    // Chestplates
                    build(ItemTags.CHEST_ARMOR, item);
                } else if(armorItem.getType() == EquipmentType.LEGGINGS) {
                    // Leggings
                    build(ItemTags.LEG_ARMOR, item);
                } else if(armorItem.getType() == EquipmentType.BOOTS) {
                    // Boots
                    build(ItemTags.FOOT_ARMOR, item);
                }

            } else if (item instanceof AdvancedShearsItem) {
                // Shears
                build(ModTags.Items.SHEARS, item);
            } else if (item instanceof BowItem) {
                // Bows
                build(ModTags.Items.BOWS, item);
            } else if(identifier.getPath().contains("_nugget")) {
                // Nuggets
                build(ModTags.Items.NUGGETS, item);

                // MOD COMPAT
                String material = identifier.getPath().replace("_nugget", "");

                TagKey<Item> tag = TagRegistration.ITEM_TAG.registerC("nuggets/" + material);
                build(tag, item);
            } else if(identifier.getPath().contains("_dust")) {
                // Dusts
                build(ModTags.Items.DUSTS, item);

                // MOD COMPAT
                String material = identifier.getPath().replace("_dust", "");

                TagKey<Item> tag = TagRegistration.ITEM_TAG.registerC("dusts/" + material);
                build(tag, item);

                build(ConventionalItemTags.DUSTS, tag);
            } else if(identifier.getPath().contains("crushed_") && identifier.getPath().contains("_ore")) {
                // Crushed Ore
                build(ModTags.Items.CRUSHED_ORES, item);
            } else if(identifier.getPath().contains("_ingot")) {
                // Ingots
                build(ModTags.Items.INGOTS, item);

                // MOD COMPAT
                String material = identifier.getPath().replace("_ingot", "");

                TagKey<Item> tag = TagRegistration.ITEM_TAG.registerC("ingots/" + material);
                build(tag, item);
            } else if(identifier.getPath().contains("_gem")) {
                // Gems
                build(ModTags.Items.GEMS, item);

                // MOD COMPAT
                String material = identifier.getPath().replace("_gem", "");

                TagKey<Item> tag = TagRegistration.ITEM_TAG.registerC("gems/" + material);
                build(tag, item);

                build(ConventionalItemTags.GEMS, tag);
            } else if(identifier.getPath().contains("raw_")) {
                // MOD COMPAT
                String material = identifier.getPath().replace("raw_", "");

                TagKey<Item> tag = TagRegistration.ITEM_TAG.registerC("raw_materials/" + material);
                build(tag, item);

                build(ConventionalItemTags.RAW_MATERIALS, tag);
            } else if(identifier.getPath().contains("_rod")) {
                // Rods
                build(ModTags.Items.RODS, item);

                // MOD COMPAT
                String material = identifier.getPath().replace("_rod", "");

                TagKey<Item> tag = TagRegistration.ITEM_TAG.registerC("rods/" + material);
                build(tag, item);

                build(ConventionalItemTags.RODS, tag);
            }
        }));

        // ------------------- ARMORS -------------------
        build(ItemTags.TRIMMABLE_ARMOR, ModTags.Items.ARMORS);

        // ------------------- SWORDS -------------------
        build(ItemTags.SWORDS, ModTags.Items.SWORDS);

        // ------------------- AXES -------------------
        build(ItemTags.AXES, ModTags.Items.AXES);

        // ------------------- MELEE -------------------
        build(ConventionalItemTags.MELEE_WEAPON_TOOLS, ModTags.Items.SWORDS, ModTags.Items.AXES);

        // ------------------- SHOVELS -------------------
        build(ItemTags.SHOVELS, ModTags.Items.SHOVELS);

        // ------------------- HOES -------------------
        build(ItemTags.HOES, ModTags.Items.HOES);

        // ------------------- PICKAXES -------------------
        build(ItemTags.PICKAXES, ModTags.Items.PICKAXES);
        build(ItemTags.CLUSTER_MAX_HARVESTABLES, ModTags.Items.PICKAXES);
        build(ConventionalItemTags.MINING_TOOL_TOOLS, ModTags.Items.PICKAXES);

        // ------------------- SHEARS -------------------
        build(ConventionalItemTags.SHEAR_TOOLS, ModTags.Items.SHEARS);
        build(ItemTags.MINING_ENCHANTABLE, ModTags.Items.SHEARS);

        // ------------------- NUGGETS -------------------
        build(ConventionalItemTags.NUGGETS, ModTags.Items.NUGGETS);

        // ------------------- DUSTS -------------------
        build(ConventionalItemTags.DUSTS, ModTags.Items.DUSTS);

        // ------------------- DURABILITY ENCHANTABLE -------------------
        build(ItemTags.DURABILITY_ENCHANTABLE, ModTags.Items.SHEARS, ModTags.Items.BOWS);

        // ------------------- BOWS -------------------
        build(ConventionalItemTags.BOW_TOOLS, ModTags.Items.BOWS);
        build(ConventionalItemTags.RANGED_WEAPON_TOOLS, ModTags.Items.BOWS);
        build(ItemTags.BOW_ENCHANTABLE, ModTags.Items.BOWS);

        // ------------------- INGOTS/GEMS -------------------
        build(ConventionalItemTags.INGOTS, ModTags.Items.INGOTS);
        build(ConventionalItemTags.GEMS, ModTags.Items.GEMS);

        build(ItemTags.BEACON_PAYMENT_ITEMS, ModTags.Items.INGOTS, ModTags.Items.GEMS);

        // ------------------- RODS -------------------
        build(ConventionalItemTags.RODS, ModTags.Items.RODS);

        // ------------------- REPAIR -------------------
        build(ModTags.Items.REPAIRS_TIN_ITEMS, ModItems.TIN_INGOT);

        build(ModTags.Items.REPAIRS_MYTHRIL_ITEMS, ModItems.MYTHRIL_INGOT);

        build(ModTags.Items.REPAIRS_ADAMANTIUM_ITEMS, ModItems.ADAMANTIUM_INGOT);

        build(ModTags.Items.REPAIRS_ONYX_ITEMS, ModItems.ONYX_GEM);

        ModBlocks.registeredBlockItems.forEach((identifier, blockItem) -> {
            Block block = blockItem.getBlock();

            if(block instanceof DoorBlock)
                build(ItemTags.DOORS, blockItem);
            else if(block instanceof SlabBlock)
                build(ItemTags.SLABS, blockItem);
            else if(block instanceof StairsBlock)
                build(ItemTags.STAIRS, blockItem);
        });
    }

    private void build(TagKey<Item> tagKey, Object... objects) {
        ProvidedTagBuilder<RegistryKey<Item>, Item> builder = builder(tagKey);

        build(builder, objects);
    }

    private void build(ProvidedTagBuilder<RegistryKey<Item>, Item> builder,
                       Object... objects) {
        for (Object object : objects) {

            if(object instanceof Item item) {
                builder
                        .add(RegistryKey.of(Registries.ITEM.getKey(), Registries.ITEM.getId(item)));
            } else if(object instanceof TagKey<?> tag && object.getClass().getGenericSuperclass() == Item.class) {
                builder
                        .addTag((TagKey<Item>) tag);
            }
        }
    }
}