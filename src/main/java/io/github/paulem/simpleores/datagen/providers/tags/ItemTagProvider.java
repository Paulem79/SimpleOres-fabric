package io.github.paulem.simpleores.datagen.providers.tags;

import io.github.paulem.simpleores.blocks.ModBlocks;
import io.github.paulem.simpleores.items.custom.AdvancedShearsItem;
import io.github.paulem.simpleores.items.ModItems;
import io.github.paulem.simpleores.tags.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        ModItems.registeredItems.forEach(((identifier, item) -> {
            if (item instanceof SwordItem swordItem) {
                this.getOrCreateTagBuilder(ItemTags.SWORDS)
                        .add(swordItem);
            } else if (item instanceof AxeItem axeItem) {
                this.getOrCreateTagBuilder(ItemTags.AXES)
                        .add(axeItem);
            } else if (item instanceof PickaxeItem pickaxeItem) {
                this.getOrCreateTagBuilder(ItemTags.PICKAXES)
                        .add(pickaxeItem);
            } else if (item instanceof ShovelItem shovelItem) {
                this.getOrCreateTagBuilder(ItemTags.SHOVELS)
                        .add(shovelItem);
            } else if (item instanceof HoeItem hoeItem) {
                this.getOrCreateTagBuilder(ItemTags.HOES)
                        .add(hoeItem);
            } else if (item instanceof ArmorItem armorItem) {
                this.getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
                        .add(armorItem);
            } else if (item instanceof AdvancedShearsItem shearsItem) {
                this.getOrCreateTagBuilder(ModTags.Items.SHEARS)
                        .add(shearsItem);
            } else if(identifier.getPath().contains("_nugget")) {
                // Nuggets
                this.getOrCreateTagBuilder(ModTags.Items.NUGGETS)
                        .add(item);
            } else if(identifier.getPath().contains("_dust")) {
                // Dusts
                this.getOrCreateTagBuilder(ModTags.Items.DUSTS)
                        .add(item);
            } else if(identifier.getPath().contains("crushed_") && identifier.getPath().contains("_ore")) {
                // Crushed Ore
                this.getOrCreateTagBuilder(ModTags.Items.CRUSHED_ORES)
                        .add(item);
            }
        }));

        ModBlocks.registeredBlockItems.forEach((identifier, blockItem) -> {
            Block block = blockItem.getBlock();

            if(block instanceof DoorBlock)
                getOrCreateTagBuilder(ItemTags.DOORS)
                        .add(blockItem);
            else if(block instanceof SlabBlock)
                getOrCreateTagBuilder(ItemTags.SLABS)
                        .add(blockItem);
            else if(block instanceof StairsBlock)
                getOrCreateTagBuilder(ItemTags.STAIRS)
                        .add(blockItem);
        });
    }
}
