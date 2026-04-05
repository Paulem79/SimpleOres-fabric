package net.paulem.simpleores.bucket.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Transformation;
import com.mojang.serialization.MapCodec;
import java.util.function.Consumer;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.paulem.simpleores.items.custom.bucket.CustomChildrenBucketItem;
import net.paulem.simpleores.stonecutter.SCId;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class CopperBucketSpecialRenderer implements SpecialModelRenderer<Fluid> {
    public static final Transformation DEFAULT_TRANSFORMATION = new Transformation(null, null, new Vector3f(1.0F, -1.0F, -1.0F), null);

    public static final Identifier COVER_TEXTURE = Identifier.fromNamespaceAndPath("simpleores", "textures/item/copper_bucket_cover.png");

    private final CopperBucketModel model;

    public CopperBucketSpecialRenderer(final CopperBucketModel model) {
        this.model = model;
    }

    @Override
    public Fluid extractArgument(final ItemStack stack) {
        Item item = stack.getItem();
        if(item instanceof CustomChildrenBucketItem bucketItem) {
            return bucketItem.getFluid();
        }

        return Fluids.EMPTY;
    }

    @Override
    public void submit(
            final Fluid fluid,
            final PoseStack poseStack,
            final SubmitNodeCollector submitNodeCollector,
            final int lightCoords,
            final int overlayCoords,
            final boolean hasFoil,
            final int outlineColor
    ) {
        System.out.println("Rendering Copper Bucket with fluid: " + fluid);
        // Render fluid
        if (fluid != Fluids.EMPTY) {
            Identifier fluidTexture = getFluidTexture(fluid);
            RenderType fluidRenderType = RenderTypes.entityCutout(fluidTexture);

            submitNodeCollector.submitModelPart(
                    this.model.root(), poseStack, fluidRenderType, lightCoords, overlayCoords, null, false, false, -1, null, outlineColor
            );
        }

        // Render cover
        RenderType coverRenderType = RenderTypes.entityCutout(COVER_TEXTURE);
        submitNodeCollector.submitModelPart(
                this.model.root(), poseStack, coverRenderType, lightCoords, overlayCoords, null, false, hasFoil, -1, null, outlineColor
        );
    }

    private Identifier getFluidTexture(Fluid fluid) {
        // Retourne la texture du fluide. Dans un vrai mod, on chercherait l'Identifier dynamiquement.
        if (fluid == Fluids.LAVA) return Identifier.withDefaultNamespace("textures/block/lava_still.png");
        if (fluid == Fluids.WATER) return Identifier.withDefaultNamespace("textures/block/water_still.png");
        return Identifier.withDefaultNamespace("textures/item/empty_armor_slot_shield.png"); // Fallback
    }

    @Override
    public void getExtents(final Consumer<Vector3fc> output) {
        PoseStack poseStack = new PoseStack();
        this.model.root().getExtentsForGui(poseStack, output);
    }

    public record Unbaked() implements SpecialModelRenderer.Unbaked<Fluid> {
        public static final MapCodec<CopperBucketSpecialRenderer.Unbaked> MAP_CODEC = MapCodec.unit(new CopperBucketSpecialRenderer.Unbaked());

        @Override
        public MapCodec<CopperBucketSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

        public CopperBucketSpecialRenderer bake(final SpecialModelRenderer.BakingContext context) {
            return new CopperBucketSpecialRenderer(new CopperBucketModel(context.entityModelSet().bakeLayer(new ModelLayerLocation(SCId.of("copper_bucket"), "main"))));
        }
    }
}