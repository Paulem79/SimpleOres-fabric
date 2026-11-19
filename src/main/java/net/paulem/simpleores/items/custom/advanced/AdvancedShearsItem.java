package net.paulem.simpleores.items.custom.advanced;

import net.paulem.sclib.item.*;
//? if >=26.4
/*import net.minecraft.core.dispenser.DispenseBehaviorIds;*/

public class AdvancedShearsItem extends SCShearsItem {
    public AdvancedShearsItem(SCToolStats material, Properties settings) {
        super(material, settings
                //? if >=26.4
                /*.dispensed(DispenseBehaviorIds.SHEARS)*/
        );
    }
}
