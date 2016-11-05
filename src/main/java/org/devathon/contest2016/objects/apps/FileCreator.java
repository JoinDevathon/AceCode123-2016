package org.devathon.contest2016.objects.apps;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.devathon.contest2016.objects.Application;
import org.devathon.contest2016.objects.Screen;

/**
 * Created by Anthony on 11/5/2016.
 */
public class FileCreator extends Application {

    public FileCreator(String name, ItemStack icon, Screen inv) {
        super(name, icon, inv);
    }

    @Override
    public void performAction(CraftPlayer player) {

    }
}
