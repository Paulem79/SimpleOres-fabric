package net.paulem.simpleores.mixin.accessor;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Set;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;

@Mixin(HolderSet.Direct.class)
public interface HolderSetDirectAccessor<T> {

    @Accessor
    List<Holder<T>> getContents();

    @Accessor
    @Final
    @Mutable
    void setContents(List<Holder<T>> contents);

    @Accessor
    void setContentsSet(Set<Holder<T>> contentsSet);

}
