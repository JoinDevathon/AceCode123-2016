package org.devathon.contest2016.objects;

import net.minecraft.server.v1_10_R1.Entity;
import net.minecraft.server.v1_10_R1.EntitySlime;
import net.minecraft.server.v1_10_R1.World;
import org.bukkit.Location;

/**
 * Created by Anthony on 11/5/2016.
 */
public abstract class NPC extends EntitySlime {

    public NPC(World world) {
        super(world);
    }

    public abstract int getUpdateLatency();
    public abstract float getAttackDamage();
    public abstract void listen(Instructions instructions);
    public abstract void spawn(Entity entity, Location loc);




}
