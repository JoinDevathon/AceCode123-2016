package org.devathon.contest2016.objects;

import net.minecraft.server.v1_10_R1.EntitySpectralArrow;
import net.minecraft.server.v1_10_R1.EnumParticle;
import net.minecraft.server.v1_10_R1.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_10_R1.World;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.devathon.contest2016.DevathonPlugin;

/**
 * Created by Anthony on 11/5/2016.
 */
public class Laser {

    private EnumParticle effect;
    private Location start;
    private Location target;

    public Laser(EnumParticle effect, Location start, Location target) {
        this.start = start;
        this.effect = effect;
        this.target = target;
    }

    public void shoot(Robot robot) {
        Location robotLocation = new Location(robot.getWorld().getWorld(), robot.locX, robot.locY+4, robot.locZ);
        Vector source = robotLocation.toVector();
        Vector target =  this.target.toVector();
        Vector direction = target.subtract(source).normalize();
        double speed = robotLocation.toVector().length();
        final Arrow arrow = Bukkit.getWorld(robot.getWorld().getWorld().getName()).spawnArrow(new Location(robot.getWorld().getWorld(), robot.locX, robot.locY+5, robot.locZ, robot.yaw, robot.pitch), direction.multiply(speed), 1f, 1f);
        NPCManager.getManager().getFired().add(arrow);
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player online : Bukkit.getOnlinePlayers()) {
                    Location loc = new Location(arrow.getWorld(), arrow.getLocation().getX(), arrow.getLocation().getY(), arrow.getLocation().getZ(), arrow.getLocation().getPitch(), arrow.getLocation().getYaw());
                    ((CraftPlayer) online).getHandle().playerConnection
                            .sendPacket(new PacketPlayOutWorldParticles(
                                    EnumParticle.FLAME, true, (float) arrow.getLocation().getX(),
                                    (float) arrow.getLocation().getY(), (float) arrow.getLocation().getZ(), 1,
                                    1, 1, (float) 0, 6));
                }
            }
        }.runTaskTimer(DevathonPlugin.getInstance(), 0, 1);

    }
}
