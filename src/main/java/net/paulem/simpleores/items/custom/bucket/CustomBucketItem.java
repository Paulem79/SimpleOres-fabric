package net.paulem.simpleores.items.custom.bucket;

//? if hasBucketlib || !containsBucket {
/*public class CustomParentBucketItem {}
*///?} else {

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.paulem.simpleores.items.ModComponents;
import net.paulem.simpleores.items.ModItems;
import org.apache.commons.lang3.function.TriFunction;
import org.jspecify.annotations.NonNull;

public class CustomBucketItem extends CustomChildrenBucketItem implements CustomBucketFluidable {
    private final String baseName;
    private final ResourceKey<Item> key;
    private final TriFunction<CustomBucketItem, String, Fluid, CustomChildrenBucketItem> registrar;

    private CustomChildrenBucketItem children;

    public CustomBucketItem(ResourceKey<Item> key, String baseName, Item.Properties settings, TriFunction<CustomBucketItem, String, Fluid, CustomChildrenBucketItem> registrar) {
        super(Fluids.EMPTY, settings.component(ModComponents.BUCKET_FLUID_BLOCK_COMPONENT, getBlockIdentifier(Blocks.AIR)).setId(key));

        this.key = key;
        this.baseName = baseName;
        this.registrar = registrar;

        createChildren();
    }

    public void createChildren() {
        Fluid fluid = Fluids.EMPTY;
        String childrenName = baseName + "_filled";

        registrar.apply(this, childrenName, fluid);
    }

    @Override
    public ItemStack getCorrespondingBucket(Fluid modFluid) {
        if(modFluid == null || modFluid == Fluids.EMPTY) return getDefaultInstance();

        Block block = modFluid.defaultFluidState().createLegacyBlock().getBlock();
        Identifier identifier = getBlockIdentifier(block);

        ItemStack stack = children.getDefaultInstance();
        stack.set(ModComponents.BUCKET_FLUID_BLOCK_COMPONENT, identifier);

        return stack;
    }

    @Override
    public String getBaseName() {
        return baseName;
    }

    @Override
    public CustomBucketItem getParent() {
        return this;
    }

    // Compat with Bucket Lib for future
    public Component getName(ItemStack stack, Fluid fluid) {
        if(fluid == Fluids.EMPTY) return Component.translatable(this.getDescriptionId(), "");

        String descriptionId = this.getDescriptionId();
        Component argument;
        descriptionId += ".filled";
        argument = getFluidDescription(fluid);

        return Component.translatable(descriptionId, argument);
    }

    @Override
    public @NonNull Component getName(@NonNull ItemStack stack) {
        return getName(stack, Fluids.EMPTY);
    }

    public ResourceKey<Item> getKey() {
        return key;
    }

    public Component getFluidDescription(Fluid fluid) {
        return FluidVariantAttributes.getName(FluidVariant.of(fluid));
    }

    public void registerFluid(Identifier identifier, Fluid modFluid) {
        // PLACEHOLDER
    }

    public CustomChildrenBucketItem getChildren() {
        return children;
    }
}
//?}
