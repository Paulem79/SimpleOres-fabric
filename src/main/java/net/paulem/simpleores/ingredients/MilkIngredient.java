package net.paulem.simpleores.ingredients;

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
    public Stream<Holder<Item>> items() {
        // Get all milk buckets for the custom buckets
        List<Item> items = new ArrayList<>(ModItems.registeredItems.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .filter(item -> item instanceof CustomBucketItem)
                .map(item -> (CustomBucketItem) item)
                .map(CustomBucketItem::getMilkBucket)
                .map(ItemStack::getItem)
                .toList()
        );

        items.add(Items.MILK_BUCKET);

        return items.stream().map(Holder::direct);
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
        public StreamCodec<RegistryFriendlyByteBuf, MilkIngredient> getStreamCodec() {
            return PACKET_CODEC;
        }

        private static MilkIngredient read(RegistryFriendlyByteBuf buffer) {
            return new MilkIngredient();
        }

        private static void write(RegistryFriendlyByteBuf buffer, MilkIngredient ingredient) {
        }
    }
}
