package me.fofola1.customCraftingRecipesPlugin.Commands;

import me.fofola1.customCraftingRecipesPlugin.Configs.Lang;
import me.fofola1.customCraftingRecipesPlugin.Menus.Setups.MainMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CustomCraftingCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player) {
            if (commandSender.hasPermission("customrecipes.menu")) {
                ((Player) commandSender).openInventory(new MainMenu().getMenu());
            } else {
                commandSender.sendMessage(Lang.getString("commands.has_no_permission"));
            }
        }

        return true;
    }
}
