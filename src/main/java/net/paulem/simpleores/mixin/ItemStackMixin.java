package net.paulem.simpleores.mixin;

import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
//? if <=1.21.11 {
/*import net.minecraft.world.item.Items;
import net.paulem.simpleores.items.custom.advanced.AdvancedShearsItem;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;*/
//?}

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    //? if <=1.21.11 {
    /*@Inject(method = "is(Lnet/minecraft/world/item/Item;)Z", at = @At("HEAD"), cancellable = true)
    private void simpleores$acceptAdvancedShears(Item item, CallbackInfoReturnable<Boolean> cir
    ) {
        ItemStack self = (ItemStack) (Object) this;

        if (item == Items.SHEARS && self.getItem() instanceof AdvancedShearsItem) {
            cir.setReturnValue(true);
        }
    }*/
    //?}
}
