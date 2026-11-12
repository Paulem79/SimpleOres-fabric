package net.paulem.simpleores.datagen.providers;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.paulem.simpleores.SimpleOres;
import net.paulem.simpleores.armors.MaterialRecipeContainer;
import net.paulem.simpleores.blocks.ModBlocks;
import net.paulem.simpleores.furnaces.ModFurnaceBlock;
import net.paulem.simpleores.furnaces.ModFurnaces;
import net.paulem.simpleores.items.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.paulem.sclib.datagen.SCRecipeProvider;
import net.paulem.sclib.datagen.SCRecipes;
import net.paulem.sclib.SCId;
import net.paulem.simpleores.tags.ModTags;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import net.paulem.simpleores.utils.MaterialUtils;
import net.paulem.simpleores.utils.MapUtils;



//? if <=1.20.1
//import net.minecraft.advancements.triggers.InventoryChangeTrigger;
//? if >1.21
import static net.minecraft.data.recipes.RecipeProvider.*;

public class ModRecipeProvider extends SCRecipeProvider {
    public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup, SimpleOres.MOD_ID, "Simple Ores Recipes");
    }

    private static SCRecipes scRecipe;

    @Override
    protected void buildRecipes(SCRecipes recipes) {
        scRecipe = recipes;
        RecipeOutput exporter = recipes.exporter();

        // No cake recipe here: the custom bucket is accepted by the vanilla one thanks to IngredientMixin

        offerDustFurnace(ModTags.Items.Conventional.COPPER_DUSTS, Items.COPPER_INGOT);
        offerDustFurnace(ModTags.Items.Conventional.TIN_DUSTS, ModItems.TIN_INGOT);
        offerDustFurnace(ModTags.Items.Conventional.MYTHRIL_DUSTS, ModItems.MYTHRIL_INGOT);
        offerDustFurnace(ModTags.Items.Conventional.ADAMANTIUM_DUSTS, ModItems.ADAMANTIUM_INGOT);

        offerCrushedOreFurnace(ModItems.CRUSHED_COPPER_ORE, Items.COPPER_INGOT);
        offerCrushedOreFurnace(ModItems.CRUSHED_TIN_ORE, ModItems.TIN_INGOT);
        offerCrushedOreFurnace(ModItems.CRUSHED_MYTHRIL_ORE, ModItems.MYTHRIL_INGOT);
        offerCrushedOreFurnace(ModItems.CRUSHED_ADAMANTIUM_ORE, ModItems.ADAMANTIUM_INGOT);

        scRecipe.shaped(RecipeCategory.MISC, ModItems.MYTHRIL_BOW)
                .pattern(" RS")
                .pattern("F S")
                .pattern(" RS")
                .define('S', Items.STRING)
                .define('F', Items.IRON_INGOT)
                .define('R', ModItems.MYTHRIL_ROD)
                .unlockedBy("has_string", scRecipe.has(Items.STRING))
                .save(exporter);

        scRecipe.shaped(RecipeCategory.MISC, ModItems.MYTHRIL_ROD)
                .pattern("R")
                .pattern("R")
                .define('R', ModItems.MYTHRIL_INGOT)
                .unlockedBy(getHasName(ModItems.MYTHRIL_INGOT), scRecipe.has(ModItems.MYTHRIL_INGOT))
                .save(exporter);

        scRecipe.shaped(RecipeCategory.MISC, ModItems.ONYX_BOW)
                .pattern(" RS")
                .pattern("F S")
                .pattern(" RS")
                .define('S', Items.STRING)
                .define('F', Items.IRON_INGOT)
                .define('R', ModItems.ONYX_ROD)
                .unlockedBy("has_string", scRecipe.has(Items.STRING))
                .save(exporter);

        scRecipe.shaped(RecipeCategory.MISC, ModItems.ONYX_ROD)
                .pattern("R")
                .pattern("R")
                .define('R', ModItems.ONYX_GEM)
                .unlockedBy(getHasName(ModItems.ONYX_GEM), scRecipe.has(ModItems.ONYX_GEM))
                .save(exporter);

        // Copper
        createMaterialSetRecipes(ConventionalItemTags.COPPER_INGOTS, Items.COPPER_INGOT, exporter, new MaterialRecipeContainer(
                //? if !hasCopperTools {
                /*ModItems.COPPER_SWORD, ModItems.COPPER_PICKAXE, ModItems.COPPER_AXE, ModItems.COPPER_SHOVEL, ModItems.COPPER_HOE, ModItems.COPPER_HELMET,
                ModItems.COPPER_CHESTPLATE, ModItems.COPPER_LEGGINGS, ModItems.COPPER_BOOTS,
                *///?} else {
                null, null, null, null, null, null, null, null, null,
                //?}
                ModItems.COPPER_SHEARS,
                null,
                ModTags.Items.Conventional.COPPER_ORES, Blocks.COPPER_BLOCK //? if >=26.2
                        .weathering().unaffected()
                , Blocks.RAW_COPPER_BLOCK, ModTags.Items.Conventional.RAW_COPPER_ORES, Items.RAW_COPPER,
                //? if hasCopperTools {
                Items.COPPER_NUGGET,
                //?} else {
                /*ModItems.COPPER_NUGGET,
                *///?}
                null,
                //? !hasCopperTools {
                /*ModBlocks.copper_bars,
                *///?} else {
                null,
                //?}
                ModBlocks.copper_pressure_plate, null, null, null,
                //? containsBucket
                ModItems.COPPER_BUCKET,
                null, true
        ));

        // Tin
        createMaterialSetRecipes(ModTags.Items.Conventional.TIN_INGOTS, ModItems.TIN_INGOT, exporter, new MaterialRecipeContainer(
                ModItems.TIN_SWORD, ModItems.TIN_PICKAXE, ModItems.TIN_AXE, ModItems.TIN_SHOVEL, ModItems.TIN_HOE,
                ModItems.TIN_HELMET, ModItems.TIN_CHESTPLATE, ModItems.TIN_LEGGINGS, ModItems.TIN_BOOTS, ModItems.TIN_SHEARS,
                //? if >=1.21.11 {
                ModItems.TIN_SPEAR,
                //?} else {
                /*null,
                *///?}
                ModTags.Items.Conventional.TIN_ORES, ModBlocks.TIN_BLOCK, ModBlocks.RAW_TIN_BLOCK, ModTags.Items.Conventional.RAW_TIN_ORES, ModItems.RAW_TIN, ModItems.TIN_NUGGET,
                ModBlocks.tin_door, ModBlocks.tin_bars, ModBlocks.tin_pressure_plate, ModBlocks.TIN_BRICKS, ModBlocks.TIN_BRICK_SLAB, ModBlocks.tin_brick_stairs,
                //? containsBucket
                null,
                0.4f, false
        ));

        // Mythril
        createMaterialSetRecipes(ModTags.Items.Conventional.MYTHRIL_INGOTS, ModItems.MYTHRIL_INGOT, exporter, new MaterialRecipeContainer(
                ModItems.MYTHRIL_SWORD, ModItems.MYTHRIL_PICKAXE, ModItems.MYTHRIL_AXE, ModItems.MYTHRIL_SHOVEL, ModItems.MYTHRIL_HOE,
                ModItems.MYTHRIL_HELMET, ModItems.MYTHRIL_CHESTPLATE, ModItems.MYTHRIL_LEGGINGS, ModItems.MYTHRIL_BOOTS, ModItems.MYTHRIL_SHEARS,
                //? if >=1.21.11 {
                ModItems.MYTHRIL_SPEAR,
                //?} else {
                /*null,
                *///?}
                ModTags.Items.Conventional.MYTHRIL_ORES, ModBlocks.MYTHRIL_BLOCK, ModBlocks.RAW_MYTHRIL_BLOCK, ModTags.Items.Conventional.RAW_MYTHRIL_ORES, ModItems.RAW_MYTHRIL, ModItems.MYTHRIL_NUGGET,
                ModBlocks.mythril_door, ModBlocks.mythril_bars, ModBlocks.mythril_pressure_plate, ModBlocks.MYTHRIL_BRICKS, ModBlocks.MYTHRIL_BRICK_SLAB, ModBlocks.mythril_brick_stairs,
                //? containsBucket
                null,
                0.7f, false
        ));

        // Adamantium
        createMaterialSetRecipes(ModTags.Items.Conventional.ADAMANTIUM_INGOTS, ModItems.ADAMANTIUM_INGOT, exporter, new MaterialRecipeContainer(
                ModItems.ADAMANTIUM_SWORD, ModItems.ADAMANTIUM_PICKAXE, ModItems.ADAMANTIUM_AXE, ModItems.ADAMANTIUM_SHOVEL, ModItems.ADAMANTIUM_HOE,
                ModItems.ADAMANTIUM_HELMET, ModItems.ADAMANTIUM_CHESTPLATE, ModItems.ADAMANTIUM_LEGGINGS, ModItems.ADAMANTIUM_BOOTS, ModItems.ADAMANTIUM_SHEARS,
                //? if >=1.21.11 {
                ModItems.ADAMANTIUM_SPEAR,
                //?} else {
                /*null,
                *///?}
                ModTags.Items.Conventional.ADAMANTIUM_ORES, ModBlocks.ADAMANTIUM_BLOCK, ModBlocks.RAW_ADAMANTIUM_BLOCK, ModTags.Items.Conventional.RAW_ADAMANTIUM_ORES, ModItems.RAW_ADAMANTIUM, ModItems.ADAMANTIUM_NUGGET,
                ModBlocks.adamantium_door, ModBlocks.adamantium_bars, ModBlocks.adamantium_pressure_plate, ModBlocks.ADAMANTIUM_BRICKS, ModBlocks.ADAMANTIUM_BRICK_SLAB, ModBlocks.adamantium_brick_stairs,
                //? containsBucket
                null,
                0.7f, false
        ));

        // Onyx
        createMaterialSetRecipes(ModTags.Items.Conventional.ONYX_GEMS, ModItems.ONYX_GEM, exporter, new MaterialRecipeContainer(
                ModItems.ONYX_SWORD, ModItems.ONYX_PICKAXE, ModItems.ONYX_AXE, ModItems.ONYX_SHOVEL, ModItems.ONYX_HOE,
                ModItems.ONYX_HELMET, ModItems.ONYX_CHESTPLATE, ModItems.ONYX_LEGGINGS, ModItems.ONYX_BOOTS, ModItems.ONYX_SHEARS,
                //? if >=1.21.11 {
                ModItems.ONYX_SPEAR,
                //?} else {
                /*null,
                *///?}
                ModTags.Items.Conventional.ONYX_ORES, ModBlocks.ONYX_BLOCK, null, null, null, null,
                ModBlocks.onyx_door, ModBlocks.onyx_bars, ModBlocks.onyx_pressure_plate, ModBlocks.ONYX_BRICKS, ModBlocks.ONYX_BRICK_SLAB, ModBlocks.onyx_brick_stairs,
                //? containsBucket
                null,
                1f, false
        ));

        for (ModFurnaceBlock furnace : ModFurnaces.getFurnaces()) {
            //$ armorRegistry
            net.minecraft.world.item.equipment.@org.jspecify.annotations.Nullable ArmorMaterial
                    material = MapUtils.keys(ModFurnaces.FURNACES, furnace.getSpeedModifier())
                    .findFirst()
                    .orElse(null);

            String materialName = MaterialUtils.getName(material);
            if(materialName == null) continue;

            Item materialItem = MaterialUtils.getMaterialItem(materialName);
            if(materialItem == null) continue;

            scRecipe.shaped(RecipeCategory.DECORATIONS, furnace.asItem())
                    .pattern("MMM")
                    .pattern("MFM")
                    .pattern("MMM")
                    .define('M', materialItem)
                    .define('F', Items.FURNACE)
                    .unlockedBy(getHasName(Items.FURNACE), scRecipe.has(Items.FURNACE))
                    .save(exporter);
        }
    }

    private static void offerCrushedOreFurnace(ItemLike crushedOre, ItemLike output) {
        scRecipe.oreSmelting(List.of(crushedOre), RecipeCategory.MISC, output,
                0.7f, 200, null);
        scRecipe.oreBlasting(List.of(crushedOre), RecipeCategory.MISC, output,
                0.7f, 100, null);
    }

    private static void offerDustFurnace(TagKey<Item> dustTag, ItemLike output) {
        scRecipe.oreSmelting(dustTag, RecipeCategory.MISC, output,
                0.7f, 200, null);
        scRecipe.oreBlasting(dustTag, RecipeCategory.MISC, output,
                0.7f, 100, null);
    }

    public static void createMaterialSetRecipes(TagKey<Item> tag, ItemLike baseItem, RecipeOutput exporter, MaterialRecipeContainer container) {
        List<ItemLike> SMELT_NUGGET_ITEMS = new ArrayList<>();

        // TOOLS
        if(container.sword() != null) {
            SMELT_NUGGET_ITEMS.add(container.sword());
            createSwordRecipe(container.sword(), tag, exporter);
        }
        if(container.pickaxe() != null) {
            SMELT_NUGGET_ITEMS.add(container.pickaxe());
            createPickaxeRecipe(container.pickaxe(), tag, exporter);
        }
        if(container.axe() != null) {
            SMELT_NUGGET_ITEMS.add(container.axe());
            createAxeRecipe(container.axe(), tag, exporter);
        }
        if(container.shovel() != null) {
            SMELT_NUGGET_ITEMS.add(container.shovel());
            createShovelRecipe(container.shovel(), tag, exporter);
        }
        if(container.hoe() != null) {
            SMELT_NUGGET_ITEMS.add(container.hoe());
            createHoeRecipe(container.hoe(), tag, exporter);
        }
        if(container.shears() != null) {
            createShearsRecipe(container.shears(), tag, exporter);
        }
        if(container.spear() != null) {
            createSpearRecipe(container.spear(), tag, exporter);
        }
        //? containsBucket {
        if(container.bucket() != null) {
            SMELT_NUGGET_ITEMS.add(container.bucket());
            scRecipe.shaped(RecipeCategory.MISC, container.bucket())
                    .pattern("R R")
                    .pattern(" R ")
                    .define('R', tag)
                    .unlockedBy(hasTag(tag), scRecipe.has(tag))
                    .save(exporter);
        }
        //?}

        if(container.block() != null) scRecipe.reversibleCompacting(RecipeCategory.BUILDING_BLOCKS, container.block(), baseItem, tag,
                RecipeCategory.DECORATIONS);
        if(container.raw() != null && container.rawBlock() != null) scRecipe.reversibleCompacting(RecipeCategory.BUILDING_BLOCKS, container.rawBlock(), container.baseRawItem(), container.raw(), RecipeCategory.DECORATIONS);

        if(container.pressurePlate() != null) {
            scRecipe.shaped(RecipeCategory.REDSTONE, container.pressurePlate())
                    .pattern("RR")
                    .define('R', tag)
                    .unlockedBy(hasTag(tag), scRecipe.has(tag))
                    .save(exporter);
        }

        if(container.cut() != null) {
            scRecipe.shaped(RecipeCategory.BUILDING_BLOCKS, container.cut(), 4)
                    .pattern("RR")
                    .pattern("RR")
                    .define('R', tag)
                    .unlockedBy(hasTag(tag), scRecipe.has(tag))
                    .save(exporter);

            scRecipe.stonecutting(RecipeCategory.BUILDING_BLOCKS, container.cut(), tag);
            // END ingot to cut block

            if(container.cutSlab() != null) {
                scRecipe.shaped(RecipeCategory.BUILDING_BLOCKS, container.cutSlab(), 6)
                        .pattern("RRR")
                        .define('R', container.cut())
                        .unlockedBy(hasTag(tag), scRecipe.has(tag))
                        .save(exporter);

                scRecipe.stonecutting(RecipeCategory.BUILDING_BLOCKS, container.cutSlab(), container.cut(), 2);
                // END cut block to cut slabs
            }

            if(container.stairs() != null) {
                scRecipe.shaped(RecipeCategory.BUILDING_BLOCKS, container.stairs(), 4)
                        .pattern("R  ")
                        .pattern("RR ")
                        .pattern("RRR")
                        .define('R', container.cut())
                        .unlockedBy(hasTag(tag), scRecipe.has(tag))
                        .save(exporter);

                scRecipe.stonecutting(RecipeCategory.BUILDING_BLOCKS, container.stairs(), container.cut());
                // END cut block to cut stairs
            }
        }

        if(container.door() != null) {
            scRecipe.door(container.door(), scRecipe.ingredient(tag))
                    .unlockedBy(hasTag(tag), scRecipe.has(tag))
                    .save(exporter);
        }
        
        if(container.bars() != null) {
            scRecipe.shaped(RecipeCategory.BUILDING_BLOCKS, container.bars(), 16)
                    .pattern("RRR")
                    .pattern("RRR")
                    .define('R', tag)
                    .unlockedBy(hasTag(tag), scRecipe.has(tag))
                    .save(exporter);
        }

        // ARMORS
        if(container.helmet() != null) {
            SMELT_NUGGET_ITEMS.add(container.helmet());
            scRecipe.shaped(RecipeCategory.COMBAT, container.helmet())
                    .pattern("RRR")
                    .pattern("R R")
                    .define('R', tag)
                    .unlockedBy(hasTag(tag), scRecipe.has(tag))
                    .save(exporter);
        }
        if(container.chesplate() != null) {
            SMELT_NUGGET_ITEMS.add(container.chesplate());
            scRecipe.shaped(RecipeCategory.COMBAT, container.chesplate())
                    .pattern("R R")
                    .pattern("RRR")
                    .pattern("RRR")
                    .define('R', tag)
                    .unlockedBy(hasTag(tag), scRecipe.has(tag))
                    .save(exporter);
        }
        if(container.leggings() != null) {
            SMELT_NUGGET_ITEMS.add(container.leggings());
            scRecipe.shaped(RecipeCategory.COMBAT, container.leggings())
                    .pattern("RRR")
                    .pattern("R R")
                    .pattern("R R")
                    .define('R', tag)
                    .unlockedBy(hasTag(tag), scRecipe.has(tag))
                    .save(exporter);
        }
        if(container.boots() != null) {
            SMELT_NUGGET_ITEMS.add(container.boots());
            scRecipe.shaped(RecipeCategory.COMBAT, container.boots())
                    .pattern("R R")
                    .pattern("R R")
                    .define('R', tag)
                    .unlockedBy(hasTag(tag), scRecipe.has(tag))
                    .save(exporter);
        }

        // The vanilla copper nugget already comes with its own ingot conversions
        boolean vanillaNugget = false;
        //? if hasCopperTools
        vanillaNugget = container.nugget() == Items.COPPER_NUGGET;

        if(container.nugget() != null && !vanillaNugget) {
            scRecipe.shapeless(RecipeCategory.MISC, container.nugget(), 9)
                    .requires(tag)
                    .unlockedBy(hasTag(tag), scRecipe.has(tag))
                    .save(exporter, SimpleOres.MOD_ID + ":" + getTagName(tag) + "_to_nugget");

            scRecipe.shaped(RecipeCategory.MISC, baseItem)
                    .pattern("RRR")
                    .pattern("RRR")
                    .pattern("RRR")
                    .define('R', container.nugget())
                    .unlockedBy(getHasName(container.nugget()), scRecipe.has(container.nugget()))
                    .save(exporter, SimpleOres.MOD_ID + ":" + getSimpleRecipeName(container.nugget()) + "_to_ingot");
        }

        if(container.nugget() != null && !SMELT_NUGGET_ITEMS.isEmpty()) {
            // Add equipment to nugget smelting recipe
            scRecipe.oreSmelting(
                    SMELT_NUGGET_ITEMS,
                    RecipeCategory.MISC,
                    container.nugget(),
                    0.1F,
                    200,
                    "smelting_nugget_" + container.nugget().asItem().getDescriptionId()
            );

            // Add equipment to nugget blasting recipe
            scRecipe.oreBlasting(
                    SMELT_NUGGET_ITEMS,
                    RecipeCategory.MISC,
                    container.nugget(),
                    0.1F,
                    200,
                    "blasting_nugget_" + container.nugget().asItem().getDescriptionId()
            );
        }

        if(!container.excludeSmeltCreation() && container.smeltXp() != null) {
            List<TagKey<Item>> SMELTABLES = new ArrayList<>();

            if(container.ores() != null) SMELTABLES.add(container.ores());
            if(container.raw() != null) SMELTABLES.add(container.raw());

            if(!SMELTABLES.isEmpty()) {
                for (TagKey<Item> smeltable : SMELTABLES) {
                    scRecipe.oreSmelting(smeltable, RecipeCategory.MISC, baseItem,
                            container.smeltXp(), 200, null);
                    scRecipe.oreBlasting(smeltable, RecipeCategory.MISC, baseItem,
                            container.smeltXp(), 100, null);
                }
            }
        }
    }

    private static void createShearsRecipe(ItemLike output, TagKey<Item> tag, RecipeOutput exporter) {
        scRecipe.shaped(RecipeCategory.TOOLS, output)
                .pattern(" R")
                .pattern("R ")
                .define('R', tag)
                .unlockedBy(hasTag(tag), scRecipe.has(tag))
                .save(exporter);
    }

    private static void createSpearRecipe(ItemLike output, TagKey<Item> tag, RecipeOutput exporter) {
        scRecipe.shaped(RecipeCategory.COMBAT, output)
                .pattern("  X")
                .pattern(" # ")
                .pattern("#  ")
                .define('#', Items.STICK)
                .define('X', tag)
                .unlockedBy(hasTag(tag), scRecipe.has(tag))
                .save(exporter);
    }

    public static void createHoeRecipe(ItemLike output, TagKey<Item> tag, RecipeOutput exporter) {
        scRecipe.shaped(RecipeCategory.COMBAT, output)
                .pattern("XX")
                .pattern(" #")
                .pattern(" #")
                .define('X', tag)
                .define('#', Items.STICK)
                .unlockedBy(hasTag(tag), scRecipe.has(tag))
                .save(exporter);
    }

    public static void createShovelRecipe(ItemLike output, TagKey<Item> tag, RecipeOutput exporter) {
        scRecipe.shaped(RecipeCategory.COMBAT, output)
                .pattern("R")
                .pattern("S")
                .pattern("S")
                .define('R', tag)
                .define('S', Items.STICK)
                .unlockedBy(hasTag(tag), scRecipe.has(tag))
                .save(exporter);
    }

    public static void createPickaxeRecipe(ItemLike output, TagKey<Item> tag, RecipeOutput exporter) {
        scRecipe.shaped(RecipeCategory.COMBAT, output)
                .pattern("RRR")
                .pattern(" S ")
                .pattern(" S ")
                .define('R', tag)
                .define('S', Items.STICK)
                .unlockedBy(hasTag(tag), scRecipe.has(tag))
                .save(exporter);
    }

    public static void createAxeRecipe(ItemLike output, TagKey<Item> tag, RecipeOutput exporter) {
        scRecipe.shaped(RecipeCategory.COMBAT, output)
                .pattern("XX")
                .pattern("X#")
                .pattern(" #")
                .define('X', tag)
                .define('#', Items.STICK)
                .unlockedBy(hasTag(tag), scRecipe.has(tag))
                .save(exporter);
    }

    public static void createSwordRecipe(ItemLike output, TagKey<Item> tag, RecipeOutput exporter) {
        scRecipe.shaped(RecipeCategory.COMBAT, output)
                .pattern("R")
                .pattern("R")
                .pattern("S")
                .define('R', tag)
                .define('S', Items.STICK)
                .unlockedBy(hasTag(tag), scRecipe.has(tag))
                .save(exporter);
    }

    public static String hasTag(TagKey<Item> tag) {
        return "has_" + getTagName(tag);
    }

    public static String getItemPath(ItemLike item) {
        return BuiltInRegistries.ITEM.getKey(item.asItem()).getPath();
    }

    public static String getTagName(TagKey<Item> tag) {
        return tag.location().getPath();
    }
}
