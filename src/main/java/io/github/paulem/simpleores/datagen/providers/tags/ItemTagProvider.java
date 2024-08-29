package io.github.paulem.simpleores.datagen.providers.tags;

import de.cech12.bucketlib.api.item.UniversalBucketItem;
import io.github.paulem.simpleores.blocks.ModBlocks;
import io.github.paulem.simpleores.items.custom.AdvancedShearsItem;
import io.github.paulem.simpleores.items.ModItems;
import io.github.paulem.simpleores.tags.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
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
            if(identifier.getPath().contains("_nugget")) {
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
                    if(armorItem.getType() == ArmorItem.Type.HELMET) this.getOrCreateTagBuilder(ItemTags.HEAD_ARMOR)
                            .add(armorItem);
                    else if(armorItem.getType() == ArmorItem.Type.CHESTPLATE) this.getOrCreateTagBuilder(ItemTags.CHEST_ARMOR)
                            .add(armorItem);
                    else if(armorItem.getType() == ArmorItem.Type.LEGGINGS) this.getOrCreateTagBuilder(ItemTags.LEG_ARMOR)
                            .add(armorItem);
                    else if(armorItem.getType() == ArmorItem.Type.BOOTS) this.getOrCreateTagBuilder(ItemTags.FOOT_ARMOR)
                            .add(armorItem);
                }
                case BowItem bowItem -> {
                    this.getOrCreateTagBuilder(ItemTags.DURABILITY_ENCHANTABLE)
                            .add(bowItem);
                    this.getOrCreateTagBuilder(ItemTags.BOW_ENCHANTABLE)
                            .add(bowItem);
                    this.getOrCreateTagBuilder(ConventionalItemTags.RANGED_WEAPONS_TOOLS)
                            .add(bowItem);
                    this.getOrCreateTagBuilder(ConventionalItemTags.BOWS_TOOLS)
                            .add(bowItem);
                }
                case AdvancedShearsItem shearsItem -> {
                    this.getOrCreateTagBuilder(ItemTags.MINING_ENCHANTABLE)
                            .add(shearsItem);
                    this.getOrCreateTagBuilder(ItemTags.DURABILITY_ENCHANTABLE)
                            .add(shearsItem);
                    this.getOrCreateTagBuilder(ModTags.Items.SHEARS)
                            .add(shearsItem);
                    this.getOrCreateTagBuilder(ConventionalItemTags.SHEARS_TOOLS)
                            .add(shearsItem);
                }
                case null, default -> {
                }
            }
        }));

        ModBlocks.registeredBlockItems.forEach((identifier, blockItem) -> {
            Block block = blockItem.getBlock();

            if(blockItem.asItem() instanceof UniversalBucketItem) {
                getOrCreateTagBuilder(ItemTags.DURABILITY_ENCHANTABLE)
                        .add(blockItem.asItem());
            }
            else if(block instanceof DoorBlock)
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
