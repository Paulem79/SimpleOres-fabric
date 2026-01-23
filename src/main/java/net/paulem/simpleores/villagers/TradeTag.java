package net.paulem.simpleores.villagers;

//? if afterDeobf {
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.villager.VillagerProfession;

public record TradeTag(ResourceKey<VillagerProfession> professionKey, int level, String tradeName) {
    public VillagerProfession profession() {
        return BuiltInRegistries.VILLAGER_PROFESSION.getOrThrow(professionKey()).value();
    }
}
//? } else {
/*public class TradeTag {}
*///? }
