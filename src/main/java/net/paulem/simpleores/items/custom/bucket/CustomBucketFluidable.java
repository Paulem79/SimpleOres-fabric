package net.paulem.simpleores.items.custom.bucket;

import net.minecraft.fluid.Fluid;

public interface CustomBucketFluidable {
    CustomChildrenBucketItem fromFluid(Fluid fluid);

    CustomParentBucketItem getParent();

    String getFluidName();

    String getBaseName();
}
