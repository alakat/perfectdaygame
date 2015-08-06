/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.perfectday.dashboard.communication.oneplayer;

import org.apache.log4j.Logger;
import org.perfectday.communication.model.plugcommunication.PerfectDayMessage;
import org.perfectday.dashboard.communication.GameBuilderCommunicator;
import org.perfectday.dashboard.exception.GameBuilderException;

/**
 *
 * @author Miguel (alakat@gmail.com)
 */
public class GameBuilderCommunicatorOnePlayer extends GameBuilderCommunicator{

    public Logger logger = Logger.getLogger(GameBuilderCommunicatorOnePlayer.class);    
    
    @Override
    public void sendMessage(PerfectDayMessage pdm) throws GameBuilderException {
        StackTraceElement elemnet = new Exception().getStackTrace()[0];
        logger.warn("Se intenta enviar un mensaje en onePlyer. :"
                +elemnet.getClassName()
                +"."+elemnet.getMethodName()
                +"("+elemnet.getLineNumber()+")");
    }

    @Override
    public void sendMessage(Object message, int type) throws GameBuilderException {
        StackTraceElement elemnet = new Exception().getStackTrace()[0];
        logger.warn("Se intenta enviar un mensaje en onePlyer. :"
                +elemnet.getClassName()
                +"."+elemnet.getMethodName()
                +"("+elemnet.getLineNumber()+")");
    }

    @Override
    public void receiveMessage(String message) {
        StackTraceElement elemnet = new Exception().getStackTrace()[0];
        logger.warn("Se intenta recibir un mensaje en onePlyer. :"
                +elemnet.getClassName()
                +"."+elemnet.getMethodName()
                +"("+elemnet.getLineNumber()+")");
    }
    
    
    
    
    
}
