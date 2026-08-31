package net.paulem.simpleores.mixin.buckets;

//? if hasBucketlib || !containsBucket {

import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Ingredient.class)
public abstract class IngredientMixin {}

//?} else {

/*import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.paulem.simpleores.items.custom.bucket.CustomBucketItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/^*
 * Makes the custom buckets usable in every recipe asking for the matching vanilla bucket: milk, water, lava,
 * powder snow, entity buckets and the empty bucket itself. Every recipe type goes through
 * {@link Ingredient#test(ItemStack)}, so the modded recipes are covered too.
 ^/
@Mixin(Ingredient.class)
public abstract class IngredientMixin {

    @Inject(
            method = "test(Lnet/minecraft/world/item/ItemStack;)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    private void simpleores$acceptCustomBuckets(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (!(stack.getItem() instanceof CustomBucketItem bucketItem)) return;

        ItemStack vanillaBucket = bucketItem.toVanillaBucket(stack);

        // The vanilla stack is never a custom bucket, so this does not recurse
        if (!vanillaBucket.isEmpty() && ((Ingredient) (Object) this).test(vanillaBucket)) {
            cir.setReturnValue(true);
        }
    }
}
*///?}
