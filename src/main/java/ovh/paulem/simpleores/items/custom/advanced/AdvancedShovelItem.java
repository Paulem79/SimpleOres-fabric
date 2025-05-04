package ovh.paulem.simpleores.items.custom.advanced;

import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;

public class AdvancedShovelItem extends ShovelItem implements AdvancedToolItem {
    public AdvancedShovelItem(ToolMaterial material, Settings settings) {
        super(material, 1.5F, -3.0F,
                settings.shovel(material, 1.5F, -3.0F)
        );
    }
}
