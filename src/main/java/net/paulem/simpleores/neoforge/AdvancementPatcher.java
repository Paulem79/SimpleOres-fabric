package net.paulem.simpleores.neoforge;

/**
 * Implemented by the ServerAdvancementManager through its mixin, so the other mixins can ask it to patch its advancements.
 */
public interface AdvancementPatcher {
    /**
     * Adds the criteria of the custom buckets to the vanilla advancements.
     * Has to be called once the default components are bound, as the criteria need the fluids to have their components.
     */
    void simpleores$patch();
}
