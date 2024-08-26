package io.github.paulem.simpleores.tags;

import io.github.paulem.simpleores.SimpleOres;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> SIMPLEORES_ORES =
                createTag("simpleores_ores");

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(SimpleOres.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> SHEARS =
                createTag("shears");

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(SimpleOres.MOD_ID, name));
        }
    }
}