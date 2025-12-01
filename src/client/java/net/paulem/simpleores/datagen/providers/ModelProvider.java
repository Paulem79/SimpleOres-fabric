package net.paulem.simpleores.datagen.providers;

//? if !hasBucketlib {
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.util.ARGB;
//?}
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.minecraft.client.data.models.*;
import net.minecraft.client.data.models.blockstates.*;
import net.minecraft.client.data.models.model.*;
import net.paulem.simpleores.bucket.tint.ClientBucketUtil;
import net.paulem.simpleores.bucket.tint.handler.BucketLayerTintSource;
import net.paulem.simpleores.bucket.tint.handler.LayersUploader;
import net.paulem.simpleores.items.custom.advanced.AdvancedArmorItem;
import net.paulem.simpleores.blocks.ModBlocks;
import net.paulem.simpleores.items.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.world.level.block.*;
import net.minecraft.world.item.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.paulem.simpleores.items.custom.advanced.AdvancedSwordItem;
import net.paulem.simpleores.items.custom.advanced.AdvancedToolItem;
import net.paulem.simpleores.items.custom.bucket.CustomBucketFluidable;
import net.paulem.simpleores.items.custom.bucket.CustomChildrenBucketItem;
import net.paulem.simpleores.items.custom.bucket.CustomParentBucketItem;
//? if >1.21.3
import net.minecraft.world.item.equipment.EquipmentAsset;
//? if 1.21.3
/*import net.paulem.simpleores.armors.ModEquipmentClientModels;*/

//? hasBucketlib
/*import de.cech12.bucketlib.api.item.UniversalBucketItem;*/

import static net.minecraft.client.data.models.BlockModelGenerators.*;

public class ModelProvider extends FabricModelProvider {
    public ModelProvider(FabricDataOutput generator) {
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

        blockStateModelGenerator.createWeightedPressurePlate(ModBlocks.copper_pressure_plate, Blocks.COPPER_BLOCK);
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
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        for (Item item : ModItems.registeredItems.values()) {
            //? if hasBucketlib {
            /*if(item instanceof UniversalBucketItem) continue;
            *///?} else {
            if(item instanceof CustomParentBucketItem parentBucketItem) {
                itemModelGenerator.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
                for (CustomChildrenBucketItem child : parentBucketItem.getChilds()) {
                    if(child.getFluid() == Fluids.WATER) {
                        registerCustomBucketWithOverlay(itemModelGenerator, child, parentBucketItem, new BucketLayerTintSource(ARGB.color(255, 0xFFFFFF), Integer.MAX_VALUE, Integer.MAX_VALUE));
                    } else {
                        registerNonWaterBucket(itemModelGenerator, child, parentBucketItem, ClientBucketUtil.getDefaultTints(child));
                    }
                }
                continue;
            }

            // Exclude childs registration because it's handled in the loop
            if(item instanceof CustomBucketFluidable) continue;
            //?}

            if (item instanceof BowItem bowItem) {
                //? if >1.21.3
                itemModelGenerator.generateBow(bowItem);
            } else if (item instanceof AdvancedArmorItem armorItem) {
                //? if >1.21.3 {
                ResourceKey<EquipmentAsset> identifier = armorItem.getMaterial().assetId();
                itemModelGenerator.generateTrimmableItem(item, identifier,
                        //? if >=1.21.5 {
                        ItemModelGenerators.prefixForSlotTrim(armorItem.getSCType().getType().getName())
                        //?} else if >1.21.3 && <1.21.5 {
                        //armorItem.getType().getName()
                        //?}
                        , false);
                //?} else if >1.21 {
                /*ResourceLocation identifier = armorItem.getMaterial().modelId();
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

    //? if !hasBucketlib {
    public final void registerCustomBucketWithOverlay(ItemModelGenerators itemModelGenerator, Item item, Item parentBucket, ItemTintSource tint) {
        ResourceLocation identifier = itemModelGenerator.generateLayeredItem(item, TextureMapping.getItemTexture(parentBucket), TextureMapping.getItemTexture(parentBucket, "_overlay"));
        itemModelGenerator.itemModelOutput.accept(item, ItemModelUtils.tintedModel(identifier, ItemModelUtils.constantTint(-1), tint));
    }

    public final void registerNonWaterBucket(ItemModelGenerators itemModelGenerator, Item item, Item parentBucket, ItemTintSource... tints) {
        LayersUploader.registerOverlayBucket(itemModelGenerator, item, parentBucket, tints);
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
        ResourceLocation identifier = ModelLocationUtils.getModelLocation(barBlock, "_post_ends");
        ResourceLocation identifier2 = ModelLocationUtils.getModelLocation(barBlock, "_post");
        ResourceLocation identifier3 = ModelLocationUtils.getModelLocation(barBlock, "_cap");
        ResourceLocation identifier4 = ModelLocationUtils.getModelLocation(barBlock, "_cap_alt");
        ResourceLocation identifier5 = ModelLocationUtils.getModelLocation(barBlock, "_side");
        ResourceLocation identifier6 = ModelLocationUtils.getModelLocation(barBlock, "_side_alt");

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
