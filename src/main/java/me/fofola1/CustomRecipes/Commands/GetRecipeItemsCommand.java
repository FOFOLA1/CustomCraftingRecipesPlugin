package me.fofola1.CustomRecipes.Commands;

import me.fofola1.CustomRecipes.CustomRecipes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.Arrays;

public class GetRecipeItemsCommand implements CommandExecutor {
    private static final CustomRecipes plugin = CustomRecipes.getInstance();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Inventory inv = ((Player) commandSender).getInventory();
        File file = new File(plugin.getDataFolder() + "/Recipes/" + strings[0] + ".yml");

        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        ItemStack item = null;
        for (int i = 0; i < 10; i++) {
            item = yaml.getItemStack("" + i);
            if (item != null) {
                inv.addItem(item);
            }

        }

        return true;
    }
}
