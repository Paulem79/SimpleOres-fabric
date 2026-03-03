package net.paulem.simpleores.blocks;

import com.google.common.base.Function;
import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.paulem.simpleores.SimpleOres;
import net.paulem.simpleores.blocks.custom.MultifunctionPressurePlateBlock;
import net.paulem.simpleores.stonecutter.SCAccess;
import net.paulem.simpleores.stonecutter.SCId;
import net.paulem.simpleores.tooltip.TooltipBlockItem;
import net.paulem.simpleores.tooltip.TooltipBlock;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;

public class ModBlocks {
    public static final LinkedHashMap<Identifier, BlockItem> registeredBlockItems = new LinkedHashMap<>();

    // RAW METAL BLOCKS
    public static final Block RAW_TIN_BLOCK = registerBlock("raw_tin_block", 
            new Block(SCBlockSettings(key, BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE, MaterialColor.METAL)
                    .sound(SoundType.STONE)
                    .strength(4.0F, 6.0F)
                    .requiresCorrectToolForDrops())));
    public static final Block RAW_MYTHRIL_BLOCK = registerBlock("raw_mythril_block", 
            new Block(SCBlockSettings(key, BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE, MaterialColor.COLOR_BLUE)
                    .sound(SoundType.STONE)
                    .strength(7.0F, 6.0F)
                    .requiresCorrectToolForDrops())));
    public static final Block RAW_ADAMANTIUM_BLOCK = registerBlock("raw_adamantium_block", 
            new Block(SCBlockSettings(key, BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE, MaterialColor.COLOR_GREEN)
                    .sound(SoundType.STONE)
                    .strength(7.0F, 12.0F)
                    .requiresCorrectToolForDrops())));

    // METAL BLOCKS
    public static final Block TIN_BLOCK = registerBlock("tin_block", 
            new Block(SCBlockSettings(key, BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE, MaterialColor.METAL)
                    .sound(SoundType.METAL)
                    .strength(4.0F, 6.0F)
                    .requiresCorrectToolForDrops())));
    public static final Block MYTHRIL_BLOCK = registerBlock("mythril_block", 
            new Block(SCBlockSettings(key, BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE, MaterialColor.COLOR_BLUE)
                    .sound(SoundType.METAL)
                    .strength(7.0F, 6.0F)
                    .requiresCorrectToolForDrops())));
    public static final Block ADAMANTIUM_BLOCK = registerBlock("adamantium_block", 
            new Block(SCBlockSettings(key, BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE, MaterialColor.COLOR_GREEN)
                    .sound(SoundType.METAL)
                    .strength(7.0F, 12.0F)
                    .requiresCorrectToolForDrops())));
    public static final Block ONYX_BLOCK = registerBlock("onyx_block", 
            new Block(SCBlockSettings(key, BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE, MaterialColor.COLOR_BLACK)
                    .sound(SoundType.METAL)
                    .strength(20.0F, 100.0F)
                    .requiresCorrectToolForDrops())));

    public static OreBlock makeExperienceDroppingBlock(IntProvider intProvider, BlockBehaviour.Properties settings) {
        //? if >1.20.1 {
        /*return new OreBlock(intProvider, settings);
        *///?} else {
        return new OreBlock(settings, intProvider);
        //?}
    }

    // ORE BLOCKS
    public static final OreBlock TIN_ORE = registerBlock("tin_ore", 
            makeExperienceDroppingBlock(UniformInt.of(2, 5), SCBlockSettings(key, BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE, MaterialColor.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(3.0F)
            )));
    public static final OreBlock DEEPSLATE_TIN_ORE = registerBlock("deepslate_tin_ore", 
            makeExperienceDroppingBlock(UniformInt.of(2, 5), SCBlockSettings(key, BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE, MaterialColor.STONE)
                    .sound(SoundType.DEEPSLATE)
                    .requiresCorrectToolForDrops()
                    .strength(3.0F)
            )));
    public static final OreBlock MYTHRIL_ORE = registerBlock("mythril_ore", 
            makeExperienceDroppingBlock(UniformInt.of(2, 5), SCBlockSettings(key, BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE, MaterialColor.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(4.0F, 3.0F)
            )));
    public static final OreBlock DEEPSLATE_MYTHRIL_ORE = registerBlock("deepslate_mythril_ore", 
            makeExperienceDroppingBlock(UniformInt.of(2, 5), SCBlockSettings(key, BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE, MaterialColor.STONE)
                    .sound(SoundType.DEEPSLATE)
                    .requiresCorrectToolForDrops()
                    .strength(4.0F, 3.0F)
            )));
    public static final OreBlock ADAMANTIUM_ORE = registerBlock("adamantium_ore", 
            makeExperienceDroppingBlock(UniformInt.of(2, 5), SCBlockSettings(key, BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE, MaterialColor.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(5.0F, 3.0F)
            )));
    public static final OreBlock DEEPSLATE_ADAMANTIUM_ORE = registerBlock("deepslate_adamantium_ore", 
            makeExperienceDroppingBlock(UniformInt.of(2, 5), SCBlockSettings(key, BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE, MaterialColor.STONE)
                    .sound(SoundType.DEEPSLATE)
                    .requiresCorrectToolForDrops()
                    .strength(5.0F, 3.0F)
            )));
    public static final OreBlock ONYX_ORE = registerBlock("onyx_ore", 
            makeExperienceDroppingBlock(UniformInt.of(9, 14), SCBlockSettings(key, BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE, MaterialColor.COLOR_BLACK)
                    .requiresCorrectToolForDrops()
                    .strength(7.0F, 3.0F)
            )));
    
    private static final SCAccess<Block, BlockBehaviour.Properties> copyAccess = new SCAccess<>(//? if >1.20.1 {
            /*BlockBehaviour.Properties::ofFullCopy
    *///?} else {
            BlockBehaviour.Properties::copy
    //?}
    );

    // Blocks - bricks - Simple Ores
//    public static Block COPPER_BRICKS = registerBlock("copper_bricks", 
//            new Block(SCBlockSettings(key, copyAccess.get(Blocks.COPPER_BLOCK))));
    public static Block TIN_BRICKS = registerBlock("tin_bricks", 
            new Block(SCBlockSettings(key, copyAccess.get(TIN_BLOCK))));
    public static Block ONYX_BRICKS = registerBlock("onyx_bricks", 
            new Block(SCBlockSettings(key, copyAccess.get(ONYX_BLOCK))));
    public static Block ADAMANTIUM_BRICKS = registerBlock("adamantium_bricks", 
            new Block(SCBlockSettings(key, copyAccess.get(ADAMANTIUM_BLOCK))));
    public static Block MYTHRIL_BRICKS = registerBlock("mythril_bricks", 
            new Block(SCBlockSettings(key, copyAccess.get(MYTHRIL_BLOCK))));

    // blocks - slabs
    public static SlabBlock TIN_BRICK_SLAB = registerBlock("tin_brick_slab", 
            new SlabBlock(SCBlockSettings(key, copyAccess.get(TIN_BRICKS))));
    public static SlabBlock ONYX_BRICK_SLAB = registerBlock("onyx_brick_slab", 
            new SlabBlock(SCBlockSettings(key, copyAccess.get(ONYX_BRICKS))));
    public static SlabBlock MYTHRIL_BRICK_SLAB = registerBlock("mythril_brick_slab", 
            new SlabBlock(SCBlockSettings(key, copyAccess.get(MYTHRIL_BRICKS))));
    public static SlabBlock ADAMANTIUM_BRICK_SLAB = registerBlock("adamantium_brick_slab", 
            new SlabBlock(SCBlockSettings(key, copyAccess.get(ADAMANTIUM_BRICKS))));

    // Blocks - stairs - simpleores
//    public static StairBlock copper_brick_stairs = registerBlock("copper_brick_stairs", 
//           new StairBlock(COPPER_BRICKS.defaultBlockState(),
//                   SCBlockSettings(key, copyAccess.get(COPPER_BRICKS))));
    public static StairBlock tin_brick_stairs = registerBlock("tin_brick_stairs", 
            new StairBlock(TIN_BRICKS.defaultBlockState(),
                    SCBlockSettings(key, copyAccess.get(TIN_BRICKS))));
    public static StairBlock onyx_brick_stairs = registerBlock("onyx_brick_stairs", 
            new StairBlock(ONYX_BRICKS.defaultBlockState(),
                    SCBlockSettings(key, copyAccess.get(ONYX_BRICKS))));
    public static StairBlock adamantium_brick_stairs = registerBlock("adamantium_brick_stairs", 
            new StairBlock(ADAMANTIUM_BRICKS.defaultBlockState(),
                    SCBlockSettings(key, copyAccess.get(ADAMANTIUM_BRICKS))));
    public static StairBlock mythril_brick_stairs = registerBlock("mythril_brick_stairs", 
            new StairBlock(MYTHRIL_BRICKS.defaultBlockState(),
                    SCBlockSettings(key, copyAccess.get(MYTHRIL_BRICKS))));

    public static DoorBlock makeDoor(BlockSetType blockSetType, BlockBehaviour.Properties settings) {
        //? if >1.20.1 {
        /*return new DoorBlock(blockSetType, settings);
        *///?} else {
        return new DoorBlock(settings, blockSetType);
        //?}
    }

    // Blocks - doors - simpleores
    // Already present on 1.21+
    //? if <1.21 {
    public static DoorBlock copper_door = registerBlock("copper_door", 
            makeDoor(BlockSetType.IRON, BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE, MaterialColor.COLOR_ORANGE)
                    .requiresCorrectToolForDrops().strength(3.0F).noOcclusion()/*Removed push reaction*/));
    //?}
    public static DoorBlock tin_door = registerBlock("tin_door", 
            makeDoor(BlockSetType.IRON, SCBlockSettings(key, BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE, MaterialColor.METAL)
                    .requiresCorrectToolForDrops().strength(4.0F).noOcclusion()/*Removed push reaction*/)));
    public static DoorBlock adamantium_door = registerBlock("adamantium_door", 
            makeDoor(BlockSetType.IRON, SCBlockSettings(key, BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE, MaterialColor.COLOR_GREEN)
                    .requiresCorrectToolForDrops().strength(7.0F).noOcclusion()/*Removed push reaction*/)));
    public static DoorBlock onyx_door = registerBlock("onyx_door", 
            makeDoor(BlockSetType.STONE, SCBlockSettings(key, BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE, MaterialColor.PODZOL)
                    .requiresCorrectToolForDrops().strength(20.0F).noOcclusion()/*Removed push reaction*/)));
    public static DoorBlock mythril_door = registerBlock("mythril_door", 
            makeDoor(BlockSetType.IRON, SCBlockSettings(key, BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE, MaterialColor.COLOR_BLUE)
                    .requiresCorrectToolForDrops().strength(7.0F).noOcclusion()/*Removed push reaction*/)));

    // Blocks - bars - simpleores
    public static IronBarsBlock copper_bars = registerBlock("copper_bars", 
            new IronBarsBlock(SCBlockSettings(key, BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE, MaterialColor.NONE)
                    .strength(3.0F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion())));
    public static IronBarsBlock tin_bars = registerBlock("tin_bars", 
            new IronBarsBlock(SCBlockSettings(key, BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE, MaterialColor.NONE)
                    .strength(4.0F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion())));
    public static IronBarsBlock onyx_bars = registerBlock("onyx_bars", 
            new IronBarsBlock(SCBlockSettings(key, BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE, MaterialColor.NONE)
                    .strength(20.0F).requiresCorrectToolForDrops().sound(SoundType.STONE).noOcclusion())));
    public static IronBarsBlock adamantium_bars = registerBlock("adamantium_bars", 
            new IronBarsBlock(SCBlockSettings(key, BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE, MaterialColor.NONE)
                    .strength(7.0F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion())));
    public static IronBarsBlock mythril_bars = registerBlock("mythril_bars", 
            new IronBarsBlock(SCBlockSettings(key, BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE, MaterialColor.NONE)
                    .strength(7.0F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion())));

    // Blocks - pressure plates
    public static final MultifunctionPressurePlateBlock copper_pressure_plate = registerBlock("copper_pressure_plate", 
            new MultifunctionPressurePlateBlock(15, MultifunctionPressurePlateBlock.Sensitivity.LIVING_WEIGHTED, 10,
                    SCBlockSettings(key, BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE, MaterialColor.COLOR_ORANGE)
                            .noCollission().strength(0.5F).sound(SoundType.COPPER)), BlockSetType.IRON));

    public static final MultifunctionPressurePlateBlock tin_pressure_plate = registerBlock("tin_pressure_plate", 
            new MultifunctionPressurePlateBlock(15, MultifunctionPressurePlateBlock.Sensitivity.EVERYTHING_WEIGHTED, 10,
                    SCBlockSettings(key, BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE, MaterialColor.METAL)
                            .noCollission().strength(0.5F).sound(SoundType.METAL)), BlockSetType.IRON));

    public static final MultifunctionPressurePlateBlock mythril_pressure_plate = registerBlock("mythril_pressure_plate", 
            new MultifunctionPressurePlateBlock(75, MultifunctionPressurePlateBlock.Sensitivity.MOBS_WEIGHTED, 10,
                    SCBlockSettings(key, BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE, MaterialColor.COLOR_BLUE)
                            .noCollission().strength(0.5F).sound(SoundType.METAL)),BlockSetType.GOLD));

    public static final MultifunctionPressurePlateBlock adamantium_pressure_plate = registerBlock("adamantium_pressure_plate", 
            new MultifunctionPressurePlateBlock(75, MultifunctionPressurePlateBlock.Sensitivity.EVERYTHING_WEIGHTED, 10,
                    SCBlockSettings(key, BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE, MaterialColor.COLOR_GREEN)
                            .noCollission().strength(0.5F).sound(SoundType.METAL)),BlockSetType.GOLD));

    public static final MultifunctionPressurePlateBlock onyx_pressure_plate = registerBlock("onyx_pressure_plate", 
            new MultifunctionPressurePlateBlock(15, MultifunctionPressurePlateBlock.Sensitivity.PLAYERS, 20,
                    SCBlockSettings(key, BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE, MaterialColor.COLOR_BLACK)
                            .noCollission().strength(0.5F).sound(SoundType.STONE)), BlockSetType.STONE));
    
    private static int id = 0;

    public static<T extends Block> T registerBlock(int id, String name, Block block) {
        Identifier identifier = new Identifier(SimpleOres.MOD_ID, name);
        
        BlockItem blockItem = registerBlockItem(block, identifier);
        registeredBlockItems.put(identifier, blockItem);
        id++;
        
        return Block.REGISTRY.add(id, identifier, block);
    }

    public static<T extends Block> BlockItem registerBlockItem(T block, Identifier identifier) {
        BlockItem blockItem = new BlockItem(block);

        Item.REGISTRY.add(Block.getIdByBlock(block), Block.REGISTRY.getIdentifier(block), blockItem);
        return Item.BLOCK_ITEMS.put(block, blockItem);
    }

    public static void init() {
        SimpleOres.LOGGER.info("Registering blocks...");
    }
}
