/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.gamebuilder;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import org.perfectday.communication.XmppPluginsCommunicator.XMPPPluginsCommunicator;
import org.perfectday.communication.masterCommunication.MasterCommunication;
import org.perfectday.core.asf.FiniteAutomatonState;
import org.perfectday.core.asf.State;
import org.perfectday.dashboard.communication.GameBuilderCommunicator;
import org.perfectday.communication.model.plugcommunication.PerfectDayMessage;
import org.perfectday.dashboard.communication.model.PerfectDayMessageFactory;
import org.perfectday.dashboard.exception.GameBuilderException;
import org.perfectday.gamebuilder.model.BattleDescription;
import org.perfectday.gamebuilder.model.MiniDescription;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.core.industry.MiniFactory;
import org.perfectday.logicengine.core.player.Player;
import org.perfectday.logicengine.missions.SearchAndDestroyMission;
import org.perfectday.logicengine.model.battelfield.BattelField;
import org.perfectday.logicengine.model.minis.Mini;

/**
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class GameBuilder extends FiniteAutomatonState {
    
    private enum GameBuilderState{
        INIT_GAME_BUILDER,
        SELECTED_ARMY,
        DEPLOY_ARMY,
        CONFIRMATION_BATTLE
    }
    
    private static final Logger logger =Logger.getLogger(GameBuilder.class);
    private GameBuilderState state;
    private GameBuilderCommunicator communication;
    private BattleDescription battleDescription;
    private List<MiniDescription> serverArmy;
    private List<Mini> trueServerArmy;
    private List<MiniDescription> clienteArmy;
    private List<Mini> trueClientArmy;
    private int battleWeidth;
    private int battleHeigth;
    private boolean server;
    protected  BattelField battlefield;


    public GameBuilder(List<State> states, State initial, State end, GameBuilderCommunicator communication) {
        super(states, initial, end);
        this.communication = communication;
        this.trueClientArmy = new ArrayList<Mini>();
        this.trueServerArmy = new ArrayList<Mini>();
    }
    
    
    
    public GameBuilder(GameBuilderCommunicator com) {
        super(null, null, null);
        this.communication = com;
        com.setGameBuilder(this);
         state = GameBuilderState.INIT_GAME_BUILDER;
    }
    public void clientAcceptBattle(){
        
        doChangeState();
    }
    
    public void receiveClientAramy(List<MiniDescription> army){
        
        this.clienteArmy = new ArrayList<MiniDescription>();
        this.clienteArmy.addAll(army);
        if(serverArmy!=null){
            state = GameBuilderState.DEPLOY_ARMY;
            doChangeState();
        }
    }
    
    public void receiveServerArmy(List<MiniDescription> army){
        this.serverArmy = new ArrayList<MiniDescription>();
        this.serverArmy.addAll(army);
        if(clienteArmy!=null){
            state = GameBuilderState.DEPLOY_ARMY;
            doChangeState();
        }
    }
    
     private void doChangeState() {
        try {
            switch (state) {
                case INIT_GAME_BUILDER:
                    if(isServer()){
                        for (String name : MiniFactory.getInstance().getIndex()) {
                            double cost = MiniFactory.getInstance().getMiniCost(name);
                            String description = "none";//TODO get mini description;
                            this.battleDescription.getMinis().add(new MiniDescription(name, cost, description));
                        }
                        PerfectDayMessage pdm = PerfectDayMessageFactory.getInstance().createInitialBattleMessage(this.battleDescription);
                        this.communication.sendMessage(pdm);
                        
                    }else{
                        //contesta con ok.
                        PerfectDayMessage response = PerfectDayMessageFactory.getInstance().createAceptMessageResponse();
                        this.getCommunication().sendMessage(response);  
                         
                    }
                    break;
                case SELECTED_ARMY:
                    break;
                case DEPLOY_ARMY:
                    break;
                case CONFIRMATION_BATTLE:
                    break;
                default:
                    logger.warn("No debería haber entrado");
                    break;
            }
        } catch (GameBuilderException ex) {
            logger.error("ERROR en communicación",ex);
            //TODO Estado de error
        }
         
    }

    public GameBuilderCommunicator getCommunication() {
        return communication;
    }

    public void setCommunication(GameBuilderCommunicator communication) {
        this.communication = communication;
        this.communication.setGameBuilder(this);
    }

    public boolean isServer() {
        return server;
    }

    public void setServer(boolean server) {
        this.server = server;
    }

    public BattleDescription getBattleDescription() {
        return battleDescription;
    }

    public void setBattleDescription(BattleDescription battleDescription) {
        this.battleDescription = battleDescription;
    }

    public List<MiniDescription> getServerArmy() {
        return serverArmy;
    }

    public void setServerArmy(List<MiniDescription> serverArmy) {
        this.serverArmy = serverArmy;
    }

    public List<MiniDescription> getClienteArmy() {
        return clienteArmy;
    }

    public void setClienteArmy(List<MiniDescription> clienteArmy) {
        this.clienteArmy = clienteArmy;
    }

    public int getBattleHeigth() {
        return battleHeigth;
    }

    public void setBattleHeigth(int battleHeigth) {
        this.battleHeigth = battleHeigth;
    }

    public int getBattleWeidth() {
        return battleWeidth;
    }

    public void setBattleWeidth(int battleWeidth) {
        this.battleWeidth = battleWeidth;
    }

    public BattelField getBattlefield() {
        return battlefield;
    }

    public void setBattlefield(BattelField battlefield) {
        this.battlefield = battlefield;
    }

    public GameBuilderState getState() {
        return state;
    }

    public void setState(GameBuilderState state) {
        this.state = state;
    }

    public List<Mini> getTrueClientArmy() {
        return trueClientArmy;
    }

    public void setTrueClientArmy(List<Mini> trueClientArmy) {
        this.trueClientArmy = trueClientArmy;
    }

    public List<Mini> getTrueServerArmy() {
        return trueServerArmy;
    }

    public void setTrueServerArmy(List<Mini> trueServerArmy) {
        this.trueServerArmy = trueServerArmy;
    }
   
    /**
     *  Inicializa el juego.
     */
    public void initGame(){
        Game.getInstance().reload();
        Game game = Game.getInstance();
        //Carga los jugadores.
        Player pServer = new Player("Rojo", false);                    
        Player pClient = new Player("Azul", false);        
        if(this instanceof  GameBuilderServer){            
            pServer.setLocal(true);
            pClient.setLocal(false);
        }
        if(this instanceof  GameBuilderClient){
            pServer.setLocal(false);
            pClient.setLocal(true);
        }
        pServer.setBand(trueServerArmy);
        pClient.setBand(trueClientArmy);
        game.getPlayers().add(pServer);
        game.getPlayers().add(pClient);
        //Las referencia a los battelfield son incorrectas, lo solventamos.
        for (Mini mini : trueServerArmy) {
            battlefield.getField(mini).setMiniOcupant(mini);
        }
        for (Mini mini : trueClientArmy) {
            battlefield.getField(mini).setMiniOcupant(mini);
        }
        //Carga el mapa.
        game.setBattelField(battlefield);        
        //Start = false.
        game.setStarted(false);        
        //Cargamos las comunicaciones correctas a EventManager.
//        Iterator i = this.getCommunication().getChat().getListeners().iterator();
        //Set misison
        switch(this.getBattleDescription().getMission()){
            case SEARCH_AND_DESTROY:
                Game.getInstance().setMission(new SearchAndDestroyMission(Game.getInstance()));
                break;
            default:
                JOptionPane.showMessageDialog(null, "Información", "La misión no ha sido reconocida", JOptionPane.INFORMATION_MESSAGE);
                Game.getInstance().setMission(new SearchAndDestroyMission(Game.getInstance()));
                break;
        }
        
        MasterCommunication.getInstance().setPlugCom(new XMPPPluginsCommunicator(this.getCommunication().getChat()));        
        //Asignar si es Cliente o Servidor.
        if(this instanceof  GameBuilderServer){            
            game.setServer(true);
        }
        if(this instanceof  GameBuilderClient){
            game.setServer(false);
        }
        
    }
    
     public void readArmies() {
        logger.debug("True Client Army");
        for (Mini mini : this.getTrueClientArmy()) {
            logger.debug(mini.toString() + ":" + mini.getId());
        }
        logger.debug("True Client Server");
        for (Mini mini : this.getTrueServerArmy()) {
            logger.debug(mini.toString() + ":" + mini.getId());
        }
    }
}
