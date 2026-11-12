package net.paulem.simpleores.datagen.providers.tags;

import net.paulem.simpleores.blocks.ModBlocks;
import net.paulem.sclib.tag.SCTags;
import net.paulem.simpleores.tags.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.paulem.sclib.datagen.SCBlockTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.world.level.block.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
//? if >=1.21.6
import net.minecraft.data.tags.TagAppender;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends SCBlockTagProvider {
    public ModBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        build(ConventionalBlockTags.ORES, ModTags.Blocks.SIMPLEORES_ORES);

        //? if >1.20.4
        TagKey<Block> storageBlocksTag = ConventionalBlockTags.STORAGE_BLOCKS;
        TagKey<Block> carverReplaceables = //$ if >26.2 'BlockTags.ORES' else 'BlockTags.OVERWORLD_CARVER_REPLACEABLES'
                BlockTags.ORES
        ;

        // ------------------- BLOCKS BREAK -------------------

        TagKey<Block> pickaxeMineable = BlockTags.MINEABLE_WITH_PICKAXE;

        TagKey<Block> needsStoneTool = BlockTags.NEEDS_STONE_TOOL;

        TagKey<Block> needsIronTool = BlockTags.NEEDS_IRON_TOOL;

        TagKey<Block> needsDiamondTool = BlockTags.NEEDS_DIAMOND_TOOL;

        // ------------------- BEACON BASE -------------------
        build(BlockTags.BEACON_BASE_BLOCKS,
                Blocks.COPPER_BLOCK,
                ModBlocks.ADAMANTIUM_BLOCK,
                ModBlocks.TIN_BLOCK,
                ModBlocks.MYTHRIL_BLOCK,
                ModBlocks.ONYX_BLOCK
        );

        // ------------------- DOORS -------------------
        TagKey<Block> doorsTagBuilder = BlockTags.DOORS;

        TagKey<Block> pressurePlatesTagBuilder = BlockTags.PRESSURE_PLATES;
        TagKey<Block> slabsTagBuilder = BlockTags.SLABS;
        TagKey<Block> stairsTagBuilder = BlockTags.STAIRS;

        ModBlocks.registeredBlockItems.forEach((identifier, blockItem) -> {
            Block block = blockItem.getBlock();

            String path = identifier.getPath();

            build(pickaxeMineable, block);

            if(path.contains("deepslate_") && path.contains("_ore")) {
                build(ModTags.Blocks.SIMPLEORES_ORES, block);

                // MOD COMPAT
                String material = identifier.getPath().replace("deepslate_", "").replace("_ore", "");

                TagKey<Block> tag = SCTags.ore(material);
                build(tag, block);

                //? if >1.20.4 {
                build(carverReplaceables, block);
                //?}
            } else if(path.contains("_ore")) {
                build(ModTags.Blocks.SIMPLEORES_ORES, block);

                // MOD COMPAT
                String material = identifier.getPath().replace("basalt_", "").replace("_ore", "");

                TagKey<Block> tag = SCTags.ore(material);
                build(tag, block);

                //? if >1.20.4 {
                build(carverReplaceables, block);
                //?}
            }

            if(path.contains("block")) {
                //? if >1.20.4
                build(storageBlocksTag, block);

                // MOD COMPAT
                String material = identifier.getPath().replace("_block", "");

                TagKey<Block> tag = SCTags.storageBlock(material);
                build(tag, block);
            }

            if(path.contains("tin") || path.contains("copper")) {
                build(needsStoneTool, block);
            }

            if(path.contains("mythril") || path.contains("adamantium")) {
                build(needsIronTool, block);
            }

            if(path.contains("onyx")) {
                build(needsDiamondTool, block);
            }

            if(block instanceof DoorBlock) {
                build(doorsTagBuilder, block);
            }
            else if(block instanceof WeightedPressurePlateBlock) {
                build(pressurePlatesTagBuilder, block);
            }
            else if(block instanceof SlabBlock) {
                build(slabsTagBuilder, block);
            }
            else if(block instanceof StairBlock) {
                build(stairsTagBuilder, block);
            }
            else if(block instanceof IronBarsBlock) {
                build(BlockTags.DRAGON_IMMUNE, block);
            }
        });
    }
}
