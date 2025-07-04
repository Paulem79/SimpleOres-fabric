package ovh.paulem.simpleores.datagen.providers.langs;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class SpanishSpainLangProvider extends GlobalLangProvider {
    public SpanishSpainLangProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "es_es", registryLookup);
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
