package net.paulem.simpleores.config.loader;

import net.paulem.simpleores.config.Config;

public abstract class ConfigLoader<T extends Config> {
    protected T config;

    public abstract void load();

    public T getConfig() {
        return config;
    }

    public static ConfigLoader<? extends Config> getLoader(boolean condition) {
        //? if hasMidnightlib {
        if (condition) {
            return new MidnightLibLoader();
        } else {
            return new BaseConfigLoader();
        }
        //? } else {
             /*return new BaseConfigLoader();
        *///?}
    }
}
