package net.paulem.simpleores.villagers;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.VillagerProfession;

public class ModTrades {
    public ModTrades() {}

    public TradeDefinition createDefinition(
            VillagerProfession
                    profession, int level, String id,
                                            ModTradeItem from, ModTradeItem to, int maxUses, int xp,
                                            float reputationDiscount) {
        return createDefinition(profession, level, id, from, to, maxUses, xp, reputationDiscount, false);
    }

    public TradeDefinition createDefinition(
            VillagerProfession
                    profession, int level, String id,
                                            ModTradeItem from, ModTradeItem to, int maxUses, int xp,
                                            float reputationDiscount, boolean enchanted) {

        return new TradeDefinition(profession, level, id, from, to, maxUses, xp, reputationDiscount, enchanted);
    }

}
