package org.devathon.contest2016;

import net.minecraft.server.v1_10_R1.EntitySlime;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.devathon.contest2016.objects.*;

public class DevathonPlugin extends JavaPlugin {

    private static DevathonPlugin instance;


    @Override
    public void onEnable() {
        instance = this;
        registerCommands();
        registerListeners();
        NPCManager.getManager().registerItems();
        NPCManager.getManager().registerEntity("Slime", 55, EntitySlime.class, Robot.class);
        RobotTimer.start();
    }

    private void registerCommands() {
        RobotSpawnCommand cmd = new RobotSpawnCommand();
        Bukkit.getServer().getPluginCommand("robot").setExecutor(cmd);
    }

    public void registerListeners() {
        GeneralListener listener = new GeneralListener();
        Bukkit.getServer().getPluginManager().registerEvents(listener, this);
    }

    @Override
    public void onDisable() {
        // put your disable code here
        NPCManager.getManager().shutdown();
    }

    public static DevathonPlugin getInstance() {
        return instance;
    }
}

