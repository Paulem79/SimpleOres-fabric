package net.paulem.simpleores.mixin.buckets;

//? if hasBucketlib || !containsBucket {

/*import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Inventory.class)
public abstract class InventoryMixin {}

*///?} else {

import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.paulem.simpleores.items.custom.bucket.CustomBucketItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Lets the recipe book actually take a custom bucket out of the inventory when it fills the crafting grid
 * with the vanilla bucket a recipe asks for. Without this the recipe would be shown as craftable
 * (see {@link StackedItemContentsMixin}) but no item would be moved to the grid.
 */
@Mixin(Inventory.class)
public abstract class InventoryMixin {

    @Inject(
            method = "findSlotMatchingCraftingIngredient",
            at = @At("RETURN"),
            cancellable = true
    )
    private void simpleores$findCustomBucketSlot(Holder<Item> holder, ItemStack stackInSlot, CallbackInfoReturnable<Integer> cir) {
        // Vanilla found a matching stack, nothing to do
        if (cir.getReturnValue() != -1) return;

        Inventory inventory = (Inventory) (Object) this;

        for (int slot = 0; slot < inventory.getContainerSize(); slot++) {
            ItemStack candidate = inventory.getItem(slot);

            if (!(candidate.getItem() instanceof CustomBucketItem bucketItem)) continue;
            if (!Inventory.isUsableForCrafting(candidate)) continue;

            ItemStack vanillaBucket = bucketItem.toVanillaBucket(candidate);
            if (vanillaBucket.isEmpty() || !vanillaBucket.is(holder)) continue;

            // The grid already holds something: only the very same bucket can be added to it
            if (!stackInSlot.isEmpty() && !ItemStack.isSameItemSameComponents(stackInSlot, candidate)) continue;

            cir.setReturnValue(slot);
            return;
        }
    }
}
//?}
