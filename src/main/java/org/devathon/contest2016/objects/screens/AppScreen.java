package org.devathon.contest2016.objects.screens;

import org.bukkit.inventory.ItemStack;
import org.devathon.contest2016.objects.Application;
import org.devathon.contest2016.objects.Screen;

/**
 * Created by Anthony on 11/5/2016.
 */
public class AppScreen implements Screen {

    private String name;
    private ItemStack[] contents;

    public AppScreen(String name, ItemStack[] contents) {
        this.name = name;
        this.contents = contents;
    }

    @Override
    public int getSize() {
        return contents.length;
    }

    @Override
    public ItemStack[] getItems() {
        return contents;
    }

    @Override
    public String getTitle() {
        return name;
    }

}
