package me.fofola1.CustomRecipes.Commands;

import me.fofola1.CustomRecipes.Configs.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GetLangStringCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        commandSender.sendMessage(Lang.getString(strings[0]));




        return true;
    }
}
