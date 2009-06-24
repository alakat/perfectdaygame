/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.communication.masterCommunication;

import com.thoughtworks.xstream.XStream;
import org.apache.log4j.Logger;
import org.perfectday.communication.XmppPluginsCommunicator.XMPPPluginsCommunicator;
import org.perfectday.communication.model.plugcommunication.PerfectDayMessage;
import org.perfectday.communication.model.plugcommunication.PlugCommunication;
import org.perfectday.logicengine.core.event.Event;
import org.perfectday.logicengine.core.event.manager.EventManager;



/**
 *
 * @author Lobo <inmortalland83@gmail.com>
 */
public class MasterCommunication{
    public static final String NAME_GAME_MESSAGE = "game-message";
    private PlugCommunication plugCom;
    private static MasterCommunication masterCom;
    private String URL;
    private EventManager eventManager;

    private static final Logger logger = Logger.getLogger(MasterCommunication.class);
   

    public String getURL() {
        return URL;
    }

    

    public void setCommunication(String user, String pass, String dest) {
         this.plugCom = new XMPPPluginsCommunicator(user,pass, dest);  
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

   
    //IMPLEMENTING PATTERN SINGLETON
    private MasterCommunication(){ }
   
    public void setCommunication(String name, String destiny) {
        this.plugCom = new XMPPPluginsCommunicator(name, destiny);        
    }
    
    
    
    public void unSetCommunication(){
        plugCom.disconnect();
    }
    public static MasterCommunication getInstance(){
        if(masterCom == null){
            masterCom = new MasterCommunication();
        }
        return masterCom;
    }

    public PlugCommunication getPlugCom() {
        return plugCom;
    }

    public void setPlugCom(PlugCommunication plugCom) {
        this.plugCom = plugCom;
        
    }
    
    /**
     * Enviamos un evento al servidor mediante el PlugComunication.
     * @param event
     */
    public void sendEvent(Event event) {
        String message = new XStream().toXML(event);
        String clazz  = event.getClass().getName();
        PerfectDayMessage pdm = new PerfectDayMessage();
        pdm.setClazz(event.getClass());
        pdm.setMessage(message);
        pdm.setType(99); //dummy type        
        this.getPlugCom().sendMessage(pdm);
    }
    
    /**
     * Transforma el mensaje de entrada en un evento y lo apila dentro de los 
     * eventos de EventManager.
     * @param message
     */
    public void receiveMessage(PerfectDayMessage message){
         Event event = (Event)new XStream().fromXML(message.getMessage());
         logger.info("El evento es de tipo:"+event.getClass().getSimpleName());
         //Apilamos el evento
         EventManager.getInstance().addEvent(event);
         //Avisamos a la hebra que vuelva a comprobar los eventos.
         EventManager.getInstance().eventWaitTest();
    }

}
