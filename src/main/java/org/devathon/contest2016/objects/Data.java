package org.devathon.contest2016.objects;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.Serializable;

/**
 * Created by Anthony on 11/5/2016.
 */
public class Data implements Serializable {

    private File file;
    private ItemStack[] items;

    public Data(File file, ItemStack[] items) {

        this.items = items;
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public ItemStack[] getItems() {
        return this.items;
    }

    public void updateItems(ItemStack[] items) {
        this.items = items;
    }

}
