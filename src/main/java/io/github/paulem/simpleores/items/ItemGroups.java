package io.github.paulem.simpleores.items;

import io.github.paulem.simpleores.SimpleOres;
import io.github.paulem.simpleores.blocks.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ItemGroups {
    public static final ItemGroup SIMPLEORES = FabricItemGroup.builder(new Identifier(SimpleOres.MOD_ID, "itemgroup.global"))
            .icon(() -> new ItemStack(ModItems.COPPER_PICKAXE))
            .displayName(Text.translatable("item_group." + SimpleOres.MOD_ID + ".simpleores_tab"))
            .build();
}
