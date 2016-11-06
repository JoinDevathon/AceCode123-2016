package org.devathon.contest2016.objects;

import net.minecraft.server.v1_10_R1.EntitySpectralArrow;
import net.minecraft.server.v1_10_R1.EnumParticle;
import net.minecraft.server.v1_10_R1.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_10_R1.World;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.devathon.contest2016.DevathonPlugin;

/**
 * Created by Anthony on 11/5/2016.
 */
public class Laser extends EntitySpectralArrow {

    private EnumParticle effect;
    private Location start;
    private Location target;

    public Laser(World world, EnumParticle effect, Location start, Location target) {
        super(world);
        setNoGravity(true);
        this.start = start;
        this.effect = effect;
        this.target = target;
    }

    public void shoot(Robot robot) {
        robot.getWorld().addEntity(this, CreatureSpawnEvent.SpawnReason.CURED);
        this.setLocation(robot.locX, robot.locY, robot.locX, robot.yaw, robot.pitch);
        super.shoot(target.getX(), target.getY(), target.getZ(), target.getYaw(), target.getPitch());
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player online : Bukkit.getOnlinePlayers()) {
                    Location loc = new Location(getWorld().getWorld(), locX, locY, locZ, yaw, pitch);
                    ((CraftPlayer) online).getHandle().playerConnection
                            .sendPacket(new PacketPlayOutWorldParticles(
                                    effect, true, (float) locX,
                                    (float) locY, (float) locZ, 1,
                                    1, 1, (float) 0, 6));
                }
            }
        }.runTaskTimer(DevathonPlugin.getInstance(), 0, 1);

    }
}
