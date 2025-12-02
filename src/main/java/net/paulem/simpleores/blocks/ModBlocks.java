package net.paulem.simpleores.blocks;

import com.google.common.base.Function;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.paulem.simpleores.SimpleOres;
import net.paulem.simpleores.blocks.custom.MultifunctionPressurePlateBlock;
import net.minecraft.world.level.block.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.paulem.simpleores.stonecutter.SCAccess;
import net.paulem.simpleores.stonecutter.SCId;
import net.paulem.simpleores.tooltip.TooltipBlockItem;
import net.paulem.simpleores.tooltip.TooltipBlock;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;

public class ModBlocks {
    public static final LinkedHashMap<ResourceLocation, BlockItem> registeredBlockItems = new LinkedHashMap<>();

    private static BlockBehaviour.Properties SCBlockSettings(ResourceKey<@NotNull Block> key, BlockBehaviour.Properties settings) {
        return settings
                //? if >1.21
                .setId(key)
        ;
    }

    // RAW METAL BLOCKS
    public static final Block RAW_TIN_BLOCK = registerBlock("raw_tin_block", key ->
            new Block(SCBlockSettings(key, BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .sound(SoundType.STONE)
                    .strength(4.0F, 6.0F)
                    .requiresCorrectToolForDrops())));
    public static final Block RAW_MYTHRIL_BLOCK = registerBlock("raw_mythril_block", key ->
            new Block(SCBlockSettings(key, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE)
                    .sound(SoundType.STONE)
                    .strength(7.0F, 6.0F)
                    .requiresCorrectToolForDrops())));
    public static final Block RAW_ADAMANTIUM_BLOCK = registerBlock("raw_adamantium_block", key ->
            new Block(SCBlockSettings(key, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN)
                    .sound(SoundType.STONE)
                    .strength(7.0F, 12.0F)
                    .requiresCorrectToolForDrops())));

    // METAL BLOCKS
    public static final Block TIN_BLOCK = registerBlock("tin_block", key ->
            new Block(SCBlockSettings(key, BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .sound(SoundType.METAL)
                    .strength(4.0F, 6.0F)
                    .requiresCorrectToolForDrops())));
    public static final Block MYTHRIL_BLOCK = registerBlock("mythril_block", key ->
            new Block(SCBlockSettings(key, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE)
                    .sound(SoundType.METAL)
                    .strength(7.0F, 6.0F)
                    .requiresCorrectToolForDrops())));
    public static final Block ADAMANTIUM_BLOCK = registerBlock("adamantium_block", key ->
            new Block(SCBlockSettings(key, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN)
                    .sound(SoundType.METAL)
                    .strength(7.0F, 12.0F)
                    .requiresCorrectToolForDrops())));
    public static final Block ONYX_BLOCK = registerBlock("onyx_block", key ->
            new Block(SCBlockSettings(key, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK)
                    .sound(SoundType.METAL)
                    .strength(20.0F, 100.0F)
                    .requiresCorrectToolForDrops())));

    public static DropExperienceBlock makeExperienceDroppingBlock(IntProvider intProvider, BlockBehaviour.Properties settings) {
        //? if >1.20.1 {
        return new DropExperienceBlock(intProvider, settings);
        //?} else {
        /*return new DropExperienceBlock(settings, intProvider);
        *///?}
    }

    // ORE BLOCKS
    public static final DropExperienceBlock TIN_ORE = registerBlock("tin_ore", key ->
            makeExperienceDroppingBlock(UniformInt.of(2, 5), SCBlockSettings(key, BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(3.0F)
            )));
    public static final DropExperienceBlock DEEPSLATE_TIN_ORE = registerBlock("deepslate_tin_ore", key ->
            makeExperienceDroppingBlock(UniformInt.of(2, 5), SCBlockSettings(key, BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .sound(SoundType.DEEPSLATE)
                    .requiresCorrectToolForDrops()
                    .strength(3.0F)
            )));
    public static final DropExperienceBlock MYTHRIL_ORE = registerBlock("mythril_ore", key ->
            makeExperienceDroppingBlock(UniformInt.of(2, 5), SCBlockSettings(key, BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(4.0F, 3.0F)
            )));
    public static final DropExperienceBlock DEEPSLATE_MYTHRIL_ORE = registerBlock("deepslate_mythril_ore", key ->
            makeExperienceDroppingBlock(UniformInt.of(2, 5), SCBlockSettings(key, BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .sound(SoundType.DEEPSLATE)
                    .requiresCorrectToolForDrops()
                    .strength(4.0F, 3.0F)
            )));
    public static final DropExperienceBlock ADAMANTIUM_ORE = registerBlock("adamantium_ore", key ->
            makeExperienceDroppingBlock(UniformInt.of(2, 5), SCBlockSettings(key, BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(5.0F, 3.0F)
            )));
    public static final DropExperienceBlock DEEPSLATE_ADAMANTIUM_ORE = registerBlock("deepslate_adamantium_ore", key ->
            makeExperienceDroppingBlock(UniformInt.of(2, 5), SCBlockSettings(key, BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .sound(SoundType.DEEPSLATE)
                    .requiresCorrectToolForDrops()
                    .strength(5.0F, 3.0F)
            )));
    public static final DropExperienceBlock ONYX_ORE = registerBlock("onyx_ore", key ->
            makeExperienceDroppingBlock(UniformInt.of(9, 14), SCBlockSettings(key, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK)
                    .requiresCorrectToolForDrops()
                    .strength(7.0F, 3.0F)
            )));
    
    private static final SCAccess<Block, BlockBehaviour.Properties> copyAccess = new SCAccess<>(//? if >1.20.1 {
            BlockBehaviour.Properties::ofFullCopy
    //?} else {
            /*BlockBehaviour.Properties::copy
    *///?}
    );

    // Blocks - bricks - Simple Ores
//    public static Block COPPER_BRICKS = registerBlock("copper_bricks", key ->
//            new Block(SCBlockSettings(key, copyAccess.get(Blocks.COPPER_BLOCK))));
    public static Block TIN_BRICKS = registerBlock("tin_bricks", key ->
            new Block(SCBlockSettings(key, copyAccess.get(TIN_BLOCK))));
    public static Block ONYX_BRICKS = registerBlock("onyx_bricks", key ->
            new Block(SCBlockSettings(key, copyAccess.get(ONYX_BLOCK))));
    public static Block ADAMANTIUM_BRICKS = registerBlock("adamantium_bricks", key ->
            new Block(SCBlockSettings(key, copyAccess.get(ADAMANTIUM_BLOCK))));
    public static Block MYTHRIL_BRICKS = registerBlock("mythril_bricks", key ->
            new Block(SCBlockSettings(key, copyAccess.get(MYTHRIL_BLOCK))));

    // blocks - slabs
    public static SlabBlock TIN_BRICK_SLAB = registerBlock("tin_brick_slab", key ->
            new SlabBlock(SCBlockSettings(key, copyAccess.get(TIN_BRICKS))));
    public static SlabBlock ONYX_BRICK_SLAB = registerBlock("onyx_brick_slab", key ->
            new SlabBlock(SCBlockSettings(key, copyAccess.get(ONYX_BRICKS))));
    public static SlabBlock MYTHRIL_BRICK_SLAB = registerBlock("mythril_brick_slab", key ->
            new SlabBlock(SCBlockSettings(key, copyAccess.get(MYTHRIL_BRICKS))));
    public static SlabBlock ADAMANTIUM_BRICK_SLAB = registerBlock("adamantium_brick_slab", key ->
            new SlabBlock(SCBlockSettings(key, copyAccess.get(ADAMANTIUM_BRICKS))));

    // Blocks - stairs - simpleores
//    public static StairBlock copper_brick_stairs = registerBlock("copper_brick_stairs", key ->
//           new StairBlock(COPPER_BRICKS.defaultBlockState(),
//                   SCBlockSettings(key, copyAccess.get(COPPER_BRICKS))));
    public static StairBlock tin_brick_stairs = registerBlock("tin_brick_stairs", key ->
            new StairBlock(TIN_BRICKS.defaultBlockState(),
                    SCBlockSettings(key, copyAccess.get(TIN_BRICKS))));
    public static StairBlock onyx_brick_stairs = registerBlock("onyx_brick_stairs", key ->
            new StairBlock(ONYX_BRICKS.defaultBlockState(),
                    SCBlockSettings(key, copyAccess.get(ONYX_BRICKS))));
    public static StairBlock adamantium_brick_stairs = registerBlock("adamantium_brick_stairs", key ->
            new StairBlock(ADAMANTIUM_BRICKS.defaultBlockState(),
                    SCBlockSettings(key, copyAccess.get(ADAMANTIUM_BRICKS))));
    public static StairBlock mythril_brick_stairs = registerBlock("mythril_brick_stairs", key ->
            new StairBlock(MYTHRIL_BRICKS.defaultBlockState(),
                    SCBlockSettings(key, copyAccess.get(MYTHRIL_BRICKS))));

    public static DoorBlock makeDoor(BlockSetType blockSetType, BlockBehaviour.Properties settings) {
        //? if >1.20.1 {
        return new DoorBlock(blockSetType, settings);
        //?} else {
        /*return new DoorBlock(settings, blockSetType);
        *///?}
    }

    // Blocks - doors - simpleores
    // Already present on 1.21+
    //? if <1.21 {
    /*public static DoorBlock copper_door = registerBlock("copper_door", key ->
            makeDoor(BlockSetType.IRON, BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE, MaterialColor.COLOR_ORANGE)
                    .requiresCorrectToolForDrops().strength(3.0F).noOcclusion()/^Removed push reaction^/));
    *///?}
    public static DoorBlock tin_door = registerBlock("tin_door", key ->
            makeDoor(BlockSetType.IRON, SCBlockSettings(key, BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .requiresCorrectToolForDrops().strength(4.0F).noOcclusion().pushReaction(PushReaction.DESTROY))));
    public static DoorBlock adamantium_door = registerBlock("adamantium_door", key ->
            makeDoor(BlockSetType.IRON, SCBlockSettings(key, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN)
                    .requiresCorrectToolForDrops().strength(7.0F).noOcclusion().pushReaction(PushReaction.DESTROY))));
    public static DoorBlock onyx_door = registerBlock("onyx_door", key ->
            makeDoor(BlockSetType.STONE, SCBlockSettings(key, BlockBehaviour.Properties.of().mapColor(MapColor.PODZOL)
                    .requiresCorrectToolForDrops().strength(20.0F).noOcclusion().pushReaction(PushReaction.DESTROY))));
    public static DoorBlock mythril_door = registerBlock("mythril_door", key ->
            makeDoor(BlockSetType.IRON, SCBlockSettings(key, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE)
                    .requiresCorrectToolForDrops().strength(7.0F).noOcclusion().pushReaction(PushReaction.DESTROY))));

    // Blocks - bars - simpleores
    //? if !hasCopperTools {
    public static IronBarsBlock copper_bars = registerBlock("copper_bars", key ->
            new IronBarsBlock(SCBlockSettings(key, BlockBehaviour.Properties.of().mapColor(MapColor.NONE)
                    .strength(3.0F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion())));
    //?}
    public static IronBarsBlock tin_bars = registerBlock("tin_bars", key ->
            new IronBarsBlock(SCBlockSettings(key, BlockBehaviour.Properties.of().mapColor(MapColor.NONE)
                    .strength(4.0F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion())));
    public static IronBarsBlock onyx_bars = registerBlock("onyx_bars", key ->
            new IronBarsBlock(SCBlockSettings(key, BlockBehaviour.Properties.of().mapColor(MapColor.NONE)
                    .strength(20.0F).requiresCorrectToolForDrops().sound(SoundType.STONE).noOcclusion())));
    public static IronBarsBlock adamantium_bars = registerBlock("adamantium_bars", key ->
            new IronBarsBlock(SCBlockSettings(key, BlockBehaviour.Properties.of().mapColor(MapColor.NONE)
                    .strength(7.0F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion())));
    public static IronBarsBlock mythril_bars = registerBlock("mythril_bars", key ->
            new IronBarsBlock(SCBlockSettings(key, BlockBehaviour.Properties.of().mapColor(MapColor.NONE)
                    .strength(7.0F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion())));

    // Blocks - pressure plates
    public static final MultifunctionPressurePlateBlock copper_pressure_plate = registerBlock("copper_pressure_plate", key ->
            new MultifunctionPressurePlateBlock(15, MultifunctionPressurePlateBlock.Sensitivity.LIVING_WEIGHTED, 10,
                    SCBlockSettings(key, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE)
                            .noCollission().strength(0.5F).sound(SoundType.COPPER)), BlockSetType.IRON));

    public static final MultifunctionPressurePlateBlock tin_pressure_plate = registerBlock("tin_pressure_plate", key ->
            new MultifunctionPressurePlateBlock(15, MultifunctionPressurePlateBlock.Sensitivity.EVERYTHING_WEIGHTED, 10,
                    SCBlockSettings(key, BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                            .noCollission().strength(0.5F).sound(SoundType.METAL)), BlockSetType.IRON));

    public static final MultifunctionPressurePlateBlock mythril_pressure_plate = registerBlock("mythril_pressure_plate", key ->
            new MultifunctionPressurePlateBlock(75, MultifunctionPressurePlateBlock.Sensitivity.MOBS_WEIGHTED, 10,
                    SCBlockSettings(key, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE)
                            .noCollission().strength(0.5F).sound(SoundType.METAL)),BlockSetType.GOLD));

    public static final MultifunctionPressurePlateBlock adamantium_pressure_plate = registerBlock("adamantium_pressure_plate", key ->
            new MultifunctionPressurePlateBlock(75, MultifunctionPressurePlateBlock.Sensitivity.EVERYTHING_WEIGHTED, 10,
                    SCBlockSettings(key, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN)
                            .noCollission().strength(0.5F).sound(SoundType.METAL)),BlockSetType.GOLD));

    public static final MultifunctionPressurePlateBlock onyx_pressure_plate = registerBlock("onyx_pressure_plate", key ->
            new MultifunctionPressurePlateBlock(15, MultifunctionPressurePlateBlock.Sensitivity.PLAYERS, 20,
                    SCBlockSettings(key, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK)
                            .noCollission().strength(0.5F).sound(SoundType.STONE)), BlockSetType.STONE));

    public static<T extends Block> T registerBlock(String name, Function<ResourceKey<@NotNull Block>, T> func) {
        ResourceLocation identifier = SCId.of(SimpleOres.MOD_ID, name);

        ResourceKey<@NotNull Block> key = ResourceKey.create(BuiltInRegistries.BLOCK.key(), SCId.of(SimpleOres.MOD_ID, name));
        T block = func.apply(key);
        
        BlockItem blockItem = registerBlockItem(block, identifier);
        registeredBlockItems.put(identifier, blockItem);
        return Registry.register(BuiltInRegistries.BLOCK, identifier, block);
    }

    public static<T extends Block> BlockItem registerBlockItem(T block, ResourceLocation identifier) {
        ResourceKey<@NotNull Item> key = ResourceKey.create(BuiltInRegistries.ITEM.key(), identifier);

        Item.Properties properties = new Item.Properties()
                //? if >1.21
                .setId(key)
        ;
        BlockItem blockItem = block instanceof TooltipBlock tooltipBlock ? new TooltipBlockItem(tooltipBlock, properties) : new BlockItem(block, properties);

        return Registry.register(BuiltInRegistries.ITEM, identifier,
                blockItem);
    }

    public static void init() {
        SimpleOres.LOGGER.info("Registering blocks...");
    }
}
