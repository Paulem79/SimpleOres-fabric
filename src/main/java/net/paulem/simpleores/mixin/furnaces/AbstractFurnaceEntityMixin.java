package net.paulem.simpleores.mixin.furnaces;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.paulem.simpleores.furnaces.ModFurnaceBlockEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//? if <=1.21
//import net.minecraft.world.level.Level;

@Mixin(AbstractFurnaceBlockEntity.class)
public class AbstractFurnaceEntityMixin extends BlockEntity {
    public AbstractFurnaceEntityMixin(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState) {
        super(type, worldPosition, blockState);
    }


    //? !afterDeobf
    //@Unique private static boolean skipSecondModifier = false;

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
            //? !afterDeobf
            //skipSecondModifier = true;
            cir.setReturnValue(simpleores$getCookingTotalTime(furnaceBlockEntity, original));
        }
    }

    //? afterDeobf {
    @Inject(
            method = "serverTick",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/level/block/entity/AbstractFurnaceBlockEntity;cookingTotalTime:I",
                    opcode = Opcodes.PUTFIELD,
                    shift = At.Shift.AFTER
            )
    )
    private static void modifyCookTime(ServerLevel level, BlockPos pos, BlockState state, AbstractFurnaceBlockEntity entity, CallbackInfo ci){
        int original = entity.cookingTotalTime;

        if(entity instanceof ModFurnaceBlockEntity furnaceBlockEntity) {
            entity.cookingTotalTime = simpleores$getCookingTotalTime(furnaceBlockEntity, original);
        }
    }
    //?} else {
    
    /*@Inject(
            method = "serverTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/entity/AbstractFurnaceBlockEntity;setChanged(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V",
                    shift = At.Shift.AFTER)
    )
    private static void modifyCookTime(//? if <=1.21 {
                                       //Level
                                       //?} else {
                                       ServerLevel
                                                   //?}
            level, BlockPos pos, BlockState state, AbstractFurnaceBlockEntity entity, CallbackInfo ci) {
        if(skipSecondModifier) {
            skipSecondModifier = false;
            return;
        }

        int original = entity.cookingTotalTime;

        if(entity instanceof ModFurnaceBlockEntity furnaceBlockEntity) {
            entity.cookingTotalTime = simpleores$getCookingTotalTime(furnaceBlockEntity, original);
        }
    }
    *///?}

    @Unique
    private static int simpleores$getCookingTotalTime(ModFurnaceBlockEntity furnaceBlockEntity, int original) {
        return (int) (original / furnaceBlockEntity.getSpeedModifier());
    }
}
