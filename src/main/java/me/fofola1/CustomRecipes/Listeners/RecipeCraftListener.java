package me.fofola1.CustomRecipes.Listeners;

import me.fofola1.CustomRecipes.Utils.Recipes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ShapedRecipe;

public class RecipeCraftListener implements Listener {
    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent e) {
        if (e.getRecipe() == null || e.getInventory() == null) return;

        if (e.getRecipe() instanceof ShapedRecipe) {
            ShapedRecipe recipe = (ShapedRecipe) e.getRecipe();
            for (ShapedRecipe i : Recipes.ManualDiscoverRecipes) {
                if (i.getKey().equals(recipe.getKey())) {
                    if (e.getViewers().get(0) instanceof Player) {
                        Player p = (Player) e.getViewers().get(0);
                        if (!p.hasDiscoveredRecipe(i.getKey())) {
                            e.getInventory().setResult(null);
                        }
                    }
                }
            }
        }
    }
}
