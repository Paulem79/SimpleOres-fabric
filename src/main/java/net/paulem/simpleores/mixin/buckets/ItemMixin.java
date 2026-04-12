package net.paulem.simpleores.mixin.buckets;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.paulem.simpleores.items.custom.bucket.CustomBucketItem;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Debug(export = true)
// This mixin should not be first in HEAD
@Mixin(value = Item.class, priority = 1500)
public abstract class ItemMixin {
    @Inject(
            method = "getCraftingRemainder",
            at = @At("HEAD"),
            cancellable = true
    )
    public void use(
            CallbackInfoReturnable<ItemStackTemplate> cir
    ) {
        Item item = (Item) (Object) this;

        if (!(item instanceof CustomBucketItem customBucketItem)) return;

        cir.setReturnValue(ItemStackTemplate.fromNonEmptyStack(customBucketItem.getEmpty()));
    }
}