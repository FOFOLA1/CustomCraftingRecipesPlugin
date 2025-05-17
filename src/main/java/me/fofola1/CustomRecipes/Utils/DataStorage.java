package me.fofola1.CustomRecipes.Utils;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DataStorage {
    public String name = "";
    public List<ItemStack> items = new ArrayList<>();
    public boolean autoDiscover = true;

    public DataStorage() {

    }
}
