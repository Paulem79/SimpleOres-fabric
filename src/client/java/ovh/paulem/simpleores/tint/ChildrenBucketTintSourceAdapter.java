package ovh.paulem.simpleores.tint;

import net.minecraft.client.render.item.tint.TintSource;
import net.minecraft.fluid.Fluids;
import ovh.paulem.simpleores.items.custom.bucket.CustomChildrenBucketItem;

public record ChildrenBucketTintSourceAdapter(CustomChildrenBucketItem bucket) {
    public TintSource getTintSource() {
        if (bucket.getFluid() == Fluids.LAVA) {
            return new ChildrenBucketTintSource(0xFF4500); // Lava color
        } else if (bucket.getFluid() == Fluids.WATER) {
            return new ChildrenBucketTintSource(0x3F76E4); // Water color
        } else {
            return new ChildrenBucketTintSource(0xFF0000);
        }
    }
}
