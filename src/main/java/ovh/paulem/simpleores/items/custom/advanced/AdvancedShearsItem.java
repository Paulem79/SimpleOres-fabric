package ovh.paulem.simpleores.items.custom.advanced;

//? if >1.20.4
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.ToolMaterial;

public class AdvancedShearsItem extends ShearsItem {
    public AdvancedShearsItem(ToolMaterial material, Settings settings) {
        super(settings
                .maxCount(1)
                //? if >1.20.4 && != 1.21 {
                .maxDamage(material.durability())
                .repairable(material.repairItems())
                .enchantable(material.enchantmentValue())
                .component(DataComponentTypes.TOOL, ShearsItem.createToolComponent())
                //?} else {
                
                /*.maxDamage(material.getDurability())
                 
                *///?}
        );
    }
}
