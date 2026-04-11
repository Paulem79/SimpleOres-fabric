package net.paulem.simpleores.items.custom.bucket;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

public interface CustomBucketFluidable {
    CustomBucketItem getParent();

    String getBaseName();

    Fluid getFluid(ItemStack stack);

    ItemStack getCorrespondingBucket(Fluid modFluid);
}
