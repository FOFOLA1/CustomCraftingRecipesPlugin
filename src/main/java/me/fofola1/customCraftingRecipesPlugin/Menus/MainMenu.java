package me.fofola1.customCraftingRecipesPlugin.Menus;

import me.fofola1.customCraftingRecipesPlugin.Configs.Lang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class MainMenu {
    public static Inventory setup() {
        ItemMeta meta;
        ArrayList<String> lore;

        Inventory inventory = Bukkit.createInventory(null, 5*9, ChatColor.translateAlternateColorCodes('&',Lang.get("main_menu.header")));

        ItemStack bg1 = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE);
        ItemStack bg2 = new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE);
        meta = bg1.getItemMeta();
        meta.setDisplayName("");
        meta.setLore(new ArrayList<>());
        bg1.setItemMeta(meta);
        bg2.setItemMeta(meta);

        for (int i = 0; i < 9; i++) inventory.setItem(i, bg2);
        for (int i = 9; i < 4*9; i++) inventory.setItem(i, bg1);
        for (int i = 4*9; i < 5*9; i++) inventory.setItem(i, bg2);

        ItemStack craftingTable = new ItemStack(Material.CRAFTING_TABLE);
        meta = craftingTable.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',Lang.get("main_menu.crafting_table_name")));
        lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&',Lang.get("main_menu.crafting_table_lore")));
        meta.setLore(lore);
        craftingTable.setItemMeta(meta);
        inventory.setItem(22, craftingTable);



        return inventory;
    }
}
