package net.paulem.simpleores.datagen.providers.langs;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ToolMaterial;
import net.paulem.simpleores.SimpleOres;
import net.paulem.simpleores.furnaces.ModFurnaces;
import net.paulem.simpleores.items.ModItems;
import net.paulem.simpleores.items.custom.advanced.AdvancedSpearItem;
import net.paulem.simpleores.utils.MaterialUtils;
import net.paulem.simpleores.utils.TranslateUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public abstract class GlobalLangProvider extends FabricLanguageProvider {
    private static final String TRANSLATION_PREFIX;
    protected final String languageCode;

    static {
        //? if >=1.21.3 {
        TRANSLATION_PREFIX = "item.simpleores.";
        //?} else {
        /*TRANSLATION_PREFIX = "block.simpleores.";
        *///?}
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
        Map<String, String> datagenFurnaces = ModFurnaces.generateFurnaceTranslations(translateFunction, languageCode);
        datagenFurnaces.forEach((key, value) -> add(translationBuilder, key, value));
    }

    //? if >=1.21.11 {
    protected void generateSpearsTranslations(TranslationBuilder translationBuilder, UnaryOperator<String> translateFunction) {
        Set<Pair<String, String>> datagenSpears = ModItems.registeredItems.entrySet()
                .stream()
                // Check if the item is a spear
                .filter(entry -> entry.getValue() instanceof AdvancedSpearItem)
                // Collect the path of identifier, and the material name of the spear
                .map(entry -> {
                    AdvancedSpearItem spearItem = (AdvancedSpearItem) entry.getValue();
                    ToolMaterial toolMaterial = spearItem.getMaterial();
                    String translatedName = TranslateUtils.getTranslatedName(MaterialUtils.toArmor(toolMaterial), languageCode);

                    return new Pair<>(
                            entry.getKey().getPath(),
                            translatedName
                    );
                })
                // Filter out null values
                .filter(pair -> pair.getSecond() != null)
                .collect(Collectors.toSet());
        
        datagenSpears.forEach(pair -> add(translationBuilder, pair.getFirst(), translateFunction.apply(pair.getSecond())));
    }
    //?}
}
