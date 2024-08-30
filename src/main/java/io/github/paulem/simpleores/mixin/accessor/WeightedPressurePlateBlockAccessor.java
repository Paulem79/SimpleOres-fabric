package io.github.paulem.simpleores.mixin.accessor;

import net.minecraft.block.WeightedPressurePlateBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(WeightedPressurePlateBlock.class)
public interface WeightedPressurePlateBlockAccessor {
    @Accessor
    int getWeight();
}