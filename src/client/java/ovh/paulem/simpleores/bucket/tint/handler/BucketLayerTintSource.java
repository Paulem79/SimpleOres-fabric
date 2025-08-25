package ovh.paulem.simpleores.bucket.tint.handler;

//? if hasBucketlib {
/*public class BucketLayerTintSource {}
*///?} else {

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.item.tint.TintSource;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.util.dynamic.Codecs;
import org.jetbrains.annotations.Nullable;
import ovh.paulem.simpleores.bucket.tint.ClientBucketUtil;
import ovh.paulem.simpleores.items.custom.bucket.CustomChildrenBucketItem;

/**
 * Represents a tint for a bucket layer, corresponding to one pixel in the texture.
 */
@Environment(EnvType.CLIENT)
public record BucketLayerTintSource(int defaultColor, int x, int y) implements TintSource {
    public static final MapCodec<BucketLayerTintSource> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance
                    .group(
                            Codecs.RGB.fieldOf("default")
                                    .forGetter(BucketLayerTintSource::defaultColor),
                            Codecs.NON_NEGATIVE_INT.fieldOf("x")
                                    .forGetter(BucketLayerTintSource::x),
                            Codecs.NON_NEGATIVE_INT.fieldOf("y")
                                    .forGetter(BucketLayerTintSource::y)
                    )
                    .apply(instance, BucketLayerTintSource::new)
    );

    @Override
    public int getTint(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity user) {
        Fluid fluid = ClientBucketUtil.getContainedFluid(stack);
        if(CustomChildrenBucketItem.isWaterLike(fluid) || (x == Integer.MAX_VALUE && y == Integer.MAX_VALUE)) {
            return ClientBucketUtil.getWaterLikeColor(fluid, defaultColor);
        }

        return ClientBucketUtil.getColorAt(fluid, defaultColor, x, y);
    }

    @Override
    public MapCodec<? extends TintSource> getCodec() {
        return CODEC;
    }
}
//?}
