package net.paulem.simpleores.advancements;

//? if >=26.2 {
import net.fabricmc.fabric.api.advancement.v1.AdvancementEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.predicates.DataComponentMatchers;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.advancements.triggers.InventoryChangeTrigger;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentExactPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.paulem.simpleores.SimpleOres;
import net.paulem.simpleores.items.ModItems;

import java.util.ArrayList;
import java.util.List;

//? hasBucketlib {
import de.cech12.bucketlib.BucketLibMod;
import de.cech12.bucketlib.api.item.UniversalBucketItem;
import de.cech12.bucketlib.item.FluidStorageData;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.world.level.material.Fluids;
//?} else {
/*import net.minecraft.advancements.triggers.FilledBucketTrigger;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.paulem.simpleores.items.ModComponents;
import net.paulem.simpleores.items.custom.bucket.CustomBucketItem;

import java.util.Map;
*///?}

/**
 * Lets the custom buckets complete the vanilla bucket advancements, through the Fabric Advancement API.
 * Every added criterion is an alternative of the vanilla one, so the requirements stay the same.
 */
public class ModVanillaAdvancements {

    private static final Identifier LAVA_BUCKET = Identifier.withDefaultNamespace("story/lava_bucket");

    //? if !hasBucketlib {
    /*// BucketLib triggers filled_bucket with the vanilla mob bucket, so only our own buckets need this
    private static final Map<Identifier, Map<String, Item>> MOB_BUCKETS = Map.of(
            Identifier.withDefaultNamespace("husbandry/tactical_fishing"), Map.of(
                    "cod_bucket", Items.COD_BUCKET,
                    "salmon_bucket", Items.SALMON_BUCKET,
                    "pufferfish_bucket", Items.PUFFERFISH_BUCKET,
                    "tropical_fish_bucket", Items.TROPICAL_FISH_BUCKET
            ),
            Identifier.withDefaultNamespace("husbandry/axolotl_in_a_bucket"), Map.of("axolotl_bucket", Items.AXOLOTL_BUCKET),
            Identifier.withDefaultNamespace("husbandry/tadpole_in_a_bucket"), Map.of("tadpole_bucket", Items.TADPOLE_BUCKET)
    );
    *///?}

    public static void init() {
        // The module only exists since Fabric API 0.161.0
        if(!FabricLoader.getInstance().isModLoaded("fabric-advancement-api-v1")) return;

        AdvancementEvents.MODIFY.register((id, builder, source, registries) -> modify(id, builder, registries));
    }

    private static void modify(Identifier id, Advancement.Builder builder, HolderLookup.Provider registries) {
        HolderGetter<Item> items = registries.lookupOrThrow(Registries.ITEM);

        for (var entry : ModItems.registeredItems.entrySet()) {
            Item bucket = entry.getValue();
            String prefix = SimpleOres.MOD_ID + ":" + entry.getKey().getPath() + "/";

            //? if hasBucketlib {
            if(!(bucket instanceof UniversalBucketItem)) continue;

            if(id.equals(LAVA_BUCKET)) {
                addAlternative(builder, "lava_bucket", prefix + "lava_bucket", InventoryChangeTrigger.TriggerInstance.hasItems(
                        holding(items, bucket, DataComponentExactPredicate.expect(BucketLibMod.STORAGE,
                                new FluidStorageData(FluidVariant.of(Fluids.LAVA), FluidConstants.BUCKET)))));
            }
            //?} else {
            /*if(!(bucket instanceof CustomBucketItem)) continue;

            if(id.equals(LAVA_BUCKET)) {
                addAlternative(builder, "lava_bucket", prefix + "lava_bucket", InventoryChangeTrigger.TriggerInstance.hasItems(
                        holding(items, bucket, DataComponentExactPredicate.expect(ModComponents.BUCKET_BLOCK_COMPONENT,
                                CustomBucketItem.getBlockIdentifier(Blocks.LAVA)))));
            }

            Map<String, Item> mobBuckets = MOB_BUCKETS.get(id);
            if(mobBuckets == null) continue;

            mobBuckets.forEach((vanillaCriterion, mobBucket) ->
                    addAlternative(builder, vanillaCriterion, prefix + vanillaCriterion, FilledBucketTrigger.TriggerInstance.filledBucket(
                            holding(items, bucket, DataComponentExactPredicate.expect(ModComponents.BUCKET_FISH_COMPONENT,
                                    CustomBucketItem.getItemIdentifier(mobBucket))))));
            *///?}
        }
    }

    private static ItemPredicate.Builder holding(HolderGetter<Item> items, Item bucket, DataComponentExactPredicate content) {
        return ItemPredicate.Builder.item()
                .of(items, bucket)
                .withComponents(DataComponentMatchers.Builder.components().exact(content).build());
    }

    /**
     * Adds the criterion to every requirement group holding the vanilla criterion, so any of them completes it.
     * Nothing is added when a data pack removed the vanilla criterion.
     */
    private static void addAlternative(Advancement.Builder builder, String vanillaCriterion, String criterion, Criterion<?> value) {
        List<List<String>> requirements = builder.getRequirements().requirements();
        if(requirements.stream().noneMatch(group -> group.contains(vanillaCriterion))) return;

        builder.addCriterion(criterion, value);
        builder.requirements(new AdvancementRequirements(requirements.stream()
                .map(group -> {
                    if(!group.contains(vanillaCriterion)) return group;

                    List<String> alternatives = new ArrayList<>(group);
                    alternatives.add(criterion);
                    return alternatives;
                })
                .toList()));
    }
}
//?} else {
/*// The Fabric Advancement API does not exist on these versions
public class ModVanillaAdvancements {
    public static void init() {}
}
*///?}
