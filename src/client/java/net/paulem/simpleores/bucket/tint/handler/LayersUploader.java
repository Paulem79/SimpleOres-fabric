package net.paulem.simpleores.bucket.tint.handler;

//? if hasBucketlib {
/*public class LayersUploader {}
*///?} else {

import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.data.*;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.paulem.simpleores.SimpleOres;

import java.util.LinkedList;
import java.util.Optional;

public class LayersUploader {
    // I want 24 layers, because the base overlay texture has 24 layers.
    public static final TextureSlot[] LAYERS = new TextureSlot[25];

    static {
        for (int i = 0; i < LAYERS.length; i++) {
            SimpleOres.LOGGER.info("Registering layer " + i);
            // Compute the texture key for this layer.
            LAYERS[i] = TextureSlot.create("layer" + i);
        }
    }

    // The model for the x layers
    public static final ModelTemplate GENERATED_TWENTY_FOUR_LAYERS = item("generated", LAYERS);

    public static void registerOverlayBucket(ItemModelGenerators itemModelGenerator, Item item, Item parentBucket, ItemTintSource... tints) {
        LinkedList<ResourceLocation > layers = new LinkedList<>();

        layers.add(TextureMapping.getItemTexture(parentBucket));
        for (int i = 1; i < LAYERS.length; i++) {
            // Get the corresponding texture for this layer. Starts with 0.
            ResourceLocation subId = TextureMapping.getItemTexture(parentBucket, "_overlay" + (i-1));
            SimpleOres.LOGGER.info("Adding overlay layer " + i + " : " + subId);
            layers.add(subId);
        }

        ResourceLocation identifier = uploadLayers(itemModelGenerator, item, layers.toArray(new ResourceLocation[0]));
        itemModelGenerator.itemModelOutput.accept(item, ItemModelUtils.tintedModel(identifier, tints));
    }

    public static ResourceLocation uploadLayers(ItemModelGenerators itemModelGenerator, Item item, ResourceLocation... layers) {
        TextureMapping layered = layered(layers);
        return GENERATED_TWENTY_FOUR_LAYERS.create(item, layered, itemModelGenerator.modelOutput);
    }

    /**
     * Get the texture map for the given layers.
     */
    public static TextureMapping layered(ResourceLocation ... layers) {
        TextureMapping textureMap = new TextureMapping();

        for (int i = 0; i < layers.length; i++) {
            SimpleOres.LOGGER.info("layering " + i + " : " + layers[i]);
            textureMap.put(LAYERS[i], layers[i]);
        }

        return textureMap;
    }

    /**
     * @see net.minecraft.client.data.models.model.ModelTemplate#create(String, TextureSlot...)
     */
    private static ModelTemplate item(String parent, TextureSlot... requiredTextureSlots) {
        return new ModelTemplate(Optional.of(ResourceLocation.withDefaultNamespace("item/" + parent)), Optional.empty(), requiredTextureSlots);
    }
}
//?}
