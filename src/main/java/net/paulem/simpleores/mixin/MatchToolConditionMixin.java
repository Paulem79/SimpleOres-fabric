package net.paulem.simpleores.mixin;

import com.google.common.collect.ImmutableList;
import net.paulem.simpleores.mixin.accessor.HolderSetDirectAccessor;
import net.paulem.simpleores.tags.ModTags;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//? if <=1.20.1 {
import com.google.common.collect.ImmutableSet;
import net.paulem.simpleores.mixin.accessor.ItemPredicateAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
//?}

import java.util.ArrayList;
import java.util.List;

@Mixin(MatchTool.class)
public abstract class MatchToolConditionMixin implements LootItemCondition {
    //? if <=1.20.1 {
    
    @SuppressWarnings("ShadowModifiers")
    @Shadow @Final private ItemPredicate predicate;
     
     //?}

    @Unique
    private static final List<ItemPredicate> ITEM_PREDICATES = new ArrayList<>();

    @Inject(at = @At("RETURN"), method = "<init>")
    private void initProxy(CallbackInfo ci) {
        //? if <=1.20.1 {
        if (((ItemPredicateAccessor) predicate).getItems() != null) ITEM_PREDICATES.add(predicate);
        //?} else {
        /*((MatchTool)(Object)this).predicate().ifPresent(ITEM_PREDICATES::add);
        *///?}
    }

    static {
        CommonLifecycleEvents.TAGS_LOADED.register((registries, client) -> {
            //? if <=1.20.1 {
            
            if (!client) {
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
             
             //?} else {
                /*//? if <=1.21 {
                HolderSet.Named<Item> modShears = BuiltInRegistries.ITEM.getTag(ModTags.Items.SHEARS).get();
                //?} else {
                /^HolderSet.Named<Item> modShears = BuiltInRegistries.ITEM.getOrThrow(ModTags.Items.SHEARS);
                ^///?}
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
            *///?}
        });
    }

}
