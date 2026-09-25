package net.paulem.simpleores.platform;

//? if fabric {
import net.fabricmc.loader.api.FabricLoader;
//?} else {
/*import net.neoforged.fml.ModList;
*///?}

/**
 * Everything which differs between the loaders and is needed by the common code.
 */
public final class Platform {
    private Platform() {}

    public static boolean isModLoaded(String modId) {
        //? if fabric {
        return FabricLoader.getInstance().isModLoaded(modId);
        //?} else {
        /*return ModList.get().isLoaded(modId);
        *///?}
    }

    public static boolean isFabric() {
        //? if fabric {
        return true;
        //?} else {
        /*return false;
        *///?}
    }
}
