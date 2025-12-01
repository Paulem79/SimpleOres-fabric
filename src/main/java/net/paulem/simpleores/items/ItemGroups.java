package net.paulem.simpleores.items;

import net.paulem.simpleores.SimpleOres;
import net.paulem.simpleores.blocks.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ItemGroups {
    public static final CreativeModeTab SIMPLEORES = FabricItemGroup.builder()
            .icon(() ->
                            //? if !hasCopperTools {
                            new ItemStack(ModItems.COPPER_PICKAXE)
                    //?} else {
                    /*new ItemStack(ModItems.MYTHRIL_PICKAXE)
                     *///?}
            )
            .title(Component.translatable("item_group." + SimpleOres.MOD_ID + ".simpleores_tab"))
            .displayItems((displayContext, entries) -> {
                entries.acceptAll(ModBlocks.registeredBlockItems.values()
                        .stream()
                        .map(blockItem -> new ItemStack(blockItem.asItem()))
                        .toList());
                entries.acceptAll(ModItems.registeredItems.values()
                        .stream()
                        .map(ItemStack::new)
                        //.sorted(Comparator.comparing(itemStack -> itemStack.getItem().getClass().getName()))
                        .toList());
            })
            .build();
}
