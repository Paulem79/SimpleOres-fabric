package net.paulem.simpleores.datagen.providers.tags;

import net.paulem.simpleores.blocks.ModBlocks;
import net.paulem.simpleores.stonecutter.SCTag;
import net.paulem.simpleores.tags.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
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

public class ModBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {
    public ModBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        build(ConventionalBlockTags.ORES, ModTags.Blocks.SIMPLEORES_ORES);

        //? if >1.20.4
        TagBuilder storageBlocksTag = new TagBuilder(ConventionalBlockTags.STORAGE_BLOCKS);
        TagBuilder carverReplaceables = new TagBuilder(BlockTags.OVERWORLD_CARVER_REPLACEABLES);

        // ------------------- BLOCKS BREAK -------------------

        TagBuilder pickaxeMineable = new TagBuilder(BlockTags.MINEABLE_WITH_PICKAXE);

        TagBuilder needsStoneTool = new TagBuilder(BlockTags.NEEDS_STONE_TOOL);

        TagBuilder needsIronTool = new TagBuilder(BlockTags.NEEDS_IRON_TOOL);

        TagBuilder needsDiamondTool = new TagBuilder(BlockTags.NEEDS_DIAMOND_TOOL);

        // ------------------- BEACON BASE -------------------
        build(BlockTags.BEACON_BASE_BLOCKS,
                Blocks.COPPER_BLOCK,
                ModBlocks.ADAMANTIUM_BLOCK,
                ModBlocks.TIN_BLOCK,
                ModBlocks.MYTHRIL_BLOCK,
                ModBlocks.ONYX_BLOCK
        );

        // ------------------- DOORS -------------------
        TagBuilder doorsTagBuilder = new TagBuilder(BlockTags.DOORS);

        TagBuilder pressurePlatesTagBuilder = new TagBuilder(BlockTags.PRESSURE_PLATES);
        TagBuilder slabsTagBuilder = new TagBuilder(BlockTags.SLABS);
        TagBuilder stairsTagBuilder = new TagBuilder(BlockTags.STAIRS);

        ModBlocks.registeredBlockItems.forEach((identifier, blockItem) -> {
            Block block = blockItem.getBlock();

            String path = identifier.getPath();

            build(pickaxeMineable, block);

            if(path.contains("deepslate_") && path.contains("_ore")) {
                build(ModTags.Blocks.SIMPLEORES_ORES, block);

                // MOD COMPAT
                String material = identifier.getPath().replace("deepslate_", "").replace("_ore", "");

                TagKey<Block> tag = SCTag.forOres(material);
                build(tag, block);

                //? if >1.20.4 {
                build(carverReplaceables, block);
                //?}
            } else if(path.contains("_ore")) {
                build(ModTags.Blocks.SIMPLEORES_ORES, block);

                // MOD COMPAT
                String material = identifier.getPath().replace("_ore", "");

                TagKey<Block> tag = SCTag.forOres(material);
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

                TagKey<Block> tag = SCTag.forBlocks(material);
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

    private void build(TagKey<Block> tagKey, Object... objects) {
        TagBuilder builder = new TagBuilder(tagKey);
        
        build(builder, objects);
    }

    private void build(TagBuilder builder,
                       Object... objects) {
        //? if >=1.21.6 {
        for (Object object : objects) {

            if(object instanceof Block block) {
                builder.get()
                        .add(ResourceKey.create(BuiltInRegistries.BLOCK.key(), BuiltInRegistries.BLOCK.getKey(block)));
            } else if(object instanceof TagKey<?> tag && object.getClass().getGenericSuperclass() == Block.class) {
                builder.get()
                        .addTag((TagKey<Block>) tag);
            }
        }
        //?} else {
            /*for (Object object : objects) {
                if (object instanceof Block block) {
                    builder.get().add(block);
                } else if (object instanceof TagKey<?> tag) {
                    builder.get().addTag((TagKey<Block>) tag);
                }
            }
        *///?}
    }

    class TagBuilder {
        private final TagKey<Block> tag;

        public TagBuilder(TagKey<Block> tag) {
            this.tag = tag;
        }

        public //? if >26.1 {
                TagAppender<Block>
        //?} else if >=1.21.6 {
        //TagAppender<ResourceKey<Block>, Block>
        //?} else {
        //FabricTagsProvider<Block>.FabricTagBuilder
        //?}
        get() {
            /*? if >=1.21.6 {*/
            return builder(tag);
            /*?} else {*/
            /*return getOrCreateTagBuilder(tag);
            *//*?}*/
        }
    }
}
