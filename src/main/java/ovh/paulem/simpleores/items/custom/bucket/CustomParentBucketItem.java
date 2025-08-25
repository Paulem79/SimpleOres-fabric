package ovh.paulem.simpleores.items.custom.bucket;

//? if hasBucketlib {
/*public class CustomParentBucketItem {}
*///?} else {

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.function.TriFunction;
import ovh.paulem.simpleores.SimpleOres;
import ovh.paulem.simpleores.stonecutter.SCIdentifier;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomParentBucketItem extends CustomChildrenBucketItem implements CustomBucketFluidable {
    private final String baseName;
    private final Map<Fluid, CustomChildrenBucketItem> buckets = new HashMap<>();
    private final TriFunction<CustomParentBucketItem, String, Fluid, CustomChildrenBucketItem> registrar;
    private final RegistryKey<Item> key;
    private final Identifier modelId;

    public CustomParentBucketItem(RegistryKey<Item> key, String baseName, Fluid fluid, Settings settings, TriFunction<CustomParentBucketItem, String, Fluid, CustomChildrenBucketItem> registrar) {
        super(fluid, settings.registryKey(key));

        this.key = key;
        this.modelId = settings.getModelId();
        this.baseName = baseName;
        this.registrar = registrar;
    }

    public void registerFluid(Identifier identifier, Fluid modFluid) {
        if(modFluid == null || modFluid == Fluids.EMPTY) return;

        String fluidName = identifier.getPath();
        // If it's not the still fluid
        if(!modFluid.isStill(modFluid.getDefaultState())) {
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
    public Text getName(ItemStack stack, Fluid fluid) {
        if(fluid == Fluids.EMPTY) return Text.translatable(this.getTranslationKey(), "");

        String descriptionId = this.getTranslationKey();
        Text argument;
        descriptionId += ".filled";
        argument = getFluidDescription(fluid);

        return Text.translatable(descriptionId, argument);
    }

    @Override
    public Text getName(ItemStack stack) {
        return getName(stack, Fluids.EMPTY);
    }

    public RegistryKey<Item> getKey() {
        return key;
    }

    public Text getFluidDescription(Fluid fluid) {
        return FluidVariantAttributes.getName(FluidVariant.of(fluid));
    }

    public Identifier getModelWithOverlay(Fluid fluid) {
        if(isWaterLike(fluid)) {
            return SCIdentifier.of(modelId.getNamespace(), baseName + "_water_bucket");
        }

        // Every non-tintable fluids uses lava model, because it's always generated and tint computation is done at runtime.
        return SCIdentifier.of(modelId.getNamespace(), baseName + "_lava_bucket");
    }
}
//?}
