package me.fofola1.customCraftingRecipesPlugin.Menus.Setups;

import me.fofola1.customCraftingRecipesPlugin.Configs.Lang;
import me.fofola1.customCraftingRecipesPlugin.CustomCraftingRecipesPlugin;
import me.fofola1.customCraftingRecipesPlugin.Menus.Holders.MainMenuHolder;
import me.fofola1.customCraftingRecipesPlugin.Utils.Items;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainMenu {
    private static final CustomCraftingRecipesPlugin plugin = CustomCraftingRecipesPlugin.getInstance();
    private static Inventory main_menu;

    public Inventory getMenu() {
        Inventory newinv = Bukkit.createInventory(new MainMenuHolder(), 5*9, ChatColor.translateAlternateColorCodes('&',Lang.getString("main_menu.header")));
        newinv.setContents(main_menu.getContents());
        return newinv;
    }

    public static void load() {
        ItemMeta meta;

        main_menu = Bukkit.createInventory(new MainMenuHolder(), 5*9, ChatColor.translateAlternateColorCodes('&',Lang.getString("main_menu.header")));

        ItemStack bg1 = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE);
        ItemStack bg2 = new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE);
        meta = bg1.getItemMeta();
        meta.setDisplayName(" ");
        meta.setLore(new ArrayList<>());
        bg1.setItemMeta(meta);
        bg2.setItemMeta(meta);

        for (int i = 0; i < 9; i++) main_menu.setItem(i, bg2);
        for (int i = 9; i < 4*9; i++) main_menu.setItem(i, bg1);
        for (int i = 4*9; i < 5*9; i++) main_menu.setItem(i, bg2);

        main_menu.setItem(22, Items.createGuiItem(
                Material.CRAFTING_TABLE,
                Lang.getString("main_menu.crafting_table.name"),
                Lang.getStringList("main_menu.crafting_table.lore")
        ));

        main_menu.setItem(36, Items.createGuiItem(
                Material.KNOWLEDGE_BOOK,
                Lang.getString("main_menu.custom_recipes_book.name"),
                Lang.getStringList("main_menu.custom_recipes_book.lore")
        ));

        main_menu.setItem(40, Items.createGuiItem(
                Material.BARRIER,
                Lang.getString("main_menu.exit.name"),
                Lang.getStringList("main_menu.exit.lore")
        ));

        File file = new File(plugin.getDataFolder() + "", "menu.yml");
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FileConfiguration conf = YamlConfiguration.loadConfiguration(file);
        conf.set("main_menu", main_menu.getContents());
        try {
            conf.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
