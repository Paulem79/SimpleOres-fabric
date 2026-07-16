package net.paulem.simpleores.mixin.buckets;

//? if hasBucketlib || !containsBucket {
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.world.entity.animal //? if >1.21.10
        .cow
        //? if <1.21.5 {
        /*.Cow;
        *///?} else {
        .AbstractCow;
        //?}

@Mixin(//? if <1.21.5 {
        /*Cow
        *///?} else {
        AbstractCow
        //?}
.class)
public abstract class AbstractCowMixin {}
//?} else {

/*import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal //? if >1.21.10
        .cow
        .AbstractCow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.paulem.simpleores.items.custom.bucket.CustomBucketItem;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractCow.class)
public abstract class AbstractCowMixin extends Animal {

    private AbstractCowMixin(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

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

        AbstractCow cow = (AbstractCow) (Object) this;
        if (bucketItem.isEmpty(itemStack) && !cow.isBaby()) {
            player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
            ItemStack bucketOrMilkBucket = ItemUtils.createFilledResult(itemStack, player, bucketItem.getMilkBucket());
            player.setItemInHand(hand, bucketOrMilkBucket);
            cir.setReturnValue(InteractionResult.SUCCESS);
        } else {
            cir.setReturnValue(super.mobInteract(player, hand));
        }
    }
}
*///?}