package ovh.paulem.simpleores.util;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.BiConsumer;

/**
 * Map concurrente préservant l'ordre d'insertion (FIFO) via une file séparée.
 * Eviction optionnelle: si maxSize > 0 et la taille dépasse, on enlève les plus anciens.
 * Ne cherche pas à être parfaite côté mémoire (les clés retirées restent éventuellement en file),
 * mais garantit un ordre d'itération cohérent (première apparition) et des opérations non bloquantes.
 */
public class ConcurrentFifoMap<K, V> {
    private final ConcurrentHashMap<K, V> map = new ConcurrentHashMap<>();
    private final ConcurrentLinkedQueue<K> order = new ConcurrentLinkedQueue<>();
    private final int maxSize;

    public ConcurrentFifoMap() {
        this(0); // 0 => pas de limite
    }

    public ConcurrentFifoMap(int maxSize) {
        this.maxSize = maxSize;
    }

    public V put(K key, V value) {
        V prev = map.put(key, value);
        if (prev == null) {
            order.add(key);
            evictIfNeeded();
        }
        return prev;
    }

    public V get(K key) {
        return map.get(key);
    }

    public V remove(K key) {
        return map.remove(key); // La clé restera peut-être dans la file, filtrée lors des itérations.
    }

    public int size() {
        return map.size();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public boolean containsKey(K key) {
        return map.containsKey(key);
    }

    /**
     * Itération dans l'ordre d'insertion (clé encore présente) sans doublons.
     */
    public void forEach(BiConsumer<K, V> consumer) {
        // LinkedHashSet pour éviter de traiter plusieurs fois la même clé (si réinsertion jamais faite).
        Set<K> seen = new LinkedHashSet<>();
        for (K k : order) {
            if (!seen.add(k)) continue; // doublon
            V v = map.get(k);
            if (v != null) consumer.accept(k, v);
        }
    }

    /**
     * Snapshot des valeurs dans l'ordre d'insertion actuel.
     */
    public Collection<V> values() {
        List<V> list = new ArrayList<>();
        Set<K> seen = new LinkedHashSet<>();
        for (K k : order) {
            if (!seen.add(k)) continue;
            V v = map.get(k);
            if (v != null) list.add(v);
        }
        return list;
    }

    private void evictIfNeeded() {
        if (maxSize <= 0) return;
        while (map.size() > maxSize) {
            K oldest = order.poll();
            if (oldest == null) break;
            map.remove(oldest); // retire éventuellement (si déjà retirée, noop)
        }
    }
}

