package net.paulem.simpleores.advancements;

//? if >=26.2 {
//? if fabric {
import net.fabricmc.fabric.api.advancement.v1.AdvancementEvents;
import net.fabricmc.loader.api.FabricLoader;
//?}
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
//? if neoforge {
/*import java.util.LinkedHashMap;
*///?}

//? hasBucketlib {
import de.cech12.bucketlib.BucketLibMod;
import de.cech12.bucketlib.api.item.UniversalBucketItem;
import net.minecraft.world.level.material.Fluids;
//? if fabric {
import de.cech12.bucketlib.item.FluidStorageData;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
//?} else {
/*import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
*///?}
//?} else {
/*import net.minecraft.advancements.triggers.FilledBucketTrigger;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.paulem.simpleores.items.ModComponents;
import net.paulem.simpleores.items.custom.bucket.CustomBucketItem;
*///?}

import java.util.Map;

/**
 * Lets the custom buckets complete the vanilla bucket advancements.
 * Every added criterion is an alternative of the vanilla one, so the requirements stay the same.
 * <p>
 * Fabric hooks the Fabric Advancement API, NeoForge patches the loaded advancements
 * through {@code ServerAdvancementManagerMixin}.
 */
public class ModVanillaAdvancements {

    private static final Identifier LAVA_BUCKET = Identifier.withDefaultNamespace("story/lava_bucket");

    /**
     * A criterion added as an alternative of a criterion of the vanilla advancement.
     */
    private record Addition(String vanillaCriterion, String criterion, Criterion<?> value) {}

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

    private static List<Addition> additions(Identifier id, HolderLookup.Provider registries) {
        List<Addition> additions = new ArrayList<>();
        HolderGetter<Item> items = registries.lookupOrThrow(Registries.ITEM);

        for (var entry : ModItems.registeredItems.entrySet()) {
            Item bucket = entry.getValue();
            String prefix = SimpleOres.MOD_ID + ":" + entry.getKey().getPath() + "/";

            //? if hasBucketlib {
            if(!(bucket instanceof UniversalBucketItem)) continue;

            if(id.equals(LAVA_BUCKET)) {
                additions.add(new Addition("lava_bucket", prefix + "lava_bucket", InventoryChangeTrigger.TriggerInstance.hasItems(
                        //? if fabric {
                        holding(items, bucket, DataComponentExactPredicate.expect(BucketLibMod.STORAGE,
                                new FluidStorageData(FluidVariant.of(Fluids.LAVA), FluidConstants.BUCKET))))));
                        //?} else {
                        /*holding(items, bucket, DataComponentExactPredicate.expect(BucketLibMod.FLUID_COMPONENT.get(),
                                SimpleFluidContent.copyOf(new FluidStack(Fluids.LAVA, FluidType.BUCKET_VOLUME)))))));
                        *///?}
            }
            //?} else {
            /*if(!(bucket instanceof CustomBucketItem)) continue;

            if(id.equals(LAVA_BUCKET)) {
                additions.add(new Addition("lava_bucket", prefix + "lava_bucket", InventoryChangeTrigger.TriggerInstance.hasItems(
                        holding(items, bucket, DataComponentExactPredicate.expect(ModComponents.BUCKET_BLOCK_COMPONENT,
                                CustomBucketItem.getBlockIdentifier(Blocks.LAVA))))));
            }

            Map<String, Item> mobBuckets = MOB_BUCKETS.get(id);
            if(mobBuckets == null) continue;

            mobBuckets.forEach((vanillaCriterion, mobBucket) ->
                    additions.add(new Addition(vanillaCriterion, prefix + vanillaCriterion, FilledBucketTrigger.TriggerInstance.filledBucket(
                            holding(items, bucket, DataComponentExactPredicate.expect(ModComponents.BUCKET_FISH_COMPONENT,
                                    CustomBucketItem.getItemIdentifier(mobBucket)))))));
            *///?}
        }

        return additions;
    }

    private static ItemPredicate.Builder holding(HolderGetter<Item> items, Item bucket, DataComponentExactPredicate content) {
        return ItemPredicate.Builder.item()
                .of(items, bucket)
                .withComponents(DataComponentMatchers.Builder.components().exact(content).build());
    }

    /**
     * Adds the criterion to every requirement group holding the vanilla criterion, so any of them completes it.
     */
    private static List<List<String>> withAlternative(List<List<String>> requirements, Addition addition) {
        return requirements.stream()
                .map(group -> {
                    if(!group.contains(addition.vanillaCriterion())) return group;

                    List<String> alternatives = new ArrayList<>(group);
                    alternatives.add(addition.criterion());
                    return alternatives;
                })
                .toList();
    }

    //? if fabric {
    public static void init() {
        // The module only exists since Fabric API 0.161.0
        if(!FabricLoader.getInstance().isModLoaded("fabric-advancement-api-v1")) return;

        AdvancementEvents.MODIFY.register((id, builder, source, registries) -> {
            for (Addition addition : additions(id, registries)) {
                List<List<String>> requirements = builder.getRequirements().requirements();
                // Nothing is added when a data pack removed the vanilla criterion
                if(requirements.stream().noneMatch(group -> group.contains(addition.vanillaCriterion()))) continue;

                builder.addCriterion(addition.criterion(), addition.value());
                builder.requirements(new AdvancementRequirements(withAlternative(requirements, addition)));
            }
        });
    }
    //?} else {
    /*/^*
     * Called by the {@code ServerAdvancementManagerMixin} for every advancement which is loaded.
     ^/
    public static Advancement modify(Identifier id, Advancement advancement, HolderLookup.Provider registries) {
        java.util.Map<String, Criterion<?>> criteria = new LinkedHashMap<>(advancement.criteria());
        List<List<String>> requirements = advancement.requirements().requirements();
        boolean modified = false;

        for (Addition addition : additions(id, registries)) {
            // Nothing is added when a data pack removed the vanilla criterion
            if(requirements.stream().noneMatch(group -> group.contains(addition.vanillaCriterion()))) continue;

            criteria.put(addition.criterion(), addition.value());
            requirements = withAlternative(requirements, addition);
            modified = true;
        }

        if(!modified) return advancement;

        return new Advancement(advancement.parent(), advancement.display(), advancement.rewards(), criteria,
                new AdvancementRequirements(requirements), advancement.sendsTelemetryEvent());
    }
    *///?}
}
//?} else {
/*// The Fabric Advancement API does not exist on these versions
public class ModVanillaAdvancements {
    public static void init() {}
}
*///?}
