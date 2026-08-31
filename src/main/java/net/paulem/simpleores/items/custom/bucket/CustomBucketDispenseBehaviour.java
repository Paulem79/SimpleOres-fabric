package net.paulem.simpleores.items.custom.bucket;

//? if hasBucketlib || !containsBucket {
public class CustomBucketDispenseBehaviour {}
 //?} else {
/*import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import org.jspecify.annotations.NonNull;

public class CustomBucketDispenseBehaviour extends DefaultDispenseItemBehavior {

    private static final CustomBucketDispenseBehaviour INSTANCE = new CustomBucketDispenseBehaviour();

    public static CustomBucketDispenseBehaviour getInstance()
    {
        return INSTANCE;
    }

    private CustomBucketDispenseBehaviour() {}

    private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

    @Override
    public @NonNull ItemStack execute(final BlockSource source, final ItemStack dispensed) {
        CustomBucketItem bucket = (CustomBucketItem) dispensed.getItem();
        BlockPos target = source.pos().relative(source.state().getValue(DispenserBlock.FACING));

        Level level = source.level();
        if (bucket.emptyContents(null, level, target, null, dispensed)) {
            bucket.checkExtraContent(null, level, dispensed, target);

            return this.consumeWithRemainder(source, dispensed, bucket.shouldMelt(dispensed) ? ItemStack.EMPTY : bucket.getEmpty());
        } else if(bucket.isEmpty(dispensed)) {
            // Pickup the contents, and return the new item with content inside
            InteractionResult interactionResult = bucket.pickup(level, null, target, dispensed);
            if(interactionResult instanceof InteractionResult.Success success) {
                ItemStack filled = success.heldItemTransformedTo();
                if(filled != null && !filled.isEmpty()) {
                    // Only one bucket of the dispensed stack is filled
                    return this.consumeWithRemainder(source, dispensed, filled);
                }
            }

            return this.defaultDispenseItemBehavior.dispense(source, dispensed);
        }

        return this.defaultDispenseItemBehavior.dispense(source, dispensed);
    }
}
*///?}