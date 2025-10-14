package net.paulem.simpleores.tags;

import net.fabricmc.fabric.impl.tag.convention.v2.TagRegistration;
import net.paulem.simpleores.SimpleOres;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.paulem.simpleores.stonecutter.SCIdentifier;
import net.paulem.simpleores.stonecutter.SCTag;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> SIMPLEORES_ORES =
                createTag("ores");

        public static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, SCIdentifier.of(SimpleOres.MOD_ID, name));
        }

        public static class Conventional {

            public static TagKey<Block> createTag(String name) {
                //? if <1.20.5 {
                /*return TagRegistration.BLOCK_TAG_REGISTRATION.registerCommon(name);
                *///?} else {
                return TagRegistration.BLOCK_TAG.registerC(name);
                //?}
            }
        }
    }

    public static class Items {
        public static final TagKey<Item> SWORDS =
                createTag("swords");

        public static final TagKey<Item> PICKAXES =
                createTag("pickaxes");

        public static final TagKey<Item> AXES =
                createTag("axes");

        public static final TagKey<Item> SHOVELS =
                createTag("shovels");

        public static final TagKey<Item> HOES =
                createTag("hoes");

        public static final TagKey<Item> BOWS =
                createTag("bows");

        public static final TagKey<Item> ARMORS =
                createTag("armors");

        public static final TagKey<Item> SHEARS =
                createTag("shears");

        public static final TagKey<Item> BUCKETS = createTag("buckets");

        public static final TagKey<Item> INGOTS =
                createTag("ingots");

        public static final TagKey<Item> GEMS =
                createTag("gems");

        public static final TagKey<Item> NUGGETS =
                createTag("nuggets");

        public static final TagKey<Item> DUSTS =
                createTag("dusts");

        public static final TagKey<Item> CRUSHED_ORES =
                createTag("crushed_ores");

        public static final TagKey<Item> RODS =
                createTag("rods");

        public static final TagKey<Item> REPAIRS_TIN_ITEMS =
                createTag("repairs_tin_items");

        public static final TagKey<Item> REPAIRS_MYTHRIL_ITEMS =
                createTag("repairs_mythril_items");

        public static final TagKey<Item> REPAIRS_ADAMANTIUM_ITEMS =
                createTag("repairs_adamantium_items");

        public static final TagKey<Item> REPAIRS_ONYX_ITEMS =
                createTag("repairs_onyx_items");

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, SCIdentifier.of(SimpleOres.MOD_ID, name));
        }

        public static class Conventional {
            public static TagKey<Item> TIN_INGOTS = SCTag.forIngot("tin");
            public static TagKey<Item> MYTHRIL_INGOTS = SCTag.forIngot("mythril");
            public static TagKey<Item> ADAMANTIUM_INGOTS = SCTag.forIngot("adamantium");

            public static TagKey<Item> ONYX_GEMS = SCTag.forGem("onyx");

            public static TagKey<Item> MYTHRIL_RODS = SCTag.forRod("mythril");
            public static TagKey<Item> ONYX_RODS = SCTag.forRod("onyx");

            public static TagKey<Item> RAW_COPPER_ORES = SCTag.forRawOre("copper");
            public static TagKey<Item> RAW_TIN_ORES = SCTag.forRawOre("tin");
            public static TagKey<Item> RAW_MYTHRIL_ORES = SCTag.forRawOre("mythril");
            public static TagKey<Item> RAW_ADAMANTIUM_ORES = SCTag.forRawOre("adamantium");

            public static TagKey<Item> COPPER_ORES = SCTag.forOre("copper");
            public static TagKey<Item> TIN_ORES = SCTag.forOre("tin");
            public static TagKey<Item> MYTHRIL_ORES = SCTag.forOre("mythril");
            public static TagKey<Item> ADAMANTIUM_ORES = SCTag.forOre("adamantium");
            public static TagKey<Item> ONYX_ORES = SCTag.forOre("onyx");

            public static TagKey<Item> TIN_DUSTS = SCTag.forDust("tin");
            public static TagKey<Item> MYTHRIL_DUSTS = SCTag.forDust("mythril");
            public static TagKey<Item> ADAMANTIUM_DUSTS = SCTag.forDust("adamantium");

            public static TagKey<Item> createTag(String name) {
                //? if <1.20.5 {
                /*return TagRegistration.ITEM_TAG_REGISTRATION.registerCommon(name);
                *///?} else {
                return TagRegistration.ITEM_TAG.registerC(name);
                //?}
            }
        }
    }
}