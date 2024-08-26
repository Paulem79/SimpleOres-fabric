package io.github.paulem.simpleores.items;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.ToolMaterial;

public class AdvancedShearsItem extends ShearsItem {
    public AdvancedShearsItem(ToolMaterial toolMaterials) {
        this(toolMaterials, new Settings());
    }

    public AdvancedShearsItem(ToolMaterial toolMaterials, Settings settings) {
        super(settings
                .maxCount(1)
                .maxDamage(toolMaterials.getDurability())
                .component(DataComponentTypes.TOOL, ShearsItem.createToolComponent())
        );
    }
}
