package ovh.paulem.simpleores.items.custom.advanced;

import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

public class AdvancedSwordItem extends SwordItem {
    public AdvancedSwordItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    public AdvancedSwordItem(ToolMaterial material, Settings settings) {
        this(material, 3, -2.4F, settings);
    }
}
