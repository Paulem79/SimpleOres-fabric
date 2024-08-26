package io.github.paulem.simpleores.datagen;

import io.github.paulem.simpleores.datagen.providers.AdvancementsProvider;
import io.github.paulem.simpleores.datagen.providers.LootTableProvider;
import io.github.paulem.simpleores.datagen.providers.ModelProvider;
import io.github.paulem.simpleores.datagen.providers.WorldGenProvider;
import io.github.paulem.simpleores.datagen.providers.recipes.RecipeProvider;
import io.github.paulem.simpleores.datagen.providers.tags.BlockTagProvider;
import io.github.paulem.simpleores.datagen.providers.tags.ItemTagProvider;
import io.github.paulem.simpleores.datagen.world.ModConfiguredFeatures;
import io.github.paulem.simpleores.datagen.world.ModPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

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
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
		registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
	}
}
