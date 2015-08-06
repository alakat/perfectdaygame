/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.perfectday.communication.oneplayer;

import com.thoughtworks.xstream.XStream;
import org.apache.log4j.Logger;
import org.perfectday.communication.model.plugcommunication.PerfectDayMessage;
import org.perfectday.communication.model.plugcommunication.PlugCommunication;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.core.event.Event;
import org.perfectday.logicengine.core.event.accident.PutAccidentEvent;
import org.perfectday.logicengine.core.event.game.PutActionEvent;
import org.perfectday.message.model.Message;

/**
 *
 * Plugin de comunicaciones ideado para simular las comunicaciones en partidas 
 * un jugador
 * @author Miguel (alakat@gmail.com)
 */
public class OnePlayerPlugCommunication extends PlugCommunication{

    private static final Logger logger =
            Logger.getLogger(OnePlayerPlugCommunication.class);
    
    @Override
    public void exposeService() {
        //Nada por hacer
    }

    @Override
    public void connect(String destiny, String typeDestiny) {
        //nada por hacer
    }

    @Override
    public void disconnect() {
        //nada por hacer
    }

    @Override
    public void sendMessage(Object message) {
        try{
        ((PerfectDayMessage)message).setSendtime(System.currentTimeMillis());
        Event event = (Event)new XStream().fromXML( ((PerfectDayMessage)message).getMessage());
        logger.debug("EVENTO GESTIONADO: "+event);
        if(event instanceof Event ){
            logger.debug("EVENTO DESCARTADO");
            return;
        }
        logger.debug("EVENTO ACEPTADO");
        Game.getGame().getMasterCommunication().receiveMessage((PerfectDayMessage) message);
        }catch(ClassCastException ex){
            logger.error("SE intenta enviar algo que no es un mensaje");
        }
    }

    @Override
    public void startUp() {
        //nada por hacer
    }

    @Override
    public Message receiveMessage() {
        //nada por hacer
        return null;
    }
    
}
