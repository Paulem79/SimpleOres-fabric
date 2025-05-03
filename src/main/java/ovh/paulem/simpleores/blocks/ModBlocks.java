package ovh.paulem.simpleores.blocks;

import com.google.common.base.Function;
import net.minecraft.registry.RegistryKey;
import ovh.paulem.simpleores.SimpleOres;
import ovh.paulem.simpleores.blocks.custom.MultifunctionPressurePlateBlock;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.util.LinkedHashMap;

public class ModBlocks {
    public static final LinkedHashMap<Identifier, BlockItem> registeredBlockItems = new LinkedHashMap<>();

    // RAW METAL BLOCKS
    public static final Block RAW_TIN_BLOCK = registerBlock("raw_tin_block", key ->
            new Block(AbstractBlock.Settings.create()
                    .registryKey(key)
                    .mapColor(MapColor.IRON_GRAY)
                    .sounds(BlockSoundGroup.STONE)
                    .strength(4.0F, 6.0F)
                    .requiresTool()));
    public static final Block RAW_MYTHRIL_BLOCK = registerBlock("raw_mythril_block", key ->
            new Block(AbstractBlock.Settings.create()
                    .registryKey(key)
                    .mapColor(MapColor.BLUE)
                    .sounds(BlockSoundGroup.STONE)
                    .strength(7.0F, 6.0F)
                    .requiresTool()));
    public static final Block RAW_ADAMANTIUM_BLOCK = registerBlock("raw_adamantium_block", key ->
            new Block(AbstractBlock.Settings.create()
                    .registryKey(key)
                    .mapColor(MapColor.GREEN)
                    .sounds(BlockSoundGroup.STONE)
                    .strength(7.0F, 12.0F)
                    .requiresTool()));

    // METAL BLOCKS
    public static final Block TIN_BLOCK = registerBlock("tin_block", key ->
            new Block(AbstractBlock.Settings.create()
                    .registryKey(key)
                    .mapColor(MapColor.IRON_GRAY)
                    .sounds(BlockSoundGroup.METAL)
                    .strength(4.0F, 6.0F)
                    .requiresTool()));
    public static final Block MYTHRIL_BLOCK = registerBlock("mythril_block", key ->
            new Block(AbstractBlock.Settings.create()
                    .registryKey(key)
                    .mapColor(MapColor.BLUE)
                    .sounds(BlockSoundGroup.METAL)
                    .strength(7.0F, 6.0F)
                    .requiresTool()));
    public static final Block ADAMANTIUM_BLOCK = registerBlock("adamantium_block", key ->
            new Block(AbstractBlock.Settings.create()
                    .registryKey(key)
                    .mapColor(MapColor.GREEN)
                    .sounds(BlockSoundGroup.METAL)
                    .strength(7.0F, 12.0F)
                    .requiresTool()));
    public static final Block ONYX_BLOCK = registerBlock("onyx_block", key ->
            new Block(AbstractBlock.Settings.create()
                    .registryKey(key)
                    .mapColor(MapColor.BLACK)
                    .sounds(BlockSoundGroup.METAL)
                    .strength(20.0F, 100.0F)
                    .requiresTool()));

    // ORE BLOCKS
    public static final ExperienceDroppingBlock TIN_ORE = registerBlock("tin_ore", key ->
            new ExperienceDroppingBlock(UniformIntProvider.create(2, 5), AbstractBlock.Settings.create()
                    .registryKey(key)
                    .mapColor(MapColor.STONE_GRAY)
                    .requiresTool()
                    .strength(3.0F)
            ));
    public static final ExperienceDroppingBlock DEEPSLATE_TIN_ORE = registerBlock("deepslate_tin_ore", key ->
            new ExperienceDroppingBlock(UniformIntProvider.create(2, 5), AbstractBlock.Settings.create()
                    .registryKey(key)
                    .mapColor(MapColor.STONE_GRAY)
                    .sounds(BlockSoundGroup.DEEPSLATE)
                    .requiresTool()
                    .strength(3.0F)
            ));
    public static final ExperienceDroppingBlock MYTHRIL_ORE = registerBlock("mythril_ore", key ->
            new ExperienceDroppingBlock(UniformIntProvider.create(2, 5), AbstractBlock.Settings.create()
                    .registryKey(key)
                    .mapColor(MapColor.STONE_GRAY)
                    .requiresTool()
                    .strength(4.0F, 3.0F)
            ));
    public static final ExperienceDroppingBlock DEEPSLATE_MYTHRIL_ORE = registerBlock("deepslate_mythril_ore", key ->
            new ExperienceDroppingBlock(UniformIntProvider.create(2, 5), AbstractBlock.Settings.create()
                    .registryKey(key)
                    .mapColor(MapColor.STONE_GRAY)
                    .sounds(BlockSoundGroup.DEEPSLATE)
                    .requiresTool()
                    .strength(4.0F, 3.0F)
            ));
    public static final ExperienceDroppingBlock ADAMANTIUM_ORE = registerBlock("adamantium_ore", key ->
            new ExperienceDroppingBlock(UniformIntProvider.create(2, 5), AbstractBlock.Settings.create()
                    .registryKey(key)
                    .mapColor(MapColor.STONE_GRAY)
                    .requiresTool()
                    .strength(5.0F, 3.0F)
            ));
    public static final ExperienceDroppingBlock DEEPSLATE_ADAMANTIUM_ORE = registerBlock("deepslate_adamantium_ore", key ->
            new ExperienceDroppingBlock(UniformIntProvider.create(2, 5), AbstractBlock.Settings.create()
                    .registryKey(key)
                    .mapColor(MapColor.STONE_GRAY)
                    .sounds(BlockSoundGroup.DEEPSLATE)
                    .requiresTool()
                    .strength(5.0F, 3.0F)
            ));
    public static final ExperienceDroppingBlock ONYX_ORE = registerBlock("onyx_ore", key ->
            new ExperienceDroppingBlock(UniformIntProvider.create(9, 14), AbstractBlock.Settings.create()
                    .registryKey(key)
                    .mapColor(MapColor.BLACK)
                    .requiresTool()
                    .strength(7.0F, 3.0F)
            ));

    // Blocks - bricks - Simple Ores
//    public static Block copper_bricks = registerBlock("copper_bricks",
//            new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));
    public static Block TIN_BRICKS = registerBlock("tin_bricks", key ->
            new Block(AbstractBlock.Settings.copy(TIN_BLOCK).registryKey(key)));
    public static Block ONYX_BRICKS = registerBlock("onyx_bricks", key ->
            new Block(AbstractBlock.Settings.copy(ONYX_BLOCK).registryKey(key)));
    public static Block ADAMANTIUM_BRICKS = registerBlock("adamantium_bricks", key ->
            new Block(AbstractBlock.Settings.copy(ADAMANTIUM_BLOCK).registryKey(key)));
    public static Block MYTHRIL_BRICKS = registerBlock("mythril_bricks", key ->
            new Block(AbstractBlock.Settings.copy(MYTHRIL_BLOCK).registryKey(key)));

    // blocks - slabs
    public static SlabBlock TIN_BRICK_SLAB = registerBlock("tin_brick_slab", key ->
            new SlabBlock(AbstractBlock.Settings.copy(TIN_BRICKS).registryKey(key)));
    public static SlabBlock ONYX_BRICK_SLAB = registerBlock("onyx_brick_slab", key ->
            new SlabBlock(AbstractBlock.Settings.copy(ONYX_BRICKS).registryKey(key)));
    public static SlabBlock MYTHRIL_BRICK_SLAB = registerBlock("mythril_brick_slab", key ->
            new SlabBlock(AbstractBlock.Settings.copy(MYTHRIL_BRICKS).registryKey(key)));
    public static SlabBlock ADAMANTIUM_BRICK_SLAB = registerBlock("adamantium_brick_slab", key ->
            new SlabBlock(AbstractBlock.Settings.copy(ADAMANTIUM_BRICKS).registryKey(key)));

    // Blocks - stairs - simpleores
//    public static StairsBlock copper_brick_stairs = registerBlock("copper_brick_stairs",
//            new StairsBlock( copper_bricks.getDefaultState(),
//                                   BlockBehaviour.Properties.copy(copper_bricks)));
    public static StairsBlock tin_brick_stairs = registerBlock("tin_brick_stairs", key ->
            new StairsBlock(TIN_BRICKS.getDefaultState(),
                    AbstractBlock.Settings.copy(TIN_BRICKS).registryKey(key)));
    public static StairsBlock onyx_brick_stairs = registerBlock("onyx_brick_stairs", key ->
            new StairsBlock(ONYX_BRICKS.getDefaultState(),
                    AbstractBlock.Settings.copy(ONYX_BRICKS).registryKey(key)));
    public static StairsBlock adamantium_brick_stairs = registerBlock("adamantium_brick_stairs", key ->
            new StairsBlock(ADAMANTIUM_BRICKS.getDefaultState(),
                    AbstractBlock.Settings.copy(ADAMANTIUM_BRICKS).registryKey(key)));
    public static StairsBlock mythril_brick_stairs = registerBlock("mythril_brick_stairs", key ->
            new StairsBlock(MYTHRIL_BRICKS.getDefaultState(),
                    AbstractBlock.Settings.copy(MYTHRIL_BRICKS).registryKey(key)));

    // Blocks - doors - simpleores
    /*public static DoorBlock copper_door = registerBlock("copper_door", key ->
            new DoorBlock(BlockSetType.COPPER, AbstractBlock.Settings.create().registryKey(key).mapColor(MapColor.ORANGE)
                    .requiresTool().strength(3.0F).nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));*/ // Already present on 1.21+
    public static DoorBlock tin_door = registerBlock("tin_door", key ->
            new DoorBlock(BlockSetType.IRON, AbstractBlock.Settings.create().registryKey(key).mapColor(MapColor.IRON_GRAY)
                    .requiresTool().strength(4.0F).nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
    public static DoorBlock adamantium_door = registerBlock("adamantium_door", key ->
            new DoorBlock(BlockSetType.IRON, AbstractBlock.Settings.create().registryKey(key).mapColor(MapColor.GREEN)
                    .requiresTool().strength(7.0F).nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
    public static DoorBlock onyx_door = registerBlock("onyx_door", key ->
            new DoorBlock(BlockSetType.STONE, AbstractBlock.Settings.create().registryKey(key).mapColor(MapColor.SPRUCE_BROWN)
                    .requiresTool().strength(20.0F).nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
    public static DoorBlock mythril_door = registerBlock("mythril_door", key ->
            new DoorBlock(BlockSetType.IRON, AbstractBlock.Settings.create().registryKey(key).mapColor(MapColor.BLUE)
                    .requiresTool().strength(7.0F).nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));

    // Blocks - bars - simpleores
    public static PaneBlock copper_bars = registerBlock("copper_bars", key ->
            new PaneBlock(AbstractBlock.Settings.create().registryKey(key).mapColor(MapColor.CLEAR)
                    .strength(3.0F).requiresTool().sounds(BlockSoundGroup.METAL).nonOpaque()));
    public static PaneBlock tin_bars = registerBlock("tin_bars", key ->
            new PaneBlock(AbstractBlock.Settings.create().registryKey(key).mapColor(MapColor.CLEAR)
                    .strength(4.0F).requiresTool().sounds(BlockSoundGroup.METAL).nonOpaque()));
    public static PaneBlock onyx_bars = registerBlock("onyx_bars", key ->
            new PaneBlock(AbstractBlock.Settings.create().registryKey(key).mapColor(MapColor.CLEAR)
                    .strength(20.0F).requiresTool().sounds(BlockSoundGroup.STONE).nonOpaque()));
    public static PaneBlock adamantium_bars = registerBlock("adamantium_bars", key ->
            new PaneBlock(AbstractBlock.Settings.create().registryKey(key).mapColor(MapColor.CLEAR)
                    .strength(7.0F).requiresTool().sounds(BlockSoundGroup.METAL).nonOpaque()));
    public static PaneBlock mythril_bars = registerBlock("mythril_bars", key ->
            new PaneBlock(AbstractBlock.Settings.create().registryKey(key).mapColor(MapColor.CLEAR)
                    .strength(7.0F).requiresTool().sounds(BlockSoundGroup.METAL).nonOpaque()));

    // Blocks - pressure plates
    public static final MultifunctionPressurePlateBlock copper_pressure_plate = registerBlock("copper_pressure_plate", key ->
            new MultifunctionPressurePlateBlock(15, MultifunctionPressurePlateBlock.Sensitivity.LIVING_WEIGHTED, 10,
                    AbstractBlock.Settings.create().registryKey(key).mapColor(MapColor.ORANGE)
                            .noCollision().strength(0.5F).sounds(BlockSoundGroup.COPPER), BlockSetType.IRON));

    public static final MultifunctionPressurePlateBlock tin_pressure_plate = registerBlock("tin_pressure_plate", key ->
            new MultifunctionPressurePlateBlock(15, MultifunctionPressurePlateBlock.Sensitivity.EVERYTHING_WEIGHTED, 10,
                    AbstractBlock.Settings.create().registryKey(key).mapColor(MapColor.IRON_GRAY)
                            .noCollision().strength(0.5F).sounds(BlockSoundGroup.METAL), BlockSetType.IRON));

    public static final MultifunctionPressurePlateBlock mythril_pressure_plate = registerBlock("mythril_pressure_plate", key ->
            new MultifunctionPressurePlateBlock(75, MultifunctionPressurePlateBlock.Sensitivity.MOBS_WEIGHTED, 10,
                    AbstractBlock.Settings.create().registryKey(key).mapColor(MapColor.BLUE)
                            .noCollision().strength(0.5F).sounds(BlockSoundGroup.METAL),BlockSetType.GOLD));

    public static final MultifunctionPressurePlateBlock adamantium_pressure_plate = registerBlock("adamantium_pressure_plate", key ->
            new MultifunctionPressurePlateBlock(75, MultifunctionPressurePlateBlock.Sensitivity.EVERYTHING_WEIGHTED, 10,
                    AbstractBlock.Settings.create().registryKey(key).mapColor(MapColor.GREEN)
                            .noCollision().strength(0.5F).sounds(BlockSoundGroup.METAL),BlockSetType.GOLD));

    public static final MultifunctionPressurePlateBlock onyx_pressure_plate = registerBlock("onyx_pressure_plate", key ->
            new MultifunctionPressurePlateBlock(15, MultifunctionPressurePlateBlock.Sensitivity.PLAYERS, 20,
                    AbstractBlock.Settings.create().registryKey(key).mapColor(MapColor.BLACK)
                            .noCollision().strength(0.5F).sounds(BlockSoundGroup.STONE), BlockSetType.STONE));

    public static<T extends Block> T registerBlock(String name, Function<RegistryKey<Block>, T> func) {
        Identifier identifier = Identifier.of(SimpleOres.MOD_ID, name);

        RegistryKey<Block> key = RegistryKey.of(Registries.BLOCK.getKey(), Identifier.of(SimpleOres.MOD_ID, name));
        T block = func.apply(key);
        
        BlockItem blockItem = registerBlockItem(block, identifier);
        registeredBlockItems.put(identifier, blockItem);
        return Registry.register(Registries.BLOCK, identifier, block);
    }

    public static<T extends Block> BlockItem registerBlockItem(T block, Identifier identifier) {
        RegistryKey<Item> key = RegistryKey.of(Registries.ITEM.getKey(), identifier);
        
        return Registry.register(Registries.ITEM, identifier,
                new BlockItem(block, new Item.Settings().registryKey(key)));
    }

    public static void init() {
        SimpleOres.LOGGER.info("Registering blocks...");
    }
}
