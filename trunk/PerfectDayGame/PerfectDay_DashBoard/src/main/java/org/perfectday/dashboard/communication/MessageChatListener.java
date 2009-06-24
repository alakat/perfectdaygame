/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.dashboard.communication;

import com.thoughtworks.xstream.XStream;
import org.apache.log4j.Logger;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;
import org.perfectday.communication.masterCommunication.MasterCommunication;
import org.perfectday.communication.model.plugcommunication.PerfectDayMessage;
import org.perfectday.dashboard.gui.ChatPanel;
import org.perfectday.dashboard.gui.WarRoom;

/**
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class MessageChatListener implements MessageListener {

    private WarRoom warRoom;
    private ChatPanel chatPannel;

    public MessageChatListener(WarRoom dashBoard) {
        this.warRoom = dashBoard;
    }
    
    public MessageChatListener(WarRoom dashBoard,ChatPanel cp) {
        this.warRoom = dashBoard;
        this.chatPannel = cp;
    }
    
    public void processMessage(Chat chat, Message mess) {
        if(mess.getType() == Message.Type.chat){
            String from = mess.getFrom();            
            from = from.substring(0, from.indexOf("/"));
            if(mess.getProperty(GameBuilderCommunicator.NAME_CONFIGURATION_GAME_ATTRIBUTE)!=null){                
                if(GameBuilderGateWay.getInstance().getGameInConstruction().containsKey(from)){
                    GameBuilderGateWay.getInstance().getGameInConstruction().get(from).
                            receiveMessage((String) mess.getProperty(
                            GameBuilderCommunicator.NAME_CONFIGURATION_GAME_ATTRIBUTE));
                }else{
                    //TODO: SI nos solicitan partida!!! (el cliente) la primera vez
                    warRoom.newBattelPetition(from,(String) mess.getProperty(
                            GameBuilderCommunicator.NAME_CONFIGURATION_GAME_ATTRIBUTE));
                }
            }else if(mess.getProperty(MasterCommunication.NAME_GAME_MESSAGE)!=null){
                Logger.getLogger(MessageChatListener.class).info("Se recibe mensaje mensaje de juego");
                PerfectDayMessage parser= (PerfectDayMessage)new XStream().fromXML((String)mess.getProperty(MasterCommunication.NAME_GAME_MESSAGE));
                long time = System.currentTimeMillis();
                Logger.getLogger(MessageChatListener.class).info("Tiempo de envio de un mensaje[] de juego:("+(time-parser.getSendtime())+")");
                MasterCommunication.getInstance().receiveMessage(parser);
                
            }else{
                warRoom.incommingMessage(from,mess.getBody());
            }
            
        }else
            Logger.getLogger(MessageChatListener.class).warn(mess.getFrom()+":"+mess.getBody());
    }

    public ChatPanel getChatPannel() {
        return chatPannel;
    }

    public void setChatPannel(ChatPanel chatPannel) {
        this.chatPannel = chatPannel;
    }

    public WarRoom getWarRoom() {
        return warRoom;
    }

    public void setWarRoom(WarRoom warRoom) {
        this.warRoom = warRoom;
    }
    

}
