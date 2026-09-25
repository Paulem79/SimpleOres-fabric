package net.paulem.simpleores.mixin;

import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.paulem.simpleores.utils.MatchToolShears;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//? if <=1.20.1 {
/*import net.paulem.simpleores.mixin.accessor.ItemPredicateAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
*///?}

@Mixin(MatchTool.class)
public abstract class MatchToolConditionMixin implements LootItemCondition {
    //? if <=1.20.1 {
    
    /*@SuppressWarnings("ShadowModifiers")
    @Shadow @Final private ItemPredicate predicate;
     
     *///?}

    @Inject(at = @At("RETURN"), method = "<init>")
    private void initProxy(CallbackInfo ci) {
        //? if <=1.20.1 {
        /*if (((ItemPredicateAccessor) predicate).getItems() != null) MatchToolShears.track(predicate);
        *///?} else {
        ((MatchTool)(Object)this).predicate().ifPresent(MatchToolShears::track);
        //?}
    }
}
