package io.github.paulem.simpleores.config;

import io.github.paulem.simpleores.SimpleOres;
import io.github.paulem.simpleores.items.SimpleOresTiers;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.minecraft.item.ArmorItem;
import net.minecraft.util.Util;

import java.util.EnumMap;

@Config(name = SimpleOres.MOD_ID)
public class SimpleOresConfig implements ConfigData
{
    public static class NotEditable {
        // ores. Sadly, they are datapack-driven :'( (so not editable in the config)
        public static int tinOreVeinPerChunks = 10;
        public static int tinOreBlocksPerVeins = 7;
        public static int tinVeinVeinPerChunks = 4;
        public static int tinVeinBlocksPerVeins = 16;
        public static int mythrilVeinPerChunks = 8;
        public static int mythrilBlocksPerVeins = 4;
        public static int adamantiumVeinPerChunks = 4;
        public static int adamantiumBlocksPerVeins = 4;
        public static int onyxVeinPerChunks = 5;
        public static int onyxBlocksPerVeins = 4;
    }

    // armor
    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("armors")
    public int copperArmorDurability = 8;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("armors")
    @ConfigEntry.Gui.CollapsibleObject
    public ArmorProtection copperArmorProtection = new ArmorProtection(2, 3, 2, 1, 3, 0, 0, 8);

    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("armors")
    public int tinArmorDurability = 9;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("armors")
    @ConfigEntry.Gui.CollapsibleObject
    public ArmorProtection tinArmorProtection = new ArmorProtection(2, 3, 2, 1, 3, 0, 0, 8);

    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("armors")
    public int mythrilArmorDurability = 22;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("armors")
    @ConfigEntry.Gui.CollapsibleObject
    public ArmorProtection mythrilArmorProtection = new ArmorProtection(3, 5, 4, 3, 4, 0, 0, 12);

    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("armors")
    public int adamantiumArmorDurability = 28;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("armors")
    @ConfigEntry.Gui.CollapsibleObject
    public ArmorProtection adamantiumArmorProtection = new ArmorProtection(3, 8, 6, 2, 8, 1, 0, 3);

    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("armors")
    public int onyxArmorDurability = 45;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("armors")
    @ConfigEntry.Gui.CollapsibleObject
    public ArmorProtection onyxArmorProtection = new ArmorProtection(5, 8, 6, 5, 11, 2, 0, 15);

    @SuppressWarnings("all")
    public static final class ArmorProtection {
        private int helmet;
        private int chestplate;
        private int leggings;
        private int boots;
        private int body;
        private int thoughness;
        private int knockbackProtection;
        private int enchantability;

        public ArmorProtection(int helmet, int chestplate, int leggings, int boots, int body, int thoughness, int knockbackProtection, int enchantability) {
            this.helmet = helmet;
            this.chestplate = chestplate;
            this.leggings = leggings;
            this.boots = boots;
            this.thoughness = thoughness;
            this.knockbackProtection = knockbackProtection;
            this.enchantability = enchantability;
        }

        public EnumMap<ArmorItem.Type, Integer> setProtectionAmount() {
            return Util.make(new EnumMap<>(ArmorItem.Type.class), attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, boots());
                attribute.put(ArmorItem.Type.LEGGINGS, leggings());
                attribute.put(ArmorItem.Type.CHESTPLATE, chestplate());
                attribute.put(ArmorItem.Type.HELMET, helmet());
                attribute.put(ArmorItem.Type.BODY, body());
            });
        }

        public int helmet() {
            return helmet;
        }

        public int chestplate() {
            return chestplate;
        }

        public int leggings() {
            return leggings;
        }

        public int boots() {
            return boots;
        }

        public int body() {
            return body;
        }

        public int thoughness() {
            return thoughness;
        }

        public int knockbackProtection() {
            return knockbackProtection;
        }

        public int enchantability() {
            return enchantability;
        }

    }

    // tools
    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("tools")
    @ConfigEntry.Gui.CollapsibleObject
    public ToolsProperties copperTools = new ToolsProperties(SimpleOresTiers.MiningLevels.STONE, 185, 4.0f, 1.0f, 8);
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("tools")
    @ConfigEntry.Gui.CollapsibleObject
    public ToolsProperties tinTools = new ToolsProperties(SimpleOresTiers.MiningLevels.STONE, 220, 3.5F, 1.0F, 8);
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("tools")
    @ConfigEntry.Gui.CollapsibleObject
    public ToolsProperties mythrilTools = new ToolsProperties(SimpleOresTiers.MiningLevels.IRON, 800, 8.0F, 3.0F, 12);
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("tools")
    @ConfigEntry.Gui.CollapsibleObject
    public ToolsProperties adamantiumTools = new ToolsProperties(SimpleOresTiers.MiningLevels.IRON, 1150, 14.0F, 3.0F, 3);
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("tools")
    @ConfigEntry.Gui.CollapsibleObject
    public ToolsProperties onyxTools = new ToolsProperties(SimpleOresTiers.MiningLevels.NETHERITE, 3280, 10.0F, 5.0F, 15);

    @SuppressWarnings("all")
    public static final class ToolsProperties {
        private SimpleOresTiers.MiningLevels miningLevel;
        private int itemDurability;
        private float miningSpeed;
        private float attackDamage;
        private int enchantability;

        public ToolsProperties(SimpleOresTiers.MiningLevels miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability) {
            this.miningLevel = miningLevel;
            this.itemDurability = itemDurability;
            this.miningSpeed = miningSpeed;
            this.attackDamage = attackDamage;
            this.enchantability = enchantability;
        }

        public SimpleOresTiers.MiningLevels miningLevel() {
            return miningLevel;
        }

        public int itemDurability() {
            return itemDurability;
        }

        public float miningSpeed() {
            return miningSpeed;
        }

        public float attackDamage() {
            return attackDamage;
        }

        public int enchantability() {
            return enchantability;
        }
    }

    // bows
    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("bows")
    public int mythrilBowDurability = 750;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("bows")
    public int onyxBowDurability = 1000;

    // villagers
    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean enableTrades = true;

    // ARMORER
    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean armorerEmeraldCopperHelmet = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean armorerEmeraldCopperChestplate = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean armorerEmeraldCopperLeggings = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean armorerEmeraldCopperBoots = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean armorerCopperToEmeralds = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean armorerTinToEmeralds = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean armorerEmeraldTinLeggings = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean armorerEmeraldTinBoots = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean armorerMythrilToEmeralds = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean armorerEmeraldTinHelmet = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean armorerEmeraldTinChestplate = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean armorerEmeraldMythrilLeggingsEnchanted = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean armorerEmeraldMythrilBootsEnchanted = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean armorerEmeraldMythrilHelmetEnchanted = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean armorerEmeraldMythrilChestplateEnchanted = true;

    // TOOLSMITH
    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean toolsmithCopperToEmeralds = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean toolsmithTinToEmeralds = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean toolsmithEmeraldCopperAxe = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean toolsmithEmeraldCopperShovel = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean toolsmithEmeraldCopperHoe = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean toolsmithEmeraldCopperPickaxe = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean toolsmithEmeraldTinAxe = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean toolsmithEmeraldTinShovel = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean toolsmithEmeraldTinHoe = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean toolsmithEmeraldTinPickaxe = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean toolsmithMythrilToEmerald = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean toolsmithEmeraldMythrilAxeEnchantedLvl3 = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean toolsmithEmeraldMythrilShovelEnchantedLvl3 = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean toolsmithEmeraldMythrilPickaxeEnchantedLvl3 = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean toolsmithEmeraldMythrilHoe = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean toolsmithAdamantiumToEmerald = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean toolsmithEmeraldAdamantiumAxeEnchanted = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean toolsmithEmeraldMythrilShovelEnchanted = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean toolsmithEmeraldMythrilPickaxeEnchanted = true;

    // WEAPONSMITH
    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean weaponsmithEmeraldMythrilAxe = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean weaponsmithEmeraldMythrilSwordEnchanted = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean weaponsmithCopperToEmerald = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean weaponsmithTinToEmerald = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean weaponsmithMythrilToEmerald = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean weaponsmithAdamantiumToEmerald = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean weaponsmithEmeraldAdamantiumAxeEnchanted = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("villagers")
    public boolean weaponsmithEmeraldAdamantiumSwordEnchanted = true;

    // copper bucket
    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("copper_bucket")
    public boolean enableCopperBucketMilking = true;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("copper_bucket")
    public int copperBucketMeltTemperature = 1000;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("copper_bucket")
    public int copperBucketFireTemperature = 9999;

}  // end class SimpleOresConfig