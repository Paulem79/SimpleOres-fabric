package ovh.paulem.simpleores.items.custom.bucket;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;

public class CustomChildrenBucketItem extends BucketItem implements CustomBucketFluidable {
    private final CustomParentBucketItem parent;
    private final Fluid fluid;

    public CustomChildrenBucketItem(Fluid fluid, Settings settings, CustomParentBucketItem parent) {
        super(fluid, settings.component(DataComponentTypes.ITEM_MODEL, parent.getOverlayModelId()));

        this.parent = parent;
        this.fluid = fluid;
    }

    public CustomChildrenBucketItem(Fluid fluid, Settings settings) {
        super(fluid, settings);

        this.parent = null;
        this.fluid = fluid;
    }

    @Override
    public CustomParentBucketItem getParent() {
        if(parent == null) {
            throw new IllegalStateException("Accessing null parent! This children should be overridden if it's itself the parent !");
        }
        return parent;
    }

    @Override
    public String getFluidName() {
        return Registries.FLUID.getId(fluid).getPath();
    }

    @Override
    public String getBaseName() {
        return getParent().getBaseName();
    }

    @Override
    public CustomChildrenBucketItem fromFluid(Fluid fluid) {
        return getParent().fromFluid(fluid);
    }

    @Override
    public Text getName(ItemStack stack) {
        if(fluid == null || fluid == Fluids.EMPTY) {
            return getParent().getName(stack);
        }
        return getParent().getName(stack, FluidVariant.of(fluid).getFluid());
    }

    public Fluid getFluid() {
        return fluid;
    }

    @Override
    public String toString() {
        return "CustomChildrenBucketItem{" +
                "parent=" + parent +
                ", fluid=" + fluid +
                ", translationKey='" + translationKey + '\'' +
                "} " + super.toString();
    }
}
