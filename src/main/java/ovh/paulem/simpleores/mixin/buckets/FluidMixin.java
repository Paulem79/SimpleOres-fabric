package ovh.paulem.simpleores.mixin.buckets;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.block.FluidBlock;

//? if !hasBucketlib {
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ovh.paulem.simpleores.items.custom.bucket.CustomBucketFluidable;
//?}

@Mixin({FluidBlock.class})
public abstract class FluidMixin {
    //? if !hasBucketlib {
    @Inject(method = "tryDrainFluid", at = @At("RETURN"), cancellable = true)
    private void injected(@Nullable LivingEntity drainer, WorldAccess world, BlockPos pos, BlockState state, CallbackInfoReturnable<ItemStack> cir) {
        cir.setReturnValue(getCorrespondingBucket(((FluidBlock) (Object) this).fluid, drainer, world, pos, state, cir));
    }

    @Unique
    private ItemStack getCorrespondingBucket(FlowableFluid fluid, @Nullable LivingEntity drainer, WorldAccess world, BlockPos pos, BlockState state, CallbackInfoReturnable<ItemStack> cir) {
        ItemStack hand = drainer == null ? null : drainer.getMainHandStack();
        Item handItem = hand == null ? null : hand.getItem();

        if(handItem instanceof CustomBucketFluidable bucketItem) {
            return new ItemStack(bucketItem.fromFluid(fluid));
        }

        return cir.getReturnValue();
    }
    //?}
}
