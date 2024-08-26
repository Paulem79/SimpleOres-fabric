package io.github.paulem.simpleores.datagen.providers.tags;

import io.github.paulem.simpleores.blocks.ModBlocks;
import io.github.paulem.simpleores.tags.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.block.*;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class BlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public BlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ModTags.Blocks.SIMPLEORES_ORES)
                .add(ModBlocks.TIN_ORE)
                .add(ModBlocks.DEEPSLATE_TIN_ORE)

                .add(ModBlocks.MYTHRIL_ORE)
                .add(ModBlocks.DEEPSLATE_MYTHRIL_ORE)

                .add(ModBlocks.ADAMANTIUM_ORE)
                .add(ModBlocks.DEEPSLATE_ADAMANTIUM_ORE)

                .add(ModBlocks.ONYX_ORE);

        getOrCreateTagBuilder(ConventionalBlockTags.ORES)
                .forceAddTag(ModTags.Blocks.SIMPLEORES_ORES);

        FabricTagProvider<Block>.FabricTagBuilder storageBlocksTag = getOrCreateTagBuilder(ConventionalBlockTags.STORAGE_BLOCKS);

        // ------------------- BLOCKS BREAK -------------------

        FabricTagProvider<Block>.FabricTagBuilder pickaxeMineable = getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE);

        FabricTagProvider<Block>.FabricTagBuilder needsStoneTool = getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL);

        FabricTagProvider<Block>.FabricTagBuilder needsIronTool = getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL);

        FabricTagProvider<Block>.FabricTagBuilder needsDiamondTool = getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL);

        // ------------------- BEACON BASE -------------------
        getOrCreateTagBuilder(BlockTags.BEACON_BASE_BLOCKS)
                .add(Blocks.COPPER_BLOCK)
                .add(ModBlocks.ADAMANTIUM_BLOCK)
                .add(ModBlocks.TIN_BLOCK)
                .add(ModBlocks.MYTHRIL_BLOCK)
                .add(ModBlocks.ONYX_BLOCK);

        // ------------------- DOORS -------------------
        FabricTagProvider<Block>.FabricTagBuilder doorsTagBuilder = getOrCreateTagBuilder(BlockTags.DOORS);

        FabricTagProvider<Block>.FabricTagBuilder pressurePlatesTagBuilder = getOrCreateTagBuilder(BlockTags.PRESSURE_PLATES);
        FabricTagProvider<Block>.FabricTagBuilder slabsTagBuilder = getOrCreateTagBuilder(BlockTags.SLABS);
        FabricTagProvider<Block>.FabricTagBuilder stairsTagBuilder = getOrCreateTagBuilder(BlockTags.STAIRS);

        ModBlocks.registeredBlockItems.forEach((identifier, blockItem) -> {
            Block block = blockItem.getBlock();

            String path = identifier.getPath();

            pickaxeMineable.add(block);

            if(path.contains("block"))
                storageBlocksTag.add(block);

            if(path.contains("tin") || path.contains("copper"))
                needsStoneTool.add(block);

            if(path.contains("mythril") || path.contains("adamantium"))
                needsIronTool.add(block);

            if(path.contains("onyx"))
                needsDiamondTool.add(block);

            if(block instanceof DoorBlock)
                doorsTagBuilder.add(block);
            else if(block instanceof WeightedPressurePlateBlock)
                pressurePlatesTagBuilder.add(block);
            else if(block instanceof SlabBlock)
                slabsTagBuilder.add(block);
            else if(block instanceof StairsBlock)
                stairsTagBuilder.add(block);
        });
    }
}