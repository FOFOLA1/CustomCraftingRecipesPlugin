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
import java.util.Arrays;

public class MainMenu {
    public Inventory setup() {
        ItemMeta meta;

        Inventory inventory = Bukkit.createInventory(null, 5*9, ChatColor.translateAlternateColorCodes('&',Lang.getString("main_menu.header")));

        ItemStack bg1 = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE);
        ItemStack bg2 = new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE);
        meta = bg1.getItemMeta();
        meta.setDisplayName(" ");
        meta.setLore(new ArrayList<>());
        bg1.setItemMeta(meta);
        bg2.setItemMeta(meta);

        for (int i = 0; i < 9; i++) inventory.setItem(i, bg2);
        for (int i = 9; i < 4*9; i++) inventory.setItem(i, bg1);
        for (int i = 4*9; i < 5*9; i++) inventory.setItem(i, bg2);

        inventory.setItem(22, createGuiItem(
                Material.CRAFTING_TABLE,
                Lang.getString("main_menu.crafting_table.name"),
                Lang.getStringList("main_menu.crafting_table.lore")
        ));

        inventory.setItem(36, createGuiItem(
                Material.KNOWLEDGE_BOOK,
                Lang.getString("main_menu.custom_recipes_book.name"),
                Lang.getStringList("main_menu.custom_recipes_book.lore")
        ));

        inventory.setItem(40, createGuiItem(
                Material.BARRIER,
                Lang.getString("main_menu.exit.name"),
                Lang.getStringList("main_menu.exit.lore")
        ));



        return inventory;
    }

    private ItemStack createGuiItem(final Material material, final String name, final ArrayList<String> lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }
}
