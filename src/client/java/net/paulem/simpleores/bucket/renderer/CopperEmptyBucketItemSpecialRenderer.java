package net.paulem.simpleores.bucket.renderer;

//? if hasBucketlib {
//public class CopperEmptyBucketItemSpecialRenderer {}
//?} else {

import com.mojang.serialization.MapCodec;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
//? if >1.21.8 {
import net.minecraft.world.entity.ItemOwner;
//?} else {
//import net.minecraft.world.entity.LivingEntity;
//?}
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.paulem.simpleores.items.ModItems;
import net.paulem.simpleores.items.custom.bucket.CustomBucketItem;
import org.joml.Matrix4fc;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class CopperEmptyBucketItemSpecialRenderer implements ItemModel {
    private static final ItemModel INSTANCE = new CopperEmptyBucketItemSpecialRenderer();

    public CopperEmptyBucketItemSpecialRenderer() {
    }

    @Override
    public void update(@NonNull ItemStackRenderState output, @NonNull ItemStack stack, @NonNull ItemModelResolver resolver, @NonNull ItemDisplayContext displayContext, @Nullable ClientLevel level, //? if >1.21.8 {
                       @Nullable ItemOwner owner,
                       //?} else {
                       // LivingEntity owner,
                       //?}
                       int seed) {
        Item item = stack.getItem();
        if (!(item instanceof CustomBucketItem bucketItem)) return;

        if(bucketItem.holdsEntity(stack) && !bucketItem.isMilkBucket(stack)) {
            resolver.appendItemLayers(output, ModItems.COVER_LOWER_COPPER_BUCKET.getDefaultInstance(), displayContext, level, owner, seed);
            return;
        }

        if(bucketItem.isEmpty(stack)) {
            resolver.appendItemLayers(output, ModItems.BASE_COPPER_BUCKET.getDefaultInstance(), displayContext, level, owner, seed);
            return;
        }

        if(bucketItem.holdsBlock(stack)) {
            resolver.appendItemLayers(output, ModItems.COVER_BLOCK_COPPER_BUCKET.getDefaultInstance(), displayContext, level, owner, seed);
            return;
        }

        // Else, liquid or milk
        resolver.appendItemLayers(output, ModItems.COVER_COPPER_BUCKET.getDefaultInstance(), displayContext, level, owner, seed);
    }

    public record Unbaked() implements ItemModel.Unbaked {
        public static final MapCodec<CopperEmptyBucketItemSpecialRenderer.Unbaked> MAP_CODEC = MapCodec.unit(new CopperEmptyBucketItemSpecialRenderer.Unbaked());

        @Override
        public @NonNull MapCodec<CopperEmptyBucketItemSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public @NonNull ItemModel bake(@NonNull BakingContext context //? afterDeobf
                , @NonNull Matrix4fc transformation
        ) {
            return CopperEmptyBucketItemSpecialRenderer.INSTANCE;
        }

        @Override
        public void resolveDependencies(@NonNull Resolver resolver) {
        }
    }
}
//?}