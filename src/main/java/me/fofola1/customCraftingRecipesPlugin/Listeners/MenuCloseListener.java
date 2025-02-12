package me.fofola1.customCraftingRecipesPlugin.Listeners;

import me.fofola1.customCraftingRecipesPlugin.Menus.Holders.CraftingRacipeMenuHolder;
import me.fofola1.customCraftingRecipesPlugin.Utils.Preloaded;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class MenuCloseListener implements Listener {

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        InventoryHolder holder = e.getInventory().getHolder();

        if (holder instanceof CraftingRacipeMenuHolder) crafting_recipe_menu(e);
    }

    private void crafting_recipe_menu(InventoryCloseEvent e) {
        Inventory topinv = e.getView().getTopInventory();
        Inventory pinv = e.getPlayer().getInventory();

        for (int i : Preloaded.crafting_allowed) {
            if (topinv.getItem(i) != null) pinv.addItem(topinv.getItem(i));
        }
        if (topinv.getItem(Preloaded.crafting_output) != null) pinv.addItem(topinv.getItem(Preloaded.crafting_output));
    }


}
