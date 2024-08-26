package io.github.paulem.simpleores.datagen.providers;

import io.github.paulem.simpleores.blocks.ModBlocks;
import io.github.paulem.simpleores.items.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class LootTableProvider extends FabricBlockLootTableProvider {
    public LootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        sameDropWithSilkTouch(ModBlocks.TIN_ORE, ModItems.RAW_TIN);
        sameDropWithSilkTouch(ModBlocks.DEEPSLATE_TIN_ORE, ModItems.RAW_TIN);

        sameDropWithSilkTouch(ModBlocks.MYTHRIL_ORE, ModItems.RAW_MYTHRIL);
        sameDropWithSilkTouch(ModBlocks.DEEPSLATE_MYTHRIL_ORE, ModItems.RAW_MYTHRIL);

        sameDropWithSilkTouch(ModBlocks.ADAMANTIUM_ORE, ModItems.RAW_ADAMANTIUM);
        sameDropWithSilkTouch(ModBlocks.DEEPSLATE_ADAMANTIUM_ORE, ModItems.RAW_ADAMANTIUM);

        sameDropWithSilkTouch(ModBlocks.ONYX_ORE, ModItems.ONYX_GEM);

        ModBlocks.registeredBlockItems.forEach((identifier, blockItem) -> {
            if(blockItem.getBlock() instanceof DoorBlock doorBlock) {
                addDrop(doorBlock, block -> doorDrops(doorBlock));
            } else if(!identifier.getPath().contains("ore")) {
                addDrop(blockItem.getBlock());
            }
        });
    }

    public void sameDropWithSilkTouch(Block block, Item drop) {
        addDrop(block, oreDrops(block, drop));
    }
}
