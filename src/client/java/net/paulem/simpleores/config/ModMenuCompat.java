package net.paulem.simpleores.config;

//? !hasMidnightlib || !hasModmenu {
 public class ModMenuCompat {}
//?} else {
/*import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.paulem.simpleores.SimpleOres;

@Environment(EnvType.CLIENT)
public class ModMenuCompat implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> MidnightConfig.getScreen(parent, SimpleOres.MOD_ID);
    }
}
*///?}