package me.fofola1.CustomRecipes.Listeners;

import me.fofola1.CustomRecipes.Configs.Lang;
import me.fofola1.CustomRecipes.CustomRecipes;
import me.fofola1.CustomRecipes.Menus.Holders.CraftingRecipeMenuHolder;
import me.fofola1.CustomRecipes.Menus.Holders.MainMenuHolder;
import me.fofola1.CustomRecipes.Menus.Setups.CraftingRecipeMenu;
import me.fofola1.CustomRecipes.Menus.Setups.RecipesBookMenu;
import me.fofola1.CustomRecipes.Utils.MenuData;
import me.fofola1.CustomRecipes.Utils.Preloaded;
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
    private static final CustomRecipes plugin = CustomRecipes.getInstance();


    @EventHandler
    public void onMenuClick(InventoryClickEvent e) {
        InventoryHolder holder = e.getInventory().getHolder();

        if (holder instanceof MainMenuHolder) main_menu(e);
        else if (holder instanceof CraftingRecipeMenuHolder) crafting_recipe_menu(e);

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
                p.openInventory(new RecipesBookMenu().get());
                break;
            case 22:
                p.openInventory(new CraftingRecipeMenu().get());
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

        Player p = (Player) e.getWhoClicked();
        switch (e.getSlot()) {
            case 7:
                MenuData.get(p.getUniqueId()).autoDiscover = !MenuData.get(p.getUniqueId()).autoDiscover;
                if (MenuData.get(p.getUniqueId()).autoDiscover) {
                    topinv.setItem(7, Preloaded.discover_recipe_automaticaly);
                } else {
                    topinv.setItem(7, Preloaded.discover_recipe_manualy);
                }
            case 8:
                if (
                        e.getClickedInventory().equals(topinv) &&
                        Preloaded.crafting_allowed.stream().anyMatch(integer -> topinv.getItem(integer) != null) &&
                        topinv.getItem(Preloaded.crafting_output) != null
                ) {
                    ArrayList<ItemStack> items = new ArrayList<>();
                    ItemStack item;
                    for (int i : Preloaded.crafting_allowed) {
                        item = topinv.getItem(i);
                        if (item != null) item.setAmount(1);
                        items.add(topinv.getItem(i));
                    }
                    items.add(topinv.getItem(Preloaded.crafting_output));
                    MenuData.get(p.getUniqueId()).items = items;

                    p.sendMessage(Lang.getString("chat.enter_recipe_name"));
                    p.closeInventory();
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

}
