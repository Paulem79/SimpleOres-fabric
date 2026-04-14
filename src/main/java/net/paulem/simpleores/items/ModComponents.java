package net.paulem.simpleores.items;

//? <=1.20.4 {
//public class ModComponents {}
//?} else {

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.paulem.simpleores.SimpleOres;
import net.paulem.simpleores.stonecutter.SCId;

public class ModComponents {
    public static DataComponentType<Identifier> BUCKET_BLOCK_COMPONENT;
    public static DataComponentType<Identifier> BUCKET_FISH_COMPONENT;

    public static void init() {
        SimpleOres.LOGGER.info("Registering {} components", SimpleOres.MOD_ID);

        BUCKET_BLOCK_COMPONENT = Registry.register(
                BuiltInRegistries.DATA_COMPONENT_TYPE,
                SCId.of("bucket_block"),
                DataComponentType.<Identifier>builder()
                        .persistent(Identifier.CODEC)
                        .build()
        );

        BUCKET_FISH_COMPONENT = Registry.register(
                BuiltInRegistries.DATA_COMPONENT_TYPE,
                SCId.of(SimpleOres.MOD_ID, "bucket_fish"),
                DataComponentType.<Identifier>builder()
                        .persistent(Identifier.CODEC)
                        .build()
        );
    }
}
//?}