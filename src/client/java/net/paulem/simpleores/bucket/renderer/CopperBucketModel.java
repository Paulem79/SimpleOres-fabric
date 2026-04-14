package net.paulem.simpleores.bucket.renderer;

//? if hasBucketlib {
/*public class CopperBucketModel {}
*///?} else {

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.util.Unit;

public class CopperBucketModel extends Model //? >1.21.8
        <Unit>
{
    public CopperBucketModel(final ModelPart root) {
        // cutout for transparent bucket background
        super(root, RenderTypes:: //? if afterDeobf {
                itemCutout
                //?} else {
                 /*itemEntityTranslucentCull
                *///?}
        );
    }

    public static LayerDefinition createLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        // Plan of the bucket
        root.addOrReplaceChild("base",
                CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -16.0F, -0.5F, 16.0F, 16.0F, 1.0F),
                PartPose.ZERO
        );

        return LayerDefinition.create(mesh, 16, 16);
    }
}
//?}