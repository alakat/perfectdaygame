/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 
 */

package org.perfectday.gamebuilder.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class BattleDescription {

    private Mission mission;
    private int point;
    private boolean battlePublic;
    private List<MiniDescription> minis;
    private int battleWeidth;
    private int battleHeigth;
    private String enemy;
    

    public BattleDescription() {
       minis = new ArrayList<MiniDescription>();
       this.battleWeidth = 17;
       this.battleHeigth = 17;
    }

    
    
    public boolean isBattlePublic() {
        return battlePublic;
        
    }

    public void setBattlePublic(boolean battlePublic) {
        this.battlePublic = battlePublic;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public List<MiniDescription> getMinis() {
        return minis;
    }

    public void setMinis(List<MiniDescription> minis) {
        this.minis = minis;
    }

    public String getEnemy() {
        return enemy;
    }

    public void setEnemy(String enemy) {
        this.enemy = enemy;
    }

    public int getBattleHeigth() {
        return battleHeigth;
    }

    public void setBattleHeigth(int battleHeigth) {
        this.battleHeigth = battleHeigth;
    }

    public int getBattleWeidth() {
        return battleWeidth;
    }

    public void setBattleWeidth(int battleWeidth) {
        this.battleWeidth = battleWeidth;
    }
    
    
    
    
    
    
}
