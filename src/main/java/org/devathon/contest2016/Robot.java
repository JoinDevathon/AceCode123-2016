package org.devathon.contest2016.objects;

import net.minecraft.server.v1_10_R1.*;
import net.minecraft.server.v1_10_R1.Material;
import net.minecraft.server.v1_10_R1.World;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Anthony on 11/5/2016.
 */
public class Robot extends EntitySlime {

    static {
        registerEntity(String.valueOf(NPCManager.getManager().getId()), 55, EntitySlime.class, Robot.class);
    }

    private int updateLatency;
    private int ticks;
    private float attackDamage;
    private Player target;
    private EntitySlime entity;

    public Robot(World world, String name, int updateLatency, float attackDamage, Location location) {
        super(world);
        this.updateLatency = updateLatency;
        this.attackDamage = attackDamage;
        setCustomName(name);
        spawn(this, location);
    }


    @Override
    protected void r() {
        goalSelector = new PathfinderGoalSelector(new MethodProfiler());
        goalSelector.a(1, new PathfinderGoalFloat(this));
        goalSelector.a(0, new RobotAttackAction(this));

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

    public float getAttackDamage() {
        return attackDamage;
    }


    public void listen(Entity entity, Instructions instructions) {
        ticks++;
        if(ticks % updateLatency == 0) {
//            followInstructions(entity, instructions);
            this.setPositionRotation(locX, locY, locZ+25, yaw, pitch);
            this.move(locX, locY+25, locZ);
            this.getNavigation().a(locX+25, locY, locZ);
        }
    }


    public void rotate90() {
        setYawPitch(yaw+90, pitch);
    }


    public void followInstructions(Entity entity, Instructions instructions) {
        switch(instructions) {
            case FORWARD:
                if(Bukkit.getServer().getWorld(this.world.getWorld().getName()).getBlockAt(new Location(this.getWorld().getWorld(), locX, locY, locZ-1.0)) == Material.AIR) {
                    entity.getBukkitEntity().teleport(new Location(this.getWorld().getWorld(), locX, locY, locZ+1, yaw, pitch));
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

    public static void registerEntity(String name, int id, Class<? extends EntityInsentient> nmsClass, Class<? extends EntityInsentient> customClass){
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


    public void spawn(EntitySlime entity, Location loc) {

        setNoGravity(true);
        entity.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        ((CraftWorld) loc.getWorld()).getHandle().addEntity(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
        entity.getBukkitEntity().teleport(loc);
        entity.setAI(false);
        RobotTimer.setSlime(entity);


    }


}
