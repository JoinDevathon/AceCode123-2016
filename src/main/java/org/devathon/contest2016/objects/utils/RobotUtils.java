package org.devathon.contest2016.objects;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by Anthony on 11/5/2016.
 */
public class RobotUtils {

    public static ItemStack generate(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        item.setItemMeta(itemMeta);
        return item;
    }

}
