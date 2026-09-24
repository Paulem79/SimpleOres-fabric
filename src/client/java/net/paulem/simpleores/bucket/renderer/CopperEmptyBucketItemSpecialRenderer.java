package net.paulem.simpleores.bucket.renderer;

//? if hasBucketlib {
public class CopperEmptyBucketItemSpecialRenderer {}
//?} else {

/*import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemModels;
import net.minecraft.client.renderer.item.ItemStackRenderState;
//? if >1.21.8 {
import net.minecraft.world.entity.ItemOwner;
//?} else {
/^import net.minecraft.world.entity.LivingEntity;
^///?}
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.paulem.simpleores.items.custom.bucket.CustomBucketItem;
import org.joml.Matrix4fc;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/^*
 * Renders the copper bucket layer matching its content.
 * Each layer is a plain model declared in the item definition, so no item has to be registered for it.
 ^/
public class CopperEmptyBucketItemSpecialRenderer implements ItemModel {
    private final ItemModel empty;
    private final ItemModel entity;
    private final ItemModel block;
    private final ItemModel melting;
    private final ItemModel filled;

    public CopperEmptyBucketItemSpecialRenderer(ItemModel empty, ItemModel entity, ItemModel block, ItemModel melting, ItemModel filled) {
        this.empty = empty;
        this.entity = entity;
        this.block = block;
        this.melting = melting;
        this.filled = filled;
    }

    @Override
    public void update(@NonNull ItemStackRenderState output, @NonNull ItemStack stack, @NonNull ItemModelResolver resolver, @NonNull ItemDisplayContext displayContext, @Nullable ClientLevel level, //? if >1.21.8 {
                       @Nullable ItemOwner owner,
                       //?} else {
                        /^LivingEntity owner,
                       ^///?}
                       int seed) {
        Item item = stack.getItem();
        if (!(item instanceof CustomBucketItem bucketItem)) return;

        selectModel(bucketItem, stack).update(output, stack, resolver, displayContext, level, owner, seed);
    }

    private ItemModel selectModel(CustomBucketItem bucketItem, ItemStack stack) {
        if(bucketItem.holdsEntity(stack) && !bucketItem.isMilkBucket(stack)) return entity;

        if(bucketItem.isEmpty(stack)) return empty;

        if(bucketItem.holdsBlock(stack)) return block;

        // If liquid, check if it's melting
        if(bucketItem.holdsFluid(stack) && bucketItem.shouldMelt(stack)) return melting;

        // Else, liquid or milk
        return filled;
    }

    public record Unbaked(ItemModel.Unbaked empty, ItemModel.Unbaked entity, ItemModel.Unbaked block,
                          ItemModel.Unbaked melting, ItemModel.Unbaked filled) implements ItemModel.Unbaked {
        public static final MapCodec<CopperEmptyBucketItemSpecialRenderer.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                ItemModels.CODEC.fieldOf("empty").forGetter(Unbaked::empty),
                ItemModels.CODEC.fieldOf("entity").forGetter(Unbaked::entity),
                ItemModels.CODEC.fieldOf("block").forGetter(Unbaked::block),
                ItemModels.CODEC.fieldOf("melting").forGetter(Unbaked::melting),
                ItemModels.CODEC.fieldOf("filled").forGetter(Unbaked::filled)
        ).apply(instance, Unbaked::new));

        @Override
        public @NonNull MapCodec<CopperEmptyBucketItemSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public @NonNull ItemModel bake(@NonNull BakingContext context //? afterDeobf
                , @NonNull Matrix4fc transformation
        ) {
            return new CopperEmptyBucketItemSpecialRenderer(
                    empty.bake(context //? afterDeobf
                            , transformation
                    ),
                    entity.bake(context //? afterDeobf
                            , transformation
                    ),
                    block.bake(context //? afterDeobf
                            , transformation
                    ),
                    melting.bake(context //? afterDeobf
                            , transformation
                    ),
                    filled.bake(context //? afterDeobf
                            , transformation
                    )
            );
        }

        @Override
        public void resolveDependencies(@NonNull Resolver resolver) {
            empty.resolveDependencies(resolver);
            entity.resolveDependencies(resolver);
            block.resolveDependencies(resolver);
            melting.resolveDependencies(resolver);
            filled.resolveDependencies(resolver);
        }
    }
}
*///?}
