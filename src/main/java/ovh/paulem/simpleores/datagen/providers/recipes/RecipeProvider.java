package ovh.paulem.simpleores.datagen.providers.recipes;

import net.minecraft.data.server.recipe.*;
import ovh.paulem.simpleores.SimpleOres;
import ovh.paulem.simpleores.armors.MaterialRecipeContainer;
import ovh.paulem.simpleores.blocks.ModBlocks;
import ovh.paulem.simpleores.items.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static net.minecraft.data.server.recipe.RecipeGenerator.*;

public class RecipeProvider extends FabricRecipeProvider {
    public RecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {
                offerDustFurnace(this, ModItems.TIN_DUST, ModItems.TIN_INGOT, "tin");
                offerDustFurnace(this, ModItems.MYTHRIL_DUST, ModItems.MYTHRIL_INGOT, "mythril");
                offerDustFurnace(this, ModItems.ADAMANTIUM_DUST, ModItems.ADAMANTIUM_INGOT, "adamantium");

                createShaped(RecipeCategory.MISC, ModItems.MYTHRIL_BOW)
                        .pattern(" RS")
                        .pattern("F S")
                        .pattern(" RS")
                        .input('S', Items.STRING)
                        .input('F', Items.IRON_INGOT)
                        .input('R', ModItems.MYTHRIL_ROD)
                        .criterion(hasItem(Items.STRING), conditionsFromItem(Items.STRING))
                        .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                        .criterion(hasItem(ModItems.MYTHRIL_ROD), conditionsFromItem(ModItems.MYTHRIL_ROD))
                        .group("mythril")
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModItems.MYTHRIL_ROD)
                        .pattern("R")
                        .pattern("R")
                        .input('R', ModItems.MYTHRIL_INGOT)
                        .criterion(hasItem(ModItems.MYTHRIL_INGOT), conditionsFromItem(ModItems.MYTHRIL_INGOT))
                        .group("mythril")
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModItems.ONYX_BOW)
                        .pattern(" RS")
                        .pattern("F S")
                        .pattern(" RS")
                        .input('S', Items.STRING)
                        .input('F', Items.IRON_INGOT)
                        .input('R', ModItems.ONYX_ROD)
                        .criterion(hasItem(Items.STRING), conditionsFromItem(Items.STRING))
                        .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                        .criterion(hasItem(ModItems.ONYX_ROD), conditionsFromItem(ModItems.ONYX_ROD))
                        .group("onyx")
                        .offerTo(exporter);

                createShaped(RecipeCategory.MISC, ModItems.ONYX_ROD)
                        .pattern("R")
                        .pattern("R")
                        .input('R', ModItems.ONYX_GEM)
                        .criterion(hasItem(ModItems.ONYX_GEM), conditionsFromItem(ModItems.ONYX_GEM))
                        .group("onyx")
                        .offerTo(exporter);

                // Copper
                createToolsAndArmorsRecipe(this, Items.COPPER_INGOT, exporter, "copper", new MaterialRecipeContainer(
                        ModItems.COPPER_SWORD, ModItems.COPPER_PICKAXE, ModItems.COPPER_AXE, ModItems.COPPER_SHOVEL, ModItems.COPPER_HOE,
                        ModItems.COPPER_HELMET, ModItems.COPPER_CHESTPLATE, ModItems.COPPER_LEGGINGS, ModItems.COPPER_BOOTS, ModItems.COPPER_SHEARS,
                        Blocks.COPPER_ORE, Blocks.DEEPSLATE_COPPER_ORE, Blocks.COPPER_BLOCK, Blocks.RAW_COPPER_BLOCK, Items.RAW_COPPER, null,
                        null, ModBlocks.copper_bars, ModBlocks.copper_pressure_plate, null, null, null, ModItems.COPPER_BUCKET, null, true
                ));

                // Tin
                createToolsAndArmorsRecipe(this, ModItems.TIN_INGOT, exporter, "tin", new MaterialRecipeContainer(
                        ModItems.TIN_SWORD, ModItems.TIN_PICKAXE, ModItems.TIN_AXE, ModItems.TIN_SHOVEL, ModItems.TIN_HOE,
                        ModItems.TIN_HELMET, ModItems.TIN_CHESTPLATE, ModItems.TIN_LEGGINGS, ModItems.TIN_BOOTS, ModItems.TIN_SHEARS,
                        ModBlocks.TIN_ORE, ModBlocks.DEEPSLATE_TIN_ORE, ModBlocks.TIN_BLOCK, ModBlocks.RAW_TIN_BLOCK, ModItems.RAW_TIN, ModItems.TIN_NUGGET,
                        ModBlocks.tin_door, ModBlocks.tin_bars, ModBlocks.tin_pressure_plate, ModBlocks.TIN_BRICKS, ModBlocks.TIN_BRICK_SLAB, ModBlocks.tin_brick_stairs, null, 0.4f, false
                ));

                // Mythril
                createToolsAndArmorsRecipe(this, ModItems.MYTHRIL_INGOT, exporter, "mythril", new MaterialRecipeContainer(
                        ModItems.MYTHRIL_SWORD, ModItems.MYTHRIL_PICKAXE, ModItems.MYTHRIL_AXE, ModItems.MYTHRIL_SHOVEL, ModItems.MYTHRIL_HOE,
                        ModItems.MYTHRIL_HELMET, ModItems.MYTHRIL_CHESTPLATE, ModItems.MYTHRIL_LEGGINGS, ModItems.MYTHRIL_BOOTS, ModItems.MYTHRIL_SHEARS,
                        ModBlocks.MYTHRIL_ORE, ModBlocks.DEEPSLATE_MYTHRIL_ORE, ModBlocks.MYTHRIL_BLOCK, ModBlocks.RAW_MYTHRIL_BLOCK, ModItems.RAW_MYTHRIL, ModItems.MYTHRIL_NUGGET,
                        ModBlocks.mythril_door, ModBlocks.mythril_bars, ModBlocks.mythril_pressure_plate, ModBlocks.MYTHRIL_BRICKS, ModBlocks.MYTHRIL_BRICK_SLAB, ModBlocks.mythril_brick_stairs, null, 0.7f, false
                ));

                // Adamantium
                createToolsAndArmorsRecipe(this, ModItems.ADAMANTIUM_INGOT, exporter, "adamantium", new MaterialRecipeContainer(
                        ModItems.ADAMANTIUM_SWORD, ModItems.ADAMANTIUM_PICKAXE, ModItems.ADAMANTIUM_AXE, ModItems.ADAMANTIUM_SHOVEL, ModItems.ADAMANTIUM_HOE,
                        ModItems.ADAMANTIUM_HELMET, ModItems.ADAMANTIUM_CHESTPLATE, ModItems.ADAMANTIUM_LEGGINGS, ModItems.ADAMANTIUM_BOOTS, ModItems.ADAMANTIUM_SHEARS,
                        ModBlocks.ADAMANTIUM_ORE, ModBlocks.DEEPSLATE_ADAMANTIUM_ORE, ModBlocks.ADAMANTIUM_BLOCK, ModBlocks.RAW_ADAMANTIUM_BLOCK, ModItems.RAW_ADAMANTIUM, ModItems.ADAMANTIUM_NUGGET,
                        ModBlocks.adamantium_door, ModBlocks.adamantium_bars, ModBlocks.adamantium_pressure_plate, ModBlocks.ADAMANTIUM_BRICKS, ModBlocks.ADAMANTIUM_BRICK_SLAB, ModBlocks.adamantium_brick_stairs, null, 0.7f, false
                ));

                // Onyx
                createToolsAndArmorsRecipe(this, ModItems.ONYX_GEM, exporter, "onyx", new MaterialRecipeContainer(
                        ModItems.ONYX_SWORD, ModItems.ONYX_PICKAXE, ModItems.ONYX_AXE, ModItems.ONYX_SHOVEL, ModItems.ONYX_HOE,
                        ModItems.ONYX_HELMET, ModItems.ONYX_CHESTPLATE, ModItems.ONYX_LEGGINGS, ModItems.ONYX_BOOTS, ModItems.ONYX_SHEARS,
                        ModBlocks.ONYX_ORE, null, ModBlocks.ONYX_BLOCK, null, null, null,
                        ModBlocks.onyx_door, ModBlocks.onyx_bars, ModBlocks.onyx_pressure_plate, ModBlocks.ONYX_BRICKS, ModBlocks.ONYX_BRICK_SLAB, ModBlocks.onyx_brick_stairs, null, 1f, false
                ));
            }
        };
    }

    @Override
    public String getName() {
        return "Simple Ores Recipes";
    }

    private void offerDustFurnace(RecipeGenerator recipeGenerator, ItemConvertible dust, ItemConvertible output, String group) {
        recipeGenerator.offerSmelting(List.of(dust), RecipeCategory.MISC, output,
                0.7f, 200, group);
        recipeGenerator.offerBlasting(List.of(dust), RecipeCategory.MISC, output,
                0.7f, 100, group);
    }

    /**
     *
     * @param requiredItem The required ore
     * @param exporter The exporter
     * @param group The group of the recipe
     * @param container ORDER : sword, pickaxe, axe, shovel, hoe, helmet, chesplate, leggings, boots
     */
    public static void createToolsAndArmorsRecipe(RecipeGenerator generator, ItemConvertible requiredItem, RecipeExporter exporter, String group, MaterialRecipeContainer container) {
        List<ItemConvertible> SMELT_NUGGET_ITEMS = new ArrayList<>();

        // TOOLS
        if(container.sword() != null) {
            SMELT_NUGGET_ITEMS.add(container.sword());
            createSwordRecipe(generator, container.sword(), requiredItem, exporter, group);
        }
        if(container.pickaxe() != null) {
            SMELT_NUGGET_ITEMS.add(container.pickaxe());
            createPickaxeRecipe(generator, container.pickaxe(), requiredItem, exporter, group);
        }
        if(container.axe() != null) {
            SMELT_NUGGET_ITEMS.add(container.axe());
            createAxeRecipe(generator, container.axe(), requiredItem, exporter, group);
        }
        if(container.shovel() != null) {
            SMELT_NUGGET_ITEMS.add(container.shovel());
            createShovelRecipe(generator, container.shovel(), requiredItem, exporter, group);
        }
        if(container.hoe() != null) {
            SMELT_NUGGET_ITEMS.add(container.hoe());
            createHoeRecipe(generator, container.hoe(), requiredItem, exporter, group);
        }
        if(container.shears() != null) {
            createShearsRecipe(generator, container.shears(), requiredItem, exporter, group);
        }
        if(container.bucket() != null) {
            SMELT_NUGGET_ITEMS.add(container.bucket());
            generator.createShaped(RecipeCategory.MISC, container.bucket())
                    .pattern("R R")
                    .pattern(" R ")
                    .input('R', requiredItem)
                    .criterion(hasItem(requiredItem), generator.conditionsFromItem(requiredItem))
                    .group(group)
                    .offerTo(exporter);
        }

        if(container.block() != null) generator.offerReversibleCompactingRecipes(RecipeCategory.BUILDING_BLOCKS, requiredItem, RecipeCategory.DECORATIONS, container.block());
        if(container.raw() != null && container.rawBlock() != null) generator.offerReversibleCompactingRecipes(RecipeCategory.BUILDING_BLOCKS, container.raw(), RecipeCategory.DECORATIONS, container.rawBlock());

        if(container.pressurePlate() != null) {
            generator.createShaped(RecipeCategory.REDSTONE, container.pressurePlate())
                    .pattern("RR")
                    .input('R', requiredItem)
                    .criterion(hasItem(requiredItem), generator.conditionsFromItem(requiredItem))
                    .group(group)
                    .offerTo(exporter);
        }

        if(container.cut() != null) {
            generator.createShaped(RecipeCategory.BUILDING_BLOCKS, container.cut(), 4)
                    .pattern("RR")
                    .pattern("RR")
                    .input('R', requiredItem)
                    .criterion(hasItem(requiredItem), generator.conditionsFromItem(requiredItem))
                    .group(group)
                    .offerTo(exporter);

            generator.offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, container.cut(), requiredItem);
            // END ingot to cut block

            if(container.cutSlab() != null) {
                generator.createShaped(RecipeCategory.BUILDING_BLOCKS, container.cutSlab(), 6)
                        .pattern("RRR")
                        .input('R', container.cut())
                        .criterion(hasItem(requiredItem), generator.conditionsFromItem(requiredItem))
                        .group(group)
                        .offerTo(exporter);

                generator.offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, container.cutSlab(), container.cut(), 2);
                // END cut block to cut slabs
            }

            if(container.stairs() != null) {
                generator.createShaped(RecipeCategory.BUILDING_BLOCKS, container.stairs(), 4)
                        .pattern("R  ")
                        .pattern("RR ")
                        .pattern("RRR")
                        .input('R', container.cut())
                        .criterion(hasItem(requiredItem), generator.conditionsFromItem(requiredItem))
                        .group(group)
                        .offerTo(exporter);

                generator.offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, container.stairs(), container.cut());
                // END cut block to cut stairs
            }
        }

        if(container.door() != null) {
            generator.createDoorRecipe(container.door(), Ingredient.ofItems(requiredItem))
                    .criterion(hasItem(requiredItem), generator.conditionsFromItem(requiredItem))
                    .group(group)
                    .offerTo(exporter);
        }
        if(container.bars() != null) generator.createShaped(RecipeCategory.BUILDING_BLOCKS, container.bars(), 16)
                .pattern("RRR")
                .pattern("RRR")
                .input('R', requiredItem)
                .criterion(hasItem(requiredItem), generator.conditionsFromItem(requiredItem))
                .group(group)
                .offerTo(exporter);

        // ARMORS
        if(container.helmet() != null) {
            SMELT_NUGGET_ITEMS.add(container.helmet());
            generator.createShaped(RecipeCategory.COMBAT, container.helmet())
                    .pattern("RRR")
                    .pattern("R R")
                    .input('R', requiredItem)
                    .criterion(hasItem(requiredItem), generator.conditionsFromItem(requiredItem))
                    .group(group)
                    .offerTo(exporter);
        }
        if(container.chesplate() != null) {
            SMELT_NUGGET_ITEMS.add(container.chesplate());
            generator.createShaped(RecipeCategory.COMBAT, container.chesplate())
                    .pattern("R R")
                    .pattern("RRR")
                    .pattern("RRR")
                    .input('R', requiredItem)
                    .criterion(hasItem(requiredItem), generator.conditionsFromItem(requiredItem))
                    .group(group)
                    .offerTo(exporter);
        }
        if(container.leggings() != null) {
            SMELT_NUGGET_ITEMS.add(container.leggings());
            generator.createShaped(RecipeCategory.COMBAT, container.leggings())
                    .pattern("RRR")
                    .pattern("R R")
                    .pattern("R R")
                    .input('R', requiredItem)
                    .criterion(hasItem(requiredItem), generator.conditionsFromItem(requiredItem))
                    .group(group)
                    .offerTo(exporter);
        }
        if(container.boots() != null) {
            SMELT_NUGGET_ITEMS.add(container.boots());
            generator.createShaped(RecipeCategory.COMBAT, container.boots())
                    .pattern("R R")
                    .pattern("R R")
                    .input('R', requiredItem)
                    .criterion(hasItem(requiredItem), generator.conditionsFromItem(requiredItem))
                    .group(group)
                    .offerTo(exporter);
        }

        if(container.nugget() != null) {
            generator.createShapeless(RecipeCategory.MISC, container.nugget(), 9)
                    .input(requiredItem)
                    .criterion(hasItem(requiredItem), generator.conditionsFromItem(requiredItem))
                    .offerTo(exporter, SimpleOres.MOD_ID + ":" + getRecipeName(requiredItem) + "_to_nugget");

            generator.createShaped(RecipeCategory.MISC, requiredItem)
                    .pattern("RRR")
                    .pattern("RRR")
                    .pattern("RRR")
                    .input('R', container.nugget())
                    .criterion(hasItem(container.nugget()), generator.conditionsFromItem(container.nugget()))
                    .offerTo(exporter, SimpleOres.MOD_ID + ":" + getRecipeName(container.nugget()) + "_to_ingot");
        }

        if(container.nugget() != null && !SMELT_NUGGET_ITEMS.isEmpty()) {
            // Add equipment to nugget smelting recipe
            generator.offerSmelting(
                    SMELT_NUGGET_ITEMS,
                    RecipeCategory.MISC,
                    container.nugget(),
                    0.1F,
                    200,
                    "smelting_nugget_" + container.nugget().asItem().getTranslationKey()
            );

            // Add equipment to nugget blasting recipe
            generator.offerBlasting(
                    SMELT_NUGGET_ITEMS,
                    RecipeCategory.MISC,
                    container.nugget(),
                    0.1F,
                    200,
                    "blasting_nugget_" + container.nugget().asItem().getTranslationKey()
            );
        }

        if(!container.excludeSmeltCreation() && container.smeltXp() != null) {
            List<ItemConvertible> SMELTABLES = new ArrayList<>();

            if(container.ore() != null) SMELTABLES.add(container.ore());
            if(container.deepslateOre() != null) SMELTABLES.add(container.deepslateOre());
            if(container.raw() != null) SMELTABLES.add(container.raw());

            if(!SMELTABLES.isEmpty()) {
                generator.offerSmelting(SMELTABLES, RecipeCategory.MISC, requiredItem,
                        container.smeltXp(), 200, group);
                generator.offerBlasting(SMELTABLES, RecipeCategory.MISC, requiredItem,
                        container.smeltXp(), 100, group);
            }
        }
    }

    private static void createShearsRecipe(RecipeGenerator generator, ItemConvertible output, ItemConvertible requiredItem, RecipeExporter exporter, String group) {
        generator.createShaped(RecipeCategory.TOOLS, output)
                .pattern(" R")
                .pattern("R ")
                .input('R', requiredItem)
                .criterion(hasItem(requiredItem), generator.conditionsFromItem(requiredItem))
                .group(group)
                .offerTo(exporter);
    }

    public static void createHoeRecipe(RecipeGenerator generator, ItemConvertible output, ItemConvertible requiredItem, RecipeExporter exporter, String group){
        generator.createShaped(RecipeCategory.COMBAT, output)
                .pattern("RR")
                .pattern("S ")
                .pattern("S ")
                .input('R', requiredItem)
                .input('S', Items.STICK)
                .criterion(hasItem(requiredItem), generator.conditionsFromItem(requiredItem))
                .criterion(hasItem(Items.STICK), generator.conditionsFromItem(Items.STICK))
                .group(group)
                .offerTo(exporter);

        generator.createShaped(RecipeCategory.COMBAT, output)
                .pattern("RR")
                .pattern(" S")
                .pattern(" S")
                .input('R', requiredItem)
                .input('S', Items.STICK)
                .criterion(hasItem(requiredItem), generator.conditionsFromItem(requiredItem))
                .criterion(hasItem(Items.STICK), generator.conditionsFromItem(Items.STICK))
                .group(group)
                .offerTo(exporter, SimpleOres.MOD_ID + ":" + getRecipeName(output) + "_inverted");
    }

    public static void createShovelRecipe(RecipeGenerator generator, ItemConvertible output, ItemConvertible requiredItem, RecipeExporter exporter, String group){
        generator.createShaped(RecipeCategory.COMBAT, output)
                .pattern("R")
                .pattern("S")
                .pattern("S")
                .input('R', requiredItem)
                .input('S', Items.STICK)
                .criterion(hasItem(requiredItem), generator.conditionsFromItem(requiredItem))
                .criterion(hasItem(Items.STICK), generator.conditionsFromItem(Items.STICK))
                .group(group)
                .offerTo(exporter);
    }

    public static void createPickaxeRecipe(RecipeGenerator generator, ItemConvertible output, ItemConvertible requiredItem, RecipeExporter exporter, String group){
        generator.createShaped(RecipeCategory.COMBAT, output)
                .pattern("RRR")
                .pattern(" S ")
                .pattern(" S ")
                .input('R', requiredItem)
                .input('S', Items.STICK)
                .criterion(hasItem(requiredItem), generator.conditionsFromItem(requiredItem))
                .criterion(hasItem(Items.STICK), generator.conditionsFromItem(Items.STICK))
                .group(group)
                .offerTo(exporter);
    }

    public static void createAxeRecipe(RecipeGenerator generator, ItemConvertible output, ItemConvertible requiredItem, RecipeExporter exporter, String group){
        generator.createShaped(RecipeCategory.COMBAT, output)
                .pattern("RR")
                .pattern("SR")
                .pattern("S ")
                .input('R', requiredItem)
                .input('S', Items.STICK)
                .criterion(hasItem(requiredItem), generator.conditionsFromItem(requiredItem))
                .criterion(hasItem(Items.STICK), generator.conditionsFromItem(Items.STICK))
                .group(group)
                .offerTo(exporter);

        generator.createShaped(RecipeCategory.COMBAT, output)
                .pattern("RR")
                .pattern("RS")
                .pattern(" S")
                .input('R', requiredItem)
                .input('S', Items.STICK)
                .criterion(hasItem(requiredItem), generator.conditionsFromItem(requiredItem))
                .criterion(hasItem(Items.STICK), generator.conditionsFromItem(Items.STICK))
                .group(group)
                .offerTo(exporter, SimpleOres.MOD_ID + ":" + getRecipeName(output) + "_inverted");
    }

    public static void createSwordRecipe(RecipeGenerator generator, ItemConvertible output, ItemConvertible requiredItem, RecipeExporter exporter, String group){
        generator.createShaped(RecipeCategory.COMBAT, output)
                .pattern("R")
                .pattern("R")
                .pattern("S")
                .input('R', requiredItem)
                .input('S', Items.STICK)
                .criterion(hasItem(requiredItem), generator.conditionsFromItem(requiredItem))
                .criterion(hasItem(Items.STICK), generator.conditionsFromItem(Items.STICK))
                .group(group)
                .offerTo(exporter);
    }
}