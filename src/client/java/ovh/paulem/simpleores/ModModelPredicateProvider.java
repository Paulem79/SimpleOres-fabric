package ovh.paulem.simpleores;

//? if <=1.21.3 {
/*import ovh.paulem.simpleores.items.ModItems;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import ovh.paulem.simpleores.stonecutter.SCIdentifier;

public class ModModelPredicateProvider {

    public static void registerModModels(){
        registerNewBow(ModItems.MYTHRIL_BOW);
        registerNewBow(ModItems.ONYX_BOW);
    }

    private static void registerNewBow(Item bow) {
        ModelPredicateProviderRegistry.register(bow, SCIdentifier.ofVanilla("pull"), (stack, world, entity, seed) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return entity.getActiveItem() != stack ? 0.0F : (float)(stack.getMaxUseTime(//? if >1.20.4
                        entity
                ) - entity.getItemUseTimeLeft()) / 20.0F;
            }
        });
        ModelPredicateProviderRegistry.register(bow, SCIdentifier.ofVanilla("pulling"), (stack, world, entity, seed) -> entity != null
                && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F);
    }
}
*///?} else {
public class ModModelPredicateProvider {

    public static void registerModModels() {
        // No model predicates to register for versions >= 1.21.4
    }
}
//?}