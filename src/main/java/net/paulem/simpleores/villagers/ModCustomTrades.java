package net.paulem.simpleores.villagers;

// TODO: Merge trades with TradesProvider
//? if >21.6 {
public class ModCustomTrades {public static void registerCustomTrades() {}}
//?} else {

/*import net.paulem.simpleores.SimpleOres;
import net.paulem.simpleores.items.ModItems;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.entity.npc.villager.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
//? if >1.20.5
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;

public class ModCustomTrades {
    public static void registerCustomTrades() {
        // ARMORER
        //? if !hasCopperTools {
        /^TradeOfferHelper.registerVillagerOffers(VillagerProfession.ARMORER, 1, factories -> {
            if(SimpleOres.CONFIG.armorerEmeraldCopperHelmet()) factories.add((level, entity, random) -> new MerchantOffer(
                    new SCTradedItem(Items.EMERALD, 3).get(),
                    new ItemStack(ModItems.COPPER_HELMET),
                    12, 1, 0.2F
            ));

            if(SimpleOres.CONFIG.armorerEmeraldCopperChestplate()) factories.add((level, entity, random) -> new MerchantOffer(
                    new SCTradedItem(Items.EMERALD, 7).get(),
                    new ItemStack(ModItems.COPPER_CHESTPLATE),
                    12, 1, 0.2F
            ));

            if(SimpleOres.CONFIG.armorerEmeraldCopperLeggings()) factories.add((level, entity, random) -> new MerchantOffer(
                    new SCTradedItem(Items.EMERALD, 5).get(),
                    new ItemStack(ModItems.COPPER_LEGGINGS),
                    12, 1, 0.2F
            ));

            if(SimpleOres.CONFIG.armorerEmeraldCopperBoots()) factories.add((level, entity, random) -> new MerchantOffer(
                    new SCTradedItem(Items.EMERALD, 2).get(),
                    new ItemStack(ModItems.COPPER_BOOTS),
                    12, 1, 0.2F
            ));
        });
        ^///?}

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.ARMORER, 2, factories -> {
            if(SimpleOres.CONFIG.armorerCopperToEmeralds()) factories.add((level, entity, random) -> new MerchantOffer(
                    new SCTradedItem(Items.COPPER_INGOT, 4).get(),
                    new ItemStack(Items.EMERALD),
                    12, 10, 0.05F
            ));

            if(SimpleOres.CONFIG.armorerTinToEmeralds()) factories.add((level, entity, random) -> new MerchantOffer(
                    new SCTradedItem(ModItems.TIN_INGOT, 4).get(),
                    new ItemStack(Items.EMERALD),
                    12, 10, 0.05F
            ));

            if(SimpleOres.CONFIG.armorerEmeraldTinLeggings()) factories.add((level, entity, random) -> new MerchantOffer(
                    new SCTradedItem(Items.EMERALD, 3).get(),
                    new ItemStack(ModItems.TIN_LEGGINGS),
                    12, 5, 0.2F
            ));

            if(SimpleOres.CONFIG.armorerEmeraldTinBoots()) factories.add((level, entity, random) -> new MerchantOffer(
                    new SCTradedItem(Items.EMERALD).get(),
                    new ItemStack(ModItems.TIN_BOOTS),
                    12, 5, 0.2F
            ));
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.ARMORER, 3, factories -> {
            if(SimpleOres.CONFIG.armorerMythrilToEmeralds()) factories.add((level, entity, random) -> new MerchantOffer(
                    new SCTradedItem(ModItems.MYTHRIL_INGOT).get(),
                    new ItemStack(Items.EMERALD),
                    12, 20, 0.05F
            ));

            if(SimpleOres.CONFIG.armorerEmeraldTinHelmet()) factories.add((level, entity, random) -> new MerchantOffer(
                    new SCTradedItem(Items.EMERALD).get(),
                    new ItemStack(ModItems.TIN_HELMET),
                    12, 10, 0.2F
            ));

            if(SimpleOres.CONFIG.armorerEmeraldTinChestplate()) factories.add((level, entity, random) -> new MerchantOffer(
                    new SCTradedItem(Items.EMERALD, 3).get(),
                    new ItemStack(ModItems.TIN_CHESTPLATE),
                    12, 10, 0.2F
            ));
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.ARMORER, 4, factories -> {
            if(SimpleOres.CONFIG.armorerEmeraldMythrilLeggingsEnchanted()) factories.add((level, entity, random) -> new VillagerTrades.EnchantedItemForEmeralds(
                    ModItems.MYTHRIL_LEGGINGS,
                    14, 3, 15, 0.2F
            ).getOffer(level, entity, random));

            if(SimpleOres.CONFIG.armorerEmeraldMythrilBootsEnchanted()) factories.add((level, entity, random) -> new VillagerTrades.EnchantedItemForEmeralds(
                    ModItems.MYTHRIL_BOOTS,
                    8, 3, 15, 0.2F
            ).getOffer(level, entity, random));
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.ARMORER, 5, factories -> {
            if(SimpleOres.CONFIG.armorerEmeraldMythrilHelmetEnchanted()) factories.add((level, entity, random) -> new VillagerTrades.EnchantedItemForEmeralds(
                    ModItems.MYTHRIL_HELMET,
                    8, 3, 30, 0.2F
            ).getOffer(level, entity, random));

            if(SimpleOres.CONFIG.armorerEmeraldMythrilChestplateEnchanted()) factories.add((level, entity, random) -> new VillagerTrades.EnchantedItemForEmeralds(
                    ModItems.MYTHRIL_CHESTPLATE,
                    16, 3, 30, 0.2F
            ).getOffer(level, entity, random));
        });

        // TOOLSMITH
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.TOOLSMITH, 2, factories -> {
            if(SimpleOres.CONFIG.toolsmithCopperToEmeralds()) factories.add((level, entity, random) -> new MerchantOffer(
                    new SCTradedItem(Items.COPPER_INGOT, 4).get(),
                    new ItemStack(Items.EMERALD),
                    12, 10, 0.05F
            ));

            if(SimpleOres.CONFIG.toolsmithTinToEmeralds()) factories.add((level, entity, random) -> new MerchantOffer(
                    new SCTradedItem(ModItems.TIN_INGOT, 4).get(),
                    new ItemStack(Items.EMERALD),
                    12, 10, 0.05F
            ));

            //? if !hasCopperTools {
            /^if(SimpleOres.CONFIG.toolsmithEmeraldCopperAxe()) factories.add((level, entity, random) -> new MerchantOffer(
                    new SCTradedItem(Items.EMERALD).get(),
                    new ItemStack(ModItems.COPPER_AXE),
                    12, 1, 0.2F
            ));

            if(SimpleOres.CONFIG.toolsmithEmeraldCopperShovel()) factories.add((level, entity, random) -> new MerchantOffer(
                    new SCTradedItem(Items.EMERALD).get(),
                    new ItemStack(ModItems.COPPER_SHOVEL),
                    12, 1, 0.2F
            ));

            if(SimpleOres.CONFIG.toolsmithEmeraldCopperHoe()) factories.add((level, entity, random) -> new MerchantOffer(
                    new SCTradedItem(Items.EMERALD).get(),
                    new ItemStack(ModItems.COPPER_HOE),
                    12, 1, 0.2F
            ));

            if(SimpleOres.CONFIG.toolsmithEmeraldCopperPickaxe()) factories.add((level, entity, random) -> new MerchantOffer(
                    new SCTradedItem(Items.EMERALD).get(),
                    new ItemStack(ModItems.COPPER_PICKAXE),
                    12, 1, 0.2F
            ));
            ^///?}

            if(SimpleOres.CONFIG.toolsmithEmeraldTinAxe()) factories.add((level, entity, random) -> new MerchantOffer(
                    new SCTradedItem(Items.EMERALD).get(),
                    new ItemStack(ModItems.TIN_AXE),
                    12, 1, 0.2F
            ));

            if(SimpleOres.CONFIG.toolsmithEmeraldTinShovel()) factories.add((level, entity, random) -> new MerchantOffer(
                    new SCTradedItem(Items.EMERALD).get(),
                    new ItemStack(ModItems.TIN_SHOVEL),
                    12, 1, 0.2F
            ));

            if(SimpleOres.CONFIG.toolsmithEmeraldTinHoe()) factories.add((level, entity, random) -> new MerchantOffer(
                    new SCTradedItem(Items.EMERALD).get(),
                    new ItemStack(ModItems.TIN_HOE),
                    12, 1, 0.2F
            ));

            if(SimpleOres.CONFIG.toolsmithEmeraldTinPickaxe()) factories.add((level, entity, random) -> new MerchantOffer(
                    new SCTradedItem(Items.EMERALD).get(),
                    new ItemStack(ModItems.TIN_PICKAXE),
                    12, 1, 0.2F
            ));
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.TOOLSMITH, 3, factories -> {
            if(SimpleOres.CONFIG.toolsmithMythrilToEmerald()) factories.add((level, entity, random) -> new MerchantOffer(
                    new SCTradedItem(ModItems.MYTHRIL_INGOT).get(),
                    new ItemStack(Items.EMERALD),
                    12, 20, 0.05F
            ));

            if(SimpleOres.CONFIG.toolsmithEmeraldMythrilAxeEnchantedLvl3()) factories.add((level, entity, random) -> new VillagerTrades.EnchantedItemForEmeralds(
                    ModItems.MYTHRIL_AXE,
                    2, 3, 10, 0.2F
            ).getOffer(level, entity, random));

            if(SimpleOres.CONFIG.toolsmithEmeraldMythrilShovelEnchantedLvl3()) factories.add((level, entity, random) -> new VillagerTrades.EnchantedItemForEmeralds(
                    ModItems.MYTHRIL_SHOVEL,
                    3, 3, 10, 0.2F
            ).getOffer(level, entity, random));

            if(SimpleOres.CONFIG.toolsmithEmeraldMythrilPickaxeEnchantedLvl3()) factories.add((level, entity, random) -> new VillagerTrades.EnchantedItemForEmeralds(
                    ModItems.MYTHRIL_PICKAXE,
                    3, 3, 10, 0.2F
            ).getOffer(level, entity, random));

            if(SimpleOres.CONFIG.toolsmithEmeraldMythrilHoe()) factories.add((level, entity, random) -> new MerchantOffer(
                    new SCTradedItem(Items.EMERALD).get(),
                    new ItemStack(ModItems.MYTHRIL_HOE),
                    3, 10, 0.2F
            ));
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.TOOLSMITH, 4, factories -> {
            if(SimpleOres.CONFIG.toolsmithAdamantiumToEmerald()) factories.add((level, entity, random) -> new MerchantOffer(
                    new SCTradedItem(ModItems.ADAMANTIUM_INGOT).get(),
                    new ItemStack(Items.EMERALD, 2),
                    12, 30, 0.05F
            ));

            if(SimpleOres.CONFIG.toolsmithEmeraldAdamantiumAxeEnchanted()) factories.add((level, entity, random) -> new VillagerTrades.EnchantedItemForEmeralds(
                    ModItems.ADAMANTIUM_AXE,
                    12, 3, 15, 0.2F
            ).getOffer(level, entity, random));

            if(SimpleOres.CONFIG.toolsmithEmeraldMythrilShovelEnchanted()) factories.add((level, entity, random) -> new VillagerTrades.EnchantedItemForEmeralds(
                    ModItems.ADAMANTIUM_SHOVEL,
                    5, 3, 15, 0.2F
            ).getOffer(level, entity, random));
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.TOOLSMITH, 5, factories -> {
            if(SimpleOres.CONFIG.toolsmithEmeraldMythrilPickaxeEnchanted()) factories.add((level, entity, random) -> new VillagerTrades.EnchantedItemForEmeralds(
                    ModItems.ADAMANTIUM_PICKAXE,
                    13, 3, 30, 0.2F
            ).getOffer(level, entity, random));
        });

            // WEAPONSMITH
            TradeOfferHelper.registerVillagerOffers(VillagerProfession.WEAPONSMITH, 1, factories -> {
                if(SimpleOres.CONFIG.weaponsmithEmeraldMythrilAxe()) factories.add((level, entity, random) -> new MerchantOffer(
                        new SCTradedItem(Items.EMERALD, 3).get(),
                        new ItemStack(ModItems.MYTHRIL_AXE),
                        12, 1, 0.2F
                ));

                if(SimpleOres.CONFIG.weaponsmithEmeraldMythrilSwordEnchanted()) factories.add((level, entity, random) -> new VillagerTrades.EnchantedItemForEmeralds(
                        ModItems.MYTHRIL_SWORD,
                        2, 3, 1, 0.05F
                ).getOffer(level, entity, random));
            });

            TradeOfferHelper.registerVillagerOffers(VillagerProfession.WEAPONSMITH, 2, factories -> {
                if(SimpleOres.CONFIG.weaponsmithCopperToEmerald()) factories.add((level, entity, random) -> new MerchantOffer(
                        new SCTradedItem(Items.COPPER_INGOT, 4).get(),
                        new ItemStack(Items.EMERALD),
                        12, 10, 0.05F
            ));

                if(SimpleOres.CONFIG.weaponsmithTinToEmerald()) factories.add((level, entity, random) -> new MerchantOffer(
                        new SCTradedItem(ModItems.TIN_INGOT, 4).get(),
                        new ItemStack(Items.EMERALD),
                        12, 10, 0.05F
                ));
            });

            TradeOfferHelper.registerVillagerOffers(VillagerProfession.WEAPONSMITH, 3, factories -> {
                if(SimpleOres.CONFIG.weaponsmithMythrilToEmerald()) factories.add((level, entity, random) -> new MerchantOffer(
                        new SCTradedItem(ModItems.MYTHRIL_INGOT).get(),
                        new ItemStack(Items.EMERALD),
                        12, 20, 0.05F
            ));
            });

            TradeOfferHelper.registerVillagerOffers(VillagerProfession.WEAPONSMITH, 4, factories -> {
                if(SimpleOres.CONFIG.weaponsmithAdamantiumToEmerald()) factories.add((level, entity, random) -> new MerchantOffer(
                        new SCTradedItem(ModItems.ADAMANTIUM_INGOT).get(),
                        new ItemStack(Items.EMERALD),
                        12, 30, 0.05F
            ));

                if(SimpleOres.CONFIG.weaponsmithEmeraldAdamantiumAxeEnchanted()) factories.add((level, entity, random) -> new VillagerTrades.EnchantedItemForEmeralds(
                        ModItems.ADAMANTIUM_AXE,
                        12, 3, 15, 0.2F
                ).getOffer(level, entity, random));
            });

            TradeOfferHelper.registerVillagerOffers(VillagerProfession.WEAPONSMITH, 5, factories -> {
                if(SimpleOres.CONFIG.weaponsmithEmeraldAdamantiumSwordEnchanted()) factories.add((level, entity, random) -> new VillagerTrades.EnchantedItemForEmeralds(
                        ModItems.ADAMANTIUM_SWORD,
                        8, 3, 30, 0.2F
                ).getOffer(level, entity, random));
            });
    }

    record SCTradedItem(
            Item item,
            int count
    ) {
        public SCTradedItem(Item item) {
            this(item, 1);
        }

        public
        //? if >1.20.5 {
            ItemCost
        //?} else {
         /^ItemStack
        ^///?}
        get() {
            //? if >1.20.5 {
            return new ItemCost(item, count);
            //?} else {
             /^return new ItemStack(item, count);
            ^///?}
        }
    }
}
*///?}