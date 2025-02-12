package me.fofola1.customCraftingRecipesPlugin.Menus;

import me.fofola1.customCraftingRecipesPlugin.Menus.Setups.CraftingRecipeMenu;
import me.fofola1.customCraftingRecipesPlugin.Menus.Setups.MainMenu;
import me.fofola1.customCraftingRecipesPlugin.Menus.Setups.RecipesBookMenu;

public class Menus {
    public static void init() {
        CraftingRecipeMenu.load();
        MainMenu.load();
        RecipesBookMenu.load();
    }
}
