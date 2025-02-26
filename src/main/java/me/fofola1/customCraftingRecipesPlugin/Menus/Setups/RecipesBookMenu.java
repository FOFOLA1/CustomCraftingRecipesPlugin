package me.fofola1.customCraftingRecipesPlugin.Menus.Setups;

import me.fofola1.customCraftingRecipesPlugin.Configs.Lang;
import me.fofola1.customCraftingRecipesPlugin.Menus.Holders.CraftingRacipeMenuHolder;
import me.fofola1.customCraftingRecipesPlugin.Menus.Holders.RecipesBookMenuHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class RecipesBookMenu {
    public Inventory recipeBookMenu;

    public Inventory get() {
        return recipeBookMenu;
    }

    public RecipesBookMenu() {
        ItemMeta meta;

        recipeBookMenu = Bukkit.createInventory(new RecipesBookMenuHolder(), 5*9, ChatColor.translateAlternateColorCodes('&', Lang.getString("main_menu.header")));

        ItemStack bg1 = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE);
        ItemStack bg2 = new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE);
        meta = bg1.getItemMeta();
        meta.setDisplayName(" ");
        meta.setLore(new ArrayList<>());
        bg1.setItemMeta(meta);
        bg2.setItemMeta(meta);

        for (int i = 0; i < 9; i++) recipeBookMenu.setItem(i, bg2);
        for (int i = 9; i < 4*9; i++) recipeBookMenu.setItem(i, bg1);
        for (int i = 4*9; i < 5*9; i++) recipeBookMenu.setItem(i, bg2);


    }
}
