package net.paulem.simpleores.mixin.furnaces;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.paulem.simpleores.furnaces.ModFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//? if <=1.21
//import net.minecraft.world.level.Level;

@Mixin(AbstractFurnaceBlockEntity.class)
public class AbstractFurnaceEntityMixin extends BlockEntity {
    public AbstractFurnaceEntityMixin(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState) {
        super(type, worldPosition, blockState);
    }

    @Inject(
            method = "getTotalCookTime",
            at = @At("RETURN"), cancellable = true)
    private static void modifyCookTime(//? if <=1.21 {
                                       //Level
                                       //?} else {
            ServerLevel
                    //?}
                    level, AbstractFurnaceBlockEntity entity, CallbackInfoReturnable<Integer> cir) {
        Integer original = cir.getReturnValue();

        if(entity instanceof ModFurnaceBlockEntity furnaceBlockEntity) {
            cir.setReturnValue((int) (original / furnaceBlockEntity.getSpeedModifier()));
        }
    }
}
