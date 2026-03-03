package net.paulem.simpleores.villagers;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.VillagerProfession;

public record TradeDefinition(
        VillagerProfession
        profession, int level, String id,
                              ModTradeItem from, ModTradeItem to, int maxUses, int xp,
                              float reputationDiscount, boolean enchanted) {}