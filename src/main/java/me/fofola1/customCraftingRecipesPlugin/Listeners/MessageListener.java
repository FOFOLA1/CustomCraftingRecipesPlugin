package me.fofola1.customCraftingRecipesPlugin.Listeners;

import me.fofola1.customCraftingRecipesPlugin.Configs.Lang;
import me.fofola1.customCraftingRecipesPlugin.CustomCraftingRecipesPlugin;
import me.fofola1.customCraftingRecipesPlugin.Menus.Setups.CraftingRecipeMenu;
import me.fofola1.customCraftingRecipesPlugin.Utils.DataStorage;
import me.fofola1.customCraftingRecipesPlugin.Utils.MenuData;
import me.fofola1.customCraftingRecipesPlugin.Utils.Preloaded;
import me.fofola1.customCraftingRecipesPlugin.Utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class MessageListener implements Listener {

    private static final CustomCraftingRecipesPlugin plugin = CustomCraftingRecipesPlugin.getInstance();

    @EventHandler
    public void onMessage(AsyncPlayerChatEvent e) {
        if (MenuData.get(e.getPlayer().getUniqueId()) == null) return;

        e.setCancelled(true);
        Player p = e.getPlayer();
        String msg = e.getMessage();
        if (msg.equalsIgnoreCase("cancel")) {
            Bukkit.getScheduler().runTask(plugin, new Runnable() {
                @Override
                public void run() {
                    Inventory newinv = new CraftingRecipeMenu().get();
                    List<ItemStack> items = MenuData.get(p.getUniqueId()).getItems();
                    ArrayList<Integer> ids = Preloaded.crafting_allowed;
                    for (int i = 0; i < ids.size(); i++) {
                        newinv.setItem(ids.get(i), items.get(i));
                    }
                    newinv.setItem(Preloaded.crafting_output, items.get(items.size() - 1));
                    newinv.setItem(8, Preloaded.create_recipe_allowed);
                    p.openInventory(newinv);
                    MenuData.remove(p.getUniqueId());
                }
            });
        } else {
            File recipes_folder = new File(plugin.getDataFolder() + "/Recipes");
            if (!recipes_folder.exists()) {
                recipes_folder.mkdirs();
            }

            StringBuilder fileName = new StringBuilder();
            fileName.append(recipes_folder);
            fileName.append("/");
            fileName.append(msg);
            fileName.append(".yml");
            if (!Utils.isFilenameValid(fileName.toString())) {
                p.sendMessage(Lang.getString("chat.invalid_recipe_name"));
                return;
            }
            File newfile = new File(fileName.toString());
            if (newfile.exists()) {
                p.sendMessage(Lang.getString("chat.recipe_name_already_exists"));
                return;
            }
            try {
                newfile.createNewFile();
            } catch (IOException error) {
                p.sendMessage(Lang.getString("chat.unexpected_error"));
                plugin.LogError(error.getMessage());
            }

            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(newfile);
            List<ItemStack> items = MenuData.get(p.getUniqueId()).getItems();

            //for (int i = 0; i < items.size(); i++) {
            //    yaml.set(String.valueOf(i), items.get(i));
            //}
            new BukkitRunnable(){
                @Override
                public void run() {
                    asyncYamlWriter(yaml, items, newfile, p);
                }
            }.runTaskLaterAsynchronously(plugin, 0);

        }
    }

    private static void asyncYamlWriter(YamlConfiguration yml, List<ItemStack> items, File newfile, Player p) {
        formatYml(yml, items);
        try {
            yml.save(newfile);
        } catch (IOException error) {
            p.sendMessage(Lang.getString("chat.unexpected_error"));
            plugin.LogError(error.getMessage());
        }
        MenuData.remove(p.getUniqueId());
        p.sendMessage(Lang.getString("chat.recipe_create_success"));
    }

    private static void formatYml(YamlConfiguration yml, List<ItemStack> items) {
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
//                }
//            }
        }
        List<Character> chars = Arrays.asList( 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'); // imutable
        ArrayList<Character> grid = new ArrayList<>(Arrays.asList(null, null, null, null, null, null, null, null, null)); // mutable
        for (int i = 0; i < map.size(); i++) {
            item = (ItemStack) map.keySet().toArray()[i];
            for (int j : map.get(item)) {
                grid.set(j, chars.get(i));
            }
            yml.set(chars.get(i).toString(), item);
        }

        ArrayList<String> formated = reduceGrid(grid);
        yml.set("shape", formated);
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
        while (out.size() != 3) {
            out.add("");
        }
        return out;
    }
}
