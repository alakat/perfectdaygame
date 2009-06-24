/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.gamebuilder.model;

import java.util.List;
import org.perfectday.logicengine.model.battelfield.BattelField;
import org.perfectday.logicengine.model.minis.Mini;

/**
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class ArmiesAndBattleFieldWrapper {
    
    private  List<Mini> armyClient;
    private List<Mini> armyServer;
    private BattelField battelFieldl;

    public List<Mini> getArmyClient() {
        return armyClient;
    }

    public void setArmyClient(List<Mini> armyClient) {
        this.armyClient = armyClient;
    }

    public List<Mini> getArmyServer() {
        return armyServer;
    }

    public void setArmyServer(List<Mini> armyServer) {
        this.armyServer = armyServer;
    }

    public BattelField getBattelFieldl() {
        return battelFieldl;
    }

    public void setBattelFieldl(BattelField battelFieldl) {
        this.battelFieldl = battelFieldl;
    }
    
    

}
