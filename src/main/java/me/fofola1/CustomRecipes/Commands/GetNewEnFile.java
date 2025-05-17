package me.fofola1.CustomRecipes.Commands;

import me.fofola1.CustomRecipes.Configs.Lang;
import me.fofola1.CustomRecipes.CustomRecipes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.File;


public class GetNewEnFile implements CommandExecutor {

    private static final CustomRecipes plugin = CustomRecipes.getInstance();

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
