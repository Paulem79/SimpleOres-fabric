package io.github.paulem.simpleores;

import io.github.paulem.simpleores.items.ModItems;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class ModModelPredicateProvider {

    public static void registerModModels(){
        registerNewBow(ModItems.MYTHRIL_BOW);
        registerNewBow(ModItems.ONYX_BOW);
    }

    private static void registerNewBow(Item bow) {
        ModelPredicateProviderRegistry.register(bow, new Identifier(Identifier.DEFAULT_NAMESPACE, "pull"), (stack, world, entity, seed) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return entity.getActiveItem() != stack ? 0.0F : (float)(stack.getMaxUseTime() - entity.getItemUseTimeLeft()) / 20.0F;
            }
        });
        ModelPredicateProviderRegistry.register(bow, new Identifier(Identifier.DEFAULT_NAMESPACE, "pulling"), (stack, world, entity, seed) -> entity != null
                && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F);
    }
}