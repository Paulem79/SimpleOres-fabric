package net.paulem.simpleores.mixin.buckets;

//? if !hasBucketlib || !containsBucket {
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
//? }
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.Properties.class)
public class ItemSettingsMixin {
    //? if !hasBucketlib || !containsBucket {
    @Inject(
            method = "buildAndValidateComponents",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void redirectGetValidatedComponents(
            Component name, Identifier modelId, CallbackInfoReturnable<DataComponentMap> cir
    ) {
        Item.Properties settings = (Item.Properties) (Object) this;

        Identifier newModelId = settings.components.getOrDefault(DataComponents.ITEM_MODEL, modelId);

        DataComponentMap componentMap = settings.components.set(DataComponents.ITEM_NAME, name).set(DataComponents.ITEM_MODEL, newModelId).build();
        if (componentMap.has(DataComponents.DAMAGE) && componentMap.getOrDefault(DataComponents.MAX_STACK_SIZE, 1) > 1) {
            throw new IllegalStateException("Item cannot have both durability and be stackable");
        } else {
            cir.setReturnValue(componentMap);
        }
    }
    //?}
}
