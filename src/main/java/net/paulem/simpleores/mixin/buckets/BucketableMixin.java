package net.paulem.simpleores.mixin.buckets;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity. //? afterDeobf && <26.2
        //animal.
        Bucketable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.paulem.simpleores.items.custom.bucket.CustomBucketItem;
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

        Block block = bucketItem.getBlock(itemStack);

        ItemStack vanillaBucketStack = pickupEntity.getBucketItemStack();
        Item vanillaBucketItem = vanillaBucketStack.getItem();
        if(!(vanillaBucketItem instanceof BucketItem vanillaBucket)) return;

        Fluid fluid = block instanceof LiquidBlock liquidBlock ? liquidBlock.fluid : Fluids.EMPTY;

        if(fluid.isSame(vanillaBucket.getContent()) && !bucketItem.holdsEntity(itemStack) && pickupEntity.isAlive()) {
            pickupEntity.playSound(pickupEntity.getPickupSound(), 1.0F, 1.0F);

            ItemStack finalBucket = CustomBucketItem.mix(vanillaBucketStack, itemStack);

            pickupEntity.saveToBucketTag(finalBucket);
            ItemStack result = ItemUtils.createFilledResult(itemStack, player, finalBucket, false);

            player.setItemInHand(hand, result);
            Level level = pickupEntity.level();
            if (!level.isClientSide()) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer)player, finalBucket);
            }

            pickupEntity.discard();
            cir.setReturnValue(Optional.of(InteractionResult.SUCCESS));
        } else {
            cir.setReturnValue(Optional.empty());
        }
    }
}