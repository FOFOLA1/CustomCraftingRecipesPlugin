package me.fofola1.customCraftingRecipesPlugin;

import me.fofola1.customCraftingRecipesPlugin.Commands.CustomCraftingCommand;
import me.fofola1.customCraftingRecipesPlugin.Configs.Lang;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class CustomCraftingRecipesPlugin extends JavaPlugin {

    private static CustomCraftingRecipesPlugin instance;

    @Override
    public void onEnable() {
        instance = this;

        // Config.yml
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        Lang.load();










        getCommand("customrecipe").setExecutor(new CustomCraftingCommand());
        LogInfo("PLUGIN STARTED");
    }

    @Override
    public void onDisable() {

        LogInfo("PLUGIN STOPPED");
    }


    public void LogInfo(String text) { getLogger().info(text); }
    public void LogWarn(String text) { getLogger().warning(text); }
    public void LogError(String text) { getLogger().severe(text); }

    public static CustomCraftingRecipesPlugin getInstance() { return instance; }
}
