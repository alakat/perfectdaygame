/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.perfectday.main.dummyengine.threads;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import org.perfectday.main.dummyengine.commons.AnimateSprite;

/**
 *
 * @author Miguel (alakat@gmail.com)
 */
public class DummySpriteControlerRunner implements Runnable {

    private static final int FRAME_SECOND = 45;
    private final Object lock = new Object();
    private List<Component> components;
    private boolean myStop;

    public DummySpriteControlerRunner() {
        myStop = false;
        components = new ArrayList<Component>();
    }

    public void addComponent(Component c) {
        this.components.add(c);
    }

    public void clearComponents() {
        this.components.clear();
    }

    public void removeComponent(Component c) {
        this.components.remove(c);
    }

    public void setMyStop(boolean myStop) {
        this.myStop = myStop;
    }
    
   

    @Override
    public void run() {
        try {
            while (!myStop) {
                long timeToWait = 1000 / FRAME_SECOND;
                for (Component component : components) {
                    component.repaint();
                }
                synchronized (lock) {
                    lock.wait(timeToWait);
                }
            }
        } catch (InterruptedException ex) {
            System.err.println(ex);
        }

    }

}
