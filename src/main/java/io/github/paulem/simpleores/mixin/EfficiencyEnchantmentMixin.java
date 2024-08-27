package io.github.paulem.simpleores.mixin;

import io.github.paulem.simpleores.tags.ModTags;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.enchantment.EfficiencyEnchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EfficiencyEnchantment.class)
public abstract class EfficiencyEnchantmentMixin {
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"), method = "isAcceptableItem")
    private boolean simpleores$isShears(ItemStack stack, Item item) {
        return stack.isOf(item) || (item == Items.SHEARS && stack.isIn(ModTags.Items.SHEARS));
    }
}
