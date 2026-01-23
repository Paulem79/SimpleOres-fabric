package net.paulem.simpleores.datagen.providers.langs;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import java.util.concurrent.CompletableFuture;

public class FrenchLangProvider extends GlobalLangProvider {
    public FrenchLangProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(packOutput, "fr_fr", registryLookup);
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        super.generateTranslations(translationBuilder);

        add(translationBuilder, "tin_ore", "Minerai d'Étain");
        add(translationBuilder, "deepslate_tin_ore", "Minerai d'Étain des âbimes");
        add(translationBuilder, "tin_block", "Bloc d'Étain");
        add(translationBuilder, "mythril_ore", "Minerai de Mythril");
        add(translationBuilder, "deepslate_mythril_ore", "Minerai de Mythril des âbimes");
        add(translationBuilder, "mythril_block", "Bloc de Mythril");
        add(translationBuilder, "adamantium_ore", "Minerai d'Adamantium");
        add(translationBuilder, "deepslate_adamantium_ore", "Minerai d'Adamantium des âbimes");
        add(translationBuilder, "adamantium_block", "Bloc d'Adamantium");
        add(translationBuilder, "onyx_ore", "Minerai d'Onyx");
        add(translationBuilder, "onyx_block", "Bloc d'Onyx");

        add(translationBuilder, "raw_tin_block", "Bloc d'Étain brut");
        add(translationBuilder, "raw_mythril_block", "Bloc de Mythril brut");
        add(translationBuilder, "raw_adamantium_block", "Bloc d'Adamantium brut");

        add(translationBuilder, "copper_pressure_plate", "Plaque de pression en Cuivre");
        add(translationBuilder, "tin_pressure_plate", "Plaque de pression en Étain");
        add(translationBuilder, "mythril_pressure_plate", "Plaque de pression en Mythril");
        add(translationBuilder, "adamantium_pressure_plate", "Plaque de pression en Adamantium");
        add(translationBuilder, "onyx_pressure_plate", "Plaque de pression en Onyx");

        add(translationBuilder, "copper_brick_slab", "Dalle en briques de Cuivre");
        add(translationBuilder, "tin_brick_slab", "Dalle en briques d'Étain");
        add(translationBuilder, "mythril_brick_slab", "Dalle en briques de Mythril");
        add(translationBuilder, "adamantium_brick_slab", "Dalle en briques d'Adamantium");
        add(translationBuilder, "onyx_brick_slab", "Dalle en briques de Onyx");

        add(translationBuilder, "copper_bricks", "Briques en Cuivre");
        add(translationBuilder, "tin_bricks", "Briques en Étain");
        add(translationBuilder, "mythril_bricks", "Briques en Mythril");
        add(translationBuilder, "adamantium_bricks", "Briques en Adamantium");
        add(translationBuilder, "onyx_bricks", "Briques en Onyx");

        add(translationBuilder, "copper_brick_stairs", "Escalier en briques de Cuivre");
        add(translationBuilder, "tin_brick_stairs", "Escalier en briques d'Étain");
        add(translationBuilder, "mythril_brick_stairs", "Escalier en briques de Mythril");
        add(translationBuilder, "adamantium_brick_stairs", "Escalier en briques d'Adamantium");
        add(translationBuilder, "onyx_brick_stairs", "Escalier en briques d'Onyx");

        add(translationBuilder, "copper_bars", "Barreaux de Cuivre");
        add(translationBuilder, "tin_bars", "Barreaux d'Étain");
        add(translationBuilder, "mythril_bars", "Barreaux de Mythril");
        add(translationBuilder, "adamantium_bars", "Barreaux d'Adamantium");
        add(translationBuilder, "onyx_bars", "Barreaux d'Onyx");

        add(translationBuilder, "adamantium_door", "Porte en Adamantium");
        add(translationBuilder, "copper_door", "Porte en Cuivre");
        add(translationBuilder, "mythril_door", "Porte en Mythril");
        add(translationBuilder, "onyx_door", "Porte en Onyx");
        add(translationBuilder, "tin_door", "Porte en Étain");
    }
}
