package net.paulem.simpleores.mixin.buckets;

//? if hasBucketlib || !containsBucket {

import org.spongepowered.asm.mixin.Mixin;
//? if >=26.2 {
import net.minecraft.world.entity.Bucketable;
//?} else {
//import net.minecraft.world.entity.animal.Bucketable;
//?}

@Mixin(Bucketable.class)
public interface BucketableMixin {}
 
//?} else {

/*import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity. //? <26.2
        //animal.
        Bucketable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.material.Fluid;
import net.paulem.simpleores.items.custom.bucket.CustomBucketItem;
import net.paulem.simpleores.utils.BucketFluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Bucketable.class)
public interface BucketableMixin {

    @Inject(
            method = "bucketMobPickup",
            at = @At("HEAD"),
            cancellable = true
    )
    private static <T extends LivingEntity & Bucketable> void onBucketMobPickup(
            Player player,
            InteractionHand hand,
            T pickupEntity,
            CallbackInfoReturnable<Optional<InteractionResult>> cir
    ) {
        ItemStack itemStack = player.getItemInHand(hand);
        Item item = itemStack.getItem();

        if (!(item instanceof CustomBucketItem bucketItem)) return;

        ItemStack vanillaBucketStack = pickupEntity.getBucketItemStack();

        // Modded bucketable entities may not use a vanilla BucketItem for their bucket
        Fluid requiredFluid = BucketFluids.contentOf(vanillaBucketStack);
        if(requiredFluid == null) return;

        Fluid fluid = bucketItem.getFluid(itemStack);

        // Anything else is left to vanilla and to the other mods, we must not cancel it
        if(!fluid.isSame(requiredFluid) || bucketItem.holdsEntity(itemStack) || !pickupEntity.isAlive()) return;

        pickupEntity.playSound(pickupEntity.getPickupSound(), 1.0F, 1.0F);

        // mix() returns a new stack of one, the held stack is decremented by createFilledResult
        ItemStack finalBucket = CustomBucketItem.mix(vanillaBucketStack, itemStack);

        pickupEntity.saveToBucketTag(finalBucket);
        ItemStack result = ItemUtils.createFilledResult(itemStack, player, finalBucket, false);

        player.setItemInHand(hand, result);
        if (player instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.FILLED_BUCKET.trigger(serverPlayer, finalBucket);
        }

        pickupEntity.discard();
        cir.setReturnValue(Optional.of(InteractionResult.SUCCESS));
    }
}
*///?}