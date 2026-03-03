package net.paulem.simpleores.mixin.buckets;

import org.spongepowered.asm.mixin.Mixin;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.paulem.simpleores.items.custom.bucket.CustomBucketFluidable;

@Mixin({LiquidBlock.class})
public abstract class FluidMixin {
    @Inject(method = "pickupBlock", at = @At("RETURN"), cancellable = true)
    private void injected(@Nullable LivingEntity drainer, LevelAccessor world, BlockPos pos, BlockState state, CallbackInfoReturnable<ItemStack> cir) {
        cir.setReturnValue(getCorrespondingBucket(((LiquidBlock) (Object) this).fluid, drainer, world, pos, state, cir));
    }

    @Unique
    private ItemStack getCorrespondingBucket(FlowingFluid fluid, @Nullable LivingEntity drainer, LevelAccessor world, BlockPos pos, BlockState state, CallbackInfoReturnable<ItemStack> cir) {
        ItemStack hand = drainer == null ? null : drainer.getMainHandItem();
        Item handItem = hand == null ? null : hand.getItem();

        if(handItem instanceof CustomBucketFluidable bucketItem) {
            return new ItemStack(bucketItem.fromFluid(fluid));
        }

        return cir.getReturnValue();
    }
}