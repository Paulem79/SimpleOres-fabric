package ovh.paulem.simpleores.mixin;

//? if hasBucketlib {
/*public class GeneratedItemModelMixin {}
*///?} else {

import net.minecraft.client.data.TextureKey;
import net.minecraft.client.render.model.json.GeneratedItemModel;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import ovh.paulem.simpleores.bucket.tint.handler.LayersUploader;

import java.util.Arrays;
import java.util.List;

@Mixin(GeneratedItemModel.class)
public class GeneratedItemModelMixin {
    // Allow up to x layers in the model instead of 5 layers from vanilla
    @Redirect(method = "bakeGeometry(Lnet/minecraft/client/render/model/ModelTextures;Lnet/minecraft/client/render/model/ErrorCollectingSpriteGetter;Lnet/minecraft/client/render/model/ModelBakeSettings;Lnet/minecraft/client/render/model/SimpleModel;)Lnet/minecraft/client/render/model/BakedGeometry;",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/model/json/GeneratedItemModel;LAYERS:Ljava/util/List;", opcode = Opcodes.GETSTATIC))
    private static List<String> getLayers() {
        return Arrays.stream(LayersUploader.LAYERS).map(TextureKey::getName).toList();
    }
}
//?}