package ovh.paulem.simpleores.datagen.providers.tags;

/*? if >=1.21.6 {*/
/*import net.minecraft.data.tag.ProvidedTagBuilder;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
*//*?}*/
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

        build(ConventionalBlockTags.ORES, ModTags.Blocks.SIMPLEORES_ORES);

        TagBuilder storageBlocksTag = new TagBuilder(ConventionalBlockTags.STORAGE_BLOCKS);

        // ------------------- BLOCKS BREAK -------------------

        TagBuilder pickaxeMineable = new TagBuilder(BlockTags.PICKAXE_MINEABLE);

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
        TagBuilder builder = new TagBuilder(tagKey);
        
        build(builder, objects);
    }

    private void build(TagBuilder builder,
                       Object... objects) {
        //? if >=1.21.6 {
        /*for (Object object : objects) {

            if(object instanceof Block block) {
                builder.get()
                        .add(RegistryKey.of(Registries.BLOCK.getKey(), Registries.BLOCK.getId(block)));
            } else if(object instanceof TagKey<?> tag && object.getClass().getGenericSuperclass() == Block.class) {
                builder.get()
                        .addTag((TagKey<Block>) tag);
            }
        }
        *///?} else {
            for (Object object : objects) {
                if (object instanceof Block block) {
                    builder.get().add(block);
                } else if (object instanceof TagKey<?> tag) {
                    builder.get().addTag((TagKey<Block>) tag);
                }
            }
        //?}
    }

    class TagBuilder {
        private final TagKey<Block> tag;

        public TagBuilder(TagKey<Block> tag) {
            this.tag = tag;
        }

        public /*? if >=1.21.6 {*//*ProvidedTagBuilder<RegistryKey<Block>, Block>*//*?} else {*/FabricTagProvider<Block>.FabricTagBuilder/*?}*/ get() {
            /*? if >=1.21.6 {*/
            /*return builder(tag);
            *//*?} else {*/
            return getOrCreateTagBuilder(tag);
            /*?}*/
        }
    }
}