package net.paulem.simpleores.items.custom.bucket;

//? if hasBucketlib || !containsBucket {
/*public class CustomChildrenBucketItem {}
*///?} else {

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.material.Fluids;
import org.jspecify.annotations.NonNull;

import java.util.Objects;

// https://discord.com/channels/507304429255393322/507982478276034570/1481037865998614609
// https://discord.com/channels/507304429255393322/507982478276034570/1481039564582682776
public class CustomChildrenBucketItem extends BucketItem implements CustomBucketFluidable {
    @Nullable
    private final CustomParentBucketItem parent;
    private final Fluid fluid;

    public CustomChildrenBucketItem(Fluid fluid, Item.Properties settings, CustomParentBucketItem parent) {
        super(fluid, settings.component(DataComponents.ITEM_MODEL, parent.getModelWithOverlay(fluid)));

        this.parent = parent;
        this.fluid = fluid;
        DispenserBlock.registerBehavior(this, CustomBucketDispenseBehaviour.getInstance());
    }

    public CustomChildrenBucketItem(Fluid fluid, Item.Properties settings) {
        super(fluid, settings);

        this.parent = null;
        this.fluid = fluid;
        DispenserBlock.registerBehavior(this, CustomBucketDispenseBehaviour.getInstance());
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel world, Entity entity, @Nullable EquipmentSlot slot) {
        super.inventoryTick(stack, world, entity, slot);

        DataComponentMap components;

        if(parent != null && Objects.equals((components = stack.getComponents()).get(DataComponents.ITEM_MODEL), parent.getModelWithOverlay(fluid))) {
            DataComponentMap newComponents = DataComponentMap.builder()
                    .addAll(components)
                    .set(DataComponents.ITEM_MODEL, parent.getModelWithOverlay(fluid))
                    .build();

            stack.applyComponents(newComponents);
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
        return BuiltInRegistries.FLUID.getKey(fluid).getPath();
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
    public @NonNull Component getName(@NonNull ItemStack stack) {
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
                BuiltInRegistries.FLUID.getKey(fluid).getPath().contains("water");
    }
}
//?}
