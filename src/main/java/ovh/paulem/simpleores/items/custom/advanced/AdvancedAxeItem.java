package ovh.paulem.simpleores.items.custom.advanced;

import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;

public class AdvancedAxeItem extends AxeItem implements AdvancedToolItem {
    public AdvancedAxeItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        /*? if >=1.21.5 {*/
        super(material, attackDamage, attackSpeed,
                settings.axe(material, attackDamage, attackSpeed));
        /*?} else {*/
        /*super(material, attackDamage, attackSpeed, settings);
         *//*?}*/
    }
}
