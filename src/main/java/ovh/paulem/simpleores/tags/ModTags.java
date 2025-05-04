package ovh.paulem.simpleores.tags;

import ovh.paulem.simpleores.SimpleOres;
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
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(SimpleOres.MOD_ID, name));
        }
    }
}