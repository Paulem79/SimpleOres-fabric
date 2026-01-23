package net.paulem.simpleores.datagen.providers.villagers;

//? if afterDeobf {
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.trading.VillagerTrade;
import net.paulem.simpleores.SimpleOres;
import net.paulem.simpleores.stonecutter.SCId;
import net.paulem.simpleores.villagers.ModVillagersTrades;
import net.paulem.simpleores.villagers.TradeDefinition;
import org.jspecify.annotations.NonNull;

public class ModVillagersTradesProvider {
    public static @NonNull Holder<VillagerTrade> bootstrap(final BootstrapContext<VillagerTrade> context) {
        ModVillagersTrades.generateFromBootstrap(context);

        int index = 0;

        for (TradeDefinition tradeDefinition : ModVillagersTrades.TRADE_DEFINITIONS) {
            if (index >= ModVillagersTrades.TRADE_DEFINITIONS.size() - 1) {
                break;
            }

            if (index > 0) {
                SimpleOres.LOGGER.info("Registering trade #{}", index);
            }

            registerFromTradeDefinition(context, tradeDefinition);

            index++;
        }

        // Get the last trade definition to return
        return registerFromTradeDefinition(context, ModVillagersTrades.TRADE_DEFINITIONS.get(index));
    }

    public static @NonNull Holder<VillagerTrade> registerFromTradeDefinition(final BootstrapContext<VillagerTrade> context, final TradeDefinition tradeDefinition) {
        ResourceKey<VillagerTrade> key = resourceKey(
                ModVillagersTradesTagsProvider.getProfessionName(tradeDefinition.profession()) + "/" +
                        tradeDefinition.level() + "/" +
                        tradeDefinition.tradeName()
        );

        return register(context, key, tradeDefinition.trade());
    }

    public static ResourceKey<VillagerTrade> createKey(VillagerProfession profession, int level, String name) {
        String professionName = ModVillagersTradesTagsProvider.getProfessionName(profession);
        return resourceKey(professionName + "/" + level + "/" + name);
    }

    public static ResourceKey<VillagerTrade> resourceKey(final String path) {
        return ResourceKey.create(Registries.VILLAGER_TRADE, SCId.of(path));
    }

    public static Holder.Reference<VillagerTrade> register(final BootstrapContext<VillagerTrade> context, final ResourceKey<VillagerTrade> resourceKey, final VillagerTrade villagerTrade) {
        return context.register(resourceKey, villagerTrade);
    }
}
//? } else {
/*public class ModVillagersTradesProvider {}
*///? }
