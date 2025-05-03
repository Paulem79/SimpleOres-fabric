package ovh.paulem.simpleores.items.custom.advanced;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.RegistryKey;

public class AdvancedShearsItem extends ShearsItem {
    public AdvancedShearsItem(ToolMaterial toolMaterials, RegistryKey<Item> registryKey) {
        this(toolMaterials, new Settings().registryKey(registryKey));
    }

    public AdvancedShearsItem(ToolMaterial toolMaterials, Settings settings) {
        super(settings
                .maxCount(1)
                .maxDamage(toolMaterials.durability())
                .component(DataComponentTypes.TOOL, ShearsItem.createToolComponent())
        );
    }
}
