package org.devathon.contest2016.objects.screens;

import org.bukkit.inventory.ItemStack;
import org.devathon.contest2016.objects.Application;
import org.devathon.contest2016.objects.Screen;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Anthony on 11/5/2016.
 */
public class HomeScreen implements Screen {

    private ArrayList<Application> toolbar;
    private String title;

    public HomeScreen(String title, ArrayList<Application> toolbar) {
        this.toolbar = toolbar;
        this.title = title;
    }

    @Override
    public int getSize() {
        return 27;
    }

    @Override
    public ItemStack[] getItems() {
        ItemStack[] items = new ItemStack[27];
        for(int x = 0; x < toolbar.size(); x++) {
            for(int i = 26; i > -1; i--) {
                    if (toolbar.get(x) != null) {
                        System.out.println(toolbar.size());
                        System.out.println(toolbar.get(0));
                        items[i] = toolbar.get(x).getIcon();
                        i = -1;
                    }
            }
         }
        return items;
    }

    @Override
    public String getTitle() {
        return title;
    }
}
