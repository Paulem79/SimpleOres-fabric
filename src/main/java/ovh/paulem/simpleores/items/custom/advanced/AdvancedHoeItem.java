package ovh.paulem.simpleores.items.custom.advanced;

import net.minecraft.item.HoeItem;
import net.minecraft.item.ToolMaterial;

public class AdvancedHoeItem extends HoeItem implements AdvancedToolItem {
    public AdvancedHoeItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {


        /*? if >=1.21.5 {*/
        super(material, attackDamage, attackSpeed,
                settings.hoe(material, attackDamage, attackSpeed));
        /*?} else {*/
        /*super(material, 1.5F, -3.0F, settings);
         *//*?}*/
    }
}
