package net.yillia.teleportmap.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.yillia.teleportmap.item.ModItems;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.EMPTY_TELEPORT_MAP, Models.GENERATED);
        itemModelGenerator.register(ModItems.TELEPORT_MAP, Models.GENERATED);
        itemModelGenerator.register(ModItems.EMPTY_RECOVERY_MAP, Models.GENERATED);
        itemModelGenerator.register(ModItems.RECOVERY_MAP, Models.GENERATED);
    }
}
