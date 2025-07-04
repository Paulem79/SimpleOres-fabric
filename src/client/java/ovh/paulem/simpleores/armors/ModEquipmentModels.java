package ovh.paulem.simpleores.armors;

//? if >=1.21.3 {

//? if >1.21.3
/*import net.minecraft.client.render.entity.equipment.EquipmentModel;*/
//? if =1.21.3
import net.minecraft.item.equipment.EquipmentModel;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * @author Autovw
 */
public final class ModEquipmentModels
{
    public static final Map<Identifier, EquipmentModel> REGISTERED_MODELS = new HashMap<>();

    public static final EquipmentModel COPPER = humanoidModel(ModArmorMaterials.COPPER);
    public static final EquipmentModel TIN = humanoidModel(ModArmorMaterials.TIN);
    public static final EquipmentModel MYTHRIL = humanoidModel(ModArmorMaterials.MYTHRIL);
    public static final EquipmentModel ADAMANTIUM = humanoidModel(ModArmorMaterials.ADAMANTIUM);
    public static final EquipmentModel ONYX = humanoidModel(ModArmorMaterials.ONYX);

    public static void bootstrap(BiConsumer<Identifier, EquipmentModel> consumer)
    {
        REGISTERED_MODELS.forEach(consumer);
    }

    private static EquipmentModel humanoidModel(ArmorMaterial armorMaterial)
    {
        Identifier location = armorMaterial
                //? if >1.21.3
                /*.assetId().getValue()*/
                //? if <=1.21.3
                .modelId()
        ;

        EquipmentModel model = EquipmentModel.builder().addHumanoidLayers(location).build();

        REGISTERED_MODELS.put(location, model);

        return model;
    }
}
//?} else {
 /*public final class ModEquipmentModels {}
*///?}