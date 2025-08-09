package ovh.paulem.simpleores.items;

import ovh.paulem.simpleores.SimpleOres;
import ovh.paulem.simpleores.blocks.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class ItemGroups {
    public static final ItemGroup SIMPLEORES = FabricItemGroup.builder()
            .icon(() ->
                    //? if !hasCopperTools {
                    new ItemStack(ModItems.COPPER_PICKAXE)
                    //?} else {
                     /*new ItemStack(ModItems.MYTHRIL_PICKAXE)
                    *///?}
            )
            .displayName(Text.translatable("item_group." + SimpleOres.MOD_ID + ".simpleores_tab"))
            .entries((displayContext, entries) -> {
                entries.addAll(ModBlocks.registeredBlockItems.values()
                        .stream()
                        .map(blockItem -> new ItemStack(blockItem.asItem()))
                        .toList());
                entries.addAll(ModItems.registeredItems.values()
                        .stream()
                        .map(ItemStack::new)
                        .toList());
            })
            .build();
}
