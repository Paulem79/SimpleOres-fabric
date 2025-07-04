package ovh.paulem.simpleores.datagen.providers.langs;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class DanishLangProvider extends GlobalLangProvider {
    public DanishLangProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "da_dk", registryLookup);
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        super.generateTranslations(translationBuilder);

        add(translationBuilder, "tin_ore", "Tinmalm");
        add(translationBuilder, "tin_block", "Tinblok");
        add(translationBuilder, "mythril_ore", "Mythrilmal");
        add(translationBuilder, "mythril_block", "Mythrilblock");
        add(translationBuilder, "adamantium_ore", "Adamantiummalm");
        add(translationBuilder, "adamantium_block", "Adamantiumblok");
        add(translationBuilder, "onyx_ore", "Onyxmalm");
        add(translationBuilder, "onyx_block", "Onyxblok");
    }
}
