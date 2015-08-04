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
    
    
    
    
}
