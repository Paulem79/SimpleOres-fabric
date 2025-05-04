package ovh.paulem.simpleores.items;

import com.google.common.base.Function;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.RegistryKey;
import ovh.paulem.simpleores.SimpleOres;
import ovh.paulem.simpleores.items.custom.advanced.*;
import ovh.paulem.simpleores.armors.ModArmorMaterials;
import ovh.paulem.simpleores.items.custom.MythrilBow;
import ovh.paulem.simpleores.items.custom.OnyxBow;
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
    public static final Item TIN_INGOT = registerItem("tin_ingot", key -> new Item(new Item.Settings().registryKey(key)));
    public static final Item RAW_TIN = registerItem("raw_tin", key -> new Item(new Item.Settings().registryKey(key)));
    public static final Item MYTHRIL_INGOT = registerItem("mythril_ingot", key -> new Item(new Item.Settings().registryKey(key)));
    public static final Item RAW_MYTHRIL = registerItem("raw_mythril", key -> new Item(new Item.Settings().registryKey(key)));
    public static final Item ADAMANTIUM_INGOT = registerItem("adamantium_ingot", key -> new Item(new Item.Settings().registryKey(key)));
    public static final Item RAW_ADAMANTIUM = registerItem("raw_adamantium", key -> new Item(new Item.Settings().registryKey(key)));
    public static final Item ONYX_GEM = registerItem("onyx_gem", key -> new Item(new Item.Settings().registryKey(key)));

    public static final Item TIN_NUGGET = registerItem("tin_nugget", key -> new Item(new Item.Settings().registryKey(key)));
    public static final Item TIN_DUST = registerItem("tin_dust", key -> new Item(new Item.Settings().registryKey(key)));
    public static final Item CRUSHED_TIN_ORE = registerItem("crushed_tin_ore", key -> new Item(new Item.Settings().registryKey(key)));
    public static final Item MYTHRIL_NUGGET = registerItem("mythril_nugget", key -> new Item(new Item.Settings().registryKey(key)));
    public static final Item MYTHRIL_DUST = registerItem("mythril_dust", key -> new Item(new Item.Settings().registryKey(key)));
    public static final Item CRUSHED_MYTHRIL_ORE = registerItem("crushed_mythril_ore", key -> new Item(new Item.Settings().registryKey(key)));
    public static final Item ADAMANTIUM_NUGGET = registerItem("adamantium_nugget", key -> new Item(new Item.Settings().registryKey(key)));
    public static final Item ADAMANTIUM_DUST = registerItem("adamantium_dust", key -> new Item(new Item.Settings().registryKey(key)));
    public static final Item CRUSHED_ADAMANTIUM_ORE = registerItem("crushed_adamantium_ore", key -> new Item(new Item.Settings().registryKey(key)));

    // parts
    public static final Item MYTHRIL_ROD = registerItem("mythril_rod", key -> new Item(new Item.Settings().registryKey(key)));
    public static final Item ONYX_ROD = registerItem("onyx_rod", key -> new Item(new Item.Settings().registryKey(key)));


    // TOOLS & WEAPONS
    // bows
    public static final MythrilBow MYTHRIL_BOW = registerItem("mythril_bow", key ->
            new MythrilBow(new Item.Settings().registryKey(key).maxDamage(SimpleOres.CONFIG.mythrilBowDurability)));
    public static final OnyxBow ONYX_BOW = registerItem("onyx_bow", key ->
            new OnyxBow(new Item.Settings().registryKey(key).maxDamage(SimpleOres.CONFIG.onyxBowDurability)));

    // swords: constant dmg 3, eff -2.4
    public static final AdvancedSwordItem COPPER_SWORD = registerItem("copper_sword", key ->
            new AdvancedSwordItem(ModToolMaterials.COPPER,
                    new Item.Settings().registryKey(key))
    );

    public static final AdvancedSwordItem TIN_SWORD = registerItem("tin_sword", key ->
            new AdvancedSwordItem(ModToolMaterials.TIN,
                    new Item.Settings().registryKey(key))
    );

    public static final AdvancedSwordItem MYTHRIL_SWORD = registerItem("mythril_sword", key ->
            new AdvancedSwordItem(ModToolMaterials.MYTHRIL,
                    new Item.Settings().registryKey(key))
    );

    public static final AdvancedSwordItem ADAMANTIUM_SWORD = registerItem("adamantium_sword", key ->
            new AdvancedSwordItem(ModToolMaterials.ADAMANTIUM,
                    new Item.Settings().registryKey(key))
    );

    public static final AdvancedSwordItem ONYX_SWORD = registerItem("onyx_sword", key ->
            new AdvancedSwordItem(ModToolMaterials.ONYX,
                    new Item.Settings().registryKey(key))
    );

    // pickaxes: constant dmg 1, eff: -2.8
    public static final AdvancedPickaxeItem COPPER_PICKAXE = registerItem("copper_pickaxe", key ->
            new AdvancedPickaxeItem(ModToolMaterials.COPPER,
                    new Item.Settings().registryKey(key))
    );
    public static final AdvancedPickaxeItem TIN_PICKAXE = registerItem("tin_pickaxe", key ->
            new AdvancedPickaxeItem(ModToolMaterials.TIN,
                    new Item.Settings().registryKey(key))
    );
    public static final AdvancedPickaxeItem MYTHRIL_PICKAXE = registerItem("mythril_pickaxe", key ->
            new AdvancedPickaxeItem(ModToolMaterials.MYTHRIL,
                    new Item.Settings().registryKey(key))
    );
    public static final AdvancedPickaxeItem ADAMANTIUM_PICKAXE = registerItem("adamantium_pickaxe", key ->
            new AdvancedPickaxeItem(ModToolMaterials.ADAMANTIUM,
                    new Item.Settings().registryKey(key))
    );
    public static final AdvancedPickaxeItem ONYX_PICKAXE = registerItem("onyx_pickaxe", key ->
            new AdvancedPickaxeItem(ModToolMaterials.ONYX,
                    new Item.Settings().registryKey(key))
    );

    // axes: axe dmg + tier dmg == ~8.0  (9.0 for uber-materials); constant eff: -3.2 (3.1 to 3.0 for uberliness)
    public static final AdvancedAxeItem COPPER_AXE = registerItem("copper_axe", key ->
            new AdvancedAxeItem(ModToolMaterials.COPPER, 7.0F, -3.2F,
                    new Item.Settings().registryKey(key))
    );
    public static final AxeItem TIN_AXE = registerItem("tin_axe", key ->
            new AxeItem(ModToolMaterials.TIN, 7.0F, -3.2F,
                    new Item.Settings().registryKey(key))
    );
    public static final AxeItem MYTHRIL_AXE = registerItem("mythril_axe", key ->
            new AxeItem(ModToolMaterials.MYTHRIL, 5.0F, -3.2F,
                    new Item.Settings().registryKey(key))
    );
    public static final AxeItem ADAMANTIUM_AXE = registerItem("adamantium_axe", key ->
            new AxeItem(ModToolMaterials.ADAMANTIUM, 5.0F, -3.2F,
                    new Item.Settings().registryKey(key))
    );
    public static final AxeItem ONYX_AXE = registerItem("onyx_axe", key ->
            new AxeItem(ModToolMaterials.ONYX, 4.0F, -3.0F,
                    new Item.Settings().registryKey(key))
    );

    // shovels: constant dmg: 1.5, eff: -3.0
    public static final AdvancedShovelItem COPPER_SHOVEL = registerItem("copper_shovel", key ->
            new AdvancedShovelItem(ModToolMaterials.COPPER,
                    new Item.Settings().registryKey(key))
    );
    public static final AdvancedShovelItem TIN_SHOVEL = registerItem("tin_shovel", key ->
            new AdvancedShovelItem(ModToolMaterials.TIN,
                    new Item.Settings().registryKey(key))
    );
    public static final AdvancedShovelItem MYTHRIL_SHOVEL = registerItem("mythril_shovel", key ->
            new AdvancedShovelItem(ModToolMaterials.MYTHRIL,
                    new Item.Settings().registryKey(key))
    );
    public static final AdvancedShovelItem ADAMANTIUM_SHOVEL = registerItem("adamantium_shovel", key ->
            new AdvancedShovelItem(ModToolMaterials.ADAMANTIUM,
                    new Item.Settings().registryKey(key))
    );
    public static final AdvancedShovelItem ONYX_SHOVEL = registerItem("onyx_shovel", key ->
            new AdvancedShovelItem(ModToolMaterials.ONYX,
                    new Item.Settings().registryKey(key))
    );

    // hoes: hoe dmg + tier dmg == 0; Eff: -3 for bad hoe materials, 0 for uber materials, rest in-between.
    public static final AdvancedHoeItem COPPER_HOE = registerItem("copper_hoe", key ->
            new AdvancedHoeItem(ModToolMaterials.COPPER, -1, -2.0F,
                    new Item.Settings().registryKey(key))
    );
    public static final AdvancedHoeItem TIN_HOE = registerItem("tin_hoe", key ->
            new AdvancedHoeItem(ModToolMaterials.TIN, -1, -2.0F,
                    new Item.Settings().registryKey(key))
    );
    public static final AdvancedHoeItem MYTHRIL_HOE = registerItem("mythril_hoe", key ->
            new AdvancedHoeItem(ModToolMaterials.MYTHRIL, -3, -1.0F,
                    new Item.Settings().registryKey(key))
    );
    public static final AdvancedHoeItem ADAMANTIUM_HOE = registerItem("adamantium_hoe", key ->
            new AdvancedHoeItem(ModToolMaterials.ADAMANTIUM, -3, -1.0F,
                    new Item.Settings().registryKey(key))
    );
    public static final AdvancedHoeItem ONYX_HOE = registerItem("onyx_hoe", key ->
            new AdvancedHoeItem(ModToolMaterials.ONYX, -5, 0.0F,
                    new Item.Settings().registryKey(key))
    );

    // shears
    public static final AdvancedShearsItem COPPER_SHEARS = registerItem(
            "copper_shears",
            key -> new AdvancedShearsItem(ModToolMaterials.COPPER, key)
    );
    public static final AdvancedShearsItem TIN_SHEARS = registerItem(
            "tin_shears",
            key ->  new AdvancedShearsItem(ModToolMaterials.TIN, key)
    );
    public static final AdvancedShearsItem MYTHRIL_SHEARS = registerItem(
            "mythril_shears",
            key ->  new AdvancedShearsItem(ModToolMaterials.MYTHRIL, key)
    );
    public static final AdvancedShearsItem ADAMANTIUM_SHEARS = registerItem(
            "adamantium_shears",
            key ->  new AdvancedShearsItem(ModToolMaterials.ADAMANTIUM, key)
    );
    public static final AdvancedShearsItem ONYX_SHEARS = registerItem(
            "onyx_shears",
            key ->  new AdvancedShearsItem(ModToolMaterials.ONYX, key)
    );

    // ARMOR
    // copper
    public static final ArmorItem COPPER_HELMET = registerItem("copper_helmet", key ->
            new AdvancedArmorItem(ModArmorMaterials.COPPER, EquipmentType.HELMET, key));
    public static final ArmorItem COPPER_CHESTPLATE = registerItem("copper_chestplate", key ->
            new AdvancedArmorItem(ModArmorMaterials.COPPER, EquipmentType.CHESTPLATE, key));
    public static final ArmorItem COPPER_LEGGINGS = registerItem("copper_leggings", key ->
            new AdvancedArmorItem(ModArmorMaterials.COPPER, EquipmentType.LEGGINGS, key));
    public static final ArmorItem COPPER_BOOTS = registerItem("copper_boots", key ->
            new AdvancedArmorItem(ModArmorMaterials.COPPER, EquipmentType.BOOTS, key));

    // tin
    public static final ArmorItem TIN_HELMET = registerItem("tin_helmet", key ->
            new AdvancedArmorItem(ModArmorMaterials.TIN, EquipmentType.HELMET, key));
    public static final ArmorItem TIN_CHESTPLATE = registerItem("tin_chestplate", key ->
            new AdvancedArmorItem(ModArmorMaterials.TIN, EquipmentType.CHESTPLATE, key));
    public static final ArmorItem TIN_LEGGINGS = registerItem("tin_leggings", key ->
            new AdvancedArmorItem(ModArmorMaterials.TIN, EquipmentType.LEGGINGS, key));
    public static final ArmorItem TIN_BOOTS = registerItem("tin_boots", key ->
            new AdvancedArmorItem(ModArmorMaterials.TIN, EquipmentType.BOOTS, key));

    // mythril
    public static final ArmorItem MYTHRIL_HELMET = registerItem("mythril_helmet", key ->
            new AdvancedArmorItem(ModArmorMaterials.MYTHRIL, EquipmentType.HELMET, key));
    public static final ArmorItem MYTHRIL_CHESTPLATE = registerItem("mythril_chestplate", key ->
            new AdvancedArmorItem(ModArmorMaterials.MYTHRIL, EquipmentType.CHESTPLATE, key));
    public static final ArmorItem MYTHRIL_LEGGINGS = registerItem("mythril_leggings", key ->
            new AdvancedArmorItem(ModArmorMaterials.MYTHRIL, EquipmentType.LEGGINGS, key));
    public static final ArmorItem MYTHRIL_BOOTS = registerItem("mythril_boots", key ->
            new AdvancedArmorItem(ModArmorMaterials.MYTHRIL, EquipmentType.BOOTS, key));

    // adamantium
    public static final ArmorItem ADAMANTIUM_HELMET = registerItem("adamantium_helmet", key ->
            new AdvancedArmorItem(ModArmorMaterials.ADAMANTIUM, EquipmentType.HELMET, key));
    public static final ArmorItem ADAMANTIUM_CHESTPLATE = registerItem("adamantium_chestplate", key ->
            new AdvancedArmorItem(ModArmorMaterials.ADAMANTIUM, EquipmentType.CHESTPLATE, key));
    public static final ArmorItem ADAMANTIUM_LEGGINGS = registerItem("adamantium_leggings", key ->
            new AdvancedArmorItem(ModArmorMaterials.ADAMANTIUM, EquipmentType.LEGGINGS, key));
    public static final ArmorItem ADAMANTIUM_BOOTS = registerItem("adamantium_boots", key ->
            new AdvancedArmorItem(ModArmorMaterials.ADAMANTIUM, EquipmentType.BOOTS, key));

    // onyx
    public static final ArmorItem ONYX_HELMET = registerItem("onyx_helmet", key ->
            new AdvancedArmorItem(ModArmorMaterials.ONYX, EquipmentType.HELMET, key));
    public static final ArmorItem ONYX_CHESTPLATE = registerItem("onyx_chestplate", key ->
            new AdvancedArmorItem(ModArmorMaterials.ONYX, EquipmentType.CHESTPLATE, key));
    public static final ArmorItem ONYX_LEGGINGS = registerItem("onyx_leggings", key ->
            new AdvancedArmorItem(ModArmorMaterials.ONYX, EquipmentType.LEGGINGS, key));
    public static final ArmorItem ONYX_BOOTS = registerItem("onyx_boots", key ->
            new AdvancedArmorItem(ModArmorMaterials.ONYX, EquipmentType.BOOTS, key));

    
    public static<T extends Item> T registerItem(String name, Function<RegistryKey<Item>, T> func) {
        Identifier identifier = Identifier.of(SimpleOres.MOD_ID, name);
        
        T item = func.apply(RegistryKey.of(Registries.ITEM.getKey(), Identifier.of(SimpleOres.MOD_ID, name)));
        
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
