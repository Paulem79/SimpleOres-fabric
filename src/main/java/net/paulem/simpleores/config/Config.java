package net.paulem.simpleores.config;

public interface Config {
    // armor
    int copperArmorDurability();
    BaseSimpleOresConfig.ArmorProtection copperArmorProtection();

    int tinArmorDurability();
    BaseSimpleOresConfig.ArmorProtection tinArmorProtection();

    int mythrilArmorDurability();
    BaseSimpleOresConfig.ArmorProtection mythrilArmorProtection();

    int adamantiumArmorDurability();
    BaseSimpleOresConfig.ArmorProtection adamantiumArmorProtection();

    int onyxArmorDurability();
    BaseSimpleOresConfig.ArmorProtection onyxArmorProtection();

    // tools
    BaseSimpleOresConfig.ToolsProperties copperTools();
    BaseSimpleOresConfig.ToolsProperties tinTools();
    BaseSimpleOresConfig.ToolsProperties mythrilTools();
    BaseSimpleOresConfig.ToolsProperties adamantiumTools();
    BaseSimpleOresConfig.ToolsProperties onyxTools();

    // bows
    int mythrilBowDurability();
    int onyxBowDurability();

    // villagers
    boolean enableTrades();

    //? if hasBucketlib {
    /*boolean enableCopperBucketMilking();

    int copperBucketMeltTemperature();

    int copperBucketFireTemperature();
    *///?}
}
