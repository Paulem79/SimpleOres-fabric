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

@Environment(EnvType.CLIENT)
public record ChildrenBucketTintSource(int defaultColor) implements TintSource {
    public static final MapCodec<ChildrenBucketTintSource> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(Codecs.RGB.fieldOf("default").forGetter(ChildrenBucketTintSource::defaultColor)).apply(instance, ChildrenBucketTintSource::new)
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
