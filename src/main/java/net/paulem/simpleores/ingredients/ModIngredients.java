package net.paulem.simpleores.ingredients;

//? if hasBucketlib {
/*public class ModIngredients {}
*///?} else {

import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.paulem.simpleores.SimpleOres;

public class ModIngredients {
    public static void init() {
        SimpleOres.LOGGER.info("Initializing Mod Ingredients...");

        CustomIngredientSerializer.register(MilkIngredient.Serializer.INSTANCE);
    }
}
//?}