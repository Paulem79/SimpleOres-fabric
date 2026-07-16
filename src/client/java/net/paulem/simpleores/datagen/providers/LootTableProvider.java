package net.paulem.simpleores.datagen.providers;

import net.minecraft.world.level.block.DoorBlock;
import net.paulem.simpleores.blocks.ModBlocks;
import net.paulem.simpleores.items.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import java.util.concurrent.CompletableFuture;

public class LootTableProvider extends FabricBlockLootSubProvider {
    public LootTableProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output //? if >1.20.4
                , registryLookup
        );
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
            Block block = blockItem.getBlock();
            if(block instanceof DoorBlock) {
                add(block, this::createDoorTable);
            } else if(!identifier.getPath().contains("ore")) {
                dropSelf(block);
            }
        });
    }

    public void sameDropWithSilkTouch(Block block, Item drop) {
        add(block, createOreDrop(block, drop));
    }
}
