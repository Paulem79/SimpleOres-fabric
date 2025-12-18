package net.paulem.simpleores.config.loader;

// TODO : better check for cloth
//? if hasClothConfig {
/*import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.paulem.simpleores.config.SimpleOresConfig;

import static net.paulem.simpleores.SimpleOres.LOGGER;

public class ClothConfigLoader extends ConfigLoader<SimpleOresConfig> {
    @Override
    public void load() {
        LOGGER.info("Cloth Config 2 detected, enabling advanced config GUI support.");

        AutoConfig.register(SimpleOresConfig.class, Toml4jConfigSerializer::new);

        this.config = new SimpleOresConfig();
        AutoConfig.getConfigHolder(SimpleOresConfig.class).getConfig();

        try {
            this.config.validatePostLoad();
        } catch (ConfigData.ValidationException e) {
            LOGGER.info("Config validation failed");
        }
    }
}
*///? } else {
 public class ClothConfigLoader {}
//? }