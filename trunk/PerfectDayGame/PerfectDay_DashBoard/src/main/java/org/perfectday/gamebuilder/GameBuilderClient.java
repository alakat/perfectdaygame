/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.gamebuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import org.perfectday.core.asf.State;
import org.perfectday.core.asf.Transaction;
import org.perfectday.dashboard.communication.GameBuilderCommunicator;
import org.perfectday.communication.model.plugcommunication.PerfectDayMessage;
import org.perfectday.dashboard.communication.model.PerfectDayMessageFactory;
import org.perfectday.dashboard.exception.GameBuilderException;
import org.perfectday.dashboard.gui.CreateArmyDialog;
import org.perfectday.gamebuilder.model.DashBoardMiniUtilities;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.main.laboratocGUI.LaboratoryGUI;

/**
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class GameBuilderClient extends GameBuilder {
    
    private static final Logger logger = Logger.getLogger(GameBuilderClient.class);
    

    public GameBuilderClient(GameBuilderCommunicator com) throws NoSuchMethodException {        
        super(new ArrayList<State>(), null, null, com);
        this.setServer(false);
        State ini = new State("Ini");
        State iniClient = new State("Init client");
        State createArmy = new State("Create army");
        State mapWaiting = new State("Waiting Mapa");        
        State mapReceived = new State("Map received");
        State gameWaiting = new State("Waiting a Game");
        State allOk = new State("All ok");
        State go = new State("Go!!!");
        
        ini.getMovements().add(iniClient);
        iniClient.getMovements().add(createArmy);
        createArmy.getMovements().add(mapWaiting);
        mapWaiting.getMovements().add(mapReceived);
        mapReceived.getMovements().add(gameWaiting);
        gameWaiting.getMovements().add(allOk);
        allOk.getMovements().add(go);
        
        this.getStates().add(ini);
        this.getStates().add(iniClient);
        this.getStates().add(createArmy);
        this.getStates().add(mapWaiting);
        this.getStates().add(mapReceived);
        this.getStates().add(gameWaiting);
        this.getStates().add(allOk);
        
        this.setActual(ini);
        this.setInitial(ini);
        this.setEnd(go);
        
        
        
        Transaction ini2iniClient= new Transaction(GameBuilderClient.class.getMethod("receiveBattlePetition"),this);              
        Transaction initClient2createArmy = new Transaction(GameBuilderClient.class.getMethod("sendResponseBattlePettionYes"), this);
        Transaction createArmy2mapWaiting = new Transaction(GameBuilderClient.class.getMethod("sendArmy"),this);
        Transaction mapWaiting2mapRecive = new Transaction(GameBuilderClient.class.getMethod("receiveMap"),this);
        Transaction mapReceived2GameWaiting = new Transaction(GameBuilderClient.class.getDeclaredMethod("sendArmyDeploy"),this);
        Transaction gameWaiting2AllOk = new Transaction(GameBuilderClient.class.getMethod("receiveGame"), this);
        Transaction allOk2Go = new Transaction(GameBuilderClient.class.getMethod("sendGo"),this);
        
        ini.getTransactions().add(ini2iniClient);
        iniClient.getTransactions().add(initClient2createArmy);
        createArmy.getTransactions().add(createArmy2mapWaiting);
        mapWaiting.getTransactions().add(mapWaiting2mapRecive);
        mapReceived.getTransactions().add(mapReceived2GameWaiting);
        gameWaiting.getTransactions().add(gameWaiting2AllOk);
        allOk.getTransactions().add(allOk2Go);
        
    }

   
    
    
    
    public void receiveBattlePetition(){
        logger.info("Recibimos peticón de batalla");
        try {
             String from =this.getCommunication().getChat().getParticipant();
             int opt = JOptionPane.showConfirmDialog(null,
                    "Petición de batalla",
                    from+" desea luchar contra usted, Acepta?",
                    JOptionPane.YES_NO_OPTION);
            if(opt == JOptionPane.YES_OPTION){
                    this.move();
            }else{
                 PerfectDayMessage pdm = PerfectDayMessageFactory.getInstance().createDenyBattle();
                 this.getCommunication().sendMessage(pdm);
                 GameBuilderFactory.getInstance().delete(this.getCommunication().getChat().getParticipant());
            }
        } catch (IllegalAccessException ex) {
            logger.error("Ilegal acceso",ex);
        } catch (InvocationTargetException ex) {
            logger.error("Ilegal invocacion",ex);
        } catch (GameBuilderException ex) {
            logger.error("error interno",ex);
            JOptionPane.showMessageDialog(null,"Error","Error interno. Porfavor envie el bug a ....", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    public void sendResponseBattlePettionYes(){
        try {
            logger.info("Enviamos respuesta de la petición");
            PerfectDayMessage pdm = PerfectDayMessageFactory.getInstance().createAceptMessageResponse();
            this.getCommunication().sendMessage(pdm);
            CreateArmyDialog armyDialog = new CreateArmyDialog(null, true, this.getBattleDescription());
            armyDialog.setVisible(true);
            this.setClienteArmy(armyDialog.getArmyList());
            this.move();
        } catch (IllegalAccessException ex) {
            logger.error("Ilegal acceso",ex);
        } catch (InvocationTargetException ex) {
            logger.error("Ilegal invocacion",ex);
        } catch (GameBuilderException ex) {
            logger.error("error interno",ex);
            JOptionPane.showMessageDialog(null,"Error","Error interno. Porfavor envie el bug a ....", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void sendArmy(){
        try{
            logger.info("Enviamos ejercito");
            PerfectDayMessage pdm = PerfectDayMessageFactory.getInstance().createArmyMessage(this.getClienteArmy());
            this.getCommunication().sendMessage(pdm);
            //Todo mostrar espera.....
         } catch (GameBuilderException ex) {
            logger.error("error interno",ex);
            JOptionPane.showMessageDialog(null,"Error en comunicaciones. Porfavor envie el bug a ....","Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void receiveMap(){
        try{
            logger.info("Recivimos mapa");
            DashBoardMiniUtilities.showDeployDialog(this);
            move();
        } catch (IllegalAccessException ex) {
            logger.error("Ilegal acceso",ex);
        } catch (InvocationTargetException ex) {
            logger.error("Ilegal invocacion",ex);
        }
        
    }
    
    public void sendArmyDeploy(){
        try{
                logger.info("Enviamos ejercirto");
            PerfectDayMessage pdm = PerfectDayMessageFactory.getInstance().createDeployMessage(this.getBattlefield());
            this.getCommunication().sendMessage(pdm); 
        } catch (GameBuilderException ex) {
            logger.error("error interno",ex);
            JOptionPane.showMessageDialog(null,"Error en comunicaciones. Porfavor envie el bug a ....","Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void receiveGame(){      
        try{
            logger.info("Recivimos juego (Despliege)");
            logger.info("inicializamos la partida");
            this.initGame();   
            move();
        } catch (IllegalAccessException ex) {
            logger.error("Ilegal acceso",ex);
        } catch (InvocationTargetException ex) {
            logger.error("Ilegal invocacion",ex);
        }
    }
    
    public void sendGo(){
        try {
            logger.info("Enviamos Listo");
            readArmies();
            PerfectDayMessage pdm = PerfectDayMessageFactory.getInstance().createGameGo();
            this.getCommunication().sendMessage(pdm);
            //Show game.
             Game.getInstance().initGUI();
             Game.getInstance().runEventManager();
             //TODO eliminar cuando se pase a 3D
             LaboratoryGUI.me.getActivationStackPanel().setAccident(Game.getInstance().getActivationStack().getStack());
             logger.info( "Activation Stack{"+ Game.getInstance().getActivationStack().toString()+"}");
            /*
             * .............GOOOO
             */
            //Desacer la construicción. Deshabilitar la posibilidad de crear otro juego
            
        } catch (GameBuilderException ex) {
            logger.error("error interno",ex);
            JOptionPane.showMessageDialog(null,"Error en comunicaciones. Porfavor envie el bug a ....","Error", JOptionPane.ERROR_MESSAGE);
            
        }
        //Show game.
        
    }

}