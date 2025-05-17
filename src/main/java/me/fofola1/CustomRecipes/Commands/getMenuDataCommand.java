package me.fofola1.CustomRecipes.Commands;

import me.fofola1.CustomRecipes.Utils.MenuData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class getMenuDataCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        commandSender.sendMessage(MenuData.getUUIDList().toString());


        return true;
    }
}
