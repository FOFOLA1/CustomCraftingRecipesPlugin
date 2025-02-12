package me.fofola1.customCraftingRecipesPlugin.Menus.Setups;

import me.fofola1.customCraftingRecipesPlugin.Configs.Lang;
import me.fofola1.customCraftingRecipesPlugin.Menus.Holders.AnvilSetNameHolder;
import me.fofola1.customCraftingRecipesPlugin.Utils.Items;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class AnvilSetName {
    private static Inventory anvilInv;

    public static Inventory getMenu() {
        Inventory newinv = Bukkit.createInventory(new AnvilSetNameHolder(), InventoryType.ANVIL, Lang.getString("set_name_anvil.header"));
        newinv.setContents(anvilInv.getContents());
        return newinv;
    }

    public static void load() {

        ItemMeta meta;

        anvilInv = Bukkit.createInventory(new AnvilSetNameHolder(), InventoryType.ANVIL, Lang.getString("set_name_anvil.header"));

        ItemStack name = new ItemStack(Material.NAME_TAG);
        meta = name.getItemMeta();
        meta.setDisplayName(" ");
        ArrayList lore = new ArrayList<String>();
        meta.setLore(new ArrayList<>());
        name.setItemMeta(meta);


        anvilInv.setItem(0, Items.createGuiItem(
                Material.NAME_TAG,
                Lang.getString("set_name_anvil.header"),
                new ArrayList<>()
        ));

    }

}
