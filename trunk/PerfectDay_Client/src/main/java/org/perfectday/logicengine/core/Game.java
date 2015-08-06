/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core;


import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.perfectday.communication.masterCommunication.MasterCommunication;
import org.perfectday.core.threads.KernellThreadGroup;
import org.perfectday.logicengine.combat.InstanceCombat;
import org.perfectday.logicengine.combat.MasterOfCombatImpl;
import org.perfectday.logicengine.core.event.Event.EventType;
import org.perfectday.logicengine.core.event.game.PutActionEvent;
import org.perfectday.logicengine.core.event.manager.EventManager;
import org.perfectday.logicengine.core.event.manager.EventManagerRunnable;
import org.perfectday.logicengine.core.player.Player;
import org.perfectday.logicengine.exceptions.NoSuchElementException;
import org.perfectday.logicengine.missions.Mission;
import org.perfectday.logicengine.model.ReferenceObject;
import org.perfectday.logicengine.model.activationstack.ActivationStack;
import org.perfectday.logicengine.model.activationstack.accidents.Accident;
import org.perfectday.logicengine.model.activationstack.accidents.Activation;
import org.perfectday.logicengine.model.activationstack.accidents.factories.ActivationFactory;
import org.perfectday.logicengine.model.battelfield.BattelField;
import org.perfectday.logicengine.model.battelfield.Field;
import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.command.combat.DeathMiniCommand;
import org.perfectday.logicengine.model.unittime.LongUnitTime;
import org.perfectday.logicengine.model.unittime.UnitTime;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.movement.MasterAPorEllos;
import org.perfectday.main.dummyengine.DummyGraphicsEngine;
import org.perfectday.main.dummyengine.GraphicsEngine;
import org.perfectday.main.dummyengine.threads.GraphicsEngineThreadGroup;
import org.perfectday.message.ReferenceObjectVO;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class Game {

    public static final String BRAIN_PROPERTIES=
            "assets/brains/brain.properties";
    
    private static Game instance;
    private static int WEIDTH_BATTELFIELD=17;
    private static int HEIGTH_BATTELFIELD=13;
    
   
    
    private List<Player> players;
    private BattelField battelField;
    private ActivationStack activationStack;
    private MasterOfCombatImpl masterOfCombat;
    private Mission mission;
    private Mini selectedMini;
    private MasterAPorEllos masterAPorEllos;
    private UnitTime actualTime;
    private UnitTime turnTime;
    private MasterCommunication masterCommunication;
    private static Logger logger = Logger.getLogger(Game.class);
    private boolean server;
    /**
     * Si la partida a iniciado.
     */
    private boolean started;
    
   
    
    /**
     * Constructor privado para crear partidas en "Stand alone"
     */
    public Game(BattelField battelField){
        players = new ArrayList<Player>();
        this.battelField = new BattelField(WEIDTH_BATTELFIELD, 
                HEIGTH_BATTELFIELD);
        this.battelField = battelField;
        this.activationStack = new ActivationStack();
        this.masterOfCombat = new MasterOfCombatImpl();
        this.masterCommunication = new MasterCommunication();
    }

    /**
     * Obtiene el game del grupo de hebras en ejecución actual
     * @return
     */
    public static Game getGame() {
        Game game=null;
        if (Thread.currentThread().getThreadGroup() instanceof KernellThreadGroup) {
            KernellThreadGroup kernellThreadGroup = (KernellThreadGroup) Thread.currentThread().getThreadGroup();
            game = kernellThreadGroup.getGame();
        }else if (Thread.currentThread().getThreadGroup() instanceof GraphicsEngineThreadGroup) {
            GraphicsEngineThreadGroup graphicsEngineThreadGroup = (GraphicsEngineThreadGroup) Thread.currentThread().getThreadGroup();
            game = graphicsEngineThreadGroup.getKernellThreadGroup().getGame();
        }else{
            StackTraceElement[] trace = Thread.currentThread().getStackTrace();
            logger.fatal("Una hebra que no pertencia a Kernell o Graphics entro " +
                    "getPerfectDayGUI["+Thread.currentThread().getName()+","
                    +Thread.currentThread().getThreadGroup().getName()+"]");
            for (StackTraceElement stackTraceElement : trace) {
                logger.fatal(stackTraceElement.toString());
            }
        }
        return game;
    }

    public Object getDintingibleObjectBand(Mini miniOcupant) {
        for(Player player: this.players){
            if(player.getBand().contains(miniOcupant)){
                return player.getDitingibleBandObject();
            }
        }
        return null;
    }

    /**
     * DEvuelve el jugador dueÃ±o del mini.
     * @param mini
     * @return
     */
    public Player getPlayerByMini(Mini mini) {
        for(Player player:this.players){
            if(player.getBand().contains(mini))
                return player;
        }
        return null;
    }

    /**
     * Desapila el proximo evento de juego y provoca la comprobación de
     * la misión.
     * Es el bucle del juego
     */
    public void nextAccident() {
        try {
            if(missionACompleted()){
                Game.getPerfectDayGUI().combatFinishWithPlayerWin(this.mission.getWiner());
                this.closeGame();
                
            }
            Accident accident = popNewAccident();
            Game.getPerfectDayGUI().addInfo(accident.toString());
            this.actualTime = accident.getUnitTime();
            accident.doAccidentWithEvent( this);
        } catch (Exception ex) {
            logger.error("NextAccident ERROR",ex);
        }
    }
    

    
    /**
     * Fin del juego, 
     */
    public void closeGame() {
        ((DummyGraphicsEngine)Game.getPerfectDayGUI()).dispose();
        ((DummyGraphicsEngine)Game.getPerfectDayGUI()).setVisible(false);
        //Eliminar System.exit(0);
        //System.exit(0);
    }
    
    

    /**
     * Devuelve true si la partida a acabado, 
     * y un jugador a alcanzado la victoria
     * @return
     */
    private boolean missionACompleted() {
        return this.mission.missionCompleted();
    }

    /**
     * obtiene 
     * @return
     */
    private Accident popNewAccident() {
        return this.activationStack.pop();
    }

    
    

    /**
     * Procesa el turno,
     * para la hebra a espera que la interfaz de usuario realice las operaciones
     * @param mini
     * @return Coste de la accion
     * @throws java.lang.InterruptedException
     */
    public synchronized UnitTime proccessTurn(Mini mini,List<Command> commandsTurn) throws InterruptedException {
        Game.getPerfectDayGUI().activateMini(mini);
        wait();
        UnitTime ut = Game.getPerfectDayGUI().getTurnCost();
        //add moved command
        
        return ut;        
    }

    public synchronized  ActivationStack getActivationStack() {
        return activationStack;
    }

    public void setActivationStack(ActivationStack activationStack) {
        this.activationStack = activationStack;
    }

    public BattelField getBattelField() {
        return battelField;
    }

    public void setBattelField(BattelField battelField) {
        this.battelField = battelField;
    }

    public MasterOfCombatImpl getMasterOfCombat() {
        return masterOfCombat;
    }

    public void setMasterOfCombat(MasterOfCombatImpl masterOfCombat) {
        this.masterOfCombat = masterOfCombat;
    }

    /**
     * Obtiene el puente hacia el motor gráfico de la hebra adecuada
     * @return
     */
    public static GraphicsEngine getPerfectDayGUI() {
        //TODO Solo deberían entrar hebras de kernell
        if (Thread.currentThread().getThreadGroup() instanceof KernellThreadGroup) {
            KernellThreadGroup kernellThreadGroup = (KernellThreadGroup) Thread.currentThread().getThreadGroup();
            return kernellThreadGroup.getGraphicsEngine().getGraphicsEngine();
        }else if (Thread.currentThread().getThreadGroup() instanceof GraphicsEngineThreadGroup) {
            GraphicsEngineThreadGroup graphicsEngineThreadGroup = (GraphicsEngineThreadGroup) Thread.currentThread().getThreadGroup();
            return graphicsEngineThreadGroup.getGraphicsEngine();
        }else{
            StackTraceElement[] trace = Thread.currentThread().getStackTrace();
            logger.fatal("Una hebra que no pertencia a Kernell o Graphics entro " +
                    "getPerfectDayGUI["+Thread.currentThread().getName()+","
                    +Thread.currentThread().getThreadGroup().getName()+"]");
            for (StackTraceElement stackTraceElement : trace) {
                logger.fatal(stackTraceElement.toString());
            }
        }
        return null;
    }


    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
    
    public void deadMini(Mini mini){
        for(Player player:this.players){
            if (player.getBand().contains(mini)){
                player.getBand().remove(mini);
                mini=null;
                return;
            }
                
        }
    }

    public Mini getSelectedMini() {
        return selectedMini;
    }

    public void setSelectedMini(Mini selectedMini) {
        this.selectedMini = selectedMini;
    }
    
   

    public MasterAPorEllos getMasterAPorEllos() {
        
        return masterAPorEllos;
    }

    public void setMasterAPorEllos(MasterAPorEllos masterAPorEllos) {
        this.masterAPorEllos = masterAPorEllos;
    }

    public UnitTime getActualTime() {
        return actualTime;
    }

    public void setActualTime(UnitTime actualTime) {
        this.actualTime = actualTime;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }
    /**
     * @see   org.perfectday.logicengine.core.event.manager.processors.CombatProcessor
     * @deprecated  
     * @param commands
     */
    public void resolvedCombat(List<Command> commands) {
        //Any Combat
        InstanceCombat combat = this.masterOfCombat.getInstanceCombat();        
        while (combat != null) {
            //do combat
            //ListCommand
            commands.addAll(combat.doCombat());
            combat = this.masterOfCombat.getInstanceCombat();
        }
        combat=null;
    }

    /**
     * @see  org.perfectday.logicengine.core.event.manager.processors.CombatProcessor
     * @deprecated  
     * @return
     */
    public List<Command> searchDead() {
        Game.getPerfectDayGUI().addInfo("Muerte recolecta espiritus...");
        List<Mini> minis = new ArrayList<Mini>();
        List<Command> commands = new ArrayList<Command>();
        for(Player player: players){
            minis.addAll(player.getBand());
        }
        List<Mini> death = new ArrayList<Mini>();
        for(Mini mini:minis){
            Game.getPerfectDayGUI().addInfo("Mini:"+mini.toString()+" vivo:"+mini.isAlive());
            if(!mini.isAlive()){
                commands.add(new DeathMiniCommand(mini.toString()));
                Game.getPerfectDayGUI().addInfo("Lo eliminamos de:"+ this.battelField.getField(mini));
                this.battelField.getField(mini).setMiniOcupant(null);
                death.add(mini);                
            }
        }
        Game.getPerfectDayGUI().addInfo("Muerto:"+death.size());
        for(Mini miniDead:death){            
            for(Player player:players){
                if(player.getBand().contains(miniDead)){
                    player.getBand().remove(miniDead);                                        
                    break;
                }
            }
            //TODO: Buscar activaciones o acciones de los muertos
            this.activationStack.deadClear(miniDead);
        }
        
        death.clear();
        minis.clear();
        death=null;
        minis = null;
        return commands;
    }
    
    /**
     * Busca un mini dentro del juego a partir de un identificador Ãºnico
     * @param referenceObjectVO
     * @return
     */
    public Mini searchMini(ReferenceObjectVO referenceObjectVO) throws NoSuchElementException {
        for (Player player : players) {
            for (Mini mini : player.getBand()) {
                if(mini.getId()==referenceObjectVO.getId().longValue())
                    return mini;
            }
        }
        throw new NoSuchElementException("No ID:"+referenceObjectVO.getId());        
    }
    
    
    public Field searchField(ReferenceObjectVO referenceObjectVO)throws NoSuchElementException{
        Field[][] fields = this.getBattelField().getBattelfield();
        for (int i = 0; i < fields.length; i++) {
            Field[] fields1 = fields[i];
            for (int j = 0; j < fields1.length; j++) {
                Field field = fields1[j];
                if(field.getId()==referenceObjectVO.getId().longValue())
                    return field;
            }
        }
        throw new NoSuchElementException("Id: "+referenceObjectVO.getId());
    }
    
    
    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isServer() {
        return server;
    }

    public void setServer(boolean server) {
        this.server = server;
    }

    public UnitTime getTurnTime() {
        return turnTime;
    }

    public void setTurnTime(UnitTime turnTime) {
        this.turnTime = turnTime;
    }
    
    /**
     * Obtiene el mini en memoria a partir de un ReferenceObject
     * @param referenceObject
     * @return
     */
    public Mini getMiniByReferneceObject(ReferenceObject referenceObject){
        for (Player player : players) {
            for (Mini mb : player.getBand()) {
                if(mb.equals(referenceObject)){
                    return mb;
                }
            }
        }
        return null;
    }
    
    /**
     * Obtiene el Field a partir de un ReferenceObject
     * @param referenceObject
     * @return
     */
    public Field getFieldByRefeferenceObject(ReferenceObject referenceObject){
        for (Field[] fields : this.getBattelField().getBattelfield()) {
            for (Field field : fields) {
                if(field.equals(referenceObject))
                    return field;
            }
        }
        return null;
    }
    


    /**
     * Inicializa la pila de activación con las activaciones de todos los minis
     */
    public void initializedActivationState(){
        for (Player player : this.getPlayers()) {
            for (Mini mini : player.getBand()) {
                Activation activation =
                    ActivationFactory.getInstance().createActivation(mini,
                    new LongUnitTime((long)(10-mini.getIniciative())));
                PutActionEvent putEvent = new PutActionEvent();
                putEvent.setActivation(activation);
                putEvent.setEventType(EventType.REQUEST);
                EventManager.getInstance().addEvent(putEvent);
            }
        }
        EventManager.getInstance().eventWaitTest();
    }

    public MasterCommunication getMasterCommunication() {
        return masterCommunication;
    }
    

}
