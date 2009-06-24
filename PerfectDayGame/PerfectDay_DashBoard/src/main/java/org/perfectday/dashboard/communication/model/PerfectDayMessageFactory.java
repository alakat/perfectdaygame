/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.dashboard.communication.model;

import org.perfectday.communication.model.plugcommunication.PerfectDayMessage;
import com.thoughtworks.xstream.XStream;
import java.util.List;
import org.perfectday.gamebuilder.model.ArmiesAndBattleFieldWrapper;
import org.perfectday.gamebuilder.model.BattleDescription;
import org.perfectday.gamebuilder.model.MiniDescription;
import org.perfectday.logicengine.model.battelfield.BattelField;

/**
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class PerfectDayMessageFactory {
    public static final int DENY_BATTLE_CODE = -1;
    public static final int INITIAL_BATTLE_CONFIGURATION_CODE = 1;
    public static final int ACCEPT_BATTLE_CODE=2;
    public static final int ARMY_DESCRIPTION = 3;
    public static final int ARMY_AND_BATTLEFIELD_CODE = 4;
    public static final int DEPLOY_CODE=5;
    public static final int BATTLE_FIELD_FINISDHED=6;
    public static final int GAME_GO=7;
    private static final PerfectDayMessageFactory instance = new PerfectDayMessageFactory();

    public static PerfectDayMessageFactory getInstance() {
        return instance;
    }
    
    
    

    public PerfectDayMessage createAceptMessageResponse() {
        PerfectDayMessage pdm = new PerfectDayMessage();
        pdm.setType(ACCEPT_BATTLE_CODE);
        return pdm;
    }

    public PerfectDayMessage createArmyAndBattleFieldMessage(ArmiesAndBattleFieldWrapper wrapper) {
        PerfectDayMessage pdm = new PerfectDayMessage();
        pdm.setType(ARMY_AND_BATTLEFIELD_CODE);
        pdm.setClazz(wrapper.getClass());
        pdm.setMessage(new XStream().toXML(wrapper));
        return pdm;
        
    }

    public PerfectDayMessage createArmyMessage(List<MiniDescription> clienteArmy) {
        PerfectDayMessage pdm = new PerfectDayMessage();
        pdm.setType(ARMY_DESCRIPTION);
        pdm.setClazz(clienteArmy.getClass());
        pdm.setMessage(new XStream().toXML(clienteArmy));
        return pdm;
    }

    
    public PerfectDayMessage createDenyBattle(){
        PerfectDayMessage pdm = new PerfectDayMessage();
        pdm.setType(DENY_BATTLE_CODE);
        return pdm;
    }

    public PerfectDayMessage createDeployMessage(BattelField battlefield) {
        PerfectDayMessage pdm = new PerfectDayMessage();
        pdm.setType(DEPLOY_CODE);
        pdm.setClazz(battlefield.getClass());
        pdm.setMessage(new XStream().toXML(battlefield));
        return pdm;
    }

    public PerfectDayMessage createGameGo() {
        PerfectDayMessage pdm = new PerfectDayMessage();
        pdm.setType(GAME_GO);
        return pdm;
    }

    public PerfectDayMessage createGameMessage(BattelField battlefield) {
        PerfectDayMessage pdm = new PerfectDayMessage();
        pdm.setType(BATTLE_FIELD_FINISDHED);
        pdm.setClazz(battlefield.getClass());
        pdm.setMessage(new XStream().toXML(battlefield));
        return pdm;
    }

    public PerfectDayMessage createInitialBattleMessage(BattleDescription battleDescription) {
        PerfectDayMessage pdm = new PerfectDayMessage();
        pdm.setType(INITIAL_BATTLE_CONFIGURATION_CODE);
        pdm.setClazz(BattleDescription.class);
        pdm.setMessage(new XStream().toXML(battleDescription));
        return pdm;
    }

   
}
