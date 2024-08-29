package io.github.paulem.simpleores.armors;

import de.cech12.bucketlib.api.item.UniversalBucketItem;
import io.github.paulem.simpleores.items.custom.AdvancedShearsItem;
import io.github.paulem.simpleores.blocks.custom.MultifunctionPressurePlateBlock;
import net.minecraft.block.*;
import net.minecraft.item.*;
import org.jetbrains.annotations.Nullable;

public record MaterialRecipeContainer(@Nullable SwordItem sword, @Nullable PickaxeItem pickaxe, @Nullable AxeItem axe, @Nullable ShovelItem shovel,
                                      @Nullable HoeItem hoe, @Nullable ArmorItem helmet, @Nullable ArmorItem chesplate, @Nullable ArmorItem leggings, @Nullable ArmorItem boots,
                                      @Nullable AdvancedShearsItem shears,
                                      @Nullable Block ore, @Nullable Block deepslateOre, @Nullable Block block, @Nullable Block rawBlock,
                                      @Nullable Item raw, @Nullable Item nugget,
                                      @Nullable DoorBlock door, @Nullable PaneBlock bars, @Nullable MultifunctionPressurePlateBlock pressurePlate, @Nullable Block cut, @Nullable
                                      SlabBlock cutSlab, @Nullable StairsBlock stairs, @Nullable UniversalBucketItem bucket,
                                      @Nullable Float smeltXp, boolean excludeSmeltCreation) {
}
