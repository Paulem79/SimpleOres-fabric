package net.paulem.simpleores.items.custom.bucket;

//? if hasBucketlib || !containsBucket {
/*public class CustomBucketItem {}
*///?} else {

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.BlockItemStateProperties;
//? if afterDeobf {
import net.minecraft.world.item.component.BrewingFuel;
import net.minecraft.world.item.component.CookingFuel;
//?}
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
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.paulem.simpleores.items.ModComponents;
import net.paulem.simpleores.utils.BucketFluids;
import net.paulem.simpleores.utils.BucketPickupUtils;
import net.paulem.simpleores.utils.LevelUtils;
import org.jspecify.annotations.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.material.Fluids;
import org.jspecify.annotations.NonNull;

/**
 * A bucket which stores its content inside data components instead of having one item per content.
 * <p>
 * Invariants kept by every method of this class:
 * <ul>
 *     <li>A <b>filled</b> bucket is always a freshly created stack of count 1 carrying a per stack
 *     {@link DataComponents#MAX_STACK_SIZE} of 1.</li>
 *     <li>An <b>empty</b> bucket is always a pristine {@code new ItemStack(this)} without any component patch,
 *     so that it stacks with every other empty bucket.</li>
 *     <li>No method ever mutates a stack it did not create itself.</li>
 * </ul>
 */
public class CustomBucketItem extends MobBucketItem implements CustomDispensibleContainerItem {
    private final Properties properties;

    public Properties getProperties() {
        return properties;
    }

    public static class Properties extends Item.Properties {
        private boolean enabledMilking;
        private int meltTemperature;
        private int fireTemperature;

        public Properties() {
            this.enabledMilking = true;
            this.meltTemperature = 1000;
            this.fireTemperature = 1300;
        }

        public Properties milkingEnabled(boolean enabledMilking) {
            this.enabledMilking = enabledMilking;
            return this;
        }

        public Properties meltTemperature(int meltTemperature) {
            this.meltTemperature = meltTemperature;
            return this;
        }

        public Properties fireTemperature(int fireTemperature) {
            this.fireTemperature = fireTemperature;
            return this;
        }

        public boolean isMilkingEnabled() {
            return enabledMilking;
        }

        public int getMeltTemperature() {
            return meltTemperature;
        }

        public int getFireTemperature() {
            return fireTemperature;
        }
    }

    public CustomBucketItem(Item.Properties settings) {
        super(null, Fluids.EMPTY, SoundEvents.EMPTY, settings
                        .component(ModComponents.BUCKET_BLOCK_COMPONENT, getBlockIdentifier(Blocks.AIR))
        );

        this.properties = (Properties) settings;

        DispenserBlock.registerBehavior(this, CustomBucketDispenseBehaviour.getInstance());
    }

    /**
     * @return a copy of {@code source} of count 1 which may hold a content. Never returns the given stack.
     */
    private static ItemStack single(ItemStack source) {
        ItemStack copy = source.copyWithCount(1);
        copy.set(DataComponents.MAX_STACK_SIZE, 1);
        return copy;
    }

    /**
     * Removes every content marker of a stack we own. Must never be called on a stack held by someone else.
     */
    private static void clearContents(ItemStack owned) {
        owned.remove(ModComponents.BUCKET_FISH_COMPONENT);
        owned.remove(DataComponents.CONSUMABLE);
        owned.remove(DataComponents.USE_REMAINDER);
        //? if afterDeobf {
        owned.remove(DataComponents.COOKING_FUEL);
        owned.remove(DataComponents.BREWING_FUEL);
        //?}
    }

    /**
     * Copies the behaviour the matching vanilla bucket gets from its own components onto a bucket we own,
     * so that a bucket of lava burns in a furnace exactly like the vanilla one.
     * Older versions do not store the fuels in components, they are handled by {@code FuelValuesMixin}.
     */
    private static void applyVanillaComponents(ItemStack owned) {
        //? if afterDeobf {
        if(!(owned.getItem() instanceof CustomBucketItem bucketItem)) return;

        ItemStack vanillaBucket = bucketItem.toVanillaBucket(owned);
        if(vanillaBucket.isEmpty()) return;

        CookingFuel cookingFuel = vanillaBucket.get(DataComponents.COOKING_FUEL);
        if(cookingFuel != null && !owned.has(DataComponents.COOKING_FUEL)) {
            owned.set(DataComponents.COOKING_FUEL, cookingFuel);
        }

        BrewingFuel brewingFuel = vanillaBucket.get(DataComponents.BREWING_FUEL);
        if(brewingFuel != null && !owned.has(DataComponents.BREWING_FUEL)) {
            owned.set(DataComponents.BREWING_FUEL, brewingFuel);
        }
        //?}
    }

    /**
     * @param itemStack the stack to base the result on. It is <b>never</b> modified.
     * @return a new bucket stack of count 1 holding the given block.
     */
    public ItemStack getCorrespondingBucket(@Nullable ItemStack itemStack, @Nullable Block block) {
        if(itemStack != null && holdsEntity(itemStack)) return itemStack.copy();
        if(block == null || block == Blocks.AIR) return getEmpty();

        ItemStack stack = single(itemStack != null ? itemStack : getDefaultInstance());
        clearContents(stack);
        stack.set(ModComponents.BUCKET_BLOCK_COMPONENT, getBlockIdentifier(block));
        applyVanillaComponents(stack);

        return stack;
    }

    public ItemStack getCorrespondingBucket(@Nullable ItemStack itemStack, @Nullable Fluid modFluid) {
        if(modFluid == null || modFluid == Fluids.EMPTY) return getEmpty();

        Block block = modFluid.defaultFluidState().createLegacyBlock().getBlock();
        return getCorrespondingBucket(itemStack, block);
    }

    public ItemStack getMilkBucket() {
        ItemStack stack = new ItemStack(this);
        stack.set(ModComponents.BUCKET_FISH_COMPONENT, getItemIdentifier(Items.MILK_BUCKET));
        stack.set(DataComponents.CONSUMABLE, Consumables.MILK_BUCKET);
        stack.set(DataComponents.USE_REMAINDER, new UseRemainder(//? afterDeobf
                ItemStackTemplate.fromNonEmptyStack
                        (getEmpty())));
        stack.set(DataComponents.MAX_STACK_SIZE, 1);

        return stack;
    }

    /**
     * @return a pristine empty bucket, without any component patch so that it stacks with the other empty buckets.
     */
    public ItemStack getEmpty() {
        return new ItemStack(this);
    }

    public boolean isEmpty(ItemStack stack) {
        return getBlock(stack) == Blocks.AIR && !holdsEntity(stack);
    }

    public boolean isMilkBucket(ItemStack stack) {
        return getItemIdentifier(Items.MILK_BUCKET).equals(stack.get(ModComponents.BUCKET_FISH_COMPONENT));
    }

    public boolean holdsFluid(ItemStack stack) {
        return getBlock(stack) instanceof LiquidBlock;
    }

    /**
     * @return the fluid held by the bucket, or {@link Fluids#EMPTY} if it holds no fluid.
     */
    public Fluid getFluid(ItemStack stack) {
        return getBlock(stack) instanceof LiquidBlock liquidBlock ? liquidBlock.fluid : Fluids.EMPTY;
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

        if(holdsEntity(stack) && isMilkBucket(stack)) { // Milk
            return Component.translatable(this.getDescriptionId() + ".milked",
                    Component.translatable(Items.MILK_BUCKET.getDescriptionId()));
        }

        if(holdsEntity(stack) && vanillaBucket instanceof MobBucketItem mobBucketItem) {
            EntityType<?> entityType = mobBucketItem.type;

            String descriptionId = this.getDescriptionId();
            Component argument;
            descriptionId += ".entity";
            argument = entityType.getDescription();

            return Component.translatable(descriptionId, argument);
        } else if(holdsEntity(stack)) {
            // Unknown entity bucket: the mod which added it is most likely not loaded anymore
            return defaultEmpty;
        }

        Block block = getBlock(stack);

        if(block == Blocks.AIR) return defaultEmpty;

        Fluid fluid = getFluid(stack);

        if(fluid != Fluids.EMPTY) {
            return Component.translatable(this.getDescriptionId() + ".filled", getFluidDescription(fluid));
        } else if(block instanceof LiquidBlock) {
            return defaultEmpty;
        } else { // Assume it's a BucketPickup
            return Component.translatable(this.getDescriptionId() + ".filled", getBlockDescription(block));
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
    public @Nullable Item getVanillaEntityBucket(ItemStack stack) {
        return itemFromIdentifier(stack.get(ModComponents.BUCKET_FISH_COMPONENT));
    }

    public static Block fromIdentifier(@Nullable Identifier identifier) {
        // The component is missing on stacks given by other mods, commands or older saves
        return identifier == null ? Blocks.AIR : BuiltInRegistries.BLOCK.getValue(identifier);
    }

    public static @Nullable Item itemFromIdentifier(@Nullable Identifier identifier) {
        return identifier == null ? null : BuiltInRegistries.ITEM.getValue(identifier);
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

        Fluid content = getFluid(itemStack);

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
                if (this.emptyContents(player, level, placePos, hitResult, itemStack)) {
                    this.checkExtraContent(player, level, itemStack, placePos);
                    if (player instanceof ServerPlayer && content != Fluids.EMPTY) {
                        CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, placePos, itemStack);
                    }

                    player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));

                    boolean shouldMelt = shouldMelt(itemStack);
                    if(shouldMelt && !player.hasInfiniteMaterials()) {
                        if(!level.isClientSide()) {
                            // As equipment slot for compat in 1.21.5
                            EquipmentSlot slot = //? if >=1.21.9 {
                                    hand.asEquipmentSlot();
                            //?} else {
                            //hand == InteractionHand.OFF_HAND ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
                            //?}
                            player.onEquippedItemBroken(itemStack.getItem(), slot);
                            return InteractionResult.SUCCESS.heldItemTransformedTo(ItemStack.EMPTY);
                        } else {
                            return InteractionResult.SUCCESS;
                        }
                    }

                    // A filled bucket is always a stack of one, so the whole held stack is replaced
                    return InteractionResult.SUCCESS.heldItemTransformedTo(getEmptySuccessItem(player, itemStack, shouldMelt));
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

    public boolean shouldMelt(ItemStack itemStack) {
        return getFluidTemperature(itemStack) >= properties.getMeltTemperature();
    }

    public int getFluidTemperature(ItemStack itemStack) {
        Fluid fluid = getFluid(itemStack);

        if(fluid == Fluids.EMPTY) {
            // Modded blocks picked up by a bucket may not be backed by a LiquidBlock
            Block block = getBlock(itemStack);
            if(block == Blocks.AIR) return 0;

            Item bucket = BucketPickupUtils.getBucketForBlock(block);
            fluid = bucket == null ? Fluids.EMPTY : BucketFluids.contentOf(bucket.getDefaultInstance());
        }

        if(fluid == null || fluid == Fluids.EMPTY) return 0;

        return FluidVariantAttributes.getTemperature(FluidVariant.of(fluid));
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
            ItemStack pickedUp = bucketPickupBlock.pickupBlock(player, level, pos, blockState);
            if (pickedUp.isEmpty()) return InteractionResult.PASS;

            // mix() always returns a fresh stack of one, the held one is only decremented by createFilledResult
            ItemStack taken = mix(pickedUp, itemStack);
            taken.set(ModComponents.BUCKET_BLOCK_COMPONENT, getBlockIdentifier(blockState.getBlock()));
            taken.set(DataComponents.MAX_STACK_SIZE, 1);
            applyVanillaComponents(taken);

            if (!taken.isEmpty()) {
                if(player != null) {
                    player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
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
     * Mix a custom bucket item with a vanilla (or modded) bucket item.
     *
     * @param customBucketStack the held stack. It is <b>never</b> modified.
     * @return a new custom bucket stack of count one holding the content of the given bucket,
     * or a pristine empty bucket if that content could not be resolved.
     */
    public static @NonNull ItemStack mix(ItemStack vanillaBucketStack, ItemStack customBucketStack) {
        ItemStack result = single(customBucketStack);
        // The held bucket may still hold a previous content (mob, milk...) which must not survive
        clearContents(result);

        Item vanillaBucketItem = vanillaBucketStack.getItem();

        boolean exchangeWithCustomBucket = vanillaBucketItem instanceof CustomBucketItem;

        Block block = Blocks.AIR;

        switch (vanillaBucketItem) {
            case MobBucketItem vanillaMobBucket when !exchangeWithCustomBucket -> {
                result.set(ModComponents.BUCKET_FISH_COMPONENT, getItemIdentifier(vanillaMobBucket));

                block = blockOf(BucketFluids.contentOf(vanillaBucketStack));
            }

            case SolidBucketItem vanillaSolidBucket -> block = vanillaSolidBucket.getBlock();

            case BucketItem vanillaBucket when !exchangeWithCustomBucket ->
                    block = blockOf(BucketFluids.contentOf(vanillaBucketStack));

            case BlockItem moddedSolidBucket when !exchangeWithCustomBucket -> block = moddedSolidBucket.getBlock();

            default -> {
                // Any other modded bucket item: ask the Fabric transfer API for its content
                if(!exchangeWithCustomBucket) block = blockOf(BucketFluids.contentOf(vanillaBucketStack));
            }
        }

        if(block == Blocks.AIR && !result.has(ModComponents.BUCKET_FISH_COMPONENT)) {
            // Nothing could be resolved, keep an empty bucket which stacks with the other ones
            return customBucketStack.getItem() instanceof CustomBucketItem customBucketItem
                    ? customBucketItem.getEmpty()
                    : result;
        }

        result.set(ModComponents.BUCKET_BLOCK_COMPONENT, getBlockIdentifier(block));
        applyVanillaComponents(result);
        return result;
    }

    private static Block blockOf(@Nullable Fluid fluid) {
        if(fluid == null || fluid == Fluids.EMPTY) return Blocks.AIR;
        return fluid.defaultFluidState().createLegacyBlock().getBlock();
    }

    /**
     * The exact opposite of {@link #mix(ItemStack, ItemStack)}: gives back the vanilla (or modded) bucket
     * matching the content of this bucket. It is what makes the custom bucket usable in every recipe
     * asking for a vanilla bucket.
     *
     * @return a new vanilla bucket stack of count one, or {@link ItemStack#EMPTY} if the content is unknown.
     */
    public ItemStack toVanillaBucket(ItemStack stack) {
        if(holdsEntity(stack)) {
            Item entityBucket = getVanillaEntityBucket(stack);
            if(entityBucket == null) return ItemStack.EMPTY;

            ItemStack vanilla = entityBucket.getDefaultInstance();
            // Keeps the entity data (custom name, health...) so that placing it back gives the same mob
            vanilla.applyComponents(stack.getComponentsPatch());
            vanilla.remove(ModComponents.BUCKET_BLOCK_COMPONENT);
            vanilla.remove(ModComponents.BUCKET_FISH_COMPONENT);
            vanilla.remove(DataComponents.MAX_STACK_SIZE);
            return vanilla;
        }

        Block block = getBlock(stack);

        if(block == Blocks.AIR) return Items.BUCKET.getDefaultInstance();

        Fluid fluid = getFluid(stack);
        if(fluid != Fluids.EMPTY) return fluid.getBucket().getDefaultInstance();

        Item solidBucket = BucketPickupUtils.getBucketForBlock(block);
        return solidBucket == null ? ItemStack.EMPTY : solidBucket.getDefaultInstance();
    }

    /**
     * Stack aware crafting remainder, mirroring what the matching vanilla bucket gives back: an empty bucket
     * for a milk, water or lava bucket, and nothing for an empty bucket (which would otherwise be duplicated)
     * or for an entity bucket, exactly like the vanilla ones.
     */
    public boolean leavesEmptyBucketWhenCrafted(ItemStack stack) {
        if(isEmpty(stack)) return false;

        ItemStack vanilla = toVanillaBucket(stack);
        if(vanilla.isEmpty()) return false;

        //? if afterDeobf {
        return vanilla.getItem().getCraftingRemainder() != null;
        //?} else {
        /*return !vanilla.getItem().getCraftingRemainder().isEmpty();
        *///?}
    }

    //? if afterDeobf {
    @Override
    public @Nullable ItemStackTemplate getCraftingRemainder(ItemStack stack) {
        return leavesEmptyBucketWhenCrafted(stack) ? ItemStackTemplate.fromNonEmptyStack(getEmpty()) : null;
    }
    //?} else {
    /*@Override
    public ItemStack getRecipeRemainder(ItemStack stack) {
        return leavesEmptyBucketWhenCrafted(stack) ? getEmpty() : ItemStack.EMPTY;
    }
    *///?}

    /**
     * Temperature used as a reference for an entity which is burning without standing in a hot fluid.
     */
    public static final int FIRE_TEMPERATURE = 1000;

    /**
     * Burns the bucket when its holder stands in a fluid (or a fire) hotter than the bucket can bear.
     * This mirrors the {@code burningTemperature} behaviour of the BucketLib flavour of this bucket.
     */
    @Override
    public void inventoryTick(final @NonNull ItemStack stack, final @NonNull ServerLevel level, final @NonNull Entity entity, final @Nullable EquipmentSlot slot) {
        super.inventoryTick(stack, level, entity, slot);

        // Heals the buckets filled before this behaviour existed
        if(!isEmpty(stack)) applyVanillaComponents(stack);

        if(!shouldBurn(entity)) return;

        level.playSound(null, entity.blockPosition(), SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 0.5F, 2.0F);
        stack.setCount(0);
    }

    public boolean shouldBurn(Entity entity) {
        if(entity instanceof Player player && player.hasInfiniteMaterials()) return false;

        int temperature;
        if(entity.isInLava()) {
            temperature = FluidVariantAttributes.getTemperature(FluidVariant.of(Fluids.LAVA));
        } else if(entity.isOnFire()) {
            temperature = FIRE_TEMPERATURE;
        } else {
            return false;
        }

        return properties.getFireTemperature() <= temperature;
    }

    public @NonNull ItemStack getEmptySuccessItem(final Player player, final ItemStack itemStack, boolean shouldBreak) {
        return !player.hasInfiniteMaterials() ? (shouldBreak ? ItemStack.EMPTY : getEmpty()) : itemStack;
    }

    @Override
    public void checkExtraContent(@Nullable final LivingEntity user, final Level level, final ItemStack itemStack, final BlockPos pos) {
        Item item = itemFromIdentifier(itemStack.get(ModComponents.BUCKET_FISH_COMPONENT));

        if (level instanceof ServerLevel && holdsEntity(itemStack) && item instanceof MobBucketItem mobBucketItem) {
            mobBucketItem.spawn((ServerLevel)level, itemStack, pos);
            level.gameEvent(user, GameEvent.ENTITY_PLACE, pos);
            // The held stack is never modified here: the callers replace it with an empty bucket,
            // or keep it untouched in creative mode.
        }
    }

    /**
     * @deprecated the held stack cannot be guessed from the user, use
     * {@link #emptyContents(LivingEntity, Level, BlockPos, BlockHitResult, ItemStack)} instead.
     */
    @Deprecated
    @Override
    public boolean emptyContents(@Nullable final LivingEntity user, final Level level, final BlockPos pos, @Nullable final BlockHitResult hitResult) {
        ItemStack itemStack = user == null ? ItemStack.EMPTY : getHeldBucket(user);
        return emptyContents(user, level, pos, hitResult, itemStack);
    }

    /**
     * @return the hand holding the given stack, main hand by default.
     */
    private static InteractionHand getHandFor(@Nullable LivingEntity user, ItemStack itemStack) {
        return user != null && user.getItemInHand(InteractionHand.OFF_HAND) == itemStack
                ? InteractionHand.OFF_HAND
                : InteractionHand.MAIN_HAND;
    }

    private static ItemStack getHeldBucket(LivingEntity user) {
        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack held = user.getItemInHand(hand);
            if(held.getItem() instanceof CustomBucketItem) return held;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean emptyContents(@Nullable LivingEntity user, Level level, BlockPos pos, @Nullable BlockHitResult hitResult, ItemStack itemStack) {
        if(isMilkBucket(itemStack)) {
            return false;
        }

        Fluid fluid = getFluid(itemStack);

        if (fluid != Fluids.EMPTY) {
            if(fluid instanceof FlowingFluid flowingFluid) {
                return emptyContentsFluid(flowingFluid, user, level, pos, hitResult, itemStack);
            }
            return false;

        } else if(holdsBlock(itemStack)) {
            // TODO: Might duplicate behaviour with "use", migrate code to "use" if possible
            Player player = user instanceof Player p ? p : null;
            InteractionResult interactionResult = useOn(new UseOnContext(level, player, getHandFor(user, itemStack), itemStack, hitResult != null ? hitResult : new BlockHitResult(Vec3.atCenterOf(pos), Direction.DOWN, pos, false)));
            return interactionResult.consumesAction();
        } else if(holdsEntity(itemStack)) {
            return true;
        }

        return false;
    }

    public boolean emptyContentsFluid(@NonNull FlowingFluid flowingFluid, @Nullable final LivingEntity user, final Level level, final BlockPos pos, @Nullable final BlockHitResult hitResult, final ItemStack itemStack) {
        BlockState blockState = level.getBlockState(pos);
        Block block = blockState.getBlock();

        boolean mayReplace = blockState.canBeReplaced(flowingFluid);
        boolean shiftKeyDown = user != null && user.isShiftKeyDown();
        boolean placeLiquid = mayReplace || block instanceof LiquidBlockContainer container && container.canPlaceLiquid(user, level, pos, blockState, flowingFluid);
        boolean canPlaceFluidInsideBlock = blockState.isAir() || placeLiquid && (!shiftKeyDown || hitResult == null);

        if (!canPlaceFluidInsideBlock) {
            return hitResult != null && this.emptyContents(user, level, hitResult.getBlockPos().relative(hitResult.getDirection()), null, itemStack);
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
            this.playEmptySound(user, level, pos, itemStack);
            return true;
        } else {
            if (!level.isClientSide() && mayReplace && !blockState.liquid()) {
                level.destroyBlock(pos, true);
            }

            if (!level.setBlock(pos, flowingFluid.defaultFluidState().createLegacyBlock(), 11) && !blockState.getFluidState().isSource()) {
                return false;
            } else {
                this.playEmptySound(user, level, pos, itemStack);
                return true;
            }
        }
    }

    public void playEmptySound(@Nullable final LivingEntity user, final LevelAccessor level, final BlockPos pos, final ItemStack itemStack) {
        Item vanillaMobBucket = itemFromIdentifier(itemStack.get(ModComponents.BUCKET_FISH_COMPONENT));
        if(holdsEntity(itemStack) && vanillaMobBucket instanceof MobBucketItem mobBucketItem) {
            mobBucketItem.playEmptySound(user, level, pos);
            return;
        }

        Fluid content = getFluid(itemStack);

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
        if (placeResult.consumesAction()) {
            // Single accounting point: place() must not consume the stack itself
            if (player != null) {
                player.setItemInHand(context.getHand(), ItemUtils.createFilledResult(context.getItemInHand(), player, getEmpty()));
            }
            // Without a player (dispenser), the remainder is handled by CustomBucketDispenseBehaviour
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
                    // The stack is consumed by useOn(), which is the single accounting point
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
