package net.paulem.simpleores.datagen.providers.langs;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import java.util.concurrent.CompletableFuture;

public class SpanishArgentinaLangProvider extends GlobalLangProvider {
    public SpanishArgentinaLangProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, "es_ar", registryLookup);
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        super.generateTranslations(translationBuilder);

        add(translationBuilder, "tin_ore", "Mineral de Estaño");
        add(translationBuilder, "tin_block", "Bloque de Estaño");
        add(translationBuilder, "mythril_ore", "Mineral de Mitrilo");
        add(translationBuilder, "mythril_block", "Bloque de Mitrilo");
        add(translationBuilder, "adamantium_ore", "Mineral de Adamantio");
        add(translationBuilder, "adamantium_block", "Bloque de Adamantio");
        add(translationBuilder, "onyx_ore", "Mineral de Onix");
        add(translationBuilder, "onyx_block", "Bloque de Onix");
    }
}
