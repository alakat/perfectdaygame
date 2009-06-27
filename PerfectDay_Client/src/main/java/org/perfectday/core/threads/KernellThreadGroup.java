/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.core.threads;

import org.perfectday.logicengine.core.event.manager.EventManager;
import org.perfectday.logicengine.core.event.manager.EventManagerThread;
import org.perfectday.threads.PerfectDayThreadGroup;

/**
 * Esta clase define el comportamiento del grupo de hebras asignado a manejar
 * el nucle del sistema.
 *
 * Permite a hebras de ejecución del nucleo acceder a recursos compartidos
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class KernellThreadGroup extends PerfectDayThreadGroup {

    /**
     * Hebra que controla el administrador de eventos en el sistema.
     */
    private EventManagerThread eventManagerThread;
    private PerfectDayThreadGroup graphicsEngine;
    private Thread gameThread;
    private Thread communicationThread;

    public KernellThreadGroup(String name) {
        super(name);
    }

    /**
     * EventManager asociado a este grupo de hebras
     * @return
     */
    public EventManager getEventManager(){
        return this.eventManagerThread.getEventManager();
    }

    /**
     * Hebra que procesa los commandos
     * @return
     */
    public EventManagerThread getEventManagerThread(){
        return eventManagerThread;
    }

    /**
     * Obtiene el grupo de hebras asociado al motor gráfico
     * @return
     */
    public PerfectDayThreadGroup getGraphicsEngine() {
        return graphicsEngine;
    }

    /**
     *
     * @param graphicsEngine
     */
    public void setGraphicsEngine(PerfectDayThreadGroup graphicsEngine) {
        this.graphicsEngine = graphicsEngine;
    }

    @Override
    public void mystop() {
        //TODO send disconecting message
        super.mystop();
    }



    /**
     * Construye un nuevo KernellThreadGroup donde iniciará el juego
     * @return
     */
    public static KernellThreadGroup buildKernellThreadGroup(){
        KernellThreadGroup group = new KernellThreadGroup("Kernell");
        group.start();
        return group;
    }



}
