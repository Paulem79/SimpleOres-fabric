package net.paulem.simpleores.stonecutter;

import net.minecraft.util.Identifier;
import net.paulem.simpleores.SimpleOres;

public class SCIdentifier {
    public static Identifier ofVanilla(String path) {
        //? if >=1.21 {
        return Identifier.ofVanilla(path);
        //?} else {
        /*return of(Identifier.DEFAULT_NAMESPACE, path);
        *///?}
    }
    public static Identifier of(String name) {
        return of(SimpleOres.MOD_ID, name);
    }

    public static Identifier of(String modId, String name) {
        //? if >=1.21 {
        return Identifier.of(modId, name);
        //?} else {
        /*return new Identifier(modId, name);
        *///?}
    }
}
