package net.paulem.simpleores.mixin.buckets;

//? if hasBucketlib || !containsBucket {

import net.minecraft.world.inventory.FurnaceFuelSlot;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FurnaceFuelSlot.class)
public abstract class FurnaceFuelSlotMixin {}

//?} else {

/*import net.minecraft.world.inventory.FurnaceFuelSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.paulem.simpleores.items.custom.bucket.CustomBucketItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/^*
 * The fuel slot of a furnace accepts an empty vanilla bucket, so it accepts an empty custom bucket too.
 * This is what lets the empty bucket left by a burnt bucket of lava stay in the slot.
 ^/
@Mixin(FurnaceFuelSlot.class)
public abstract class FurnaceFuelSlotMixin {

    @Inject(method = "isBucket", at = @At("HEAD"), cancellable = true)
    private static void simpleores$isCustomBucket(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (!(stack.getItem() instanceof CustomBucketItem bucketItem)) return;

        if (bucketItem.toVanillaBucket(stack).is(Items.BUCKET)) {
            cir.setReturnValue(true);
        }
    }
}
*///?}
