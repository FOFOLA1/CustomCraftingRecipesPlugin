package me.fofola1.customCraftingRecipesPlugin.Configs;

import me.fofola1.customCraftingRecipesPlugin.Commands.CustomCraftingCommand;
import me.fofola1.customCraftingRecipesPlugin.CustomCraftingRecipesPlugin;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class Lang {

    private static Map<String, String> active_messages = new HashMap<>();
    private static Map<String, String> fallback_messages = new HashMap<>();
    private static CustomCraftingRecipesPlugin plugin = CustomCraftingRecipesPlugin.getInstance();


    public static String get(String key) {
        if (active_messages.get(key) != null) return ChatColor.translateAlternateColorCodes('&', active_messages.get(key));
        else if (fallback_messages.get(key) != null) {
            plugin.LogWarn("Key \"" + key + "\" was not found in activated language, using key from fallback language instead!");
            return ChatColor.translateAlternateColorCodes('&', fallback_messages.get(key));
        }
        else {
            plugin.LogError("Key \"" + key + "\" was not found in active nor fallback language! Did not using any text for this key!");
            return "";
        }
    }

    public static void load() {
        File langFolder = new File(plugin.getDataFolder() + "/Lang");
        String langFileName = plugin.getConfig().getString("lang") + ".yml";

        if (!langFolder.exists()) {
            langFolder.mkdirs();
        }

        File setFile = new File(langFolder, langFileName);

        try {
            File enFile = new File(langFolder, "en.yml");
            if (!enFile.exists()) {
                InputStream in = plugin.getResource("Lang/en.yml");
                Files.copy(in, enFile.toPath());
                plugin.LogInfo("Created \"Lang/en.yml\". If you want to use different language, make a copy, rename it, translate it and replace value of \"lang\" in \"config.yml\" to file name without file extension.");

            }

            load_lang(enFile, fallback_messages);
            plugin.LogInfo("Fallback language loaded");

            if (!setFile.exists()) {
                plugin.LogWarn("Lang file " + langFileName + " not found! Create one or change value of \"lang\" in \"config.yml\". For now using only fallback language (" + enFile.getName() + ").");
                active_messages = fallback_messages;
            } else {
                plugin.LogInfo("Loading language: " + setFile.getName());
                load_lang(setFile, active_messages);
                plugin.LogInfo(get("log.on_load").replace("%lang_file_name%", setFile.getName()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static void load_lang(File file, Map<String, String> target) {
        FileConfiguration lang = YamlConfiguration.loadConfiguration(file);
        for (String key : lang.getKeys(false)) {
            for (String messName : lang.getConfigurationSection(key).getKeys(false)) {
                String message = lang.getString(key + "." + messName);
                target.put(key + "." + messName, message);
            }
        }
    }
}
