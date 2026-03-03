package net.paulem.simpleores.bucket.tint;

//? if hasBucketlib || !containsBucket {
public class ClientBucketUtil {}
//?} else {

/*import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.data.models.model.ItemModelUtils;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.paulem.simpleores.bucket.tint.handler.BucketLayerTintSource;
import net.paulem.simpleores.items.custom.bucket.CustomChildrenBucketItem;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientBucketUtil {
    // Cache to avoid recomputing dominant colors for the same fluids
    private static final Map<Fluid, Integer> DOMINANT_COLOR_CACHE = new ConcurrentHashMap<>();
    private static final Map<String, Integer> COLOR_AT_CACHE = new ConcurrentHashMap<>();

    public static Fluid getContainedFluid(ItemStack stack) {
        Item item = stack.getItem();

        if (item instanceof CustomChildrenBucketItem bucketItem) {
            return bucketItem.getFluid();
        }

        return Fluids.EMPTY;
    }

    public static int getWaterLikeColor(Fluid fluid, int defaultColor) {
        int color = ARGB.color(255, defaultColor);

        FluidRenderHandler fluidRenderHandler;
        if (fluid == Fluids.EMPTY || (fluidRenderHandler = FluidRenderHandlerRegistry.INSTANCE.get(fluid)) == null) {
            return color;
        }

        int handlerColor = fluidRenderHandler.getFluidColor(null, null, fluid.defaultFluidState());
        color = ARGB.color(255, handlerColor);

        if (color == -1) {
            // If already computed, use the cache
            Integer cached = DOMINANT_COLOR_CACHE.get(fluid);
            if (cached != null) {
                return ARGB.color(255, cached);
            }

            // Retrieve fluid sprites
            TextureAtlasSprite[] sprites = fluidRenderHandler.getFluidSprites(null, null, fluid.defaultFluidState());
            if (sprites != null && sprites.length > 0 && sprites[0] != null) {
                Integer dominant = computeDominantOpaqueColor(sprites[0]);
                if (dominant != null) {
                    DOMINANT_COLOR_CACHE.put(fluid, dominant);
                    return ARGB.color(255, dominant);
                }
            }
        }

        return color;
    }

    // Compute the most frequent opaque color in the first sprite frame.
    private static Integer computeDominantOpaqueColor(TextureAtlasSprite sprite) {
        try {
            int frame = 0; // first frame is enough for a dominant color
            int width = sprite.contents().width();
            int height = sprite.contents().height();
            if (width <= 0 || height <= 0) return null;

            SpriteContents contents = sprite.contents();

            Map<Integer, Integer> colorCount = new HashMap<>();
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int argb = getPixelColor(contents, frame, 0, x, y);
                    int a = (argb >>> 24) & 0xFF;
                    if (a <= 16) continue; // ignore nearly transparent pixels to avoid background influence
                    int rgb = argb & 0xFFFFFF;
                    colorCount.put(rgb, colorCount.getOrDefault(rgb, 0) + 1);
                }
            }

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
            return null; // Bail out on mapping differences or other issues
        }
    }

    public static int getColorAt(Fluid fluid, int defaultColor, int x, int y) {
        int color = ARGB.color(255, defaultColor);

        String cacheKey = fluid.toString() + ":" + x + ":" + y;

        Integer cached = COLOR_AT_CACHE.get(cacheKey);
        if (cached != null) {
            return ARGB.color(255, cached);
        }

        FluidRenderHandler fluidRenderHandler;
        if (fluid == Fluids.EMPTY || (fluidRenderHandler = FluidRenderHandlerRegistry.INSTANCE.get(fluid)) == null) {
            return color;
        }

        TextureAtlasSprite[] sprites = fluidRenderHandler.getFluidSprites(null, null, fluid.defaultFluidState());
        if (sprites != null && sprites.length > 0 && sprites[0] != null) {
            Integer at = getColorAt(sprites[0], x, y);
            if (at != null) {
                COLOR_AT_CACHE.put(cacheKey, at);
                return ARGB.color(255, at);
            }
        }

        COLOR_AT_CACHE.put(cacheKey, color);
        return color;
    }

    /^*
     * Get the color at the given coordinates in the sprite.
     ^/
    private static Integer getColorAt(TextureAtlasSprite sprite, int x, int y) {
        try {
            int frame = 0; // first frame
            int width = sprite.contents().width();
            int height = sprite.contents().height();
            if (width <= 0 || height <= 0) return null;

            SpriteContents contents = sprite.contents();

            int argb = getPixelColor(contents, frame, 0, x, y);
            int a = (argb >>> 24) & 0xFF;
            if (a <= 16) return 0x00FF00; // ignore nearly transparent pixels to avoid background influence

            int rgb = argb & 0xFFFFFF;

            return rgb;
        } catch (Throwable t) {
            return null; // Bail out on mapping differences or other issues
        }
    }
    
    /^*
     * {@return the pixel color at frame {@code frameIndex} within mipmap {@code layer} at sprite relative coordinates}
     ^/
    private static int getPixelColor(SpriteContents contents, int frameIndex, int layer, int x, int y) {
        @Nullable SpriteContents.AnimatedTexture animation = contents.animatedTexture;
        if (animation == null) return getNotAnimatedPixelColor(contents, layer, x, y);
        else return getAnimatedPixelColor(contents, animation, frameIndex, layer, x, y);
    }

    private static int getNotAnimatedPixelColor(SpriteContents contents, int layer, int x, int y) {
        return contents.byMipLevel[layer]
                .getPixel(
                        x, y
                );
    }

    private static int getAnimatedPixelColor(SpriteContents contents, SpriteContents.AnimatedTexture animation, int frameIndex, int layer, int x, int y) {
        return contents.byMipLevel[layer]
                .getPixel(
                        x + (animation.getFrameX(frameIndex) * contents.width() >> layer), y + (animation.getFrameY(frameIndex) * contents.height() >> layer)
                );
    }

    // The offset of the y-layer from the start of the texture. (optional)
    private static final int OVERLAY_COORDS_Y_OFFSET = 4;
    // The coordinates of the overlay pixels from the start of this y-layer.
    private static final List<List<Integer>> OVERLAY_COORDS = Arrays.asList(
            // First y
            Arrays.asList(4, 5, 6, 7, 8, 9, 10, 11),
            Arrays.asList(3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
            Arrays.asList(5, 6, 7, 8, 9, 10)
    );

    public static ItemTintSource[] getDefaultTints(CustomChildrenBucketItem child) {
        List<ItemTintSource> tintSources = new ArrayList<>();

        tintSources.add(ItemModelUtils.constantTint(-1));
        for (List<Integer> row : OVERLAY_COORDS) {
            for (Integer x : row) {
                int y = OVERLAY_COORDS.indexOf(row) + OVERLAY_COORDS_Y_OFFSET;
                ItemTintSource tintSource = new BucketLayerTintSource(ARGB.color(255, 0xFFFFFF), x, y);
                tintSources.add(tintSource);
            }
        }

        return tintSources.toArray(new ItemTintSource[0]);
    }
}
*///?}
