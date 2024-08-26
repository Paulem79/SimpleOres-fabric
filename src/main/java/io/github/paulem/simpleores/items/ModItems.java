package io.github.paulem.simpleores.items;

import com.google.common.base.Suppliers;
import de.cech12.bucketlib.api.item.UniversalBucketItem;
import io.github.paulem.simpleores.SimpleOres;
import io.github.paulem.simpleores.armors.ModArmorMaterials;
import mod.alexndr.simplecorelib.api.content.config.SimpleOresConfig;
import mod.alexndr.simplecorelib.api.content.content.MythrilBow;
import mod.alexndr.simplecorelib.api.content.content.OnyxBow;
import mod.alexndr.simplecorelib.api.content.content.SimpleOresTiers;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ShearsDispenserBehavior;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;

public class ModItems {
    public static final LinkedHashMap<Identifier, Item> registeredItems = new LinkedHashMap<>();

    // ingots and nuggets
    public static final Item TIN_INGOT = registerItem("tin_ingot", new Item(new Item.Settings()));
    public static final Item RAW_TIN = registerItem("raw_tin", new Item(new Item.Settings()));
    public static final Item MYTHRIL_INGOT = registerItem("mythril_ingot", new Item(new Item.Settings()));
    public static final Item RAW_MYTHRIL = registerItem("raw_mythril", new Item(new Item.Settings()));
    public static final Item ADAMANTIUM_INGOT = registerItem("adamantium_ingot", new Item(new Item.Settings()));
    public static final Item RAW_ADAMANTIUM = registerItem("raw_adamantium", new Item(new Item.Settings()));
    public static final Item ONYX_GEM = registerItem("onyx_gem", new Item(new Item.Settings()));

    public static final Item TIN_NUGGET = registerItem("tin_nugget", new Item(new Item.Settings()));
    public static final Item TIN_DUST = registerItem("tin_dust", new Item(new Item.Settings()));
    public static final Item CRUSHED_TIN_ORE = registerItem("crushed_tin_ore", new Item(new Item.Settings()));
    public static final Item COPPER_NUGGET = registerItem("copper_nugget", new Item(new Item.Settings()));
    public static final Item COPPER_DUST = registerItem("copper_dust", new Item(new Item.Settings()));
    public static final Item CRUSHED_COPPER_ORE = registerItem("crushed_copper_ore", new Item(new Item.Settings()));
    public static final Item MYTHRIL_NUGGET = registerItem("mythril_nugget", new Item(new Item.Settings()));
    public static final Item MYTHRIL_DUST = registerItem("mythril_dust", new Item(new Item.Settings()));
    public static final Item CRUSHED_MYTHRIL_ORE = registerItem("crushed_mythril_ore", new Item(new Item.Settings()));
    public static final Item ADAMANTIUM_NUGGET = registerItem("adamantium_nugget", new Item(new Item.Settings()));
    public static final Item ADAMANTIUM_DUST = registerItem("adamantium_dust", new Item(new Item.Settings()));
    public static final Item CRUSHED_ADAMANTIUM_ORE = registerItem("crushed_adamantium_ore", new Item(new Item.Settings()));

    // parts
    public static final Item MYTHRIL_ROD = registerItem("mythril_rod", new Item(new Item.Settings()));
    public static final Item ONYX_ROD = registerItem("onyx_rod", new Item(new Item.Settings()));

    // buckets
    public static final UniversalBucketItem COPPER_BUCKET = registerItem("copper_bucket",
            new UniversalBucketItem(
                    new UniversalBucketItem.Properties()
                            .upperCrackingTemperature(SimpleOresConfig.copperBucketMeltTemperature)
                            .burningTemperature(SimpleOresConfig.copperBucketFireTemperature)
                            .milking(Suppliers.ofInstance(SimpleOresConfig.enableCopperBucketMilking))
            ));


    // TOOLS & WEAPONS
    // bows
    public static final MythrilBow MYTHRIL_BOW = registerItem("mythril_bow",
            new MythrilBow(new Item.Settings().maxDamage(750)));
    public static final OnyxBow ONYX_BOW = registerItem("onyx_bow",
            new OnyxBow(new Item.Settings().maxDamage(1000)));

    // swords: constant dmg 3, eff -2.4
    public static final SwordItem COPPER_SWORD = registerItem("copper_sword",
            new SwordItem(SimpleOresTiers.COPPER,
                    new Item.Settings().attributeModifiers(
                            SwordItem.createAttributeModifiers(SimpleOresTiers.COPPER, 3, -2.4f)
                    )));

    public static final SwordItem TIN_SWORD = registerItem("tin_sword",
            new SwordItem(SimpleOresTiers.TIN,
                    new Item.Settings().attributeModifiers(
                            SwordItem.createAttributeModifiers(SimpleOresTiers.TIN,  3, -2.4F)
                    )));

    public static final SwordItem MYTHRIL_SWORD = registerItem("mythril_sword",
            new SwordItem(SimpleOresTiers.MYTHRIL,
                    new Item.Settings().attributeModifiers(
                            SwordItem.createAttributeModifiers(SimpleOresTiers.MYTHRIL, 3, -2.4F)
                    )));

    public static final SwordItem ADAMANTIUM_SWORD = registerItem("adamantium_sword",
            new SwordItem(SimpleOresTiers.ADAMANTIUM,
                    new Item.Settings().attributeModifiers(
                            SwordItem.createAttributeModifiers(SimpleOresTiers.ADAMANTIUM, 3, -2.4F)
                    )));

    public static final SwordItem ONYX_SWORD = registerItem("onyx_sword",
            new SwordItem(SimpleOresTiers.ONYX,
                    new Item.Settings().attributeModifiers(
                            SwordItem.createAttributeModifiers(SimpleOresTiers.ONYX, 3, -2.4F)
                    )));

    // pickaxes: constant dmg 1, eff: -2.8
    public static final PickaxeItem COPPER_PICKAXE = registerItem("copper_pickaxe",
            new PickaxeItem(SimpleOresTiers.COPPER,
                    new Item.Settings().attributeModifiers(
                            PickaxeItem.createAttributeModifiers(SimpleOresTiers.COPPER, 1, -2.8F)
                    )));
    public static final PickaxeItem TIN_PICKAXE = registerItem("tin_pickaxe",
            new PickaxeItem(SimpleOresTiers.TIN,
                    new Item.Settings().attributeModifiers(
                            PickaxeItem.createAttributeModifiers(SimpleOresTiers.TIN, 1, -2.8F)
                    )));
    public static final PickaxeItem MYTHRIL_PICKAXE = registerItem("mythril_pickaxe",
            new PickaxeItem(SimpleOresTiers.MYTHRIL,
                    new Item.Settings().attributeModifiers(
                            PickaxeItem.createAttributeModifiers(SimpleOresTiers.MYTHRIL, 1, -2.8F)
                    )));
    public static final PickaxeItem ADAMANTIUM_PICKAXE = registerItem("adamantium_pickaxe",
            new PickaxeItem(SimpleOresTiers.ADAMANTIUM,
                    new Item.Settings().attributeModifiers(
                            PickaxeItem.createAttributeModifiers(SimpleOresTiers.ADAMANTIUM, 1, -2.8F)
                    )));
    public static final PickaxeItem ONYX_PICKAXE = registerItem("onyx_pickaxe",
            new PickaxeItem(SimpleOresTiers.ONYX,
                    new Item.Settings().attributeModifiers(
                            PickaxeItem.createAttributeModifiers(SimpleOresTiers.ONYX, 1, -2.8F)
                    )));

    // axes: axe dmg + tier dmg == ~8.0  (9.0 for uber-materials); constant eff: -3.2 (3.1 to 3.0 for uberliness)
    public static final AxeItem COPPER_AXE = registerItem("copper_axe",
            new AxeItem(SimpleOresTiers.COPPER,
                    new Item.Settings().attributeModifiers(
                            AxeItem.createAttributeModifiers(SimpleOresTiers.COPPER,  7.0F, -3.2F)
                    )));
    public static final AxeItem TIN_AXE = registerItem("tin_axe",
            new AxeItem(SimpleOresTiers.TIN,
                    new Item.Settings().attributeModifiers(
                            AxeItem.createAttributeModifiers(SimpleOresTiers.TIN,  7.0F, -3.2F)
                    )));
    public static final AxeItem MYTHRIL_AXE = registerItem("mythril_axe",
            new AxeItem(SimpleOresTiers.MYTHRIL,
                    new Item.Settings().attributeModifiers(
                            AxeItem.createAttributeModifiers(SimpleOresTiers.MYTHRIL,  5.0F, -3.2F)
                    )));
    public static final AxeItem ADAMANTIUM_AXE = registerItem("adamantium_axe",
            new AxeItem(SimpleOresTiers.ADAMANTIUM,
                    new Item.Settings().attributeModifiers(
                            AxeItem.createAttributeModifiers(SimpleOresTiers.ADAMANTIUM,  5.0F, -3.2F)
                    )));
    public static final AxeItem ONYX_AXE = registerItem("onyx_axe",
            new AxeItem(SimpleOresTiers.ONYX,
                    new Item.Settings().attributeModifiers(
                            AxeItem.createAttributeModifiers(SimpleOresTiers.ONYX, 4.0F, -3.0F)
                    )));

    // shovels: constant dmg: 1.5, eff: -3.0
    public static final ShovelItem COPPER_SHOVEL = registerItem("copper_shovel",
            new ShovelItem(SimpleOresTiers.COPPER,
                    new Item.Settings().attributeModifiers(
                            ShovelItem.createAttributeModifiers(SimpleOresTiers.COPPER, 1.5F, -3.0F)
                    )));
    public static final ShovelItem TIN_SHOVEL = registerItem("tin_shovel",
            new ShovelItem(SimpleOresTiers.TIN,
                    new Item.Settings().attributeModifiers(
                            ShovelItem.createAttributeModifiers(SimpleOresTiers.TIN, 1.5F, -3.0F)
                    )));
    public static final ShovelItem MYTHRIL_SHOVEL = registerItem("mythril_shovel",
            new ShovelItem(SimpleOresTiers.MYTHRIL,
                    new Item.Settings().attributeModifiers(
                            ShovelItem.createAttributeModifiers(SimpleOresTiers.MYTHRIL, 1.5F, -3.0F)
                    )));
    public static final ShovelItem ADAMANTIUM_SHOVEL = registerItem("adamantium_shovel",
            new ShovelItem(SimpleOresTiers.ADAMANTIUM,
                    new Item.Settings().attributeModifiers(
                            ShovelItem.createAttributeModifiers(SimpleOresTiers.ADAMANTIUM, 1.5F, -3.0F)
                    )));
    public static final ShovelItem ONYX_SHOVEL = registerItem("onyx_shovel",
            new ShovelItem(SimpleOresTiers.ONYX,
                    new Item.Settings().attributeModifiers(
                            ShovelItem.createAttributeModifiers(SimpleOresTiers.ONYX, 1.5F, -3.0F)
                    )));

    // hoes: hoe dmg + tier dmg == 0; Eff: -3 for bad hoe materials, 0 for uber materials, rest in-between.
    public static final HoeItem COPPER_HOE = registerItem("copper_hoe",
            new HoeItem(SimpleOresTiers.COPPER,
                    new Item.Settings().attributeModifiers(
                            HoeItem.createAttributeModifiers(SimpleOresTiers.COPPER, -1, -2.0F)
                    )));
    public static final HoeItem TIN_HOE = registerItem("tin_hoe",
            new HoeItem(SimpleOresTiers.TIN,
                    new Item.Settings().attributeModifiers(
                            HoeItem.createAttributeModifiers(SimpleOresTiers.TIN, -1, -2.0F)
                    )));
    public static final HoeItem MYTHRIL_HOE = registerItem("mythril_hoe",
            new HoeItem(SimpleOresTiers.MYTHRIL,
                    new Item.Settings().attributeModifiers(
                            HoeItem.createAttributeModifiers(SimpleOresTiers.MYTHRIL, -3, -1.0F)
                    )));
    public static final HoeItem ADAMANTIUM_HOE = registerItem("adamantium_hoe",
            new HoeItem(SimpleOresTiers.ADAMANTIUM,
                    new Item.Settings().attributeModifiers(
                            HoeItem.createAttributeModifiers(SimpleOresTiers.ADAMANTIUM, -3, -1.0F)
                    )));
    public static final HoeItem ONYX_HOE = registerItem("onyx_hoe",
            new HoeItem(SimpleOresTiers.ONYX,
                    new Item.Settings().attributeModifiers(
                            HoeItem.createAttributeModifiers(SimpleOresTiers.ONYX, -5, 0.0F)
                    )));

    // shears
    public static final AdvancedShearsItem COPPER_SHEARS = registerItem(
            "copper_shears", new AdvancedShearsItem(SimpleOresTiers.COPPER)
    );
    public static final AdvancedShearsItem TIN_SHEARS = registerItem(
            "tin_shears", new AdvancedShearsItem(SimpleOresTiers.TIN)
    );
    public static final AdvancedShearsItem MYTHRIL_SHEARS = registerItem(
            "mythril_shears", new AdvancedShearsItem(SimpleOresTiers.MYTHRIL)
    );
    public static final AdvancedShearsItem ADAMANTIUM_SHEARS = registerItem(
            "adamantium_shears", new AdvancedShearsItem(SimpleOresTiers.ADAMANTIUM)
    );
    public static final AdvancedShearsItem ONYX_SHEARS = registerItem(
            "onyx_shears", new AdvancedShearsItem(SimpleOresTiers.ONYX)
    );

    // ARMOR
    // copper
    public static final ArmorItem COPPER_HELMET = registerItem("copper_helmet",
            new ArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.HELMET,
                    new Item.Settings().maxDamage(
                            ArmorItem.Type.HELMET.getMaxDamage(SimpleOresConfig.copperArmorDurability))));
    public static final ArmorItem COPPER_LEGGINGS = registerItem("copper_leggings",
            new ArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.LEGGINGS,
                    new Item.Settings().maxDamage(
                            ArmorItem.Type.LEGGINGS.getMaxDamage(SimpleOresConfig.copperArmorDurability))));
    public static final ArmorItem COPPER_CHESTPLATE = registerItem("copper_chestplate",
            new ArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings().maxDamage(
                            ArmorItem.Type.CHESTPLATE.getMaxDamage(SimpleOresConfig.copperArmorDurability))));
    public static final ArmorItem COPPER_BOOTS = registerItem("copper_boots",
            new ArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.BOOTS,
                    new Item.Settings().maxDamage(
                            ArmorItem.Type.BOOTS.getMaxDamage(SimpleOresConfig.copperArmorDurability))));

    // tin
    public static final ArmorItem TIN_HELMET = registerItem("tin_helmet",
            new ArmorItem(ModArmorMaterials.TIN, ArmorItem.Type.HELMET,
                    new Item.Settings().maxDamage(
                            ArmorItem.Type.HELMET.getMaxDamage(SimpleOresConfig.tinArmorDurability))));
    public static final ArmorItem TIN_LEGGINGS = registerItem("tin_leggings",
            new ArmorItem(ModArmorMaterials.TIN, ArmorItem.Type.LEGGINGS,
                    new Item.Settings().maxDamage(
                            ArmorItem.Type.LEGGINGS.getMaxDamage(SimpleOresConfig.tinArmorDurability))));
    public static final ArmorItem TIN_CHESTPLATE = registerItem("tin_chestplate",
            new ArmorItem(ModArmorMaterials.TIN, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings().maxDamage(
                            ArmorItem.Type.CHESTPLATE.getMaxDamage(SimpleOresConfig.tinArmorDurability))));
    public static final ArmorItem TIN_BOOTS = registerItem("tin_boots",
            new ArmorItem(ModArmorMaterials.TIN, ArmorItem.Type.BOOTS,
                    new Item.Settings().maxDamage(
                            ArmorItem.Type.BOOTS.getMaxDamage(SimpleOresConfig.tinArmorDurability))));

    // mythril
    public static final ArmorItem MYTHRIL_HELMET = registerItem("mythril_helmet",
            new ArmorItem(ModArmorMaterials.MYTHRIL, ArmorItem.Type.HELMET,
                    new Item.Settings().maxDamage(
                            ArmorItem.Type.HELMET.getMaxDamage(SimpleOresConfig.mythrilArmorDurability))));
    public static final ArmorItem MYTHRIL_LEGGINGS = registerItem("mythril_leggings",
            new ArmorItem(ModArmorMaterials.MYTHRIL, ArmorItem.Type.LEGGINGS,
                    new Item.Settings().maxDamage(
                            ArmorItem.Type.LEGGINGS.getMaxDamage(SimpleOresConfig.mythrilArmorDurability))));
    public static final ArmorItem MYTHRIL_CHESTPLATE = registerItem("mythril_chestplate",
            new ArmorItem(ModArmorMaterials.MYTHRIL, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings().maxDamage(
                            ArmorItem.Type.CHESTPLATE.getMaxDamage(SimpleOresConfig.mythrilArmorDurability))));
    public static final ArmorItem MYTHRIL_BOOTS = registerItem("mythril_boots",
            new ArmorItem(ModArmorMaterials.MYTHRIL, ArmorItem.Type.BOOTS,
                    new Item.Settings().maxDamage(
                            ArmorItem.Type.BOOTS.getMaxDamage(SimpleOresConfig.mythrilArmorDurability))));

    // adamantium
    public static final ArmorItem ADAMANTIUM_HELMET = registerItem("adamantium_helmet",
            new ArmorItem(ModArmorMaterials.ADAMANTIUM, ArmorItem.Type.HELMET,
                    new Item.Settings().maxDamage(
                            ArmorItem.Type.HELMET.getMaxDamage(SimpleOresConfig.adamantiumArmorDurability))));
    public static final ArmorItem ADAMANTIUM_LEGGINGS = registerItem("adamantium_leggings",
            new ArmorItem(ModArmorMaterials.ADAMANTIUM, ArmorItem.Type.LEGGINGS,
                    new Item.Settings().maxDamage(
                            ArmorItem.Type.LEGGINGS.getMaxDamage(SimpleOresConfig.adamantiumArmorDurability))));
    public static final ArmorItem ADAMANTIUM_CHESTPLATE = registerItem("adamantium_chestplate",
            new ArmorItem(ModArmorMaterials.ADAMANTIUM, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings().maxDamage(
                            ArmorItem.Type.CHESTPLATE.getMaxDamage(SimpleOresConfig.adamantiumArmorDurability))));
    public static final ArmorItem ADAMANTIUM_BOOTS = registerItem("adamantium_boots",
            new ArmorItem(ModArmorMaterials.ADAMANTIUM, ArmorItem.Type.BOOTS,
                    new Item.Settings().maxDamage(
                            ArmorItem.Type.BOOTS.getMaxDamage(SimpleOresConfig.adamantiumArmorDurability))));

    // onyx
    public static final ArmorItem ONYX_HELMET = registerItem("onyx_helmet",
            new ArmorItem(ModArmorMaterials.ONYX, ArmorItem.Type.HELMET,
                    new Item.Settings().maxDamage(
                            ArmorItem.Type.HELMET.getMaxDamage(SimpleOresConfig.onyxArmorDurability))));
    public static final ArmorItem ONYX_LEGGINGS = registerItem("onyx_leggings",
            new ArmorItem(ModArmorMaterials.ONYX, ArmorItem.Type.LEGGINGS,
                    new Item.Settings().maxDamage(
                            ArmorItem.Type.LEGGINGS.getMaxDamage(SimpleOresConfig.onyxArmorDurability))));
    public static final ArmorItem ONYX_CHESTPLATE = registerItem("onyx_chestplate",
            new ArmorItem(ModArmorMaterials.ONYX, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings().maxDamage(
                            ArmorItem.Type.CHESTPLATE.getMaxDamage(SimpleOresConfig.onyxArmorDurability))));
    public static final ArmorItem ONYX_BOOTS = registerItem("onyx_boots",
            new ArmorItem(ModArmorMaterials.ONYX, ArmorItem.Type.BOOTS,
                    new Item.Settings().maxDamage(
                            ArmorItem.Type.BOOTS.getMaxDamage(SimpleOresConfig.onyxArmorDurability))));

    
    public static<T extends Item> T registerItem(String name, T item) {
        Identifier identifier = Identifier.of(SimpleOres.MOD_ID, name);
        T registered = Registry.register(Registries.ITEM, identifier, item);
        registeredItems.put(identifier, registered);

        if(item instanceof AdvancedShearsItem)
            DispenserBlock.registerBehavior(item, new ShearsDispenserBehavior());

        return registered;
    }

    public static void init() {
        SimpleOres.LOGGER.info("Registering items...");
    }
}
