package net.paulem.simpleores.config;

import net.minecraft.util.Util;
import net.paulem.simpleores.stonecutter.SCArmor;
import net.paulem.simpleores.items.ModToolMaterials;

/**
 * Classe de base contenant les valeurs par défaut des configurations
 * et les classes utilitaires (sans annotations de config).
 */
@SuppressWarnings("unused")
public class BaseSimpleOresConfig implements Config {
    public BaseSimpleOresConfig() {
        // Intentionally empty: this no-arg constructor is used by the config loader/serializer.
    }

    public static class NotEditable {
        protected NotEditable() {}
        // ores. Sadly, they are datapack-driven :'( (so not editable in the config)
        public static final int tinOreVeinPerChunks = 10;
        public static final int tinOreBlocksPerVeins = 7;
        public static final int tinVeinVeinPerChunks = 4;
        public static final int tinVeinBlocksPerVeins = 16;
        public static final int mythrilVeinPerChunks = 8;
        public static final int mythrilBlocksPerVeins = 4;
        public static final int adamantiumVeinPerChunks = 4;
        public static final int adamantiumBlocksPerVeins = 4;
        public static final int onyxVeinPerChunks = 5;
        public static final int onyxBlocksPerVeins = 4;
    }

    // armor defaults
    public final int copperArmorDurability = 8;
    public final ArmorProtection copperArmorProtection = new ArmorProtection(2, 3, 2, 1, 3, 0, 0, 8);

    public final int tinArmorDurability = 9;
    public final ArmorProtection tinArmorProtection = new ArmorProtection(2, 3, 2, 1, 3, 0, 0, 8);

    public final int mythrilArmorDurability = 22;
    public final ArmorProtection mythrilArmorProtection = new ArmorProtection(3, 5, 4, 3, 4, 0, 0, 12);

    public final int adamantiumArmorDurability = 28;
    public final ArmorProtection adamantiumArmorProtection = new ArmorProtection(3, 8, 6, 2, 8, 1, 0, 3);

    public final int onyxArmorDurability = 45;
    public final ArmorProtection onyxArmorProtection = new ArmorProtection(5, 8, 6, 5, 11, 2, 0, 15);

    // tools defaults
    public final ToolsProperties copperTools = new ToolsProperties(ModToolMaterials.MiningLevels.STONE, 185, 4.0f, 1.0f, 8);
    public final ToolsProperties tinTools = new ToolsProperties(ModToolMaterials.MiningLevels.STONE, 220, 3.5F, 1.0F, 8);
    public final ToolsProperties mythrilTools = new ToolsProperties(ModToolMaterials.MiningLevels.IRON, 800, 8.0F, 3.0F, 12);
    public final ToolsProperties adamantiumTools = new ToolsProperties(ModToolMaterials.MiningLevels.IRON, 1150, 14.0F, 3.0F, 3);
    public final ToolsProperties onyxTools = new ToolsProperties(ModToolMaterials.MiningLevels.NETHERITE, 3280, 10.0F, 5.0F, 15);

    // bows defaults
    public final int mythrilBowDurability = 750;
    public final int onyxBowDurability = 1000;

    // villagers defaults
    public final boolean enableTrades = true;

    // copper bucket defaults
    //? if hasBucketlib {
    /*public final boolean enableCopperBucketMilking = true;
    public final int copperBucketMeltTemperature = 1000;
    public final int copperBucketFireTemperature = 9999;
    *///?}

    // Implement Config getters
    @Override
    public int copperArmorDurability() { return this.copperArmorDurability; }
    @Override
    public ArmorProtection copperArmorProtection() { return this.copperArmorProtection; }

    @Override
    public int tinArmorDurability() { return this.tinArmorDurability; }
    @Override
    public ArmorProtection tinArmorProtection() { return this.tinArmorProtection; }

    @Override
    public int mythrilArmorDurability() { return this.mythrilArmorDurability; }
    @Override
    public ArmorProtection mythrilArmorProtection() { return this.mythrilArmorProtection; }

    @Override
    public int adamantiumArmorDurability() { return this.adamantiumArmorDurability; }
    @Override
    public ArmorProtection adamantiumArmorProtection() { return this.adamantiumArmorProtection; }

    @Override
    public int onyxArmorDurability() { return this.onyxArmorDurability; }
    @Override
    public ArmorProtection onyxArmorProtection() { return this.onyxArmorProtection; }

    @Override
    public ToolsProperties copperTools() { return this.copperTools; }
    @Override
    public ToolsProperties tinTools() { return this.tinTools; }
    @Override
    public ToolsProperties mythrilTools() { return this.mythrilTools; }
    @Override
    public ToolsProperties adamantiumTools() { return this.adamantiumTools; }
    @Override
    public ToolsProperties onyxTools() { return this.onyxTools; }

    @Override
    public int mythrilBowDurability() { return this.mythrilBowDurability; }
    @Override
    public int onyxBowDurability() { return this.onyxBowDurability; }

    @Override
    public boolean enableTrades() { return this.enableTrades; }

    //? hasBucketlib {
    
    /*@Override
    public boolean enableCopperBucketMilking() { return this.enableCopperBucketMilking; }
    @Override
    public int copperBucketMeltTemperature() { return this.copperBucketMeltTemperature; }
    @Override
    public int copperBucketFireTemperature() { return this.copperBucketFireTemperature; }
    *///?}

     @SuppressWarnings("all")
     public static class ArmorProtection {
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
             this.body = body;
         }

         //? if >1.20.4 {
         public SCArmor.EnumProtection setProtectionAmount() {
             return Util.make(new SCArmor.EnumProtection(SCArmor.ArmorEquipmentType.class), attribute -> {
                 attribute.put(SCArmor.ArmorEquipmentType.BOOTS, boots());
                 attribute.put(SCArmor.ArmorEquipmentType.LEGGINGS, leggings());
                 attribute.put(SCArmor.ArmorEquipmentType.CHESTPLATE, chestplate());
                 attribute.put(SCArmor.ArmorEquipmentType.HELMET, helmet());
                 attribute.put(SCArmor.ArmorEquipmentType.BODY, body());
             });
         }
         //?} else {

         /*public int[] getProtectionAmount() {
             return new int[]{helmet(), chestplate(), leggings(), boots()};
         }

         */
         //?}

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

     @SuppressWarnings("all")
     public static class ToolsProperties {
         private ModToolMaterials.MiningLevels miningLevel;
         private int itemDurability;
         private float miningSpeed;
         private float attackDamage;
         private int enchantability;

         public ToolsProperties(ModToolMaterials.MiningLevels miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability) {
             this.miningLevel = miningLevel;
             this.itemDurability = itemDurability;
             this.miningSpeed = miningSpeed;
             this.attackDamage = attackDamage;
             this.enchantability = enchantability;
         }

         public ModToolMaterials.MiningLevels miningLevel() {
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
}
