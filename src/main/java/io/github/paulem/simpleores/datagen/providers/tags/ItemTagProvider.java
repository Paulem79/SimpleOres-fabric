package io.github.paulem.simpleores.datagen.providers.tags;

import io.github.paulem.simpleores.blocks.ModBlocks;
import io.github.paulem.simpleores.items.AdvancedShearsItem;
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
            switch (item) {
                case SwordItem swordItem -> this.getOrCreateTagBuilder(ItemTags.SWORDS)
                        .add(swordItem);
                case AxeItem axeItem -> this.getOrCreateTagBuilder(ItemTags.AXES)
                        .add(axeItem);
                case PickaxeItem pickaxeItem -> this.getOrCreateTagBuilder(ItemTags.PICKAXES)
                        .add(pickaxeItem);
                case ShovelItem shovelItem -> this.getOrCreateTagBuilder(ItemTags.SHOVELS)
                        .add(shovelItem);
                case HoeItem hoeItem -> this.getOrCreateTagBuilder(ItemTags.HOES)
                        .add(hoeItem);
                case ArmorItem armorItem -> {
                    this.getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
                            .add(armorItem);
                }
                case AdvancedShearsItem shearsItem -> {
                    this.getOrCreateTagBuilder(ModTags.Items.SHEARS)
                            .add(shearsItem);
                }
                case null, default -> {
                }
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
