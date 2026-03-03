package net.paulem.simpleores.stonecutter;

import net.minecraft.resources.ResourceLocation;
import net.paulem.simpleores.SimpleOres;

public class SCId {
    public static ResourceLocation ofVanilla(String path) {
        //? if >=1.21 {
        /*return ResourceLocation.withDefaultNamespace(path);
        *///?} else {
        return of(ResourceLocation.DEFAULT_NAMESPACE, path);
        //?}
    }
    public static ResourceLocation of(String name) {
        return of(SimpleOres.MOD_ID, name);
    }

    public static ResourceLocation of(String modId, String name) {
        //? if >=1.21 {
        /*return ResourceLocation.fromNamespaceAndPath(modId, name);
        *///?} else {
        return new ResourceLocation(modId, name);
        //?}
    }
}
