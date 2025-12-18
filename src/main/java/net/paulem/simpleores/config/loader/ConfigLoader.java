package net.paulem.simpleores.config.loader;

import net.paulem.simpleores.config.Config;

public abstract class ConfigLoader<T extends Config> {
    protected T config;

    public abstract void load();

    public T getConfig() {
        return config;
    }

    public static ConfigLoader<?> getLoader(boolean condition) {
        //? if hasClothConfig {
        /*if (condition) {
            return new ClothConfigLoader();
        } else {
            return new BaseConfigLoader();
        }
        *///? } else {
             return new BaseConfigLoader();
        //?}
    }
}
