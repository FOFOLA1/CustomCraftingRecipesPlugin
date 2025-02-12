package me.fofola1.customCraftingRecipesPlugin.Utils;

import me.fofola1.customCraftingRecipesPlugin.Configs.Lang;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class Preloaded {

    public static ItemStack create_recipe_allowed = Items.createGuiItem(Material.LIME_WOOL, Lang.getString("recipes_creators.crafting.create_recipe.allowed"), new ArrayList<>());
    public static ItemStack create_recipe_unallowed = Items.createGuiItem(Material.RED_WOOL, Lang.getString("recipes_creators.crafting.create_recipe.unallowed"), new ArrayList<>());

    public static ArrayList<Integer> crafting_allowed = new ArrayList<>(Arrays.asList(
            19, 20, 21,
            28, 29, 30,
            37, 38, 39
    ));
    public static int crafting_output = 32;
}
