package io.github.paulem.simpleores.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.paulem.simpleores.items.AdvancedShearsItem;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin({MooshroomEntity.class, SheepEntity.class, SnowGolemEntity.class, WolfEntity.class})
public abstract class EntityInteractShearsMixin {

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"), method = "interactMob")
	private boolean isShears(ItemStack stack, Item item, Operation<Boolean> original) {
		return original.call(stack, item) || (item == Items.SHEARS && stack.getItem() instanceof AdvancedShearsItem);
	}

}
