package me.fofola1.CustomRecipes.Commands;

import me.fofola1.CustomRecipes.CustomRecipes;
import me.fofola1.CustomRecipes.Utils.Recipes;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomRecipesTabCompleter implements TabCompleter {
    private static final CustomRecipes plugin = CustomRecipes.getInstance();

    private final List<String> subcommands = Arrays.asList("discover", "help", "reload");
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 1) {
            List<String> suggestions = new ArrayList<>();
            for (String sub : subcommands) {
                if (sub.toLowerCase().startsWith(args[0].toLowerCase())) {
                    suggestions.add(sub);
                }
            }
            return suggestions;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("discover")) {
            List<String> players = new ArrayList<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getName().toLowerCase().startsWith(args[1].toLowerCase())) {
                    players.add(player.getName());
                }
            }
            return players;
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("discover")) {
            List<String> recipes = new ArrayList<>();
            for (ShapedRecipe recipe : Recipes.ManualDiscoverRecipes) {
                if (
                    recipe.getKey()
                        .toString()
                        .replace(plugin.getName().toLowerCase() + ":", "")
                        .toLowerCase()
                        .startsWith(args[2].toLowerCase())
                ) {
                    recipes.add(
                            recipe.getKey()
                                    .toString()
                                    .replace(plugin.getName().toLowerCase() + ":", "")
                    );
                }
            }
            for (ShapedRecipe recipe : Recipes.AutoDiscoverRecipes) {
                if (recipe.getKey()
                        .toString()
                        .replace(plugin.getName().toLowerCase() + ":", "")
                        .toLowerCase()
                        .startsWith(args[2].toLowerCase())) {
                    recipes.add(
                            recipe.getKey()
                                    .toString()
                                    .replace(plugin.getName().toLowerCase() + ":", "")
                    );
                }
            }
            return recipes;
        }

        return new ArrayList<>();
    }
}
