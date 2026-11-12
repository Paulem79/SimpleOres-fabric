package net.paulem.simpleores.items.custom.advanced;

import net.paulem.sclib.item.*;

public class AdvancedShovelItem extends SCShovelItem implements AdvancedToolItem {
    public AdvancedShovelItem(SCToolStats material, Properties settings) {
        super(material, 1.5F, -3.0F, settings);
    }
}
