/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.gamebuilder.model;

import org.perfectday.logicengine.model.minis.MiniLevel;

/**
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class MiniDescription {

    private MiniLevel level;
    private String mini;
    private double cost;
    private String description;
    private int startX;
    private int startY;
    private String name;

    public MiniDescription() {
        this.level = MiniLevel.SIMPLE_SOLDIER;
    }
    
    

    public MiniDescription(String name,double c, String desc) {
      this.mini = name;
      this.level = MiniLevel.SIMPLE_SOLDIER;
      this.cost = c;
      this.description=desc;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MiniLevel getLevel() {
        return level;
    }

    public void setLevel(MiniLevel level) {
        this.level = level;
    }

    public String getMini() {
        return mini;
    }

    public void setMini(String mini) {
        this.mini = mini;
    }

    @Override
    public String toString() {
        return this.getMini();
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
    
    
    
}
