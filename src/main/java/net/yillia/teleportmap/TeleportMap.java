package net.yillia.teleportmap;

import net.fabricmc.api.ModInitializer;

import net.yillia.teleportmap.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TeleportMap implements ModInitializer {
	public static final String MOD_ID = "teleportmap";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		LOGGER.info("Hello Fabric world!");
		ModItems.register();
	}
}