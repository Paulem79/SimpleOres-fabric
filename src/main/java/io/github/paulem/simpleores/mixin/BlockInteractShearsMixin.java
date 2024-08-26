package io.github.paulem.simpleores.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.paulem.simpleores.items.AdvancedShearsItem;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.PumpkinBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin({BeehiveBlock.class, PumpkinBlock.class})
public abstract class BlockInteractShearsMixin {

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"), method = "onUseWithItem")
	private boolean isShears(ItemStack stack, Item item, Operation<Boolean> original) {
		return original.call(stack, item) || (item == Items.SHEARS && stack.getItem() instanceof AdvancedShearsItem);
	}

}
