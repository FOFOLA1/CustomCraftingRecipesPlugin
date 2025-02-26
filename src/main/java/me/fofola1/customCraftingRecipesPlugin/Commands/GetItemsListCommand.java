package me.fofola1.customCraftingRecipesPlugin.Commands;

import me.fofola1.customCraftingRecipesPlugin.Utils.MenuData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetItemsListCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player p = (Player) commandSender;
        p.sendMessage(MenuData.get(p.getUniqueId()).getItems().toString());

        return true;
    }
}
