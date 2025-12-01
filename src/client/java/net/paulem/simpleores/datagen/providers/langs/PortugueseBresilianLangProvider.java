package net.paulem.simpleores.datagen.providers.langs;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import java.util.concurrent.CompletableFuture;

public class PortugueseBresilianLangProvider extends GlobalLangProvider {
    public PortugueseBresilianLangProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, "pt_br", registryLookup);
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        super.generateTranslations(translationBuilder);

        add(translationBuilder, "tin_ore", "Minério de estanho");
        add(translationBuilder, "tin_block", "Bloco de estanho");
        add(translationBuilder, "mythril_ore", "Minério de mithril");
        add(translationBuilder, "mythril_block", "Bloco de mithril");
        add(translationBuilder, "adamantium_ore", "Minério de adamântio");
        add(translationBuilder, "adamantium_block", "Bloco de adamântio");
        add(translationBuilder, "onyx_ore", "Minério de ônix");
        add(translationBuilder, "onyx_block", "Bloco de ônix");
    }
}
