package net.paulem.simpleores.utils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SolidBucketItem;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.Nullable;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class BucketPickupUtils {

    // Guava doesn't allow null values, so the cache stores an Optional
    private static final LoadingCache<Block, Optional<SolidBucketItem>> BUCKET_CACHE = CacheBuilder.newBuilder()
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .maximumSize(1000)
            .build(new CacheLoader<>() {
                @Override
                public Optional<SolidBucketItem> load(Block block) {
                    return Optional.ofNullable(findBucketInRegistry(block));
                }
            });

    @Nullable
    public static SolidBucketItem getBucketForBlock(Block block) {
        try {
            return BUCKET_CACHE.get(block).orElse(null);
        } catch (ExecutionException e) {
            return findBucketInRegistry(block);
        }
    }

    private static @Nullable SolidBucketItem findBucketInRegistry(Block block) {
        for (Item item : BuiltInRegistries.ITEM) {
            if (item instanceof SolidBucketItem solidBucket) {
                if (solidBucket.getBlock() == block) {
                    return solidBucket;
                }
            }
        }
        return null;
    }
}