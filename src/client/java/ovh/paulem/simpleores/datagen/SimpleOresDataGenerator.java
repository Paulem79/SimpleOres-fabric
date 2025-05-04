package ovh.paulem.simpleores.datagen;

import ovh.paulem.simpleores.datagen.providers.*;
import ovh.paulem.simpleores.datagen.providers.recipes.RecipeProvider;
import ovh.paulem.simpleores.datagen.providers.tags.BlockTagProvider;
import ovh.paulem.simpleores.datagen.providers.tags.ItemTagProvider;
import ovh.paulem.simpleores.world.ModConfiguredFeatures;
import ovh.paulem.simpleores.world.ModPlacedFeatures;
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

		FabricDataGenerator.Pack secondaryPack = fabricDataGenerator.createPack();
		secondaryPack.addProvider(ModEquipmentAssetProvider::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
		registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
	}
}
