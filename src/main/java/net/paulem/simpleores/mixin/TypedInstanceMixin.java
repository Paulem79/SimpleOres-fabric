package net.paulem.simpleores.mixin;

import net.minecraft.core.TypedInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.paulem.simpleores.items.custom.advanced.AdvancedShearsItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TypedInstance.class)
public interface TypedInstanceMixin<T> {
    @Inject(method = "is(Ljava/lang/Object;)Z", at = @At("HEAD"), cancellable = true)
    private void simpleores$isAdvancedShear(
            T rawType, CallbackInfoReturnable<Boolean> cir
    ) {
        TypedInstance<?> instance = (TypedInstance<?>) this;

        if(instance instanceof ItemStack self && rawType instanceof Item item) {
            if (item == Items.SHEARS && self.getItem() instanceof AdvancedShearsItem) {
                cir.setReturnValue(true);
            }
        }


    }
}
