package net.paulem.simpleores.items;

import net.paulem.simpleores.SimpleOres;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ItemGroups {
    public static final CreativeModeTab SIMPLEORES =
            FabricItemGroup
            .builder(//? if <=1.19.4
            net.paulem.simpleores.stonecutter.SCId.of("itemgroup.global")
            )
            .icon(() ->
                            new ItemStack(ModItems.COPPER_PICKAXE)
            )
            .title(Component.translatable("item_group." + SimpleOres.MOD_ID + ".simpleores_tab"))
            .build();
}
