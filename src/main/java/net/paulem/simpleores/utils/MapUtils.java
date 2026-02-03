package net.paulem.simpleores.utils;

import java.util.Map;
import java.util.stream.Stream;

public class MapUtils {
    private MapUtils(){}

    public static <K, V> Stream<K> keys(Map<K, V> map, V value) {
        return map
                .entrySet()
                .stream()
                .filter(entry -> value.equals(entry.getValue()))
                .map(Map.Entry::getKey);
    }

    public static <K, V> Stream<K> keys(ConcurrentFifoMap<K, V> map, V value) {
        return map
                .entrySet()
                .stream()
                .filter(entry -> value.equals(entry.getValue()))
                .map(Map.Entry::getKey);
    }
}
