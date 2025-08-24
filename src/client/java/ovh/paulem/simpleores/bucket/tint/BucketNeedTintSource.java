package ovh.paulem.simpleores.bucket.tint;

//? if hasBucketlib {
/*public class ChildrenBucketTintSource {}*/
//?} else {

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

/**
 * Represents a tint source for fluids needing a tint, like water.
 */
@Environment(EnvType.CLIENT)
public record BucketNeedTintSource(int defaultColor) implements TintSource {
    public static final MapCodec<BucketNeedTintSource> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(Codecs.RGB.fieldOf("default").forGetter(BucketNeedTintSource::defaultColor)).apply(instance, BucketNeedTintSource::new)
    );

    @Override
    public int getTint(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity user) {
        Fluid fluid = ClientBucketUtil.getContainedFluid(stack);
        return ClientBucketUtil.getColorFromFluid(fluid, defaultColor);
    }

    @Override
    public MapCodec<? extends TintSource> getCodec() {
        return CODEC;
    }
}
//?}
