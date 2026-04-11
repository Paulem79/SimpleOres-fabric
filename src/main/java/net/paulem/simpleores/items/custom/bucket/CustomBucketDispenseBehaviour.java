package net.paulem.simpleores.items.custom.bucket;

//? if hasBucketlib || !containsBucket {
 /*public class CustomBucketDispenseBehaviour {}
*///?} else {

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorageUtil;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.mixin.transfer.BucketItemAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;
import oshi.util.tuples.Pair;

/**
 * Adapted from cech12's BucketLib
 */
public class CustomBucketDispenseBehaviour extends DefaultDispenseItemBehavior {

    private static final CustomBucketDispenseBehaviour INSTANCE = new CustomBucketDispenseBehaviour();

    public static CustomBucketDispenseBehaviour getInstance()
    {
        return INSTANCE;
    }

    private CustomBucketDispenseBehaviour() {}

    @Override
    protected ItemStack execute(BlockSource pointer, ItemStack stack) {
        return dispenseFluidContainer(pointer, stack);
    }

    public ItemStack dispenseFluidContainer(BlockSource source, ItemStack stack) {
        Level level = source.level();
        Direction dispenserFacing = source.state().getValue(DispenserBlock.FACING);
        BlockPos pos = source.pos().relative(dispenserFacing);
        if (stack.getItem() instanceof CustomBucketItem) {
            Pair<Boolean, ItemStack> result = tryPickUpFluid(stack, null, level, null, pos, dispenserFacing);
            if (result.getA()) {
                if (stack.getCount() == 1) {
                    return result.getB();
                }
                if (!(source.blockEntity()).insertItem(result.getB()).isEmpty()) {
                    new DefaultDispenseItemBehavior().dispense(source, result.getB());
                }
                ItemStack stackCopy = stack.copy();
                stackCopy.shrink(1);
                return stackCopy;
            }
        } else {
            Pair<Boolean, ItemStack> result = tryPlaceFluid(stack, null, level, null, pos);
            if (result.getA()) {
                return result.getB();
            }
        }
        return stack;
    }

    public Pair<Boolean, ItemStack> tryPickUpFluid(ItemStack stack, @Nullable Player player, Level level, InteractionHand interactionHand, BlockPos pos, Direction direction) {
        //Fluid Storage interaction
        Storage<FluidVariant> storage = FluidStorage.SIDED.find(level, pos, direction.getOpposite());
        if (storage != null && player != null && FluidStorageUtil.interactWithFluidStorage(storage, player, interactionHand)) {
            return new Pair<>(true, player.getItemInHand(interactionHand).copy());
        }
        //Fluid Source / Waterlogged Block interaction
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();
        if (block instanceof BucketPickup bucketPickup) {
            ItemStack fullVanillaBucket = bucketPickup.pickupBlock(player, level, pos, state);
            if (fullVanillaBucket.getItem() instanceof BucketItem vanillaBucketItem) {
                Fluid fluid = ((BucketItemAccessor) vanillaBucketItem)
                        //? if afterDeobf {
                        .fabric_getContent();
                        //? } else {
                        /*.fabric_getFluid();
                        *///?}
                if (stack.getItem() instanceof CustomBucketItem bucketItem) {
                    SoundEvent sound = bucketPickup.getPickupSound().orElse(FluidVariantAttributes.getFillSound(FluidVariant.of(fluid)));
                    level.playSound(player, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
                    ItemStack usedStack = bucketItem.getCorrespondingBucket(fluid);
                    return new Pair<>(true, usedStack);
                }
                level.setBlock(pos, state, 3);
                return new Pair<>(false, stack);
            }
            //show incompatibility message and reset the block state
            if (!fullVanillaBucket.isEmpty()) {
                level.setBlock(pos, state, 3);
                return new Pair<>(false, stack);
            }
        }
        return new Pair<>(false, stack);
    }

    public Pair<Boolean, ItemStack> tryPlaceFluid(ItemStack stack, @Nullable Player player, Level level, InteractionHand interactionHand, BlockPos pos) {
        //Fluid Storage interaction
        Storage<FluidVariant> storage = FluidStorage.SIDED.find(level, pos, null);
        if (storage != null && player != null && FluidStorageUtil.interactWithFluidStorage(storage, player, interactionHand)) {
            return new Pair<>(true, player.getItemInHand(interactionHand).copy());
        }

        if(!(stack.getItem() instanceof CustomBucketItem bucketItem)) return new Pair<>(false, stack);

        Fluid fluid = bucketItem.getFluid(stack);
        //vaporize
        boolean vaporize = //? if >1.21.10 {
         level.environmentAttributes().getValue(net.minecraft.world.attribute.EnvironmentAttributes.WATER_EVAPORATES, pos);
        //?} else {
        /*level.dimensionType().ultraWarm();
        *///?}

        if (vaporize && fluid.defaultFluidState().is(FluidTags.WATER)) {
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            level.playSound(player, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 2.6F + (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.8F);
            for (int i = 0; i < 8; ++i) {
                level.addParticle(ParticleTypes.LARGE_SMOKE, (double) x + Math.random(), (double) y + Math.random(), (double) z + Math.random(), 0.0, 0.0, 0.0);
            }
            return new Pair<>(true, bucketItem.getEmpty());
        }
        //waterlogged Block interaction
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();
        if (block instanceof LiquidBlockContainer liquidBlockContainer && liquidBlockContainer.canPlaceLiquid(player, level, pos, state, fluid)) {
            liquidBlockContainer.placeLiquid(level, pos, state, fluid.defaultFluidState());
            level.playSound(player, pos, FluidVariantAttributes.getEmptySound(FluidVariant.of(fluid)), SoundSource.BLOCKS, 1.0F, 1.0F);
            return new Pair<>(true, bucketItem.getEmpty());
        }
        //air / replaceable block interaction
        if (state.isAir() || state.canBeReplaced(fluid) || (!state.getFluidState().isEmpty() && !(block instanceof LiquidBlockContainer))) {
            if (level.setBlock(pos, fluid.defaultFluidState().createLegacyBlock(), 11) || state.getFluidState().isSource()) {
                level.playSound(player, pos, FluidVariantAttributes.getEmptySound(FluidVariant.of(fluid)), SoundSource.BLOCKS, 1.0F, 1.0F);
                return new Pair<>(true, bucketItem.getEmpty());
            }
        }
        return new Pair<>(false, stack);
    }

}
//?}
