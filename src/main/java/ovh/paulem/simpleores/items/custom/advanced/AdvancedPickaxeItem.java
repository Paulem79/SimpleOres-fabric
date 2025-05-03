package ovh.paulem.simpleores.items.custom.advanced;

import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;

public class AdvancedPickaxeItem extends PickaxeItem {
    public AdvancedPickaxeItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    public AdvancedPickaxeItem(ToolMaterial material, Settings settings) {
        this(material, 1, -2.8F, settings);
    }
}
