package io.github.paulem.simpleores.mixin;

import com.google.common.collect.ImmutableList;
import io.github.paulem.simpleores.mixin.accessor.HolderSetDirectAccessor;
import io.github.paulem.simpleores.tags.ModTags;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(MatchToolLootCondition.class)
public abstract class MatchToolMixin implements LootCondition {

    @Unique
    private static final List<ItemPredicate> ITEM_PREDICATES = new ArrayList<>();

    @Inject(at = @At("RETURN"), method = "<init>")
    private void initProxy(CallbackInfo ci) {
        //collect all MatchTool predicates
        ((MatchToolLootCondition)(Object)this).predicate().ifPresent(ITEM_PREDICATES::add);
    }

    static {
        CommonLifecycleEvents.TAGS_LOADED.register((registries, client) -> {
            RegistryEntryList.Named<Item> modShears = Registries.ITEM.getEntryList(ModTags.Items.SHEARS).get();
            RegistryEntry<Item> shearsHolder = Registries.ITEM.getEntry(Items.SHEARS);
            //add mod shears to all MatchTool predicates that contains vanilla shears
            for (RegistryEntry<Item> modShear : modShears) {
                for (ItemPredicate itemPredicate : ITEM_PREDICATES) {
                    itemPredicate.items().ifPresent(holders -> {
                        if (holders instanceof RegistryEntryList.Direct && holders.contains(shearsHolder) && !holders.contains(modShear)) {
                            HolderSetDirectAccessor<Item> accessor = ((HolderSetDirectAccessor<Item>) holders);
                            ArrayList<RegistryEntry<Item>> newList = new ArrayList<>(accessor.getEntries());
                            newList.add(modShear);
                            accessor.setEntries(ImmutableList.copyOf(newList));
                            accessor.setEntrySet(null); //reset contents set
                        }
                    });
                }
            }
        });
    }

}
