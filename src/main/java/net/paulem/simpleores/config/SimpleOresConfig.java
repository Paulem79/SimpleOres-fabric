package net.paulem.simpleores.config;

//? if hasMidnightlib {
import eu.midnightdust.lib.config.MidnightConfig;
import net.paulem.simpleores.items.ModToolMaterials;

public class SimpleOresConfig extends MidnightConfig implements Config {
    public static final String ARMORS = "armors";
    public static final String TOOLS = "tools";
    public static final String BOWS = "bows";
    public static final String VILLAGERS = "villagers";
    public static final String COPPER_BUCKET = "copper_bucket";

    @Entry(category = ARMORS) public static int copperArmorDurability = new BaseSimpleOresConfig().copperArmorDurability;
    @Entry(category = ARMORS) public static int copperArmor_helmet = new BaseSimpleOresConfig().copperArmorProtection.helmet();
    @Entry(category = ARMORS) public static int copperArmor_chestplate = new BaseSimpleOresConfig().copperArmorProtection.chestplate();
    @Entry(category = ARMORS) public static int copperArmor_leggings = new BaseSimpleOresConfig().copperArmorProtection.leggings();
    @Entry(category = ARMORS) public static int copperArmor_boots = new BaseSimpleOresConfig().copperArmorProtection.boots();
    @Entry(category = ARMORS) public static int copperArmor_body = new BaseSimpleOresConfig().copperArmorProtection.body();
    @Entry(category = ARMORS) public static int copperArmor_thoughness = new BaseSimpleOresConfig().copperArmorProtection.thoughness();
    @Entry(category = ARMORS) public static int copperArmor_knockbackProtection = new BaseSimpleOresConfig().copperArmorProtection.knockbackProtection();
    @Entry(category = ARMORS) public static int copperArmor_enchantability = new BaseSimpleOresConfig().copperArmorProtection.enchantability();

    @Entry(category = ARMORS) public static int tinArmorDurability = new BaseSimpleOresConfig().tinArmorDurability;
    @Entry(category = ARMORS) public static int tinArmor_helmet = new BaseSimpleOresConfig().tinArmorProtection.helmet();
    @Entry(category = ARMORS) public static int tinArmor_chestplate = new BaseSimpleOresConfig().tinArmorProtection.chestplate();
    @Entry(category = ARMORS) public static int tinArmor_leggings = new BaseSimpleOresConfig().tinArmorProtection.leggings();
    @Entry(category = ARMORS) public static int tinArmor_boots = new BaseSimpleOresConfig().tinArmorProtection.boots();
    @Entry(category = ARMORS) public static int tinArmor_body = new BaseSimpleOresConfig().tinArmorProtection.body();
    @Entry(category = ARMORS) public static int tinArmor_thoughness = new BaseSimpleOresConfig().tinArmorProtection.thoughness();
    @Entry(category = ARMORS) public static int tinArmor_knockbackProtection = new BaseSimpleOresConfig().tinArmorProtection.knockbackProtection();
    @Entry(category = ARMORS) public static int tinArmor_enchantability = new BaseSimpleOresConfig().tinArmorProtection.enchantability();

    @Entry(category = ARMORS) public static int mythrilArmorDurability = new BaseSimpleOresConfig().mythrilArmorDurability;
    @Entry(category = ARMORS) public static int mythrilArmor_helmet = new BaseSimpleOresConfig().mythrilArmorProtection.helmet();
    @Entry(category = ARMORS) public static int mythrilArmor_chestplate = new BaseSimpleOresConfig().mythrilArmorProtection.chestplate();
    @Entry(category = ARMORS) public static int mythrilArmor_leggings = new BaseSimpleOresConfig().mythrilArmorProtection.leggings();
    @Entry(category = ARMORS) public static int mythrilArmor_boots = new BaseSimpleOresConfig().mythrilArmorProtection.boots();
    @Entry(category = ARMORS) public static int mythrilArmor_body = new BaseSimpleOresConfig().mythrilArmorProtection.body();
    @Entry(category = ARMORS) public static int mythrilArmor_thoughness = new BaseSimpleOresConfig().mythrilArmorProtection.thoughness();
    @Entry(category = ARMORS) public static int mythrilArmor_knockbackProtection = new BaseSimpleOresConfig().mythrilArmorProtection.knockbackProtection();
    @Entry(category = ARMORS) public static int mythrilArmor_enchantability = new BaseSimpleOresConfig().mythrilArmorProtection.enchantability();

    @Entry(category = ARMORS) public static int adamantiumArmorDurability = new BaseSimpleOresConfig().adamantiumArmorDurability;
    @Entry(category = ARMORS) public static int adamantiumArmor_helmet = new BaseSimpleOresConfig().adamantiumArmorProtection.helmet();
    @Entry(category = ARMORS) public static int adamantiumArmor_chestplate = new BaseSimpleOresConfig().adamantiumArmorProtection.chestplate();
    @Entry(category = ARMORS) public static int adamantiumArmor_leggings = new BaseSimpleOresConfig().adamantiumArmorProtection.leggings();
    @Entry(category = ARMORS) public static int adamantiumArmor_boots = new BaseSimpleOresConfig().adamantiumArmorProtection.boots();
    @Entry(category = ARMORS) public static int adamantiumArmor_body = new BaseSimpleOresConfig().adamantiumArmorProtection.body();
    @Entry(category = ARMORS) public static int adamantiumArmor_thoughness = new BaseSimpleOresConfig().adamantiumArmorProtection.thoughness();
    @Entry(category = ARMORS) public static int adamantiumArmor_knockbackProtection = new BaseSimpleOresConfig().adamantiumArmorProtection.knockbackProtection();
    @Entry(category = ARMORS) public static int adamantiumArmor_enchantability = new BaseSimpleOresConfig().adamantiumArmorProtection.enchantability();

    @Entry(category = ARMORS) public static int onyxArmorDurability = new BaseSimpleOresConfig().onyxArmorDurability;
    @Entry(category = ARMORS) public static int onyxArmor_helmet = new BaseSimpleOresConfig().onyxArmorProtection.helmet();
    @Entry(category = ARMORS) public static int onyxArmor_chestplate = new BaseSimpleOresConfig().onyxArmorProtection.chestplate();
    @Entry(category = ARMORS) public static int onyxArmor_leggings = new BaseSimpleOresConfig().onyxArmorProtection.leggings();
    @Entry(category = ARMORS) public static int onyxArmor_boots = new BaseSimpleOresConfig().onyxArmorProtection.boots();
    @Entry(category = ARMORS) public static int onyxArmor_body = new BaseSimpleOresConfig().onyxArmorProtection.body();
    @Entry(category = ARMORS) public static int onyxArmor_thoughness = new BaseSimpleOresConfig().onyxArmorProtection.thoughness();
    @Entry(category = ARMORS) public static int onyxArmor_knockbackProtection = new BaseSimpleOresConfig().onyxArmorProtection.knockbackProtection();
    @Entry(category = ARMORS) public static int onyxArmor_enchantability = new BaseSimpleOresConfig().onyxArmorProtection.enchantability();

    @Entry(category = TOOLS) public static ModToolMaterials.MiningLevels copperTools_miningLevel = new BaseSimpleOresConfig().copperTools.miningLevel();
    @Entry(category = TOOLS) public static int copperTools_itemDurability = new BaseSimpleOresConfig().copperTools.itemDurability();
    @Entry(category = TOOLS) public static float copperTools_miningSpeed = new BaseSimpleOresConfig().copperTools.miningSpeed();
    @Entry(category = TOOLS) public static float copperTools_attackDamage = new BaseSimpleOresConfig().copperTools.attackDamage();
    @Entry(category = TOOLS) public static int copperTools_enchantability = new BaseSimpleOresConfig().copperTools.enchantability();

    @Entry(category = TOOLS) public static ModToolMaterials.MiningLevels tinTools_miningLevel = new BaseSimpleOresConfig().tinTools.miningLevel();
    @Entry(category = TOOLS) public static int tinTools_itemDurability = new BaseSimpleOresConfig().tinTools.itemDurability();
    @Entry(category = TOOLS) public static float tinTools_miningSpeed = new BaseSimpleOresConfig().tinTools.miningSpeed();
    @Entry(category = TOOLS) public static float tinTools_attackDamage = new BaseSimpleOresConfig().tinTools.attackDamage();
    @Entry(category = TOOLS) public static int tinTools_enchantability = new BaseSimpleOresConfig().tinTools.enchantability();

    @Entry(category = TOOLS) public static ModToolMaterials.MiningLevels mythrilTools_miningLevel = new BaseSimpleOresConfig().mythrilTools.miningLevel();
    @Entry(category = TOOLS) public static int mythrilTools_itemDurability = new BaseSimpleOresConfig().mythrilTools.itemDurability();
    @Entry(category = TOOLS) public static float mythrilTools_miningSpeed = new BaseSimpleOresConfig().mythrilTools.miningSpeed();
    @Entry(category = TOOLS) public static float mythrilTools_attackDamage = new BaseSimpleOresConfig().mythrilTools.attackDamage();
    @Entry(category = TOOLS) public static int mythrilTools_enchantability = new BaseSimpleOresConfig().mythrilTools.enchantability();

    @Entry(category = TOOLS) public static ModToolMaterials.MiningLevels adamantiumTools_miningLevel = new BaseSimpleOresConfig().adamantiumTools.miningLevel();
    @Entry(category = TOOLS) public static int adamantiumTools_itemDurability = new BaseSimpleOresConfig().adamantiumTools.itemDurability();
    @Entry(category = TOOLS) public static float adamantiumTools_miningSpeed = new BaseSimpleOresConfig().adamantiumTools.miningSpeed();
    @Entry(category = TOOLS) public static float adamantiumTools_attackDamage = new BaseSimpleOresConfig().adamantiumTools.attackDamage();
    @Entry(category = TOOLS) public static int adamantiumTools_enchantability = new BaseSimpleOresConfig().adamantiumTools.enchantability();

    @Entry(category = TOOLS) public static ModToolMaterials.MiningLevels onyxTools_miningLevel = new BaseSimpleOresConfig().onyxTools.miningLevel();
    @Entry(category = TOOLS) public static int onyxTools_itemDurability = new BaseSimpleOresConfig().onyxTools.itemDurability();
    @Entry(category = TOOLS) public static float onyxTools_miningSpeed = new BaseSimpleOresConfig().onyxTools.miningSpeed();
    @Entry(category = TOOLS) public static float onyxTools_attackDamage = new BaseSimpleOresConfig().onyxTools.attackDamage();
    @Entry(category = TOOLS) public static int onyxTools_enchantability = new BaseSimpleOresConfig().onyxTools.enchantability();

    @Entry(category = BOWS) public static int mythrilBowDurability = new BaseSimpleOresConfig().mythrilBowDurability;
    @Entry(category = BOWS) public static int onyxBowDurability = new BaseSimpleOresConfig().onyxBowDurability;

    @Entry(category = VILLAGERS) public static boolean enableTrades = new BaseSimpleOresConfig().enableTrades;

    //? if containsBucket {
    @Entry(category = COPPER_BUCKET) public static boolean enableCopperBucketMilking = new BaseSimpleOresConfig().enableCopperBucketMilking;
    @Entry(category = COPPER_BUCKET) public static int copperBucketMeltTemperature = new BaseSimpleOresConfig().copperBucketMeltTemperature;
    @Entry(category = COPPER_BUCKET) public static int copperBucketFireTemperature = new BaseSimpleOresConfig().copperBucketFireTemperature;
    //?}

    @Override
    public int copperArmorDurability() { return copperArmorDurability; }
    @Override
    public BaseSimpleOresConfig.ArmorProtection copperArmorProtection() {
        return new BaseSimpleOresConfig.ArmorProtection(copperArmor_helmet, copperArmor_chestplate, copperArmor_leggings, copperArmor_boots, copperArmor_body, copperArmor_thoughness, copperArmor_knockbackProtection, copperArmor_enchantability);
    }

    @Override
    public int tinArmorDurability() { return tinArmorDurability; }
    @Override
    public BaseSimpleOresConfig.ArmorProtection tinArmorProtection() {
        return new BaseSimpleOresConfig.ArmorProtection(tinArmor_helmet, tinArmor_chestplate, tinArmor_leggings, tinArmor_boots, tinArmor_body, tinArmor_thoughness, tinArmor_knockbackProtection, tinArmor_enchantability);
    }

    @Override
    public int mythrilArmorDurability() { return mythrilArmorDurability; }
    @Override
    public BaseSimpleOresConfig.ArmorProtection mythrilArmorProtection() {
        return new BaseSimpleOresConfig.ArmorProtection(mythrilArmor_helmet, mythrilArmor_chestplate, mythrilArmor_leggings, mythrilArmor_boots, mythrilArmor_body, mythrilArmor_thoughness, mythrilArmor_knockbackProtection, mythrilArmor_enchantability);
    }

    @Override
    public int adamantiumArmorDurability() { return adamantiumArmorDurability; }
    @Override
    public BaseSimpleOresConfig.ArmorProtection adamantiumArmorProtection() {
        return new BaseSimpleOresConfig.ArmorProtection(adamantiumArmor_helmet, adamantiumArmor_chestplate, adamantiumArmor_leggings, adamantiumArmor_boots, adamantiumArmor_body, adamantiumArmor_thoughness, adamantiumArmor_knockbackProtection, adamantiumArmor_enchantability);
    }

    @Override
    public int onyxArmorDurability() { return onyxArmorDurability; }
    @Override
    public BaseSimpleOresConfig.ArmorProtection onyxArmorProtection() {
        return new BaseSimpleOresConfig.ArmorProtection(onyxArmor_helmet, onyxArmor_chestplate, onyxArmor_leggings, onyxArmor_boots, onyxArmor_body, onyxArmor_thoughness, onyxArmor_knockbackProtection, onyxArmor_enchantability);
    }

    @Override
    public BaseSimpleOresConfig.ToolsProperties copperTools() {
        return new BaseSimpleOresConfig.ToolsProperties(copperTools_miningLevel, copperTools_itemDurability, copperTools_miningSpeed, copperTools_attackDamage, copperTools_enchantability);
    }

    @Override
    public BaseSimpleOresConfig.ToolsProperties tinTools() {
        return new BaseSimpleOresConfig.ToolsProperties(tinTools_miningLevel, tinTools_itemDurability, tinTools_miningSpeed, tinTools_attackDamage, tinTools_enchantability);
    }

    @Override
    public BaseSimpleOresConfig.ToolsProperties mythrilTools() {
        return new BaseSimpleOresConfig.ToolsProperties(mythrilTools_miningLevel, mythrilTools_itemDurability, mythrilTools_miningSpeed, mythrilTools_attackDamage, mythrilTools_enchantability);
    }

    @Override
    public BaseSimpleOresConfig.ToolsProperties adamantiumTools() {
        return new BaseSimpleOresConfig.ToolsProperties(adamantiumTools_miningLevel, adamantiumTools_itemDurability, adamantiumTools_miningSpeed, adamantiumTools_attackDamage, adamantiumTools_enchantability);
    }

    @Override
    public BaseSimpleOresConfig.ToolsProperties onyxTools() {
        return new BaseSimpleOresConfig.ToolsProperties(onyxTools_miningLevel, onyxTools_itemDurability, onyxTools_miningSpeed, onyxTools_attackDamage, onyxTools_enchantability);
    }

    @Override
    public int mythrilBowDurability() { return mythrilBowDurability; }
    @Override
    public int onyxBowDurability() { return onyxBowDurability; }

    @Override
    public boolean enableTrades() { return enableTrades; }

    //? if containsBucket {
    @Override
    public boolean enableCopperBucketMilking() { return enableCopperBucketMilking; }
    @Override
    public int copperBucketMeltTemperature() { return copperBucketMeltTemperature; }
    @Override
    public int copperBucketFireTemperature() { return copperBucketFireTemperature; }
    //?}
}
//? } else {
  /*public class SimpleOresConfig {}
*///? }
