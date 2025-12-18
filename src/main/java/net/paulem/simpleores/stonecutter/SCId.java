package net.paulem.simpleores.stonecutter;

import net.minecraft.resources.Identifier;
import net.paulem.simpleores.SimpleOres;

public class SCId {
    public static Identifier ofVanilla(String path) {
        //? if >=1.21 {
        return Identifier.withDefaultNamespace(path);
        //?} else {
        /*return of(ResourceLocation.DEFAULT_NAMESPACE, path);
        *///?}
    }
    public static Identifier of(String name) {
        return of(SimpleOres.MOD_ID, name);
    }

    public static Identifier of(String modId, String name) {
        //? if >=1.21 {
        return Identifier.fromNamespaceAndPath(modId, name);
        //?} else {
        /*return new ResourceLocation(modId, name);
        *///?}
    }
}
