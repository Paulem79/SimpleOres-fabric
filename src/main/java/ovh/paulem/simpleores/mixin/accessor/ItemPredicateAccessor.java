package ovh.paulem.simpleores.mixin.accessor;

import net.minecraft.predicate.item.ItemPredicate;
import org.spongepowered.asm.mixin.Mixin;
//? if 1.20.1 {
/*import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;*/
//?}

@Mixin(ItemPredicate.class)
public interface ItemPredicateAccessor {
    //? if 1.20.1 {
    /*@Mutable
    @Accessor("items")
    void setItems(Set<Item> items);

    @Mutable
    @Accessor("items")
    Set<Item> getItems();
    *///?}
}
