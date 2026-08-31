package net.paulem.simpleores.bucket.renderer;

//? if hasBucketlib {
public class CopperBucketItemSpecialRenderer {}
//?} else {

/*import com.mojang.serialization.MapCodec;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
//? if >1.21.8 {
import net.minecraft.world.entity.ItemOwner;
//?} else {
/^import net.minecraft.world.entity.LivingEntity;
^///?}
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.paulem.simpleores.items.ModComponents;
import net.paulem.simpleores.stonecutter.SCId;
import net.paulem.simpleores.utils.BucketPickupUtils;
import org.joml.Matrix4fc;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class CopperBucketItemSpecialRenderer implements ItemModel {
    private static final ItemModel INSTANCE = new CopperBucketItemSpecialRenderer();

    public CopperBucketItemSpecialRenderer() {
    }

    @Override
    public void update(@NonNull ItemStackRenderState output, ItemStack stack, @NonNull ItemModelResolver resolver, @NonNull ItemDisplayContext displayContext, @Nullable ClientLevel level, //? if >1.21.8 {
                       @Nullable ItemOwner owner,
                       //?} else {
                        /^LivingEntity owner,
                       ^///?}
                       int seed) {
        if(stack.has(ModComponents.BUCKET_FISH_COMPONENT)) {
            updateForEntity(output, stack, resolver, displayContext, level, owner, seed);
            return;
        }

        Identifier blockIdentifier = stack.get(ModComponents.BUCKET_BLOCK_COMPONENT);

        // Nothing to add: the empty bucket layer of the composite model is still rendered
        if(blockIdentifier == null) return;

        Block block = BuiltInRegistries.BLOCK.getValue(blockIdentifier);

        Item vanillaBucket = null;

        if(block instanceof LiquidBlock liquidBlock) {
            Fluid fluid = liquidBlock.fluid;

            if(fluid != Fluids.EMPTY) vanillaBucket = fluid.getBucket();
        } else if(block instanceof BucketPickup) {
            vanillaBucket = BucketPickupUtils.getBucketForBlock(block);
        }

        // Unknown content: only the empty bucket layer of the composite model is rendered
        if(vanillaBucket == null) return;

        resolver.appendItemLayers(output, vanillaBucket.getDefaultInstance(), displayContext, level, owner, seed);
    }

    public void updateForEntity(@NonNull ItemStackRenderState output, ItemStack stack, @NonNull ItemModelResolver resolver, @NonNull ItemDisplayContext displayContext, @Nullable ClientLevel level, //? if >1.21.8 {
                                @Nullable ItemOwner owner,
                                //?} else {
                                 /^LivingEntity owner,
                                ^///?}
                                int seed) {
        Identifier entityBucketIdentifier = stack.get(ModComponents.BUCKET_FISH_COMPONENT);

        // The bucket of a mod which is not loaded anymore: only the empty bucket layer is rendered
        if(entityBucketIdentifier == null) return;

        Item vanillaEntityBucketItem = BuiltInRegistries.ITEM.getValue(entityBucketIdentifier);

        if(vanillaEntityBucketItem == null) return;

        resolver.appendItemLayers(output, vanillaEntityBucketItem.getDefaultInstance(), displayContext, level, owner, seed);
    }

    public record Unbaked() implements ItemModel.Unbaked {
        public static final MapCodec<CopperBucketItemSpecialRenderer.Unbaked> MAP_CODEC = MapCodec.unit(new CopperBucketItemSpecialRenderer.Unbaked());

        @Override
        public @NonNull MapCodec<CopperBucketItemSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public @NonNull ItemModel bake(@NonNull BakingContext context //? afterDeobf
                , @NonNull Matrix4fc transformation
        ) {
            return CopperBucketItemSpecialRenderer.INSTANCE;
        }

        @Override
        public void resolveDependencies(@NonNull Resolver resolver) {
        }
    }
}
*///?}