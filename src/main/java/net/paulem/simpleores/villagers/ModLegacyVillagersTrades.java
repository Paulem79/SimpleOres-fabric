package net.paulem.simpleores.villagers;

//? if >=26.1 {
public class ModLegacyVillagersTrades {
    public static void registerCustomTrades() {}
}
//?} else {

/*import net.minecraft.world.entity.npc.villager.VillagerTrades;
import net.minecraft.world.item.trading.MerchantOffer;
//? if fabric {
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
//?} else {
/^import net.neoforged.neoforge.event.village.VillagerTradesEvent;
^///?}

public class ModLegacyVillagersTrades {
    public static void registerCustomTrades() {
        ModVillagersTrades.generateFromBootstrap();

        //? if fabric {
        for (TradeDefinition trade : ModVillagersTrades.TRADE_DEFINITIONS) {
            TradeOfferHelper.registerVillagerOffers(trade.profession(), trade.level(), factories -> factories.add(createListing(trade)));
        }
        //?}
    }

    //? if neoforge {
    /^public static void onVillagerTrades(VillagerTradesEvent event) {
        for (TradeDefinition trade : ModVillagersTrades.TRADE_DEFINITIONS) {
            if (!trade.matches(event.getType())) continue;

            event.getTrades().get(trade.level()).add(createListing(trade));
        }
    }
    ^///?}

    private static VillagerTrades.ItemListing createListing(TradeDefinition trade) {
        if (trade.enchanted()) {
            return (level, entity, random) -> new VillagerTrades.EnchantedItemForEmeralds(
                    trade.to().getGives().getItem(),
                    trade.from().getWants() //? if >1.20.5 {
                    .count(),
                    //?} else {
                    /^.getCount(),
                    ^///?}
                    trade.maxUses(),
                    trade.xp(),
                    trade.reputationDiscount()
            ).getOffer(level, entity, random);
        }

        return (level, entity, random) -> new MerchantOffer(
                trade.to().getWants(),
                trade.from().getGives(),
                trade.maxUses(),
                trade.xp(),
                trade.reputationDiscount()
        );
    }
}
*///?}
