package ovh.paulem.simpleores.mixin.buckets;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.item.BucketItem;

//? if !hasBucketlib {
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ovh.paulem.simpleores.items.custom.bucket.CustomBucketFluidable;
//?}

@Mixin(BucketItem.class)
public class BucketItemMixin {
    //? if !hasBucketlib {
    @Inject(method = "getEmptiedStack", at = @At("RETURN"), cancellable = true)
    private static void injected(ItemStack stack, PlayerEntity player, CallbackInfoReturnable<ItemStack> cir) {
        cir.setReturnValue(getEmptiedStack(stack, player, cir));
    }

    @Unique
    private static ItemStack getEmptiedStack(ItemStack stack, PlayerEntity player, CallbackInfoReturnable<ItemStack> cir) {
        return !player.isInCreativeMode() && stack.getItem() instanceof CustomBucketFluidable bucketItem ? new ItemStack(bucketItem.getParent()) : cir.getReturnValue();
    }
    //?}
}
