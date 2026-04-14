package net.paulem.simpleores.items.custom.bows;

import net.minecraft.core.HolderLookup;
//? if 1.21 {
/*import net.minecraft.resources.ResourceKey;
import net.minecraft.core.Holder;*/
//?}
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.paulem.simpleores.tooltip.TooltipItem;

public abstract class CustomBow extends BowItem implements TooltipItem {
    public CustomBow(Properties properties) {
        super(properties);
    }

    public //? if 1.21 {
    //Holder.Reference<Enchantment>
    //?} else {
    Enchantment
    //?}
    getEnchantment(HolderLookup.RegistryLookup<Enchantment> enchantmentImpl, //? if 1.21 {
                                      //ResourceKey<Enchantment> enchantment
                                      //?} else {
                                      Enchantment enchantment
                                      //?}
                                      ) {
        //? if 1.20.6 {
        /*return enchantment;
        *///?} else if 1.21 {
        //return enchantmentImpl.getOrThrow(enchantment);
        //?} else {
        return enchantment;
        //?}
    }
}
