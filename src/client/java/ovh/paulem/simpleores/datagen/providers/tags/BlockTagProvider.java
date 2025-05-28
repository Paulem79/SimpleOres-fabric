package ovh.paulem.simpleores.datagen.providers.tags;

import net.minecraft.data.tag.ProvidedTagBuilder;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import ovh.paulem.simpleores.blocks.ModBlocks;
import ovh.paulem.simpleores.tags.ModTags;
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
        build(ModTags.Blocks.SIMPLEORES_ORES,
                ModBlocks.TIN_ORE,
                ModBlocks.DEEPSLATE_TIN_ORE,
                ModBlocks.MYTHRIL_ORE,
                ModBlocks.DEEPSLATE_MYTHRIL_ORE,
                ModBlocks.ADAMANTIUM_ORE,
                ModBlocks.DEEPSLATE_ADAMANTIUM_ORE,
                ModBlocks.ONYX_ORE
        );

        builder(ConventionalBlockTags.ORES)
                .addTag(ModTags.Blocks.SIMPLEORES_ORES);

        ProvidedTagBuilder<RegistryKey<Block>, Block> storageBlocksTag = builder(ConventionalBlockTags.STORAGE_BLOCKS);

        // ------------------- BLOCKS BREAK -------------------

        ProvidedTagBuilder<RegistryKey<Block>, Block> pickaxeMineable = builder(BlockTags.PICKAXE_MINEABLE);

        ProvidedTagBuilder<RegistryKey<Block>, Block> needsStoneTool = builder(BlockTags.NEEDS_STONE_TOOL);

        ProvidedTagBuilder<RegistryKey<Block>, Block> needsIronTool = builder(BlockTags.NEEDS_IRON_TOOL);

        ProvidedTagBuilder<RegistryKey<Block>, Block> needsDiamondTool = builder(BlockTags.NEEDS_DIAMOND_TOOL);

        // ------------------- BEACON BASE -------------------
        build(BlockTags.BEACON_BASE_BLOCKS,
                Blocks.COPPER_BLOCK,
                ModBlocks.ADAMANTIUM_BLOCK,
                ModBlocks.TIN_BLOCK,
                ModBlocks.MYTHRIL_BLOCK,
                ModBlocks.ONYX_BLOCK
        );

        // ------------------- DOORS -------------------
        ProvidedTagBuilder<RegistryKey<Block>, Block> doorsTagBuilder = builder(BlockTags.DOORS);

        ProvidedTagBuilder<RegistryKey<Block>, Block> pressurePlatesTagBuilder = builder(BlockTags.PRESSURE_PLATES);
        ProvidedTagBuilder<RegistryKey<Block>, Block> slabsTagBuilder = builder(BlockTags.SLABS);
        ProvidedTagBuilder<RegistryKey<Block>, Block> stairsTagBuilder = builder(BlockTags.STAIRS);

        ModBlocks.registeredBlockItems.forEach((identifier, blockItem) -> {
            Block block = blockItem.getBlock();

            String path = identifier.getPath();

            build(pickaxeMineable, block);

            if(path.contains("block")) {
                build(storageBlocksTag, block);
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
            else if(block instanceof StairsBlock) {
                build(stairsTagBuilder, block);
            }
            else if(block instanceof PaneBlock) {
                build(BlockTags.DRAGON_IMMUNE, block);
            }
        });
    }
    
    private void build(TagKey<Block> tagKey, Object... objects) {
        ProvidedTagBuilder<RegistryKey<Block>, Block> builder = builder(tagKey);
        
        build(builder, objects);
    }

    private void build(ProvidedTagBuilder<RegistryKey<Block>, Block> builder,
                       Object... objects) {
        for (Object object : objects) {

            if(object instanceof Block block) {
                builder
                        .add(RegistryKey.of(Registries.BLOCK.getKey(), Registries.BLOCK.getId(block)));
            } else if(object instanceof TagKey<?> tag && object.getClass().getGenericSuperclass() == Block.class) {
                builder
                        .addTag((TagKey<Block>) tag);
            }
        }
    }
}