package org.devathon.contest2016.objects;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_10_R1.EntitySlime;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.devathon.contest2016.DevathonPlugin;

import java.util.ArrayList;

/**
 * Created by Anthony on 11/5/2016.
 */
public class RobotTimer {

    private static ArrayList<NPC> robots = new ArrayList<>();
    private static EntitySlime slime;

    public static void setSlime(EntitySlime slime2) {
        slime = slime2;
    }

    public static void start() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(DevathonPlugin.getInstance(), new BukkitRunnable() {
            @Override
            public void run() {
             for(Controller controller : NPCManager.getManager().getControllers()) {
                 if(controller.getInstructions() != null) {
                     controller.getNpc().listen(slime, controller.getInstructions());
                     controller.removeInstructions();
                 }

                 TextComponent message = new TextComponent();
                 message.setText("ROBOT: ");
                 message.setColor(ChatColor.BLUE);
                 message.addExtra(controller.getNpc().getName());
                 message.setColor(ChatColor.RED);
                 message.addExtra(" ACTIVE: ");
                 message.setColor(ChatColor.BLUE);
                 message.addExtra(String.valueOf(!controller.getNpc().dead));
                 message.setColor(ChatColor.RED);
                 message.setBold(true);
                 controller.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, message);
             }
            }
        }, 20, 5);
    }

    public static void addNPC(NPC robot) {
        robots.add(robot);
    }

    public static void despawn(NPC robot) {
        robots.remove(robot);
    }


}
