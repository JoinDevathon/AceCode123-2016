package org.devathon.contest2016.objects;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;

/**
 * Created by Anthony on 11/5/2016.
 */
public class RobotSpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(command.getName().equalsIgnoreCase("robot")) {
            if(commandSender instanceof ConsoleCommandSender) return true;
            if(strings.length == 0) {
                commandSender.sendMessage(ChatColor.RED + "Please specify the robot's name!");
                return true;
            }
            String name = ChatColor.translateAlternateColorCodes('&', strings[0]);
            if(NPCManager.getManager().addRobot((CraftPlayer) commandSender, name)) {
                commandSender.sendMessage(ChatColor.GREEN + "Successfully spawned Robot!");
            } else {
                commandSender.sendMessage(ChatColor.RED + "You already have a Robot!");
            }
        }
        if(command.getName().equalsIgnoreCase("shutdownRobot")) {
            if(commandSender instanceof ConsoleCommandSender) return true;
            if(!NPCManager.getManager().removeInstance((CraftPlayer) commandSender)) {
                commandSender.sendMessage(ChatColor.RED + "You don't have a Robot running!");
            }
        }
        return true;
    }
}
