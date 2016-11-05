package org.devathon.contest2016.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Anthony on 11/5/2016.
 */
public class InteractEvent implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_AIR) {

        }
    }

}
