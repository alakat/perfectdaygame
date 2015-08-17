/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.gamebuilder;

import java.util.HashMap;
import java.util.Map;
import org.perfectday.dashboard.communication.GameBuilderCommunicator;
import org.perfectday.dashboard.communication.GameBuilderGateWay;
import org.perfectday.dashboard.communication.oneplayer.GameBuilderCommunicatorOnePlayer;
import org.perfectday.dashboard.exception.GameBuilderException;
import org.perfectday.dashboard.threads.DashBoardThreadGroup;

/**
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class GameBuilderFactory {
    public static final String ONEPLAYER_NAME_BUILDERS = "oneplayer";
    private static final GameBuilderFactory instance = new GameBuilderFactory();
    private Map<String,GameBuilder> builders;

    private GameBuilderFactory() {
        this.builders = new HashMap<String, GameBuilder>();
    }

    public void delete(String participant) {
       if(this.builders.containsKey(participant))
           this.builders.remove(participant);
    }
    
    public GameBuilder getGameBuilder(String userDestiny){
        if(this.builders.containsKey(userDestiny))
            return this.builders.get(userDestiny);
        return null;
    }

    public static GameBuilderFactory getInstance() {
        return instance;
    }

    /**
     * Crea una instancia de un Objecto {@link GameBuilderServer} para
     * la creación de un nuevo juego.
     * Marca el sistema como "InGameConstruction"
     * @param userDestiny
     * @return
     * @throws java.lang.NoSuchMethodException
     * @throws org.perfectday.dashboard.exception.GameBuilderException Si ya se
     * esta creando un juego
     */
    public GameBuilderServer createGameBuilderServer(String userDestiny) throws NoSuchMethodException, GameBuilderException{
        if (Thread.currentThread().getThreadGroup() instanceof DashBoardThreadGroup) {
            DashBoardThreadGroup dashBoardThreadGroup = (DashBoardThreadGroup) Thread.currentThread().getThreadGroup();
            if(dashBoardThreadGroup.isInGameConstruction()){
                throw new GameBuilderException("Otra partida se está construyendo");
            }else{
                dashBoardThreadGroup.setInGameConstruction(true);
            }
        }
        GameBuilderCommunicator bgc =
                GameBuilderGateWay.getInstance().getCommunication(userDestiny);
        GameBuilderServer gb = new GameBuilderServer(bgc);
        bgc.setGameBuilder(gb);
        this.builders.put(userDestiny, gb);
        return gb;
    }
    
    /**
     * Crea una instancia de un Objecto {@link GameBuilderClient} para
     * la creación de un nuevo juego.
     * Marca el sistema como "InGameConstruction"
     * @param userDestiny
     * @return
     * @throws java.lang.NoSuchMethodException
     * @throws org.perfectday.dashboard.exception.GameBuilderException Si ya se
     * esta creando un juego
     */
    public GameBuilderClient createGameBuilderClient(String userDestiny) throws NoSuchMethodException, GameBuilderException{
         if (Thread.currentThread().getThreadGroup() instanceof DashBoardThreadGroup) {
            DashBoardThreadGroup dashBoardThreadGroup = (DashBoardThreadGroup) Thread.currentThread().getThreadGroup();
            if(dashBoardThreadGroup.isInGameConstruction()){
                throw new GameBuilderException("Otra partida se está construyendo");
            }else{
                dashBoardThreadGroup.setInGameConstruction(true);
            }
        }

        GameBuilderCommunicator bgc =
                GameBuilderGateWay.getInstance().getCommunication(userDestiny);
        GameBuilderClient gb = new GameBuilderClient(bgc);
        bgc.setGameBuilder(gb);
        this.builders.put(userDestiny, gb);
        return gb;
    }
    
    
    /**
     * Crea una instancia de un Objecto {@link GameBuilderServer} para
     * la creación de un nuevo juego.
     * Marca el sistema como "InGameConstruction"
     * @return
     * @throws java.lang.NoSuchMethodException
     * @throws org.perfectday.dashboard.exception.GameBuilderException Si ya se
     * esta creando un juego
     */
    public GameBuilderOnePlayer createGameBuilderOnePlayer() throws NoSuchMethodException, GameBuilderException{
        if (Thread.currentThread().getThreadGroup() instanceof DashBoardThreadGroup) {
            DashBoardThreadGroup dashBoardThreadGroup = (DashBoardThreadGroup) Thread.currentThread().getThreadGroup();
            if(dashBoardThreadGroup.isInGameConstruction()){
                throw new GameBuilderException("Otra partida se está construyendo");
            }else{
                dashBoardThreadGroup.setInGameConstruction(true);
            }
        }
        GameBuilderOnePlayer gb = new GameBuilderOnePlayer();
        
        this.builders.put(ONEPLAYER_NAME_BUILDERS, gb);
        return gb;
    }
    
    /**
     * Crea una instancia de un Objecto {@link GameBuilderServer} para
     * la creación de un nuevo juego. Donde se escoger? una misi?n preseleccionada 
     * @return
     * @throws NoSuchMethodException
     * @throws GameBuilderException 
     */
    public GameBuilderSelectionQuest createGameBuilderOnePlayerSelectQuest() throws NoSuchMethodException, GameBuilderException{
        if (Thread.currentThread().getThreadGroup() instanceof DashBoardThreadGroup) {
            DashBoardThreadGroup dashBoardThreadGroup = (DashBoardThreadGroup) Thread.currentThread().getThreadGroup();
            if(dashBoardThreadGroup.isInGameConstruction()){
                throw new GameBuilderException("Otra partida se está construyendo");
            }else{
                dashBoardThreadGroup.setInGameConstruction(true);
            }
        }
        GameBuilderSelectionQuest gb = new GameBuilderSelectionQuest();
        this.builders.put(ONEPLAYER_NAME_BUILDERS, gb);
        return gb;
    }
    

}
