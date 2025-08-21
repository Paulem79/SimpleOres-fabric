package ovh.paulem.simpleores.items;

//? hasBucketlib
/*import de.cech12.bucketlib.api.item.UniversalBucketItem;
import com.google.common.base.Suppliers;*/
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.RegistryKey;
import ovh.paulem.simpleores.SimpleOres;
import ovh.paulem.simpleores.items.custom.bucket.CustomParentBucketItem;
import ovh.paulem.simpleores.items.custom.bucket.CustomChildrenBucketItem;
import ovh.paulem.simpleores.stonecutter.SCArmor;
import ovh.paulem.simpleores.items.custom.advanced.*;
import ovh.paulem.simpleores.items.custom.MythrilBow;
import ovh.paulem.simpleores.items.custom.OnyxBow;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ShearsDispenserBehavior;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import ovh.paulem.simpleores.stonecutter.SCIdentifier;
import ovh.paulem.simpleores.util.ConcurrentFifoMap;

public class ModItems {
    public static final ConcurrentFifoMap<Identifier, Item> registeredItems = new ConcurrentFifoMap<>();

    // ingots and nuggets
    public static final Item TIN_INGOT = register("tin_ingot", Item::new);
    public static final Item RAW_TIN = register("raw_tin", Item::new);
    public static final Item MYTHRIL_INGOT = register("mythril_ingot", Item::new);
    public static final Item RAW_MYTHRIL = register("raw_mythril", Item::new);
    public static final Item ADAMANTIUM_INGOT = register("adamantium_ingot", Item::new);
    public static final Item RAW_ADAMANTIUM = register("raw_adamantium", Item::new);
    public static final Item ONYX_GEM = register("onyx_gem", Item::new);

    public static final Item TIN_NUGGET = register("tin_nugget", Item::new);
    public static final Item TIN_DUST = register("tin_dust", Item::new);
    public static final Item CRUSHED_TIN_ORE = register("crushed_tin_ore", Item::new);
    public static final Item MYTHRIL_NUGGET = register("mythril_nugget", Item::new);
    public static final Item MYTHRIL_DUST = register("mythril_dust", Item::new);
    public static final Item CRUSHED_MYTHRIL_ORE = register("crushed_mythril_ore", Item::new);
    public static final Item ADAMANTIUM_NUGGET = register("adamantium_nugget", Item::new);
    public static final Item ADAMANTIUM_DUST = register("adamantium_dust", Item::new);
    public static final Item CRUSHED_ADAMANTIUM_ORE = register("crushed_adamantium_ore", Item::new);

    // parts
    public static final Item MYTHRIL_ROD = register("mythril_rod", Item::new);
    public static final Item ONYX_ROD = register("onyx_rod", Item::new);

    //? hasBucketlib {
    /*// buckets
    public static final UniversalBucketItem COPPER_BUCKET = registerByKey("copper_bucket",
            key -> new UniversalBucketItem(//? if >1.21
                    key,
                    new UniversalBucketItem.Properties()
                            .upperCrackingTemperature(SimpleOres.CONFIG.copperBucketMeltTemperature)
                            .burningTemperature(SimpleOres.CONFIG.copperBucketFireTemperature)
                            .milking(Suppliers.ofInstance(SimpleOres.CONFIG.enableCopperBucketMilking))
            ));
    *///?} else {
    public static final CustomParentBucketItem COPPER_BUCKET = registerByKey("copper_bucket", key ->
            new CustomParentBucketItem(key, "copper", Fluids.EMPTY, new Item.Settings().maxCount(16),
                    (bucketItem, name, fluid) ->
                            register(name, innerSettings -> new CustomChildrenBucketItem(fluid, innerSettings.recipeRemainder(bucketItem).maxCount(1), bucketItem))
            )
    );
    //?}


    // TOOLS & WEAPONS
    // bows
    public static final MythrilBow MYTHRIL_BOW = register("mythril_bow", settings ->
            new MythrilBow(settings.maxDamage(SimpleOres.CONFIG.mythrilBowDurability)));
    public static final OnyxBow ONYX_BOW = register("onyx_bow", settings ->
            new OnyxBow(settings.maxDamage(SimpleOres.CONFIG.onyxBowDurability)));

    // swords: constant dmg 3, eff -2.4
    //? if !hasCopperTools
    public static final AdvancedSwordItem COPPER_SWORD = register("copper_sword", settings -> new AdvancedSwordItem(ModToolMaterials.COPPER, settings));

    public static final AdvancedSwordItem TIN_SWORD = register("tin_sword", settings -> new AdvancedSwordItem(ModToolMaterials.TIN, settings));

    public static final AdvancedSwordItem MYTHRIL_SWORD = register("mythril_sword", settings -> new AdvancedSwordItem(ModToolMaterials.MYTHRIL, settings));

    public static final AdvancedSwordItem ADAMANTIUM_SWORD = register("adamantium_sword", settings -> new AdvancedSwordItem(ModToolMaterials.ADAMANTIUM, settings));

    public static final AdvancedSwordItem ONYX_SWORD = register("onyx_sword", settings -> new AdvancedSwordItem(ModToolMaterials.ONYX, settings));

    // pickaxes: constant dmg 1, eff: -2.8
    //? if !hasCopperTools
    public static final AdvancedPickaxeItem COPPER_PICKAXE = register("copper_pickaxe", settings -> new AdvancedPickaxeItem(ModToolMaterials.COPPER, settings));
    public static final AdvancedPickaxeItem TIN_PICKAXE = register("tin_pickaxe", settings -> new AdvancedPickaxeItem(ModToolMaterials.TIN, settings));
    public static final AdvancedPickaxeItem MYTHRIL_PICKAXE = register("mythril_pickaxe", settings -> new AdvancedPickaxeItem(ModToolMaterials.MYTHRIL, settings));
    public static final AdvancedPickaxeItem ADAMANTIUM_PICKAXE = register("adamantium_pickaxe", settings -> new AdvancedPickaxeItem(ModToolMaterials.ADAMANTIUM, settings));
    public static final AdvancedPickaxeItem ONYX_PICKAXE = register("onyx_pickaxe", settings -> new AdvancedPickaxeItem(ModToolMaterials.ONYX, settings));

    // axes: axe dmg + tier dmg == ~8.0  (9.0 for uber-materials); constant eff: -3.2 (3.1 to 3.0 for uberliness)
    //? if !hasCopperTools
    public static final AdvancedAxeItem COPPER_AXE = register("copper_axe", settings -> new AdvancedAxeItem(ModToolMaterials.COPPER, 7.0F, -3.2F, settings));
    public static final AdvancedAxeItem TIN_AXE = register("tin_axe", settings -> new AdvancedAxeItem(ModToolMaterials.TIN, 7.0F, -3.2F, settings));
    public static final AdvancedAxeItem MYTHRIL_AXE = register("mythril_axe", settings -> new AdvancedAxeItem(ModToolMaterials.MYTHRIL, 5.0F, -3.2F, settings));
    public static final AdvancedAxeItem ADAMANTIUM_AXE = register("adamantium_axe", settings -> new AdvancedAxeItem(ModToolMaterials.ADAMANTIUM, 5.0F, -3.2F, settings));
    public static final AdvancedAxeItem ONYX_AXE = register("onyx_axe", settings -> new AdvancedAxeItem(ModToolMaterials.ONYX, 4.0F, -3.0F, settings));

    // shovels: constant dmg: 1.5, eff: -3.0
    //? if !hasCopperTools
    public static final AdvancedShovelItem COPPER_SHOVEL = register("copper_shovel", settings -> new AdvancedShovelItem(ModToolMaterials.COPPER, settings));
    public static final AdvancedShovelItem TIN_SHOVEL = register("tin_shovel", settings -> new AdvancedShovelItem(ModToolMaterials.TIN, settings));
    public static final AdvancedShovelItem MYTHRIL_SHOVEL = register("mythril_shovel", settings -> new AdvancedShovelItem(ModToolMaterials.MYTHRIL, settings));
    public static final AdvancedShovelItem ADAMANTIUM_SHOVEL = register("adamantium_shovel", settings -> new AdvancedShovelItem(ModToolMaterials.ADAMANTIUM, settings));
    public static final AdvancedShovelItem ONYX_SHOVEL = register("onyx_shovel", settings -> new AdvancedShovelItem(ModToolMaterials.ONYX, settings));

    // hoes: hoe dmg + tier dmg == 0; Eff: -3 for bad hoe materials, 0 for uber materials, rest in-between.
    //? if !hasCopperTools
    public static final AdvancedHoeItem COPPER_HOE = register("copper_hoe", settings -> new AdvancedHoeItem(ModToolMaterials.COPPER, -1, -2.0F, settings));
    public static final AdvancedHoeItem TIN_HOE = register("tin_hoe", settings -> new AdvancedHoeItem(ModToolMaterials.TIN, -1, -2.0F, settings));
    public static final AdvancedHoeItem MYTHRIL_HOE = register("mythril_hoe", settings -> new AdvancedHoeItem(ModToolMaterials.MYTHRIL, -3, -1.0F, settings));
    public static final AdvancedHoeItem ADAMANTIUM_HOE = register("adamantium_hoe", settings -> new AdvancedHoeItem(ModToolMaterials.ADAMANTIUM, -3, -1.0F, settings));
    public static final AdvancedHoeItem ONYX_HOE = register("onyx_hoe", settings -> new AdvancedHoeItem(ModToolMaterials.ONYX, -5, 0.0F, settings));


    // shears
    public static final AdvancedShearsItem COPPER_SHEARS = register(
            "copper_shears", settings -> 
            new AdvancedShearsItem(ModToolMaterials.COPPER, settings)
    );
    public static final AdvancedShearsItem TIN_SHEARS = register(
            "tin_shears", settings ->
                    new AdvancedShearsItem(ModToolMaterials.TIN, settings)
    );
    public static final AdvancedShearsItem MYTHRIL_SHEARS = register(
            "mythril_shears", settings ->
                    new AdvancedShearsItem(ModToolMaterials.MYTHRIL, settings)
    );
    public static final AdvancedShearsItem ADAMANTIUM_SHEARS = register(
            "adamantium_shears", settings ->
                    new AdvancedShearsItem(ModToolMaterials.ADAMANTIUM, settings)
    );
    public static final AdvancedShearsItem ONYX_SHEARS = register(
            "onyx_shears", settings ->
                    new AdvancedShearsItem(ModToolMaterials.ONYX, settings)
    );

    // ARMOR
    // copper
    //? if !hasCopperTools {
    public static final AdvancedArmorItem COPPER_HELMET = register("copper_helmet", settings -> SCArmor.get(SCArmor.SOArmorMaterial.COPPER, SCArmor.ArmorEquipmentType.HELMET, settings));
    public static final AdvancedArmorItem COPPER_CHESTPLATE = register("copper_chestplate", settings -> SCArmor.get(SCArmor.SOArmorMaterial.COPPER, SCArmor.ArmorEquipmentType.CHESTPLATE, settings));
    public static final AdvancedArmorItem COPPER_LEGGINGS = register("copper_leggings", settings -> SCArmor.get(SCArmor.SOArmorMaterial.COPPER, SCArmor.ArmorEquipmentType.LEGGINGS, settings));
    public static final AdvancedArmorItem COPPER_BOOTS = register("copper_boots", settings -> SCArmor.get(SCArmor.SOArmorMaterial.COPPER, SCArmor.ArmorEquipmentType.BOOTS, settings));
    //?}

    // tin
    public static final AdvancedArmorItem TIN_HELMET = register("tin_helmet", settings -> SCArmor.get(SCArmor.SOArmorMaterial.TIN, SCArmor.ArmorEquipmentType.HELMET, settings));
    public static final AdvancedArmorItem TIN_CHESTPLATE = register("tin_chestplate", settings -> SCArmor.get(SCArmor.SOArmorMaterial.TIN, SCArmor.ArmorEquipmentType.CHESTPLATE, settings));
    public static final AdvancedArmorItem TIN_LEGGINGS = register("tin_leggings", settings -> SCArmor.get(SCArmor.SOArmorMaterial.TIN, SCArmor.ArmorEquipmentType.LEGGINGS, settings));
    public static final AdvancedArmorItem TIN_BOOTS = register("tin_boots", settings -> SCArmor.get(SCArmor.SOArmorMaterial.TIN, SCArmor.ArmorEquipmentType.BOOTS, settings));

    // mythril
    public static final AdvancedArmorItem MYTHRIL_HELMET = register("mythril_helmet", settings -> SCArmor.get(SCArmor.SOArmorMaterial.MYTHRIL, SCArmor.ArmorEquipmentType.HELMET, settings));
    public static final AdvancedArmorItem MYTHRIL_CHESTPLATE = register("mythril_chestplate", settings -> SCArmor.get(SCArmor.SOArmorMaterial.MYTHRIL, SCArmor.ArmorEquipmentType.CHESTPLATE, settings));
    public static final AdvancedArmorItem MYTHRIL_LEGGINGS = register("mythril_leggings", settings -> SCArmor.get(SCArmor.SOArmorMaterial.MYTHRIL, SCArmor.ArmorEquipmentType.LEGGINGS, settings));
    public static final AdvancedArmorItem MYTHRIL_BOOTS = register("mythril_boots", settings -> SCArmor.get(SCArmor.SOArmorMaterial.MYTHRIL, SCArmor.ArmorEquipmentType.BOOTS, settings));

    // adamantium
    public static final AdvancedArmorItem ADAMANTIUM_HELMET = register("adamantium_helmet", settings -> SCArmor.get(SCArmor.SOArmorMaterial.ADAMANTIUM, SCArmor.ArmorEquipmentType.HELMET, settings));
    public static final AdvancedArmorItem ADAMANTIUM_CHESTPLATE = register("adamantium_chestplate", settings -> SCArmor.get(SCArmor.SOArmorMaterial.ADAMANTIUM, SCArmor.ArmorEquipmentType.CHESTPLATE, settings));
    public static final AdvancedArmorItem ADAMANTIUM_LEGGINGS = register("adamantium_leggings", settings -> SCArmor.get(SCArmor.SOArmorMaterial.ADAMANTIUM, SCArmor.ArmorEquipmentType.LEGGINGS, settings));
    public static final AdvancedArmorItem ADAMANTIUM_BOOTS = register("adamantium_boots", settings -> SCArmor.get(SCArmor.SOArmorMaterial.ADAMANTIUM, SCArmor.ArmorEquipmentType.BOOTS, settings));

    // onyx
    public static final AdvancedArmorItem ONYX_HELMET = register("onyx_helmet", settings -> SCArmor.get(SCArmor.SOArmorMaterial.ONYX, SCArmor.ArmorEquipmentType.HELMET, settings));
    public static final AdvancedArmorItem ONYX_CHESTPLATE = register("onyx_chestplate", settings -> SCArmor.get(SCArmor.SOArmorMaterial.ONYX, SCArmor.ArmorEquipmentType.CHESTPLATE, settings));
    public static final AdvancedArmorItem ONYX_LEGGINGS = register("onyx_leggings", settings -> SCArmor.get(SCArmor.SOArmorMaterial.ONYX, SCArmor.ArmorEquipmentType.LEGGINGS, settings));
    public static final AdvancedArmorItem ONYX_BOOTS = register("onyx_boots", settings -> SCArmor.get(SCArmor.SOArmorMaterial.ONYX, SCArmor.ArmorEquipmentType.BOOTS, settings));


    public static Item register(String id, Item.Settings settings) {
        return register(keyOf(id), Item::new, settings);
    }

    public static<T extends Item> T register(String id, java.util.function.Function<Item.Settings, T> factory) {
        return register(keyOf(id), factory, new Item.Settings());
    }

    public static<T extends Item> T register(String id, java.util.function.Function<Item.Settings, T> factory, Item.Settings settings) {
        return register(keyOf(id), factory, settings);
    }

    public static<T extends Item> T register(RegistryKey<Item> key, java.util.function.Function<Item.Settings, T> factory, Item.Settings settings) {
        T item = factory.apply(settings
                //? if >1.21
                .registryKey(key)
        );

        registeredItems.put(key.getValue(), item);

        if (item instanceof BlockItem blockItem) {
            blockItem.appendBlocks(Item.BLOCK_ITEMS, item);
        } else if(item instanceof AdvancedShearsItem) {
            DispenserBlock.registerBehavior(item, new ShearsDispenserBehavior());
        }

        return Registry.register(Registries.ITEM, key, item);
    }

    public static<T extends Item> T registerByKey(String id, java.util.function.Function<RegistryKey<Item>, T> factory) {
        RegistryKey<Item> key = keyOf(id);
        T item = factory.apply(key);

        registeredItems.put(key.getValue(), item);

        if (item instanceof BlockItem blockItem) {
            blockItem.appendBlocks(Item.BLOCK_ITEMS, item);
        } else if(item instanceof AdvancedShearsItem) {
            DispenserBlock.registerBehavior(item, new ShearsDispenserBehavior());
        }

        return Registry.register(Registries.ITEM, key, item);
    }

    private static RegistryKey<Item> keyOf(String id) {
        return RegistryKey.of(Registries.ITEM.getKey(), SCIdentifier.of(SimpleOres.MOD_ID, id));
    }

    public static void init() {
        SimpleOres.LOGGER.info("Registering items...");
    }
}
