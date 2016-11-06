package org.devathon.contest2016.objects;

import net.minecraft.server.v1_10_R1.EntityInsentient;
import net.minecraft.server.v1_10_R1.EntitySlime;
import net.minecraft.server.v1_10_R1.EntityTypes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by Anthony on 11/5/2016.
 */
public class NPCManager {

    private static NPCManager manager;
    private ArrayList<Controller> controllers = new ArrayList<>();
    public HashMap<ItemStack, Instructions> controls = new HashMap<>();
    private ArrayList<Laser> fired = new ArrayList<>();
    private HashMap<UUID, ItemStack[]> contents = new HashMap<>();

    public static NPCManager getManager() {
        return manager == null ? manager = new NPCManager() : manager;
    }

    private NPCManager() {}

    public ArrayList<Laser> getFired() {
        return fired;
    }

    public ArrayList<Controller> getControllers() {
        return controllers;
    }

    public boolean hasRobot(CraftPlayer player) {
        System.out.println(controllers.size());
        for(Controller controller : controllers) {
            if(controller.getPlayer().getName().equals(player.getName())) {
                return true;
            }
        }
        return false;
    }

    public void registerItems() {
        ItemStack forward = RobotUtils.generate(Material.WOOD_SWORD, ChatColor.GREEN + "FORWARD " + ChatColor.GRAY + "(Right Click)");
        ItemStack backward = RobotUtils.generate(Material.GOLD_SWORD, ChatColor.GREEN + "BACKWARD " + ChatColor.GRAY + "(Right Click)");
        ItemStack right = RobotUtils.generate(Material.STONE_SWORD, ChatColor.GREEN + "RIGHT " + ChatColor.GRAY + "(Right Click)");
        ItemStack left = RobotUtils.generate(Material.IRON_SWORD, ChatColor.GREEN + "LEFT " + ChatColor.GRAY + "(Right Click)");
        ItemStack up = RobotUtils.generate(Material.DIAMOND, ChatColor.GREEN + "UP " + ChatColor.GRAY + "(Right Click)");
        ItemStack down = RobotUtils.generate(Material.COAL, ChatColor.GREEN + "DOWN " + ChatColor.GRAY + "(Right Click)");
        ItemStack firework = RobotUtils.generate(Material.FIREWORK, ChatColor.GREEN + "FIREWORK " + ChatColor.GRAY + "(Right Click)");
        controls.put(firework, Instructions.FIREWORK);
        controls.put(forward, Instructions.FORWARD);
        controls.put(backward, Instructions.BACKWARD);
        controls.put(right, Instructions.RIGHT);
        controls.put(left, Instructions.LEFT);
        controls.put(up, Instructions.UP);
        controls.put(down, Instructions.DOWN);

    }

    public void shutdown() {
        ArrayList<Controller> copy = new ArrayList<>();
        for(Controller controller : controllers) {
            copy.add(controller);
        }
        for(Controller controller : copy) {
            removeInstance(controller.getPlayer());
        }
    }

    public boolean addRobot(CraftPlayer player, String name) {
        if(!hasRobot(player)) {
            Robot robot = new Robot(((CraftWorld) player.getLocation().getWorld()).getHandle(), name, 1, 2.5f, player.getLocation());
            Controller controller = new Controller(player, robot);
            controllers.add(controller);
            contents.put(player.getUniqueId(), player.getInventory().getContents());
            ItemStack attack = RobotUtils.generate(Material.NETHER_STAR, ChatColor.GREEN + "LASER ATTACK " + ChatColor.GRAY + "(Right Click)");
            player.getInventory().setItem(0, attack);
            int i = 1;
            for(ItemStack item : controls.keySet()) {
                player.getInventory().setItem(i, item);
                i++;
            }
            return true;
        }
        return false;
    }

    public void performInstruction(CraftPlayer player, ItemStack itemStack) {
        for(ItemStack item : controls.keySet()) {
            if(item.equals(itemStack)) {
                System.out.println("In set");
                for(Controller controller : controllers) {
                    if(controller.getPlayer().equals(player)) {
                        System.out.println("Instruction added! " + controls.get(item).toString().toUpperCase() + "!");
                        controller.addInstruction(controls.get(item));
                        player.sendMessage(ChatColor.GREEN + "Robot performing " + ChatColor.YELLOW + "" + ChatColor.BOLD + controls.get(item).toString().toUpperCase() + ChatColor.GREEN + " Action!");
                        break;
                    }
                }
            }
        }
        ItemStack attack = RobotUtils.generate(Material.NETHER_STAR, ChatColor.GREEN + "LASER ATTACK " + ChatColor.GRAY + "(Right Click)");
        if(itemStack.equals(attack)) {
            Inventory inv = Bukkit.createInventory(null, 27, ChatColor.GREEN + "Laser Targets ◎");
            for(Player p : Bukkit.getOnlinePlayers()) {
                inv.addItem(RobotUtils.generate(Material.NAME_TAG, ChatColor.YELLOW + p.getName()));
            }
            player.openInventory(inv);
        }
    }

    public void startLaserAttack(Player sender, Player to) {
        for(Controller controller : controllers) {
            if(controller.getPlayer().equals((CraftPlayer) sender)) {
                ((Robot)controller.getNpc()).attack(to);
                sender.sendMessage(ChatColor.GREEN + "Commencing Laser attack on " + ChatColor.RED + to.getName() + ChatColor.GREEN + "!");
            }
        }
    }

    public boolean removeInstance(EntitySlime entity) {
        Controller control = null;
        for(Controller controller : controllers) {
            if(((EntitySlime) controller.getNpc()).equals(entity)) {
                control = controller;
                break;
            }
        }
        if(control != null) {
            control.getPlayer().sendMessage(ChatColor.RED + "Your Robot has stopped ticking :'(");
            control.getPlayer().getInventory().setContents(contents.get(control.getPlayer().getUniqueId()));
            return controllers.remove(control);
        }
        return false;
    }

    public boolean removeInstance(CraftPlayer player) {
        Controller control = null;
        for(Controller controller : controllers) {
            if(controller.getPlayer().equals(player)) {
                control = controller;
                break;
            }
        }
        if(control != null) {
            control.getPlayer().sendMessage(ChatColor.RED + "Your Robot has stopped ticking :'(");
            control.getPlayer().getInventory().setContents(contents.get(control.getPlayer().getUniqueId()));
            return controllers.remove(control);
        }
        return false;
    }

    public void registerEntity(String name, int id, Class<? extends EntityInsentient> nmsClass, Class<? extends EntityInsentient> customClass){
        try {

            List<Map<?, ?>> dataMap = new ArrayList<Map<?, ?>>();
            for (Field f : EntityTypes.class.getDeclaredFields()){
                if (f.getType().getSimpleName().equals(Map.class.getSimpleName())){
                    f.setAccessible(true);
                    dataMap.add((Map<?, ?>) f.get(null));
                }
            }

            if (dataMap.get(2).containsKey(id)){
                dataMap.get(0).remove(name);
                dataMap.get(2).remove(id);
            }

            Method method = EntityTypes.class.getDeclaredMethod("a", Class.class, String.class, int.class);
            method.setAccessible(true);
            method.invoke(null, customClass, name, id);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
