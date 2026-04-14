package net.paulem.simpleores.villagers;

import net.minecraft.world.entity.npc.villager.VillagerProfession;
//? if afterDeobf {
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.trading.VillagerTrade;
//? }
import net.minecraft.world.item.Items;
import net.paulem.simpleores.items.ModItems;

import java.util.*;

public class ModVillagersTrades {
    public static final List<TradeDefinition> TRADE_DEFINITIONS = new ArrayList<>();
    public static ModTrades INSTANCE;

    public static void generateFromBootstrap(//? afterDeobf
            final BootstrapContext<VillagerTrade> context
    ) {
        INSTANCE = new ModTrades(//? afterDeobf
                context
        );

        //? if !hasCopperTools {
        /*// ARMORER - level 1: emerald -> copper armor pieces
        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.ARMORER, 1, "copper_helmet_emerald",
                new ModTradeItem(Items.EMERALD, 3), new ModTradeItem(ModItems.COPPER_HELMET),
                12, 1, 0.2F
        ));

        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.ARMORER, 1, "copper_chestplate_emerald",
                new ModTradeItem(Items.EMERALD, 7), new ModTradeItem(ModItems.COPPER_CHESTPLATE),
                12, 1, 0.2F
        ));

        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.ARMORER, 1, "copper_leggings_emerald",
                new ModTradeItem(Items.EMERALD, 5), new ModTradeItem(ModItems.COPPER_LEGGINGS),
                12, 1, 0.2F
        ));

        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.ARMORER, 1, "copper_boots_emerald",
                new ModTradeItem(Items.EMERALD, 2), new ModTradeItem(ModItems.COPPER_BOOTS),
                12, 1, 0.2F
        ));
        *///? }

        // ARMORER - level 2: ingots -> emerald
        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.ARMORER, 2, "copper_ingot_to_emerald",
                new ModTradeItem(Items.COPPER_INGOT, 4), new ModTradeItem(Items.EMERALD),
                12, 10, 0.05F
        ));

        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.ARMORER, 2, "tin_ingot_to_emerald",
                new ModTradeItem(ModItems.TIN_INGOT, 4), new ModTradeItem(Items.EMERALD),
                12, 10, 0.05F
        ));

        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.ARMORER, 2, "tin_leggings_emerald",
                new ModTradeItem(Items.EMERALD, 3), new ModTradeItem(ModItems.TIN_LEGGINGS),
                12, 5, 0.2F
        ));

        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.ARMORER, 2, "tin_boots_emerald",
                new ModTradeItem(Items.EMERALD, 1), new ModTradeItem(ModItems.TIN_BOOTS),
                12, 5, 0.2F
        ));

        // ARMORER - level 3: mythril -> emerald and tin armor
        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.ARMORER, 3, "mythril_ingot_to_emerald",
                new ModTradeItem(ModItems.MYTHRIL_INGOT), new ModTradeItem(Items.EMERALD),
                12, 20, 0.05F
        ));

        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.ARMORER, 3, "tin_helmet_emerald",
                new ModTradeItem(Items.EMERALD, 1), new ModTradeItem(ModItems.TIN_HELMET),
                12, 10, 0.2F
        ));

        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.ARMORER, 3, "tin_chestplate_emerald",
                new ModTradeItem(Items.EMERALD, 3), new ModTradeItem(ModItems.TIN_CHESTPLATE),
                12, 10, 0.2F
        ));

        // ARMORER - level 4: enchanted mythril leggings/boots
        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.ARMORER, 4, "mythril_leggings_enchanted",
                new ModTradeItem(Items.EMERALD, 14), new ModTradeItem(ModItems.MYTHRIL_LEGGINGS),
                15, 3, 0.2F, true
        ));

        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.ARMORER, 4, "mythril_boots_enchanted",
                new ModTradeItem(Items.EMERALD, 8), new ModTradeItem(ModItems.MYTHRIL_BOOTS),
                15, 3, 0.2F, true
        ));

        // ARMORER - level 5: enchanted mythril helmet/chestplate
        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.ARMORER, 5, "mythril_helmet_enchanted",
                new ModTradeItem(Items.EMERALD, 8), new ModTradeItem(ModItems.MYTHRIL_HELMET),
                30, 3, 0.2F, true
        ));

        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.ARMORER, 5, "mythril_chestplate_enchanted",
                new ModTradeItem(Items.EMERALD, 16), new ModTradeItem(ModItems.MYTHRIL_CHESTPLATE),
                30, 3, 0.2F, true
        ));

        // TOOLSMITH - level 2: ingots -> emerald and tool offers
        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.TOOLSMITH, 2, "copper_ingot_to_emerald",
                new ModTradeItem(Items.COPPER_INGOT, 4), new ModTradeItem(Items.EMERALD),
                12, 10, 0.05F
        ));

        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.TOOLSMITH, 2, "tin_ingot_to_emerald",
                new ModTradeItem(ModItems.TIN_INGOT, 4), new ModTradeItem(Items.EMERALD),
                12, 10, 0.05F
        ));

        //? if !hasCopperTools {
        /*// Copper tool offers (TOOLSMITH level 2)
        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.TOOLSMITH, 2, "copper_axe_emerald",
                new ModTradeItem(Items.EMERALD, 1), new ModTradeItem(ModItems.COPPER_AXE),
                12, 1, 0.2F
        ));

        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.TOOLSMITH, 2, "copper_shovel_emerald",
                new ModTradeItem(Items.EMERALD, 1), new ModTradeItem(ModItems.COPPER_SHOVEL),
                12, 1, 0.2F
        ));

        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.TOOLSMITH, 2, "copper_hoe_emerald",
                new ModTradeItem(Items.EMERALD, 1), new ModTradeItem(ModItems.COPPER_HOE),
                12, 1, 0.2F
        ));

        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.TOOLSMITH, 2, "copper_pickaxe_emerald",
                new ModTradeItem(Items.EMERALD, 1), new ModTradeItem(ModItems.COPPER_PICKAXE),
                12, 1, 0.2F
        ));
        *///? }

        // tin tools
        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.TOOLSMITH, 2, "tin_axe_emerald",
                new ModTradeItem(Items.EMERALD, 1), new ModTradeItem(ModItems.TIN_AXE),
                12, 1, 0.2F
        ));

        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.TOOLSMITH, 2, "tin_shovel_emerald",
                new ModTradeItem(Items.EMERALD, 1), new ModTradeItem(ModItems.TIN_SHOVEL),
                12, 1, 0.2F
        ));

        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.TOOLSMITH, 2, "tin_hoe_emerald",
                new ModTradeItem(Items.EMERALD, 1), new ModTradeItem(ModItems.TIN_HOE),
                12, 1, 0.2F
        ));

        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.TOOLSMITH, 2, "tin_pickaxe_emerald",
                new ModTradeItem(Items.EMERALD, 1), new ModTradeItem(ModItems.TIN_PICKAXE),
                12, 1, 0.2F
        ));

        // TOOLSMITH - level 3
        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.TOOLSMITH, 3, "mythril_ingot_to_emerald",
                new ModTradeItem(ModItems.MYTHRIL_INGOT), new ModTradeItem(Items.EMERALD),
                12, 20, 0.05F
        ));

        // enchanted mythril tools level 3
        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.TOOLSMITH, 3, "mythril_axe_enchanted_lvl3",
                new ModTradeItem(Items.EMERALD, 2), new ModTradeItem(ModItems.MYTHRIL_AXE),
                10, 3, 0.2F, true
        ));

        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.TOOLSMITH, 3, "mythril_shovel_enchanted_lvl3",
                new ModTradeItem(Items.EMERALD, 3), new ModTradeItem(ModItems.MYTHRIL_SHOVEL),
                10, 3, 0.2F, true
        ));

        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.TOOLSMITH, 3, "mythril_pickaxe_enchanted_lvl3",
                new ModTradeItem(Items.EMERALD, 3), new ModTradeItem(ModItems.MYTHRIL_PICKAXE),
                10, 3, 0.2F, true
        ));

        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.TOOLSMITH, 3, "mythril_hoe_emerald",
                new ModTradeItem(Items.EMERALD, 1), new ModTradeItem(ModItems.MYTHRIL_HOE),
                3, 10, 0.2F
        ));

        // TOOLSMITH - level 4
        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.TOOLSMITH, 4, "adamantium_ingot_to_emerald",
                new ModTradeItem(ModItems.ADAMANTIUM_INGOT), new ModTradeItem(Items.EMERALD, 2),
                12, 30, 0.05F
        ));

        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.TOOLSMITH, 4, "adamantium_axe_enchanted",
                new ModTradeItem(Items.EMERALD, 12), new ModTradeItem(ModItems.ADAMANTIUM_AXE),
                15, 3, 0.2F, true
        ));

        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.TOOLSMITH, 4, "adamantium_shovel_enchanted",
                new ModTradeItem(Items.EMERALD, 5), new ModTradeItem(ModItems.ADAMANTIUM_SHOVEL),
                15, 3, 0.2F, true
        ));

        // TOOLSMITH - level 5
        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.TOOLSMITH, 5, "adamantium_pickaxe_enchanted",
                new ModTradeItem(Items.EMERALD, 13), new ModTradeItem(ModItems.ADAMANTIUM_PICKAXE),
                30, 3, 0.2F, true
        ));

        // WEAPONSMITH - level 1
        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.WEAPONSMITH, 1, "mythril_axe_emerald",
                new ModTradeItem(Items.EMERALD, 3), new ModTradeItem(ModItems.MYTHRIL_AXE),
                12, 1, 0.2F
        ));

        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.WEAPONSMITH, 1, "mythril_sword_enchanted",
                new ModTradeItem(Items.EMERALD, 2), new ModTradeItem(ModItems.MYTHRIL_SWORD),
                1, 3, 0.05F, true
        ));

        // WEAPONSMITH - level 2
        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.WEAPONSMITH, 2, "copper_ingot_to_emerald",
                new ModTradeItem(Items.COPPER_INGOT, 4), new ModTradeItem(Items.EMERALD),
                12, 10, 0.05F
        ));

        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.WEAPONSMITH, 2, "tin_ingot_to_emerald",
                new ModTradeItem(ModItems.TIN_INGOT, 4), new ModTradeItem(Items.EMERALD),
                12, 10, 0.05F
        ));

        // WEAPONSMITH - level 3
        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.WEAPONSMITH, 3, "mythril_ingot_to_emerald",
                new ModTradeItem(ModItems.MYTHRIL_INGOT), new ModTradeItem(Items.EMERALD),
                12, 20, 0.05F
        ));

        // WEAPONSMITH - level 4
        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.WEAPONSMITH, 4, "adamantium_ingot_to_emerald",
                new ModTradeItem(ModItems.ADAMANTIUM_INGOT), new ModTradeItem(Items.EMERALD),
                12, 30, 0.05F
        ));

        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.WEAPONSMITH, 4, "adamantium_axe_enchanted",
                new ModTradeItem(Items.EMERALD, 12), new ModTradeItem(ModItems.ADAMANTIUM_AXE),
                15, 3, 0.2F, true
        ));

        // WEAPONSMITH - level 5
        TRADE_DEFINITIONS.add(INSTANCE.createDefinition(
                VillagerProfession.WEAPONSMITH, 5, "adamantium_sword_enchanted",
                new ModTradeItem(Items.EMERALD, 8), new ModTradeItem(ModItems.ADAMANTIUM_SWORD),
                30, 3, 0.2F, true
        ));
    }
}