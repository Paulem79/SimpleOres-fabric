package ovh.paulem.simpleores.items.custom.bucket;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorageUtil;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.mixin.transfer.BucketItemAccessor;
import net.minecraft.block.*;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/**
 * Adapted from cech12's BucketLib
 */
public class CustomBucketDispenseBehaviour extends ItemDispenserBehavior {

    private static final CustomBucketDispenseBehaviour INSTANCE = new CustomBucketDispenseBehaviour();

    public static CustomBucketDispenseBehaviour getInstance()
    {
        return INSTANCE;
    }

    private CustomBucketDispenseBehaviour() {}

    @Override
    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        return dispenseFluidContainer(pointer, stack);
    }

    public ItemStack dispenseFluidContainer(BlockPointer source, ItemStack stack) {
        World level = source.world();
        Direction dispenserFacing = source.state().get(DispenserBlock.FACING);
        BlockPos pos = source.pos().offset(dispenserFacing);
        if (stack.getItem() instanceof CustomParentBucketItem) {
            Pair<Boolean, ItemStack> result = tryPickUpFluid(stack, null, level, null, pos, dispenserFacing);
            if (result.getLeft()) {
                if (stack.getCount() == 1) {
                    return result.getRight();
                }
                if (!(source.blockEntity()).addToFirstFreeSlot(result.getRight()).isEmpty()) {
                    new ItemDispenserBehavior().dispense(source, result.getRight());
                }
                ItemStack stackCopy = stack.copy();
                stackCopy.decrement(1);
                return stackCopy;
            }
        } else {
            Pair<Boolean, ItemStack> result = tryPlaceFluid(stack, null, level, null, pos);
            if (result.getLeft()) {
                return result.getRight();
            }
        }
        return stack;
    }

    public Pair<Boolean, ItemStack> tryPickUpFluid(ItemStack stack, @Nullable PlayerEntity player, World level, Hand interactionHand, BlockPos pos, Direction direction) {
        //Fluid Storage interaction
        Storage<FluidVariant> storage = FluidStorage.SIDED.find(level, pos, direction.getOpposite());
        if (storage != null && player != null && FluidStorageUtil.interactWithFluidStorage(storage, player, interactionHand)) {
            return new Pair<>(true, player.getStackInHand(interactionHand).copy());
        }
        //Fluid Source / Waterlogged Block interaction
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();
        if (block instanceof FluidDrainable bucketPickup) {
            ItemStack fullVanillaBucket = bucketPickup.tryDrainFluid(player, level, pos, state);
            if (fullVanillaBucket.getItem() instanceof BucketItem vanillaBucketItem) {
                Fluid fluid = ((BucketItemAccessor) vanillaBucketItem).fabric_getFluid();
                if (stack.getItem() instanceof CustomChildrenBucketItem bucketItem) {
                    SoundEvent sound = bucketPickup.getBucketFillSound().orElse(FluidVariantAttributes.getFillSound(FluidVariant.of(fluid)));
                    level.playSound(player, pos, sound, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    ItemStack usedStack = bucketItem.fromFluid(fluid).getDefaultStack();
                    return new Pair<>(true, usedStack);
                }
                level.setBlockState(pos, state, 3);
                return new Pair<>(false, stack);
            }
            //show incompatibility message and reset the block state
            if (!fullVanillaBucket.isEmpty()) {
                level.setBlockState(pos, state, 3);
                return new Pair<>(false, stack);
            }
        }
        return new Pair<>(false, stack);
    }

    public Pair<Boolean, ItemStack> tryPlaceFluid(ItemStack stack, @Nullable PlayerEntity player, World level, Hand interactionHand, BlockPos pos) {
        //Fluid Storage interaction
        Storage<FluidVariant> storage = FluidStorage.SIDED.find(level, pos, null);
        if (storage != null && player != null && FluidStorageUtil.interactWithFluidStorage(storage, player, interactionHand)) {
            return new Pair<>(true, player.getStackInHand(interactionHand).copy());
        }

        if(!(stack.getItem() instanceof CustomChildrenBucketItem bucketItem)) return new Pair<>(false, stack);

        Fluid fluid = bucketItem.getFluid();
        //vaporize
        if (level.getDimension().ultrawarm() && fluid.getDefaultState().isIn(FluidTags.WATER)) {
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            level.playSound(player, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.8F);
            for (int i = 0; i < 8; ++i) {
                level.addParticleClient(ParticleTypes.LARGE_SMOKE, (double) x + Math.random(), (double) y + Math.random(), (double) z + Math.random(), 0.0, 0.0, 0.0);
            }
            return new Pair<>(true, bucketItem.getParent().getDefaultStack());
        }
        //waterlogged Block interaction
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();
        if (block instanceof FluidFillable liquidBlockContainer && liquidBlockContainer.canFillWithFluid(player, level, pos, state, fluid)) {
            liquidBlockContainer.tryFillWithFluid(level, pos, state, fluid.getDefaultState());
            level.playSound(player, pos, FluidVariantAttributes.getEmptySound(FluidVariant.of(fluid)), SoundCategory.BLOCKS, 1.0F, 1.0F);
            return new Pair<>(true, bucketItem.getParent().getDefaultStack());
        }
        //air / replaceable block interaction
        if (state.isAir() || state.canBucketPlace(fluid) || (!state.getFluidState().isEmpty() && !(block instanceof FluidFillable))) {
            if (level.setBlockState(pos, fluid.getDefaultState().getBlockState(), 11) || state.getFluidState().isStill()) {
                level.playSound(player, pos, FluidVariantAttributes.getEmptySound(FluidVariant.of(fluid)), SoundCategory.BLOCKS, 1.0F, 1.0F);
                return new Pair<>(true, bucketItem.getParent().getDefaultStack());
            }
        }
        return new Pair<>(false, stack);
    }

}
