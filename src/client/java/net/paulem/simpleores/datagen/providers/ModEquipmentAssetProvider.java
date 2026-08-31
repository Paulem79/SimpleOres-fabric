package net.paulem.simpleores.datagen.providers;

//? if >=1.21.3 {
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.CachedOutput;
//? if >1.21.3 {
import net.minecraft.client.data.models.EquipmentAssetProvider;
//?} else {
/*import net.minecraft.client.data.models.EquipmentModelProvider;
*///?}
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.resources.Identifier;
import net.paulem.simpleores.armors.ModEquipmentClientModels;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ModEquipmentAssetProvider extends //$ if >1.21.3 'EquipmentAssetProvider' else 'EquipmentModelProvider'
        EquipmentAssetProvider
{
    protected final PackOutput.PathProvider pathProvider;

    public ModEquipmentAssetProvider(FabricPackOutput packOutput)
    {
        super(packOutput);
        this.pathProvider = packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK,
                //$ if >1.21.3 '"equipment"' else '"models/equipment"'
                "equipment"
        );
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
//?} else {
/*public class ModEquipmentAssetProvider {}
*///?}
