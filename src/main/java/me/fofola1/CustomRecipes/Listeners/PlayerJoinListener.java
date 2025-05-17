package me.fofola1.CustomRecipes.Listeners;

import me.fofola1.CustomRecipes.Utils.Recipes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ShapedRecipe;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        for (ShapedRecipe recipe : Recipes.AutoDiscoverRecipes) {
            if (!p.hasDiscoveredRecipe(recipe.getKey())) {
                p.discoverRecipe(recipe.getKey());
            }
        }
    }
}
