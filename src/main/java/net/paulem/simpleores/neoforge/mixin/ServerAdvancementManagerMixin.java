package net.paulem.simpleores.neoforge.mixin;

import org.spongepowered.asm.mixin.Mixin;

//? if neoforge && >=26.2 && <26.3 {
/*import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.paulem.simpleores.advancements.ModVanillaAdvancements;
import net.paulem.simpleores.neoforge.AdvancementPatcher;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;

import java.util.HashMap;
import java.util.Map;

/^*
 * NeoForge has no advancement API like Fabric, so the loaded advancements are patched once the server resources are
 * ready (see {@link ReloadableServerResourcesMixin}), which is what lets the custom buckets complete the vanilla bucket advancements.
 ^/
@Mixin(ServerAdvancementManager.class)
public abstract class ServerAdvancementManagerMixin implements AdvancementPatcher {
    @Shadow @Final private HolderLookup.Provider registries;
    @Shadow private Map<Identifier, AdvancementHolder> advancements;

    @Shadow
    protected abstract void apply(Map<Identifier, Advancement> preparations, ResourceManager manager, ProfilerFiller profiler);

    @Override
    public void simpleores$patch() {
        Map<Identifier, Advancement> patched = new HashMap<>();
        advancements.forEach((id, holder) -> patched.put(id, ModVanillaAdvancements.modify(id, holder.value(), registries)));

        // Rebuilds the advancements and the tree, the manager and profiler are not used by apply
        apply(patched, null, null);
    }
}
*///?} else {
@Mixin
public abstract class ServerAdvancementManagerMixin {}
//?}
