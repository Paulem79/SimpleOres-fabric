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

    // ARMORER
    boolean armorerEmeraldCopperHelmet();
    boolean armorerEmeraldCopperChestplate();
    boolean armorerEmeraldCopperLeggings();
    boolean armorerEmeraldCopperBoots();
    boolean armorerCopperToEmeralds();
    boolean armorerTinToEmeralds();
    boolean armorerEmeraldTinLeggings();
    boolean armorerEmeraldTinBoots();
    boolean armorerMythrilToEmeralds();
    boolean armorerEmeraldTinHelmet();
    boolean armorerEmeraldTinChestplate();
    boolean armorerEmeraldMythrilLeggingsEnchanted();
    boolean armorerEmeraldMythrilBootsEnchanted();
    boolean armorerEmeraldMythrilHelmetEnchanted();
    boolean armorerEmeraldMythrilChestplateEnchanted();

    // TOOLSMITH
    boolean toolsmithCopperToEmeralds();
    boolean toolsmithTinToEmeralds();
    boolean toolsmithEmeraldCopperAxe();
    boolean toolsmithEmeraldCopperShovel();
    boolean toolsmithEmeraldCopperHoe();
    boolean toolsmithEmeraldCopperPickaxe();
    boolean toolsmithEmeraldTinAxe();
    boolean toolsmithEmeraldTinShovel();
    boolean toolsmithEmeraldTinHoe();
    boolean toolsmithEmeraldTinPickaxe();
    boolean toolsmithMythrilToEmerald();
    boolean toolsmithEmeraldMythrilAxeEnchantedLvl3();
    boolean toolsmithEmeraldMythrilShovelEnchantedLvl3();
    boolean toolsmithEmeraldMythrilPickaxeEnchantedLvl3();
    boolean toolsmithEmeraldMythrilHoe();
    boolean toolsmithAdamantiumToEmerald();
    boolean toolsmithEmeraldAdamantiumAxeEnchanted();
    boolean toolsmithEmeraldMythrilShovelEnchanted();
    boolean toolsmithEmeraldMythrilPickaxeEnchanted();

    // WEAPONSMITH
    boolean weaponsmithEmeraldMythrilAxe();
    boolean weaponsmithEmeraldMythrilSwordEnchanted();
    boolean weaponsmithCopperToEmerald();
    boolean weaponsmithTinToEmerald();
    boolean weaponsmithMythrilToEmerald();
    boolean weaponsmithAdamantiumToEmerald();
    boolean weaponsmithEmeraldAdamantiumAxeEnchanted();
    boolean weaponsmithEmeraldAdamantiumSwordEnchanted();

    // copper bucket
    boolean enableCopperBucketMilking();
    int copperBucketMeltTemperature();
    int copperBucketFireTemperature();
}
