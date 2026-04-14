package net.paulem.simpleores;

//? if <=1.21.3 {
/*import net.paulem.simpleores.items.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.Item;
import net.paulem.simpleores.stonecutter.SCId;

public class ModModelPredicateProvider {

    public static void registerModModels(){
        registerNewBow(ModItems.MYTHRIL_BOW);
        registerNewBow(ModItems.ONYX_BOW);
    }

    private static void registerNewBow(Item bow) {
        ItemProperties.register(bow, SCId.ofVanilla("pull"), (stack, world, entity, seed) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return entity.getUseItem() != stack ? 0.0F : (float)(stack.getUseDuration(//? if >1.20.6
                        entity
                ) - entity.getUseItemRemainingTicks()) / 20.0F;
            }
        });
        ItemProperties.register(bow, SCId.ofVanilla("pulling"), (stack, world, entity, seed) -> entity != null
                && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
    }
}
*///?} else {
public class ModModelPredicateProvider {

    public static void registerModModels() {
        // No model predicates to register for versions >= 1.21.4
    }
}
//?}
