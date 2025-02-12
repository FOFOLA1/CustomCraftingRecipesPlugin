package me.fofola1.customCraftingRecipesPlugin.Menus.Setups;

import me.fofola1.customCraftingRecipesPlugin.Configs.Lang;
import me.fofola1.customCraftingRecipesPlugin.Menus.Holders.CraftingRacipeMenuHolder;
import me.fofola1.customCraftingRecipesPlugin.Utils.Items;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class CraftingRecipeMenu {
    private static Inventory craftingRecipeMenu;

    public Inventory getMenu() {
        Inventory newinv = Bukkit.createInventory(new CraftingRacipeMenuHolder(), 6*9, ChatColor.translateAlternateColorCodes('&',Lang.getString("recipes_creators.crafting.header")));
        newinv.setContents(craftingRecipeMenu.getContents());
        return newinv;
    }

    public static void load() {
        ItemMeta meta;

        craftingRecipeMenu = Bukkit.createInventory(new CraftingRacipeMenuHolder(), 6*9, ChatColor.translateAlternateColorCodes('&', Lang.getString("recipes_creators.crafting.header")));

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

        craftingRecipeMenu.setItem(8, Items.createGuiItem(
                Material.RED_WOOL,
                Lang.getString("recipes_creators.crafting.create_recipe.unallowed"),
                new ArrayList<>()
        ));


    }

}
