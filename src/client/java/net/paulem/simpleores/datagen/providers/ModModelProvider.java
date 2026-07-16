package net.paulem.simpleores.datagen.providers;

//? if containsBucket && !hasBucketlib {
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.renderer.item.ItemModel;
//?}
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.minecraft.client.data.models.*;
import net.minecraft.client.data.models.blockstates.*;
import net.minecraft.client.data.models.model.*;
import net.paulem.simpleores.bucket.renderer.CopperBucketItemSpecialRenderer;
import net.paulem.simpleores.bucket.renderer.CopperEmptyBucketItemSpecialRenderer;
import net.paulem.simpleores.furnaces.ModFurnaces;
import net.paulem.simpleores.items.custom.advanced.AdvancedArmorItem;
import net.paulem.simpleores.blocks.ModBlocks;
import net.paulem.simpleores.items.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.world.level.block.*;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.paulem.simpleores.items.custom.advanced.AdvancedSwordItem;
import net.paulem.simpleores.items.custom.advanced.AdvancedToolItem;
import net.paulem.simpleores.items.custom.bucket.CustomBucketItem;
//? if >1.21.3 && <=26.2 {
/*import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.resources.ResourceKey;*/
//?}
import org.jspecify.annotations.NonNull;
//? if 1.21.3
/*import net.paulem.simpleores.armors.ModEquipmentClientModels;*/
//? if <1.21.5
//import net.minecraft.resources.Identifier;

//? hasBucketlib
//import de.cech12.bucketlib.api.item.UniversalBucketItem;

import java.util.Map;

import static net.minecraft.client.data.models.BlockModelGenerators.*;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricPackOutput generator) {
        super(generator);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        BlockModelGenerators.BlockFamilyProvider tinBricksPool = blockStateModelGenerator.family(ModBlocks.TIN_BRICKS);
        tinBricksPool.stairs(ModBlocks.tin_brick_stairs);
        tinBricksPool.slab(ModBlocks.TIN_BRICK_SLAB);

        BlockModelGenerators.BlockFamilyProvider mythrilBricksPool = blockStateModelGenerator.family(ModBlocks.MYTHRIL_BRICKS);
        mythrilBricksPool.stairs(ModBlocks.mythril_brick_stairs);
        mythrilBricksPool.slab(ModBlocks.MYTHRIL_BRICK_SLAB);

        BlockModelGenerators.BlockFamilyProvider adamantiumBricksPool = blockStateModelGenerator.family(ModBlocks.ADAMANTIUM_BRICKS);
        adamantiumBricksPool.stairs(ModBlocks.adamantium_brick_stairs);
        adamantiumBricksPool.slab(ModBlocks.ADAMANTIUM_BRICK_SLAB);

        BlockModelGenerators.BlockFamilyProvider onyxBricksPool = blockStateModelGenerator.family(ModBlocks.ONYX_BRICKS);
        onyxBricksPool.stairs(ModBlocks.onyx_brick_stairs);
        onyxBricksPool.slab(ModBlocks.ONYX_BRICK_SLAB);

        for (Block modFurnace : ModFurnaces.getFurnaces()) {
            blockStateModelGenerator.createFurnace(modFurnace, TexturedModel.ORIENTABLE_ONLY_TOP);
        }

        blockStateModelGenerator.createWeightedPressurePlate(ModBlocks.copper_pressure_plate, Blocks.COPPER_BLOCK //? if >=26.2
                .weathering().unaffected()
        );
        blockStateModelGenerator.createWeightedPressurePlate(ModBlocks.tin_pressure_plate, ModBlocks.TIN_BLOCK);
        blockStateModelGenerator.createWeightedPressurePlate(ModBlocks.mythril_pressure_plate, ModBlocks.MYTHRIL_BLOCK);
        blockStateModelGenerator.createWeightedPressurePlate(ModBlocks.adamantium_pressure_plate, ModBlocks.ADAMANTIUM_BLOCK);
        blockStateModelGenerator.createWeightedPressurePlate(ModBlocks.onyx_pressure_plate, ModBlocks.ONYX_BLOCK);

        ModBlocks.registeredBlockItems.forEach((identifier, blockItem) -> {
            Block block = blockItem.getBlock();

            String path = identifier.getPath();

            if(block instanceof IronBarsBlock) {
                registerBars(blockStateModelGenerator, block);
            }
            else if(block instanceof DoorBlock)
                blockStateModelGenerator.createDoor(block);
            else if(path.contains("ore") || path.contains("block"))
                blockStateModelGenerator.createTrivialCube(block);
        });
    }

    @Override
    public void generateItemModels(@NonNull ItemModelGenerators itemModelGenerator) {
        for (Item item : ModItems.registeredItems.values()) {
            //? if hasBucketlib {
            /*if(item instanceof UniversalBucketItem) continue;
            *///?} else containsBucket {
            if(item instanceof CustomBucketItem bucketItem) {
                generateCopperBucket(itemModelGenerator, bucketItem);
                continue;
            }
            //?}

            if (item instanceof BowItem bowItem) {
                //? if >1.21.3
                itemModelGenerator.generateBow(bowItem);
            } else if (item instanceof AdvancedArmorItem armorItem) {
                //? if >26.2 {
                itemModelGenerator.generateTrimmableItem(item, ItemModelGenerators.prefixForSlotTrim(armorItem.getSCType().getType().getName()),
                        false, Map.of());
                //?} else if >1.21.3 {
                /*ResourceKey<EquipmentAsset> identifier = armorItem.getMaterial().assetId();
                itemModelGenerator.generateTrimmableItem(item, identifier,
                        //? if >=1.21.5 {
                        ItemModelGenerators.prefixForSlotTrim(armorItem.getSCType().getType().getName())
                        //?} else if >1.21.3 && <1.21.5 {
                        //armorItem.getType().getName()
                        //?}
                        , false);
                *///?} else if >1.21 {
                /*Identifier identifier = armorItem.getMaterial().modelId();
                itemModelGenerator.generateArmorTrims(item, identifier, ModEquipmentClientModels.REGISTERED_MODELS.get(identifier), armorItem.getSCType().getType().getSlot());
                *///?} else {
                 /*itemModelGenerator.generateArmorTrims(armorItem);
                *///?}
            } else if (item instanceof AdvancedToolItem) {
                itemModelGenerator.generateFlatItem(item, ModelTemplates.FLAT_HANDHELD_ITEM);
            } else if (item instanceof AdvancedSwordItem swordItem) {
                itemModelGenerator.generateFlatItem(swordItem, ModelTemplates.FLAT_HANDHELD_ITEM);
            } else {
                itemModelGenerator.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
            }
        }
    }

    //? if containsBucket && !hasBucketlib {
    public final void generateCopperBucket(ItemModelGenerators itemModelGenerator, final Item bucketItem) {
        ItemModel.Unbaked bucketModel = ItemModelUtils.composite(
                new CopperBucketItemSpecialRenderer.Unbaked(),
                new CopperEmptyBucketItemSpecialRenderer.Unbaked()
        );

        itemModelGenerator.itemModelOutput.accept(bucketItem, bucketModel);
    }
    //?}

    /*? if >=1.21.5 {*/
    private void registerBars(BlockModelGenerators generator, Block barBlock) {
        MultiVariant weightedVariant = plainVariant(ModelLocationUtils.getModelLocation(barBlock, "_post_ends"));
        MultiVariant weightedVariant2 = plainVariant(ModelLocationUtils.getModelLocation(barBlock, "_post"));
        MultiVariant weightedVariant3 = plainVariant(ModelLocationUtils.getModelLocation(barBlock, "_cap"));
        MultiVariant weightedVariant4 = plainVariant(ModelLocationUtils.getModelLocation(barBlock, "_cap_alt"));
        MultiVariant weightedVariant5 = plainVariant(ModelLocationUtils.getModelLocation(barBlock, "_side"));
        MultiVariant weightedVariant6 = plainVariant(ModelLocationUtils.getModelLocation(barBlock, "_side_alt"));
        generator.blockStateOutput
                .accept(
                        MultiPartGenerator.multiPart(barBlock)
                                .with(weightedVariant)
                                .with(
                                        condition().term(BlockStateProperties.NORTH, false).term(BlockStateProperties.EAST, false).term(BlockStateProperties.SOUTH, false).term(BlockStateProperties.WEST, false),
                                        weightedVariant2
                                )
                                .with(
                                        condition().term(BlockStateProperties.NORTH, true).term(BlockStateProperties.EAST, false).term(BlockStateProperties.SOUTH, false).term(BlockStateProperties.WEST, false),
                                        weightedVariant3
                                )
                                .with(
                                        condition().term(BlockStateProperties.NORTH, false).term(BlockStateProperties.EAST, true).term(BlockStateProperties.SOUTH, false).term(BlockStateProperties.WEST, false),
                                        weightedVariant3.with(Y_ROT_90)
                                )
                                .with(
                                        condition().term(BlockStateProperties.NORTH, false).term(BlockStateProperties.EAST, false).term(BlockStateProperties.SOUTH, true).term(BlockStateProperties.WEST, false),
                                        weightedVariant4
                                )
                                .with(
                                        condition().term(BlockStateProperties.NORTH, false).term(BlockStateProperties.EAST, false).term(BlockStateProperties.SOUTH, false).term(BlockStateProperties.WEST, true),
                                        weightedVariant4.with(Y_ROT_90)
                                )
                                .with(condition().term(BlockStateProperties.NORTH, true), weightedVariant5)
                                .with(condition().term(BlockStateProperties.EAST, true), weightedVariant5.with(Y_ROT_90))
                                .with(condition().term(BlockStateProperties.SOUTH, true), weightedVariant6)
                                .with(condition().term(BlockStateProperties.WEST, true), weightedVariant6.with(Y_ROT_90))
                );
        generator.registerSimpleFlatItemModel(barBlock);
    }
    /*?} else {*/
    /*private void registerBars(BlockModelGenerators blockStateModelGenerator, Block barBlock) {
        Identifier identifier = ModelLocationUtils.getModelLocation(barBlock, "_post_ends");
        Identifier identifier2 = ModelLocationUtils.getModelLocation(barBlock, "_post");
        Identifier identifier3 = ModelLocationUtils.getModelLocation(barBlock, "_cap");
        Identifier identifier4 = ModelLocationUtils.getModelLocation(barBlock, "_cap_alt");
        Identifier identifier5 = ModelLocationUtils.getModelLocation(barBlock, "_side");
        Identifier identifier6 = ModelLocationUtils.getModelLocation(barBlock, "_side_alt");

        blockStateModelGenerator.blockStateOutput
                .accept(
                        MultiPartGenerator.multiPart(barBlock)
                                .with(Variant.variant().with(VariantProperties.MODEL, identifier))
                                .with(
                                        Condition.condition().term(BlockStateProperties.NORTH, false).term(BlockStateProperties.EAST, false).term(BlockStateProperties.SOUTH, false).term(BlockStateProperties.WEST, false),
                                        Variant.variant().with(VariantProperties.MODEL, identifier2)
                                )
                                .with(
                                        Condition.condition().term(BlockStateProperties.NORTH, true).term(BlockStateProperties.EAST, false).term(BlockStateProperties.SOUTH, false).term(BlockStateProperties.WEST, false),
                                        Variant.variant().with(VariantProperties.MODEL, identifier3)
                                )
                                .with(
                                        Condition.condition().term(BlockStateProperties.NORTH, false).term(BlockStateProperties.EAST, true).term(BlockStateProperties.SOUTH, false).term(BlockStateProperties.WEST, false),
                                        Variant.variant().with(VariantProperties.MODEL, identifier3).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                                )
                                .with(
                                        Condition.condition().term(BlockStateProperties.NORTH, false).term(BlockStateProperties.EAST, false).term(BlockStateProperties.SOUTH, true).term(BlockStateProperties.WEST, false),
                                        Variant.variant().with(VariantProperties.MODEL, identifier4)
                                )
                                .with(
                                        Condition.condition().term(BlockStateProperties.NORTH, false).term(BlockStateProperties.EAST, false).term(BlockStateProperties.SOUTH, false).term(BlockStateProperties.WEST, true),
                                        Variant.variant().with(VariantProperties.MODEL, identifier4).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                                )
                                .with(Condition.condition().term(BlockStateProperties.NORTH, true), Variant.variant().with(VariantProperties.MODEL, identifier5))
                                .with(
                                        Condition.condition().term(BlockStateProperties.EAST, true),
                                        Variant.variant().with(VariantProperties.MODEL, identifier5).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                                )
                                .with(Condition.condition().term(BlockStateProperties.SOUTH, true), Variant.variant().with(VariantProperties.MODEL, identifier6))
                                .with(
                                        Condition.condition().term(BlockStateProperties.WEST, true),
                                        Variant.variant().with(VariantProperties.MODEL, identifier6).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                                )
                );
        blockStateModelGenerator.createSimpleFlatItemModel(barBlock);
    }
    *//*?}*/
}
