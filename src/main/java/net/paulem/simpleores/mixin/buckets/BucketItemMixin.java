package net.paulem.simpleores.mixin.buckets;

import net.paulem.simpleores.items.custom.bucket.CustomBucketItem;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;

@Mixin(BucketItem.class)
public class BucketItemMixin {
    //? if containsBucket && !hasBucketlib {
    /*@Inject(method = "getEmptySuccessItem", at = @At("RETURN"), cancellable = true)
    private static void injected(ItemStack stack, Player player, CallbackInfoReturnable<ItemStack> cir) {
        cir.setReturnValue(getEmptiedStack(stack, player, cir));
    }

    @Unique
    private static ItemStack getEmptiedStack(ItemStack stack, Player player, CallbackInfoReturnable<ItemStack> cir) {
        return !player.hasInfiniteMaterials() && stack.getItem() instanceof CustomBucketItem bucketItem ? bucketItem.getEmpty() : cir.getReturnValue();
    }*/
    //?}
}