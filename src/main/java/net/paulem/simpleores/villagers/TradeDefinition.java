package net.paulem.simpleores.villagers;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.villager.VillagerProfession;

//? if afterDeobf {
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.trading.VillagerTrade;

public record TradeDefinition(ResourceKey<VillagerProfession>
        professionKey, int level, String tradeName, VillagerTrade trade) {
    public VillagerProfession profession() {
        return BuiltInRegistries.VILLAGER_PROFESSION.getOrThrow(professionKey()).value();
    }
}
//? } else {
/*public record TradeDefinition(//$villagerProfession
        ResourceKey<VillagerProfession>
        profession, int level, String id,
                              ModTradeItem from, ModTradeItem to, int maxUses, int xp,
                              float reputationDiscount, boolean enchanted) {}
*///? }