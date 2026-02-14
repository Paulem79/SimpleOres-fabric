package net.paulem.simpleores.utils;

import net.minecraft.world.item.equipment.ArmorMaterial;
import net.paulem.simpleores.armors.ModArmorMaterials;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class TranslateUtils {
    private static final Map<//$ armorRegistry
            net.minecraft.world.item.equipment.ArmorMaterial
            , Map<String, String>> MATERIAL_NAMES = Map.of(
            ModArmorMaterials.COPPER, Map.of(
                    "en_us", "Copper",
                    "fr_fr", "Cuivre"
            ),
            ModArmorMaterials.TIN, Map.of(
                    "en_us", "Tin",
                    "fr_fr", "Étain"
            ),
            ModArmorMaterials.MYTHRIL, Map.of(
                    "en_us", "Mythril",
                    "fr_fr", "Mythril"
            ),
            ModArmorMaterials.ADAMANTIUM, Map.of(
                    "en_us", "Adamantium",
                    "fr_fr", "Adamantium"
            ),
            ModArmorMaterials.ONYX, Map.of(
                    "en_us", "Onyx",
                    "fr_fr", "Onyx"
            )
    );

    public static String getTranslatedName(//$ armorRegistry
                                    net.minecraft.world.item.equipment.ArmorMaterial
            material, String locale) {
        @Nullable String translatedName = MATERIAL_NAMES.get(material).get(locale);

        if(translatedName == null) {
            return MATERIAL_NAMES.get(material).get("en_us");
        }

        return translatedName;
    }
}
