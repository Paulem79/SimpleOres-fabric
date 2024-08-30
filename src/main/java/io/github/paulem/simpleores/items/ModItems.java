package io.github.paulem.simpleores.items;

import com.google.common.base.Suppliers;
import de.cech12.bucketlib.api.item.UniversalBucketItem;
import io.github.paulem.simpleores.SimpleOres;
import io.github.paulem.simpleores.armors.AdvancedArmorItem;
import io.github.paulem.simpleores.armors.ModArmorMaterials;
import io.github.paulem.simpleores.config.SimpleOresConfig;
import io.github.paulem.simpleores.items.custom.AdvancedShearsItem;
import io.github.paulem.simpleores.items.custom.MythrilBow;
import io.github.paulem.simpleores.items.custom.OnyxBow;
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
                            .upperCrackingTemperature(SimpleOres.CONFIG.copperBucketMeltTemperature)
                            .burningTemperature(SimpleOres.CONFIG.copperBucketFireTemperature)
                            .milking(Suppliers.ofInstance(SimpleOres.CONFIG.enableCopperBucketMilking))
            ));


    // TOOLS & WEAPONS
    // bows
    public static final MythrilBow MYTHRIL_BOW = registerItem("mythril_bow",
            new MythrilBow(new Item.Settings().maxDamage(SimpleOres.CONFIG.mythrilBowDurability)));
    public static final OnyxBow ONYX_BOW = registerItem("onyx_bow",
            new OnyxBow(new Item.Settings().maxDamage(SimpleOres.CONFIG.onyxBowDurability)));

    // swords: constant dmg 3, eff -2.4
    public static final SwordItem COPPER_SWORD = registerItem("copper_sword",
            new SwordItem(SimpleOresTiers.COPPER, 3, -2.4f, new Item.Settings()));

    public static final SwordItem TIN_SWORD = registerItem("tin_sword",
            new SwordItem(SimpleOresTiers.TIN, 3, -2.4F, new Item.Settings()));

    public static final SwordItem MYTHRIL_SWORD = registerItem("mythril_sword",
            new SwordItem(SimpleOresTiers.MYTHRIL, 3, -2.4F, new Item.Settings()));

    public static final SwordItem ADAMANTIUM_SWORD = registerItem("adamantium_sword",
            new SwordItem(SimpleOresTiers.ADAMANTIUM, 3, -2.4F,
                    new Item.Settings()));

    public static final SwordItem ONYX_SWORD = registerItem("onyx_sword",
            new SwordItem(SimpleOresTiers.ONYX, 3, -2.4F, new Item.Settings()));

    // pickaxes: constant dmg 1, eff: -2.8
    public static final PickaxeItem COPPER_PICKAXE = registerItem("copper_pickaxe",
            new PickaxeItem(SimpleOresTiers.COPPER, 1, -2.8F, new Item.Settings()));
    public static final PickaxeItem TIN_PICKAXE = registerItem("tin_pickaxe",
            new PickaxeItem(SimpleOresTiers.TIN, 1, -2.8F, new Item.Settings()));
    public static final PickaxeItem MYTHRIL_PICKAXE = registerItem("mythril_pickaxe",
            new PickaxeItem(SimpleOresTiers.MYTHRIL, 1, -2.8F, new Item.Settings()));
    public static final PickaxeItem ADAMANTIUM_PICKAXE = registerItem("adamantium_pickaxe",
            new PickaxeItem(SimpleOresTiers.ADAMANTIUM, 1, -2.8F, new Item.Settings()));
    public static final PickaxeItem ONYX_PICKAXE = registerItem("onyx_pickaxe",
            new PickaxeItem(SimpleOresTiers.ONYX, 1, -2.8F, new Item.Settings()));

    // axes: axe dmg + tier dmg == ~8.0  (9.0 for uber-materials); constant eff: -3.2 (3.1 to 3.0 for uberliness)
    public static final AxeItem COPPER_AXE = registerItem("copper_axe",
            new AxeItem(SimpleOresTiers.COPPER, 7.0F, -3.2F, new Item.Settings()));
    public static final AxeItem TIN_AXE = registerItem("tin_axe",
            new AxeItem(SimpleOresTiers.TIN, 7.0F, -3.2F, new Item.Settings()));
    public static final AxeItem MYTHRIL_AXE = registerItem("mythril_axe",
            new AxeItem(SimpleOresTiers.MYTHRIL, 5.0F, -3.2F, new Item.Settings()));
    public static final AxeItem ADAMANTIUM_AXE = registerItem("adamantium_axe",
            new AxeItem(SimpleOresTiers.ADAMANTIUM, 5.0F, -3.2F, new Item.Settings()));
    public static final AxeItem ONYX_AXE = registerItem("onyx_axe",
            new AxeItem(SimpleOresTiers.ONYX, 4.0F, -3.0F, new Item.Settings()));

    // shovels: constant dmg: 1.5, eff: -3.0
    public static final ShovelItem COPPER_SHOVEL = registerItem("copper_shovel",
            new ShovelItem(SimpleOresTiers.COPPER, 1.5F, -3.0F, new Item.Settings()));
    public static final ShovelItem TIN_SHOVEL = registerItem("tin_shovel",
            new ShovelItem(SimpleOresTiers.TIN, 1.5F, -3.0F, new Item.Settings()));
    public static final ShovelItem MYTHRIL_SHOVEL = registerItem("mythril_shovel",
            new ShovelItem(SimpleOresTiers.MYTHRIL, 1.5F, -3.0F, new Item.Settings()));
    public static final ShovelItem ADAMANTIUM_SHOVEL = registerItem("adamantium_shovel",
            new ShovelItem(SimpleOresTiers.ADAMANTIUM, 1.5F, -3.0F, new Item.Settings()));
    public static final ShovelItem ONYX_SHOVEL = registerItem("onyx_shovel",
            new ShovelItem(SimpleOresTiers.ONYX, 1.5F, -3.0F, new Item.Settings()));

    // hoes: hoe dmg + tier dmg == 0; Eff: -3 for bad hoe materials, 0 for uber materials, rest in-between.
    public static final HoeItem COPPER_HOE = registerItem("copper_hoe",
            new HoeItem(SimpleOresTiers.COPPER, -1, -2.0F, new Item.Settings()));
    public static final HoeItem TIN_HOE = registerItem("tin_hoe",
            new HoeItem(SimpleOresTiers.TIN, -1, -2.0F, new Item.Settings()));
    public static final HoeItem MYTHRIL_HOE = registerItem("mythril_hoe",
            new HoeItem(SimpleOresTiers.MYTHRIL, -3, -1.0F, new Item.Settings()));
    public static final HoeItem ADAMANTIUM_HOE = registerItem("adamantium_hoe",
            new HoeItem(SimpleOresTiers.ADAMANTIUM, -3, -1.0F, new Item.Settings()));
    public static final HoeItem ONYX_HOE = registerItem("onyx_hoe",
            new HoeItem(SimpleOresTiers.ONYX, -5, 0.0F, new Item.Settings()));

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
            new AdvancedArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.HELMET));
    public static final ArmorItem COPPER_CHESTPLATE = registerItem("copper_chestplate",
            new AdvancedArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.CHESTPLATE));
    public static final ArmorItem COPPER_LEGGINGS = registerItem("copper_leggings",
            new AdvancedArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.LEGGINGS));
    public static final ArmorItem COPPER_BOOTS = registerItem("copper_boots",
            new AdvancedArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.BOOTS));

    // tin
    public static final ArmorItem TIN_HELMET = registerItem("tin_helmet",
            new AdvancedArmorItem(ModArmorMaterials.TIN, ArmorItem.Type.HELMET));
    public static final ArmorItem TIN_CHESTPLATE = registerItem("tin_chestplate",
            new AdvancedArmorItem(ModArmorMaterials.TIN, ArmorItem.Type.CHESTPLATE));
    public static final ArmorItem TIN_LEGGINGS = registerItem("tin_leggings",
            new AdvancedArmorItem(ModArmorMaterials.TIN, ArmorItem.Type.LEGGINGS));
    public static final ArmorItem TIN_BOOTS = registerItem("tin_boots",
            new AdvancedArmorItem(ModArmorMaterials.TIN, ArmorItem.Type.BOOTS));

    // mythril
    public static final ArmorItem MYTHRIL_HELMET = registerItem("mythril_helmet",
            new AdvancedArmorItem(ModArmorMaterials.MYTHRIL, ArmorItem.Type.HELMET));
    public static final ArmorItem MYTHRIL_CHESTPLATE = registerItem("mythril_chestplate",
            new AdvancedArmorItem(ModArmorMaterials.MYTHRIL, ArmorItem.Type.CHESTPLATE));
    public static final ArmorItem MYTHRIL_LEGGINGS = registerItem("mythril_leggings",
            new AdvancedArmorItem(ModArmorMaterials.MYTHRIL, ArmorItem.Type.LEGGINGS));
    public static final ArmorItem MYTHRIL_BOOTS = registerItem("mythril_boots",
            new AdvancedArmorItem(ModArmorMaterials.MYTHRIL, ArmorItem.Type.BOOTS));

    // adamantium
    public static final ArmorItem ADAMANTIUM_HELMET = registerItem("adamantium_helmet",
            new AdvancedArmorItem(ModArmorMaterials.ADAMANTIUM, ArmorItem.Type.HELMET));
    public static final ArmorItem ADAMANTIUM_CHESTPLATE = registerItem("adamantium_chestplate",
            new AdvancedArmorItem(ModArmorMaterials.ADAMANTIUM, ArmorItem.Type.CHESTPLATE));
    public static final ArmorItem ADAMANTIUM_LEGGINGS = registerItem("adamantium_leggings",
            new AdvancedArmorItem(ModArmorMaterials.ADAMANTIUM, ArmorItem.Type.LEGGINGS));
    public static final ArmorItem ADAMANTIUM_BOOTS = registerItem("adamantium_boots",
            new AdvancedArmorItem(ModArmorMaterials.ADAMANTIUM, ArmorItem.Type.BOOTS));

    // onyx
    public static final ArmorItem ONYX_HELMET = registerItem("onyx_helmet",
            new AdvancedArmorItem(ModArmorMaterials.ONYX, ArmorItem.Type.HELMET));
    public static final ArmorItem ONYX_CHESTPLATE = registerItem("onyx_chestplate",
            new AdvancedArmorItem(ModArmorMaterials.ONYX, ArmorItem.Type.CHESTPLATE));
    public static final ArmorItem ONYX_LEGGINGS = registerItem("onyx_leggings",
            new AdvancedArmorItem(ModArmorMaterials.ONYX, ArmorItem.Type.LEGGINGS));
    public static final ArmorItem ONYX_BOOTS = registerItem("onyx_boots",
            new AdvancedArmorItem(ModArmorMaterials.ONYX, ArmorItem.Type.BOOTS));

    
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
