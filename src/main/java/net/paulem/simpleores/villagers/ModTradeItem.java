package net.paulem.simpleores.villagers;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
//? afterDeobf {
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.trading.TradeCost;
//? }
//? if >1.20.5 && !afterDeobf
//import net.minecraft.world.item.trading.ItemCost;

public record ModTradeItem(Item item, int count) {
    public ModTradeItem(Item item) {
        this(item, 1);
    }

    public //? afterDeobf {
    ItemStackTemplate
    //? } else {
    /*ItemStack
    *///? }
        getGives() {
        //? afterDeobf {
        return new ItemStackTemplate(item, count);
        //? } else {
            /*return new ItemStack(item, count);
        *///? }
    }

    public //? afterDeobf {
    TradeCost
    //? } else {
        /*//? if >1.20.5 {
        ItemCost
        //? } else {
        //ItemStack
        //? }
    *///? }
    getWants() {
        //? afterDeobf {
        return new TradeCost(item, count);
        //? } else {
            /*//? if >1.20.5 {
            return new ItemCost(item, count);
            //? } else {
            //return new ItemStack(item, count);
            //? }
        *///? }
    }
}

