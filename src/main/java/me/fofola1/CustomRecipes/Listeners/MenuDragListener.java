package me.fofola1.CustomRecipes.Listeners;

import me.fofola1.CustomRecipes.CustomRecipes;
import me.fofola1.CustomRecipes.Menus.Holders.CraftingRecipeMenuHolder;
import me.fofola1.CustomRecipes.Utils.Preloaded;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.scheduler.BukkitRunnable;

public class MenuDragListener implements Listener {
    private static final CustomRecipes plugin = CustomRecipes.getInstance();

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        InventoryHolder holder = e.getInventory().getHolder();

        if (holder instanceof CraftingRecipeMenuHolder) crafting_recipe_menu(e);
    }


    private void crafting_recipe_menu(InventoryDragEvent e) {
        new BukkitRunnable(){
            @Override
            public void run() {
                asyncDrag(e);
            }
        }.runTaskLaterAsynchronously(plugin, 0);
    }
    private void asyncDrag(InventoryDragEvent e) {
        Inventory topinv = e.getView().getTopInventory();

        if (
                Preloaded.crafting_allowed.stream().allMatch(allowed_item -> topinv.getItem(allowed_item) == null) ||
                        topinv.getItem(Preloaded.crafting_output) == null
        ) topinv.setItem(8, Preloaded.create_recipe_unallowed);
        else topinv.setItem(8, Preloaded.create_recipe_allowed);
    }
}
