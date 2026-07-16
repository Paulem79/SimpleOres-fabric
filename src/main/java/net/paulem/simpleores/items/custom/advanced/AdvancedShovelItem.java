package net.paulem.simpleores.items.custom.advanced;

//? if <26.2 {
/*import net.minecraft.world.item.ShovelItem;
 *///?} else {
import net.minecraft.world.item.Item;
//?}
import net.minecraft.world.item.ToolMaterial;

public class AdvancedShovelItem extends //$ if >=26.2 'Item' else 'ShovelItem'
        Item
        implements AdvancedToolItem {
    public AdvancedShovelItem(ToolMaterial material, Properties settings) {
        //? if >=26.2 {
        super(settings.shovel(material, 1.5F, -3.0F));
        //?} else if >=1.21.5 {
        /*super(material, 1.5F, -3.0F,
                settings.shovel(material, 1.5F, -3.0F));
        *///?} else if 1.20.6 || 1.21 {
        /*super(material, settings.attributes(ShovelItem.createAttributes(material, 1.5F, -3.0F)));
         *///?} else {
        /*super(material, 1.5F, -3.0F, settings);
         *///?}
    }
}