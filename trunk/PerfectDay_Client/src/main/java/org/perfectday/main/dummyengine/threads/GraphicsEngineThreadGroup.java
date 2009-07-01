/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.main.dummyengine.threads;

import org.perfectday.core.threads.KernellThreadGroup;
import org.perfectday.main.dummyengine.DummyGraphicsEngine;
import org.perfectday.main.dummyengine.GraphicsEngine;
import org.perfectday.threads.PerfectDayThreadGroup;

/**
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class GraphicsEngineThreadGroup extends PerfectDayThreadGroup {

    private Thread engine;
    private KernellThreadGroup kernellThreadGroup;
    private GraphicsEngine graphicsEngine;

    //FIXME Juanjo otras hebras que necesites.

    public GraphicsEngineThreadGroup(String name) {
        super(name);
        graphicsEngine = new DummyGraphicsEngine();
    }

    /**
     * Solo en el motor dummy
     * @return
     */
    public DummyGraphicsEngine getDummyGraphicsEngine() {
        return (DummyGraphicsEngine) graphicsEngine;
    }



    public KernellThreadGroup getKernellThreadGroup() {
        return kernellThreadGroup;
    }

    /**
     * Ejecución del motor gráfico
     */
    public void gameGo(){
        final DummyGraphicsEngine dummyGraphicsEngine= ((DummyGraphicsEngine)graphicsEngine);
        this.engine =  new Thread(new Runnable() {
            public void run() {
                dummyGraphicsEngine.setVisible(true);

            }
        });
        this.engine.start();
    }

    public void setKernellThreadGroup(KernellThreadGroup kernellThreadGroup) {
        this.kernellThreadGroup = kernellThreadGroup;
    }

    @Override
    public void mystop() {
        this.engine.stop();
        super.mystop();
    }


    /**
     * Interfaz al motor gráfico
     * @return
     */
    public GraphicsEngine getGraphicsEngine(){
        return graphicsEngine;
    }




    /**
     * construye un ThreadGroup para el
     * @return
     */
    public static GraphicsEngineThreadGroup buildGraphicsEngineThreadGroup(){
        GraphicsEngineThreadGroup group = new GraphicsEngineThreadGroup("GraphicsEngine");
        group.start();
        return group;
    }

}
