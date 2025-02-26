package me.fofola1.customCraftingRecipesPlugin.Utils;

import org.bukkit.inventory.ItemStack;

import java.util.*;

public class MenuData {
    private static Map<UUID, DataStorage> data = new HashMap<UUID, DataStorage>();

    public static DataStorage get(UUID uuid) {
        return data.get(uuid);
    }

    public static void set(UUID uuid, String name, ArrayList<ItemStack> items) {
        DataStorage map = new DataStorage(name, items);
        data.put(uuid, map);
    }

    public static void remove(UUID uuid) {
        data.remove(uuid);
    }

    public static void edit(UUID uuid, String name) {
        get(uuid).setName(name);
    }

    public static void edit(UUID uuid, ArrayList<ItemStack> items) {
        get(uuid).setItems(items);
    }
    public static Set<UUID> getUUIDList() {
        return data.keySet();
    }
}
