/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.perfectday.gamebuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import org.perfectday.communication.XmppPluginsCommunicator.XMPPPluginsCommunicator;
import org.perfectday.communication.oneplayer.OnePlayerPlugCommunication;
import org.perfectday.core.asf.State;
import org.perfectday.core.asf.Transaction;
import org.perfectday.core.threads.GameMode;
import org.perfectday.core.threads.KernellThreadGroup;
import org.perfectday.dashboard.communication.oneplayer.GameBuilderCommunicatorOnePlayer;
import org.perfectday.dashboard.exception.GameBuilderException;
import org.perfectday.dashboard.gui.CreateArmyDialog;
import org.perfectday.dashboard.threads.DashBoardThreadGroup;
import org.perfectday.gamebuilder.model.DashBoardMiniUtilities;
import org.perfectday.gamebuilder.model.MiniDescription;
import org.perfectday.logicengine.core.industry.MiniFactory;
import org.perfectday.logicengine.missions.SearchAndDestroyMission;
import org.perfectday.logicengine.model.battelfield.BattelField;
import org.perfectday.logicengine.model.battelfield.generator.simple.SimpleMapGenerator;
import org.perfectday.main.dummyengine.threads.GraphicsEngineThreadGroup;

/**
 *
 * @author Miguel (alakat@gmail.com)
 */
public class GameBuilderOnePlayer extends GameBuilder{

    private static final Logger logger =
            Logger.getLogger(GameBuilderOnePlayer.class);
    public GameBuilderOnePlayer() throws NoSuchMethodException {
        super(new ArrayList<State>(), 
                null,
                null,
                new GameBuilderCommunicatorOnePlayer());
        State initServer = new State("Init Server");
        State configPlayerArmy = new State("set army player");
        State autoConfigAIArmy = new State("set AI army");
        State generateMap = new State("generate map");
        State go = new State("Go!!!");
        
        
        initServer.getMovements().add(configPlayerArmy);
        configPlayerArmy.getMovements().add(autoConfigAIArmy);
        autoConfigAIArmy.getMovements().add(generateMap);
        generateMap.getMovements().add(go);
        
        this.setInitial(initServer);
        this.setActual(initServer);
        this.setEnd(go);
        
        this.getStates().add(initServer);
        this.getStates().add(configPlayerArmy);
        this.getStates().add(autoConfigAIArmy);
        this.getStates().add(generateMap);
        this.getStates().add(go);
        
 
        Transaction init2configPlayerArmy     
                = new Transaction(GameBuilderOnePlayer.class.getMethod("configPlayerArmy"), this);
        Transaction configPlayer2configAIArmy = new Transaction(
                GameBuilderOnePlayer.class.getMethod("configAIArmy"), this);
        Transaction configAIArmy2generateMap = new Transaction(
                GameBuilderOnePlayer.class.getMethod("generateMap"), this);
        Transaction generateMap2go = new Transaction(
                GameBuilderOnePlayer.class.getMethod("starGame"), this);
        
        initServer.getTransactions().add(init2configPlayerArmy);
        configPlayerArmy.getTransactions().add(configPlayer2configAIArmy);
        autoConfigAIArmy.getTransactions().add(configAIArmy2generateMap);
        generateMap.getTransactions().add(generateMap2go);
       
        
        this.setClienteArmy(new ArrayList<MiniDescription>());
        this.setServerArmy(new ArrayList<MiniDescription>());
    }
    /**
     * Ordena la visualizaci?n del dialogo de construcci?n del 
     * ejercitor del jugaro
     */
    public void configPlayerArmy(){
        
        try {
            
            for (String name : MiniFactory.getInstance().getIndex()) {
                double cost = MiniFactory.getInstance().getMiniCost(name);
                String description = "none"; //TODO get mini description;
                this.getBattleDescription().getMinis().add(new MiniDescription(name, cost, description));
            }
            
            logger.info("arrancamos el di?logo de ejercitos");
            CreateArmyDialog armyDialog = new CreateArmyDialog(null,
                    true, 
                    this.getBattleDescription());
            armyDialog.setVisible(true);
            this.setServerArmy(armyDialog.getArmyList());
            this.move();
        } catch (IllegalAccessException ex) {
            logger.error(ex);
        } catch (InvocationTargetException ex) {
            logger.error(ex.getCause(),ex);
        }
        
    }
    /**
     * Auto genera un ejercito para la m?quina
     */
    public void configAIArmy() throws IllegalAccessException, InvocationTargetException{
        
        //TODO : HAcer algo m?s mol?n
        this.getClienteArmy().add(new MiniDescription("Soldier", 10, null));
        this.getClienteArmy().add(new MiniDescription("Soldier", 10, null));
        this.getClienteArmy().add(new MiniDescription("Soldier", 10, null));
        this.move();
    }
    
    /**
     * Genera un mapa para la partida
     */
    public void generateMap() throws IllegalAccessException, InvocationTargetException{
        try{
            logger.info("Generando Mapa.....");
            this.battlefield = new BattelField(
                    this.getBattleDescription().getBattleWeidth(), 
                    this.getBattleDescription().getBattleHeigth());
            SimpleMapGenerator simpleMapGenerator =
                    new SimpleMapGenerator(
                            this.battlefield.getHigth(),
                            this.battlefield.getWeidth(),
                            this.battlefield);
                simpleMapGenerator.generateBattelField();
            this.battlefield.generateBattelField(simpleMapGenerator);  
            logger.info("Mapa generado!");
            logger.info("Generando ejercito cliente.....");
            for (MiniDescription miniDescription : this.getClienteArmy()) {
                this.getTrueClientArmy().add(
                        DashBoardMiniUtilities.buildMini(miniDescription));
            }
            logger.info("Ejercito cliente generado!");
            logger.info("Generando ejercito del servidor....");
            for (MiniDescription miniDescription : this.getServerArmy()) {
                this.getTrueServerArmy().add(
                        DashBoardMiniUtilities.buildMini(miniDescription));
            }
            logger.info("Ejercito del servidor generado!");
            DashBoardMiniUtilities.showDeployDialog(this);
        
            this.move();
        }catch(GameBuilderException ex){
            logger.error("Error interno",ex);
            JOptionPane.showMessageDialog(null,"Error",
                "Error interno en comunicaciones. Porfavor envie el bug a ....", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    /**
     * Arranca el juego
     */
    public void starGame(){
        
        logger.info("Configuramos el juego");
        
        this.initGame();
        this.endConstructionGame();
        if (Thread.currentThread().getThreadGroup() instanceof DashBoardThreadGroup) {
            DashBoardThreadGroup dashBoardThreadGroup =
                    (DashBoardThreadGroup) Thread.currentThread().getThreadGroup();
            GraphicsEngineThreadGroup graphicsEngineThreadGroup =
                    GraphicsEngineThreadGroup.buildGraphicsEngineThreadGroup();
            dashBoardThreadGroup.setGraphicsInRun(graphicsEngineThreadGroup);
            graphicsEngineThreadGroup.getDummyGraphicsEngine().getActivationStackPanel().
                        setAccident(dashBoardThreadGroup.getKernellInRun().
                        getGame().getActivationStack().getStack());
            if(!dashBoardThreadGroup.gameGo()){
                logger.fatal("La partida no se ejecuto. No todos los atributos en DashBoardThreadGruop están listos");
            }
        }else{
            logger.fatal("Una hebra que no pertenece a DashBoardThreadGroup " +
                    "ejecuto este códifo ["+Thread.currentThread().getName()+
                    ","+Thread.currentThread().getThreadGroup().getName()+"]");
        }
    }

    @Override
    public void initGame() {
        
        KernellThreadGroup kernellThreadGroup =
                KernellThreadGroup.buildKernellThreadGroup(GameMode.OnePlayer,
                    getTrueServerArmy(),
                    getTrueClientArmy(),
                    battlefield,
                    new SearchAndDestroyMission((null)),
                    new OnePlayerPlugCommunication());

        if (Thread.currentThread().getThreadGroup() instanceof DashBoardThreadGroup) {
            DashBoardThreadGroup dashBoardThreadGroup = (DashBoardThreadGroup) Thread.currentThread().getThreadGroup();
            dashBoardThreadGroup.setKernellInRun(kernellThreadGroup);
        }else{
            logger.fatal("Una Hebra que no pertene a DashBoardThreadGroup " +
                    "ejecuto este metodo ["+Thread.currentThread().getName()+
                    ","+Thread.currentThread().getThreadGroup().getName()+"]");
        }
    }
    
    
    
}
