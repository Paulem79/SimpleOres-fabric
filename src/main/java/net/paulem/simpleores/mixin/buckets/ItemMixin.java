package net.paulem.simpleores.mixin.buckets;

//? if hasBucketlib || !containsBucket {

/*import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.world.item.Item;

@Mixin(value = Item.class)
public abstract class ItemMixin {}
 
*///?} else {

import net.minecraft.world.item.Item;
//? if afterDeobf {
import net.minecraft.world.item.ItemStackTemplate;
//?} else {
 /*import net.minecraft.world.item.ItemStack;
*///?}
import net.paulem.simpleores.items.custom.bucket.CustomBucketItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Item.class)
public abstract class ItemMixin {
    @Inject(
            method = "getCraftingRemainder",
            at = @At("HEAD"),
            cancellable = true
    )
    public void use(
            CallbackInfoReturnable<//? if afterDeobf {
                    ItemStackTemplate
                    //?} else {
                     /*ItemStack
                    *///?}
                    > cir
    ) {
        Item item = (Item) (Object) this;

        if (!(item instanceof CustomBucketItem customBucketItem)) return;

        cir.setReturnValue(//? afterDeobf
                ItemStackTemplate.fromNonEmptyStack(
                        customBucketItem.getEmpty()
                        //? afterDeobf
                )
        );
    }
}
//?}