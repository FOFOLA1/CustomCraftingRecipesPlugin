package me.fofola1.CustomRecipes;

import me.fofola1.CustomRecipes.Commands.*;
import me.fofola1.CustomRecipes.Configs.Lang;
import me.fofola1.CustomRecipes.Listeners.*;
import me.fofola1.CustomRecipes.Menus.Holders.CraftingRecipeMenuHolder;
import me.fofola1.CustomRecipes.Menus.Holders.MainMenuHolder;
import me.fofola1.CustomRecipes.Menus.Holders.RecipesBookMenuHolder;
import me.fofola1.CustomRecipes.Utils.MenuData;
import me.fofola1.CustomRecipes.Utils.Recipes;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.UUID;

// TODO Smazat nepotřebný importy
// TODO Smazat test commandy (i z plugin.yml), classy, outputy
// TODO Lepší vypisy do logu, třeba trochu formátování atd.
// TODO (opt.) Editor
// TODO Logika pro auto discover
// TODO vymyslet material pro auto discover v menu
// TODO (opt.) celý to přepsat do pořádnýho OOP
// TODO (opt.) lépe promyslet názvy a obsah tříd DataStorage, MenuData, Preloaded, Utils
// TODO Command na discovernutí recipu
// TODO Připsat další často opakované části kódu do metody do Recipes X
// TODO Projít code inspektor a projít warningy
// TODO *************** UDĚLAT DOKUMENTACI *************** X
// TODO dodělat metody v CustomRecipesCommand



public final class CustomRecipes extends JavaPlugin {

    private static CustomRecipes instance;

    @Override
    public void onEnable() {
        instance = this;

        // Config.yml
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        Recipes.AutoDiscoverRecipes = new ArrayList<ShapedRecipe>();
        Recipes.ManualDiscoverRecipes = new ArrayList<ShapedRecipe>();

        Lang.load();
        Recipes.LoadAllRecipes();


        getCommand("removerecipe").setExecutor(new RemoveRecipeCommand()); // TEST
        getCommand("getrecipe").setExecutor(new GetRecipeCommand()); // TEST
        getCommand("getitemslist").setExecutor(new GetItemsListCommand()); // TEST
        getCommand("getmenudata").setExecutor(new getMenuDataCommand()); // TEST
        getCommand("getnewenfile").setExecutor(new GetNewEnFile());  // TEST
        getCommand("getlangstring").setExecutor(new GetLangStringCommand());  // TEST
        getCommand("getrecipeitems").setExecutor(new GetRecipeItemsCommand()); // TEST
        getCommand("customrecipe").setExecutor(new CustomRecipesCommand());
        getCommand("customrecipe").setTabCompleter(new CustomRecipesTabCompleter());


        getServer().getPluginManager().registerEvents(new MenuClickListener(), this);
        getServer().getPluginManager().registerEvents(new MenuCloseListener(), this);
        getServer().getPluginManager().registerEvents(new MenuDragListener(), this);
        getServer().getPluginManager().registerEvents(new MessageListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new RecipeCraftListener(), this);

        LogInfo("PLUGIN STARTED");
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        //HandlerList.unregisterAll();
        for (ShapedRecipe recipe: Recipes.AutoDiscoverRecipes) {
            Bukkit.removeRecipe(recipe.getKey());
        }
        for (ShapedRecipe recipe: Recipes.ManualDiscoverRecipes) {
            Bukkit.removeRecipe(recipe.getKey());
        }
        Recipes.ManualDiscoverRecipes = null;
        Recipes.AutoDiscoverRecipes = null;

        for (UUID uuid : MenuData.getUUIDList()) {
            Player p = Bukkit.getPlayer(uuid);
            if (p == null) continue;
            if (!p.isOnline()) continue;
            if (
                    p.getOpenInventory().getTopInventory().getHolder() instanceof CraftingRecipeMenuHolder ||
                    p.getOpenInventory().getTopInventory().getHolder() instanceof MainMenuHolder ||
                    p.getOpenInventory().getTopInventory().getHolder() instanceof RecipesBookMenuHolder
            ) {
                p.closeInventory();
                p.sendMessage("Plugin is currently disabling, try again later!");
            }
        }

        saveConfig();


        LogInfo("PLUGIN STOPPED");
    }

    public void reloadPlugin() {
        Bukkit.getScheduler().cancelTasks(this);

        for (ShapedRecipe recipe: Recipes.AutoDiscoverRecipes) {
            Bukkit.removeRecipe(recipe.getKey());
        }
        for (ShapedRecipe recipe: Recipes.ManualDiscoverRecipes) {
            Bukkit.removeRecipe(recipe.getKey());
        }
        Recipes.ManualDiscoverRecipes = null;
        Recipes.AutoDiscoverRecipes = null;
        Lang.unload();

        for (UUID uuid : MenuData.getUUIDList()) {
            Player p = Bukkit.getPlayer(uuid);
            if (!p.isOnline()) continue;
            if (
                    p.getOpenInventory().getTopInventory().getHolder() instanceof CraftingRecipeMenuHolder ||
                            p.getOpenInventory().getTopInventory().getHolder() instanceof MainMenuHolder ||
                            p.getOpenInventory().getTopInventory().getHolder() instanceof RecipesBookMenuHolder
            ) {
                p.closeInventory();
                p.sendMessage("Plugin is currently disabling, try again later!");
            }
        }
        saveConfig();
        LogInfo("PLUGIN STOPPED");


        getConfig().options().copyDefaults();
        saveDefaultConfig();

        Recipes.AutoDiscoverRecipes = new ArrayList<ShapedRecipe>();
        Recipes.ManualDiscoverRecipes = new ArrayList<ShapedRecipe>();

        Lang.load();
        Recipes.LoadAllRecipes();
        LogInfo("PLUGIN STARTED");
    }


    public void LogInfo(String text) { getLogger().info(text); }
    public void LogWarn(String text) { getLogger().warning(text); }
    public void LogError(String text) { getLogger().severe(text); }

    public static CustomRecipes getInstance() { return instance; }
}
