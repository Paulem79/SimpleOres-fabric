package net.paulem.simpleores.datagen.providers;

// TODO: Merge trades with CustomTrades
//? if >1.21.11 {
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.KeyTagProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.TradeCost;
import net.minecraft.world.item.trading.VillagerTrade;
import net.paulem.simpleores.SimpleOres;
import net.paulem.simpleores.items.ModItems;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class VillagersTradesTagsProvider extends KeyTagProvider<VillagerTrade> {
    protected static final List<TradeDefinition> TRADE_DEFINITIONS = new ArrayList<>();

    static {
        // ARMORER - level 1: emerald -> copper armor pieces
        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.ARMORER, 1, "copper_helmet_emerald",
                new VillagerTrade(
                        new TradeCost(Items.EMERALD, 3),
                        new ItemStack(Items.COPPER_HELMET),
                        12,
                        1,
                        0.2F,
                        Optional.empty(),
                        List.of()
                )
        ));

        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.ARMORER, 1, "copper_chestplate_emerald",
                new VillagerTrade(
                        new TradeCost(Items.EMERALD, 7),
                        new ItemStack(Items.COPPER_CHESTPLATE),
                        12,
                        1,
                        0.2F,
                        Optional.empty(),
                        List.of()
                )
        ));

        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.ARMORER, 1, "copper_leggings_emerald",
                new VillagerTrade(
                        new TradeCost(Items.EMERALD, 5),
                        new ItemStack(Items.COPPER_LEGGINGS),
                        12,
                        1,
                        0.2F,
                        Optional.empty(),
                        List.of()
                )
        ));

        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.ARMORER, 1, "copper_boots_emerald",
                new VillagerTrade(
                        new TradeCost(Items.EMERALD, 2),
                        new ItemStack(Items.COPPER_BOOTS),
                        12,
                        1,
                        0.2F,
                        Optional.empty(),
                        List.of()
                )
        ));

        // ARMORER - level 2: ingots -> emerald
        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.ARMORER, 2, "copper_ingot_to_emerald",
                new VillagerTrade(
                        new TradeCost(Items.COPPER_INGOT, 4),
                        new ItemStack(Items.EMERALD),
                        12,
                        10,
                        0.05F,
                        Optional.empty(),
                        List.of()
                )
        ));

        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.ARMORER, 2, "tin_ingot_to_emerald",
                new VillagerTrade(
                        new TradeCost(ModItems.TIN_INGOT, 4),
                        new ItemStack(Items.EMERALD),
                        12,
                        10,
                        0.05F,
                        Optional.empty(),
                        List.of()
                )
        ));

        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.ARMORER, 2, "tin_leggings_emerald",
                new VillagerTrade(
                        new TradeCost(Items.EMERALD, 3),
                        new ItemStack(ModItems.TIN_LEGGINGS),
                        12,
                        5,
                        0.2F,
                        Optional.empty(),
                        List.of()
                )
        ));

        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.ARMORER, 2, "tin_boots_emerald",
                new VillagerTrade(
                        new TradeCost(Items.EMERALD, 1),
                        new ItemStack(ModItems.TIN_BOOTS),
                        12,
                        5,
                        0.2F,
                        Optional.empty(),
                        List.of()
                )
        ));

        // ARMORER - level 3: mythril -> emerald and tin armor
        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.ARMORER, 3, "mythril_ingot_to_emerald",
                new VillagerTrade(
                        new TradeCost(ModItems.MYTHRIL_INGOT, 1),
                        new ItemStack(Items.EMERALD),
                        12,
                        20,
                        0.05F,
                        Optional.empty(),
                        List.of()
                )
        ));

        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.ARMORER, 3, "tin_helmet_emerald",
                new VillagerTrade(
                        new TradeCost(Items.EMERALD, 1),
                        new ItemStack(ModItems.TIN_HELMET),
                        12,
                        10,
                        0.2F,
                        Optional.empty(),
                        List.of()
                )
        ));

        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.ARMORER, 3, "tin_chestplate_emerald",
                new VillagerTrade(
                        new TradeCost(Items.EMERALD, 3),
                        new ItemStack(ModItems.TIN_CHESTPLATE),
                        12,
                        10,
                        0.2F,
                        Optional.empty(),
                        List.of()
                )
        ));

        // ARMORER - level 4: enchanted mythril leggings/boots
        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.ARMORER, 4, "mythril_leggings_enchanted",
                new VillagerTrade(
                        new TradeCost(Items.EMERALD, 14),
                        new ItemStack(ModItems.MYTHRIL_LEGGINGS),
                        15,
                        3,
                        0.2F,
                        Optional.empty(),
                        List.of()
                )
        ));

        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.ARMORER, 4, "mythril_boots_enchanted",
                new VillagerTrade(
                        new TradeCost(Items.EMERALD, 8),
                        new ItemStack(ModItems.MYTHRIL_BOOTS),
                        15,
                        3,
                        0.2F,
                        Optional.empty(),
                        List.of()
                )
        ));

        // ARMORER - level 5: enchanted mythril helmet/chestplate
        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.ARMORER, 5, "mythril_helmet_enchanted",
                new VillagerTrade(
                        new TradeCost(Items.EMERALD, 8),
                        new ItemStack(ModItems.MYTHRIL_HELMET),
                        30,
                        3,
                        0.2F,
                        Optional.empty(),
                        List.of()
                )
        ));

        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.ARMORER, 5, "mythril_chestplate_enchanted",
                new VillagerTrade(
                        new TradeCost(Items.EMERALD, 16),
                        new ItemStack(ModItems.MYTHRIL_CHESTPLATE),
                        30,
                        3,
                        0.2F,
                        Optional.empty(),
                        List.of()
                )
        ));

        // TOOLSMITH - level 2: ingots -> emerald and tool offers
        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.TOOLSMITH, 2, "copper_ingot_to_emerald",
                new VillagerTrade(
                        new TradeCost(Items.COPPER_INGOT, 4),
                        new ItemStack(Items.EMERALD),
                        12,
                        10,
                        0.05F,
                        Optional.empty(),
                        List.of()
                )
        ));

        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.TOOLSMITH, 2, "tin_ingot_to_emerald",
                new VillagerTrade(
                        new TradeCost(ModItems.TIN_INGOT, 4),
                        new ItemStack(Items.EMERALD),
                        12,
                        10,
                        0.05F,
                        Optional.empty(),
                        List.of()
                )
        ));

        // Copper tool offers (TOOLSMITH level 2) - use vanilla Items constants for copper tools
        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.TOOLSMITH, 2, "copper_axe_emerald",
                new VillagerTrade(
                        new TradeCost(Items.EMERALD, 1),
                        new ItemStack(Items.COPPER_AXE),
                        12,
                        1,
                        0.2F,
                        Optional.empty(),
                        List.of()
                )
        ));

        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.TOOLSMITH, 2, "copper_shovel_emerald",
                new VillagerTrade(
                        new TradeCost(Items.EMERALD, 1),
                        new ItemStack(Items.COPPER_SHOVEL),
                        12,
                        1,
                        0.2F,
                        Optional.empty(),
                        List.of()
                )
        ));

        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.TOOLSMITH, 2, "copper_hoe_emerald",
                new VillagerTrade(
                        new TradeCost(Items.EMERALD, 1),
                        new ItemStack(Items.COPPER_HOE),
                        12,
                        1,
                        0.2F,
                        Optional.empty(),
                        List.of()
                )
        ));

        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.TOOLSMITH, 2, "copper_pickaxe_emerald",
                new VillagerTrade(
                        new TradeCost(Items.EMERALD, 1),
                        new ItemStack(Items.COPPER_PICKAXE),
                        12,
                        1,
                        0.2F,
                        Optional.empty(),
                        List.of()
                )
        ));

        // tin tools
        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.TOOLSMITH, 2, "tin_axe_emerald",
                new VillagerTrade(
                        new TradeCost(Items.EMERALD, 1),
                        new ItemStack(ModItems.TIN_AXE),
                        12,
                        1,
                        0.2F,
                        Optional.empty(),
                        List.of()
                )
        ));

        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.TOOLSMITH, 2, "tin_shovel_emerald",
                new VillagerTrade(
                        new TradeCost(Items.EMERALD, 1),
                        new ItemStack(ModItems.TIN_SHOVEL),
                        12,
                        1,
                        0.2F,
                        Optional.empty(),
                        List.of()
                )
        ));

        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.TOOLSMITH, 2, "tin_hoe_emerald",
                new VillagerTrade(
                        new TradeCost(Items.EMERALD, 1),
                        new ItemStack(ModItems.TIN_HOE),
                        12,
                        1,
                        0.2F,
                        Optional.empty(),
                        List.of()
                )
        ));

        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.TOOLSMITH, 2, "tin_pickaxe_emerald",
                new VillagerTrade(
                        new TradeCost(Items.EMERALD, 1),
                        new ItemStack(ModItems.TIN_PICKAXE),
                        12,
                        1,
                        0.2F,
                        Optional.empty(),
                        List.of()
                )
        ));

        // TOOLSMITH - level 3
        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.TOOLSMITH, 3, "mythril_ingot_to_emerald",
                new VillagerTrade(
                        new TradeCost(ModItems.MYTHRIL_INGOT, 1),
                        new ItemStack(Items.EMERALD),
                        12,
                        20,
                        0.05F,
                        Optional.empty(),
                        List.of()
                )
        ));

        // enchanted mythril tools level 3
        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.TOOLSMITH, 3, "mythril_axe_enchanted_lvl3",
                new VillagerTrade(
                        new TradeCost(Items.EMERALD, 2),
                        new ItemStack(ModItems.MYTHRIL_AXE),
                        10,
                        3,
                        0.2F,
                        Optional.empty(),
                        List.of()
                )
        ));

        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.TOOLSMITH, 3, "mythril_shovel_enchanted_lvl3",
                new VillagerTrade(
                        new TradeCost(Items.EMERALD, 3),
                        new ItemStack(ModItems.MYTHRIL_SHOVEL),
                        10,
                        3,
                        0.2F,
                        Optional.empty(),
                        List.of()
                )
        ));

        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.TOOLSMITH, 3, "mythril_pickaxe_enchanted_lvl3",
                new VillagerTrade(
                        new TradeCost(Items.EMERALD, 3),
                        new ItemStack(ModItems.MYTHRIL_PICKAXE),
                        10,
                        3,
                        0.2F,
                        Optional.empty(),
                        List.of()
                )
        ));

        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.TOOLSMITH, 3, "mythril_hoe_emerald",
                new VillagerTrade(
                        new TradeCost(Items.EMERALD, 1),
                        new ItemStack(ModItems.MYTHRIL_HOE),
                        3,
                        10,
                        0.2F,
                        Optional.empty(),
                        List.of()
                )
        ));

        // TOOLSMITH - level 4
        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.TOOLSMITH, 4, "adamantium_ingot_to_emerald",
                new VillagerTrade(
                        new TradeCost(ModItems.ADAMANTIUM_INGOT, 1),
                        new ItemStack(Items.EMERALD, 2),
                        12,
                        30,
                        0.05F,
                        Optional.empty(),
                        List.of()
                )
        ));

        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.TOOLSMITH, 4, "adamantium_axe_enchanted",
                new VillagerTrade(
                        new TradeCost(Items.EMERALD, 12),
                        new ItemStack(ModItems.ADAMANTIUM_AXE),
                        15,
                        3,
                        0.2F,
                        Optional.empty(),
                        List.of()
                )
        ));

        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.TOOLSMITH, 4, "adamantium_shovel_enchanted",
                new VillagerTrade(
                        new TradeCost(Items.EMERALD, 5),
                        new ItemStack(ModItems.ADAMANTIUM_SHOVEL),
                        15,
                        3,
                        0.2F,
                        Optional.empty(),
                        List.of()
                )
        ));

        // TOOLSMITH - level 5
        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.TOOLSMITH, 5, "adamantium_pickaxe_enchanted",
                new VillagerTrade(
                        new TradeCost(Items.EMERALD, 13),
                        new ItemStack(ModItems.ADAMANTIUM_PICKAXE),
                        30,
                        3,
                        0.2F,
                        Optional.empty(),
                        List.of()
                )
        ));

        // WEAPONSMITH - level 1
        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.WEAPONSMITH, 1, "mythril_axe_emerald",
                new VillagerTrade(
                        new TradeCost(Items.EMERALD, 3),
                        new ItemStack(ModItems.MYTHRIL_AXE),
                        12,
                        1,
                        0.2F,
                        Optional.empty(),
                        List.of()
                )
        ));

        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.WEAPONSMITH, 1, "mythril_sword_enchanted",
                new VillagerTrade(
                        new TradeCost(Items.EMERALD, 2),
                        new ItemStack(ModItems.MYTHRIL_SWORD),
                        1,
                        3,
                        0.05F,
                        Optional.empty(),
                        List.of()
                )
        ));

        // WEAPONSMITH - level 2
        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.WEAPONSMITH, 2, "copper_ingot_to_emerald",
                new VillagerTrade(
                        new TradeCost(Items.COPPER_INGOT, 4),
                        new ItemStack(Items.EMERALD),
                        12,
                        10,
                        0.05F,
                        Optional.empty(),
                        List.of()
                )
        ));

        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.WEAPONSMITH, 2, "tin_ingot_to_emerald",
                new VillagerTrade(
                        new TradeCost(ModItems.TIN_INGOT, 4),
                        new ItemStack(Items.EMERALD),
                        12,
                        10,
                        0.05F,
                        Optional.empty(),
                        List.of()
                )
        ));

        // WEAPONSMITH - level 3
        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.WEAPONSMITH, 3, "mythril_ingot_to_emerald",
                new VillagerTrade(
                        new TradeCost(ModItems.MYTHRIL_INGOT, 1),
                        new ItemStack(Items.EMERALD),
                        12,
                        20,
                        0.05F,
                        Optional.empty(),
                        List.of()
                )
        ));

        // WEAPONSMITH - level 4
        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.WEAPONSMITH, 4, "adamantium_ingot_to_emerald",
                new VillagerTrade(
                        new TradeCost(ModItems.ADAMANTIUM_INGOT, 1),
                        new ItemStack(Items.EMERALD),
                        12,
                        30,
                        0.05F,
                        Optional.empty(),
                        List.of()
                )
        ));

        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.WEAPONSMITH, 4, "adamantium_axe_enchanted",
                new VillagerTrade(
                        new TradeCost(Items.EMERALD, 12),
                        new ItemStack(ModItems.ADAMANTIUM_AXE),
                        15,
                        3,
                        0.2F,
                        Optional.empty(),
                        List.of()
                )
        ));

        // WEAPONSMITH - level 5
        TRADE_DEFINITIONS.add(new TradeDefinition(VillagerProfession.WEAPONSMITH, 5, "adamantium_sword_enchanted",
                new VillagerTrade(
                        new TradeCost(Items.EMERALD, 8),
                        new ItemStack(ModItems.ADAMANTIUM_SWORD),
                        30,
                        3,
                        0.2F,
                        Optional.empty(),
                        List.of()
                )
        ));
    }

    public VillagersTradesTagsProvider(final PackOutput output, final CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, Registries.VILLAGER_TRADE, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider registries) {
        for (TradeDefinition tradeDefinition : TRADE_DEFINITIONS) {
            this.registerTrade(tradeDefinition.profession(), tradeDefinition.level(), tradeDefinition.tradeName());
        }
    }

    public void registerTrade(VillagerProfession profession, int level, String tradeName) {
        this.tag(getKey(profession, level)).add(VillagersTradesProvider.createKey(profession, level, tradeName));
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
        String professionName = BuiltInRegistries.VILLAGER_PROFESSION.getKey(profession).toShortString();
        SimpleOres.LOGGER.info(professionName);
        return professionName;
    }

    public record TradeDefinition(ResourceKey<VillagerProfession> professionKey, int level, String tradeName, VillagerTrade trade) {
        public VillagerProfession profession() {
            return BuiltInRegistries.VILLAGER_PROFESSION.getOrThrow(professionKey()).value();
        }
    }
}
//? } else {
/*public class VillagersTradesTagsProvider {}
*///? }
