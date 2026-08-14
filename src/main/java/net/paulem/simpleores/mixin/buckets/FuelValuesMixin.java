package net.paulem.simpleores.mixin.buckets;

//? if hasBucketlib || !containsBucket || afterDeobf {

import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;

// The fuels are stored in components on the newer versions, see CustomBucketItem#applyVanillaComponents
@Mixin(Item.class)
public abstract class FuelValuesMixin {}

//?} else {

/*import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.FuelValues;
import net.paulem.simpleores.items.custom.bucket.CustomBucketItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Makes a custom bucket burn in a furnace exactly like the vanilla bucket it holds the content of,
// a bucket of lava being the obvious case. The remainder (an empty bucket) is given back by
// CustomBucketItem#getRecipeRemainder, which the furnace uses through the Fabric item API.
@Mixin(FuelValues.class)
public abstract class FuelValuesMixin {

    @Inject(method = "isFuel", at = @At("HEAD"), cancellable = true)
    private void simpleores$isCustomBucketFuel(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        ItemStack vanillaBucket = simpleores$toVanillaBucket(stack);
        if(vanillaBucket == null) return;

        // The vanilla stack is never a custom bucket, so this does not recurse
        cir.setReturnValue(((FuelValues) (Object) this).isFuel(vanillaBucket));
    }

    @Inject(method = "burnDuration", at = @At("HEAD"), cancellable = true)
    private void simpleores$customBucketBurnDuration(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        ItemStack vanillaBucket = simpleores$toVanillaBucket(stack);
        if(vanillaBucket == null) return;

        cir.setReturnValue(((FuelValues) (Object) this).burnDuration(vanillaBucket));
    }

    private static ItemStack simpleores$toVanillaBucket(ItemStack stack) {
        if(!(stack.getItem() instanceof CustomBucketItem bucketItem)) return null;

        ItemStack vanillaBucket = bucketItem.toVanillaBucket(stack);
        return vanillaBucket.isEmpty() ? null : vanillaBucket;
    }
}
*///?}
