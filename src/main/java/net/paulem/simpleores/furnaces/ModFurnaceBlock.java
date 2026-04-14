package net.paulem.simpleores.furnaces;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.paulem.simpleores.tooltip.TooltipBlock;
import org.jspecify.annotations.Nullable;
//? if >1.20.1 {
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
//?}

public class ModFurnaceBlock extends AbstractFurnaceBlock implements TooltipBlock {
    private final double speedModifier;

    //? if >1.20.1 {
    public static final MapCodec<ModFurnaceBlock> CODEC = RecordCodecBuilder.mapCodec(i ->
            i.group(propertiesCodec())
                    .and(Codec.DOUBLE
                            .fieldOf("speed_modifier")
                            .forGetter(block -> block.speedModifier)
                    )
                    .apply(i, ModFurnaceBlock::new)
    );

    @Override
    public MapCodec<ModFurnaceBlock> codec() {
        return CODEC;
    }
    //?}

    public ModFurnaceBlock(final BlockBehaviour.Properties properties, final double speedModifier) {
        super(properties);

        this.speedModifier = speedModifier;
    }

    @Override
    public BlockEntity newBlockEntity(final BlockPos worldPosition, final BlockState blockState) {
        return new ModFurnaceBlockEntity(worldPosition, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(final Level level, final BlockState blockState, final BlockEntityType<T> type) {
        return createFurnaceTicker(level, type, ModFurnacesEntities.FABRIC_FURNACE);
    }

    @Override
    protected void openContainer(final Level level, final BlockPos pos, final Player player) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof ModFurnaceBlockEntity) {
            player.openMenu((MenuProvider)blockEntity);
            player.awardStat(Stats.INTERACT_WITH_FURNACE);
        }
    }

    @Override
    public void animateTick(final BlockState state, final Level level, final BlockPos pos, final RandomSource random) {
        if (state.getValue(LIT)) {
            double x = pos.getX() + 0.5;
            double y = pos.getY();
            double z = pos.getZ() + 0.5;
            if (random.nextDouble() < 0.1) {
                level.playLocalSound(x, y, z, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction direction = state.getValue(FACING);
            Direction.Axis axis = direction.getAxis();

            double ss = random.nextDouble() * 0.6 - 0.3;
            double dx = axis == Direction.Axis.X ? direction.getStepX() * 0.52 : ss;
            double dy = random.nextDouble() * 6.0 / 16.0;
            double dz = axis == Direction.Axis.Z ? direction.getStepZ() * 0.52 : ss;
            level.addParticle(ParticleTypes.SMOKE, x + dx, y + dy, z + dz, 0.0, 0.0, 0.0);
            level.addParticle(ParticleTypes.FLAME, x + dx, y + dy, z + dz, 0.0, 0.0, 0.0);
        }
    }

    @Override
    public void appendClientTooltip(ItemStack stack, TooltipAccept tooltips) {
        tooltips.accept(Component.translatable("tips.furnace.speed_modifier", String.format("%.1f", this.getSpeedModifier()))
                .withStyle(ChatFormatting.AQUA));
    }

    public double getSpeedModifier() {
        return speedModifier;
    }
}
