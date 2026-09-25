package net.paulem.simpleores.utils;

//? if hasBucketlib || !containsBucket {
public class BucketFluids {}
//?} else {

/*//? if fabric {
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
//?} else if >=26.3 {
/^import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.fluid.FluidUtil;
^///?} else {
/^import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
^///?}
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.paulem.simpleores.mixin.accessor.BucketItemAccessor;
import org.jspecify.annotations.Nullable;

/^*
 * Resolves the fluid held by any bucket like item, including the modded ones which do not extend
 * {@link BucketItem} but expose their content through the fluid API of the loader.
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

        //? if fabric {
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
        //?} else if >=26.3 {
        /^if(stack.getCapability(Capabilities.Fluid.ITEM, ItemAccess.forStack(stack)) == null) return null;

        return FluidUtil.getFirstStackContained(stack).getFluid();
        ^///?} else {
        /^if(FluidUtil.getFluidHandler(stack).isEmpty()) return null;

        return FluidUtil.getFluidContained(stack).map(FluidStack::getFluid).orElse(Fluids.EMPTY);
        ^///?}
    }

    public static Component nameOf(Fluid fluid) {
        //? if fabric {
        return FluidVariantAttributes.getName(FluidVariant.of(fluid));
        //?} else {
        /^return fluid.getFluidType().getDescription();
        ^///?}
    }

    public static int temperatureOf(Fluid fluid) {
        //? if fabric {
        return FluidVariantAttributes.getTemperature(FluidVariant.of(fluid));
        //?} else {
        /^return fluid.getFluidType().getTemperature();
        ^///?}
    }
}
*///?}
