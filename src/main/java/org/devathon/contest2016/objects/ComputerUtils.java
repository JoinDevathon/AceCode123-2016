package org.devathon.contest2016.objects;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

/**
 * Created by Anthony on 11/5/2016.
 */
public class ComputerUtils {

    public static ItemStack createItem(Material material, String name, ArrayList<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return item;
    }

}
