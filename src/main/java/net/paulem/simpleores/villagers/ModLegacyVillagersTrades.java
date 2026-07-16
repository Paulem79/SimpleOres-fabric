package net.paulem.simpleores.villagers;

//? if >26.1 {
public class ModLegacyVillagersTrades {
    public static void registerCustomTrades() {}
}
//?} else {

/*import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.world.entity.npc.villager.VillagerTrades;
import net.minecraft.world.item.trading.MerchantOffer;

public class ModLegacyVillagersTrades {
    public static void registerCustomTrades() {
        ModVillagersTrades.generateFromBootstrap();

        for (TradeDefinition trade : ModVillagersTrades.TRADE_DEFINITIONS) {
            TradeOfferHelper.registerVillagerOffers(trade.profession(), trade.level(), factories -> {
                if (trade.enchanted()) {
                    factories.add((level, entity, random) -> new VillagerTrades.EnchantedItemForEmeralds(
                            trade.to().getGives().getItem(),
                            trade.from().getWants() //? if >1.20.5 {
                            .count(),
                            //?} else {
                            /^.getCount(),
                            ^///?}
                            trade.maxUses(),
                            trade.xp(),
                            trade.reputationDiscount()
                    ).getOffer(level, entity, random));
                } else {
                    factories.add((level, entity, random) -> new MerchantOffer(
                            trade.to().getWants(),
                            trade.from().getGives(),
                            trade.maxUses(),
                            trade.xp(),
                            trade.reputationDiscount()
                    ));
                }
            });
        }
    }
}
*///?}