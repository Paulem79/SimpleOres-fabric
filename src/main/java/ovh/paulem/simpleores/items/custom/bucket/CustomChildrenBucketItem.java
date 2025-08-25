package ovh.paulem.simpleores.items.custom.bucket;

//? if hasBucketlib {
/*public class CustomChildrenBucketItem {}
*///?} else {

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class CustomChildrenBucketItem extends BucketItem implements CustomBucketFluidable {
    @Nullable
    private final CustomParentBucketItem parent;
    private final Fluid fluid;

    public CustomChildrenBucketItem(Fluid fluid, Settings settings, CustomParentBucketItem parent) {
        super(fluid, settings.component(DataComponentTypes.ITEM_MODEL, parent.getModelWithOverlay(fluid)));

        this.parent = parent;
        this.fluid = fluid;
    }

    public CustomChildrenBucketItem(Fluid fluid, Settings settings) {
        super(fluid, settings);

        this.parent = null;
        this.fluid = fluid;
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, @Nullable EquipmentSlot slot) {
        super.inventoryTick(stack, world, entity, slot);

        ComponentMap components;

        if(parent != null && Objects.equals((components = stack.getComponents()).get(DataComponentTypes.ITEM_MODEL), parent.getModelWithOverlay(fluid))) {
            ComponentMap newComponents = ComponentMap.builder()
                    .addAll(components)
                    .add(DataComponentTypes.ITEM_MODEL, parent.getModelWithOverlay(fluid))
                    .build();

            stack.applyComponentsFrom(newComponents);
        }
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

    public boolean isWaterLike() {
        return isWaterLike(fluid);
    }

    public static boolean isWaterLike(Fluid fluid) {
        return FluidVariant.of(fluid).isOf(Fluids.WATER) ||
                Registries.FLUID.getId(fluid).getPath().contains("water");
    }
}
//?}