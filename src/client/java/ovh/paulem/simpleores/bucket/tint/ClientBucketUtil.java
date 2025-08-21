package ovh.paulem.simpleores.bucket.tint;

//? if hasBucketlib {
/*public class ClientBucketUtil {}*/
//?} else {

import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.minecraft.client.data.ItemModels;
import net.minecraft.client.render.item.tint.TintSource;
import net.minecraft.client.texture.SpriteContents;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.ColorHelper;
import org.jetbrains.annotations.Nullable;
import ovh.paulem.simpleores.bucket.tint.handler.BucketLayerTintSource;
import ovh.paulem.simpleores.items.custom.bucket.CustomChildrenBucketItem;
import net.minecraft.client.texture.Sprite;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ClientBucketUtil {
    // Cache pour éviter de recalculer la couleur dominante des mêmes fluides
    private static final Map<Fluid, Integer> DOMINANT_COLOR_CACHE = new ConcurrentHashMap<>();
    //private static final Map<Fluid, Integer> OVERLAY_COLOR_CACHE = new ConcurrentHashMap<>();

    public static Fluid getContainedFluid(ItemStack stack) {
        Item item = stack.getItem();

        if(item instanceof CustomChildrenBucketItem bucketItem) {
            return bucketItem.getFluid();
        }

        return Fluids.EMPTY;
    }

    public static int getColorFromFluid(Fluid fluid, int defaultColor) {
        int color = ColorHelper.withAlpha(255, defaultColor);

        FluidRenderHandler fluidRenderHandler;
        if (fluid == Fluids.EMPTY || (fluidRenderHandler = FluidRenderHandlerRegistry.INSTANCE.get(fluid)) == null) {
            return color;
        }

        int handlerColor = fluidRenderHandler.getFluidColor(null, null, fluid.getDefaultState());
        color = ColorHelper.withAlpha(255, handlerColor);

        if(color == -1){
            // Si déjà calculé -> utiliser le cache
            Integer cached = DOMINANT_COLOR_CACHE.get(fluid);
            if (cached != null) {
                return ColorHelper.withAlpha(255, cached);
            }

            // Récupération des sprites du fluide
            Sprite[] sprites = fluidRenderHandler.getFluidSprites(null, null, fluid.getDefaultState());
            if (sprites != null && sprites.length > 0 && sprites[0] != null) {
                Integer dominant = computeDominantOpaqueColor(sprites[0]);
                if (dominant != null) {
                    DOMINANT_COLOR_CACHE.put(fluid, dominant);
                    return ColorHelper.withAlpha(255, dominant);
                }
            }
        }

        return color;
    }

    // Calcule la couleur opaque la plus fréquente dans le premier frame du sprite.
    private static Integer computeDominantOpaqueColor(Sprite sprite) {
        try {
            int frame = 0; // premier frame suffisant pour une couleur dominante
            int width = sprite.getContents().getWidth();
            int height = sprite.getContents().getHeight();
            if (width <= 0 || height <= 0) return null;

            SpriteContents contents = sprite.getContents();

            Map<Integer, Integer> colorCount = new HashMap<>();
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int argb = getPixelColor(contents, frame, 0, x, y);
                    int a = (argb >>> 24) & 0xFF;
                    if (a <= 16) continue; // ignore (nearly) transparent pixels to avoid background influence
                    int rgb = argb & 0xFFFFFF;
                    colorCount.put(rgb, colorCount.getOrDefault(rgb, 0) + 1);
                }
            }

            // Find most frequent color
            int dominantColor = 0;
            int maxCount = 0;
            for (Map.Entry<Integer, Integer> entry : colorCount.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    dominantColor = entry.getKey();
                }
            }

            return maxCount > 0 ? dominantColor : null;
        } catch (Throwable t) {
            return null; // En cas d'incompatibilité de méthode (mappings) ou autre
        }
    }

    public static int getColorAt(Fluid fluid, int defaultColor, int x, int y) {
        int color = ColorHelper.withAlpha(255, defaultColor);

        FluidRenderHandler fluidRenderHandler;
        if (fluid == Fluids.EMPTY || (fluidRenderHandler = FluidRenderHandlerRegistry.INSTANCE.get(fluid)) == null) {
            return color;
        }

        int handlerColor = fluidRenderHandler.getFluidColor(null, null, fluid.getDefaultState());
        color = ColorHelper.withAlpha(255, handlerColor);

        if(color == -1){
            // Si déjà calculé -> utiliser le cache
            /*Integer cached = OVERLAY_COLOR_CACHE.get(fluid);
            if (cached != null) {
                return ColorHelper.withAlpha(255, cached);
            }*/

            // Récupération des sprites du fluide
            Sprite[] sprites = fluidRenderHandler.getFluidSprites(null, null, fluid.getDefaultState());
            if (sprites != null && sprites.length > 0 && sprites[0] != null) {
                Integer at = getColorAt(sprites[0], x, y);
                if (at != null) {
                    //OVERLAY_COLOR_CACHE.put(fluid, at);
                    return ColorHelper.withAlpha(255, at);
                }
            }
        }

        return color;
    }

    // Calcule la couleur opaque la plus fréquente dans le premier frame du sprite.
    private static Integer getColorAt(Sprite sprite, int x, int y) {
        try {
            int frame = 0; // premier frame
            int width = sprite.getContents().getWidth();
            int height = sprite.getContents().getHeight();
            if (width <= 0 || height <= 0) return null;

            SpriteContents contents = sprite.getContents();

            int argb = getPixelColor(contents, frame, 0, x, y);
            int a = (argb >>> 24) & 0xFF;
            if (a <= 16) return 0x00FF00; // ignore (nearly) transparent pixels to avoid background influence

            int rgb = argb & 0xFFFFFF;

            return rgb;
        } catch (Throwable t) {
            return null; // En cas d'incompatibilité de méthode (mappings) ou autre
        }
    }
    
    /**
     * {@return the pixel color at frame {@code frameIndex} within mipmap {@code layer} at sprite relative coordinates}
     */
    private static int getPixelColor(SpriteContents contents, int frameIndex, int layer, int x, int y) {
        @Nullable SpriteContents.Animation animation = contents.animation;
        if(animation == null) return getNotAnimatedPixelColor(contents, layer, x, y);
        else return getAnimatedPixelColor(contents, animation, frameIndex, layer, x, y);
    }

    private static int getNotAnimatedPixelColor(SpriteContents contents, int layer, int x, int y) {
        return contents.mipmapLevelsImages[layer]
                .getColorArgb(
                        x, y
                );
    }

    private static int getAnimatedPixelColor(SpriteContents contents, SpriteContents.Animation animation, int frameIndex, int layer, int x, int y) {
        return contents.mipmapLevelsImages[layer]
                .getColorArgb(
                        x + (animation.getFrameX(frameIndex) * contents.getWidth() >> layer), y + (animation.getFrameY(frameIndex) * contents.getHeight() >> layer)
                );
    }

    private static final List<List<Integer>> OVERLAY_COORDS = Arrays.asList(
            // First y
            Arrays.asList(4, 5, 6, 7, 8, 9, 10, 11),
            Arrays.asList(3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
            Arrays.asList(5, 6, 7, 8, 9, 10)
    );

    public static TintSource[] getDefaultTints(CustomChildrenBucketItem child) {
        List<TintSource> tintSources = new ArrayList<>();

        tintSources.add(ItemModels.constantTintSource(-1));
        for (List<Integer> row : OVERLAY_COORDS) {
            for (Integer x : row) {
                int y = OVERLAY_COORDS.indexOf(row) + 4;
                TintSource tintSource = new BucketLayerTintSource(ColorHelper.withAlpha(255, 0xFFFFFF), x, y);
                tintSources.add(tintSource);
            }
        }

        return tintSources.toArray(new TintSource[0]);
    }
}
//?}
