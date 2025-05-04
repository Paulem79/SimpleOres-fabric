package ovh.paulem.simpleores.items.custom.advanced;

import net.minecraft.item.HoeItem;
import net.minecraft.item.ToolMaterial;

public class AdvancedHoeItem extends HoeItem implements AdvancedToolItem {
    public AdvancedHoeItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed,
                settings.hoe(material, attackDamage, attackSpeed)
        );
    }
}
