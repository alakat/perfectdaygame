/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core.event.manager.processors;

import org.apache.log4j.Logger;
import org.perfectday.communication.masterCommunication.MasterCommunication;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.core.event.Event;
import org.perfectday.logicengine.core.event.manager.EventManager;
import org.perfectday.logicengine.core.event.movement.MovedMiniEvent;
import org.perfectday.logicengine.model.battelfield.Field;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.model.unittime.UnitTime;
import org.perfectday.logicengine.model.unittime.factories.LongUnitTimeFactory;

/**
 * Procesa los movimientos de los minis, y cuando hay que solicitar al server 
 * que mueva y cuando pintar el cambio
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class MoveMiniProcessor implements Processor{

    private Logger logger = Logger.getLogger(MoveMiniProcessor.class);
    
    @Override
    public void eventRequest(Event event) {
        logger.info("request");
        MovedMiniEvent movedMiniEvent = (MovedMiniEvent) event;
                
        if(Game.getInstance().isServer()){
            logger.info("is server");
            // procesamiento
            logger.info("Movimiento logico");
            Mini m = Game.getInstance().getMiniByReferneceObject(movedMiniEvent.getMini());
            //Desregistramos los apoyos de este mini
            if(m.getSupport()!=null)
                 Game.getInstance().getBattelField().removeSupport(m, m.getSupport());
            Field dest = Game.getInstance().getFieldByRefeferenceObject( movedMiniEvent.getDest());
            Game.getInstance().getBattelField().getField(m).setMiniOcupant(null);
            dest.setMiniOcupant(m);
            //Procesamiento de apoyos
            logger.info("aplicamos apoyos");
            if(m.getSupport()!=null)                    
                Game.getInstance().getBattelField().applySupport(m, m.getSupport());
            logger.info("Calculamos tiempo");
            UnitTime ut = LongUnitTimeFactory.getInstance().
                        doMovementAction(Game.getInstance().getSelectedMini());
            movedMiniEvent.setUtMoved(ut);
            //apilo el event
            event = event.generateEventResponse();
            //Aplia el nuevo evento RESPONSE
            EventManager.getInstance().addEvent(event);          
        }else{
            event = event.generateEventResponse();
            //Aplia el nuevo evento
            MasterCommunication.getInstance().sendEvent(event);
            
        }    
        
    }

    /**
     * En el procesamiento SOLO se pinta la interfez y se procesa los datos 
     * enviados por el server.
     * @param event
     */
    @Override
    public void eventResponse(Event event) {
       logger.info("response");
        MovedMiniEvent movedMiniEvent = (MovedMiniEvent) event;
        
        //Movemos la unidad
        Mini m = Game.getInstance().getMiniByReferneceObject(movedMiniEvent.getMini());
        if((!Game.getInstance().isServer())||(!Game.getInstance().getPlayerByMini(m).isLocal())){
            logger.info("Si no es server movimiento logico");
            /* Obtener las verdaderas referencias */
            
            Field dest = Game.getInstance().getFieldByRefeferenceObject( movedMiniEvent.getDest());
            Game.getInstance().getBattelField().getField(m).setMiniOcupant(null);
            dest.setMiniOcupant(m);
        }if(Game.getInstance().isServer()){
            //Si es el server se lo enviamos al cliente
            logger.info("Send mensaje");
            MasterCommunication.getInstance().sendEvent(event);
        }
        //Asignamos el coste
        if(Game.getInstance().getPlayerByMini(movedMiniEvent.getMini()).isLocal()){
           Game.getInstance().getTurnTime().plus(movedMiniEvent.getUtMoved()); 
        }
        
        //volvemos a pintar el mapa.
        logger.info("pintamos la interfaz");
        Game.getInstance().getPerfectDayGUI().redraw();
        //Enviamos el movimiento
    }

}
