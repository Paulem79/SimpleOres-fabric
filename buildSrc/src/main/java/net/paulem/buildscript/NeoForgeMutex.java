package net.paulem.buildscript;

import org.gradle.api.services.BuildService;
import org.gradle.api.services.BuildServiceParameters;

/**
 * Shared build service used with {@code maxParallelUsages = 1}: NeoForm decompiles and recompiles Minecraft,
 * which takes several GB of memory, so only one NeoForge node may create its artifacts at a time.
 */
public abstract class NeoForgeMutex implements BuildService<BuildServiceParameters.None> {
}
