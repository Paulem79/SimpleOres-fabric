package net.paulem.simpleores.mixin;

import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.paulem.simpleores.items.custom.advanced.AdvancedShearsItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Inject(method = //? if >1.21.11 {
            "is(Ljava/util/function/Predicate;)Z"
            //?} else {
            /*"is(Lnet/minecraft/world/item/Item;)Z"
            *///? }
            , at = @At("HEAD"), cancellable = true)
    private void simpleores$acceptAdvancedShears(//? if >1.21.11 {
            Predicate<Holder<Item>> item
            //?} else {
            /*Item item
            *///? }
            , CallbackInfoReturnable<Boolean> cir
    ) {
        ItemStack self = (ItemStack) (Object) this;

        if (item == Items.SHEARS && self.getItem() instanceof AdvancedShearsItem) {
            cir.setReturnValue(true);
        }
    }
}
