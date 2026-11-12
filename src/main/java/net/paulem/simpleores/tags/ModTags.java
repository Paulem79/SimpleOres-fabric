package net.paulem.simpleores.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.paulem.simpleores.SimpleOres;
import net.paulem.sclib.SCId;
import net.paulem.sclib.tag.SCTags;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> SIMPLEORES_ORES =
                createTag("ores");

        public static TagKey<Block> createTag(String name) {
            return TagKey.create(Registries.BLOCK, SCId.of(SimpleOres.MOD_ID, name));
        }

        public static class Conventional {

            public static TagKey<Block> createTag(String name) {
                // Both loaders share the "c" namespace of the conventional tags
                return TagKey.create(Registries.BLOCK, SCId.of("c", name));
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
            return TagKey.create(Registries.ITEM, SCId.of(SimpleOres.MOD_ID, name));
        }

        public static class Conventional {
            public static TagKey<Item> TIN_INGOTS = SCTags.ingot("tin");
            public static TagKey<Item> MYTHRIL_INGOTS = SCTags.ingot("mythril");
            public static TagKey<Item> ADAMANTIUM_INGOTS = SCTags.ingot("adamantium");

            public static TagKey<Item> ONYX_GEMS = SCTags.gem("onyx");

            public static TagKey<Item> MYTHRIL_RODS = SCTags.rod("mythril");
            public static TagKey<Item> ONYX_RODS = SCTags.rod("onyx");

            public static TagKey<Item> COPPER_INGOTS = SCTags.ingot("copper");

            public static TagKey<Item> RAW_COPPER_ORES = SCTags.rawOre("copper");
            public static TagKey<Item> RAW_TIN_ORES = SCTags.rawOre("tin");
            public static TagKey<Item> RAW_MYTHRIL_ORES = SCTags.rawOre("mythril");
            public static TagKey<Item> RAW_ADAMANTIUM_ORES = SCTags.rawOre("adamantium");

            public static TagKey<Item> COPPER_ORES = SCTags.oreItem("copper");
            public static TagKey<Item> TIN_ORES = SCTags.oreItem("tin");
            public static TagKey<Item> MYTHRIL_ORES = SCTags.oreItem("mythril");
            public static TagKey<Item> ADAMANTIUM_ORES = SCTags.oreItem("adamantium");
            public static TagKey<Item> ONYX_ORES = SCTags.oreItem("onyx");

            public static TagKey<Item> COPPER_DUSTS = SCTags.dust("copper");
            public static TagKey<Item> TIN_DUSTS = SCTags.dust("tin");
            public static TagKey<Item> MYTHRIL_DUSTS = SCTags.dust("mythril");
            public static TagKey<Item> ADAMANTIUM_DUSTS = SCTags.dust("adamantium");

            public static TagKey<Item> createTag(String name) {
                // Both loaders share the "c" namespace of the conventional tags
                return TagKey.create(Registries.ITEM, SCId.of("c", name));
            }
        }
    }
}
