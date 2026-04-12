package net.paulem.simpleores.mixin.buckets;

//? if hasBucketlib || !containsBucket {
/*
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.world.item.BucketItem;

@Mixin(value = BucketItem.class, priority = 1500)
public abstract class BucketItemMixin {}
 */
//?} else {

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.paulem.simpleores.items.custom.bucket.CustomBucketItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// This mixin should not be first in HEAD
@Mixin(value = BucketItem.class, priority = 1500)
public abstract class BucketItemMixin extends Item {
    private BucketItemMixin(Properties properties) {
        super(properties);
    }

    @Inject(
            method = "use",
            at = @At("HEAD"),
            cancellable = true
    )
    public void use(
            Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir
    ) {
        ItemStack itemStack = player.getItemInHand(hand);
        Item item = itemStack.getItem();

        if (!(item instanceof CustomBucketItem)) return;

        cir.setReturnValue(super.use(level, player, hand));
    }
}
//?}