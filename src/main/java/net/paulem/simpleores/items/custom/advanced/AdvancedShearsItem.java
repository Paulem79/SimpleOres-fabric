package net.paulem.simpleores.items.custom.advanced;

//? if >1.20.4
//import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.Tier;

public class AdvancedShearsItem extends ShearsItem {
    public AdvancedShearsItem(Tier material, Properties settings) {
        super(settings
                .stacksTo(1)
                //? if >1.20.4 && != 1.21 {
                /*.durability(material.durability())
                .repairable(material.repairItems())
                .enchantable(material.enchantmentValue())
                .component(DataComponents.TOOL, ShearsItem.createToolProperties())
                *///?} else {
                
                .durability(material.getUses())
                 
                //?}
        );
    }
}
