package ovh.paulem.simpleores.tint;

import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.minecraft.client.render.item.tint.TintSource;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.ColorHelper;
import ovh.paulem.simpleores.items.custom.bucket.CustomChildrenBucketItem;

public record ChildrenBucketTintSourceAdapter(CustomChildrenBucketItem bucket) {
    public TintSource getTintSource() {
        Fluid fluid = bucket.getFluid();

        int color = ColorHelper.withAlpha(255, 0xFFFFFF); // Default color is white
        FluidRenderHandler fluidRenderHandler;
        if (fluid != Fluids.EMPTY && (fluidRenderHandler = FluidRenderHandlerRegistry.INSTANCE.get(fluid)) != null) {
            color = ColorHelper.withAlpha(255, fluidRenderHandler.getFluidColor(null, null, fluid.getDefaultState()));
        }

        return new ChildrenBucketTintSource(color);
    }
}
