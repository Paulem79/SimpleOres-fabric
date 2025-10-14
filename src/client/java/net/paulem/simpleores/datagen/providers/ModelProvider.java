package net.paulem.simpleores.datagen.providers;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.minecraft.client.data.*;
import net.paulem.simpleores.bucket.tint.ClientBucketUtil;
import net.paulem.simpleores.bucket.tint.handler.BucketLayerTintSource;
import net.paulem.simpleores.bucket.tint.handler.LayersUploader;
import net.paulem.simpleores.items.custom.advanced.AdvancedArmorItem;
import net.paulem.simpleores.blocks.ModBlocks;
import net.paulem.simpleores.items.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.paulem.simpleores.items.custom.advanced.AdvancedSwordItem;
import net.paulem.simpleores.items.custom.advanced.AdvancedToolItem;

//? if !hasBucketlib {
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.client.render.item.tint.TintSource;
import net.paulem.simpleores.items.custom.bucket.CustomBucketFluidable;
import net.paulem.simpleores.items.custom.bucket.CustomChildrenBucketItem;
import net.paulem.simpleores.items.custom.bucket.CustomParentBucketItem;
//?}

//? if >=1.21.5
import net.minecraft.client.render.model.json.WeightedVariant;
//? if <1.21.5 || !hasBucketlib
import net.minecraft.util.Identifier;
//? if >1.21.3
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.registry.RegistryKey;
import net.minecraft.state.property.Properties;
//? if 1.21.3
/*import net.paulem.simpleores.armors.ModEquipmentModels;*/

//? hasBucketlib
/*import de.cech12.bucketlib.api.item.UniversalBucketItem;*/

import static net.minecraft.client.data.BlockStateModelGenerator.*;

public class ModelProvider extends FabricModelProvider {
    public ModelProvider(FabricDataOutput generator) {
        super(generator);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

        BlockStateModelGenerator.BlockTexturePool tinBricksPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.TIN_BRICKS);
        tinBricksPool.stairs(ModBlocks.tin_brick_stairs);
        tinBricksPool.slab(ModBlocks.TIN_BRICK_SLAB);

        BlockStateModelGenerator.BlockTexturePool mythrilBricksPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.MYTHRIL_BRICKS);
        mythrilBricksPool.stairs(ModBlocks.mythril_brick_stairs);
        mythrilBricksPool.slab(ModBlocks.MYTHRIL_BRICK_SLAB);

        BlockStateModelGenerator.BlockTexturePool adamantiumBricksPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.ADAMANTIUM_BRICKS);
        adamantiumBricksPool.stairs(ModBlocks.adamantium_brick_stairs);
        adamantiumBricksPool.slab(ModBlocks.ADAMANTIUM_BRICK_SLAB);

        BlockStateModelGenerator.BlockTexturePool onyxBricksPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.ONYX_BRICKS);
        onyxBricksPool.stairs(ModBlocks.onyx_brick_stairs);
        onyxBricksPool.slab(ModBlocks.ONYX_BRICK_SLAB);

        blockStateModelGenerator.registerWeightedPressurePlate(ModBlocks.copper_pressure_plate, Blocks.COPPER_BLOCK);
        blockStateModelGenerator.registerWeightedPressurePlate(ModBlocks.tin_pressure_plate, ModBlocks.TIN_BLOCK);
        blockStateModelGenerator.registerWeightedPressurePlate(ModBlocks.mythril_pressure_plate, ModBlocks.MYTHRIL_BLOCK);
        blockStateModelGenerator.registerWeightedPressurePlate(ModBlocks.adamantium_pressure_plate, ModBlocks.ADAMANTIUM_BLOCK);
        blockStateModelGenerator.registerWeightedPressurePlate(ModBlocks.onyx_pressure_plate, ModBlocks.ONYX_BLOCK);

        ModBlocks.registeredBlockItems.forEach((identifier, blockItem) -> {
            Block block = blockItem.getBlock();

            String path = identifier.getPath();

            if(block instanceof PaneBlock) {
                registerBars(blockStateModelGenerator, block);
            }
            else if(block instanceof DoorBlock)
                blockStateModelGenerator.registerDoor(block);
            else if(path.contains("ore") || path.contains("block"))
                blockStateModelGenerator.registerSimpleCubeAll(block);
        });
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        for (Item item : ModItems.registeredItems.values()) {
            //? if hasBucketlib {
            /*if(item instanceof UniversalBucketItem) continue;
            *///?} else {
            if(item instanceof CustomParentBucketItem parentBucketItem) {
                itemModelGenerator.register(item, Models.GENERATED);
                for (CustomChildrenBucketItem child : parentBucketItem.getChilds()) {
                    if(child.getFluid() == Fluids.WATER) {
                        registerCustomBucketWithOverlay(itemModelGenerator, child, parentBucketItem, new BucketLayerTintSource(ColorHelper.withAlpha(255, 0xFFFFFF), Integer.MAX_VALUE, Integer.MAX_VALUE));
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
                itemModelGenerator.registerBow(bowItem);
            } else if (item instanceof AdvancedArmorItem armorItem) {
                //? if >1.21.3 {
                RegistryKey<EquipmentAsset> identifier = armorItem.getMaterial().assetId();
                itemModelGenerator.registerArmor(item, identifier,
                        //? if >=1.21.5 {
                        ItemModelGenerator.getTrimAssetIdPrefix(armorItem.getSCType().getType().getName())
                        //?} else if >1.21.3 && <1.21.5 {
                        //armorItem.getType().getName()
                        //?}
                        , false);
                //?} else if >1.21 {
                /*Identifier identifier = armorItem.getMaterial().modelId();
                itemModelGenerator.registerArmor(item, identifier, ModEquipmentModels.REGISTERED_MODELS.get(identifier), armorItem.getSCType().getType().getEquipmentSlot());
                *///?} else {
                 /*itemModelGenerator.registerArmor(armorItem);
                *///?}
            } else if (item instanceof AdvancedToolItem) {
                itemModelGenerator.register(item, Models.HANDHELD);
            } else if (item instanceof AdvancedSwordItem swordItem) {
                itemModelGenerator.register(swordItem, Models.HANDHELD);
            } else {
                itemModelGenerator.register(item, Models.GENERATED);
            }
        }
    }

    //? if !hasBucketlib {
    public final void registerCustomBucketWithOverlay(ItemModelGenerator itemModelGenerator, Item item, Item parentBucket, TintSource tint) {
        Identifier identifier = itemModelGenerator.uploadTwoLayers(item, TextureMap.getId(parentBucket), TextureMap.getSubId(parentBucket, "_overlay"));
        itemModelGenerator.output.accept(item, ItemModels.tinted(identifier, ItemModels.constantTintSource(-1), tint));
    }

    public final void registerNonWaterBucket(ItemModelGenerator itemModelGenerator, Item item, Item parentBucket, TintSource... tints) {
        LayersUploader.registerOverlayBucket(itemModelGenerator, item, parentBucket, tints);
    }
    //?}

    /*? if >=1.21.5 {*/
    private void registerBars(BlockStateModelGenerator generator, Block barBlock) {
        WeightedVariant weightedVariant = createWeightedVariant(ModelIds.getBlockSubModelId(barBlock, "_post_ends"));
        WeightedVariant weightedVariant2 = createWeightedVariant(ModelIds.getBlockSubModelId(barBlock, "_post"));
        WeightedVariant weightedVariant3 = createWeightedVariant(ModelIds.getBlockSubModelId(barBlock, "_cap"));
        WeightedVariant weightedVariant4 = createWeightedVariant(ModelIds.getBlockSubModelId(barBlock, "_cap_alt"));
        WeightedVariant weightedVariant5 = createWeightedVariant(ModelIds.getBlockSubModelId(barBlock, "_side"));
        WeightedVariant weightedVariant6 = createWeightedVariant(ModelIds.getBlockSubModelId(barBlock, "_side_alt"));
        generator.blockStateCollector
                .accept(
                        MultipartBlockModelDefinitionCreator.create(barBlock)
                                .with(weightedVariant)
                                .with(
                                        createMultipartConditionBuilder().put(Properties.NORTH, false).put(Properties.EAST, false).put(Properties.SOUTH, false).put(Properties.WEST, false),
                                        weightedVariant2
                                )
                                .with(
                                        createMultipartConditionBuilder().put(Properties.NORTH, true).put(Properties.EAST, false).put(Properties.SOUTH, false).put(Properties.WEST, false),
                                        weightedVariant3
                                )
                                .with(
                                        createMultipartConditionBuilder().put(Properties.NORTH, false).put(Properties.EAST, true).put(Properties.SOUTH, false).put(Properties.WEST, false),
                                        weightedVariant3.apply(ROTATE_Y_90)
                                )
                                .with(
                                        createMultipartConditionBuilder().put(Properties.NORTH, false).put(Properties.EAST, false).put(Properties.SOUTH, true).put(Properties.WEST, false),
                                        weightedVariant4
                                )
                                .with(
                                        createMultipartConditionBuilder().put(Properties.NORTH, false).put(Properties.EAST, false).put(Properties.SOUTH, false).put(Properties.WEST, true),
                                        weightedVariant4.apply(ROTATE_Y_90)
                                )
                                .with(createMultipartConditionBuilder().put(Properties.NORTH, true), weightedVariant5)
                                .with(createMultipartConditionBuilder().put(Properties.EAST, true), weightedVariant5.apply(ROTATE_Y_90))
                                .with(createMultipartConditionBuilder().put(Properties.SOUTH, true), weightedVariant6)
                                .with(createMultipartConditionBuilder().put(Properties.WEST, true), weightedVariant6.apply(ROTATE_Y_90))
                );
        generator.registerItemModel(barBlock);
    }
    /*?} else {*/
    /*private void registerBars(BlockStateModelGenerator blockStateModelGenerator, Block barBlock) {
        Identifier identifier = ModelIds.getBlockSubModelId(barBlock, "_post_ends");
        Identifier identifier2 = ModelIds.getBlockSubModelId(barBlock, "_post");
        Identifier identifier3 = ModelIds.getBlockSubModelId(barBlock, "_cap");
        Identifier identifier4 = ModelIds.getBlockSubModelId(barBlock, "_cap_alt");
        Identifier identifier5 = ModelIds.getBlockSubModelId(barBlock, "_side");
        Identifier identifier6 = ModelIds.getBlockSubModelId(barBlock, "_side_alt");

        blockStateModelGenerator.blockStateCollector
                .accept(
                        MultipartBlockStateSupplier.create(barBlock)
                                .with(BlockStateVariant.create().put(VariantSettings.MODEL, identifier))
                                .with(
                                        When.create().set(Properties.NORTH, false).set(Properties.EAST, false).set(Properties.SOUTH, false).set(Properties.WEST, false),
                                        BlockStateVariant.create().put(VariantSettings.MODEL, identifier2)
                                )
                                .with(
                                        When.create().set(Properties.NORTH, true).set(Properties.EAST, false).set(Properties.SOUTH, false).set(Properties.WEST, false),
                                        BlockStateVariant.create().put(VariantSettings.MODEL, identifier3)
                                )
                                .with(
                                        When.create().set(Properties.NORTH, false).set(Properties.EAST, true).set(Properties.SOUTH, false).set(Properties.WEST, false),
                                        BlockStateVariant.create().put(VariantSettings.MODEL, identifier3).put(VariantSettings.Y, VariantSettings.Rotation.R90)
                                )
                                .with(
                                        When.create().set(Properties.NORTH, false).set(Properties.EAST, false).set(Properties.SOUTH, true).set(Properties.WEST, false),
                                        BlockStateVariant.create().put(VariantSettings.MODEL, identifier4)
                                )
                                .with(
                                        When.create().set(Properties.NORTH, false).set(Properties.EAST, false).set(Properties.SOUTH, false).set(Properties.WEST, true),
                                        BlockStateVariant.create().put(VariantSettings.MODEL, identifier4).put(VariantSettings.Y, VariantSettings.Rotation.R90)
                                )
                                .with(When.create().set(Properties.NORTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier5))
                                .with(
                                        When.create().set(Properties.EAST, true),
                                        BlockStateVariant.create().put(VariantSettings.MODEL, identifier5).put(VariantSettings.Y, VariantSettings.Rotation.R90)
                                )
                                .with(When.create().set(Properties.SOUTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier6))
                                .with(
                                        When.create().set(Properties.WEST, true),
                                        BlockStateVariant.create().put(VariantSettings.MODEL, identifier6).put(VariantSettings.Y, VariantSettings.Rotation.R90)
                                )
                );
        blockStateModelGenerator.registerItemModel(barBlock);
    }
    *//*?}*/
}