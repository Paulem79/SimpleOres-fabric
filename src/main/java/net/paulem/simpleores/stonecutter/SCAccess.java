package net.paulem.simpleores.stonecutter;

import java.util.function.Function;

public class SCAccess<T, V> {
    private final Function<T, V> getter;

    public SCAccess(Function<T, V> getter) {
        this.getter = getter;
    }

    public V get(T instance) {
        return getter.apply(instance);
    }
}
