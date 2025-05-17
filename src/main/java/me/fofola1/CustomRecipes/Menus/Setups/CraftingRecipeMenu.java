package me.fofola1.CustomRecipes.Menus.Setups;

import me.fofola1.CustomRecipes.Configs.Lang;
import me.fofola1.CustomRecipes.Menus.Holders.CraftingRecipeMenuHolder;
import me.fofola1.CustomRecipes.Utils.Preloaded;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class CraftingRecipeMenu {
    private Inventory craftingRecipeMenu;

    public Inventory get() {
        return craftingRecipeMenu;
    }

    public CraftingRecipeMenu() {
        ItemMeta meta;

        craftingRecipeMenu = Bukkit.createInventory(new CraftingRecipeMenuHolder(), 6*9, ChatColor.translateAlternateColorCodes('&', Lang.getString("recipes_creators.crafting.header")));

        ItemStack bg1 = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE);
        meta = bg1.getItemMeta();
        meta.setDisplayName(" ");
        meta.setLore(new ArrayList<>());
        bg1.setItemMeta(meta);

        ArrayList<Integer> spaces = new ArrayList<>(Arrays.asList(
                19, 20, 21,
                28, 29, 30, 32,
                37, 38, 39
        ));
        for (int i = 0; i < 6*9; i++) if (!spaces.contains(i)) craftingRecipeMenu.setItem(i, bg1);

        craftingRecipeMenu.setItem(8, Preloaded.create_recipe_unallowed);
        craftingRecipeMenu.setItem(7, Preloaded.discover_recipe_automaticaly);


    }

}
