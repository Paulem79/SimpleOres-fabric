package net.paulem.simpleores.utils;

import com.google.common.collect.ImmutableList;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.paulem.simpleores.mixin.accessor.HolderSetDirectAccessor;
import net.paulem.simpleores.tags.ModTags;
//? if <=1.20.1 {
/*import com.google.common.collect.ImmutableSet;
import net.paulem.simpleores.mixin.accessor.ItemPredicateAccessor;
*///?}

import java.util.ArrayList;
import java.util.List;

/**
 * Adds the shears of the mod to every MatchTool loot condition which accepts the vanilla shears.
 * The predicates are collected by MatchToolConditionMixin and patched when the tags are loaded,
 * which each loader signals by calling {@link #onTagsLoaded(boolean)}.
 */
public final class MatchToolShears {
    private static final List<ItemPredicate> ITEM_PREDICATES = new ArrayList<>();

    private MatchToolShears() {}

    public static void track(ItemPredicate predicate) {
        ITEM_PREDICATES.add(predicate);
    }

    public static void onTagsLoaded(boolean client) {
        //? if <=1.20.1 {

        /*if (!client) {
            List<Item> shears = new ArrayList<>();
            for (Holder<Item> entry :
                    BuiltInRegistries.ITEM.getOrCreateTag(ModTags.Items.SHEARS))
                shears.add(entry.value());

            for (ItemPredicate p : ITEM_PREDICATES) {
                if (((ItemPredicateAccessor) p).getItems().contains(Items.SHEARS)) {
                    ImmutableSet.Builder<Item> builder = new ImmutableSet.Builder<>();
                    builder.addAll(((ItemPredicateAccessor) p).getItems());
                    builder.addAll(shears);
                    ((ItemPredicateAccessor) p).setItems(builder.build());
                }
            }

            shears.clear();
        }
        ITEM_PREDICATES.clear();

        *///?} else {
            //? if <=1.21 {
            /*HolderSet.Named<Item> modShears = BuiltInRegistries.ITEM.getTag(ModTags.Items.SHEARS).get();
            *///?} else {
            HolderSet.Named<Item> modShears = BuiltInRegistries.ITEM.getOrThrow(ModTags.Items.SHEARS);
            //?}
            Holder<Item> shearsHolder = BuiltInRegistries.ITEM.wrapAsHolder(Items.SHEARS);
            //add mod shears to all MatchTool predicates that contains vanilla shears
            for (Holder<Item> modShear : modShears) {
                for (ItemPredicate itemPredicate : ITEM_PREDICATES) {
                    itemPredicate.items().ifPresent(holders -> {
                        if (holders instanceof HolderSet.Direct && holders.contains(shearsHolder) && !holders.contains(modShear)) {
                            HolderSetDirectAccessor<Item> accessor = ((HolderSetDirectAccessor<Item>) holders);
                            ArrayList<Holder<Item>> newList = new ArrayList<>(accessor.getContents());
                            newList.add(modShear);
                            accessor.setContents(ImmutableList.copyOf(newList));
                            accessor.setContentsSet(null); //reset contents set
                        }
                    });
                }
            }
        //?}
    }
}
