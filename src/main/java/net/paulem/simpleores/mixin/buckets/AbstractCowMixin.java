package net.paulem.simpleores.mixin.buckets;

//? if hasBucketlib || !containsBucket {
/*import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.world.entity.animal //? if >1.21.10
        .cow
        //? if <1.21.5 {
        /^.Cow;
        ^///?} else {
        .AbstractCow;
        //?}

@Mixin(//? if <1.21.5 {
        /^Cow
        ^///?} else {
        AbstractCow
        //?}
.class)
public abstract class AbstractCowMixin {}
*///?} else {

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal //? if >1.21.10
        .cow
        .AbstractCow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.paulem.simpleores.SimpleOres;
import net.paulem.simpleores.items.custom.bucket.CustomBucketItem;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractCow.class)
public abstract class AbstractCowMixin {

    @Inject(
            method = "mobInteract",
            at = @At("HEAD"),
            cancellable = true
    )
    public void mobInteract(
            Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir
    ) {
        ItemStack itemStack = player.getItemInHand(hand);
        Item item = itemStack.getItem();

        if (!(item instanceof CustomBucketItem bucketItem)) return;

        if (!SimpleOres.CONFIG.enableCopperBucketMilking()) return;

        AbstractCow cow = (AbstractCow) (Object) this;

        // Anything else is left to vanilla and to the other mods, we must not cancel it
        if (!bucketItem.isEmpty(itemStack) || cow.isBaby()) return;

        player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
        // getMilkBucket() is a new stack of one, the held stack is decremented by createFilledResult
        ItemStack bucketOrMilkBucket = ItemUtils.createFilledResult(itemStack, player, bucketItem.getMilkBucket());
        player.setItemInHand(hand, bucketOrMilkBucket);
        cir.setReturnValue(InteractionResult.SUCCESS);
    }
}
//?}