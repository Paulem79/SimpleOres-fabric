package net.paulem.simpleores.villagers;

import net.minecraft.resources.ResourceKey;
//? afterDeobf {

import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;

import java.util.List;
import java.util.Optional;

import static net.minecraft.world.item.trading.VillagerTrades.enchantedItem;
//? }
import net.minecraft.world.entity.npc.villager.VillagerProfession;

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
            trade = new VillagerTrade(
                    from.getWants(),
                    to.getGives(),
                    maxUses,
                    xp,
                    reputationDiscount,
                    Optional.empty(),
                    enchantedItem(items, enchantmentsForTradedEquipment
                            //? if >=26.2
                            .get()
                            , to.item())
            );
        } else {
            trade = new VillagerTrade(
                    from.getWants(),
                    to.getGives(),
                    maxUses,
                    xp,
                    reputationDiscount,
                    Optional.empty(),
                    List.of()
            );
        }

        return new TradeDefinition(profession, level, id, trade);
         //? } else {
        /*return new TradeDefinition(profession, level, id, from, to, maxUses, xp, reputationDiscount, enchanted);
        *///? }
    }

}
