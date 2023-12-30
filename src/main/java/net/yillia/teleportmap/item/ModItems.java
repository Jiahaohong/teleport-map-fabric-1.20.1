package net.yillia.teleportmap.item;

import com.sun.source.tree.IdentifierTree;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.util.telemetry.TelemetryLogger;
import net.minecraft.item.EmptyMapItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.yillia.teleportmap.TeleportMap;

public class ModItems {
    public static final Item EMPTY_TELEPORT_MAP = registerItem("empty_teleport_map", new EmptyTPMapItem(new FabricItemSettings()));

    public static final Item TELEPORT_MAP = registerItem("teleport_map", new TPMapItem(new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(TeleportMap.MOD_ID, name), item);
    }
    public static void register() {
        TeleportMap.LOGGER.info("Registering Mod Items for " + TeleportMap.MOD_ID);
    }
}
