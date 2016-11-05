package org.devathon.contest2016.objects;


import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by Anthony on 11/5/2016.
 */
public class BukkitHarddrive implements Serializable {

    private LinkedList<Data> info;

    public BukkitHarddrive(LinkedList<Data> info) {
        this.info = info;
    }

    public LinkedList<Data> getInfo() {
        return info;
    }

    public void update() {
        for(Data d : info) {
            try {
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(d.getFile()));
                out.writeObject(d.getItems());
                out.flush();
                out.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

}
