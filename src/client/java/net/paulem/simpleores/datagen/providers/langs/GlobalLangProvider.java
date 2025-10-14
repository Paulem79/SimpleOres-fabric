package net.paulem.simpleores.datagen.providers.langs;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import net.paulem.simpleores.SimpleOres;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public abstract class GlobalLangProvider extends FabricLanguageProvider {
    private static String translationPrefix;
    protected final String languageCode;

    static {
        translationPrefix = "block.simpleores.";
        //? if >=1.21.3
        translationPrefix = "item.simpleores.";
    }

    public GlobalLangProvider(FabricDataOutput dataOutput, String languageCode, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, languageCode //? if >1.20.4
                , registryLookup
        );

        this.languageCode = languageCode;
    }

    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        generateTranslations(translationBuilder);
    }

    public void generateTranslations(TranslationBuilder translationBuilder) {
        // Load an existing language file.
        try {
            SimpleOres.LOGGER.info("Adding existing language file for language: {}", languageCode);
            Path existingFilePath = dataOutput.getModContainer().findPath("assets/" + SimpleOres.MOD_ID + "/lang/" + languageCode + ".existing.json").get();
            translationBuilder.add(existingFilePath);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add existing language file!", e);
        }
    }

    public void add(TranslationBuilder translationBuilder, String key, String value) {
        translationBuilder.add(translationPrefix + key, value);
    }
}
