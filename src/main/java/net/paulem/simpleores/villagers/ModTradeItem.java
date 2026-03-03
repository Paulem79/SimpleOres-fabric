package net.paulem.simpleores.villagers;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public record ModTradeItem(Item item, int count) {
    public ModTradeItem(Item item) {
        this(item, 1);
    }

    public
    ItemStack
        getGives() {
            return new ItemStack(item, count);
    }

    public
        ItemStack
    getWants() {
            return new ItemStack(item, count);
    }
}

