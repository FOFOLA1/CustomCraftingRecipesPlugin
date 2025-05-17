package me.fofola1.CustomRecipes.Commands;

import me.fofola1.CustomRecipes.Configs.Lang;
import me.fofola1.CustomRecipes.CustomRecipes;
import me.fofola1.CustomRecipes.Menus.Setups.MainMenu;
import me.fofola1.CustomRecipes.Utils.Recipes;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.PluginManager;

public class CustomRecipesCommand implements CommandExecutor {
    private static final CustomRecipes plugin = CustomRecipes.getInstance();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (commandSender instanceof Player) {
            if (commandSender.hasPermission("customrecipes.menu")) {
                if (args.length == 0)
                    ((Player) commandSender).openInventory(new MainMenu().get());
                else {
                    switch (args[0].toLowerCase()) {
                        case "discover":
                            discover_recipe((Player) commandSender, args);
                            break;
                        case "help":
                            show_help((Player) commandSender);
                            break;
                        case "reload":
                            reload_plugin((Player) commandSender);
                    }
                }
            } else {
                commandSender.sendMessage(Lang.getString("commands.has_no_permission"));
            }
        }

        return true;
    }

    private void discover_recipe(Player p, String[] args) {
        Player target = Bukkit.getPlayer(args[1]);
        String key = plugin.getName().toLowerCase() + ":" + args[2];
        for (ShapedRecipe recipe : Recipes.ManualDiscoverRecipes) {
            if (recipe.getKey().toString().equalsIgnoreCase(key)) {
                assert target != null;
                if (!target.hasDiscoveredRecipe(recipe.getKey()))
                    target.discoverRecipe(recipe.getKey());
                return;
            }
        }
        for (ShapedRecipe recipe : Recipes.AutoDiscoverRecipes) {
            if (recipe.getKey().toString().equalsIgnoreCase(key)) {
                assert target != null;
                if (!target.hasDiscoveredRecipe(recipe.getKey()))
                    target.discoverRecipe(recipe.getKey());
                return;
            }
        }
    }

    private void show_help(Player p) {
        //TODO dodělat
    }

    private void reload_plugin(Player p) {
        p.sendMessage("Reloading...");
        plugin.reloadPlugin();
        p.sendMessage("Reloaded!");
    }
}
