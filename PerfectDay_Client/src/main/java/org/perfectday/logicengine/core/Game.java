/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.core;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import org.apache.log4j.Logger;
import org.perfectday.infomation.Journalist;
import org.perfectday.logicengine.combat.InstanceCombat;
import org.perfectday.logicengine.combat.MasterOfCombatImpl;
import org.perfectday.logicengine.core.event.Event.EventType;
import org.perfectday.logicengine.core.event.game.PutActionEvent;
import org.perfectday.logicengine.core.event.manager.EventManager;
import org.perfectday.logicengine.core.event.manager.EventManagerThread;
import org.perfectday.logicengine.core.player.Player;
import org.perfectday.logicengine.exceptions.NoSuchElementException;
import org.perfectday.logicengine.missions.Mission;
import org.perfectday.logicengine.missions.SearchAndDestroyMission;
import org.perfectday.logicengine.model.ReferenceObject;
import org.perfectday.logicengine.model.activationstack.ActivationStack;
import org.perfectday.logicengine.model.activationstack.accidents.Accident;
import org.perfectday.logicengine.model.activationstack.accidents.Activation;
import org.perfectday.logicengine.model.activationstack.accidents.factories.ActivationFactory;
import org.perfectday.logicengine.model.battelfield.BattelField;
import org.perfectday.logicengine.model.battelfield.Field;
import org.perfectday.logicengine.model.battelfield.generator.SimpleMapGenerator;
import org.perfectday.logicengine.model.command.Command;
import org.perfectday.logicengine.model.command.combat.DeathMiniCommand;
import org.perfectday.logicengine.model.unittime.LongUnitTime;
import org.perfectday.logicengine.model.unittime.UnitTime;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.logicengine.movement.MasterAPorEllos;
import org.perfectday.main.laboratocGUI.LaboratoryGUI;
import org.perfectday.main.laboratocGUI.PerfectDayGUI;
import org.perfectday.message.ReferenceObjectVO;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class Game {

    public static final String BRAIN_PROPERTIES=
            "./org/perfectday/logicengine/brain/brain.properties";
    
    private static Game instance;
    private static int WEIDTH_BATTELFIELD=17;
    private static int HEIGTH_BATTELFIELD=13;
    
   
    
    private List<Player> players;
    private BattelField battelField;
    private ActivationStack activationStack;
    private MasterOfCombatImpl masterOfCombat;
    private PerfectDayGUI perfectDayGUI;
    private Mission mission;
    private Mini selectedMini;
    private MasterAPorEllos masterAPorEllos;
    private UnitTime actualTime;
    private UnitTime turnTime;
    private Logger logger = Logger.getLogger(Game.class);
    private EventManagerThread eventManagerThread;
    private boolean server;
    /**
     * Si la partida a iniciado.
     */
    private boolean started;
    
    /**
     * Constructor publico para crear partidas a traves de internet
     * @param bt
     */
    public Game(BattelField bt){
        this.players = new ArrayList<Player>();
        this.battelField = bt;
        this.activationStack = new ActivationStack();
        this.masterOfCombat = MasterOfCombatImpl.getInstance();
        this.perfectDayGUI = new LaboratoryGUI();
        this.eventManagerThread = EventManagerThread.getEventManagerThread();
        this.started = false;
        Game.instance = this;        
    }
    
    private Game(){
        players = new ArrayList<Player>();
        this.activationStack = new ActivationStack();
        this.masterOfCombat = MasterOfCombatImpl.getInstance();        
        this.perfectDayGUI = new LaboratoryGUI();         
        this.eventManagerThread = EventManagerThread.getEventManagerThread();
        
    }
    /**
     * Constructor privado para crear partidas en "Stand alone"
     */
    private Game(boolean b){
        players = new ArrayList<Player>();
        players.add(Player.createPlayerAzul());
        players.add(Player.createPlayerRojo());
        this.battelField = new BattelField(WEIDTH_BATTELFIELD, 
                HEIGTH_BATTELFIELD);
        SimpleMapGenerator simpleMapGenerator = 
                new SimpleMapGenerator(this.battelField.getHigth(), 
                this.battelField.getWeidth());
        simpleMapGenerator.generateBattelField();
        this.battelField.generateBattelField(simpleMapGenerator);        
        this.activationStack = new ActivationStack();
        this.masterOfCombat = MasterOfCombatImpl.getInstance();        
        this.perfectDayGUI = new LaboratoryGUI();         
        
        this.eventManagerThread = EventManagerThread.getEventManagerThread();
    }

    public static Game getInstance() {
        if (instance==null)
            instance = new Game(false);
        return instance;
    }
    
    public static Game getInstance_(){
        if (instance==null)
            instance = new Game();
        return instance;
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
     * DEvuelve el jugador dueño del mini.
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
     * inicializalos la interfazdel juego
     */
    public void initGUI() {
        this.perfectDayGUI.setGame(this);
        final LaboratoryGUI laboratoryGUI = (LaboratoryGUI) this.perfectDayGUI;
        final JFrame jpctdebug = new JFrame("Test jpct..v0.0.1");
        Thread t = new Thread(new Runnable() {
            public void run() {
                laboratoryGUI.setVisible(true);
                
            }
        });
        
        t.start();
        
    }
    /**
     * Loop del juego
     * @throws java.lang.Exception
     */
    public void loopGame() throws Exception {
        logger.info(this.activationStack.toString());
        List<Command> commands = new ArrayList<Command>();
        while (!missionACompleted()) {
            Accident accident = popNewAccident();
            LaboratoryGUI.me.addInfo(accident.toString());
            this.actualTime = accident.getUnitTime();
            accident.doAccident(commands, this);
            Journalist.getInstance().infoCombat(commands);
            this.perfectDayGUI.proccessCommand(commands);
            commands.clear();
        }

        
        LaboratoryGUI.me.combatFinishWithPlayerWin(this.mission.getWiner());
        this.closeGame();
    }

    public void nextAccident() {
        try {
            if(missionACompleted()){
                LaboratoryGUI.me.combatFinishWithPlayerWin(this.mission.getWiner());
                this.closeGame();
                
            }
            Accident accident = popNewAccident();
            LaboratoryGUI.me.addInfo(accident.toString());
            this.actualTime = accident.getUnitTime();
            accident.doAccidentWithEvent( this);
        } catch (Exception ex) {
            logger.error("NextAccident ERROR",ex);
        }
    }
    
    /**
     * Inicia la partida
     */
    public void startGame(){
        int irojo=2;
        int jrojo=2;
        int iazul=15;
        int jazul=2;
        
        List<Mini> bandRoja = (List<Mini>) players.get(1).getBand();
        for(Mini mini: bandRoja){
            logger.info("Mini:"+mini.toString());
            battelField.getBattelfield()[irojo][jrojo].setMiniOcupant(mini);
            Activation activation = 
                    ActivationFactory.getInstance().createActivation(mini,
                    new LongUnitTime((long)(10-mini.getIniciative())));
            this.activationStack.put(activation);
            jrojo++;
            if(jrojo>battelField.getBattelfield()[0].length){
                jrojo=2;
                irojo--;
            }
        }                
        logger.info("...");
        List<Mini> bandAzul = (List<Mini>) players.get(0).getBand();
        for(Mini mini: bandAzul){
            logger.info("Mini:"+mini.toString());
            battelField.getBattelfield()[iazul][jazul].setMiniOcupant(mini);
            Activation activation = 
                    ActivationFactory.getInstance().createActivation(mini,
                    new LongUnitTime((long)(10-mini.getIniciative())));
            this.activationStack.put(activation);
            jazul++;
            if(jazul>=battelField.getBattelfield()[0].length){
                jazul=2;
                iazul++;
            }
        }    
        //Mission
        this.mission = new SearchAndDestroyMission(instance);
        initGUI();
    }
    
    
    
    public void doGameWithEvent(){
        this.runEventManager();
        startGame();        
        logger.info(this.activationStack.toString());
        this.nextAccident();
    }
    
    public void doGame() throws CloneNotSupportedException, InterruptedException, Exception{
        startGame();
        loopGame();
    }

    
    /**
     * Fin del juego, 
     */
    public void closeGame() {
        LaboratoryGUI.me.dispose();
        LaboratoryGUI.me.setVisible(false);
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
        perfectDayGUI.activateMini(mini);
        wait();
        UnitTime ut = perfectDayGUI.getTurnCost();
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

    public PerfectDayGUI getPerfectDayGUI() {
        return perfectDayGUI;
    }

    public void setPerfectDayGUI(PerfectDayGUI perfectDayGUI) {
        this.perfectDayGUI = perfectDayGUI;
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
        LaboratoryGUI.me.addInfo("Muerte recolecta espiritus...");
        List<Mini> minis = new ArrayList<Mini>();
        List<Command> commands = new ArrayList<Command>();
        for(Player player: players){
            minis.addAll(player.getBand());
        }
        List<Mini> death = new ArrayList<Mini>();
        for(Mini mini:minis){
            LaboratoryGUI.me.addInfo("Mini:"+mini.toString()+" vivo:"+mini.isAlive());
            if(!mini.isAlive()){
                commands.add(new DeathMiniCommand(mini.toString()));
                LaboratoryGUI.me.addInfo("Lo eliminamos de:"+ this.battelField.getField(mini));
                this.battelField.getField(mini).setMiniOcupant(null);
                death.add(mini);                
            }
        }
        LaboratoryGUI.me.addInfo("Muerto:"+death.size()); 
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
    
    
     
    public static void main(String[] argv) 
            throws CloneNotSupportedException, InterruptedException, Exception{
        Game game = Game.getInstance();        
        game.doGameWithEvent();
        
    }
    
    /**
     * Busca un mini dentro del juego a partir de un identificador único
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
    
    /**
     * Recarga los valores de Game para una nueva partida.
     */
    public void reload(){
        this.players = new ArrayList<Player>();
        this.activationStack = new ActivationStack();
        this.masterOfCombat = MasterOfCombatImpl.getInstance();
        this.perfectDayGUI = new LaboratoryGUI();
        this.eventManagerThread = EventManagerThread.getEventManagerThread();
        this.started = false;
        this.battelField = null;
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
     * Inicializa la pila de activación a partir de los minis de los jugadores
     */
    public  static void initializedActivationState(){
        for (Player player : Game.getInstance().getPlayers()) {
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
    
    
    public void runEventManager(){
        Thread tEvent = new Thread(EventManagerThread.getEventManagerThread());
        tEvent.start();
    }
}
