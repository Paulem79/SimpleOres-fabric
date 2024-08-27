package io.github.paulem.simpleores.datagen.providers;

import de.cech12.bucketlib.api.item.UniversalBucketItem;
import io.github.paulem.simpleores.blocks.ModBlocks;
import io.github.paulem.simpleores.items.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.*;
import net.minecraft.data.client.*;
import net.minecraft.item.*;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

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

            if(block instanceof DoorBlock)
                blockStateModelGenerator.registerDoor(block);
            else if(path.contains("ore") || path.contains("block"))
                blockStateModelGenerator.registerSimpleCubeAll(block);
        });
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        for (Item item : ModItems.registeredItems.values()) {
            if (item instanceof BowItem || item instanceof UniversalBucketItem)
                continue;

            if (item instanceof ArmorItem armorItem) {
                itemModelGenerator.registerArmor(armorItem);
            } else if (item instanceof ToolItem) {
                itemModelGenerator.register(item, Models.HANDHELD);
            } else {
                itemModelGenerator.register(item, Models.GENERATED);
            }
        }
    }
}