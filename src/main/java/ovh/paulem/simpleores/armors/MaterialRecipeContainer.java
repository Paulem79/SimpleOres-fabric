package ovh.paulem.simpleores.armors;

import net.minecraft.registry.tag.TagKey;
import ovh.paulem.simpleores.items.custom.advanced.AdvancedArmorItem;
import ovh.paulem.simpleores.items.custom.advanced.AdvancedPickaxeItem;
import ovh.paulem.simpleores.items.custom.advanced.AdvancedShearsItem;
import ovh.paulem.simpleores.blocks.custom.MultifunctionPressurePlateBlock;
import net.minecraft.block.*;
import net.minecraft.item.*;
import org.jetbrains.annotations.Nullable;
import ovh.paulem.simpleores.items.custom.advanced.AdvancedSwordItem;

public record MaterialRecipeContainer(@Nullable AdvancedSwordItem sword, @Nullable AdvancedPickaxeItem pickaxe, @Nullable AxeItem axe, @Nullable ShovelItem shovel,
                                      @Nullable HoeItem hoe, @Nullable AdvancedArmorItem helmet, @Nullable AdvancedArmorItem chesplate, @Nullable AdvancedArmorItem leggings,
                                      @Nullable AdvancedArmorItem boots, @Nullable AdvancedShearsItem shears,
                                      @Nullable TagKey<Item> ores, @Nullable Block block, @Nullable Block rawBlock,
                                      @Nullable TagKey<Item> raw, @Nullable ItemConvertible baseRawItem, @Nullable ItemConvertible nugget,
                                      @Nullable DoorBlock door, @Nullable PaneBlock bars, @Nullable MultifunctionPressurePlateBlock pressurePlate, @Nullable Block cut, @Nullable
                                      SlabBlock cutSlab, @Nullable StairsBlock stairs,
                                      @Nullable Item bucket, @Nullable Float smeltXp, boolean excludeSmeltCreation) {
}
