package net.paulem.simpleores.villagers;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.trading.VillagerTrade;

import java.util.List;
import java.util.Optional;

import static net.minecraft.world.item.trading.VillagerTrades.enchantedItem;

public class ModTrades {
    private final HolderGetter<Item> items;
    private final Optional<HolderSet<Enchantment>> enchantmentsForTradedEquipment;

    public ModTrades(final BootstrapContext<VillagerTrade> context) {
        items = context.lookup(Registries.ITEM);
        enchantmentsForTradedEquipment = context.lookup(Registries.ENCHANTMENT)
                .get(EnchantmentTags.ON_TRADED_EQUIPMENT)
                .map(named -> named);
    }

    public TradeDefinition createDefinition(ResourceKey<VillagerProfession> profession, int level, String id,
                                            ModTradeItem from, ModTradeItem to, int maxUses, int xp,
                                            float reputationDiscount) {
        return createDefinition(profession, level, id, from, to, maxUses, xp, reputationDiscount, false);
    }

    public TradeDefinition createDefinition(ResourceKey<VillagerProfession> profession, int level, String id,
                                            ModTradeItem from, ModTradeItem to, int maxUses, int xp,
                                            float reputationDiscount, boolean enchanted) {
        VillagerTrade trade;
        if (enchanted) {
            trade = new VillagerTrade(
                    from.getFrom(),
                    to.getTo(),
                    maxUses,
                    xp,
                    reputationDiscount,
                    Optional.empty(),
                    enchantedItem(items, enchantmentsForTradedEquipment, to.item())
            );
        } else {
            trade = new VillagerTrade(
                    from.getFrom(),
                    to.getTo(),
                    maxUses,
                    xp,
                    reputationDiscount,
                    Optional.empty(),
                    List.of()
            );
        }

        return new TradeDefinition(profession, level, id, trade);
    }

}
