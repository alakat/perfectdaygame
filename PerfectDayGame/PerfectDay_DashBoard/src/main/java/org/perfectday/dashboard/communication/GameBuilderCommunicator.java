/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.dashboard.communication;

import com.thoughtworks.xstream.XStream;
import java.util.List;
import org.apache.log4j.Logger;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.perfectday.communication.model.plugcommunication.PerfectDayMessage;
import org.perfectday.dashboard.communication.model.PerfectDayMessageFactory;
import org.perfectday.dashboard.exception.GameBuilderException;
import org.perfectday.gamebuilder.GameBuilder;
import org.perfectday.gamebuilder.GameBuilderServer;
import org.perfectday.gamebuilder.model.ArmiesAndBattleFieldWrapper;
import org.perfectday.gamebuilder.model.DashBoardMiniUtilities;
import org.perfectday.gamebuilder.model.MiniDescription;
import org.perfectday.logicengine.model.battelfield.BattelField;

/**
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class GameBuilderCommunicator {

    public static final String  NAME_CONFIGURATION_GAME_ATTRIBUTE="conf-perfectday-game";
    public Logger logger = Logger.getLogger(GameBuilderCommunicator.class);    
    private Chat chat;
    private GameBuilder gameBuilder;
    
    
    public void sendMessage(Object message, int type) throws GameBuilderException{
        String textMess = (new XStream()).toXML(message);
        PerfectDayMessage pdm =new PerfectDayMessage();
        pdm.setType(type);
        pdm.setMessage(textMess);
        pdm.setClazz(message.getClass());
        Message mes = new Message();
        mes.setType(Message.Type.chat);
        pdm.setSendtime(System.currentTimeMillis());
        mes.setProperty(NAME_CONFIGURATION_GAME_ATTRIBUTE,
                new XStream().toXML(pdm));
        try {
            chat.sendMessage(mes);
        } catch (XMPPException ex) {
            logger.error("Error en envio!!!",ex);
            throw new  GameBuilderException();
        }
    }
    
    public void sendMessage(PerfectDayMessage pdm) throws GameBuilderException{
        Message mes = new Message();
        pdm.setSendtime(System.currentTimeMillis());
        mes.setType(Message.Type.chat);
        mes.setProperty(NAME_CONFIGURATION_GAME_ATTRIBUTE,
                new XStream().toXML(pdm));
         try {
             logger.info(mes.getType()+":"+mes);
            chat.sendMessage(mes);
        } catch (XMPPException ex) {
            logger.error("Error en envio!!!",ex);
            throw new  GameBuilderException();
        }
    }
    
    public void receiveMessage(String message){
        try{
        PerfectDayMessage pdm = 
                (PerfectDayMessage) new XStream().fromXML(message);
        long t = System.currentTimeMillis();
        logger.info("Tiempo de env√≠o del nuevo mensaje:"+(t-pdm.getSendtime()));
        switch(pdm.getType()){
            case PerfectDayMessageFactory.ACCEPT_BATTLE_CODE:
                this.gameBuilder.move();
                break;
            case PerfectDayMessageFactory.ARMY_DESCRIPTION:
                if (this.gameBuilder instanceof GameBuilderServer){
                    ((GameBuilderServer)this.gameBuilder).setClienteArmy((List<MiniDescription>) new XStream().fromXML(pdm.getMessage()));
                    ((GameBuilderServer)this.gameBuilder).armiesOkTest();
                }
                break;
            case PerfectDayMessageFactory.DENY_BATTLE_CODE:
                break;
            case PerfectDayMessageFactory.INITIAL_BATTLE_CONFIGURATION_CODE:
                break;
            case PerfectDayMessageFactory.ARMY_AND_BATTLEFIELD_CODE:
                ArmiesAndBattleFieldWrapper wrapper = (ArmiesAndBattleFieldWrapper) (new XStream()).fromXML(pdm.getMessage());
                this.gameBuilder.setBattlefield(wrapper.getBattelFieldl());
                this.gameBuilder.setTrueClientArmy(wrapper.getArmyClient());
                this.gameBuilder.setTrueServerArmy(wrapper.getArmyServer());
                this.gameBuilder.move();
                break;
            case PerfectDayMessageFactory.DEPLOY_CODE:
                BattelField receivedBattleField = (BattelField) (new XStream()).fromXML(pdm.getMessage());
                DashBoardMiniUtilities.sinchronizedBattleField(this.gameBuilder.getBattlefield(),receivedBattleField,this.gameBuilder.getTrueClientArmy());
                receivedBattleField = null;
                this.gameBuilder.move();
                break;
            case PerfectDayMessageFactory.BATTLE_FIELD_FINISDHED:
                BattelField receivedBattleField_ = (BattelField) (new XStream()).fromXML(pdm.getMessage());
                this.gameBuilder.setBattlefield(receivedBattleField_);
                this.gameBuilder.move();
                break;
                
            case PerfectDayMessageFactory.GAME_GO:
                logger.info("La partida comienza!!!!");
                this.gameBuilder.move();    
            default:
                logger.warn("CÛdifo de perfectdaymessage no reconocido:"+ pdm.getType()+"{"+pdm.getMessage()+"}");
        }
        }catch(Exception ex){
            logger.error("Error en logica de comunicaciones",ex);            
        }
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public GameBuilder getGameBuilder() {
        return gameBuilder;
    }

    public void setGameBuilder(GameBuilder gameBuilder) {
        this.gameBuilder = gameBuilder;
    }
    
}
