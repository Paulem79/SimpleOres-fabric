package net.paulem.simpleores.mixin.buckets;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity. //? afterDeobf && <26.2
        //animal.
        Bucketable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.Fluid;
import net.paulem.simpleores.items.ModComponents;
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

        if (!bucketItem.holdsFluid(itemStack)) return;

        Block block = bucketItem.getBlock(itemStack);

        if (!(block instanceof LiquidBlock liquidBlock)) return;

        Fluid fluid = liquidBlock.fluid;

        if(fluid.is(FluidTags.WATER) && pickupEntity.isAlive()) {
            pickupEntity.playSound(pickupEntity.getPickupSound(), 1.0F, 1.0F);

            ItemStack vanillaBucket = pickupEntity.getBucketItemStack();
            ItemStack finalBucket = CustomBucketItem.mix(vanillaBucket, itemStack);

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