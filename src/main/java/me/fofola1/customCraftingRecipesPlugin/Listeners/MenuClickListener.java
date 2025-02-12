package me.fofola1.customCraftingRecipesPlugin.Listeners;

import me.fofola1.customCraftingRecipesPlugin.CustomCraftingRecipesPlugin;
import me.fofola1.customCraftingRecipesPlugin.Menus.Holders.AnvilSetNameHolder;
import me.fofola1.customCraftingRecipesPlugin.Menus.Holders.CraftingRacipeMenuHolder;
import me.fofola1.customCraftingRecipesPlugin.Menus.Holders.MainMenuHolder;
import me.fofola1.customCraftingRecipesPlugin.Menus.Setups.AnvilSetName;
import me.fofola1.customCraftingRecipesPlugin.Menus.Setups.CraftingRecipeMenu;
import me.fofola1.customCraftingRecipesPlugin.Menus.Setups.RecipesBookMenu;
import me.fofola1.customCraftingRecipesPlugin.Utils.DataStorage;
import me.fofola1.customCraftingRecipesPlugin.Utils.MenuData;
import me.fofola1.customCraftingRecipesPlugin.Utils.Preloaded;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class MenuClickListener implements Listener {
    private static final CustomCraftingRecipesPlugin plugin = CustomCraftingRecipesPlugin.getInstance();


    @EventHandler
    public void onMenuClick(InventoryClickEvent e) {
        InventoryHolder holder = e.getInventory().getHolder();

        if (holder instanceof MainMenuHolder) main_menu(e);
        else if (holder instanceof CraftingRacipeMenuHolder) crafting_recipe_menu(e);
        else if (holder instanceof AnvilSetNameHolder) anvil_set_name(e);

    }


    private void main_menu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();

        if (
                item == null
        ) return;

        e.setCancelled(true);
        switch (e.getSlot()) {
            case 40:
                p.closeInventory();
                break;
            case 36:
                p.openInventory(new RecipesBookMenu().getMenu());
                break;
            case 22:
                p.openInventory(new CraftingRecipeMenu().getMenu());
                break;
        }
    }

    private void crafting_recipe_menu(InventoryClickEvent e) {
        Inventory topinv = e.getView().getTopInventory();

        if (!(
                e.getCurrentItem() == null ||
                        e.getClickedInventory().equals(e.getView().getBottomInventory()) ||
                        (
                                e.getClickedInventory().equals(topinv) &&
                                        (
                                                Preloaded.crafting_allowed.contains(e.getSlot()) ||
                                                        e.getSlot() == Preloaded.crafting_output
                                        )
                        )
        )) e.setCancelled(true);

        switch (e.getSlot()) {
            case 0:
                if (
                        e.getClickedInventory().equals(topinv) &&
                        Preloaded.crafting_allowed.stream().anyMatch(integer -> topinv.getItem(integer) != null) &&
                        topinv.getItem(Preloaded.crafting_output) != null
                ) {
                    ArrayList<ItemStack> items = new ArrayList<>();
                    for (int i : Preloaded.crafting_allowed) {
                        items.add(topinv.getItem(i));
                    }
                    items.add(topinv.getItem(Preloaded.crafting_output));
                    MenuData.set(e.getWhoClicked().getUniqueId(), null, items);
                    e.getWhoClicked().openInventory(AnvilSetName.getMenu());
                }
                break;
            default:
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        async_crafting_recipe_menu(e);
                    }
                }.runTaskLaterAsynchronously(plugin, 0);
        }

    }
    private void async_crafting_recipe_menu(InventoryClickEvent e) {
        Inventory topinv = e.getView().getTopInventory();

        if (
                Preloaded.crafting_allowed.stream().allMatch(allowed_item -> topinv.getItem(allowed_item) == null) ||
                        topinv.getItem(Preloaded.crafting_output) == null
        ) topinv.setItem(8, Preloaded.create_recipe_unallowed);
        else topinv.setItem(8, Preloaded.create_recipe_allowed);


    }

    private void anvil_set_name(InventoryClickEvent e) {
        e.setCancelled(true);
        if (e.getClickedInventory().equals(e.getView().getBottomInventory()))
            return;

        if (e.getSlot() == 2) {
            Inventory newInv = new CraftingRecipeMenu().getMenu();
            DataStorage data = MenuData.get(e.getWhoClicked().getUniqueId());
            if (data == null) return;
            ArrayList<ItemStack> items = (ArrayList<ItemStack>) data.getItems();
            for (int i : Preloaded.crafting_allowed) {
                newInv.setItem(i, items.getFirst());
                items.removeFirst();
            }
            newInv.setItem(Preloaded.crafting_output, items.getLast());

            e.getWhoClicked().openInventory(newInv);

            new BukkitRunnable(){
                @Override
                public void run() {
                    async_anvil_set_name(e);
                }
            }.runTaskLaterAsynchronously(plugin, 0);
        }
    }

    private void async_anvil_set_name(InventoryClickEvent e) {
        MenuData.edit(e.getWhoClicked().getUniqueId(), e.getView().getTopInventory().getItem(2).getItemMeta().getDisplayName());
    }

}
