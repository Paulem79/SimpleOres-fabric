package net.paulem.simpleores.datagen.providers;

//? if >1.21.3 {
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.EquipmentAssetProvider;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.paulem.simpleores.armors.ModEquipmentClientModels;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ModEquipmentAssetProvider extends EquipmentAssetProvider
{
    protected final PackOutput.PathProvider pathProvider;

    public ModEquipmentAssetProvider(FabricDataOutput packOutput)
    {
        super(packOutput);
        this.pathProvider = packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "equipment");
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        Map<Identifier, EquipmentClientInfo> map = new HashMap<>();
        ModEquipmentClientModels.bootstrap((id, model) -> {
            if (map.putIfAbsent(id, model) != null)
            {
                throw new IllegalStateException("Duplicate equipment model for id: " + id.toString());
            }
        });
        return DataProvider.saveAll(output, EquipmentClientInfo.CODEC, this.pathProvider, map);
    }
}
//?} else if 1.21.3 {
/*import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.models.EquipmentModelProvider;
import net.minecraft.world.item.equipment.EquipmentModel;
import net.minecraft.resources.ResourceLocation;
import net.paulem.simpleores.armors.ModEquipmentClientModels;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ModEquipmentAssetProvider extends EquipmentModelProvider
{
    protected final PackOutput.PathProvider pathProvider;

    public ModEquipmentAssetProvider(FabricDataOutput packOutput)
    {
        super(packOutput);
        this.pathProvider = packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models/equipment");
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        Map<ResourceLocation, EquipmentModel> map = new HashMap<>();
        ModEquipmentClientModels.bootstrap((id, model) -> {
            if (map.putIfAbsent(id, model) != null)
            {
                throw new IllegalStateException("Duplicate equipment model for id: " + id.toString());
            }
        });
        return DataProvider.saveAll(output, EquipmentModel.CODEC, this.pathProvider, map);
    }
}
*///?} else {
/*public class ModEquipmentAssetProvider {}
*///?}
