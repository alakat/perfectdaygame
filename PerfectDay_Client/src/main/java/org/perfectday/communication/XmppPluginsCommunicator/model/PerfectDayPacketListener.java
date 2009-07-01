/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.communication.XmppPluginsCommunicator.model;

import com.thoughtworks.xstream.XStream;
import org.apache.log4j.Logger;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Packet;
import org.perfectday.communication.masterCommunication.MasterCommunication;
import org.perfectday.communication.model.plugcommunication.PerfectDayMessage;
import org.perfectday.dashboard.threads.DashBoardThreadGroup;
import org.perfectday.logicengine.core.Game;
import org.perfectday.threads.commands.kernell.MessageReceiveCommand;

/**
 * Dummy class se usa MessageChatListener
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class PerfectDayPacketListener implements PacketListener,MessageListener{

    private static final Logger logger =
            Logger.getLogger(PerfectDayPacketListener.class);
    
    public PerfectDayPacketListener() {
    }

    @Override
    public void processPacket(Packet packet) {
        if(packet instanceof org.jivesoftware.smack.packet.Message){
            logger.info("recivido mensage:"+packet.getPacketID());
            org.jivesoftware.smack.packet.Message message =
                    (org.jivesoftware.smack.packet.Message)packet;
            logger.info("Se revice info:"+message.getBody());
            logger.info("Se revice Property pd:"+message.getProperty("PD"));
            logger.info("From: "+message.getFrom());
            logger.info("To: "+message.getTo());
            PerfectDayMessage pdMessage = (PerfectDayMessage) new XStream().fromXML((String)message.getProperty(MasterCommunication.NAME_GAME_MESSAGE));
            DashBoardThreadGroup.sendEventToKernell(new MessageReceiveCommand(pdMessage));
        }
    }

    @Override
    public void processMessage(Chat arg0, org.jivesoftware.smack.packet.Message message) {

    }

    
}
