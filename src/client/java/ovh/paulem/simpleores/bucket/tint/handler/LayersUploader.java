package ovh.paulem.simpleores.bucket.tint.handler;

//? if hasBucketlib {
/*public class LayersUploader {}
*///?} else {

import net.minecraft.client.data.*;
import net.minecraft.client.render.item.tint.TintSource;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import ovh.paulem.simpleores.SimpleOres;

import java.util.LinkedList;
import java.util.Optional;

public class LayersUploader {
    // I want 24 layers, because the base overlay texture has 24 layers.
    public static final TextureKey[] LAYERS = new TextureKey[25];

    static {
        for (int i = 0; i < LAYERS.length; i++) {
            SimpleOres.LOGGER.info("Registering layer " + i);
            // Compute the texture key for this layer.
            LAYERS[i] = TextureKey.of("layer" + i);
        }
    }

    // The model for the x layers
    public static final Model GENERATED_TWENTY_FOUR_LAYERS = item("generated", LAYERS);

    public static void registerOverlayBucket(ItemModelGenerator itemModelGenerator, Item item, Item parentBucket, TintSource... tints) {
        LinkedList<Identifier> layers = new LinkedList<>();

        layers.add(TextureMap.getId(parentBucket));
        for (int i = 1; i < LAYERS.length; i++) {
            // Get the corresponding texture for this layer. Starts with 0.
            Identifier subId = TextureMap.getSubId(parentBucket, "_overlay" + (i-1));
            SimpleOres.LOGGER.info("Adding overlay layer " + i + " : " + subId);
            layers.add(subId);
        }

        Identifier identifier = uploadLayers(itemModelGenerator, item, layers.toArray(new Identifier[0]));
        itemModelGenerator.output.accept(item, ItemModels.tinted(identifier, tints));
    }

    public static Identifier uploadLayers(ItemModelGenerator itemModelGenerator, Item item, Identifier... layers) {
        TextureMap layered = layered(layers);
        return GENERATED_TWENTY_FOUR_LAYERS.upload(item, layered, itemModelGenerator.modelCollector);
    }

    /**
     * Get the texture map for the given layers.
     */
    public static TextureMap layered(Identifier... layers) {
        TextureMap textureMap = new TextureMap();

        for (int i = 0; i < layers.length; i++) {
            SimpleOres.LOGGER.info("layering " + i + " : " + layers[i]);
            textureMap.put(LAYERS[i], layers[i]);
        }

        return textureMap;
    }

    /**
     * @see net.minecraft.client.data.Models#item(String, TextureKey...)
     */
    private static Model item(String parent, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(Identifier.ofVanilla("item/" + parent)), Optional.empty(), requiredTextureKeys);
    }
}
//?}