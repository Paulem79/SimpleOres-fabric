package net.paulem.simpleores.armors;

//? if >=1.21.3 {

//? if >=1.21.3
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.equipment.ArmorMaterial;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * @author Autovw
 */
public final class ModEquipmentClientModels
{
    public static final Map<ResourceLocation, EquipmentClientInfo> REGISTERED_MODELS = new HashMap<>();

    public static final EquipmentClientInfo COPPER = humanoidModel(ModArmorMaterials.COPPER);
    public static final EquipmentClientInfo TIN = humanoidModel(ModArmorMaterials.TIN);
    public static final EquipmentClientInfo MYTHRIL = humanoidModel(ModArmorMaterials.MYTHRIL);
    public static final EquipmentClientInfo ADAMANTIUM = humanoidModel(ModArmorMaterials.ADAMANTIUM);
    public static final EquipmentClientInfo ONYX = humanoidModel(ModArmorMaterials.ONYX);

    public static void bootstrap(BiConsumer<ResourceLocation, EquipmentClientInfo> consumer)
    {
        REGISTERED_MODELS.forEach(consumer);
    }

    private static EquipmentClientInfo humanoidModel(ArmorMaterial armorMaterial)
    {
        ResourceLocation location = armorMaterial
                //? if >1.21.3 {
                .assetId()//$location
                    .location(
                    )
                //?} else {
                /*.modelId()
                *///?}
        ;

        EquipmentClientInfo model = EquipmentClientInfo.builder().addHumanoidLayers(location).build();

        REGISTERED_MODELS.put(location, model);

        return model;
    }
}
//?} else {
 /*public final class ModEquipmentClientModels {}
*///?}
