package me.fofola1.CustomRecipes.Utils;

import org.bukkit.inventory.ItemStack;

import java.util.*;

public class MenuData {
    private static Map<UUID, DataStorage> data = new HashMap<UUID, DataStorage>();

    public static DataStorage get(UUID uuid) {
        if (data.get(uuid) == null)
            data.put(uuid, new DataStorage());
        return data.get(uuid);
    }

    public static void remove(UUID uuid) {
        data.remove(uuid);
    }

    public static Set<UUID> getUUIDList() {
        return data.keySet();
    }
}
