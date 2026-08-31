package net.paulem.simpleores.villagers;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
//? afterDeobf {

import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.trading.TradeCost;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static net.minecraft.world.item.trading.VillagerTrades.enchantedItem;
//? }
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import org.jspecify.annotations.Nullable;

public class ModTrades {
    //? afterDeobf {
    
    private final HolderGetter<Item> items;
    private final Optional<HolderSet<Enchantment>> enchantmentsForTradedEquipment;

    public ModTrades(final BootstrapContext<VillagerTrade> context) {
        items = context.lookup(Registries.ITEM);
        enchantmentsForTradedEquipment = context.lookup(Registries.ENCHANTMENT)
                .get(EnchantmentTags.ON_TRADED_EQUIPMENT)
                .map(named -> named);
    }
    //?} else {
    /*public ModTrades() {}
    *///? }

    public TradeDefinition createDefinition(//$villagerProfession
            ResourceKey<VillagerProfession>
                    profession, int level, String id,
                                            ModTradeItem from, ModTradeItem to, int maxUses, int xp,
                                            float reputationDiscount) {
        return createDefinition(profession, level, id, from, to, maxUses, xp, reputationDiscount, false);
    }

    public TradeDefinition createDefinition(//$villagerProfession
            ResourceKey<VillagerProfession>
                    profession, int level, String id,
                                            ModTradeItem from, ModTradeItem to, int maxUses, int xp,
                                            float reputationDiscount, boolean enchanted) {
        //? afterDeobf {
        
        VillagerTrade trade;
        if (enchanted) {
            trade = makeTrade(
                    from.getWants(),
                    to.getGives(),
                    maxUses,
                    xp,
                    reputationDiscount,
                    enchantedItem(items, enchantmentsForTradedEquipment
                            //? if >=26.2
                            .get()
                            , to.item())
            );
        } else {
            trade = makeTrade(
                    from.getWants(),
                    to.getGives(),
                    maxUses,
                    xp,
                    reputationDiscount,
                    null
            );
        }

        return new TradeDefinition(profession, level, id, trade);
         //? } else {
        /*return new TradeDefinition(profession, level, id, from, to, maxUses, xp, reputationDiscount, enchanted);
        *///? }
    }

    //? afterDeobf {
    public static VillagerTrade makeTrade(
            final TradeCost wants,
            final ItemStackTemplate gives,
            final int maxUses,
            final int xp,
            final float reputationDiscount,
            @Nullable
                    //? if >26.2 {
            /*List<Holder<LootItemFunction>>
                    *///?} else {
                    List<LootItemFunction>
                    //?}
                    givenItemModifiers) {
        //? if >26.2 {
        /*VillagerTrade.Builder builder = VillagerTrade.builder(wants, gives, maxUses, xp, reputationDiscount);
        return builder
                .addModifiers(givenItemModifiers != null ? givenItemModifiers : List.of())
                .build();
        *///?} else {
        return new VillagerTrade(
                wants,
                gives,
                maxUses,
                xp,
                reputationDiscount,
                Optional.empty(),
                givenItemModifiers != null ? givenItemModifiers : List.of()
        );
        //?}
    }
    //?}

}
