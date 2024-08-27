package io.github.paulem.simpleores.datagen.providers.recipes;

import io.github.paulem.simpleores.armors.MaterialRecipeContainer;
import io.github.paulem.simpleores.blocks.ModBlocks;
import io.github.paulem.simpleores.items.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RecipeProvider extends FabricRecipeProvider {
    public RecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.MYTHRIL_BOW)
                .pattern(" RS")
                .pattern("F S")
                .pattern(" RS")
                .input('S', Items.STICK)
                .input('F', Items.IRON_INGOT)
                .input('R', ModItems.MYTHRIL_ROD)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .criterion(hasItem(ModItems.MYTHRIL_ROD), conditionsFromItem(ModItems.MYTHRIL_ROD))
                .group("mythril")
                .offerTo(exporter, Identifier.of(getRecipeName(ModItems.MYTHRIL_BOW)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.ONYX_BOW)
                .pattern(" RS")
                .pattern("F S")
                .pattern(" RS")
                .input('S', Items.STICK)
                .input('F', Items.IRON_INGOT)
                .input('R', ModItems.ONYX_ROD)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .criterion(hasItem(ModItems.ONYX_ROD), conditionsFromItem(ModItems.ONYX_ROD))
                .group("onyx")
                .offerTo(exporter, Identifier.of(getRecipeName(ModItems.ONYX_BOW)));

        // Copper
        createToolsAndArmorsRecipe(Items.COPPER_INGOT, exporter, "copper", new MaterialRecipeContainer(
                ModItems.COPPER_SWORD, ModItems.COPPER_PICKAXE, ModItems.COPPER_AXE, ModItems.COPPER_SHOVEL, ModItems.COPPER_HOE,
                ModItems.COPPER_HELMET, ModItems.COPPER_CHESTPLATE, ModItems.COPPER_LEGGINGS, ModItems.COPPER_BOOTS, ModItems.COPPER_SHEARS,
                Blocks.COPPER_ORE, Blocks.DEEPSLATE_COPPER_ORE, Blocks.COPPER_BLOCK, Blocks.RAW_COPPER_BLOCK, Items.RAW_COPPER, ModItems.COPPER_NUGGET,
                null, ModBlocks.copper_bars, ModBlocks.copper_pressure_plate, null, null, null, ModItems.COPPER_BUCKET, null, true
        ));

        // Tin
        createToolsAndArmorsRecipe(ModItems.TIN_INGOT, exporter, "tin", new MaterialRecipeContainer(
                ModItems.TIN_SWORD, ModItems.TIN_PICKAXE, ModItems.TIN_AXE, ModItems.TIN_SHOVEL, ModItems.TIN_HOE,
                ModItems.TIN_HELMET, ModItems.TIN_CHESTPLATE, ModItems.TIN_LEGGINGS, ModItems.TIN_BOOTS, ModItems.TIN_SHEARS,
                ModBlocks.TIN_ORE, ModBlocks.DEEPSLATE_TIN_ORE, ModBlocks.TIN_BLOCK, ModBlocks.RAW_TIN_BLOCK, ModItems.RAW_TIN, ModItems.TIN_NUGGET,
                ModBlocks.tin_door, ModBlocks.tin_bars, ModBlocks.tin_pressure_plate, ModBlocks.TIN_BRICKS, ModBlocks.TIN_BRICK_SLAB, ModBlocks.tin_brick_stairs, null, 0.4f, false
        ));

        // Mythril
        createToolsAndArmorsRecipe(ModItems.MYTHRIL_INGOT, exporter, "mythril", new MaterialRecipeContainer(
                ModItems.MYTHRIL_SWORD, ModItems.MYTHRIL_PICKAXE, ModItems.MYTHRIL_AXE, ModItems.MYTHRIL_SHOVEL, ModItems.MYTHRIL_HOE,
                ModItems.MYTHRIL_HELMET, ModItems.MYTHRIL_CHESTPLATE, ModItems.MYTHRIL_LEGGINGS, ModItems.MYTHRIL_BOOTS, ModItems.MYTHRIL_SHEARS,
                ModBlocks.MYTHRIL_ORE, ModBlocks.DEEPSLATE_MYTHRIL_ORE, ModBlocks.MYTHRIL_BLOCK, ModBlocks.RAW_MYTHRIL_BLOCK, ModItems.RAW_MYTHRIL, ModItems.MYTHRIL_NUGGET,
                ModBlocks.mythril_door, ModBlocks.mythril_bars, ModBlocks.mythril_pressure_plate, ModBlocks.MYTHRIL_BRICKS, ModBlocks.MYTHRIL_BRICK_SLAB, ModBlocks.mythril_brick_stairs, null, 0.7f, false
        ));

        // Adamantium
        createToolsAndArmorsRecipe(ModItems.ADAMANTIUM_INGOT, exporter, "adamantium", new MaterialRecipeContainer(
                ModItems.ADAMANTIUM_SWORD, ModItems.ADAMANTIUM_PICKAXE, ModItems.ADAMANTIUM_AXE, ModItems.ADAMANTIUM_SHOVEL, ModItems.ADAMANTIUM_HOE,
                ModItems.ADAMANTIUM_HELMET, ModItems.ADAMANTIUM_CHESTPLATE, ModItems.ADAMANTIUM_LEGGINGS, ModItems.ADAMANTIUM_BOOTS, ModItems.ADAMANTIUM_SHEARS,
                ModBlocks.ADAMANTIUM_ORE, ModBlocks.DEEPSLATE_ADAMANTIUM_ORE, ModBlocks.ADAMANTIUM_BLOCK, ModBlocks.RAW_ADAMANTIUM_BLOCK, ModItems.RAW_ADAMANTIUM, ModItems.ADAMANTIUM_NUGGET,
                ModBlocks.adamantium_door, ModBlocks.adamantium_bars, ModBlocks.adamantium_pressure_plate, ModBlocks.ADAMANTIUM_BRICKS, ModBlocks.ADAMANTIUM_BRICK_SLAB, ModBlocks.adamantium_brick_stairs, null, 0.7f, false
        ));

        // Onyx
        createToolsAndArmorsRecipe(ModItems.ONYX_GEM, exporter, "onyx", new MaterialRecipeContainer(
                ModItems.ONYX_SWORD, ModItems.ONYX_PICKAXE, ModItems.ONYX_AXE, ModItems.ONYX_SHOVEL, ModItems.ONYX_HOE,
                ModItems.ONYX_HELMET, ModItems.ONYX_CHESTPLATE, ModItems.ONYX_LEGGINGS, ModItems.ONYX_BOOTS, ModItems.ONYX_SHEARS,
                ModBlocks.ONYX_ORE, null, ModBlocks.ONYX_BLOCK, null, null, null,
                ModBlocks.onyx_door, ModBlocks.onyx_bars, ModBlocks.onyx_pressure_plate, ModBlocks.ONYX_BRICKS, ModBlocks.ONYX_BRICK_SLAB, ModBlocks.onyx_brick_stairs, null, 1f, false
        ));
    }

    /**
     *
     * @param requiredItem The required ore
     * @param exporter The exporter
     * @param group The group of the recipe
     * @param container ORDER : sword, pickaxe, axe, shovel, hoe, helmet, chesplate, leggings, boots
     */
    public static void createToolsAndArmorsRecipe(ItemConvertible requiredItem, RecipeExporter exporter, String group, MaterialRecipeContainer container) {
        List<ItemConvertible> SMELT_NUGGET_ITEMS = new ArrayList<>();

        // TOOLS
        if(container.sword() != null) {
            SMELT_NUGGET_ITEMS.add(container.sword());
            createSwordRecipe(container.sword(), requiredItem, exporter, group);
        }
        if(container.pickaxe() != null) {
            SMELT_NUGGET_ITEMS.add(container.pickaxe());
            createPickaxeRecipe(container.pickaxe(), requiredItem, exporter, group);
        }
        if(container.axe() != null) {
            SMELT_NUGGET_ITEMS.add(container.axe());
            createAxeRecipe(container.axe(), requiredItem, exporter, group);
        }
        if(container.shovel() != null) {
            SMELT_NUGGET_ITEMS.add(container.shovel());
            createShovelRecipe(container.shovel(), requiredItem, exporter, group);
        }
        if(container.hoe() != null) {
            SMELT_NUGGET_ITEMS.add(container.hoe());
            createHoeRecipe(container.hoe(), requiredItem, exporter, group);
        }
        if(container.shears() != null) {
            createShearsRecipe(container.shears(), requiredItem, exporter, group);
        }
        if(container.bucket() != null) {
            SMELT_NUGGET_ITEMS.add(container.bucket());
            ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, container.bucket())
                    .pattern("R R")
                    .pattern(" R ")
                    .input('R', requiredItem)
                    .criterion(hasItem(requiredItem), conditionsFromItem(requiredItem))
                    .group(group)
                    .offerTo(exporter, Identifier.of(getRecipeName(container.bucket())));
        }

        if(container.block() != null) offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, requiredItem, RecipeCategory.DECORATIONS, container.block());
        if(container.raw() != null && container.rawBlock() != null) offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, container.raw(), RecipeCategory.DECORATIONS, container.rawBlock());

        if(container.pressurePlate() != null) {
            ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, container.pressurePlate())
                    .pattern("RR")
                    .input('R', requiredItem)
                    .criterion(hasItem(requiredItem), conditionsFromItem(requiredItem))
                    .group(group)
                    .offerTo(exporter, Identifier.of(getRecipeName(container.pressurePlate())));
        }

        if(container.cut() != null) {
            ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, container.cut(), 4)
                    .pattern("RR")
                    .pattern("RR")
                    .input('R', requiredItem)
                    .criterion(hasItem(requiredItem), conditionsFromItem(requiredItem))
                    .group(group)
                    .offerTo(exporter, Identifier.of(getRecipeName(container.cut())));

            offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, container.cut(), requiredItem);
            // END ingot to cut block

            if(container.cutSlab() != null) {
                ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, container.cutSlab(), 6)
                        .pattern("RRR")
                        .input('R', container.cut())
                        .criterion(hasItem(container.cut()), conditionsFromItem(container.cut()))
                        .group(group)
                        .offerTo(exporter, Identifier.of(getRecipeName(container.cutSlab())));

                offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, container.cutSlab(), container.cut(), 2);
                // END cut block to cut slabs
            }

            if(container.stairs() != null) {
                ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, container.stairs(), 4)
                        .pattern("R  ")
                        .pattern("RR ")
                        .pattern("RRR")
                        .input('R', container.cut())
                        .criterion(hasItem(container.cut()), conditionsFromItem(container.cut()))
                        .group(group)
                        .offerTo(exporter, Identifier.of(getRecipeName(container.stairs())));

                offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, container.stairs(), container.cut());
                // END cut block to cut stairs
            }
        }

        if(container.door() != null) {
            createDoorRecipe(container.door(), Ingredient.ofItems(requiredItem))
                    .criterion(hasItem(requiredItem), conditionsFromItem(requiredItem))
                    .group(group)
                    .offerTo(exporter, Identifier.of(getRecipeName(container.door())));
        }
        if(container.bars() != null) ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, container.bars(), 16)
                .pattern("RRR")
                .pattern("RRR")
                .input('R', requiredItem)
                .criterion(hasItem(requiredItem), conditionsFromItem(requiredItem))
                .group(group)
                .offerTo(exporter, Identifier.of(getRecipeName(container.bars())));

        // ARMORS
        if(container.helmet() != null) {
            SMELT_NUGGET_ITEMS.add(container.helmet());
            ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, container.helmet())
                    .pattern("RRR")
                    .pattern("R R")
                    .input('R', requiredItem)
                    .criterion(hasItem(requiredItem), conditionsFromItem(requiredItem))
                    .group(group)
                    .offerTo(exporter, Identifier.of(getRecipeName(container.helmet())));
        }
        if(container.chesplate() != null) {
            SMELT_NUGGET_ITEMS.add(container.chesplate());
            ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, container.chesplate())
                    .pattern("R R")
                    .pattern("RRR")
                    .pattern("RRR")
                    .input('R', requiredItem)
                    .criterion(hasItem(requiredItem), conditionsFromItem(requiredItem))
                    .group(group)
                    .offerTo(exporter, Identifier.of(getRecipeName(container.chesplate())));
        }
        if(container.leggings() != null) {
            SMELT_NUGGET_ITEMS.add(container.leggings());
            ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, container.leggings())
                    .pattern("RRR")
                    .pattern("R R")
                    .pattern("R R")
                    .input('R', requiredItem)
                    .criterion(hasItem(requiredItem), conditionsFromItem(requiredItem))
                    .group(group)
                    .offerTo(exporter, Identifier.of(getRecipeName(container.leggings())));
        }
        if(container.boots() != null) {
            SMELT_NUGGET_ITEMS.add(container.boots());
            ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, container.boots())
                    .pattern("R R")
                    .pattern("R R")
                    .input('R', requiredItem)
                    .criterion(hasItem(requiredItem), conditionsFromItem(requiredItem))
                    .group(group)
                    .offerTo(exporter, Identifier.of(getRecipeName(container.boots())));
        }

        if(container.nugget() != null) {
            ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, container.nugget(), 9)
                    .input(requiredItem)
                    .criterion(hasItem(requiredItem), conditionsFromItem(requiredItem))
                    .offerTo(exporter, Identifier.of(getRecipeName(requiredItem) + "_to_nugget"));

            ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, requiredItem)
                    .pattern("RRR")
                    .pattern("RRR")
                    .pattern("RRR")
                    .input('R', container.nugget())
                    .criterion(hasItem(container.nugget()), conditionsFromItem(container.nugget()))
                    .offerTo(exporter, Identifier.of(getRecipeName(container.nugget()) + "_to_ingot"));
        }

        if(container.nugget() != null && !SMELT_NUGGET_ITEMS.isEmpty()) {
            // Add equipment to nugget smelting recipe
            CookingRecipeJsonBuilder smelting = CookingRecipeJsonBuilder.createSmelting(
                    Ingredient.ofStacks(SMELT_NUGGET_ITEMS.stream().map(ItemStack::new)),
                    RecipeCategory.MISC,
                    container.nugget(),
                    0.1F,
                    200
            );

            // Add criterions
            for(ItemConvertible item : SMELT_NUGGET_ITEMS) {
                smelting.criterion(hasItem(item), conditionsFromItem(item));
            }

            // Put in Minecraft
            smelting.offerTo(exporter, getSmeltingItemPath(container.nugget()));

            // Add equipment to nugget blasting recipe
            CookingRecipeJsonBuilder blasting = CookingRecipeJsonBuilder.createBlasting(
                    Ingredient.ofStacks(SMELT_NUGGET_ITEMS.stream().map(ItemStack::new)),
                    RecipeCategory.MISC,
                    container.nugget(),
                    0.1F,
                    200
            );

            // Add criterions
            for(ItemConvertible item : SMELT_NUGGET_ITEMS) {
                blasting.criterion(hasItem(item), conditionsFromItem(item));
            }

            // Put in Minecraft
            blasting.offerTo(exporter, getBlastingItemPath(container.nugget()));
        }

        if(!container.excludeSmeltCreation() && container.smeltXp() != null) {
            List<ItemConvertible> SMELTABLES = new ArrayList<>();

            if(container.ore() != null) SMELTABLES.add(container.ore());
            if(container.deepslateOre() != null) SMELTABLES.add(container.deepslateOre());
            if(container.raw() != null) SMELTABLES.add(container.raw());

            if(!SMELTABLES.isEmpty()) {
                offerSmelting(exporter, SMELTABLES, RecipeCategory.MISC, requiredItem,
                        container.smeltXp(), 200, group);
                offerBlasting(exporter, SMELTABLES, RecipeCategory.MISC, requiredItem,
                        container.smeltXp(), 100, group);
            }
        }
    }

    private static void createShearsRecipe(ItemConvertible output, ItemConvertible requiredItem, RecipeExporter exporter, String group) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, output)
                .pattern(" R")
                .pattern("R ")
                .input('R', requiredItem)
                .criterion(hasItem(requiredItem), conditionsFromItem(requiredItem))
                .group(group)
                .offerTo(exporter, Identifier.of(getRecipeName(output)));
    }

    public static void createHoeRecipe(ItemConvertible output, ItemConvertible requiredItem, RecipeExporter exporter, String group){
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, output)
                .pattern("RR")
                .pattern("S ")
                .pattern("S ")
                .input('R', requiredItem)
                .input('S', Items.STICK)
                .criterion(hasItem(requiredItem), conditionsFromItem(requiredItem))
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .group(group)
                .offerTo(exporter, Identifier.of(getRecipeName(output)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, output)
                .pattern("RR")
                .pattern(" S")
                .pattern(" S")
                .input('R', requiredItem)
                .input('S', Items.STICK)
                .criterion(hasItem(requiredItem), conditionsFromItem(requiredItem))
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .group(group)
                .offerTo(exporter, Identifier.of(getRecipeName(output) + "_inverted"));
    }

    public static void createShovelRecipe(ItemConvertible output, ItemConvertible requiredItem, RecipeExporter exporter, String group){
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, output)
                .pattern("R")
                .pattern("S")
                .pattern("S")
                .input('R', requiredItem)
                .input('S', Items.STICK)
                .criterion(hasItem(requiredItem), conditionsFromItem(requiredItem))
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .group(group)
                .offerTo(exporter, Identifier.of(getRecipeName(output)));
    }

    public static void createPickaxeRecipe(ItemConvertible output, ItemConvertible requiredItem, RecipeExporter exporter, String group){
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, output)
                .pattern("RRR")
                .pattern(" S ")
                .pattern(" S ")
                .input('R', requiredItem)
                .input('S', Items.STICK)
                .criterion(hasItem(requiredItem), conditionsFromItem(requiredItem))
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .group(group)
                .offerTo(exporter, Identifier.of(getRecipeName(output)));
    }

    public static void createAxeRecipe(ItemConvertible output, ItemConvertible requiredItem, RecipeExporter exporter, String group){
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, output)
                .pattern("RR")
                .pattern("SR")
                .pattern("S ")
                .input('R', requiredItem)
                .input('S', Items.STICK)
                .criterion(hasItem(requiredItem), conditionsFromItem(requiredItem))
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .group(group)
                .offerTo(exporter, Identifier.of(getRecipeName(output)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, output)
                .pattern("RR")
                .pattern("RS")
                .pattern(" S")
                .input('R', requiredItem)
                .input('S', Items.STICK)
                .criterion(hasItem(requiredItem), conditionsFromItem(requiredItem))
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .group(group)
                .offerTo(exporter, Identifier.of(getRecipeName(output) + "_inverted"));
    }

    public static void createSwordRecipe(ItemConvertible output, ItemConvertible requiredItem, RecipeExporter exporter, String group){
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, output)
                .pattern("R")
                .pattern("R")
                .pattern("S")
                .input('R', requiredItem)
                .input('S', Items.STICK)
                .criterion(hasItem(requiredItem), conditionsFromItem(requiredItem))
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .group(group)
                .offerTo(exporter, Identifier.of(getRecipeName(output)));
    }
}