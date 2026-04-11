package net.paulem.simpleores.items;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.paulem.simpleores.SimpleOres;

public class ModComponents {
    public static DataComponentType<Identifier> BUCKET_FLUID_BLOCK_COMPONENT;

    public static void init() {
        SimpleOres.LOGGER.info("Registering {} components", SimpleOres.MOD_ID);

        BUCKET_FLUID_BLOCK_COMPONENT = Registry.register(
                BuiltInRegistries.DATA_COMPONENT_TYPE,
                Identifier.fromNamespaceAndPath(SimpleOres.MOD_ID, "bucket_fluid_block"),
                DataComponentType.<Identifier>builder()
                        .persistent(Identifier.CODEC)
                        .build()
        );
    }
}