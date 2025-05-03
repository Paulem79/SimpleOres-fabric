package ovh.paulem.simpleores.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.data.client.EquipmentModelProvider;
import net.minecraft.item.equipment.EquipmentModel;
import net.minecraft.util.Identifier;
import ovh.paulem.simpleores.armors.ModEquipmentModels;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author Autovw
 */
public class ModEquipmentModelProvider extends EquipmentModelProvider
{
    protected final DataOutput.PathResolver pathProvider;

    public ModEquipmentModelProvider(FabricDataOutput packOutput)
    {
        super(packOutput);
        this.pathProvider = packOutput.getResolver(DataOutput.OutputType.RESOURCE_PACK, "models/equipment");
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
