package io.github.paulem.simpleores.mixin.accessor;

import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Set;

@Mixin(RegistryEntryList.Direct.class)
public interface HolderSetDirectAccessor<T> {

    @Accessor
    List<RegistryEntry<T>> getEntries();

    @Accessor
    @Final
    @Mutable
    void setEntries(List<RegistryEntry<T>> contents);

    @Accessor
    void setEntrySet(Set<RegistryEntry<T>> contentsSet);

}
