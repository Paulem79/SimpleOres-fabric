package net.paulem.simpleores.datagen.providers.langs;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class EnglishLangProvider extends GlobalLangProvider {
    public EnglishLangProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(packOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        super.generateTranslations(translationBuilder);

        add(translationBuilder, "tin_ore", "Tin Ore");
        add(translationBuilder, "deepslate_tin_ore", "Deepslate Tin Ore");
        add(translationBuilder, "tin_block", "Block of Tin");
        add(translationBuilder, "raw_tin_block", "Block of Raw Tin");
        add(translationBuilder, "mythril_ore", "Mythril Ore");
        add(translationBuilder, "deepslate_mythril_ore", "Deepslate Mythril Ore");
        add(translationBuilder, "mythril_block", "Block of Mythril");
        add(translationBuilder, "raw_mythril_block", "Block of Raw Mythril");
        add(translationBuilder, "adamantium_ore", "Adamantium Ore");
        add(translationBuilder, "deepslate_adamantium_ore", "Deepslate Adamantium Ore");
        add(translationBuilder, "adamantium_block", "Block of Adamantium");
        add(translationBuilder, "raw_adamantium_block", "Block of Raw Adamantium");
        add(translationBuilder, "onyx_ore", "Onyx Ore");
        add(translationBuilder, "onyx_block", "Block of Onyx");

        add(translationBuilder, "copper_bricks", "Copper Bricks");
        add(translationBuilder, "tin_bricks", "Cut Tin");
        add(translationBuilder, "mythril_bricks", "Cut Mythril");
        add(translationBuilder, "adamantium_bricks", "Cut Adamantium");
        add(translationBuilder, "onyx_bricks", "Onyx Bricks");

        add(translationBuilder, "copper_brick_stairs", "Copper Brick Stairs");
        add(translationBuilder, "tin_brick_stairs", "Cut Tin Stairs");
        add(translationBuilder, "mythril_brick_stairs", "Cut Mythril Stairs");
        add(translationBuilder, "adamantium_brick_stairs", "Cut Adamantium Stairs");
        add(translationBuilder, "onyx_brick_stairs", "Onyx Brick Stairs");

        add(translationBuilder, "copper_brick_slab", "Copper Brick Slab");
        add(translationBuilder, "tin_brick_slab", "Cut Tin Slab");
        add(translationBuilder, "mythril_brick_slab", "Cut Mythril Slab");
        add(translationBuilder, "adamantium_brick_slab", "Cut Adamantium Slab");
        add(translationBuilder, "onyx_brick_slab", "Onyx Brick Slab");

        add(translationBuilder, "copper_bars", "Copper Bars");
        add(translationBuilder, "tin_bars", "Tin Bars");
        add(translationBuilder, "mythril_bars", "Mythril Bars");
        add(translationBuilder, "adamantium_bars", "Adamantium Bars");
        add(translationBuilder, "onyx_bars", "Onyx Bars");

        add(translationBuilder, "adamantium_door", "Adamantium Door");
        add(translationBuilder, "copper_door", "Copper Door");
        add(translationBuilder, "mythril_door", "Mythril Door");
        add(translationBuilder, "onyx_door", "Onyx Door");
        add(translationBuilder, "tin_door", "Tin Door");

        add(translationBuilder, "copper_pressure_plate", "Copper Pressure Plate");
        add(translationBuilder, "tin_pressure_plate", "Tin Pressure Plate");
        add(translationBuilder, "mythril_pressure_plate", "Mythril Pressure Plate");
        add(translationBuilder, "adamantium_pressure_plate", "Adamantium Pressure Plate");
        add(translationBuilder, "onyx_pressure_plate", "Onyx Pressure Plate");

        generateFurnaceTranslations(translationBuilder, materialName -> materialName + " Furnace");
        translationBuilder.add("tips.furnace.speed_modifier", "Speed Modifier: x%s");
    }
}
