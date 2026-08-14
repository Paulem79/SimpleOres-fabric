package net.paulem.simpleores.mixin.buckets;

//? if hasBucketlib || !containsBucket {

/*import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;

// StackedItemContents does not exist on the versions without the custom bucket
@Mixin(Item.class)
public abstract class StackedItemContentsMixin {}

*///?} else {

import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.item.ItemStack;
import net.paulem.simpleores.items.custom.bucket.CustomBucketItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * The recipe book counts what the player owns by item, ignoring the components. A filled custom bucket is
 * therefore counted as the vanilla bucket it stands for, so that a recipe asking for a lava bucket is shown
 * as craftable when the player only carries a copper bucket filled with lava.
 */
@Mixin(StackedItemContents.class)
public abstract class StackedItemContentsMixin {

    @Inject(
            method = "accountStack(Lnet/minecraft/world/item/ItemStack;I)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void simpleores$accountAsVanillaBucket(ItemStack stack, int maxCount, CallbackInfo ci) {
        if (!(stack.getItem() instanceof CustomBucketItem bucketItem)) return;

        // An empty bucket is accounted as a vanilla empty bucket, like everywhere else
        ItemStack vanillaBucket = bucketItem.toVanillaBucket(stack);
        if (vanillaBucket.isEmpty()) return;

        vanillaBucket.setCount(stack.getCount());

        ci.cancel();
        // The vanilla stack is never a custom bucket, so this does not recurse
        ((StackedItemContents) (Object) this).accountStack(vanillaBucket, maxCount);
    }
}
//?}
