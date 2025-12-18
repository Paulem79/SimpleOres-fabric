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

    // ARMORER defaults
    public final boolean armorerEmeraldCopperHelmet = true;
    public final boolean armorerEmeraldCopperChestplate = true;
    public final boolean armorerEmeraldCopperLeggings = true;
    public final boolean armorerEmeraldCopperBoots = true;
    public final boolean armorerCopperToEmeralds = true;
    public final boolean armorerTinToEmeralds = true;
    public final boolean armorerEmeraldTinLeggings = true;
    public final boolean armorerEmeraldTinBoots = true;
    public final boolean armorerMythrilToEmeralds = true;
    public final boolean armorerEmeraldTinHelmet = true;
    public final boolean armorerEmeraldTinChestplate = true;
    public final boolean armorerEmeraldMythrilLeggingsEnchanted = true;
    public final boolean armorerEmeraldMythrilBootsEnchanted = true;
    public final boolean armorerEmeraldMythrilHelmetEnchanted = true;
    public final boolean armorerEmeraldMythrilChestplateEnchanted = true;

    // TOOLSMITH defaults
    public final boolean toolsmithCopperToEmeralds = true;
    public final boolean toolsmithTinToEmeralds = true;
    public final boolean toolsmithEmeraldCopperAxe = true;
    public final boolean toolsmithEmeraldCopperShovel = true;
    public final boolean toolsmithEmeraldCopperHoe = true;
    public final boolean toolsmithEmeraldCopperPickaxe = true;
    public final boolean toolsmithEmeraldTinAxe = true;
    public final boolean toolsmithEmeraldTinShovel = true;
    public final boolean toolsmithEmeraldTinHoe = true;
    public final boolean toolsmithEmeraldTinPickaxe = true;
    public final boolean toolsmithMythrilToEmerald = true;
    public final boolean toolsmithEmeraldMythrilAxeEnchantedLvl3 = true;
    public final boolean toolsmithEmeraldMythrilShovelEnchantedLvl3 = true;
    public final boolean toolsmithEmeraldMythrilPickaxeEnchantedLvl3 = true;
    public final boolean toolsmithEmeraldMythrilHoe = true;
    public final boolean toolsmithAdamantiumToEmerald = true;
    public final boolean toolsmithEmeraldAdamantiumAxeEnchanted = true;
    public final boolean toolsmithEmeraldMythrilShovelEnchanted = true;
    public final boolean toolsmithEmeraldMythrilPickaxeEnchanted = true;

    // WEAPONSMITH defaults
    public final boolean weaponsmithEmeraldMythrilAxe = true;
    public final boolean weaponsmithEmeraldMythrilSwordEnchanted = true;
    public final boolean weaponsmithCopperToEmerald = true;
    public final boolean weaponsmithTinToEmerald = true;
    public final boolean weaponsmithMythrilToEmerald = true;
    public final boolean weaponsmithAdamantiumToEmerald = true;
    public final boolean weaponsmithEmeraldAdamantiumAxeEnchanted = true;
    public final boolean weaponsmithEmeraldAdamantiumSwordEnchanted = true;

    // copper bucket defaults
    public final boolean enableCopperBucketMilking = true;
    public final int copperBucketMeltTemperature = 1000;
    public final int copperBucketFireTemperature = 9999;

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
