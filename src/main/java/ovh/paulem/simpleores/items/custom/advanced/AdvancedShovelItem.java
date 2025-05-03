package ovh.paulem.simpleores.items.custom.advanced;

import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;

public class AdvancedShovelItem extends ShovelItem {
    public AdvancedShovelItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    public AdvancedShovelItem(ToolMaterial material, Settings settings) {
        this(material, 1.5F, -3.0F, settings);
    }
}
