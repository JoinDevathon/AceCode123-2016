package org.devathon.contest2016.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.devathon.contest2016.objects.PlayerManager;

/**
 * Created by Anthony on 11/5/2016.
 */
public class ComputerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(command.getName().equalsIgnoreCase("BukkitComputer")) {
            PlayerManager.getManager().addComputer(((Player) commandSender));
        }
        return true;
    }
}
