package net.paulem.simpleores.config.loader;

import net.paulem.simpleores.config.BaseSimpleOresConfig;

public class BaseConfigLoader extends ConfigLoader<BaseSimpleOresConfig> {
    @Override
    public void load() {
        this.config = new BaseSimpleOresConfig();
    }
}
