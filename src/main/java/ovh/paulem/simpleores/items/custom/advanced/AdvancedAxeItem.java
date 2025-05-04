package ovh.paulem.simpleores.items.custom.advanced;

import net.minecraft.item.AxeItem;
import net.minecraft.item.ToolMaterial;

public class AdvancedAxeItem extends AxeItem implements AdvancedToolItem {
    public AdvancedAxeItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings.axe(material, attackDamage, attackSpeed));
    }
}
