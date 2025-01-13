package me.fofola1.customCraftingRecipesPlugin.Commands;

import me.fofola1.customCraftingRecipesPlugin.Menus.MainMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CustomCraftingCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) ((Player) commandSender).openInventory(MainMenu.setup());
        return true;
    }
}
