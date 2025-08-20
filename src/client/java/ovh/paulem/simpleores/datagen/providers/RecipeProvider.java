package ovh.paulem.simpleores.datagen.providers;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.advancement.AdvancementCriterion;
//? if <=1.20.1
//import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.data.recipe.*;
import net.minecraft.item.Item;
import net.minecraft.recipe.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import ovh.paulem.simpleores.SimpleOres;
import ovh.paulem.simpleores.armors.MaterialRecipeContainer;
import ovh.paulem.simpleores.blocks.ModBlocks;
import ovh.paulem.simpleores.items.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import ovh.paulem.simpleores.stonecutter.SCIdentifier;
import ovh.paulem.simpleores.tags.ModTags;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

//? if >1.21
import static net.minecraft.data.recipe.RecipeGenerator.*;

//? if >1.21.3 {
import static net.minecraft.data.recipe.RecipeGenerator.getRecipeName;
import static net.minecraft.data.recipe.RecipeGenerator.hasItem;
//?}


public class RecipeProvider extends FabricRecipeProvider {
    public RecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output //? if >1.20.4
                , registryLookup
        );
    }

    private static SCRecipe scRecipe;

    public void extracted(//$ generatorOrExporter
                                 net.minecraft.data.recipe.RecipeGenerator
                                         generator, RecipeExporter exporter) {
        scRecipe = new SCRecipe(this, generator, exporter);

        offerDustFurnace(ModTags.Items.Conventional.TIN_DUSTS, ModItems.TIN_INGOT, "tin");
        offerDustFurnace(ModTags.Items.Conventional.MYTHRIL_DUSTS, ModItems.MYTHRIL_INGOT, "mythril");
        offerDustFurnace(ModTags.Items.Conventional.ADAMANTIUM_DUSTS, ModItems.ADAMANTIUM_INGOT, "adamantium");

        scRecipe.createShaped(RecipeCategory.MISC, ModItems.MYTHRIL_BOW)
                .pattern(" RS")
                .pattern("F S")
                .pattern(" RS")
                .input('S', Items.STRING)
                .input('F', Items.IRON_INGOT)
                .input('R', ModItems.MYTHRIL_ROD)
                .criterion(hasItem(Items.STRING), scRecipe.conditionsFromItem(Items.STRING))
                .criterion(hasItem(Items.IRON_INGOT), scRecipe.conditionsFromItem(Items.IRON_INGOT))
                .criterion(hasItem(ModItems.MYTHRIL_ROD), scRecipe.conditionsFromItem(ModItems.MYTHRIL_ROD))
                .group("mythril")
                .offerTo(exporter);

        scRecipe.createShaped(RecipeCategory.MISC, ModItems.MYTHRIL_ROD)
                .pattern("R")
                .pattern("R")
                .input('R', ModItems.MYTHRIL_INGOT)
                .criterion(hasItem(ModItems.MYTHRIL_INGOT), scRecipe.conditionsFromItem(ModItems.MYTHRIL_INGOT))
                .group("mythril")
                .offerTo(exporter);

        scRecipe.createShaped(RecipeCategory.MISC, ModItems.ONYX_BOW)
                .pattern(" RS")
                .pattern("F S")
                .pattern(" RS")
                .input('S', Items.STRING)
                .input('F', Items.IRON_INGOT)
                .input('R', ModItems.ONYX_ROD)
                .criterion(hasItem(Items.STRING), scRecipe.conditionsFromItem(Items.STRING))
                .criterion(hasItem(Items.IRON_INGOT), scRecipe.conditionsFromItem(Items.IRON_INGOT))
                .criterion(hasItem(ModItems.ONYX_ROD), scRecipe.conditionsFromItem(ModItems.ONYX_ROD))
                .group("onyx")
                .offerTo(exporter);

        scRecipe.createShaped(RecipeCategory.MISC, ModItems.ONYX_ROD)
                .pattern("R")
                .pattern("R")
                .input('R', ModItems.ONYX_GEM)
                .criterion(hasItem(ModItems.ONYX_GEM), scRecipe.conditionsFromItem(ModItems.ONYX_GEM))
                .group("onyx")
                .offerTo(exporter);

        // Copper
        createMaterialSetRecipes(ConventionalItemTags.COPPER_INGOTS, Items.COPPER_INGOT, exporter, "copper", new MaterialRecipeContainer(
                //? if !hasCopperTools {
                ModItems.COPPER_SWORD, ModItems.COPPER_PICKAXE, ModItems.COPPER_AXE, ModItems.COPPER_SHOVEL, ModItems.COPPER_HOE, ModItems.COPPER_HELMET,
                ModItems.COPPER_CHESTPLATE, ModItems.COPPER_LEGGINGS, ModItems.COPPER_BOOTS,
                //?} else {
                /*null, null, null, null, null, null, null, null, null,
                *///?}
                ModItems.COPPER_SHEARS,
                ModTags.Items.Conventional.COPPER_ORES, Blocks.COPPER_BLOCK, Blocks.RAW_COPPER_BLOCK, ModTags.Items.Conventional.RAW_COPPER_ORES, Items.RAW_COPPER, null,
                null,
                //? !hasCopperTools {
                ModBlocks.copper_bars,
                //?} else {
                /*null,
                *///?}
                ModBlocks.copper_pressure_plate, null, null, null,
                //? hasBucketlib {
                /*ModItems.COPPER_BUCKET,
                *///?} else {
                null,
                //?}
                null, true
        ));

        // Tin
        createMaterialSetRecipes(ModTags.Items.Conventional.TIN_INGOTS, ModItems.TIN_INGOT, exporter, "tin", new MaterialRecipeContainer(
                ModItems.TIN_SWORD, ModItems.TIN_PICKAXE, ModItems.TIN_AXE, ModItems.TIN_SHOVEL, ModItems.TIN_HOE,
                ModItems.TIN_HELMET, ModItems.TIN_CHESTPLATE, ModItems.TIN_LEGGINGS, ModItems.TIN_BOOTS, ModItems.TIN_SHEARS,
                ModTags.Items.Conventional.TIN_ORES, ModBlocks.TIN_BLOCK, ModBlocks.RAW_TIN_BLOCK, ModTags.Items.Conventional.RAW_TIN_ORES, ModItems.RAW_TIN, ModItems.TIN_NUGGET,
                ModBlocks.tin_door, ModBlocks.tin_bars, ModBlocks.tin_pressure_plate, ModBlocks.TIN_BRICKS, ModBlocks.TIN_BRICK_SLAB, ModBlocks.tin_brick_stairs, null, 0.4f, false
        ));

        // Mythril
        createMaterialSetRecipes(ModTags.Items.Conventional.MYTHRIL_INGOTS, ModItems.MYTHRIL_INGOT, exporter, "mythril", new MaterialRecipeContainer(
                ModItems.MYTHRIL_SWORD, ModItems.MYTHRIL_PICKAXE, ModItems.MYTHRIL_AXE, ModItems.MYTHRIL_SHOVEL, ModItems.MYTHRIL_HOE,
                ModItems.MYTHRIL_HELMET, ModItems.MYTHRIL_CHESTPLATE, ModItems.MYTHRIL_LEGGINGS, ModItems.MYTHRIL_BOOTS, ModItems.MYTHRIL_SHEARS,
                ModTags.Items.Conventional.MYTHRIL_ORES, ModBlocks.MYTHRIL_BLOCK, ModBlocks.RAW_MYTHRIL_BLOCK, ModTags.Items.Conventional.RAW_MYTHRIL_ORES, ModItems.RAW_MYTHRIL, ModItems.MYTHRIL_NUGGET,
                ModBlocks.mythril_door, ModBlocks.mythril_bars, ModBlocks.mythril_pressure_plate, ModBlocks.MYTHRIL_BRICKS, ModBlocks.MYTHRIL_BRICK_SLAB, ModBlocks.mythril_brick_stairs, null, 0.7f, false
        ));

        // Adamantium
        createMaterialSetRecipes(ModTags.Items.Conventional.ADAMANTIUM_INGOTS, ModItems.ADAMANTIUM_INGOT, exporter, "adamantium", new MaterialRecipeContainer(
                ModItems.ADAMANTIUM_SWORD, ModItems.ADAMANTIUM_PICKAXE, ModItems.ADAMANTIUM_AXE, ModItems.ADAMANTIUM_SHOVEL, ModItems.ADAMANTIUM_HOE,
                ModItems.ADAMANTIUM_HELMET, ModItems.ADAMANTIUM_CHESTPLATE, ModItems.ADAMANTIUM_LEGGINGS, ModItems.ADAMANTIUM_BOOTS, ModItems.ADAMANTIUM_SHEARS,
                ModTags.Items.Conventional.ADAMANTIUM_ORES, ModBlocks.ADAMANTIUM_BLOCK, ModBlocks.RAW_ADAMANTIUM_BLOCK, ModTags.Items.Conventional.RAW_ADAMANTIUM_ORES, ModItems.RAW_ADAMANTIUM, ModItems.ADAMANTIUM_NUGGET,
                ModBlocks.adamantium_door, ModBlocks.adamantium_bars, ModBlocks.adamantium_pressure_plate, ModBlocks.ADAMANTIUM_BRICKS, ModBlocks.ADAMANTIUM_BRICK_SLAB, ModBlocks.adamantium_brick_stairs, null, 0.7f, false
        ));

        // Onyx
        createMaterialSetRecipes(ModTags.Items.Conventional.ONYX_GEMS, ModItems.ONYX_GEM, exporter, "onyx", new MaterialRecipeContainer(
                ModItems.ONYX_SWORD, ModItems.ONYX_PICKAXE, ModItems.ONYX_AXE, ModItems.ONYX_SHOVEL, ModItems.ONYX_HOE,
                ModItems.ONYX_HELMET, ModItems.ONYX_CHESTPLATE, ModItems.ONYX_LEGGINGS, ModItems.ONYX_BOOTS, ModItems.ONYX_SHEARS,
                ModTags.Items.Conventional.ONYX_ORES, ModBlocks.ONYX_BLOCK, null, null, null, null,
                ModBlocks.onyx_door, ModBlocks.onyx_bars, ModBlocks.onyx_pressure_plate, ModBlocks.ONYX_BRICKS, ModBlocks.ONYX_BRICK_SLAB, ModBlocks.onyx_brick_stairs, null, 1f, false
        ));
    }

    //? if <1.21.3 {
    
    /*@Override
    public void generate(RecipeExporter recipeExporter) {
        extracted(recipeExporter, recipeExporter);
    }*/
     
    //?} else {
    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {
                extracted(this, exporter);
            }
        };
    }
    //?}

    @Override
    public String getName() {
        return "Simple Ores Recipes";
    }

    private static void offerDustFurnace(TagKey<Item> dustTag, ItemConvertible output, String group) {
        scRecipe.offerSmelting(dustTag, RecipeCategory.MISC, output,
                0.7f, 200, group);
        scRecipe.offerBlasting(dustTag, RecipeCategory.MISC, output,
                0.7f, 100, group);
    }

    public static void createMaterialSetRecipes(TagKey<Item> tag, ItemConvertible baseItem, RecipeExporter exporter, String group, MaterialRecipeContainer container) {
        List<ItemConvertible> SMELT_NUGGET_ITEMS = new ArrayList<>();

        // TOOLS
        if(container.sword() != null) {
            SMELT_NUGGET_ITEMS.add(container.sword());
            createSwordRecipe(container.sword(), tag, exporter, group);
        }
        if(container.pickaxe() != null) {
            SMELT_NUGGET_ITEMS.add(container.pickaxe());
            createPickaxeRecipe(container.pickaxe(), tag, exporter, group);
        }
        if(container.axe() != null) {
            SMELT_NUGGET_ITEMS.add(container.axe());
            createAxeRecipe(container.axe(), tag, exporter, group);
        }
        if(container.shovel() != null) {
            SMELT_NUGGET_ITEMS.add(container.shovel());
            createShovelRecipe(container.shovel(), tag, exporter, group);
        }
        if(container.hoe() != null) {
            SMELT_NUGGET_ITEMS.add(container.hoe());
            createHoeRecipe(container.hoe(), tag, exporter, group);
        }
        if(container.shears() != null) {
            createShearsRecipe(container.shears(), tag, exporter, group);
        }
        if(container.bucket() != null) {
            SMELT_NUGGET_ITEMS.add(container.bucket());
            scRecipe.createShaped(RecipeCategory.MISC, container.bucket())
                    .pattern("R R")
                    .pattern(" R ")
                    .input('R', tag)
                    .criterion(hasTag(tag), scRecipe.conditionsFromTag(tag))
                    .group(group)
                    .offerTo(exporter);
        }

        if(container.block() != null) scRecipe.offerReversibleCompactingRecipes(RecipeCategory.BUILDING_BLOCKS, container.block(), baseItem, tag,
                RecipeCategory.DECORATIONS);
        if(container.raw() != null && container.rawBlock() != null) scRecipe.offerReversibleCompactingRecipes(RecipeCategory.BUILDING_BLOCKS, container.rawBlock(), container.baseRawItem(), container.raw(), RecipeCategory.DECORATIONS);

        if(container.pressurePlate() != null) {
            scRecipe.createShaped(RecipeCategory.REDSTONE, container.pressurePlate())
                    .pattern("RR")
                    .input('R', tag)
                    .criterion(hasTag(tag), scRecipe.conditionsFromTag(tag))
                    .group(group)
                    .offerTo(exporter);
        }

        if(container.cut() != null) {
            scRecipe.createShaped(RecipeCategory.BUILDING_BLOCKS, container.cut(), 4)
                    .pattern("RR")
                    .pattern("RR")
                    .input('R', tag)
                    .criterion(hasTag(tag), scRecipe.conditionsFromTag(tag))
                    .group(group)
                    .offerTo(exporter);

            scRecipe.offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, container.cut(), tag);
            // END ingot to cut block

            if(container.cutSlab() != null) {
                scRecipe.createShaped(RecipeCategory.BUILDING_BLOCKS, container.cutSlab(), 6)
                        .pattern("RRR")
                        .input('R', container.cut())
                        .criterion(hasTag(tag), scRecipe.conditionsFromTag(tag))
                        .group(group)
                        .offerTo(exporter);

                scRecipe.offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, container.cutSlab(), container.cut(), 2);
                // END cut block to cut slabs
            }

            if(container.stairs() != null) {
                scRecipe.createShaped(RecipeCategory.BUILDING_BLOCKS, container.stairs(), 4)
                        .pattern("R  ")
                        .pattern("RR ")
                        .pattern("RRR")
                        .input('R', container.cut())
                        .criterion(hasTag(tag), scRecipe.conditionsFromTag(tag))
                        .group(group)
                        .offerTo(exporter);

                scRecipe.offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, container.stairs(), container.cut());
                // END cut block to cut stairs
            }
        }

        if(container.door() != null) {
            scRecipe.createDoorRecipe(container.door(), scRecipe.ingredientFromTag(tag))
                    .criterion(hasTag(tag), scRecipe.conditionsFromTag(tag))
                    .group(group)
                    .offerTo(exporter);
        }
        if(container.bars() != null) scRecipe.createShaped(RecipeCategory.BUILDING_BLOCKS, container.bars(), 16)
                .pattern("RRR")
                .pattern("RRR")
                .input('R', tag)
                .criterion(hasTag(tag), scRecipe.conditionsFromTag(tag))
                .group(group)
                .offerTo(exporter);

        // ARMORS
        if(container.helmet() != null) {
            SMELT_NUGGET_ITEMS.add(container.helmet());
            scRecipe.createShaped(RecipeCategory.COMBAT, container.helmet())
                    .pattern("RRR")
                    .pattern("R R")
                    .input('R', tag)
                    .criterion(hasTag(tag), scRecipe.conditionsFromTag(tag))
                    .group(group)
                    .offerTo(exporter);
        }
        if(container.chesplate() != null) {
            SMELT_NUGGET_ITEMS.add(container.chesplate());
            scRecipe.createShaped(RecipeCategory.COMBAT, container.chesplate())
                    .pattern("R R")
                    .pattern("RRR")
                    .pattern("RRR")
                    .input('R', tag)
                    .criterion(hasTag(tag), scRecipe.conditionsFromTag(tag))
                    .group(group)
                    .offerTo(exporter);
        }
        if(container.leggings() != null) {
            SMELT_NUGGET_ITEMS.add(container.leggings());
            scRecipe.createShaped(RecipeCategory.COMBAT, container.leggings())
                    .pattern("RRR")
                    .pattern("R R")
                    .pattern("R R")
                    .input('R', tag)
                    .criterion(hasTag(tag), scRecipe.conditionsFromTag(tag))
                    .group(group)
                    .offerTo(exporter);
        }
        if(container.boots() != null) {
            SMELT_NUGGET_ITEMS.add(container.boots());
            scRecipe.createShaped(RecipeCategory.COMBAT, container.boots())
                    .pattern("R R")
                    .pattern("R R")
                    .input('R', tag)
                    .criterion(hasTag(tag), scRecipe.conditionsFromTag(tag))
                    .group(group)
                    .offerTo(exporter);
        }

        if(container.nugget() != null) {
            scRecipe.createShapeless(RecipeCategory.MISC, container.nugget(), 9)
                    .input(tag)
                    .criterion(hasTag(tag), scRecipe.conditionsFromTag(tag))
                    .offerTo(exporter, SimpleOres.MOD_ID + ":" + getTagName(tag) + "_to_nugget");

            scRecipe.createShaped(RecipeCategory.MISC, baseItem)
                    .pattern("RRR")
                    .pattern("RRR")
                    .pattern("RRR")
                    .input('R', container.nugget())
                    .criterion(hasItem(container.nugget()), scRecipe.conditionsFromItem(container.nugget()))
                    .offerTo(exporter, SimpleOres.MOD_ID + ":" + getRecipeName(container.nugget()) + "_to_ingot");
        }

        if(container.nugget() != null && !SMELT_NUGGET_ITEMS.isEmpty()) {
            // Add equipment to nugget smelting recipe
            scRecipe.offerSmelting(
                    SMELT_NUGGET_ITEMS,
                    RecipeCategory.MISC,
                    container.nugget(),
                    0.1F,
                    200,
                    "smelting_nugget_" + container.nugget().asItem().getTranslationKey()
            );

            // Add equipment to nugget blasting recipe
            scRecipe.offerBlasting(
                    SMELT_NUGGET_ITEMS,
                    RecipeCategory.MISC,
                    container.nugget(),
                    0.1F,
                    200,
                    "blasting_nugget_" + container.nugget().asItem().getTranslationKey()
            );
        }

        if(!container.excludeSmeltCreation() && container.smeltXp() != null) {
            List<TagKey<Item>> SMELTABLES = new ArrayList<>();

            if(container.ores() != null) SMELTABLES.add(container.ores());
            if(container.raw() != null) SMELTABLES.add(container.raw());

            if(!SMELTABLES.isEmpty()) {
                for (TagKey<Item> smeltable : SMELTABLES) {
                    scRecipe.offerSmelting(smeltable, RecipeCategory.MISC, baseItem,
                            container.smeltXp(), 200, group);
                    scRecipe.offerBlasting(smeltable, RecipeCategory.MISC, baseItem,
                            container.smeltXp(), 100, group);
                }
            }
        }
    }

    private static void createShearsRecipe(ItemConvertible output, TagKey<Item> tag, RecipeExporter exporter, String group) {
        scRecipe.createShaped(RecipeCategory.TOOLS, output)
                .pattern(" R")
                .pattern("R ")
                .input('R', tag)
                .criterion(hasTag(tag), scRecipe.conditionsFromTag(tag))
                .group(group)
                .offerTo(exporter);
    }

    public static void createHoeRecipe(ItemConvertible output, TagKey<Item> tag, RecipeExporter exporter, String group){
        scRecipe.createShaped(RecipeCategory.COMBAT, output)
                .pattern("RR")
                .pattern("S ")
                .pattern("S ")
                .input('R', tag)
                .input('S', Items.STICK)
                .criterion(hasTag(tag), scRecipe.conditionsFromTag(tag))
                .criterion(hasItem(Items.STICK), scRecipe.conditionsFromItem(Items.STICK))
                .group(group)
                .offerTo(exporter);

        scRecipe.createShaped(RecipeCategory.COMBAT, output)
                .pattern("RR")
                .pattern(" S")
                .pattern(" S")
                .input('R', tag)
                .input('S', Items.STICK)
                .criterion(hasTag(tag), scRecipe.conditionsFromTag(tag))
                .criterion(hasItem(Items.STICK), scRecipe.conditionsFromItem(Items.STICK))
                .group(group)
                .offerTo(exporter, SimpleOres.MOD_ID + ":" + getRecipeName(output) + "_inverted");
    }

    public static void createShovelRecipe(ItemConvertible output, TagKey<Item> tag, RecipeExporter exporter, String group){
        scRecipe.createShaped(RecipeCategory.COMBAT, output)
                .pattern("R")
                .pattern("S")
                .pattern("S")
                .input('R', tag)
                .input('S', Items.STICK)
                .criterion(hasTag(tag), scRecipe.conditionsFromTag(tag))
                .criterion(hasItem(Items.STICK), scRecipe.conditionsFromItem(Items.STICK))
                .group(group)
                .offerTo(exporter);
    }

    public static void createPickaxeRecipe(ItemConvertible output, TagKey<Item> tag, RecipeExporter exporter, String group){
        scRecipe.createShaped(RecipeCategory.COMBAT, output)
                .pattern("RRR")
                .pattern(" S ")
                .pattern(" S ")
                .input('R', tag)
                .input('S', Items.STICK)
                .criterion(hasTag(tag), scRecipe.conditionsFromTag(tag))
                .criterion(hasItem(Items.STICK), scRecipe.conditionsFromItem(Items.STICK))
                .group(group)
                .offerTo(exporter);
    }

    public static void createAxeRecipe(ItemConvertible output, TagKey<Item> tag, RecipeExporter exporter, String group){
        scRecipe.createShaped(RecipeCategory.COMBAT, output)
                .pattern("RR")
                .pattern("SR")
                .pattern("S ")
                .input('R', tag)
                .input('S', Items.STICK)
                .criterion(hasTag(tag), scRecipe.conditionsFromTag(tag))
                .criterion(hasItem(Items.STICK), scRecipe.conditionsFromItem(Items.STICK))
                .group(group)
                .offerTo(exporter);

        scRecipe.createShaped(RecipeCategory.COMBAT, output)
                .pattern("RR")
                .pattern("RS")
                .pattern(" S")
                .input('R', tag)
                .input('S', Items.STICK)
                .criterion(hasTag(tag), scRecipe.conditionsFromTag(tag))
                .criterion(hasItem(Items.STICK), scRecipe.conditionsFromItem(Items.STICK))
                .group(group)
                .offerTo(exporter, SimpleOres.MOD_ID + ":" + getRecipeName(output) + "_inverted");
    }

    public static void createSwordRecipe(ItemConvertible output, TagKey<Item> tag, RecipeExporter exporter, String group){
        scRecipe.createShaped(RecipeCategory.COMBAT, output)
                .pattern("R")
                .pattern("R")
                .pattern("S")
                .input('R', tag)
                .input('S', Items.STICK)
                .criterion(hasTag(tag), scRecipe.conditionsFromTag(tag))
                .criterion(hasItem(Items.STICK), scRecipe.conditionsFromItem(Items.STICK))
                .group(group)
                .offerTo(exporter);
    }

    public record SCRecipe(FabricRecipeProvider provider, //$ generatorOrExporter
            net.minecraft.data.recipe.RecipeGenerator
            generator, RecipeExporter exporter) {

        public ShapedRecipeJsonBuilder createShaped(RecipeCategory category, ItemConvertible output) {
            return createShaped(category, output, 1);
        }

        public ShapedRecipeJsonBuilder createShaped(RecipeCategory category, ItemConvertible output, int count) {
            //? >=1.21.3 {
            return generator().createShaped(category, output, count);
            //?} else {
            /*return ShapedRecipeJsonBuilder.create(category, output, count);
            *///?}
        }


        public
        //? >1.20.1 {
        AdvancementCriterion<?>
        //?} else {
        /*InventoryChangedCriterion.Conditions
        *///?}
        conditionsFromItem(ItemConvertible item) {
            //? >=1.21.3 {
            return generator().conditionsFromItem(item);
            //?} else {
            /*return provider().conditionsFromItem(item);
            *///?}
        }

        public
            //? >1.20.1 {
        AdvancementCriterion<?>
        //?} else {
        /*InventoryChangedCriterion.Conditions
        *///?}
        conditionsFromTag(TagKey<Item> tag) {
            //? >=1.21.3 {
            return generator().conditionsFromTag(tag);
            //?} else {
            /*return provider().conditionsFromTag(tag);
            *///?}
        }

        public void offerSmelting(List<ItemConvertible> inputs, RecipeCategory category, ItemConvertible output, float experience, int cookingTime, String group) {
            //? >=1.21.3 {
            generator().offerSmelting(inputs, category, output, experience, cookingTime, group);
            //?} else {
            /*provider().offerSmelting(exporter(), inputs, category, output, experience, cookingTime, group);
            *///?}
        }

        public void offerBlasting(List<ItemConvertible> inputs, RecipeCategory category, ItemConvertible output, float experience, int cookingTime, String group) {
            //? >=1.21.3 {
            generator().offerBlasting(inputs, category, output, experience, cookingTime, group);
            //?} else {
            /*provider().offerBlasting(exporter(), inputs, category, output, experience, cookingTime, group);
            *///?}
        }

        public void offerReversibleCompactingRecipes(
                RecipeCategory reverseCategory, ItemConvertible packedItem, ItemConvertible unpackedItem,
                TagKey<Item> unpackedInput, RecipeCategory compactingCategory
        ) {
            createShaped(compactingCategory, packedItem)
                .pattern("RRR")
                .pattern("RRR")
                .pattern("RRR")
                .input('R', unpackedInput)
                .criterion(hasTag(unpackedInput), conditionsFromTag(unpackedInput))
                .offerTo(exporter(), //? if >1.21
                        RegistryKey.of(RegistryKeys.RECIPE,
                                SCIdentifier.of(getTagName(unpackedInput) + "_to_" + getItemPath(packedItem))
                        //? if >1.21
                        )
                );

            createShapeless(reverseCategory, unpackedItem, 9)
                    .input(packedItem)
                    .criterion(hasItem(packedItem), conditionsFromItem(packedItem))
                    .offerTo(exporter(), //? if >1.21
                            RegistryKey.of(RegistryKeys.RECIPE,
                                    SCIdentifier.of(getItemPath(packedItem) + "_to_" + getItemPath(unpackedItem))
                            //? if >1.21
                            )
                    );
        }

        public ShapelessRecipeJsonBuilder createShapeless(RecipeCategory category, ItemConvertible output) {
            return createShapeless(category, output, 1);
        }

        public ShapelessRecipeJsonBuilder createShapeless(RecipeCategory category, ItemConvertible output, int count) {
            //? >=1.21.3 {
            return generator().createShapeless(category, output, count);
            //?} else {
            /*return ShapelessRecipeJsonBuilder.create(category, output, count);
            *///?}
        }

        public void offerStonecuttingRecipe(RecipeCategory category, ItemConvertible output, TagKey<Item> input) {
            offerStonecuttingRecipe(category, output, input, 1);
        }

        public void offerStonecuttingRecipe(RecipeCategory category, ItemConvertible output, TagKey<Item> input, int count) {
            //? >=1.21 {
            StonecuttingRecipeJsonBuilder.createStonecutting(ingredientFromTag(input), category, output, count)
                    .criterion(hasTag(input), conditionsFromTag(input))
                    .offerTo(this.exporter, getItemPath(output) + "_from_" + getTagName(input) + "_stonecutting");
            //?} else {
            
            /*SingleItemRecipeJsonBuilder.createStonecutting(Ingredient.fromTag(input), category, output, count)
                .criterion(hasTag(input), conditionsFromTag(input))
                .offerTo(generator(), getItemPath(output) + "_from_" + getTagName(input) + "_stonecutting");
             
            *///?}
        }

        public void offerStonecuttingRecipe(RecipeCategory category, ItemConvertible output, ItemConvertible input) {
            offerStonecuttingRecipe(category, output, input, 1);
        }

        public void offerStonecuttingRecipe(RecipeCategory category, ItemConvertible output, ItemConvertible input, int count) {
            //? >=1.21 {
            StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(input), category, output, count)
                    .criterion(hasItem(input), conditionsFromItem(input))
                    .offerTo(this.exporter, getItemPath(output) + "_from_" + getItemPath(input) + "_stonecutting");
            //?} else {
            
            /*SingleItemRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(input), category, output, count)
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(generator(), getItemPath(output) + "_from_" + getItemPath(input) + "_stonecutting");
             
            *///?}
        }

        public CraftingRecipeJsonBuilder createDoorRecipe(ItemConvertible output, Ingredient input) {
            return createShaped(RecipeCategory.REDSTONE, output, 3)
                    .input('#', input)
                    .pattern("##")
                    .pattern("##")
                    .pattern("##");
        }

        public void offerSmelting(TagKey<Item> tag, RecipeCategory category, ItemConvertible output, float experience, int cookingTime, String group
        ) {
            offerMultipleOptions(RecipeSerializer.SMELTING, //? if >1.20.1
                    SmeltingRecipe::new,
                    tag, category, output, experience, cookingTime, group, "_from_smelting");
        }

        public void offerBlasting(TagKey<Item> tag, RecipeCategory category, ItemConvertible output, float experience, int cookingTime, String group
        ) {
            offerMultipleOptions(RecipeSerializer.BLASTING, //? if >1.20.1
                    BlastingRecipe::new,
                    tag, category, output, experience, cookingTime, group, "_from_blasting");
        }

        public<T extends AbstractCookingRecipe> void offerMultipleOptions(
                RecipeSerializer<T> serializer,
                //? if >1.20.1
                AbstractCookingRecipe.RecipeFactory<T> recipeFactory,
                TagKey<Item> tag,
                RecipeCategory category,
                ItemConvertible output,
                float experience,
                int cookingTime,
                String group,
                String suffix
        ) {
            CookingRecipeJsonBuilder.create(ingredientFromTag(tag), category, output, experience, cookingTime, serializer //? if >1.20.1
                    , recipeFactory
            )
                    .group(group)
                    .criterion(hasTag(tag), conditionsFromTag(tag))
                    .offerTo(exporter, getItemPath(output) + suffix + "_" + getTagName(tag));
        }

        public Ingredient ingredientFromTag(TagKey<Item> tag) {
            //? >=1.21.3 {
            return generator().ingredientFromTag(tag);
            //?} else {
            /*return Ingredient.fromTag(tag);
            *///?}
        }
    }

    public static String hasTag(TagKey<Item> tag) {
        return "has_" + getTagName(tag);
    }

    public static String getItemPath(ItemConvertible item) {
        return Registries.ITEM.getId(item.asItem()).getPath();
    }

    public static String getTagName(TagKey<Item> tag) {
        return tag.id().getPath();
    }
}