package net.paulem.simpleores.bucket.renderer;

import com.mojang.serialization.MapCodec;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.paulem.simpleores.items.custom.bucket.CustomChildrenBucketItem;
import net.paulem.simpleores.stonecutter.SCId;
import org.joml.Matrix4fc;
import org.jspecify.annotations.Nullable;

public class CopperBucketItemSpecialRenderer implements ItemModel {
    private static final ItemModel INSTANCE = new CopperBucketItemSpecialRenderer();

    public static final Identifier COVER_TEXTURE = SCId.of("item/copper_bucket_cover.png");
    public static final Identifier EMPTY_COVER_TEXTURE = SCId.of("item/copper_bucket.png");

    public static final ModelLayerLocation COPPER_BUCKET_MODEL_LAYER = new ModelLayerLocation(SCId.of("copper_bucket"), "");

    public CopperBucketItemSpecialRenderer() {
    }

    @Override
    public void update(ItemStackRenderState output, ItemStack stack, ItemModelResolver resolver, ItemDisplayContext displayContext, @Nullable ClientLevel level, @Nullable ItemOwner owner, int seed) {
        Item item = stack.getItem();

        if(item instanceof CustomChildrenBucketItem bucketItem) {
            Fluid fluid = bucketItem.getFluid();
            Item vanillaBucket = fluid.getBucket();
            resolver.appendItemLayers(output, vanillaBucket.getDefaultInstance(), displayContext, level, owner, seed);
        }
    }

    public record Unbaked() implements ItemModel.Unbaked {
        public static final MapCodec<CopperBucketItemSpecialRenderer.Unbaked> MAP_CODEC = MapCodec.unit(new CopperBucketItemSpecialRenderer.Unbaked());

        @Override
        public MapCodec<CopperBucketItemSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public ItemModel bake(BakingContext context, Matrix4fc transformation) {
            return CopperBucketItemSpecialRenderer.INSTANCE;
        }

        @Override
        public void resolveDependencies(Resolver resolver) {
            resolver.markDependency(COVER_TEXTURE);
        }
    }
}
