package net.paulem.simpleores.bucket.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Transformation;
import com.mojang.serialization.MapCodec;
import java.util.function.Consumer;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.paulem.simpleores.items.custom.bucket.CustomBucketFluidable;
import net.paulem.simpleores.stonecutter.SCId;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.jspecify.annotations.NonNull;

public class CopperBucketSpecialRenderer implements SpecialModelRenderer<Fluid> {
    public static final Transformation DEFAULT_TRANSFORMATION = new Transformation(null, null, new Vector3f(1.0F, -1.0F, -1.0F), null);

    public static final Identifier COVER_TEXTURE = SCId.of("textures/item/copper_bucket_cover.png");
    private static final RenderType COVER_RENDER_TYPE = RenderTypes.entityCutout(COVER_TEXTURE);
    public static final ModelLayerLocation COPPER_BUCKET_MODEL_LAYER = new ModelLayerLocation(SCId.of("copper_bucket"), "");

    private final CopperBucketModel model;

    public CopperBucketSpecialRenderer(final CopperBucketModel model) {
        this.model = model;
    }

    @Override
    public Fluid extractArgument(final ItemStack stack) {
        Item item = stack.getItem();
        if(item instanceof CustomBucketFluidable bucketItem) {
            return bucketItem.getFluid();
        }

        return Fluids.EMPTY;
    }

    @Override
    public void submit(
            final Fluid fluid,
            final @NonNull PoseStack poseStack,
            final SubmitNodeCollector submitNodeCollector,
            final int lightCoords,
            final int overlayCoords,
            final boolean hasFoil,
            final int outlineColor
    ) {
        System.out.println("Rendering Copper Bucket with fluid: " + fluid);

        poseStack.pushPose();
        poseStack.mulPose(DEFAULT_TRANSFORMATION);

        // Render fluid
        if (fluid != Fluids.EMPTY) {
            Identifier fluidTexture = getFluidTexture(fluid);
            RenderType fluidRenderType = RenderTypes.entityCutout(fluidTexture);

            submitNodeCollector.submitModelPart(
                    this.model.root(), poseStack, fluidRenderType, lightCoords, overlayCoords, null, false, false, -1, null, outlineColor
            );
        }

        // Render cover
        submitNodeCollector.submitModelPart(
                this.model.root(), poseStack, COVER_RENDER_TYPE, lightCoords, overlayCoords, null, false, hasFoil, -1, null, outlineColor
        );
        poseStack.popPose();
    }

    private Identifier getFluidTexture(Fluid fluid) {
        // Retourne la texture du fluide. Dans un vrai mod, on chercherait l'Identifier dynamiquement.
        if (fluid == Fluids.LAVA) return Identifier.withDefaultNamespace("textures/block/exposed_copper.png");
        if (fluid == Fluids.WATER) return Identifier.withDefaultNamespace("textures/block/exposed_copper.png");
        return Identifier.withDefaultNamespace("textures/block/exposed_copper.png"); // Fallback
    }

    @Override
    public void getExtents(final Consumer<Vector3fc> output) {
        PoseStack poseStack = new PoseStack();
        poseStack.mulPose(DEFAULT_TRANSFORMATION);
        this.model.root().getExtentsForGui(poseStack, output);
    }

    public record Unbaked() implements SpecialModelRenderer.Unbaked<Fluid> {
        public static final MapCodec<CopperBucketSpecialRenderer.Unbaked> MAP_CODEC = MapCodec.unit(new CopperBucketSpecialRenderer.Unbaked());

        @Override
        public MapCodec<CopperBucketSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

        public CopperBucketSpecialRenderer bake(final SpecialModelRenderer.BakingContext context) {
            return new CopperBucketSpecialRenderer(new CopperBucketModel(context.entityModelSet().bakeLayer(COPPER_BUCKET_MODEL_LAYER)));
        }
    }
}
