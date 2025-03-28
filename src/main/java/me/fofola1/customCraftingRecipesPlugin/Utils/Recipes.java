package me.fofola1.customCraftingRecipesPlugin.Utils;

import me.fofola1.customCraftingRecipesPlugin.CustomCraftingRecipesPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Recipes {
    private static final CustomCraftingRecipesPlugin plugin = CustomCraftingRecipesPlugin.getInstance();
    public static ArrayList<ShapedRecipe> AutoDiscoverRecipes = new ArrayList<ShapedRecipe>();
    public static ArrayList<ShapedRecipe> ManualDiscoverRecipes = new ArrayList<ShapedRecipe>();

    public static void LoadRecipe(File file) {
        FileConfiguration yml = YamlConfiguration.loadConfiguration(file);
        String name = file.getName().replace(".yml", "");
        NamespacedKey key = new NamespacedKey(plugin, name);
        ShapedRecipe recipe = new ShapedRecipe(key, yml.getItemStack("output"));
        ArrayList<String> shape = (ArrayList<String>) yml.getList("shape");
        switch (shape.size()) {
            case 1 -> recipe.shape(shape.get(0));
            case 2 -> recipe.shape(shape.get(0), shape.get(1));
            case 3 -> recipe.shape(shape.get(0), shape.get(1), shape.get(2));
        }

        String item;
        for (char c : new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'}) {
            item = yml.getString(String.valueOf(c));
            if (item == null) break;
            recipe.setIngredient(c, Material.valueOf(item));

        }
        if (yml.getBoolean("auto_discover")) AutoDiscoverRecipes.add(recipe);
        else ManualDiscoverRecipes.add(recipe);
        Bukkit.addRecipe(recipe);
        plugin.LogInfo("Recipe " + name + " loaded");
    }

    public static void LoadAllRecipes() {
        File craftingFolder = new File(plugin.getDataFolder() + "/Recipes/CraftingTable");
        List<File> craftingFolderFiles = new ArrayList<File>();
        Utils.listFiles(craftingFolder, craftingFolderFiles);

        for (File file : craftingFolderFiles) {
            if (!file.getName().endsWith(".yml")) continue;
            Bukkit.getScheduler().runTask(plugin, () -> {
                LoadRecipe(file);
            });
        }
        Bukkit.reloadData();
    }
}
