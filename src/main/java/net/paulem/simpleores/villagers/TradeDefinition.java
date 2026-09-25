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
                              float reputationDiscount, boolean enchanted) {
    /^*
     * @return whether the given profession of the villager is the one of this trade
     ^/
    public boolean matches(Object type) {
        //? if >=1.21.5 {
        // Depending on the version, the loaders give either the key or the profession itself
        if (type instanceof ResourceKey<?> key) return key.equals(profession);

        return type instanceof VillagerProfession villagerProfession
                && net.minecraft.core.registries.BuiltInRegistries.VILLAGER_PROFESSION.getResourceKey(villagerProfession).filter(profession::equals).isPresent();
        //?} else {
        /^return type == profession;
        ^///?}
    }
}
*///? }