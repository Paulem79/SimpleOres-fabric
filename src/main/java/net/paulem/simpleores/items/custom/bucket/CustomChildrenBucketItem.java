package net.paulem.simpleores.items.custom.bucket;

//? if hasBucketlib || !containsBucket {
/*public class CustomChildrenBucketItem {}
*///?} else {

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.paulem.simpleores.items.ModComponents;
import org.jspecify.annotations.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.material.Fluids;
import org.jspecify.annotations.NonNull;

public class CustomChildrenBucketItem extends BucketItem implements CustomBucketFluidable {
    @Nullable
    private final CustomBucketItem parent;
    private final Fluid fluid;

    public CustomChildrenBucketItem(Fluid fluid, Item.Properties settings, @Nullable CustomBucketItem parent) {
        super(fluid, settings);

        this.parent = parent;
        this.fluid = fluid;
        DispenserBlock.registerBehavior(this, CustomBucketDispenseBehaviour.getInstance());
    }

    public CustomChildrenBucketItem(Fluid fluid, Item.Properties settings) {
        super(fluid, settings);

        this.parent = null;
        this.fluid = fluid;
        DispenserBlock.registerBehavior(this, CustomBucketDispenseBehaviour.getInstance());
    }

    @Override
    public ItemStack getCorrespondingBucket(Fluid modFluid) {
        if(modFluid == null || modFluid == Fluids.EMPTY) return getDefaultInstance();

        Block block = modFluid.defaultFluidState().createLegacyBlock().getBlock();
        Identifier identifier = getBlockIdentifier(block);

        ItemStack stack = getDefaultInstance();
        stack.set(ModComponents.BUCKET_FLUID_BLOCK_COMPONENT, identifier);

        return stack;
    }

    @Override
    public CustomBucketItem getParent() {
        if(parent == null) {
            throw new IllegalStateException("Accessing null parent! This children should be overridden if it's itself the parent !");
        }
        return parent;
    }

    @Override
    public String getBaseName() {
        return getParent().getBaseName();
    }

    @Override
    public @NonNull Component getName(@NonNull ItemStack stack) {
        if(fluid == null || fluid == Fluids.EMPTY) {
            return getParent().getName(stack);
        }
        return getParent().getName(stack, FluidVariant.of(fluid).getFluid());
    }

    @Override
    public Fluid getFluid(ItemStack stack) {
        Block block = fromIdentifier(stack.get(ModComponents.BUCKET_FLUID_BLOCK_COMPONENT));
        return block instanceof LiquidBlock liquidBlock ? liquidBlock.fluid : Fluids.EMPTY;
    }

    public static Block fromIdentifier(Identifier identifier) {
        return BuiltInRegistries.BLOCK.getValue(identifier);
    }

    public static Identifier getBlockIdentifier(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    public boolean isWaterLike() {
        return isWaterLike(fluid);
    }

    public static boolean isWaterLike(Fluid fluid) {
        return FluidVariant.of(fluid).isOf(Fluids.WATER) ||
                BuiltInRegistries.FLUID.getKey(fluid).getPath().contains("water");
    }

    @Override
    public @NonNull InteractionResult use(final @NonNull Level level, final Player player, final @NonNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        BlockHitResult hitResult = getPlayerPOVHitResult(
                level, player, this.content == Fluids.EMPTY ? net.minecraft.world.level.ClipContext.Fluid.SOURCE_ONLY : net.minecraft.world.level.ClipContext.Fluid.NONE
        );
        if (hitResult.getType() == HitResult.Type.MISS) {
            return InteractionResult.PASS;
        } else if (hitResult.getType() != HitResult.Type.BLOCK) {
            return InteractionResult.PASS;
        } else {
            BlockPos pos = hitResult.getBlockPos();
            Direction direction = hitResult.getDirection();
            BlockPos directionOffsetPos = pos.relative(direction);
            if (level.mayInteract(player, pos) && player.mayUseItemAt(directionOffsetPos, direction, itemStack)) {
                BlockState clicked = level.getBlockState(pos);
                BlockPos placePos = clicked.getBlock() instanceof LiquidBlockContainer && this.content == Fluids.WATER ? pos : directionOffsetPos;
                if (this.emptyContents(player, level, placePos, hitResult)) {
                    this.checkExtraContent(player, level, itemStack, placePos);
                    if (player instanceof ServerPlayer && this.content != Fluids.EMPTY) {
                        CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, placePos, itemStack);
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                    ItemStack emptyResult = ItemUtils.createFilledResult(itemStack, player, getEmptySuccessItem(itemStack, player));
                    return InteractionResult.SUCCESS.heldItemTransformedTo(emptyResult);
                } else {
                    if (this.content == Fluids.EMPTY) {
                        BlockState blockState = level.getBlockState(pos);
                        if (blockState.getBlock() instanceof BucketPickup bucketPickupBlock) {
                            ItemStack taken = itemStack.copy(); //bucketPickupBlock.pickupBlock(player, level, pos, blockState); // TODO : Or copy ? or other ?
                            taken.set(ModComponents.BUCKET_FLUID_BLOCK_COMPONENT, BuiltInRegistries.BLOCK.getKey(blockState.getBlock()));

                            if (!taken.isEmpty()) {
                                player.awardStat(Stats.ITEM_USED.get(this));
                                bucketPickupBlock.getPickupSound().ifPresent(soundEvent -> player.playSound(soundEvent, 1.0F, 1.0F));
                                level.gameEvent(player, GameEvent.FLUID_PICKUP, pos);
                                ItemStack result = ItemUtils.createFilledResult(itemStack, player, taken);
                                if (!level.isClientSide()) {
                                    CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer)player, taken);
                                }

                                return InteractionResult.SUCCESS.heldItemTransformedTo(result);
                            }
                        }
                    }

                    return InteractionResult.FAIL;
                }
            } else {
                return InteractionResult.FAIL;
            }
        }
    }

    public static ItemStack getEmptySuccessItem(final ItemStack itemStack, final Player player) {
        Item item = itemStack.getItem();
        if(item instanceof CustomChildrenBucketItem childrenBucket) {
            return !player.hasInfiniteMaterials() ? childrenBucket.getParent().getDefaultInstance() : itemStack;
        }
        return !player.hasInfiniteMaterials() ? item.getDefaultInstance() : itemStack;
    }

    @Override
    public void checkExtraContent(@Nullable final LivingEntity user, final Level level, final ItemStack itemStack, final BlockPos pos) {
    }

    @Override
    public boolean emptyContents(@Nullable final LivingEntity user, final Level level, final BlockPos pos, @Nullable final BlockHitResult hitResult) {
        if (!(this.content instanceof FlowingFluid flowingFluid)) {
            return false;
        } else {
            BlockState blockState = level.getBlockState(pos);
            Block block = blockState.getBlock();
            boolean mayReplace = blockState.canBeReplaced(this.content);
            boolean shiftKeyDown = user != null && user.isShiftKeyDown();
            boolean placeLiquid = mayReplace || block instanceof LiquidBlockContainer container && container.canPlaceLiquid(user, level, pos, blockState, this.content);
            boolean canPlaceFluidInsideBlock = blockState.isAir() || placeLiquid && (!shiftKeyDown || hitResult == null);
            if (!canPlaceFluidInsideBlock) {
                return hitResult != null && this.emptyContents(user, level, hitResult.getBlockPos().relative(hitResult.getDirection()), null);
            } else if (level.environmentAttributes().getValue(EnvironmentAttributes.WATER_EVAPORATES, pos) && this.content.is(FluidTags.WATER)) {
                int x = pos.getX();
                int y = pos.getY();
                int z = pos.getZ();
                RandomSource random = level.getRandom();
                level.playSound(user, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 2.6F + (random.nextFloat() - random.nextFloat()) * 0.8F);

                for (int i = 0; i < 8; i++) {
                    level.addParticle(ParticleTypes.LARGE_SMOKE, x + random.nextFloat(), y + random.nextFloat(), z + random.nextFloat(), 0.0, 0.0, 0.0);
                }

                return true;
            } else if (block instanceof LiquidBlockContainer containerx && this.content == Fluids.WATER) {
                containerx.placeLiquid(level, pos, blockState, flowingFluid.getSource(false));
                this.playEmptySound(user, level, pos);
                return true;
            } else {
                if (!level.isClientSide() && mayReplace && !blockState.liquid()) {
                    level.destroyBlock(pos, true);
                }

                if (!level.setBlock(pos, this.content.defaultFluidState().createLegacyBlock(), 11) && !blockState.getFluidState().isSource()) {
                    return false;
                } else {
                    this.playEmptySound(user, level, pos);
                    return true;
                }
            }
        }
    }

    protected void playEmptySound(@Nullable final LivingEntity user, final LevelAccessor level, final BlockPos pos) {
        SoundEvent soundEvent = this.content.is(FluidTags.LAVA) ? SoundEvents.BUCKET_EMPTY_LAVA : SoundEvents.BUCKET_EMPTY;
        level.playSound(user, pos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.gameEvent(user, GameEvent.FLUID_PLACE, pos);
    }
}
//?}
