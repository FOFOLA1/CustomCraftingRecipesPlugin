package me.fofola1.CustomRecipes.Utils;

import me.fofola1.CustomRecipes.CustomRecipes;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.io.File;
import java.io.IOException;
import java.util.*;

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

    public static void save(YamlConfiguration yml, List<ItemStack> items, Player p) {
        ItemStack output = items.getLast();
        items.removeLast();
        Map<ItemStack, ArrayList<Integer>> map = new HashMap<>();
        ItemStack item;
        ArrayList<Integer> temp;

        for (int i = 0; i < 9; i++) {
            item = items.get(i);
            if (item == null) continue;

            temp = map.get(item);
            if (temp == null) temp = new ArrayList<>();
            temp.add(i);
            map.put(item, temp);
//            if (item != null) {
//                for (int j = i+1; j < 9; j++) {
//
//
//            }
        }
        List<Character> chars = Arrays.asList( 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'); // imutable
        ArrayList<Character> grid = new ArrayList<>(Arrays.asList(null, null, null, null, null, null, null, null, null)); // mutable
        for (int i = 0; i < map.size(); i++) {
            item = (ItemStack) map.keySet().toArray()[i];
            for (int j : map.get(item)) {
                grid.set(j, chars.get(i));
            }
            yml.set(chars.get(i).toString(), item.getType().toString());
        }

        ArrayList<String> formated = reduceGrid(grid);

        yml.set("shape", formated.toArray());
        yml.set("output", output);
        yml.set("auto_discover", MenuData.get(p.getUniqueId()).autoDiscover);
    }

    public static ArrayList<String> reduceGrid(ArrayList<Character> grid) {
        int first = 0;
        int last = grid.size()-1;
        int left = 0;
        int right = 2;

        while (grid.get(first) == null && grid.get(first+1) == null && grid.get(first+2) == null) {
            first += 3;
        }

        while (grid.get(last) == null && grid.get(last-1) == null && grid.get(last-2) == null) {
            last -= 3;
        }
        int i = 0;
        while (grid.get(i) == null && grid.get(i+3) == null && grid.get(i+6) == null) {
            left++;
            if (i == 1) break;
            i++;
        }

        i = 0;
        while (grid.get(8-i) == null && grid.get(8-i-3) == null && grid.get(8-i-6) == null) {
            right--;
            if (i == 1) break;
            i++;
        }

        int rowlen = right - left + 1;
        ArrayList<String> out = new ArrayList<>();
        StringBuilder temp = new StringBuilder(3);
        for (i = first; i <= last; i++) {
            if (i%3 >= left && i%3 <= right) {

                if (temp.length() == rowlen) {
                    out.add(temp.toString());
                    temp = new StringBuilder(3);
                }
                temp.append(grid.get(i) == null ? " " : grid.get(i));
            }
        }
        out.add(temp.toString());
        return out;
    }
}
