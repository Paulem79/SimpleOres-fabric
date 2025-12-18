package net.paulem.simpleores.config;

//? if hasClothConfig {
/*import me.shedaniel.autoconfig.annotation.Config;
import net.paulem.simpleores.SimpleOres;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.paulem.simpleores.items.ModToolMaterials;

@Config(name = SimpleOres.MOD_ID)
public class SimpleOresConfig implements ConfigData, net.paulem.simpleores.config.Config
{
    @ConfigEntry.Gui.Excluded
    private final BaseSimpleOresConfig supers = new BaseSimpleOresConfig();

    public static class ToolsProperties extends BaseSimpleOresConfig.ToolsProperties {
        public ToolsProperties(ModToolMaterials.MiningLevels miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability) {
            super(miningLevel, itemDurability, miningSpeed, attackDamage, enchantability);
        }
    }
    public static class ArmorProtection extends BaseSimpleOresConfig.ArmorProtection {
        public ArmorProtection(int helmet, int chestplate, int leggings, int boots, int body, int thoughness, int knockbackProtection, int enchantability) {
            super(helmet, chestplate, leggings, boots, body, thoughness, knockbackProtection, enchantability);
        }
    }

    // armor
    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("armors")
    public int copperArmorDurability = supers.copperArmorDurability;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("armors")
    @ConfigEntry.Gui.CollapsibleObject
    public BaseSimpleOresConfig.ArmorProtection copperArmorProtection = supers.copperArmorProtection;

    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("armors")
    public int tinArmorDurability = supers.tinArmorDurability;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("armors")
    @ConfigEntry.Gui.CollapsibleObject
    public BaseSimpleOresConfig.ArmorProtection tinArmorProtection = supers.tinArmorProtection;

    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("armors")
    public int mythrilArmorDurability = supers.mythrilArmorDurability;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("armors")
    @ConfigEntry.Gui.CollapsibleObject
    public BaseSimpleOresConfig.ArmorProtection mythrilArmorProtection = supers.mythrilArmorProtection;

    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("armors")
    public int adamantiumArmorDurability = supers.adamantiumArmorDurability;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("armors")
    @ConfigEntry.Gui.CollapsibleObject
    public BaseSimpleOresConfig.ArmorProtection adamantiumArmorProtection = supers.adamantiumArmorProtection;

    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("armors")
    public int onyxArmorDurability = supers.onyxArmorDurability;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("armors")
    @ConfigEntry.Gui.CollapsibleObject
    public BaseSimpleOresConfig.ArmorProtection onyxArmorProtection = supers.onyxArmorProtection;

    // tools
    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("tools")
    @ConfigEntry.Gui.CollapsibleObject
    public BaseSimpleOresConfig.ToolsProperties copperTools = supers.copperTools;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("tools")
    @ConfigEntry.Gui.CollapsibleObject
    public BaseSimpleOresConfig.ToolsProperties tinTools = supers.tinTools;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("tools")
    @ConfigEntry.Gui.CollapsibleObject
    public BaseSimpleOresConfig.ToolsProperties mythrilTools = supers.mythrilTools;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("tools")
    @ConfigEntry.Gui.CollapsibleObject
    public BaseSimpleOresConfig.ToolsProperties adamantiumTools = supers.adamantiumTools;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("tools")
    @ConfigEntry.Gui.CollapsibleObject
    public BaseSimpleOresConfig.ToolsProperties onyxTools = supers.onyxTools;

    // bows
    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("bows")
    public int mythrilBowDurability = supers.mythrilBowDurability;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("bows")
    public int onyxBowDurability = supers.onyxBowDurability;

    // villagers
    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean enableTrades = supers.enableTrades;

    // ARMORER
    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean armorerEmeraldCopperHelmet = supers.armorerEmeraldCopperHelmet;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean armorerEmeraldCopperChestplate = supers.armorerEmeraldCopperChestplate;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean armorerEmeraldCopperLeggings = supers.armorerEmeraldCopperLeggings;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean armorerEmeraldCopperBoots = supers.armorerEmeraldCopperBoots;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean armorerCopperToEmeralds = supers.armorerCopperToEmeralds;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean armorerTinToEmeralds = supers.armorerTinToEmeralds;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean armorerEmeraldTinLeggings = supers.armorerEmeraldTinLeggings;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean armorerEmeraldTinBoots = supers.armorerEmeraldTinBoots;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean armorerMythrilToEmeralds = supers.armorerMythrilToEmeralds;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean armorerEmeraldTinHelmet = supers.armorerEmeraldTinHelmet;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean armorerEmeraldTinChestplate = supers.armorerEmeraldTinChestplate;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean armorerEmeraldMythrilLeggingsEnchanted = supers.armorerEmeraldMythrilLeggingsEnchanted;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean armorerEmeraldMythrilBootsEnchanted = supers.armorerEmeraldMythrilBootsEnchanted;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean armorerEmeraldMythrilHelmetEnchanted = supers.armorerEmeraldMythrilHelmetEnchanted;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean armorerEmeraldMythrilChestplateEnchanted = supers.armorerEmeraldMythrilChestplateEnchanted;

    // TOOLSMITH
    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean toolsmithCopperToEmeralds = supers.toolsmithCopperToEmeralds;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean toolsmithTinToEmeralds = supers.toolsmithTinToEmeralds;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean toolsmithEmeraldCopperAxe = supers.toolsmithEmeraldCopperAxe;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean toolsmithEmeraldCopperShovel = supers.toolsmithEmeraldCopperShovel;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean toolsmithEmeraldCopperHoe = supers.toolsmithEmeraldCopperHoe;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean toolsmithEmeraldCopperPickaxe = supers.toolsmithEmeraldCopperPickaxe;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean toolsmithEmeraldTinAxe = supers.toolsmithEmeraldTinAxe;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean toolsmithEmeraldTinShovel = supers.toolsmithEmeraldTinShovel;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean toolsmithEmeraldTinHoe = supers.toolsmithEmeraldTinHoe;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean toolsmithEmeraldTinPickaxe = supers.toolsmithEmeraldTinPickaxe;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean toolsmithMythrilToEmerald = supers.toolsmithMythrilToEmerald;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean toolsmithEmeraldMythrilAxeEnchantedLvl3 = supers.toolsmithEmeraldMythrilAxeEnchantedLvl3;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean toolsmithEmeraldMythrilShovelEnchantedLvl3 = supers.toolsmithEmeraldMythrilShovelEnchantedLvl3;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean toolsmithEmeraldMythrilPickaxeEnchantedLvl3 = supers.toolsmithEmeraldMythrilPickaxeEnchantedLvl3;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean toolsmithEmeraldMythrilHoe = supers.toolsmithEmeraldMythrilHoe;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean toolsmithAdamantiumToEmerald = supers.toolsmithAdamantiumToEmerald;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean toolsmithEmeraldAdamantiumAxeEnchanted = supers.toolsmithEmeraldAdamantiumAxeEnchanted;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean toolsmithEmeraldMythrilShovelEnchanted = supers.toolsmithEmeraldMythrilShovelEnchanted;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean toolsmithEmeraldMythrilPickaxeEnchanted = supers.toolsmithEmeraldMythrilPickaxeEnchanted;

    // WEAPONSMITH
    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean weaponsmithEmeraldMythrilAxe = supers.weaponsmithEmeraldMythrilAxe;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean weaponsmithEmeraldMythrilSwordEnchanted = supers.weaponsmithEmeraldMythrilSwordEnchanted;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean weaponsmithCopperToEmerald = supers.weaponsmithCopperToEmerald;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean weaponsmithTinToEmerald = supers.weaponsmithTinToEmerald;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean weaponsmithMythrilToEmerald = supers.weaponsmithMythrilToEmerald;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean weaponsmithAdamantiumToEmerald = supers.weaponsmithAdamantiumToEmerald;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean weaponsmithEmeraldAdamantiumAxeEnchanted = supers.weaponsmithEmeraldAdamantiumAxeEnchanted;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean weaponsmithEmeraldAdamantiumSwordEnchanted = supers.weaponsmithEmeraldAdamantiumSwordEnchanted;

    // copper bucket
    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("copper_bucket")
    public boolean enableCopperBucketMilking = supers.enableCopperBucketMilking;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("copper_bucket")
    public int copperBucketMeltTemperature = supers.copperBucketMeltTemperature;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("copper_bucket")
    public int copperBucketFireTemperature = supers.copperBucketFireTemperature;

    // Implement Config getters to expose the public fields via the interface
    @Override
    public int copperArmorDurability() { return this.copperArmorDurability; }
    @Override
    public BaseSimpleOresConfig.ArmorProtection copperArmorProtection() { return this.copperArmorProtection; }

    @Override
    public int tinArmorDurability() { return this.tinArmorDurability; }
    @Override
    public BaseSimpleOresConfig.ArmorProtection tinArmorProtection() { return this.tinArmorProtection; }

    @Override
    public int mythrilArmorDurability() { return this.mythrilArmorDurability; }
    @Override
    public BaseSimpleOresConfig.ArmorProtection mythrilArmorProtection() { return this.mythrilArmorProtection; }

    @Override
    public int adamantiumArmorDurability() { return this.adamantiumArmorDurability; }
    @Override
    public BaseSimpleOresConfig.ArmorProtection adamantiumArmorProtection() { return this.adamantiumArmorProtection; }

    @Override
    public int onyxArmorDurability() { return this.onyxArmorDurability; }
    @Override
    public BaseSimpleOresConfig.ArmorProtection onyxArmorProtection() { return this.onyxArmorProtection; }

    @Override
    public BaseSimpleOresConfig.ToolsProperties copperTools() { return this.copperTools; }
    @Override
    public BaseSimpleOresConfig.ToolsProperties tinTools() { return this.tinTools; }
    @Override
    public BaseSimpleOresConfig.ToolsProperties mythrilTools() { return this.mythrilTools; }
    @Override
    public BaseSimpleOresConfig.ToolsProperties adamantiumTools() { return this.adamantiumTools; }
    @Override
    public BaseSimpleOresConfig.ToolsProperties onyxTools() { return this.onyxTools; }

    @Override
    public int mythrilBowDurability() { return this.mythrilBowDurability; }
    @Override
    public int onyxBowDurability() { return this.onyxBowDurability; }

    @Override
    public boolean enableTrades() { return this.enableTrades; }

    @Override
    public boolean armorerEmeraldCopperHelmet() { return this.armorerEmeraldCopperHelmet; }
    @Override
    public boolean armorerEmeraldCopperChestplate() { return this.armorerEmeraldCopperChestplate; }
    @Override
    public boolean armorerEmeraldCopperLeggings() { return this.armorerEmeraldCopperLeggings; }
    @Override
    public boolean armorerEmeraldCopperBoots() { return this.armorerEmeraldCopperBoots; }
    @Override
    public boolean armorerCopperToEmeralds() { return this.armorerCopperToEmeralds; }
    @Override
    public boolean armorerTinToEmeralds() { return this.armorerTinToEmeralds; }
    @Override
    public boolean armorerEmeraldTinLeggings() { return this.armorerEmeraldTinLeggings; }
    @Override
    public boolean armorerEmeraldTinBoots() { return this.armorerEmeraldTinBoots; }
    @Override
    public boolean armorerMythrilToEmeralds() { return this.armorerMythrilToEmeralds; }
    @Override
    public boolean armorerEmeraldTinHelmet() { return this.armorerEmeraldTinHelmet; }
    @Override
    public boolean armorerEmeraldTinChestplate() { return this.armorerEmeraldTinChestplate; }
    @Override
    public boolean armorerEmeraldMythrilLeggingsEnchanted() { return this.armorerEmeraldMythrilLeggingsEnchanted; }
    @Override
    public boolean armorerEmeraldMythrilBootsEnchanted() { return this.armorerEmeraldMythrilBootsEnchanted; }
    @Override
    public boolean armorerEmeraldMythrilHelmetEnchanted() { return this.armorerEmeraldMythrilHelmetEnchanted; }
    @Override
    public boolean armorerEmeraldMythrilChestplateEnchanted() { return this.armorerEmeraldMythrilChestplateEnchanted; }

    @Override
    public boolean toolsmithCopperToEmeralds() { return this.toolsmithCopperToEmeralds; }
    @Override
    public boolean toolsmithTinToEmeralds() { return this.toolsmithTinToEmeralds; }
    @Override
    public boolean toolsmithEmeraldCopperAxe() { return this.toolsmithEmeraldCopperAxe; }
    @Override
    public boolean toolsmithEmeraldCopperShovel() { return this.toolsmithEmeraldCopperShovel; }
    @Override
    public boolean toolsmithEmeraldCopperHoe() { return this.toolsmithEmeraldCopperHoe; }
    @Override
    public boolean toolsmithEmeraldCopperPickaxe() { return this.toolsmithEmeraldCopperPickaxe; }
    @Override
    public boolean toolsmithEmeraldTinAxe() { return this.toolsmithEmeraldTinAxe; }
    @Override
    public boolean toolsmithEmeraldTinShovel() { return this.toolsmithEmeraldTinShovel; }
    @Override
    public boolean toolsmithEmeraldTinHoe() { return this.toolsmithEmeraldTinHoe; }
    @Override
    public boolean toolsmithEmeraldTinPickaxe() { return this.toolsmithEmeraldTinPickaxe; }
    @Override
    public boolean toolsmithMythrilToEmerald() { return this.toolsmithMythrilToEmerald; }
    @Override
    public boolean toolsmithEmeraldMythrilAxeEnchantedLvl3() { return this.toolsmithEmeraldMythrilAxeEnchantedLvl3; }
    @Override
    public boolean toolsmithEmeraldMythrilShovelEnchantedLvl3() { return this.toolsmithEmeraldMythrilShovelEnchantedLvl3; }
    @Override
    public boolean toolsmithEmeraldMythrilPickaxeEnchantedLvl3() { return this.toolsmithEmeraldMythrilPickaxeEnchantedLvl3; }
    @Override
    public boolean toolsmithEmeraldMythrilHoe() { return this.toolsmithEmeraldMythrilHoe; }
    @Override
    public boolean toolsmithAdamantiumToEmerald() { return this.toolsmithAdamantiumToEmerald; }
    @Override
    public boolean toolsmithEmeraldAdamantiumAxeEnchanted() { return this.toolsmithEmeraldAdamantiumAxeEnchanted; }
    @Override
    public boolean toolsmithEmeraldMythrilShovelEnchanted() { return this.toolsmithEmeraldMythrilShovelEnchanted; }
    @Override
    public boolean toolsmithEmeraldMythrilPickaxeEnchanted() { return this.toolsmithEmeraldMythrilPickaxeEnchanted; }

    @Override
    public boolean weaponsmithEmeraldMythrilAxe() { return this.weaponsmithEmeraldMythrilAxe; }
    @Override
    public boolean weaponsmithEmeraldMythrilSwordEnchanted() { return this.weaponsmithEmeraldMythrilSwordEnchanted; }
    @Override
    public boolean weaponsmithCopperToEmerald() { return this.weaponsmithCopperToEmerald; }
    @Override
    public boolean weaponsmithTinToEmerald() { return this.weaponsmithTinToEmerald; }
    @Override
    public boolean weaponsmithMythrilToEmerald() { return this.weaponsmithMythrilToEmerald; }
    @Override
    public boolean weaponsmithAdamantiumToEmerald() { return this.weaponsmithAdamantiumToEmerald; }
    @Override
    public boolean weaponsmithEmeraldAdamantiumAxeEnchanted() { return this.weaponsmithEmeraldAdamantiumAxeEnchanted; }
    @Override
    public boolean weaponsmithEmeraldAdamantiumSwordEnchanted() { return this.weaponsmithEmeraldAdamantiumSwordEnchanted; }

    @Override
    public boolean enableCopperBucketMilking() { return this.enableCopperBucketMilking; }
    @Override
    public int copperBucketMeltTemperature() { return this.copperBucketMeltTemperature; }
    @Override
    public int copperBucketFireTemperature() { return this.copperBucketFireTemperature; }

}  // end class SimpleOresConfig
*///? } else {
  public class SimpleOresConfig {}
//? }