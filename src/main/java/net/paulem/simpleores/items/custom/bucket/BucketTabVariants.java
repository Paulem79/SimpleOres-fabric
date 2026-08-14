package net.paulem.simpleores.items.custom.bucket;

//? if hasBucketlib || !containsBucket {
/*public class BucketTabVariants {}
*///?} else {

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.SolidBucketItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Builds every filled variant of a {@link CustomBucketItem} to show them in the creative tab,
 * right after the empty bucket. Every bucket like item of the registry is used, so the buckets
 * added by the other mods get their variant too.
 */
public class BucketTabVariants {

    private BucketTabVariants() {}

    public static List<ItemStack> build(CustomBucketItem bucket) {
        List<ItemStack> variants = new ArrayList<>();

        variants.add(bucket.getMilkBucket());

        for (Item item : BuiltInRegistries.ITEM) {
            // Never mix a custom bucket with itself
            if(item instanceof CustomBucketItem) continue;

            // MobBucketItem extends BucketItem and SolidBucketItem extends BlockItem: order matters
            boolean isBucket = item instanceof MobBucketItem
                    || item instanceof SolidBucketItem
                    || item instanceof BucketItem;

            if(!isBucket) continue;

            ItemStack variant = CustomBucketItem.mix(item.getDefaultInstance(), bucket.getEmpty());

            // Drops minecraft:bucket itself and every content which could not be resolved
            if(variant.isEmpty() || bucket.isEmpty(variant)) continue;

            variants.add(variant);
        }

        return variants;
    }
}
//?}
