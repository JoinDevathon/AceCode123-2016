package org.devathon.contest2016.objects;

import org.bukkit.inventory.ItemStack;

/**
 * Created by Anthony on 11/5/2016.
 */
public interface Screen {

    int getSize();
    ItemStack[] getItems();
    String getTitle();

}
