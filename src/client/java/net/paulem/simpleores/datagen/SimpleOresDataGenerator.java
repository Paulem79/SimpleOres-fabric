package net.paulem.simpleores.datagen;

import net.paulem.simpleores.datagen.providers.*;
import net.paulem.simpleores.datagen.providers.RecipeProvider;
import net.paulem.simpleores.datagen.providers.langs.*;
import net.paulem.simpleores.datagen.providers.tags.BlockTagProvider;
import net.paulem.simpleores.datagen.providers.tags.ItemTagProvider;
import net.paulem.simpleores.world.ModConfiguredFeatures;
import net.paulem.simpleores.world.ModPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

import java.util.Arrays;
import java.util.List;

public class SimpleOresDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModelProvider::new);
		pack.addProvider(RecipeProvider::new);
		pack.addProvider(BlockTagProvider::new);
		pack.addProvider(ItemTagProvider::new);
		pack.addProvider(WorldGenProvider::new);
		pack.addProvider(LootTableProvider::new);
		pack.addProvider(AdvancementsProvider::new);

		List<FabricDataGenerator.Pack.RegistryDependentFactory<GlobalLangProvider>> langs = Arrays.asList(ChineseSimplifiedLangProvider::new, ChineseTraditionalLangProvider::new,
				EnglishLangProvider::new, FrenchLangProvider::new, DanishLangProvider::new,
				PortugueseBresilianLangProvider::new, SpanishArgentinaLangProvider::new, SpanishSpainLangProvider::new,
				TurkeyLangProvider::new, UkrainianLangProvider::new);

		for (FabricDataGenerator.Pack.RegistryDependentFactory<GlobalLangProvider> lang : langs) {
			pack.addProvider(lang);
		}

		//? if >=1.21.3 {
		FabricDataGenerator.Pack secondaryPack = fabricDataGenerator.createPack();
		secondaryPack.addProvider(ModEquipmentAssetProvider::new);
		//?}
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
		registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
	}
}
