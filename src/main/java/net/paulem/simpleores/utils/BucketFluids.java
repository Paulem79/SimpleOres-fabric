package net.paulem.simpleores.utils;

//? if hasBucketlib || !containsBucket {
public class BucketFluids {}
//?} else {

/*import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.paulem.simpleores.mixin.accessor.BucketItemAccessor;
import org.jspecify.annotations.Nullable;

/^*
 * Resolves the fluid held by any bucket like item, including the modded ones which do not extend
 * {@link BucketItem} but expose their content through the Fabric transfer API.
 ^/
public class BucketFluids {

    private BucketFluids() {}

    /^*
     * @return the fluid contained in the given stack, {@link Fluids#EMPTY} if it holds none,
     * or {@code null} if the content could not be resolved at all.
     ^/
    public static @Nullable Fluid contentOf(ItemStack stack) {
        if(stack.isEmpty()) return Fluids.EMPTY;

        if(stack.getItem() instanceof BucketItem bucketItem) {
            return ((BucketItemAccessor) bucketItem).getContent();
        }

        Storage<FluidVariant> storage = FluidStorage.ITEM.find(stack, ContainerItemContext.withConstant(stack));
        if(storage == null) return null;

        try (Transaction transaction = Transaction.openOuter()) {
            for (StorageView<FluidVariant> view : storage) {
                if(!view.isResourceBlank() && view.getAmount() > 0) {
                    return view.getResource().getFluid();
                }
            }
        }

        return Fluids.EMPTY;
    }
}
*///?}
