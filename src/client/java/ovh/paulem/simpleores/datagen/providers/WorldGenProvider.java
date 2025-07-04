package ovh.paulem.simpleores.datagen.providers;

import ovh.paulem.simpleores.SimpleOres;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class WorldGenProvider extends FabricDynamicRegistryProvider {
    public WorldGenProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        // HERE GOES FUTURE WORLD GEN!
        entries.addAll(//? if >1.21 {
                registries.getOrThrow
                        //?} else {
                        /*registries.getWrapperOrThrow
                        *///?}
                        (RegistryKeys.CONFIGURED_FEATURE));
        entries.addAll(//? if >1.21 {
                registries.getOrThrow
                //?} else {
                /*registries.getWrapperOrThrow
                *///?}
                        (RegistryKeys.PLACED_FEATURE));
    }

    @Override
    public String getName() {
        return SimpleOres.MOD_ID;
    }
}