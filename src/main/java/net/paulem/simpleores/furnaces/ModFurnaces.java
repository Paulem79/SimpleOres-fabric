package net.paulem.simpleores.furnaces;

import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.paulem.simpleores.armors.ModArmorMaterials;
import net.paulem.simpleores.blocks.ModBlocks;
import net.paulem.simpleores.utils.ConcurrentFifoMap;
import net.paulem.simpleores.utils.MaterialUtils;
import net.paulem.simpleores.utils.TranslateUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.function.ToIntFunction;
import java.util.function.UnaryOperator;

public class ModFurnaces {
    private static final List<ModFurnaceBlock> allFurnaces = new ArrayList<>();

    public static final ConcurrentFifoMap<//$ armorRegistry
            net.minecraft.world.item.equipment.@org.jspecify.annotations.Nullable ArmorMaterial
            , Double> FURNACES = new ConcurrentFifoMap<>();

    static {
        FURNACES.put(ModArmorMaterials.COPPER, 1.2d);
        FURNACES.put(ModArmorMaterials.TIN, 1.5d);
        FURNACES.put(ModArmorMaterials.MYTHRIL, 2d);
        FURNACES.put(ModArmorMaterials.ADAMANTIUM, 3d);
        FURNACES.put(ModArmorMaterials.ONYX, 5d);
    }

    public static void init() {
        for (Map.Entry<//$ armorRegistry
                net.minecraft.world.item.equipment.@org.jspecify.annotations.Nullable ArmorMaterial
                , Double> entry : FURNACES.entrySet()) {
            registerFurnace(entry.getKey(), entry.getValue());
        }
    }

    public static Map<String, String> generateFurnaceTranslations(UnaryOperator<String> translateFunction, String locale) {
        Map<String, String> translates = new HashMap<>();

        for (Map.Entry<//$ armorRegistry
                net.minecraft.world.item.equipment.@org.jspecify.annotations.Nullable ArmorMaterial
                , Double> entry : FURNACES.entrySet()) {
            String id = getFurnaceName(entry.getKey());

            String translatedMaterialName = TranslateUtils.getTranslatedName(entry.getKey(), locale);
            String fullName = translateFunction.apply(translatedMaterialName);

            translates.put(id, fullName);
        }

        return translates;
    }

    public static String getFurnaceName(//$ armorRegistry
                                        net.minecraft.world.item.equipment.@org.jspecify.annotations.Nullable ArmorMaterial
                                                material) {
        return MaterialUtils.getName(material) + "_furnace";
    }

    private static void registerFurnace(//$ armorRegistry
                                        net.minecraft.world.item.equipment.@org.jspecify.annotations.Nullable ArmorMaterial
            material, double speedModifier) {
        Pair<Float, Float> strength = MaterialUtils.getStrength(material);
        String name = getFurnaceName(material);

        ModFurnaceBlock furnace = ModBlocks.registerBlock(name, key -> new ModFurnaceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                .lightLevel(createLightLevelFromBlockState(13))
                .strength(strength.getLeft(), strength.getRight())
                .requiresCorrectToolForDrops()
                //? if >1.21
                .setId(key)
                , speedModifier));

        allFurnaces.add(furnace);
    }

    public static Collection<ModFurnaceBlock> getFurnaces() {
        return allFurnaces;
    }

    private static ToIntFunction<BlockState> createLightLevelFromBlockState(int litLevel) {
        return blockState -> blockState.getValue(BlockStateProperties.LIT) ? litLevel : 0;
    }
}
