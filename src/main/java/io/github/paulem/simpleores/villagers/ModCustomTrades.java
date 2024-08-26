package io.github.paulem.simpleores.villagers;

import io.github.paulem.simpleores.items.ModItems;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;

public class ModCustomTrades {
    public static void registerCustomTrades() {
        // ARMORER
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.ARMORER, 1, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 3),
                    new ItemStack(ModItems.COPPER_HELMET),
                    12, 1, 0.2F
            ));

            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 7),
                    new ItemStack(ModItems.COPPER_CHESTPLATE),
                    12, 1, 0.2F
            ));

            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 5),
                    new ItemStack(ModItems.COPPER_LEGGINGS),
                    12, 1, 0.2F
            ));

            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 2),
                    new ItemStack(ModItems.COPPER_BOOTS),
                    12, 1, 0.2F
            ));
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.ARMORER, 2, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.COPPER_INGOT, 4),
                    new ItemStack(Items.EMERALD),
                    12, 10, 0.05F
            ));

            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(ModItems.TIN_INGOT, 4),
                    new ItemStack(Items.EMERALD),
                    12, 10, 0.05F
            ));

            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 3),
                    new ItemStack(ModItems.TIN_LEGGINGS),
                    12, 5, 0.2F
            ));

            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD),
                    new ItemStack(ModItems.TIN_BOOTS),
                    12, 5, 0.2F
            ));
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.ARMORER, 3, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(ModItems.MYTHRIL_INGOT),
                    new ItemStack(Items.EMERALD),
                    12, 20, 0.05F
            ));

            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD),
                    new ItemStack(ModItems.TIN_HELMET),
                    12, 10, 0.2F
            ));

            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 3),
                    new ItemStack(ModItems.TIN_CHESTPLATE),
                    12, 10, 0.2F
            ));
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.ARMORER, 4, factories -> {
            factories.add((entity, random) -> new TradeOffers.SellEnchantedToolFactory(
                    ModItems.MYTHRIL_LEGGINGS,
                    14, 3, 15, 0.2F
            ).create(entity, random));

            factories.add((entity, random) -> new TradeOffers.SellEnchantedToolFactory(
                    ModItems.MYTHRIL_BOOTS,
                    8, 3, 15, 0.2F
            ).create(entity, random));
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.ARMORER, 5, factories -> {
            factories.add((entity, random) -> new TradeOffers.SellEnchantedToolFactory(
                    ModItems.MYTHRIL_HELMET,
                    8, 3, 30, 0.2F
            ).create(entity, random));

            factories.add((entity, random) -> new TradeOffers.SellEnchantedToolFactory(
                    ModItems.MYTHRIL_CHESTPLATE,
                    16, 3, 30, 0.2F
            ).create(entity, random));
        });

        // TOOLSMITH
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.TOOLSMITH, 2, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.COPPER_INGOT, 4),
                    new ItemStack(Items.EMERALD),
                    12, 10, 0.05F
            ));

            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(ModItems.TIN_INGOT, 4),
                    new ItemStack(Items.EMERALD),
                    12, 10, 0.05F
            ));

            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD),
                    new ItemStack(ModItems.COPPER_AXE),
                    12, 1, 0.2F
            ));

            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD),
                    new ItemStack(ModItems.COPPER_SHOVEL),
                    12, 1, 0.2F
            ));

            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD),
                    new ItemStack(ModItems.COPPER_HOE),
                    12, 1, 0.2F
            ));

            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD),
                    new ItemStack(ModItems.COPPER_PICKAXE),
                    12, 1, 0.2F
            ));

            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD),
                    new ItemStack(ModItems.TIN_AXE),
                    12, 1, 0.2F
            ));

            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD),
                    new ItemStack(ModItems.TIN_SHOVEL),
                    12, 1, 0.2F
            ));

            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD),
                    new ItemStack(ModItems.TIN_HOE),
                    12, 1, 0.2F
            ));

            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD),
                    new ItemStack(ModItems.TIN_PICKAXE),
                    12, 1, 0.2F
            ));
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.TOOLSMITH, 3, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(ModItems.MYTHRIL_INGOT),
                    new ItemStack(Items.EMERALD),
                    12, 20, 0.05F
            ));

            factories.add((entity, random) -> new TradeOffers.SellEnchantedToolFactory(
                    ModItems.MYTHRIL_AXE,
                    2, 3, 10, 0.2F
            ).create(entity, random));

            factories.add((entity, random) -> new TradeOffers.SellEnchantedToolFactory(
                    ModItems.MYTHRIL_SHOVEL,
                    3, 3, 10, 0.2F
            ).create(entity, random));

            factories.add((entity, random) -> new TradeOffers.SellEnchantedToolFactory(
                    ModItems.MYTHRIL_PICKAXE,
                    3, 3, 10, 0.2F
            ).create(entity, random));

            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD),
                    new ItemStack(ModItems.MYTHRIL_HOE),
                    3, 10, 0.2F
            ));
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.TOOLSMITH, 4, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(ModItems.ADAMANTIUM_INGOT),
                    new ItemStack(Items.EMERALD, 2),
                    12, 30, 0.05F
            ));

            factories.add((entity, random) -> new TradeOffers.SellEnchantedToolFactory(
                    ModItems.ADAMANTIUM_AXE,
                    12, 3, 15, 0.2F
            ).create(entity, random));

            factories.add((entity, random) -> new TradeOffers.SellEnchantedToolFactory(
                    ModItems.ADAMANTIUM_SHOVEL,
                    5, 3, 15, 0.2F
            ).create(entity, random));
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.TOOLSMITH, 5, factories -> {
            factories.add((entity, random) -> new TradeOffers.SellEnchantedToolFactory(
                    ModItems.ADAMANTIUM_PICKAXE,
                    13, 3, 30, 0.2F
            ).create(entity, random));
        });

        // WEAPONSMITH
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.WEAPONSMITH, 1, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 3),
                    new ItemStack(ModItems.MYTHRIL_AXE),
                    12, 1, 0.2F
            ));

            factories.add((entity, random) -> new TradeOffers.SellEnchantedToolFactory(
                    ModItems.MYTHRIL_SWORD,
                    2, 3, 1, 0.05F
            ).create(entity, random));
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.WEAPONSMITH, 2, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.COPPER_INGOT, 4),
                    new ItemStack(Items.EMERALD),
                    12, 10, 0.05F
            ));

            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(ModItems.TIN_INGOT, 4),
                    new ItemStack(Items.EMERALD),
                    12, 10, 0.05F
            ));
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.WEAPONSMITH, 3, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(ModItems.MYTHRIL_INGOT),
                    new ItemStack(Items.EMERALD),
                    12, 20, 0.05F
            ));
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.WEAPONSMITH, 4, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(ModItems.ADAMANTIUM_INGOT),
                    new ItemStack(Items.EMERALD),
                    12, 30, 0.05F
            ));

            factories.add((entity, random) -> new TradeOffers.SellEnchantedToolFactory(
                    ModItems.ADAMANTIUM_AXE,
                    12, 3, 15, 0.2F
            ).create(entity, random));
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.WEAPONSMITH, 5, factories -> {
            factories.add((entity, random) -> new TradeOffers.SellEnchantedToolFactory(
                    ModItems.ADAMANTIUM_SWORD,
                    8, 3, 30, 0.2F
            ).create(entity, random));
        });
    }
}
