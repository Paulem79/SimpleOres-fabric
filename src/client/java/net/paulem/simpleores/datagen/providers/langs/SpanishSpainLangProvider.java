package net.paulem.simpleores.datagen.providers.langs;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import java.util.concurrent.CompletableFuture;

public class SpanishSpainLangProvider extends GlobalLangProvider {
    public SpanishSpainLangProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(packOutput, "es_es", registryLookup);
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        super.generateTranslations(translationBuilder);

        add(translationBuilder, "tin_ore", "Mena de estaño");
        add(translationBuilder, "tin_block", "Bloque de estaño");
        add(translationBuilder, "mythril_ore", "Mena de mitrilo");
        add(translationBuilder, "mythril_block", "Bloque de mitrilo");
        add(translationBuilder, "adamantium_ore", "Mena de adamantio");
        add(translationBuilder, "adamantium_block", "Bloque de adamantio");
        add(translationBuilder, "onyx_ore", "Mena de onyx");
        add(translationBuilder, "onyx_block", "Bloque de Onyx");
    }
}
