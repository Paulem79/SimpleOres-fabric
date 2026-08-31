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

//? if >26.2 {
/*import net.minecraft.advancements.predicates.DataComponentMatchers;
import net.minecraft.advancements.predicates.EnchantmentPredicate;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.advancements.predicates.MinMaxBounds;
import net.minecraft.core.component.predicates.DataComponentPredicates;
import net.minecraft.core.component.predicates.EnchantmentsPredicate;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import java.util.List;
*///?}

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

    //? if <=26.2 {
    public void sameDropWithSilkTouch(Block block, Item drop) {
        add(block, createOreDrop(block, drop));
    }
    //?} else {
    /*// FIXME: This might be a bug in this 26.3 snapshot i guess, because this is not the good way for silk touch imo, fix this when possible
    // Manually builds the Silk Touch predicate, avoiding the broken Vanilla registry wrapper
    private LootItemCondition.Builder customHasSilkTouch() {
        return MatchTool.toolMatches(
                ItemPredicate.Builder.item()
                        .withComponents(
                                DataComponentMatchers.Builder.components()
                                        .partial(
                                                DataComponentPredicates.ENCHANTMENTS,
                                                EnchantmentsPredicate.enchantments(List.of(new EnchantmentPredicate(this.enchantments.getOrThrow(Enchantments.SILK_TOUCH), MinMaxBounds.Ints.atLeast(1))))
                                        )
                                        .build()
                        )
        );
    }

    public void sameDropWithSilkTouch(Block block, Item drop) {
        // Manually assemble the Ore Drop Loot Table to avoid Vanilla's crash-prone helper methods
        this.add(block, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(block)
                                .when(customHasSilkTouch()) // If Silk Touch -> drop block
                                .otherwise(                 // Otherwise -> apply Fortune & Explosion Decay to raw item
                                        this.applyExplosionDecay(block, LootItem.lootTableItem(drop)
                                                .apply(ApplyBonusCount.addOreBonusCount(this.enchantments.getOrThrow(Enchantments.FORTUNE))))
                                )
                        )
                )
        );
    }
    *///?}
}
