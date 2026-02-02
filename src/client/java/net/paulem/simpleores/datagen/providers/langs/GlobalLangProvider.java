package net.paulem.simpleores.datagen.providers.langs;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.paulem.simpleores.SimpleOres;
import net.paulem.simpleores.furnaces.ModFurnaces;

import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.UnaryOperator;

public abstract class GlobalLangProvider extends FabricLanguageProvider {
    private static final String TRANSLATION_PREFIX;
    protected final String languageCode;

    static {
        //? if >=1.21.3 {
        TRANSLATION_PREFIX = "item.simpleores.";
        //?} else {
        //TRANSLATION_PREFIX = "block.simpleores.";
        //?}
    }

    public GlobalLangProvider(FabricPackOutput packOutput, String languageCode, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(packOutput, languageCode //? if >1.20.4
                , registryLookup
        );

        this.languageCode = languageCode;
    }

    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
        generateTranslations(translationBuilder);
    }

    public void generateTranslations(TranslationBuilder translationBuilder) {
        // Load an existing language file.
        try {
            SimpleOres.LOGGER.info("Adding existing language file for language: {}", languageCode);
            Path existingFilePath = packOutput.getModContainer().findPath("assets/" + SimpleOres.MOD_ID + "/lang/" + languageCode + ".existing.json").get();
            translationBuilder.add(existingFilePath);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add existing language file!", e);
        }
    }

    public void add(TranslationBuilder translationBuilder, String key, String value) {
        translationBuilder.add(TRANSLATION_PREFIX + key, value);
    }

    protected void generateFurnaceTranslations(TranslationBuilder translationBuilder, UnaryOperator<String> translateFunction) {
        Map<String, String> datagenFurnaces = ModFurnaces.getForDatagen(translateFunction, languageCode);
        datagenFurnaces.forEach((key, value) -> add(translationBuilder, key, value));
    }
}
