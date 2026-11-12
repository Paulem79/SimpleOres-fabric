package net.paulem.simpleores.armors;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
//? if hasCopperTools
import net.minecraft.world.item.equipment.ArmorMaterials;
import net.paulem.sclib.item.SCArmorMaterial;
import net.paulem.sclib.item.SCEquipSound;
import net.paulem.simpleores.SimpleOres;
import net.paulem.simpleores.config.BaseSimpleOresConfig;
import net.paulem.simpleores.tags.ModTags;

public final class ModArmorMaterials {
    //? if !hasCopperTools
    //public static final SCArmorMaterial COPPER_SC = armor("copper", SimpleOres.CONFIG.copperArmorDurability(), SimpleOres.CONFIG.copperArmorProtection(), SCEquipSound.CHAIN, ModTags.Items.Conventional.COPPER_INGOTS);
    public static final SCArmorMaterial TIN_SC = armor("tin", SimpleOres.CONFIG.tinArmorDurability(), SimpleOres.CONFIG.tinArmorProtection(), SCEquipSound.CHAIN, ModTags.Items.REPAIRS_TIN_ITEMS);
    public static final SCArmorMaterial MYTHRIL_SC = armor("mythril", SimpleOres.CONFIG.mythrilArmorDurability(), SimpleOres.CONFIG.mythrilArmorProtection(), SCEquipSound.GOLD, ModTags.Items.REPAIRS_MYTHRIL_ITEMS);
    public static final SCArmorMaterial ADAMANTIUM_SC = armor("adamantium", SimpleOres.CONFIG.adamantiumArmorDurability(), SimpleOres.CONFIG.adamantiumArmorProtection(), SCEquipSound.IRON, ModTags.Items.REPAIRS_ADAMANTIUM_ITEMS);
    public static final SCArmorMaterial ONYX_SC = armor("onyx", SimpleOres.CONFIG.onyxArmorDurability(), SimpleOres.CONFIG.onyxArmorProtection(), SCEquipSound.TURTLE, ModTags.Items.REPAIRS_ONYX_ITEMS);

    // The vanilla objects, in the type of the version
    public static final //$ armorRegistry
    net.minecraft.world.item.equipment.@org.jspecify.annotations.Nullable ArmorMaterial
            COPPER = //? if hasCopperTools {
            ArmorMaterials.COPPER;
    //?} else {
    /*COPPER_SC.vanilla();
    *///?}
    public static final //$ armorRegistry
    net.minecraft.world.item.equipment.@org.jspecify.annotations.Nullable ArmorMaterial
            TIN = TIN_SC.vanilla();
    public static final //$ armorRegistry
    net.minecraft.world.item.equipment.@org.jspecify.annotations.Nullable ArmorMaterial
            MYTHRIL = MYTHRIL_SC.vanilla();
    public static final //$ armorRegistry
    net.minecraft.world.item.equipment.@org.jspecify.annotations.Nullable ArmorMaterial
            ADAMANTIUM = ADAMANTIUM_SC.vanilla();
    public static final //$ armorRegistry
    net.minecraft.world.item.equipment.@org.jspecify.annotations.Nullable ArmorMaterial
            ONYX = ONYX_SC.vanilla();

    private static SCArmorMaterial armor(String name, int durability, BaseSimpleOresConfig.ArmorProtection p, SCEquipSound sound, TagKey<Item> repair) {
        return SCArmorMaterial.builder(SimpleOres.MOD_ID, name)
                .durabilityMultiplier(durability)
                .protection(p.helmet(), p.chestplate(), p.leggings(), p.boots())
                .enchantability(p.enchantability())
                .equipSound(sound)
                .toughness(p.thoughness())
                .knockbackResistance(p.knockbackProtection() / 10.0F)
                .repairItems(repair)
                .build();
    }
}
