package org.devathon.contest2016.objects;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_10_R1.PlayerList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.CraftServer;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.devathon.contest2016.objects.apps.FileCreator;
import org.devathon.contest2016.objects.screens.AppScreen;
import org.devathon.contest2016.objects.screens.HomeScreen;

import java.util.ArrayList;

/**
 * Created by Anthony on 11/5/2016.
 */
public class PlayerManager {

    private static PlayerManager manager;
    private ArrayList<Application> apps;
    private ArrayList<BukkitComputer> instances;

    public static PlayerManager getManager() {
        return manager == null ? new PlayerManager() : manager;
    }

    public void register() {
        this.apps = new ArrayList<>();
        this.instances = new ArrayList<>();
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GREEN + "For all your file creating");
        lore.add(ChatColor.GREEN + "& printing needs!");
        ItemStack creatorIcon = ComputerUtils.createItem(Material.FIREBALL, ChatColor.GOLD + "File Creator", lore);
        AppScreen screen = new AppScreen("File Creator", new ItemStack[27]);
        FileCreator creator = new FileCreator("File Creator", creatorIcon, screen);
        apps.add(creator);
    }

    private PlayerManager() {
        register();
    }

    public void removeInstance(Player player) {
        for(BukkitComputer computer : instances) {
            if(computer.getPlayer().equals((CraftPlayer) player)) {
                instances.remove(computer);
            }
        }
    }

    public boolean addComputer(Player player) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        BukkitComputer computer = new BukkitComputer(craftPlayer, player.getLocation(), apps, new HomeScreen(ChatColor.GREEN + "Home: ", apps));
        instances.add(computer);
        return true;
    }


}
