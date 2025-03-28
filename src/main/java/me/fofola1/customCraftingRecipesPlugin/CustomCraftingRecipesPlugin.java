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


        ItemStack item = new ItemStack(Material.DIAMOND_SWORD);

        // The meta of the diamond sword where we can change the name, and properties of the item.
        ItemMeta meta = item.getItemMeta();

        // We will initialise the next variable after changing the properties of the sword

        // This sets the name of the item.
        meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Emerald Sword");

        // Set the meta of the sword to the edited meta.
        item.setItemMeta(meta);

        // Add the custom enchantment to make the emerald sword special
        // In this case, we're adding the permission that modifies the damage value on level 5
        // Level 5 is represented by the second parameter. You can change this to anything compatible with a sword
        // create a NamespacedKey for your recipe
        key = new NamespacedKey(this, "emerald_sword");

        // Create our custom recipe variable
        ShapedRecipe recipe = new ShapedRecipe(key, item);

        // Here we will set the places. E and S can represent anything, and the letters can be anything. Beware; this is case sensitive.
        recipe.shape("E", "E", "S");

        // Set what the letters represent.
        // E = Emerald, S = Stick
        recipe.setIngredient('E', Material.EMERALD);
        recipe.setIngredient('S', Material.STICK);

        // Finally, add the recipe to the bukkit recipes
        Bukkit.addRecipe(recipe);





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
