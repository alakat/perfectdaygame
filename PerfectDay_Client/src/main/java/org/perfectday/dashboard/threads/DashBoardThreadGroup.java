/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.dashboard.threads;

import org.perfectday.core.threads.KernellThreadGroup;
import org.perfectday.main.dummyengine.threads.GraphicsEngineThreadGroup;
import org.perfectday.threads.Command;
import org.perfectday.threads.PerfectDayThreadGroup;

/**
 * Este ThreadGroup es lanzado con el Dashboard y sirve de puente de enlace
 * el nucleo y el DashBoard
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class DashBoardThreadGroup extends PerfectDayThreadGroup{

    private static final DashBoardThreadGroup instance = new DashBoardThreadGroup("Dashboard");

    public static void sendEventToKernell(Command messageReceive) {
        if (Thread.currentThread().getThreadGroup() instanceof DashBoardThreadGroup) {
            DashBoardThreadGroup dashBoardThreadGroup = (DashBoardThreadGroup) Thread.currentThread().getThreadGroup();
            dashBoardThreadGroup.getKernellInRun().getCommandRunner().sendCommand(messageReceive);
        }
    }
    private KernellThreadGroup kernellInRun;
    private GraphicsEngineThreadGroup graphicsInRun;
    private boolean inGameConstruction;

    private DashBoardThreadGroup(String name) {
        super(name);
    }

    /**
     * Carga los grupos de hebras que ejecutarán el juego. Ademas ordena a estos
     * empezar el juego
     * @param kernell ThreadGroup del kernell del juego
     * @param graphics ThreadGroup para el controls de Graphicos
     * @return
     */
    public boolean gameGo(){
        if(this.kernellInRun!=null &&
                this.graphicsInRun!=null){
            kernellInRun.setGraphicsEngine(graphicsInRun);
            graphicsInRun.setKernellThreadGroup(kernellInRun);
            graphicsInRun.getGraphicsEngine().setGame(kernellInRun.getGame());
            kernellInRun.gameGo();
            graphicsInRun.gameGo();
            return true;
        }else{
            return false;
        }
    }

    /**
     * Para la ejecución actual del juego
     * @return
     */
    public boolean stopGame(){
        if(this.kernellInRun==null ||
                this.graphicsInRun==null){
            return false;
        }
        kernellInRun.mystop();
        graphicsInRun.mystop();
        kernellInRun=null;
        graphicsInRun=null;
        return true;
    }

    /**
     * No indica si existe un juego empezado o pendiente de empezar
     * @return true si existe
     */
    public boolean inGame(){
        return this.kernellInRun!=null;
    }

    
    public static DashBoardThreadGroup getInstance() {
        return instance;
    }

    public void setGraphicsInRun(GraphicsEngineThreadGroup graphicsInRun) {
        this.graphicsInRun = graphicsInRun;
    }

    public void setKernellInRun(KernellThreadGroup kernellInRun) {
        this.kernellInRun = kernellInRun;
    }

    public GraphicsEngineThreadGroup getGraphicsInRun() {
        return graphicsInRun;
    }

    public KernellThreadGroup getKernellInRun() {
        return kernellInRun;
    }

    public boolean isInGameConstruction() {
        return inGameConstruction;
    }

    public void setInGameConstruction(boolean inGameConstruction) {
        this.inGameConstruction = inGameConstruction;
    }

    
    
}
