package org.devathon.contest2016.objects;

import net.minecraft.server.v1_10_R1.EntitySlime;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Anthony on 11/5/2016.
 */
public class GeneralListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(e.getAction().equals(Action.RIGHT_CLICK_AIR) && NPCManager.getManager().hasRobot((CraftPlayer) e.getPlayer())) {
            System.out.println("Clicked!");
            NPCManager.getManager().performInstruction((CraftPlayer) e.getPlayer(), e.getItem());
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if(e.getEntity() instanceof EntitySlime) {
            NPCManager.getManager().removeInstance((EntitySlime) e.getEntity());
        }
    }

    @EventHandler
    public void onHit(ProjectileHitEvent e) {
        if(NPCManager.getManager().getFired().contains(e.getEntity())) {
            NPCManager.getManager().getFired().remove(e.getEntity());
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(e.getInventory().getTitle().equals(ChatColor.GREEN + "Laser Targets ◎")) {
            if(e.getCurrentItem() != null) {
                Player player = Bukkit.getPlayer(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
                if(player != null && player.isOnline()) {
                    NPCManager.getManager().startLaserAttack((Player) e.getWhoClicked(), player);
                    e.setCancelled(true);
                    e.getWhoClicked().closeInventory();
                } else {
                    e.getWhoClicked().sendMessage(ChatColor.RED + "Target Player not found!");
                }
            }
        }
    }

}
