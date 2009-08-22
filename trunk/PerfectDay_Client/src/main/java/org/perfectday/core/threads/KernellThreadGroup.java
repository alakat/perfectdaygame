/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.perfectday.core.threads;

import java.util.List;
import org.apache.log4j.Logger;
import org.perfectday.communication.model.plugcommunication.PlugCommunication;
import org.perfectday.logicengine.core.Game;
import org.perfectday.logicengine.core.event.manager.EventManager;
import org.perfectday.logicengine.core.event.manager.EventManagerRunnable;
import org.perfectday.logicengine.core.player.Player;
import org.perfectday.logicengine.missions.Mission;
import org.perfectday.logicengine.model.battelfield.BattelField;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.main.dummyengine.threads.GraphicsEngineThreadGroup;
import org.perfectday.threads.Command;
import org.perfectday.threads.PerfectDayThreadGroup;

/**
 * Esta clase define el comportamiento del grupo de hebras asignado a manejar
 * el nucle del sistema.
 *
 * Permite a hebras de ejecución del nucleo acceder a recursos compartidos
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class KernellThreadGroup extends PerfectDayThreadGroup {

    private static Logger logger = Logger.getLogger(KernellThreadGroup.class);

    public static void sendGraphicsCommand(Command command) {
        if (Thread.currentThread().getThreadGroup() instanceof KernellThreadGroup) {
            KernellThreadGroup kernell   = (KernellThreadGroup) Thread.currentThread().getThreadGroup();
            kernell.getGraphicsEngine().getCommandRunner().sendCommand(command);
        }
    }

    /**
     * Hebra que controla el administrador de eventos en el sistema.
     */
    private EventManagerRunnable eventManagerRunnable;
    private EventManager eventManager;
    private GraphicsEngineThreadGroup graphicsEngine;
    private Thread gameThread;
    private Thread eventManagerThread;
    private Game game;
    private PlugCommunication plugCommunication;

    /**
     * 
     * @param name
     * @param game
     * @param plugCommunication
     */
    public KernellThreadGroup(String name, Game game, PlugCommunication plugCommunication) {
        super(name);
        this.game = game;
        this.plugCommunication = plugCommunication;
        game.getMasterCommunication().setPlugCom(plugCommunication);
    }

    

    

    /**
     * EventManager asociado a este grupo de hebras
     * @return
     */
    public EventManager getEventManager() {
        return this.eventManager;
    }

    /**
     * Hebra que procesa los commandos
     * @return
     */
    public Thread getEventManagerThread() {
        return eventManagerThread;
    }

    /**
     * Obtiene el grupo de hebras asociado al motor gráfico
     * @return
     */
    public GraphicsEngineThreadGroup getGraphicsEngine() {
        return graphicsEngine;
    }

    /**
     *
     * @param graphicsEngine
     */
    public void setGraphicsEngine(GraphicsEngineThreadGroup graphicsEngine) {
        this.graphicsEngine = graphicsEngine;
    }

    public EventManagerRunnable getEventManagerRunnable() {
        return eventManagerRunnable;
    }

    public Game getGame() {
        return game;
    }

    public Thread getGameThread() {
        return gameThread;
    }


    public PlugCommunication getPlugCommunication() {
        return plugCommunication;
    }



    @Override
    public void mystop() {
        //TODO send disconecting message
        super.mystop();
    }

    /**
     * Este método se ejecuta para iniciar el juego
     * @return
     */
    public boolean gameGo(){
        try{
            if(this.game.isServer()){
                //Se inicializa la pila de activación
                this.eventManager = new EventManager();
                this.eventManagerRunnable = new EventManagerRunnable(eventManager);
                this.eventManagerThread = new Thread(this, eventManagerRunnable, "Kernell-EventManger");
                final Runnable gameRunnable = new Runnable() {
                    @Override
                    public void run() {
                        if (Thread.currentThread().getThreadGroup() instanceof KernellThreadGroup) {

                            KernellThreadGroup kernellThreadGroup = (KernellThreadGroup) Thread.currentThread().getThreadGroup();
                            Game game = kernellThreadGroup.getGame();
                            game.initializedActivationState();
                            int eventosApilados = 0;
                            logger.info("Número de jugadores:"+game.getPlayers().size());
                            for (Player player : game.getPlayers()) {
                                logger.info("Jugador:"+player);
                                eventosApilados += player.getBand().size();
                            }
                            Object o = new Object();
                            logger.info("Esperamos al apilamiento de los eventos");
                            while (game.getActivationStack().getStack().size()!=eventosApilados){
                                synchronized(o){
                                    try{
//                                        logger.info("Espera....");
                                        o.wait(1000);
//                                        logger.info("Consulta?¿"+eventosApilados+"!="+game.getActivationStack().getStack().size());
                                    }catch(InterruptedException e){
                                        logger.fatal(Thread.currentThread().getName()+e.getMessage(),e);
                                    }
                                }
                            }
                            logger.info("Activaciones apiladas");
                            game.nextAccident();
                        }
                    }
                };
                this.gameThread = new Thread(this, gameRunnable, "Kernell-core");
                this.eventManagerThread.start();
                this.gameThread.start();
            }else{
                this.eventManager = new EventManager();
                this.eventManagerRunnable = new EventManagerRunnable(eventManager);
                this.eventManagerThread = new Thread(this, eventManagerRunnable, "Kernell-EventManger");
                this.eventManagerThread.start();
            }
            return true;
        }catch(Throwable t){

            logger.fatal(t.getMessage(), t);
            return false;
        }
    }

    /**
     * Construye un nuevo KernellThreadGroup donde iniciará el juego
     * @return KernellThreadGroup que ha sido creado
     */
    public static KernellThreadGroup buildKernellThreadGroup(boolean isServer,
            List<Mini> serverArmy, List<Mini> clientArmy,
            BattelField battlefield, Mission mission,
            PlugCommunication communication) {

        Game game = new Game(battlefield);
        mission.setGame(game);
        //Carga los jugadores.
        Player pServer = new Player("Rojo", false);
        Player pClient = new Player("Azul", false);
        if(isServer){
            pServer.setLocal(true);
            pClient.setLocal(false);
        }else{
            pServer.setLocal(false);
            pClient.setLocal(true);
        }
        pServer.setBand(serverArmy);
        pClient.setBand(clientArmy);
        game.getPlayers().add(pServer);
        game.getPlayers().add(pClient);
        //Cargamos mission
        game.setMission(mission);
        game.setServer(isServer);

        KernellThreadGroup group = new KernellThreadGroup("Kernell",game,communication);
        group.start();
        return group;
    }
}
