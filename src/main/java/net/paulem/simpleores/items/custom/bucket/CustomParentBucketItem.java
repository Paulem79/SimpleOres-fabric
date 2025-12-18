package net.paulem.simpleores.items.custom.bucket;

//? if hasBucketlib || !containsBucket {
/*public class CustomParentBucketItem {}
*///?} else {

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.apache.commons.lang3.function.TriFunction;
import net.paulem.simpleores.SimpleOres;
import net.paulem.simpleores.stonecutter.SCId;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomParentBucketItem extends CustomChildrenBucketItem implements CustomBucketFluidable {
    private final String baseName;
    private final Map<Fluid, CustomChildrenBucketItem> buckets = new HashMap<>();
    private final TriFunction<CustomParentBucketItem, String, Fluid, CustomChildrenBucketItem> registrar;
    private final ResourceKey<Item> key;
    private final Identifier modelId;

    public CustomParentBucketItem(ResourceKey<Item> key, String baseName, Fluid fluid, Item.Properties settings, TriFunction<CustomParentBucketItem, String, Fluid, CustomChildrenBucketItem> registrar) {
        super(fluid, settings.setId(key));

        this.key = key;
        this.modelId = settings.effectiveModel();
        this.baseName = baseName;
        this.registrar = registrar;
    }

    public void registerFluid(Identifier identifier, Fluid modFluid) {
        if(modFluid == null || modFluid == Fluids.EMPTY) return;

        String fluidName = identifier.getPath();
        // If it's not the still fluid
        if(!modFluid.isSource(modFluid.defaultFluidState())) {
            return;
        }

        SimpleOres.LOGGER.info("Registering fluid: " + identifier + " for item " + this.baseName + "_bucket");

        CustomChildrenBucketItem bucket = registrar.apply(this, this.baseName + "_" + fluidName + "_bucket", modFluid);
        buckets.put(modFluid, bucket);
    }

    public Collection<CustomChildrenBucketItem> getChilds() {
        return buckets.values();
    }

    @Override
    public CustomChildrenBucketItem fromFluid(Fluid fluid) {
        CustomChildrenBucketItem gotBucket = buckets.get(FluidVariant.of(fluid).getFluid());
        return gotBucket == null ? getParent() : gotBucket;
    }

    @Override
    public String getBaseName() {
        return baseName;
    }

    @Override
    public CustomParentBucketItem getParent() {
        return this;
    }

    // Compat with Bucket Lib
    public Component getName(ItemStack stack, Fluid fluid) {
        if(fluid == Fluids.EMPTY) return Component.translatable(this.getDescriptionId(), "");

        String descriptionId = this.getDescriptionId();
        Component argument;
        descriptionId += ".filled";
        argument = getFluidDescription(fluid);

        return Component.translatable(descriptionId, argument);
    }

    @Override
    public Component getName(ItemStack stack) {
        return getName(stack, Fluids.EMPTY);
    }

    public ResourceKey<Item> getKey() {
        return key;
    }

    public Component getFluidDescription(Fluid fluid) {
        return FluidVariantAttributes.getName(FluidVariant.of(fluid));
    }

    public Identifier getModelWithOverlay(Fluid fluid) {
        if(isWaterLike(fluid)) {
            return SCId.of(modelId.getNamespace(), baseName + "_water_bucket");
        }

        // Every non-tintable fluids uses lava model, because it's always generated and tint computation is done at runtime.
        return SCId.of(modelId.getNamespace(), baseName + "_lava_bucket");
    }
}
//?}
