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

    // Le cache stocke un Optional car Guava n'autorise pas les valeurs nulles
    private static final LoadingCache<Block, Optional<SolidBucketItem>> BUCKET_CACHE = CacheBuilder.newBuilder()
            .expireAfterAccess(10, TimeUnit.MINUTES) // Optionnel : vide le cache si inutilisé
            .maximumSize(1000) // Sécurité pour la mémoire
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
            // En cas d'erreur de chargement, on retourne le fallback
            return findBucketInRegistry(block);
        }
    }

    /**
     * Ta logique originale de recherche, extraite pour le CacheLoader
     */
    private static @Nullable SolidBucketItem findBucketInRegistry(Block block) {
        // On itère sur les items
        for (Item item : BuiltInRegistries.ITEM) {
            if (item instanceof SolidBucketItem solidBucket) {
                // Comparaison de référence (==) : l'opération la plus rapide en Java
                if (solidBucket.getBlock() == block) {
                    return solidBucket;
                }
            }
        }
        return null;
    }
}