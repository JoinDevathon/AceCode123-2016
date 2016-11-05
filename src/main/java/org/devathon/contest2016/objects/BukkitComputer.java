package org.devathon.contest2016.objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Anthony on 11/5/2016.
 */
public class BukkitComputer implements Runnable {

    private Location loc;
    private CraftPlayer player;
    private ArrayList<Application> apps;
    private boolean on;
    private Screen home;

    public BukkitComputer(CraftPlayer player, Location loc, ArrayList<Application> apps, Screen home) {
        this.loc = loc;
        this.player = player;
        this.apps = apps;
        this.home = home;
        start();
    }

    public ArrayList<Application> getApps() {
        return apps;
    }

    public Location getLoc() {
        return loc;
    }

    public CraftPlayer getPlayer() {
        return player;
    }

    public void setLoc(Location location) {
        this.loc = location;
    }

    public void openApplication(ItemStack item) {
        for(Application app : apps) {
            if(app.getIcon().equals(item)) {
                app.openGUI(player);
                break;
            }
        }
    }

    private void start() {
        on = true;
        Inventory inventory = Bukkit.createInventory(player, home.getSize(), home.getTitle());
        inventory.setContents(home.getItems());
        player.openInventory(inventory);
    }

    @Override
    public void run() {
        while(on) {

        }
    }
}
