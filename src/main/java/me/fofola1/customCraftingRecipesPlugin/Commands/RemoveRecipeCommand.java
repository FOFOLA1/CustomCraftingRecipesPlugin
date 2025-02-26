package me.fofola1.customCraftingRecipesPlugin.Commands;

import me.fofola1.customCraftingRecipesPlugin.CustomCraftingRecipesPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveRecipeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        System.out.println(CustomCraftingRecipesPlugin.key);
        Player p = (Player) commandSender;
        p.undiscoverRecipe(CustomCraftingRecipesPlugin.key);
        System.out.println(p.getDiscoveredRecipes().toString());
        return true;
    }
}
