package net.paulem.simpleores.items.custom.bucket;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

/**
 * Provides itemstack from dispenser behaviour, for easier handling and per-stack dispenser behaviour.
 */
public interface CustomDispensibleContainerItem extends DispensibleContainerItem {
    default boolean emptyContents(@Nullable final LivingEntity user, final Level level, final BlockPos pos, @Nullable final BlockHitResult hitResult, final ItemStack stack) {
        return emptyContents(user, level, pos, hitResult);
    }
}
