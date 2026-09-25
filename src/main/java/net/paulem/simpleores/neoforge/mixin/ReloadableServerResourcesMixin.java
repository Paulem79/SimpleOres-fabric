package net.paulem.simpleores.neoforge.mixin;

import org.spongepowered.asm.mixin.Mixin;

//? if neoforge && >=26.2 && <26.3 {
/*import net.minecraft.server.ReloadableServerResources;
import net.minecraft.server.ServerAdvancementManager;
import net.paulem.simpleores.neoforge.AdvancementPatcher;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ReloadableServerResources.class)
public abstract class ReloadableServerResourcesMixin {
    @Shadow @Final private ServerAdvancementManager advancements;

    // The default components of the fluids are bound at the end of this method
    @Inject(method = "updateComponentsAndStaticRegistryTags", at = @At("TAIL"))
    private void simpleores$patchAdvancements(CallbackInfo ci) {
        ((AdvancementPatcher) advancements).simpleores$patch();
    }
}
*///?} else {
@Mixin
public abstract class ReloadableServerResourcesMixin {}
//?}
