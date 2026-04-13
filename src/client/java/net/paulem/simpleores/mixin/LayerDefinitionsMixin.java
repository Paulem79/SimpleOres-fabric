package net.paulem.simpleores.mixin;

//? if hasBucketlib || !containsBucket {
/*
import net.minecraft.client.model.geom.LayerDefinitions;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LayerDefinitions.class)
public class LayerDefinitionsMixin {}*/
//?} else {

import com.google.common.collect.ImmutableMap;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.paulem.simpleores.bucket.renderer.CopperBucketModel;
import net.paulem.simpleores.bucket.renderer.CopperBucketItemSpecialRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(LayerDefinitions.class)
public class LayerDefinitionsMixin {
    // Mixin at bottom of method Lnet/minecraft/client/model/geom/LayerDefinitions;createRoots()Ljava/util/Map;
    @Inject(method = "createRoots", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableMap$Builder;build()Lcom/google/common/collect/ImmutableMap;"))
    private static void onCreateRoots(CallbackInfoReturnable<Map<ModelLayerLocation, LayerDefinition>> info,
                                      @Local(name = //? if afterDeobf {
                                              "result"
                                              //?} else {
                                              // "builder"
                                              //?}
                                      ) ImmutableMap.Builder<ModelLayerLocation, LayerDefinition> result) {
        result.put(CopperBucketItemSpecialRenderer.COPPER_BUCKET_MODEL_LAYER, CopperBucketModel.createLayer());
    }
}
//?}