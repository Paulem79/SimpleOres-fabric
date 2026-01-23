package net.paulem.simpleores.datagen.providers.langs;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import java.util.concurrent.CompletableFuture;

public class ChineseTraditionalLangProvider extends GlobalLangProvider {
    public ChineseTraditionalLangProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(packOutput, "zh_tw", registryLookup);
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        super.generateTranslations(translationBuilder);

        add(translationBuilder, "tin_ore", "錫礦石");
        add(translationBuilder, "tin_block", "錫磚");
        add(translationBuilder, "mythril_ore", "秘銀礦石");
        add(translationBuilder, "mythril_block", "秘銀磚");
        add(translationBuilder, "adamantium_ore", "精金礦石");
        add(translationBuilder, "adamantium_block", "精金磚");
        add(translationBuilder, "onyx_ore", "縞瑪瑙礦石");
        add(translationBuilder, "onyx_block", "縞瑪瑙磚");
        add(translationBuilder, "mythril_furnace", "秘銀熔爐");
        add(translationBuilder, "mythril_furnace_lit", "秘銀熔爐");
        add(translationBuilder, "onyx_furnace", "縞瑪瑙熔爐");
        add(translationBuilder, "onyx_furnace_lit", "縞瑪瑙熔爐");
        add(translationBuilder, "copper_door_block", "銅門");
        add(translationBuilder, "onyx_door_block", "縞瑪瑙門");
        add(translationBuilder, "copper_bars", "銅栏杆");
        add(translationBuilder, "tin_bars", "錫栏杆");
        add(translationBuilder, "mythril_bars", "秘銀栏杆");
        add(translationBuilder, "adamantium_bars", "精金栏杆");
        add(translationBuilder, "onyx_bars", "縞瑪瑙栏杆");
    }
}
