package net.paulem.simpleores.datagen;

import net.paulem.simpleores.datagen.providers.*;
import net.paulem.simpleores.datagen.providers.langs.*;
import net.paulem.simpleores.datagen.providers.tags.BlockTagProvider;
import net.paulem.simpleores.datagen.providers.tags.ModItemTagProvider;
import net.paulem.simpleores.world.ModConfiguredFeatures;
import net.paulem.simpleores.world.ModPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import java.util.Arrays;
import java.util.List;

public class SimpleOresDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModelProvider::new);
		pack.addProvider(RecipeProvider::new);
		pack.addProvider(BlockTagProvider::new);
		pack.addProvider(ModItemTagProvider::new);
		pack.addProvider(ModRegistryDataGenerator::new);
		pack.addProvider(LootTableProvider::new);
		pack.addProvider(AdvancementsProvider::new);
		//? if >1.21.11
		pack.addProvider(VillagersTradesTagsProvider::new);

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
	public void buildRegistry(RegistrySetBuilder registryBuilder) {
		registryBuilder.add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
		registryBuilder.add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
		//? if >1.21.11
		registryBuilder.add(Registries.VILLAGER_TRADE, VillagersTradesProvider::bootstrap);
	}
}
