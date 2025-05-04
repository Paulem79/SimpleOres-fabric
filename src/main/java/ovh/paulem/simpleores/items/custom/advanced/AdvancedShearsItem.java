package ovh.paulem.simpleores.items.custom.advanced;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.ToolMaterial;

public class AdvancedShearsItem extends ShearsItem {
    public AdvancedShearsItem(ToolMaterial material, Settings settings) {
        super(settings
                .maxCount(1)
                .maxDamage(material.durability())
                .repairable(material.repairItems())
                .enchantable(material.enchantmentValue())
                .component(DataComponentTypes.TOOL, ShearsItem.createToolComponent())
        );
    }
}
