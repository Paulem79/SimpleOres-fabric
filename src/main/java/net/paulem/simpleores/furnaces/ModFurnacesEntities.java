package net.paulem.simpleores.furnaces;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.paulem.simpleores.stonecutter.SCId;

public class ModFurnacesEntities {
    public static BlockEntityType<ModFurnaceBlockEntity> FABRIC_FURNACE = register(
            "fabric_furnace",
            FabricBlockEntityTypeBuilder.create(ModFurnaceBlockEntity::new,
                    ModFurnaces.getFurnaces().toArray(new Block[0])
            ).build());

    public static void init() {
        // no-op
    }

    private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> type) {
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, SCId.of(name), type);
    }
}