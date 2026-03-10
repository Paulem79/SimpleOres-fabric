//? if hasBucketlib || !containsBucket {
/*
package net.paulem.simpleores.mixin;

import org.spongepowered.asm.mixin.Mixin;

@Mixin
public class GeneratedItemModelMixin {}
*///?} else {
package net.paulem.simpleores.mixin;
import org.spongepowered.asm.mixin.Mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.data.models.model.TextureSlot;
import org.objectweb.asm.Opcodes;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.injection.At;
import net.paulem.simpleores.bucket.tint.handler.LayersUploader;

import java.util.Arrays;
import java.util.List;

//? if afterDeobf {
import net.minecraft.client.resources.model.cuboid.ItemModelGenerator;
//?} else {
//import net.minecraft.client.renderer.block.model.ItemModelGenerator;
//?}

@Mixin(ItemModelGenerator.class)
public class GeneratedItemModelMixin {
    // Allow up to x layers in the model instead of 5 layers from vanilla
    @WrapOperation(
            //? if afterDeobf {
            method = "bake(Lnet/minecraft/client/resources/model/sprite/TextureSlots;Lnet/minecraft/client/resources/model/ModelBaker;Lnet/minecraft/client/renderer/block/dispatch/ModelState;Lnet/minecraft/client/resources/model/ModelDebugName;)Lnet/minecraft/client/resources/model/geometry/QuadCollection;",
            //?} elif >1.21.10 {
            //method = "bake(Lnet/minecraft/client/renderer/block/model/TextureSlots;Lnet/minecraft/client/resources/model/ModelBaker;Lnet/minecraft/client/resources/model/ModelState;Lnet/minecraft/client/resources/model/ModelDebugName;)Lnet/minecraft/client/resources/model/QuadCollection;",
            //?} else {
            /*method = "bake(Lnet/minecraft/client/renderer/block/model/TextureSlots;Lnet/minecraft/client/resources/model/SpriteGetter;Lnet/minecraft/client/resources/model/ModelState;Lnet/minecraft/client/resources/model/ModelDebugName;)Lnet/minecraft/client/resources/model/QuadCollection;",
            *///?}
            at = @At(value = "FIELD",
                    //? if afterDeobf {
                    target = "Lnet/minecraft/client/resources/model/cuboid/ItemModelGenerator;LAYERS:Ljava/util/List;",
                    //?} else {
                    //target = "Lnet/minecraft/client/renderer/block/model/ItemModelGenerator;LAYERS:Ljava/util/List;",
                    //?}
                    opcode = Opcodes.GETSTATIC))
    private static List<String> getLayers(Operation<List<String>> original) {
        return Arrays.stream(LayersUploader.LAYERS).map(TextureSlot::getId).toList();
    }
}
//?}
