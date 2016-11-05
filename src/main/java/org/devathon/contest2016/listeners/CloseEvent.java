package org.devathon.contest2016.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.devathon.contest2016.objects.PlayerManager;

/**
 * Created by Anthony on 11/5/2016.
 */
public class CloseEvent implements Listener {

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        PlayerManager.getManager().removeInstance((Player) e.getPlayer());
    }

}
