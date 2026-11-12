package net.paulem.simpleores.datagen.providers;

import net.minecraft.world.level.block.DoorBlock;
import net.paulem.simpleores.blocks.ModBlocks;
import net.paulem.simpleores.items.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.paulem.sclib.datagen.SCBlockLootProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public class LootTableProvider extends SCBlockLootProvider {
    public LootTableProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generate() {
        ore(ModBlocks.TIN_ORE, ModItems.RAW_TIN);
        ore(ModBlocks.DEEPSLATE_TIN_ORE, ModItems.RAW_TIN);

        ore(ModBlocks.MYTHRIL_ORE, ModItems.RAW_MYTHRIL);
        ore(ModBlocks.DEEPSLATE_MYTHRIL_ORE, ModItems.RAW_MYTHRIL);

        ore(ModBlocks.ADAMANTIUM_ORE, ModItems.RAW_ADAMANTIUM);
        ore(ModBlocks.DEEPSLATE_ADAMANTIUM_ORE, ModItems.RAW_ADAMANTIUM);

        ore(ModBlocks.ONYX_ORE, ModItems.ONYX_GEM);
        ore(ModBlocks.BASALT_ONYX_ORE, ModItems.ONYX_GEM);

        ModBlocks.registeredBlockItems.forEach((identifier, blockItem) -> {
            Block block = blockItem.getBlock();
            if(block instanceof DoorBlock) {
                door(block);
            } else if(!identifier.getPath().contains("ore")) {
                self(block);
            }
        });
    }

}
