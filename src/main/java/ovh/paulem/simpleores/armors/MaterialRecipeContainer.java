package ovh.paulem.simpleores.armors;

import ovh.paulem.simpleores.items.custom.advanced.AdvancedArmorItem;
import ovh.paulem.simpleores.items.custom.advanced.AdvancedPickaxeItem;
import ovh.paulem.simpleores.items.custom.advanced.AdvancedShearsItem;
import ovh.paulem.simpleores.blocks.custom.MultifunctionPressurePlateBlock;
import net.minecraft.block.*;
import net.minecraft.item.*;
import org.jetbrains.annotations.Nullable;
import ovh.paulem.simpleores.items.custom.advanced.AdvancedSwordItem;

public record MaterialRecipeContainer(@Nullable AdvancedSwordItem sword, @Nullable AdvancedPickaxeItem pickaxe, @Nullable AxeItem axe, @Nullable ShovelItem shovel,
                                      @Nullable HoeItem hoe, @Nullable AdvancedArmorItem helmet, @Nullable AdvancedArmorItem chesplate, @Nullable AdvancedArmorItem leggings, @Nullable AdvancedArmorItem boots,
                                      @Nullable AdvancedShearsItem shears,
                                      @Nullable Block ore, @Nullable Block deepslateOre, @Nullable Block block, @Nullable Block rawBlock,
                                      @Nullable Item raw, @Nullable Item nugget,
                                      @Nullable DoorBlock door, @Nullable PaneBlock bars, @Nullable MultifunctionPressurePlateBlock pressurePlate, @Nullable Block cut, @Nullable
                                      SlabBlock cutSlab, @Nullable StairsBlock stairs,
                                      @Nullable Float smeltXp, boolean excludeSmeltCreation) {
}
