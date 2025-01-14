package me.fofola1.customCraftingRecipesPlugin.Commands;

import me.fofola1.customCraftingRecipesPlugin.Configs.Lang;
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
