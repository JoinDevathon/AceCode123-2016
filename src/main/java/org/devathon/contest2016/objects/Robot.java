package org.devathon.contest2016.objects;

import net.minecraft.server.v1_10_R1.*;
import net.minecraft.server.v1_10_R1.Material;
import net.minecraft.server.v1_10_R1.World;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

/**
 * Created by Anthony on 11/5/2016.
 */
public class Robot extends NPC {

    private int updateLatency;
    private int ticks;
    private float attackDamage;
    private Player target;

    public Robot(World world, String name, int updateLatency, float attackDamage, Location location) {
        super(world);
        this.updateLatency = updateLatency;
        this.attackDamage = attackDamage;
        setCustomName(name);
        Set goalB = (Set) getPrivateField("b", PathfinderGoalSelector.class, goalSelector); goalB.clear();
        Set goalC = (Set) getPrivateField("c", PathfinderGoalSelector.class, goalSelector); goalC.clear();
        Set targetB = (Set) getPrivateField("b", PathfinderGoalSelector.class, targetSelector); targetB.clear();
        Set targetC = (Set) getPrivateField("c", PathfinderGoalSelector.class, targetSelector); targetC.clear();
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this. goalSelector.a(0, new RobotAttackAction(this));

        spawn(this, location);
    }

    public static Object getPrivateField(String fieldName, Class clazz, Object object)
    {
        Field field;
        Object o = null;

        try
        {
            field = clazz.getDeclaredField(fieldName);

            field.setAccessible(true);

            o = field.get(object);
        }
        catch(NoSuchFieldException e)
        {
            e.printStackTrace();
        }
        catch(IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return o;
    }

    public void attack(Player player) {
        this.target = player;
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    public Player getTarget() {
        return target;
    }

    public String getName() {
        return this.getCustomName();
    }

    public int getUpdateLatency() {
        return updateLatency;
    }

    @Override
    public float getAttackDamage() {
        return attackDamage;
    }


    @Override
    public void listen(Instructions instructions) {
        ticks++;
        if(ticks % updateLatency == 0) {
            followInstructions(instructions);
        }
    }

    public void rotate90() {
        setYawPitch(yaw+90, pitch);
    }



    public void followInstructions(Instructions instructions) {
        switch(instructions) {
            case FORWARD:
                if(Bukkit.getServer().getWorld(this.world.getWorld().getName()).getBlockAt(new Location(this.getWorld().getWorld(), locX, locY, locZ-1.0)) == Material.AIR) {
                    this.move(locX, locY, locZ-1);
                }
                break;
            case BACKWARD:
                if(Bukkit.getServer().getWorld(this.world.getWorld().getName()).getBlockAt(new Location(this.getWorld().getWorld(), locX, locY, locZ-1.0)) == Material.AIR) {
                    this.move(locX, locY, locZ-1);
                }
                break;
            case LEFT:
                if(Bukkit.getServer().getWorld(this.world.getWorld().getName()).getBlockAt(new Location(this.getWorld().getWorld(), locX-1, locY, locZ)) == Material.AIR) {
                    this.move(locX-1, locY, locZ);
                }
                break;
            case RIGHT:
                if(Bukkit.getServer().getWorld(this.world.getWorld().getName()).getBlockAt(new Location(this.getWorld().getWorld(), locX+1, locY, locZ)) == Material.AIR) {
                    this.move(locX+1, locY, locZ);
                }
                break;
            case UP:
                if(Bukkit.getServer().getWorld(this.world.getWorld().getName()).getBlockAt(new Location(this.getWorld().getWorld(), locX, locY+1, locZ)) == Material.AIR) {
                    this.move(locX, locY+1, locZ);
                }
                break;
            case DOWN:
                if(Bukkit.getServer().getWorld(this.world.getWorld().getName()).getBlockAt(new Location(this.getWorld().getWorld(), locX, locY-1, locZ)) == Material.AIR) {
                    this.move(locX, locY - 1, locZ);
                }
                break;

            case FIREWORK:
                FireworkEffect effect = FireworkEffect.builder().flicker(true).trail(true).withColor(Color.BLUE).build();
                Firework fw = world.getWorld().spawn(new Location(getWorld().getWorld(), locX, locY, locZ), Firework.class);
                FireworkMeta meta = fw.getFireworkMeta();
                meta.clearEffects();
                meta.addEffect(effect);
                meta.setPower(0);
                fw.setFireworkMeta(meta);
                break;
            default:
                System.out.println("Default case called!");

        }
    }


    @Override
    public void spawn(Entity entity, Location loc) {
        entity.setNoGravity(true);
        entity.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        ((CraftWorld) loc.getWorld()).getHandle().addEntity(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
        ((Slime) entity.getBukkitEntity()).teleport(loc);
        System.out.println("Spawned");
        System.out.println(entity.locX + "," + entity.locY + "," + loc.getZ());
    }


}
