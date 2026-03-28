package net.paulem.simpleores.config.loader;

//? if hasMidnightlib {
import eu.midnightdust.lib.config.MidnightConfig;
import net.paulem.simpleores.SimpleOres;
import net.paulem.simpleores.config.SimpleOresConfig;

import static net.paulem.simpleores.SimpleOres.LOGGER;

public class MidnightLibLoader extends ConfigLoader<SimpleOresConfig> {
    @Override
    public void load() {
        LOGGER.info("MidnightLib detected, enabling advanced config GUI support.");

		MidnightConfig.init(SimpleOres.MOD_ID, SimpleOresConfig.class);
        this.config = //? isLegacyMidnightLib {
                // new SimpleOresConfig();
                //?} else {
                (SimpleOresConfig) MidnightConfig.configInstances.get(SimpleOres.MOD_ID);
                //?}
    }
}
//? } else {
 /*public class MidnightLibLoader {}
*///? }