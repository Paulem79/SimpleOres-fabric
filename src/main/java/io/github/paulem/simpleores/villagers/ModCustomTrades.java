package io.github.paulem.simpleores.villagers;

import io.github.paulem.simpleores.SimpleOres;
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
            if(SimpleOres.CONFIG.armorerEmeraldCopperHelmet) factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 3),
                    new ItemStack(ModItems.COPPER_HELMET),
                    12, 1, 0.2F
            ));

            if(SimpleOres.CONFIG.armorerEmeraldCopperChestplate) factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 7),
                    new ItemStack(ModItems.COPPER_CHESTPLATE),
                    12, 1, 0.2F
            ));

            if(SimpleOres.CONFIG.armorerEmeraldCopperLeggings) factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 5),
                    new ItemStack(ModItems.COPPER_LEGGINGS),
                    12, 1, 0.2F
            ));

            if(SimpleOres.CONFIG.armorerEmeraldCopperBoots) factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 2),
                    new ItemStack(ModItems.COPPER_BOOTS),
                    12, 1, 0.2F
            ));
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.ARMORER, 2, factories -> {
            if(SimpleOres.CONFIG.armorerCopperToEmeralds) factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.COPPER_INGOT, 4),
                    new ItemStack(Items.EMERALD),
                    12, 10, 0.05F
            ));

            if(SimpleOres.CONFIG.armorerTinToEmeralds) factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(ModItems.TIN_INGOT, 4),
                    new ItemStack(Items.EMERALD),
                    12, 10, 0.05F
            ));

            if(SimpleOres.CONFIG.armorerEmeraldTinLeggings) factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 3),
                    new ItemStack(ModItems.TIN_LEGGINGS),
                    12, 5, 0.2F
            ));

            if(SimpleOres.CONFIG.armorerEmeraldTinBoots) factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD),
                    new ItemStack(ModItems.TIN_BOOTS),
                    12, 5, 0.2F
            ));
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.ARMORER, 3, factories -> {
            if(SimpleOres.CONFIG.armorerMythrilToEmeralds) factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(ModItems.MYTHRIL_INGOT),
                    new ItemStack(Items.EMERALD),
                    12, 20, 0.05F
            ));

            if(SimpleOres.CONFIG.armorerEmeraldTinHelmet) factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD),
                    new ItemStack(ModItems.TIN_HELMET),
                    12, 10, 0.2F
            ));

            if(SimpleOres.CONFIG.armorerEmeraldTinChestplate) factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 3),
                    new ItemStack(ModItems.TIN_CHESTPLATE),
                    12, 10, 0.2F
            ));
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.ARMORER, 4, factories -> {
            if(SimpleOres.CONFIG.armorerEmeraldMythrilLeggingsEnchanted) factories.add((entity, random) -> new TradeOffers.SellEnchantedToolFactory(
                    ModItems.MYTHRIL_LEGGINGS,
                    14, 3, 15, 0.2F
            ).create(entity, random));

            if(SimpleOres.CONFIG.armorerEmeraldMythrilBootsEnchanted) factories.add((entity, random) -> new TradeOffers.SellEnchantedToolFactory(
                    ModItems.MYTHRIL_BOOTS,
                    8, 3, 15, 0.2F
            ).create(entity, random));
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.ARMORER, 5, factories -> {
            if(SimpleOres.CONFIG.armorerEmeraldMythrilHelmetEnchanted) factories.add((entity, random) -> new TradeOffers.SellEnchantedToolFactory(
                    ModItems.MYTHRIL_HELMET,
                    8, 3, 30, 0.2F
            ).create(entity, random));

            if(SimpleOres.CONFIG.armorerEmeraldMythrilChestplateEnchanted) factories.add((entity, random) -> new TradeOffers.SellEnchantedToolFactory(
                    ModItems.MYTHRIL_CHESTPLATE,
                    16, 3, 30, 0.2F
            ).create(entity, random));
        });

        // TOOLSMITH
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.TOOLSMITH, 2, factories -> {
            if(SimpleOres.CONFIG.toolsmithCopperToEmeralds) factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.COPPER_INGOT, 4),
                    new ItemStack(Items.EMERALD),
                    12, 10, 0.05F
            ));

            if(SimpleOres.CONFIG.toolsmithTinToEmeralds) factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(ModItems.TIN_INGOT, 4),
                    new ItemStack(Items.EMERALD),
                    12, 10, 0.05F
            ));

            if(SimpleOres.CONFIG.toolsmithEmeraldCopperAxe) factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD),
                    new ItemStack(ModItems.COPPER_AXE),
                    12, 1, 0.2F
            ));

            if(SimpleOres.CONFIG.toolsmithEmeraldCopperShovel) factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD),
                    new ItemStack(ModItems.COPPER_SHOVEL),
                    12, 1, 0.2F
            ));

            if(SimpleOres.CONFIG.toolsmithEmeraldCopperHoe) factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD),
                    new ItemStack(ModItems.COPPER_HOE),
                    12, 1, 0.2F
            ));

            if(SimpleOres.CONFIG.toolsmithEmeraldCopperPickaxe) factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD),
                    new ItemStack(ModItems.COPPER_PICKAXE),
                    12, 1, 0.2F
            ));

            if(SimpleOres.CONFIG.toolsmithEmeraldTinAxe) factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD),
                    new ItemStack(ModItems.TIN_AXE),
                    12, 1, 0.2F
            ));

            if(SimpleOres.CONFIG.toolsmithEmeraldTinShovel) factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD),
                    new ItemStack(ModItems.TIN_SHOVEL),
                    12, 1, 0.2F
            ));

            if(SimpleOres.CONFIG.toolsmithEmeraldTinHoe) factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD),
                    new ItemStack(ModItems.TIN_HOE),
                    12, 1, 0.2F
            ));

            if(SimpleOres.CONFIG.toolsmithEmeraldTinPickaxe) factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD),
                    new ItemStack(ModItems.TIN_PICKAXE),
                    12, 1, 0.2F
            ));
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.TOOLSMITH, 3, factories -> {
            if(SimpleOres.CONFIG.toolsmithMythrilToEmerald) factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(ModItems.MYTHRIL_INGOT),
                    new ItemStack(Items.EMERALD),
                    12, 20, 0.05F
            ));

            if(SimpleOres.CONFIG.toolsmithEmeraldMythrilAxeEnchantedLvl3) factories.add((entity, random) -> new TradeOffers.SellEnchantedToolFactory(
                    ModItems.MYTHRIL_AXE,
                    2, 3, 10, 0.2F
            ).create(entity, random));

            if(SimpleOres.CONFIG.toolsmithEmeraldMythrilShovelEnchantedLvl3) factories.add((entity, random) -> new TradeOffers.SellEnchantedToolFactory(
                    ModItems.MYTHRIL_SHOVEL,
                    3, 3, 10, 0.2F
            ).create(entity, random));

            if(SimpleOres.CONFIG.toolsmithEmeraldMythrilPickaxeEnchantedLvl3) factories.add((entity, random) -> new TradeOffers.SellEnchantedToolFactory(
                    ModItems.MYTHRIL_PICKAXE,
                    3, 3, 10, 0.2F
            ).create(entity, random));

            if(SimpleOres.CONFIG.toolsmithEmeraldMythrilHoe) factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD),
                    new ItemStack(ModItems.MYTHRIL_HOE),
                    3, 10, 0.2F
            ));
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.TOOLSMITH, 4, factories -> {
            if(SimpleOres.CONFIG.toolsmithAdamantiumToEmerald) factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(ModItems.ADAMANTIUM_INGOT),
                    new ItemStack(Items.EMERALD, 2),
                    12, 30, 0.05F
            ));

            if(SimpleOres.CONFIG.toolsmithEmeraldAdamantiumAxeEnchanted) factories.add((entity, random) -> new TradeOffers.SellEnchantedToolFactory(
                    ModItems.ADAMANTIUM_AXE,
                    12, 3, 15, 0.2F
            ).create(entity, random));

            if(SimpleOres.CONFIG.toolsmithEmeraldMythrilShovelEnchanted) factories.add((entity, random) -> new TradeOffers.SellEnchantedToolFactory(
                    ModItems.ADAMANTIUM_SHOVEL,
                    5, 3, 15, 0.2F
            ).create(entity, random));
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.TOOLSMITH, 5, factories -> {
            if(SimpleOres.CONFIG.toolsmithEmeraldMythrilPickaxeEnchanted) factories.add((entity, random) -> new TradeOffers.SellEnchantedToolFactory(
                    ModItems.ADAMANTIUM_PICKAXE,
                    13, 3, 30, 0.2F
            ).create(entity, random));
        });

        // WEAPONSMITH
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.WEAPONSMITH, 1, factories -> {
            if(SimpleOres.CONFIG.weaponsmithEmeraldMythrilAxe) factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.EMERALD, 3),
                    new ItemStack(ModItems.MYTHRIL_AXE),
                    12, 1, 0.2F
            ));

            if(SimpleOres.CONFIG.weaponsmithEmeraldMythrilSwordEnchanted) factories.add((entity, random) -> new TradeOffers.SellEnchantedToolFactory(
                    ModItems.MYTHRIL_SWORD,
                    2, 3, 1, 0.05F
            ).create(entity, random));
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.WEAPONSMITH, 2, factories -> {
            if(SimpleOres.CONFIG.weaponsmithCopperToEmerald) factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(Items.COPPER_INGOT, 4),
                    new ItemStack(Items.EMERALD),
                    12, 10, 0.05F
            ));

            if(SimpleOres.CONFIG.weaponsmithTinToEmerald) factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(ModItems.TIN_INGOT, 4),
                    new ItemStack(Items.EMERALD),
                    12, 10, 0.05F
            ));
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.WEAPONSMITH, 3, factories -> {
            if(SimpleOres.CONFIG.weaponsmithMythrilToEmerald) factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(ModItems.MYTHRIL_INGOT),
                    new ItemStack(Items.EMERALD),
                    12, 20, 0.05F
            ));
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.WEAPONSMITH, 4, factories -> {
            if(SimpleOres.CONFIG.weaponsmithAdamantiumToEmerald) factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(ModItems.ADAMANTIUM_INGOT),
                    new ItemStack(Items.EMERALD),
                    12, 30, 0.05F
            ));

            if(SimpleOres.CONFIG.weaponsmithEmeraldAdamantiumAxeEnchanted) factories.add((entity, random) -> new TradeOffers.SellEnchantedToolFactory(
                    ModItems.ADAMANTIUM_AXE,
                    12, 3, 15, 0.2F
            ).create(entity, random));
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.WEAPONSMITH, 5, factories -> {
            if(SimpleOres.CONFIG.weaponsmithEmeraldAdamantiumSwordEnchanted) factories.add((entity, random) -> new TradeOffers.SellEnchantedToolFactory(
                    ModItems.ADAMANTIUM_SWORD,
                    8, 3, 30, 0.2F
            ).create(entity, random));
        });
    }
}
