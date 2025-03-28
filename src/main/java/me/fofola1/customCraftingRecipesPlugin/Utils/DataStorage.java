package me.fofola1.customCraftingRecipesPlugin.Utils;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class DataStorage {
    private String name;
    private List<ItemStack> items;
    public boolean autoDiscover = true;

    public DataStorage(String name, List<ItemStack> items) {
        this.name = name;
        this.items = items;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setItems(List<ItemStack> items) {
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public List<ItemStack> getItems() {
        return items;
    }
}
