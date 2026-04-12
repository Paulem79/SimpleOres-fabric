package net.paulem.simpleores.items.custom.bucket;

//? if hasBucketlib || !containsBucket {
/*public class CustomBucketItem {}
*///?} else {

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.component.UseRemainder;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.paulem.simpleores.items.ModComponents;
import net.paulem.simpleores.mixin.accessor.BucketItemAccessor;
import net.paulem.simpleores.utils.LevelUtils;
import org.jspecify.annotations.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.material.Fluids;
import org.jspecify.annotations.NonNull;

// TODO: Fix glitched behaviour in creative mode with buckets when picking up entity
public class CustomBucketItem extends MobBucketItem implements CustomDispensibleContainerItem {
    public CustomBucketItem(Properties settings) {
        super(null, Fluids.EMPTY, SoundEvents.EMPTY, settings
                        .component(ModComponents.BUCKET_BLOCK_COMPONENT, getBlockIdentifier(Blocks.AIR))
                        //.component(DataComponents.MAX_STACK_SIZE, 16) // TODO: Fix bug with max stack size which duplicates bucket on picking up liquid/block when holding 16 empty buckets
        );

        DispenserBlock.registerBehavior(this, CustomBucketDispenseBehaviour.getInstance());
    }

    public ItemStack getCorrespondingBucket(@Nullable ItemStack itemStack, Block block) {
        if(block == null || block == Blocks.AIR) return getEmpty();

        Identifier identifier = getBlockIdentifier(block);

        ItemStack stack = itemStack != null ? itemStack : getDefaultInstance();

        stack.set(ModComponents.BUCKET_BLOCK_COMPONENT, identifier);
        if(stack.has(ModComponents.BUCKET_FISH_COMPONENT)) {
            stack.remove(ModComponents.BUCKET_FISH_COMPONENT);
        }
        if(stack.has(DataComponents.CONSUMABLE) && stack.has(DataComponents.USE_REMAINDER)) {
            stack.remove(DataComponents.CONSUMABLE);
            stack.remove(DataComponents.USE_REMAINDER);
        }

        //stack.set(DataComponents.MAX_STACK_SIZE, 1);

        return stack;
    }

    public ItemStack getCorrespondingBucket(@Nullable ItemStack itemStack, Fluid modFluid) {
        if(modFluid == null || modFluid == Fluids.EMPTY) return getEmpty();

        Block block = modFluid.defaultFluidState().createLegacyBlock().getBlock();
        return getCorrespondingBucket(itemStack, block);
    }

    public ItemStack getMilkBucket() {
        ItemStack stack = getDefaultInstance();
        stack.set(ModComponents.BUCKET_FISH_COMPONENT, getItemIdentifier(Items.MILK_BUCKET));
        stack.set(DataComponents.CONSUMABLE, Consumables.MILK_BUCKET);
        stack.set(DataComponents.USE_REMAINDER, new UseRemainder(//? afterDeobf
                ItemStackTemplate.fromNonEmptyStack
                        (getEmpty())));
        //stack.set(DataComponents.MAX_STACK_SIZE, 1);

        return stack;
    }

    public ItemStack getEmpty() {
        Identifier identifier = getBlockIdentifier(Blocks.AIR);

        ItemStack stack = getDefaultInstance();
        stack.set(ModComponents.BUCKET_BLOCK_COMPONENT, identifier);
        //stack.set(DataComponents.MAX_STACK_SIZE, 16);

        return stack;
    }

    public boolean isEmpty(ItemStack stack) {
        return getBlock(stack) == Blocks.AIR && !holdsEntity(stack);
    }

    public boolean isMilkBucket(ItemStack stack) {
        return holdsEntity(stack) && stack.get(ModComponents.BUCKET_FISH_COMPONENT).equals(getItemIdentifier(Items.MILK_BUCKET));
    }

    public boolean holdsFluid(ItemStack stack) {
        return getBlock(stack) instanceof LiquidBlock;
    }

    public boolean holdsBlock(ItemStack stack) {
        return getBlock(stack) instanceof BucketPickup && !holdsFluid(stack) && !holdsEntity(stack);
    }

    public boolean holdsEntity(ItemStack stack) {
        return stack.has(ModComponents.BUCKET_FISH_COMPONENT);
    }

    @Override
    public @NonNull Component getName(@NonNull ItemStack stack) {
        Component defaultEmpty = Component.translatable(this.getDescriptionId(), "");

        Identifier mobBucketIdentifier = stack.get(ModComponents.BUCKET_FISH_COMPONENT);
        Item vanillaBucket = itemFromIdentifier(mobBucketIdentifier);

        if(holdsEntity(stack) && vanillaBucket instanceof MobBucketItem mobBucketItem) {
            EntityType<?> entityType = mobBucketItem.type;

            String descriptionId = this.getDescriptionId();
            Component argument;
            descriptionId += ".entity";
            argument = entityType.getDescription();

            return Component.translatable(descriptionId, argument);
        } else if(holdsEntity(stack)) { // Milk
            String descriptionId = this.getDescriptionId();
            Component argument;
            descriptionId += ".milked";
            argument = Component.translatable(Items.MILK_BUCKET.getDescriptionId());
            return Component.translatable(descriptionId, argument);
        }

        Block block = getBlock(stack);

        if(block == Blocks.AIR) return defaultEmpty;

        if(block instanceof LiquidBlock liquidBlock) {
            Fluid fluid = liquidBlock.fluid;

            if(fluid == Fluids.EMPTY) return defaultEmpty;

            String descriptionId = this.getDescriptionId();
            Component argument;
            descriptionId += ".filled";
            argument = getFluidDescription(fluid);

            return Component.translatable(descriptionId, argument);
        } else { // TODO: Assume it's a BucketPickup
            String descriptionId = this.getDescriptionId();
            Component argument;
            descriptionId += ".filled";
            argument = getBlockDescription(block);

            return Component.translatable(descriptionId, argument);
        }
    }

    protected Component getFluidDescription(Fluid fluid) {
        return FluidVariantAttributes.getName(FluidVariant.of(fluid));
    }

    protected Component getBlockDescription(Block block) {
        return block.getName();
    }

    /**
     * @return The block corresponding to the bucket. Can be LiquidBlock or BucketPickup implementation.
     */
    public Block getBlock(ItemStack stack) {
        return fromIdentifier(stack.get(ModComponents.BUCKET_BLOCK_COMPONENT));
    }

    /**
     * @return The entity vanilla bucket item inside the bucket. Can be null if no entity is inside.
     */
    public Item getVanillaEntityBucket(ItemStack stack) {
        return itemFromIdentifier(stack.get(ModComponents.BUCKET_FISH_COMPONENT));
    }

    public static Block fromIdentifier(Identifier identifier) {
        return BuiltInRegistries.BLOCK.getValue(identifier);
    }

    public static Item itemFromIdentifier(Identifier identifier) {
        return BuiltInRegistries.ITEM.getValue(identifier);
    }

    public static Identifier getBlockIdentifier(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    public static Identifier getItemIdentifier(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }

    @Override
    public @NonNull InteractionResult use(final @NonNull Level level, final Player player, final @NonNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if(isMilkBucket(itemStack)) {
            // Thanks to BucketItemMixin
            return super.use(level, player, hand);
        }

        Block blockContent = getBlock(itemStack);
        Fluid content = blockContent instanceof LiquidBlock liquidBlock ? liquidBlock.fluid : Fluids.EMPTY;

        BlockHitResult hitResult = getPlayerPOVHitResult(
                level, player, content == Fluids.EMPTY ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE
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
                BlockPos placePos = clicked.getBlock() instanceof LiquidBlockContainer && content == Fluids.WATER ? pos : directionOffsetPos;

                // FOR PLACEMENT
                if (this.emptyContents(player, level, placePos, hitResult)) {
                    this.checkExtraContent(player, level, itemStack, placePos);
                    if (player instanceof ServerPlayer && content != Fluids.EMPTY) {
                        CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, placePos, itemStack);
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                    ItemStack emptyResult = ItemUtils.createFilledResult(itemStack, player, getEmptySuccessItem(player, itemStack));
                    return InteractionResult.SUCCESS.heldItemTransformedTo(emptyResult);
                } else {
                    // FOR PICKUP
                    if (content == Fluids.EMPTY) {
                        InteractionResult interactionResult = pickup(level, player, pos, itemStack);
                        if (interactionResult != null) {
                            return interactionResult;
                        }
                    }

                    return InteractionResult.FAIL;
                }
            } else {
                return InteractionResult.FAIL;
            }
        }
    }

    public InteractionResult pickup(@NonNull Level level, @Nullable Player player, BlockPos pos, ItemStack itemStack) {
        Item item = itemStack.getItem();

        if(!(item instanceof CustomBucketItem customBucketItem)) {
            return InteractionResult.PASS;
        }

        if(customBucketItem.isMilkBucket(itemStack)) {
            return InteractionResult.PASS;
        }

        BlockState blockState = level.getBlockState(pos);

        if (blockState.getBlock() instanceof BucketPickup bucketPickupBlock && customBucketItem.isEmpty(itemStack)) {
            ItemStack taken = mix(bucketPickupBlock.pickupBlock(player, level, pos, blockState), itemStack);
            taken.set(ModComponents.BUCKET_BLOCK_COMPONENT, BuiltInRegistries.BLOCK.getKey(blockState.getBlock()));
            //taken.set(DataComponents.MAX_STACK_SIZE, 1);

            if (!taken.isEmpty()) {
                if(player != null) {
                    player.awardStat(Stats.ITEM_USED.get(this));
                }

                bucketPickupBlock.getPickupSound().ifPresent(soundEvent -> {
                    if(player != null) {
                        player.playSound(soundEvent, 1.0F, 1.0F);
                    } else {
                        level.playSound(null, pos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
                    }
                });

                level.gameEvent(player, GameEvent.FLUID_PICKUP, pos);

                // Manage for player inventory if not dispenser
                ItemStack result = player != null ? ItemUtils.createFilledResult(itemStack, player, taken) : taken;

                // Also checks if not null at the same time
                if (!level.isClientSide() && player instanceof ServerPlayer) {
                    CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, taken);
                }

                return InteractionResult.SUCCESS.heldItemTransformedTo(result);
            }
        }

        return InteractionResult.PASS;
    }

    /**
     * Mix a custom bucket item with a vanilla bucket item
     */
    public static @NonNull ItemStack mix(ItemStack vanillaBucketStack, ItemStack customBucketStack) {
        ItemStack result = customBucketStack.copy();

        Item vanillaBucketItem = vanillaBucketStack.getItem();

        boolean exchangeWithCustomBucket = vanillaBucketItem instanceof CustomBucketItem;

        Block block = Blocks.AIR;

        switch (vanillaBucketItem) {
            case MobBucketItem vanillaMobBucket when !exchangeWithCustomBucket -> {
                result.set(ModComponents.BUCKET_FISH_COMPONENT, getItemIdentifier(vanillaMobBucket));

                Fluid fluid = ((BucketItemAccessor) vanillaMobBucket).getContent();
                block = fluid.defaultFluidState().createLegacyBlock().getBlock();
            }

            case SolidBucketItem vanillaSolidBucket -> block = vanillaSolidBucket.getBlock();

            case BucketItem vanillaBucket -> {
                Fluid fluid = ((BucketItemAccessor) vanillaBucket).getContent();
                block = fluid.defaultFluidState().createLegacyBlock().getBlock();
            }

            default -> {
            }
        }

        result.set(ModComponents.BUCKET_BLOCK_COMPONENT, getBlockIdentifier(block));
        //result.set(DataComponents.MAX_STACK_SIZE, block == Blocks.AIR ? 16 : 1);
        return result;
    }

    public @NonNull ItemStack getEmptySuccessItem(final Player player, final ItemStack itemStack) {
        return !player.hasInfiniteMaterials() ? getEmpty() : itemStack;
    }

    @Override
    public void checkExtraContent(@Nullable final LivingEntity user, final Level level, final ItemStack itemStack, final BlockPos pos) {
        Item item = itemFromIdentifier(itemStack.get(ModComponents.BUCKET_FISH_COMPONENT));

        if (level instanceof ServerLevel && holdsEntity(itemStack) && item instanceof MobBucketItem mobBucketItem) {
            mobBucketItem.spawn((ServerLevel)level, itemStack, pos);
            level.gameEvent(user, GameEvent.ENTITY_PLACE, pos);

            // Remove if dispenser or smth like that, or not creative mode
            if(user == null || !user.hasInfiniteMaterials()) {
                itemStack.remove(ModComponents.BUCKET_FISH_COMPONENT);
            }
            //itemStack.set(DataComponents.MAX_STACK_SIZE, 16);
        }
    }

    @Override
    public boolean emptyContents(@Nullable final LivingEntity user, final Level level, final BlockPos pos, @Nullable final BlockHitResult hitResult) {
        ItemStack itemStack = user == null ? ItemStack.EMPTY : user.getItemInHand(InteractionHand.MAIN_HAND);
        return emptyContents(user, level, pos, hitResult, itemStack);
    }

    @Override
    public boolean emptyContents(@Nullable LivingEntity user, Level level, BlockPos pos, @Nullable BlockHitResult hitResult, ItemStack itemStack) {
        Block content = getBlock(itemStack);

        if(isMilkBucket(itemStack)) {
            return false;
        }

        if (holdsFluid(itemStack) && content instanceof LiquidBlock liquidBlock) {
            Fluid fluid = liquidBlock.fluid;
            if(fluid instanceof FlowingFluid flowingFluid) {
                return emptyContentsFluid(flowingFluid, user, level, pos, hitResult);
            }
            return false;

        } else if(holdsBlock(itemStack)) {
            // TODO: Might duplicate behaviour with "use", migrate code to "use" if possible
            InteractionResult interactionResult = useOn(new UseOnContext(level, (Player) user, InteractionHand.MAIN_HAND, itemStack, hitResult != null ? hitResult : new BlockHitResult(pos.getCenter(), Direction.DOWN, pos, false)));
            return interactionResult.consumesAction();
        } else if(holdsEntity(itemStack)) {
            return true;
        }

        return false;
    }

    public boolean emptyContentsFluid(@NonNull FlowingFluid flowingFluid, @Nullable final LivingEntity user, final Level level, final BlockPos pos, @Nullable final BlockHitResult hitResult) {
        BlockState blockState = level.getBlockState(pos);
        Block block = blockState.getBlock();

        boolean mayReplace = blockState.canBeReplaced(flowingFluid);
        boolean shiftKeyDown = user != null && user.isShiftKeyDown();
        boolean placeLiquid = mayReplace || block instanceof LiquidBlockContainer container && container.canPlaceLiquid(user, level, pos, blockState, flowingFluid);
        boolean canPlaceFluidInsideBlock = blockState.isAir() || placeLiquid && (!shiftKeyDown || hitResult == null);

        if (!canPlaceFluidInsideBlock) {
            return hitResult != null && this.emptyContents(user, level, hitResult.getBlockPos().relative(hitResult.getDirection()), null);
        } else if (LevelUtils.doWaterEvaporate(level, pos) && flowingFluid.is(FluidTags.WATER)) {
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            RandomSource random = level.getRandom();
            level.playSound(user, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 2.6F + (random.nextFloat() - random.nextFloat()) * 0.8F);

            for (int i = 0; i < 8; i++) {
                level.addParticle(ParticleTypes.LARGE_SMOKE, x + random.nextFloat(), y + random.nextFloat(), z + random.nextFloat(), 0.0, 0.0, 0.0);
            }

            return true;
        } else if (block instanceof LiquidBlockContainer containerx && flowingFluid == Fluids.WATER) {
            containerx.placeLiquid(level, pos, blockState, flowingFluid.getSource(false));
            this.playEmptySound(user, level, pos);
            return true;
        } else {
            if (!level.isClientSide() && mayReplace && !blockState.liquid()) {
                level.destroyBlock(pos, true);
            }

            if (!level.setBlock(pos, flowingFluid.defaultFluidState().createLegacyBlock(), 11) && !blockState.getFluidState().isSource()) {
                return false;
            } else {
                this.playEmptySound(user, level, pos);
                return true;
            }
        }
    }

    public void playEmptySound(@Nullable final LivingEntity user, final LevelAccessor level, final BlockPos pos) {
        ItemStack itemStack = user == null ? ItemStack.EMPTY : user.getItemInHand(InteractionHand.MAIN_HAND);

        Item vanillaMobBucket = itemFromIdentifier(itemStack.get(ModComponents.BUCKET_FISH_COMPONENT));
        if(holdsEntity(itemStack) && vanillaMobBucket instanceof MobBucketItem mobBucketItem) {
            mobBucketItem.playEmptySound(user, level, pos);
            return;
        }

        Block blockContent = getBlock(itemStack);
        Fluid content = blockContent instanceof LiquidBlock liquidBlock ? liquidBlock.fluid : Fluids.EMPTY;

        SoundEvent soundEvent = content.is(FluidTags.LAVA) ? SoundEvents.BUCKET_EMPTY_LAVA : SoundEvents.BUCKET_EMPTY;
        level.playSound(user, pos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.gameEvent(user, GameEvent.FLUID_PLACE, pos);
    }

    public @NonNull InteractionResult useOn(final @NonNull UseOnContext context) {
        if(isMilkBucket(context.getItemInHand())) {
            return InteractionResult.PASS;
        }

        InteractionResult placeResult = this.place(new BlockPlaceContext(context));
        Player player = context.getPlayer();
        if (placeResult.consumesAction() && player != null) {
            player.setItemInHand(context.getHand(), getEmptySuccessItem(player, context.getItemInHand()));
        }

        return placeResult;
    }

    public InteractionResult place(final BlockPlaceContext placeContext) {
        Block block = this.getBlock(placeContext.getItemInHand());

        BucketPickup bucketPickup = block instanceof BucketPickup bucketPickupBlock ? bucketPickupBlock : null;
        // If not bucket pickup or liquid block, pass
        if(bucketPickup == null || block instanceof LiquidBlock) return InteractionResult.PASS;

        if (!block.isEnabled(placeContext.getLevel().enabledFeatures())) {
            return InteractionResult.FAIL;
        } else if (!placeContext.canPlace()) {
            return InteractionResult.FAIL;
        } else {
            BlockPlaceContext updatedPlaceContext = this.updatePlacementContext(placeContext);
            if (updatedPlaceContext == null) {
                return InteractionResult.FAIL;
            } else {
                BlockState placementState = this.getPlacementState(updatedPlaceContext);
                if (placementState == null) {
                    return InteractionResult.FAIL;
                } else if (!this.placeBlock(updatedPlaceContext, placementState)) {
                    return InteractionResult.FAIL;
                } else {
                    BlockPos pos = updatedPlaceContext.getClickedPos();
                    Level level = updatedPlaceContext.getLevel();
                    Player player = updatedPlaceContext.getPlayer();
                    ItemStack itemStack = updatedPlaceContext.getItemInHand();
                    BlockState placedState = level.getBlockState(pos);
                    if (placedState.is(placementState.getBlock())) {
                        placedState = this.updateBlockStateFromTag(pos, level, itemStack, placedState);
                        BlockItem.updateCustomBlockEntityTag(level, player, pos, itemStack);
                        updateBlockEntityComponents(level, pos, itemStack);
                        placedState.getBlock().setPlacedBy(level, pos, placedState, player, itemStack);
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, pos, itemStack);
                        }
                    }

                    SoundType soundType = placedState.getSoundType();
                    bucketPickup.getPickupSound().ifPresent(soundEvent ->
                            level.playSound(player, pos, soundEvent, SoundSource.BLOCKS, (soundType.getVolume() + 1.0F) / 2.0F, soundType.getPitch() * 0.8F)
                    );

                    level.gameEvent(GameEvent.BLOCK_PLACE, pos, GameEvent.Context.of(player, placedState));
                    itemStack.consume(1, player);
                    return InteractionResult.SUCCESS;
                }
            }
        }
    }

    @Nullable
    public BlockPlaceContext updatePlacementContext(final BlockPlaceContext context) {
        return context;
    }

    @Nullable
    protected BlockState getPlacementState(final BlockPlaceContext context) {
        BlockState stateForPlacement = this.getBlock(context.getItemInHand()).getStateForPlacement(context);
        return stateForPlacement != null && this.canPlace(context, stateForPlacement) ? stateForPlacement : null;
    }

    protected boolean canPlace(final BlockPlaceContext context, final BlockState stateForPlacement) {
        Player player = context.getPlayer();
        return (!this.mustSurvive() || stateForPlacement.canSurvive(context.getLevel(), context.getClickedPos()))
                && context.getLevel().isUnobstructed(stateForPlacement, context.getClickedPos(), CollisionContext.placementContext(player));
    }

    protected boolean mustSurvive() {
        return true;
    }

    protected boolean placeBlock(final BlockPlaceContext context, final BlockState placementState) {
        return context.getLevel().setBlock(context.getClickedPos(), placementState, 11);
    }

    private BlockState updateBlockStateFromTag(final BlockPos pos, final Level level, final ItemStack itemStack, final BlockState placedState) {
        BlockItemStateProperties blockState = itemStack.getOrDefault(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY);
        if (blockState.isEmpty()) {
            return placedState;
        } else {
            BlockState modifiedState = blockState.apply(placedState);
            if (modifiedState != placedState) {
                level.setBlock(pos, modifiedState, 2);
            }

            return modifiedState;
        }
    }

    private static void updateBlockEntityComponents(final Level level, final BlockPos pos, final ItemStack itemStack) {
        BlockEntity entity = level.getBlockEntity(pos);
        if (entity != null) {
            entity.applyComponentsFromItemStack(itemStack);
            entity.setChanged();
        }
    }

}
//?}
