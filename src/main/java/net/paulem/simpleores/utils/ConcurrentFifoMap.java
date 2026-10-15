package net.paulem.simpleores.utils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.BiConsumer;

/**
 * Concurrent map keeping the insertion order (FIFO) through a separate queue.
 * Optional eviction: if maxSize > 0 and the size goes over it, the oldest entries are removed.
 * Not perfect memory-wise (removed keys may stay in the queue), but iteration order stays
 * consistent (first appearance) and operations are non-blocking.
 */
public class ConcurrentFifoMap<K, V> {
    private final ConcurrentHashMap<K, V> map = new ConcurrentHashMap<>();
    private final ConcurrentLinkedQueue<K> order = new ConcurrentLinkedQueue<>();
    private final int maxSize;

    public ConcurrentFifoMap() {
        this(0); // 0 => no limit
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
        return map.remove(key); // The key may stay in the queue, it is filtered while iterating
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
     * Iterates in insertion order (key still present) without duplicates.
     */
    public void forEach(BiConsumer<K, V> consumer) {
        // LinkedHashSet so the same key isn't processed twice
        Set<K> seen = new LinkedHashSet<>();
        for (K k : order) {
            if (!seen.add(k)) continue; // duplicate
            V v = map.get(k);
            if (v != null) consumer.accept(k, v);
        }
    }

    /**
     * Snapshot of the values in the current insertion order.
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
            map.remove(oldest); // may already be removed, no-op then
        }
    }

    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> entries = new LinkedHashSet<>();
        Set<K> seen = new LinkedHashSet<>();
        for (K k : order) {
            if (!seen.add(k)) continue;
            V v = map.get(k);
            if (v != null) entries.add(new AbstractMap.SimpleEntry<>(k, v));
        }
        return entries;
    }
}

