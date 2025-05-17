package me.fofola1.CustomRecipes.Commands;

import me.fofola1.CustomRecipes.Utils.MenuData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetItemsListCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player p = (Player) commandSender;
        if (MenuData.get(p.getUniqueId()) != null)
            p.sendMessage(MenuData.get(p.getUniqueId()).getItems().toString());
        else p.sendMessage("null");
        return true;
    }
}
