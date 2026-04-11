package net.paulem.simpleores.bucket.renderer;

import com.mojang.serialization.MapCodec;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.paulem.simpleores.items.ModItems;
import net.paulem.simpleores.items.custom.bucket.CustomBucketItem;
import net.paulem.simpleores.stonecutter.SCId;
import org.joml.Matrix4fc;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class CopperEmptyBucketItemSpecialRenderer implements ItemModel {
    private static final ItemModel INSTANCE = new CopperEmptyBucketItemSpecialRenderer();

    public static final Identifier COVER_TEXTURE = SCId.of("item/copper_bucket_cover.png");

    public CopperEmptyBucketItemSpecialRenderer() {
    }

    @Override
    public void update(@NonNull ItemStackRenderState output, @NonNull ItemStack stack, ItemModelResolver resolver, @NonNull ItemDisplayContext displayContext, @Nullable ClientLevel level, @Nullable ItemOwner owner, int seed) {
        Item item = stack.getItem();
        if(item instanceof CustomBucketItem parentBucketItem) {
            resolver.appendItemLayers(output, ModItems.LOWER_COPPER_BUCKET.getDefaultInstance(), displayContext, level, owner, seed);
        } else {
            resolver.appendItemLayers(output, ModItems.COVER_COPPER_BUCKET.getDefaultInstance(), displayContext, level, owner, seed);
        }
    }

    public record Unbaked() implements ItemModel.Unbaked {
        public static final MapCodec<CopperEmptyBucketItemSpecialRenderer.Unbaked> MAP_CODEC = MapCodec.unit(new CopperEmptyBucketItemSpecialRenderer.Unbaked());

        @Override
        public @NonNull MapCodec<CopperEmptyBucketItemSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public @NonNull ItemModel bake(@NonNull BakingContext context, @NonNull Matrix4fc transformation) {
            return CopperEmptyBucketItemSpecialRenderer.INSTANCE;
        }

        @Override
        public void resolveDependencies(@NonNull Resolver resolver) {
        }
    }
}
