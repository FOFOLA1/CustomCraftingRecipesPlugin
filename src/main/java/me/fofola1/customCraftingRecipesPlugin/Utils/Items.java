package me.fofola1.customCraftingRecipesPlugin.Utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Items {
    public static ItemStack createGuiItem(final Material material, final String name, final ArrayList<String> lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }
    public static ItemStack createGuiItem(final Material material, final String name, final ArrayList<String> lore, int amout) {
        final ItemStack item = new ItemStack(material, amout);
        final ItemMeta meta = item.getItemMeta();

        System.out.println(name);

        meta.setDisplayName(name);
        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }
}
