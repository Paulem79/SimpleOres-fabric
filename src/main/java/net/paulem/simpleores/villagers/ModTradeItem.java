package net.paulem.simpleores.villagers;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.trading.TradeCost;

public record ModTradeItem(Item item, int count) {
    public ModTradeItem(Item item) {
        this(item, 1);
    }

    public //? afterDeobf {
    ItemStackTemplate
    //? } else {
    //ItemStack
    //? }
    getTo() {
        //? afterDeobf {
        return new ItemStackTemplate(item, count);
        //? } else {
        //return itemStack;
        //? }
    }

    public TradeCost getFrom() {
        return new TradeCost(item, count);
    }
}

