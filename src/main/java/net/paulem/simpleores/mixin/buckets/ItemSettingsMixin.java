package net.paulem.simpleores.mixin.buckets;

import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;

//? if !hasBucketlib {
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//?}

@Mixin(Item.Settings.class)
public class ItemSettingsMixin {
    //? if !hasBucketlib {
    @Inject(
            method = "getValidatedComponents",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void redirectGetValidatedComponents(
            Text name, Identifier modelId, CallbackInfoReturnable<ComponentMap> cir
    ) {
        Item.Settings settings = (Item.Settings) (Object) this;

        Identifier newModelId = settings.components.getOrDefault(DataComponentTypes.ITEM_MODEL, modelId);

        ComponentMap componentMap = settings.components.add(DataComponentTypes.ITEM_NAME, name).add(DataComponentTypes.ITEM_MODEL, newModelId).build();
        if (componentMap.contains(DataComponentTypes.DAMAGE) && componentMap.getOrDefault(DataComponentTypes.MAX_STACK_SIZE, 1) > 1) {
            throw new IllegalStateException("Item cannot have both durability and be stackable");
        } else {
            cir.setReturnValue(componentMap);
        }
    }
    //?}
}
