package io.github.paulem.simpleores.config;

import io.github.paulem.simpleores.SimpleOres;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

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
    public ArmorProtection copperArmorProtection = new ArmorProtection(2, 3, 2, 1, 0, 0);

    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("armors")
    public int tinArmorDurability = 9;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("armors")
    @ConfigEntry.Gui.CollapsibleObject
    public ArmorProtection tinArmorProtection = new ArmorProtection(2, 3, 2, 1, 0, 0);

    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("armors")
    public int mythrilArmorDurability = 22;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("armors")
    @ConfigEntry.Gui.CollapsibleObject
    public ArmorProtection mythrilArmorProtection = new ArmorProtection(3, 5, 4, 3, 0, 0);

    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("armors")
    public int adamantiumArmorDurability = 28;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("armors")
    @ConfigEntry.Gui.CollapsibleObject
    public ArmorProtection adamantiumArmorProtection = new ArmorProtection(3, 8, 6, 2, 1, 0);

    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("armors")
    public int onyxArmorDurability = 45;
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.Category("armors")
    @ConfigEntry.Gui.CollapsibleObject
    public ArmorProtection onyxArmorProtection = new ArmorProtection(5, 8, 6, 5, 2, 0);

    @SuppressWarnings("all")
    public static final class ArmorProtection {
        private int helmet;
        private int chestplate;
        private int leggings;
        private int boots;
        private int thoughness;
        private int knockbackProtection;

        public ArmorProtection(int helmet, int chestplate, int leggings, int boots, int thoughness, int knockbackProtection) {
            this.helmet = helmet;
            this.chestplate = chestplate;
            this.leggings = leggings;
            this.boots = boots;
            this.thoughness = thoughness;
            this.knockbackProtection = knockbackProtection;
        }

        public int[] getProtectionAmount() {
                return new int[]{helmet(), chestplate(), leggings(), boots()};
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

        public int thoughness() {
            return thoughness;
        }

        public int knockbackProtection() {
            return knockbackProtection;
        }

    }

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
