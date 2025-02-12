package me.fofola1.customCraftingRecipesPlugin.Commands;

import me.fofola1.customCraftingRecipesPlugin.Configs.Lang;
import me.fofola1.customCraftingRecipesPlugin.CustomCraftingRecipesPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.File;


public class GetNewEnFile implements CommandExecutor {

    private static final CustomCraftingRecipesPlugin plugin = CustomCraftingRecipesPlugin.getInstance();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        File file = new File(plugin.getDataFolder() + "/Lang", "en.yml");
        if (file.exists()) {
            file.delete();
            Lang.reload();
        }



        return true;
    }
}
