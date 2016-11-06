package org.devathon.contest2016.objects;

import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Anthony on 11/5/2016.
 */
public class Controller {

    private CraftPlayer player;
    private NPC npc;
    private LinkedList<Instructions> instructions = new LinkedList<>();

    public Controller(CraftPlayer player, NPC npc) {
        this.player = player;
        this.npc = npc;
    }

    public Instructions getInstructions() {
        if(instructions.size() > 0) {
            return instructions.getFirst();
        }
        return null;
    }

    public void removeInstructions() {
        instructions.removeFirst();
    }

    public void addInstruction(Instructions instructions) {
        this.instructions.add(instructions);
    }

    public CraftPlayer getPlayer() {
        return player;
    }

    public void setPlayer(CraftPlayer player) {
        this.player = player;
    }


    public NPC getNpc() {
        return npc;
    }

    public void setNpc(NPC npc) {
        this.npc = npc;
    }
}
