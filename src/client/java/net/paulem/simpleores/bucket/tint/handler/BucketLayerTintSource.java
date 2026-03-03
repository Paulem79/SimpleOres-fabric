package net.paulem.simpleores.bucket.tint.handler;

//? if hasBucketlib || !containsBucket {
public class BucketLayerTintSource {}
//?} else {

/*import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;
import net.paulem.simpleores.bucket.tint.ClientBucketUtil;
import net.paulem.simpleores.items.custom.bucket.CustomChildrenBucketItem;

/^*
 * Represents a tint for a bucket layer, corresponding to one pixel in the texture.
 ^/
@Environment(EnvType.CLIENT)
public record BucketLayerTintSource(int defaultColor, int x, int y) implements ItemTintSource {
    public static final MapCodec<BucketLayerTintSource> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance
                    .group(
                            ExtraCodecs.RGB_COLOR_CODEC.fieldOf("default")
                                    .forGetter(BucketLayerTintSource::defaultColor),
                            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("x")
                                    .forGetter(BucketLayerTintSource::x),
                            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("y")
                                    .forGetter(BucketLayerTintSource::y)
                    )
                    .apply(instance, BucketLayerTintSource::new)
    );

    @Override
    public int calculate(ItemStack stack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity) {
        Fluid fluid = ClientBucketUtil.getContainedFluid(stack);
        if(CustomChildrenBucketItem.isWaterLike(fluid) || (x == Integer.MAX_VALUE && y == Integer.MAX_VALUE)) {
            return ClientBucketUtil.getWaterLikeColor(fluid, defaultColor);
        }

        return ClientBucketUtil.getColorAt(fluid, defaultColor, x, y);
    }

    @Override
    public MapCodec<? extends ItemTintSource> type() {
        return CODEC;
    }
}
*///?}
