package net.yillia.teleportmap;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.yillia.teleportmap.data.ModLangProvider;
import net.yillia.teleportmap.data.ModModelProvider;
import net.yillia.teleportmap.data.ModRecipeProvider;

public class TeleportMapDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ModLangProvider.EN_US::new);
		pack.addProvider(ModLangProvider.ZH_CN::new);
		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModRecipeProvider::new);
	}
}
