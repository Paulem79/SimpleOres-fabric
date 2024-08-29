package io.github.paulem.simpleores.armors;

import io.github.paulem.simpleores.SimpleOres;
import io.github.paulem.simpleores.items.ModItems;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public final class ModArmorMaterials
{
    public static final RegistryEntry<ArmorMaterial> COPPER;
    public static final RegistryEntry<ArmorMaterial> TIN;
    public static final RegistryEntry<ArmorMaterial> MYTHRIL;
    public static final RegistryEntry<ArmorMaterial> ADAMANTIUM;
    public static final RegistryEntry<ArmorMaterial> ONYX;

    static {// We place copper somewhere between leather and chainmail.
        COPPER =
                register("copper", Util.make(new EnumMap<>(ArmorItem.Type.class), (attribute) -> {
                            SimpleOres.CONFIG.copperArmorProtection.setProtectionAmount(attribute);
                        }), 8, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, SimpleOres.CONFIG.copperArmorProtection.thoughness(), SimpleOres.CONFIG.copperArmorProtection.knockbackProtection(), Ingredient.fromTag(ConventionalItemTags.COPPER_INGOTS)
                ); // end copper

        TIN = register("tin",
                Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                    SimpleOres.CONFIG.tinArmorProtection.setProtectionAmount(map);
                }),
                9, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN,
                SimpleOres.CONFIG.tinArmorProtection.thoughness(), SimpleOres.CONFIG.tinArmorProtection.knockbackProtection(),
                Ingredient.ofItems(ModItems.TIN_INGOT));

        MYTHRIL = register("mythril", Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                    SimpleOres.CONFIG.mythrilArmorProtection.setProtectionAmount(map);
                }),
                12, SoundEvents.ITEM_ARMOR_EQUIP_GOLD,
                SimpleOres.CONFIG.mythrilArmorProtection.thoughness(), SimpleOres.CONFIG.mythrilArmorProtection.knockbackProtection(),
                Ingredient.ofItems(ModItems.MYTHRIL_INGOT));

        ADAMANTIUM = register("adamantium",
                Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                    SimpleOres.CONFIG.adamantiumArmorProtection.setProtectionAmount(map);
                }),
                3, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
                SimpleOres.CONFIG.adamantiumArmorProtection.thoughness(), SimpleOres.CONFIG.adamantiumArmorProtection.knockbackProtection(),
                Ingredient.ofItems(ModItems.ADAMANTIUM_INGOT));

        ONYX = register("onyx",
                Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                    SimpleOres.CONFIG.onyxArmorProtection.setProtectionAmount(map);
                }),
                15, SoundEvents.ITEM_ARMOR_EQUIP_TURTLE,
                SimpleOres.CONFIG.onyxArmorProtection.thoughness(), SimpleOres.CONFIG.onyxArmorProtection.knockbackProtection(),
                Ingredient.ofItems(ModItems.ONYX_GEM));
    }

    /**
     * @param name                  Name ofItems the armor material
     * @param typeProtections       The amount ofItems protection per slot
     * @param enchantability        The higher the number, the more likely better enchantments will be applied when using the enchanting table
     * @param toughness             Toughness for netherite armor
     * @param knockbackResistance   The knockback resistance for armor
     * @param ingredientItem        Item used in anvil to repair the armor piece
     * @return Registered armor material
     */
    private static RegistryEntry<ArmorMaterial> register(String name, EnumMap<ArmorItem.Type, Integer> typeProtections, int enchantability, RegistryEntry<SoundEvent> equipSound, float toughness, float knockbackResistance, Ingredient ingredientItem)
    {
        Identifier loc = Identifier.of(SimpleOres.MOD_ID, name);
        Supplier<Ingredient> ingredient = () -> ingredientItem;
        List<ArmorMaterial.Layer> layers = List.of(new ArmorMaterial.Layer(loc));

        return Registry.registerReference(Registries.ARMOR_MATERIAL, loc, new ArmorMaterial(typeProtections, enchantability, equipSound, ingredient, layers, toughness, knockbackResistance));
    }

} // end class
