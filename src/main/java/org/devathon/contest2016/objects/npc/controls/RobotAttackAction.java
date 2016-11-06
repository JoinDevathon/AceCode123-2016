package org.devathon.contest2016.objects;

import net.minecraft.server.v1_10_R1.EnumParticle;
import net.minecraft.server.v1_10_R1.PathfinderGoal;
import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * Created by Anthony on 11/5/2016.
 */
public class RobotAttackAction extends PathfinderGoal {

    private Robot robot;
    private int ticks = 0;
    private int shots = 0;

    public RobotAttackAction(Robot robot) {
        this.robot = robot;
    }

    @Override
    public boolean a() {
        if(robot.getTarget() != null && shots < 5) {
            return true;
        } else if(shots == 5) {
            robot.setTarget(null);
            shots = 0;
        }
        return false;
    }

    @Override
    public void e() {
        ticks++;
        if(ticks % 5 == 0) {
            shots++;
            Laser laser = new Laser(EnumParticle.FLAME, new Location(robot.getWorld().getWorld(), robot.locX, robot.locY, robot.locZ, robot.yaw, robot.pitch), robot.getTarget().getLocation());
            laser.shoot(robot);
        }
    }
}
