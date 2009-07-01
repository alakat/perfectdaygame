/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.threads.commands.graphics;

import java.util.List;
import org.perfectday.logicengine.model.battelfield.Field;
import org.perfectday.logicengine.model.minis.Mini;
import org.perfectday.main.dummyengine.threads.GraphicsEngineThreadGroup;
import org.perfectday.threads.Command;

/**
 *
 * Este comando solicita al motor de gráficos que actualize las casillas a la
 * que un determinado mini puede acceder con su movimeinto.
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class AddMiniFieldAccessCommand extends Command {

    private List<Field> access;
    private Mini mini;

    public AddMiniFieldAccessCommand(List<Field> access, Mini mini) {
        this.access = access;
        this.mini = mini;
    }


    @Override
    public void trueRun() throws Exception {
        if (Thread.currentThread().getThreadGroup() instanceof GraphicsEngineThreadGroup) {
            GraphicsEngineThreadGroup graphicsEngineThreadGroup = (GraphicsEngineThreadGroup) Thread.currentThread().getThreadGroup();
            graphicsEngineThreadGroup.getGraphicsEngine().addMiniAccess(mini, access);
            graphicsEngineThreadGroup.getGraphicsEngine().redraw();
        }
    }


}
