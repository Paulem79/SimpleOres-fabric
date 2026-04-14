package net.paulem.simpleores.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class LevelUtils {
    public static boolean doWaterEvaporate(Level level, BlockPos pos) {
        return //? if >1.21.10 {
                level.environmentAttributes().getValue(net.minecraft.world.attribute.EnvironmentAttributes.WATER_EVAPORATES, pos);
        //?} else {
         /*level.dimensionType().ultraWarm();
        *///?}
    }
}
