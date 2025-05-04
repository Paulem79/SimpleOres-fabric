package ovh.paulem.simpleores.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.EquipmentAssetProvider;
import net.minecraft.client.render.entity.equipment.EquipmentModel;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.util.Identifier;
import ovh.paulem.simpleores.armors.ModEquipmentModels;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author Autovw
 */
public class ModEquipmentAssetProvider extends EquipmentAssetProvider
{
    protected final DataOutput.PathResolver pathProvider;

    public ModEquipmentAssetProvider(FabricDataOutput packOutput)
    {
        super(packOutput);
        this.pathProvider = packOutput.getResolver(DataOutput.OutputType.RESOURCE_PACK, "equipment");
    }

    @Override
    public CompletableFuture<?> run(DataWriter output) {
        Map<Identifier, EquipmentModel> map = new HashMap<>();
        ModEquipmentModels.bootstrap((id, model) -> {
            if (map.putIfAbsent(id, model) != null)
            {
                throw new IllegalStateException("Duplicate equipment model for id: " + id.toString());
            }
        });
        return DataProvider.writeAllToPath(output, EquipmentModel.CODEC, this.pathProvider, map);
    }
}
