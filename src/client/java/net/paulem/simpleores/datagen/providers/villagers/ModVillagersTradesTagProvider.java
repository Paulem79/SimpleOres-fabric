package net.paulem.simpleores.datagen.providers.villagers;

//? if afterDeobf {
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
//? afterDeobf
import net.minecraft.world.item.trading.VillagerTrade;
import net.paulem.simpleores.villagers.TradeDefinition;
import net.paulem.simpleores.villagers.ModVillagersTrades;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class ModVillagersTradesTagProvider extends TagsProvider<VillagerTrade> {

    public ModVillagersTradesTagProvider(final PackOutput output, final CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, Registries.VILLAGER_TRADE, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider registries) {
        for (TradeDefinition tradeDefinition : ModVillagersTrades.TRADE_DEFINITIONS) {
            this.registerTrade(tradeDefinition.profession(), tradeDefinition.level(), tradeDefinition.tradeName());
        }
    }

    public void registerTrade(VillagerProfession profession, int level, String tradeName) {
        this.tag(getKey(profession, level)).add(ModVillagersTradesProvider.createKey(profession, level, tradeName));
    }

    /**
     * Get the tag key for the given profession and level.
     */
    public static TagKey<VillagerTrade> getKey(VillagerProfession profession, int level) {
        return TagKey.create(Registries.VILLAGER_TRADE, Identifier.withDefaultNamespace(
                getProfessionName(profession).toLowerCase().replace(" ", "_") + "/level_" + level
        ));
    }

    public static String getProfessionName(VillagerProfession profession) {
        return BuiltInRegistries.VILLAGER_PROFESSION.getKey(profession).toShortString();
    }

}
//? } else {
/*public class ModVillagersTradesTagProvider {}
*///? }
