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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            String grid = "";

            for (int i = 0; i < items.size(); i++) {
                yaml.set(String.valueOf(i), items.get(i));
            }
            try {
                yaml.save(newfile);
            } catch (IOException error) {
                p.sendMessage(Lang.getString("chat.unexpected_error"));
                plugin.LogError(error.getMessage());
            }


            MenuData.remove(p.getUniqueId());
            p.sendMessage(Lang.getString("chat.recipe_create_success"));
        }
    }
}
