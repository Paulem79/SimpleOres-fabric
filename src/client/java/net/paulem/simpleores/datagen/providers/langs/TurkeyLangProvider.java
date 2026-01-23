package net.paulem.simpleores.datagen.providers.langs;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import java.util.concurrent.CompletableFuture;

public class TurkeyLangProvider extends GlobalLangProvider {
    public TurkeyLangProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(packOutput, "tr_tr", registryLookup);
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        super.generateTranslations(translationBuilder);

        add(translationBuilder, "tin_ore", "Kalay Cevheri");
        add(translationBuilder, "tin_block", "Kalay Bloğu");
        add(translationBuilder, "mythril_ore", "Mitril Cevheri");
        add(translationBuilder, "mythril_block", "Mitril Bloğu");
        add(translationBuilder, "adamantium_ore", "Adamantiyum Cevheri");
        add(translationBuilder, "adamantium_block", "Adamantiyum Bloğu");
        add(translationBuilder, "onyx_ore", "Oniks Cevheri");
        add(translationBuilder, "onyx_block", "Oniks Bloğu");
    }
}
