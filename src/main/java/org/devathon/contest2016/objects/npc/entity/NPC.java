package org.devathon.contest2016.objects;

import net.minecraft.server.v1_10_R1.Entity;
import net.minecraft.server.v1_10_R1.EntitySlime;
import net.minecraft.server.v1_10_R1.World;
import org.bukkit.Location;

/**
 * Created by Anthony on 11/5/2016.
 */
public interface NPC {

    int getUpdateLatency();
    float getAttackDamage();
    void listen(Instructions instructions);
    void spawn(Entity entity, Location loc);




}
