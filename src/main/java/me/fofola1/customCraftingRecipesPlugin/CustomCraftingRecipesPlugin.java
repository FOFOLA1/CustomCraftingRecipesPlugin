package me.fofola1.customCraftingRecipesPlugin;

import me.fofola1.customCraftingRecipesPlugin.Commands.*;
import me.fofola1.customCraftingRecipesPlugin.Configs.Lang;
import me.fofola1.customCraftingRecipesPlugin.Listeners.MenuClickListener;
import me.fofola1.customCraftingRecipesPlugin.Listeners.MenuCloseListener;
import me.fofola1.customCraftingRecipesPlugin.Listeners.MenuDragListener;
import me.fofola1.customCraftingRecipesPlugin.Listeners.MessageListener;
import me.fofola1.customCraftingRecipesPlugin.Utils.Recipes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

// TODO Smazat nepotřebný importy
// TODO Smazat test commandy (i z plugin.yml), classy, outputy
// TODO Lepší vypisy do logu, třeba trochu formátování atd.
// TODO (opt.) Editor
// TODO Logika pro auto discover
// TODO vymyslet material pro auto discover v menu
// TODO (opt.) celý to přepsat do pořádnýho OOP
// TODO (opt.) lépe promyslet názvy a obsah tříd DataStorage, MenuData, Preloaded, Utils
// TODO Command na discovernutí recipu
// TODO Připsat další často opakované části kódu do metody do Recipes
// TODO Projít code inspektor a projít warningy
// TODO *************** UDĚLAT DOKUMENTACI ***************



public final class CustomCraftingRecipesPlugin extends JavaPlugin {

    private static CustomCraftingRecipesPlugin instance;
    public static NamespacedKey key;

    @Override
    public void onEnable() {
        instance = this;

        // Config.yml
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        Lang.load();
        Recipes.LoadAllRecipes();


        getCommand("removerecipe").setExecutor(new RemoveRecipeCommand()); // TEST
        getCommand("getrecipe").setExecutor(new GetRecipeCommand()); // TEST
        getCommand("getitemslist").setExecutor(new GetItemsListCommand()); // TEST
        getCommand("getmenudata").setExecutor(new getMenuDataCommand()); // TEST
        getCommand("getnewenfile").setExecutor(new GetNewEnFile());  // TEST
        getCommand("getlangstring").setExecutor(new GetLangStringCommand());  // TEST
        getCommand("getrecipeitems").setExecutor(new GetRecipeItemsCommand()); // TEST
        getCommand("customrecipe").setExecutor(new CustomCraftingCommand());

        getServer().getPluginManager().registerEvents(new MenuClickListener(), this);
        getServer().getPluginManager().registerEvents(new MenuCloseListener(), this);
        getServer().getPluginManager().registerEvents(new MenuDragListener(), this);
        getServer().getPluginManager().registerEvents(new MessageListener(), this);

        LogInfo("PLUGIN STARTED");
    }

    @Override
    public void onDisable() {
        Bukkit.removeRecipe(key);
        LogInfo("PLUGIN STOPPED");
    }


    public void LogInfo(String text) { getLogger().info(text); }
    public void LogWarn(String text) { getLogger().warning(text); }
    public void LogError(String text) { getLogger().severe(text); }

    public static CustomCraftingRecipesPlugin getInstance() { return instance; }
}
