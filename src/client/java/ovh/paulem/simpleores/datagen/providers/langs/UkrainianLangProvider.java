package ovh.paulem.simpleores.datagen.providers.langs;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class UkrainianLangProvider extends GlobalLangProvider {
    public UkrainianLangProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "uk_ua", registryLookup);
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        super.generateTranslations(translationBuilder);

        add(translationBuilder, "tin_ore", "Олов'яна руда");
        add(translationBuilder, "tin_block", "Блок олова");
        add(translationBuilder, "mythril_ore", "Міфрилова руда");
        add(translationBuilder, "mythril_block", "Блок міфрилу");
        add(translationBuilder, "adamantium_ore", "Адамантова руда");
        add(translationBuilder, "adamantium_block", "Блок адамантію");
        add(translationBuilder, "onyx_ore", "Оніксова руда");
        add(translationBuilder, "onyx_block", "Блок оніксу");

        add(translationBuilder, "copper_bricks", "Мідна цегла");
        add(translationBuilder, "tin_bricks", "Олов'яна цегла");
        add(translationBuilder, "mythril_bricks", "Міфрилова цегла");
        add(translationBuilder, "adamantium_bricks", "Адамантова цегла");
        add(translationBuilder, "onyx_bricks", "Оніксова цегла");

        add(translationBuilder, "copper_brick_stairs", "Сходи з мідної цегли");
        add(translationBuilder, "tin_brick_stairs", "Сходи з олов'яної цегли");
        add(translationBuilder, "mythril_brick_stairs", "Сходи з міфрилової цегли");
        add(translationBuilder, "adamantium_brick_stairs", "Сходи з адамантової цегли");
        add(translationBuilder, "onyx_brick_stairs", "Сходи з оніксової цегли");

        add(translationBuilder, "copper_bars", "Мідні грати");
        add(translationBuilder, "tin_bars", "Олов'яні грати");
        add(translationBuilder, "mythril_bars", "Міфрилові грати");
        add(translationBuilder, "adamantium_bars", "Адамантові грати");
        add(translationBuilder, "onyx_bars", "Оніксові грати");

        add(translationBuilder, "adamantium_door", "Адамантові двері");
        add(translationBuilder, "copper_door", "Мідні двері");
        add(translationBuilder, "mythril_door", "Міфрилові двері");
        add(translationBuilder, "onyx_door", "Оніксові двері");
        add(translationBuilder, "tin_door", "Олов'яні двері");
    }
}
