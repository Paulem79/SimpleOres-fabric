package net.paulem.simpleores.ingredients;

//? if hasBucketlib {

/*public class MilkIngredient {}
 
*///?} else {

import com.mojang.serialization.Decoder;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredient;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
//? if afterDeobf
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.paulem.simpleores.items.ModItems;
import net.paulem.simpleores.items.custom.bucket.CustomBucketItem;
import net.paulem.simpleores.stonecutter.SCId;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class MilkIngredient implements CustomIngredient {
    @Override
    public boolean test(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }

        if (stack.getItem() == Items.MILK_BUCKET) {
            return true;
        }

        Item item = stack.getItem();

        if(!(item instanceof CustomBucketItem bucketItem)) return false;

        return bucketItem.isMilkBucket(stack);
    }

    @Override
    public Stream<Holder<Item>> //? if afterDeobf {
    items
    //?} else {
     /*getMatchingItems
    *///?}
            () {
        // Only the vanilla milk bucket can be matched at the item level: a custom bucket holds its content
        // in its components, so advertising it here would let an empty or a lava filled one match instead.
        return Stream.of(Items.MILK_BUCKET.builtInRegistryHolder());
    }

    /**
     * @return what is shown in the recipe book for this ingredient: the vanilla milk bucket and every custom
     * milk bucket, even though only {@link #test(ItemStack)} decides what actually matches.
     */
    @Override
    public SlotDisplay //? if afterDeobf {
    display
    //?} else {
     /*toDisplay
    *///?}
            () {
        List<SlotDisplay> displays = new ArrayList<>();
        displays.add(displayOf(new ItemStack(Items.MILK_BUCKET)));

        ModItems.registeredItems.values()
                .stream()
                .filter(item -> item instanceof CustomBucketItem)
                .map(item -> (CustomBucketItem) item)
                .map(CustomBucketItem::getMilkBucket)
                .forEach(stack -> displays.add(displayOf(stack)));

        return new SlotDisplay.Composite(displays);
    }

    private static SlotDisplay displayOf(ItemStack stack) {
        return new SlotDisplay.ItemStackSlotDisplay(//? if afterDeobf {
                ItemStackTemplate.fromNonEmptyStack(
                        stack
                )
                //?} else {
                /*stack
                *///?}
        );
    }

    @Override
    public boolean requiresTesting() {
        return true;
    }

    @Override
    public @NonNull CustomIngredientSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    public static final class Serializer implements CustomIngredientSerializer<MilkIngredient> {

        public static final Serializer INSTANCE = new Serializer();
        public static final Identifier NAME = SCId.of("milk");

        public static final MapCodec<MilkIngredient> CODEC = MapCodec.of(Encoder.empty(), Decoder.unit(new MilkIngredient()));

        private static final StreamCodec<RegistryFriendlyByteBuf, MilkIngredient> PACKET_CODEC = StreamCodec.of(
                MilkIngredient.Serializer::write,
                MilkIngredient.Serializer::read);

        private Serializer() {}

        @Override
        public Identifier getIdentifier() {
            return NAME;
        }

        @Override
        public MapCodec<MilkIngredient> getCodec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, MilkIngredient> //? if afterDeobf {
        getStreamCodec
        //?} else {
         /*getPacketCodec
        *///?}
                () {
            return PACKET_CODEC;
        }

        private static MilkIngredient read(RegistryFriendlyByteBuf buffer) {
            return new MilkIngredient();
        }

        private static void write(RegistryFriendlyByteBuf buffer, MilkIngredient ingredient) {
        }
    }
}
//?}