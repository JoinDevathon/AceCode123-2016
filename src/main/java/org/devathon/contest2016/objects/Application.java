package org.devathon.contest2016.objects;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Anthony on 11/5/2016.
 */
public abstract class Application {

    private Screen inv;
    private String name;
    private ItemStack icon;

    public Application(String name, ItemStack icon, Screen inv) {
        this.inv = inv;
        this.name = name;
        this.icon = icon;
    }

    public abstract void performAction(CraftPlayer player);

    public void openGUI(Player player) {
        Inventory inventory = Bukkit.createInventory(player, inv.getSize(), inv.getTitle());
        inventory.setContents(inv.getItems());
        player.openInventory(inventory);
    }

    public ItemStack getIcon() {
        return this.icon;
    }

    public String getName() {
        return name;
    }

    public Screen getInv() {
        return inv;
    }

}
