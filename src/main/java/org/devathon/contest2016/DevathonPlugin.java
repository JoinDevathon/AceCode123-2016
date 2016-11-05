package org.devathon.contest2016;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.devathon.contest2016.commands.ComputerCommand;

public class DevathonPlugin extends JavaPlugin {

    private static DevathonPlugin instance;


    @Override
    public void onEnable() {
       instance = this;
        registerCommands();

    }

    private void registerCommands() {
        ComputerCommand command = new ComputerCommand();
        Bukkit.getServer().getPluginCommand("bukkitcomputer").setExecutor(command);
    }

    @Override
    public void onDisable() {
        // put your disable code here
    }

    public static DevathonPlugin getInstance() {
        return instance;
    }
}

