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
import org.perfectday.gamebuilder.model.ArmiesAndBattleFieldWrapper;
import org.perfectday.gamebuilder.model.DashBoardMiniUtilities;
import org.perfectday.gamebuilder.model.MiniDescription;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.core.industry.MiniFactory;
import org.perfectday.logicengine.core.player.Player;
import org.perfectday.logicengine.model.battelfield.BattelField;
import org.perfectday.logicengine.model.battelfield.generator.SimpleMapGenerator;
import org.perfectday.main.laboratocGUI.LaboratoryGUI;

/**
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class GameBuilderServer extends GameBuilder{
    
    private static final Logger logger = Logger.getLogger(GameBuilderServer.class);
    public GameBuilderServer(GameBuilderCommunicator com) throws NoSuchMethodException {
        super(new ArrayList<State>(), null, null, com);
        this.setServer(true);
        State initServer = new State("Init Server");
        State aceptedWaiting = new State("Wait Client Acepted");
        State receiveAcept = new State("Receive Client Acepted");
        State okArmy = new State("Ok Armies");
        State sendMap = new State("Send Map");
        State gameBuilding = new State("Game Building");
        State okWaiting = new State("ok waiting!!");
        State go = new State("Go!!!");
        
        
        initServer.getMovements().add(aceptedWaiting);
        aceptedWaiting.getMovements().add(receiveAcept);
        receiveAcept.getMovements().add(okArmy);
        okArmy.getMovements().add(sendMap);
        sendMap.getMovements().add(gameBuilding);
        gameBuilding.getMovements().add(okWaiting);
        okWaiting.getMovements().add(go);
        
        this.setActual(initServer);
        this.setInitial(initServer);
        this.setEnd(go);
        
        this.getStates().add(initServer);
        this.getStates().add(aceptedWaiting);
        this.getStates().add(receiveAcept);
        this.getStates().add(okArmy);
    
        this.getStates().add(sendMap);
        this.getStates().add(gameBuilding);
        this.getStates().add(okWaiting);
        this.getStates().add(go);
        
        Transaction initServer2AceptedWaiting = new Transaction(GameBuilderServer.class.getMethod("sendBattlePetition"), this);
        Transaction aceptedWaiting2ReceiveAcept = new Transaction(GameBuilderServer.class.getMethod("receivedAcepted"), this);
        Transaction receiveAcept2OkArmies  = new Transaction(GameBuilderServer.class.getMethod("receiveClientArmy"), this);
        Transaction okArmies2SendingMap = new Transaction(GameBuilderServer.class.getMethod("sendingMap"),this);
        Transaction sendingMap2GameBuilding = new Transaction(GameBuilderServer.class.getMethod("generateGame"), this);
        Transaction gameBuilding2OkWaiting = new Transaction(GameBuilderServer.class.getMethod("sendGame"), this);
        Transaction okWaiting2Go = new Transaction(GameBuilderServer.class.getMethod("receivedOk"),this);
        
        initServer.getTransactions().add(initServer2AceptedWaiting);
        aceptedWaiting.getTransactions().add(aceptedWaiting2ReceiveAcept);
        receiveAcept.getTransactions().add(receiveAcept2OkArmies);
        okArmy.getTransactions().add(okArmies2SendingMap);
        sendMap.getTransactions().add(sendingMap2GameBuilding);
        gameBuilding.getTransactions().add(gameBuilding2OkWaiting);
        okWaiting.getTransactions().add(okWaiting2Go);
    }
    
    public void sendBattlePetition(){
        try {
            for (String name : MiniFactory.getInstance().getIndex()) {
                double cost = MiniFactory.getInstance().getMiniCost(name);
                String description = "none"; //TODO get mini description;
                this.getBattleDescription().getMinis().add(new MiniDescription(name, cost, description));
            }
            PerfectDayMessage pdm = PerfectDayMessageFactory.getInstance().createInitialBattleMessage(this.getBattleDescription());
            this.getCommunication().sendMessage(pdm);
            JOptionPane.showMessageDialog(null, "Perición de batalla enviada", "Información", JOptionPane.INFORMATION_MESSAGE);
        } catch (GameBuilderException ex) {
            logger.error("Error interno",ex);
            JOptionPane.showMessageDialog(null,"Error","Error interno. Porfavor envie el bug a ....", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    public void receivedAcepted(){
        try{
        CreateArmyDialog armyDialog = new CreateArmyDialog(null, true, this.getBattleDescription());
        armyDialog.setVisible(true);
        this.setServerArmy(armyDialog.getArmyList());
        armiesOkTest();
       } catch (IllegalAccessException ex) {
            logger.error("Ilegal acceso",ex);
            JOptionPane.showMessageDialog(null,"Error interno. Porfavor envie el bug a ....","Error", JOptionPane.ERROR_MESSAGE);
        } catch (InvocationTargetException ex) {
            logger.error("Ilegal invocacion",ex);
            JOptionPane.showMessageDialog(null,"Error interno. Porfavor envie el bug a ....","Error", JOptionPane.ERROR_MESSAGE);
        } 
    }
    
    public void receiveClientArmy(){
        try{
            move();
        } catch (IllegalAccessException ex) {
            logger.error("Ilegal acceso",ex);
            JOptionPane.showMessageDialog(null,"Error interno. Porfavor envie el bug a ....","Error", JOptionPane.ERROR_MESSAGE);
        } catch (InvocationTargetException ex) {
            logger.error("Ilegal invocacion",ex);
            JOptionPane.showMessageDialog(null,"Error interno. Porfavor envie el bug a ....","Error", JOptionPane.ERROR_MESSAGE);
        } 
    }
    
    public void sendingMap(){
        try {
            logger.info("Generando Mapa.....");
            this.battlefield = new BattelField(this.getBattleDescription().getBattleWeidth(), this.getBattleDescription().getBattleHeigth());
            SimpleMapGenerator simpleMapGenerator = new SimpleMapGenerator(this.battlefield.getHigth(), this.battlefield.getWeidth());
                simpleMapGenerator.generateBattelField();
            this.battlefield.generateBattelField(simpleMapGenerator);  
            logger.info("Mapa generado!");
            logger.info("Generando ejercito cliente.....");
            for (MiniDescription miniDescription : this.getClienteArmy()) {
                this.getTrueClientArmy().add(DashBoardMiniUtilities.buildMini(miniDescription));
            }
            logger.info("Ejercito cliente generado!");
            logger.info("Generando ejercito del servidor....");
            for (MiniDescription miniDescription : this.getServerArmy()) {
                this.getTrueServerArmy().add(DashBoardMiniUtilities.buildMini(miniDescription));
            }
            logger.info("Ejercito del servidor generado!");
            ArmiesAndBattleFieldWrapper wrapper = new ArmiesAndBattleFieldWrapper();
            wrapper.setArmyClient(this.getTrueClientArmy());
            wrapper.setArmyServer(this.getTrueServerArmy());
            wrapper.setBattelFieldl(battlefield);
            logger.info("Envio de datos!");
            PerfectDayMessage pdm = PerfectDayMessageFactory.getInstance().createArmyAndBattleFieldMessage(wrapper);
            this.getCommunication().sendMessage(pdm);
            DashBoardMiniUtilities.showDeployDialog(this);
            
       }  catch (GameBuilderException ex) {
            logger.error("Error interno",ex);
            JOptionPane.showMessageDialog(null,"Error","Error interno en comunicaciones. Porfavor envie el bug a ....", JOptionPane.ERROR_MESSAGE);
            
        }
        
    }
    
    public void generateGame(){
        try {
            logger.info("Generate Game...");
            this.initGame();
            move();
       } catch (IllegalAccessException ex) {
            logger.error("Ilegal acceso",ex);
            JOptionPane.showMessageDialog(null,"Error interno. Porfavor envie el bug a ....","Error", JOptionPane.ERROR_MESSAGE);
        } catch (InvocationTargetException ex) {
            logger.error("Ilegal invocacion",ex);
            JOptionPane.showMessageDialog(null,"Error interno. Porfavor envie el bug a ....","Error", JOptionPane.ERROR_MESSAGE);
        } 
    }
    
    public void sendGame(){
         try {
            logger.info("Sending Game....");
            PerfectDayMessage pdm = PerfectDayMessageFactory.getInstance().createGameMessage(this.battlefield);
                this.getCommunication().sendMessage(pdm);
        } catch (GameBuilderException ex) {
            logger.error("Error interno",ex);
            JOptionPane.showMessageDialog(null,"Error","Error interno. Porfavor envie el bug a ....", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    public void receivedOk(){
        logger.info("Recivimos ok!!");     
        readArmies();
        Game.initializedActivationState();
        Game.getInstance().initGUI();
        Game.getInstance().runEventManager();
        //TODO eliminar cuando se pase a 3D
        LaboratoryGUI.me.getActivationStackPanel().setAccident(Game.getInstance().getActivationStack().getStack());
        logger.info( "Activation Stack{"+ Game.getInstance().getActivationStack().toString()+"}");
        
        int eventosApilados = 0;
        for (Player player : Game.getInstance().getPlayers()) {
            eventosApilados += player.getBand().size();
        }
        logger.info("Esperamos al apilamiento de los eventos");
        while (Game.getInstance().getActivationStack().getStack().size()!=eventosApilados){
            //waiting loop
        }
        logger.info("Activaciones apiladas");
        Game.getInstance().nextAccident();
        
//        //Todo Gestor de hebras
//        Thread t = new Thread(new Runnable() {
//            public void run() {
//                try {
//                    Game.getInstance().nextAccident();
//                } catch (Exception ex) {
//                   Logger.getLogger("HEBRA DE JUEGO").error("Error en loop del juego",ex);
//                }
//            }
//        });
//        t.start();
        
        
        
    }

    public void armiesOkTest() throws IllegalAccessException, InvocationTargetException {
        if(this.getClienteArmy()!=null &&
                this.getServerArmy()!=null)
            this.move();
    }
    
    

}
