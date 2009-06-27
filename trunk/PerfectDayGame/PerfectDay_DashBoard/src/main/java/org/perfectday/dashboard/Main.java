/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.dashboard;

import org.perfectday.dashboard.gui.DashBoard;
import org.perfectday.dashboard.threads.DashBoardThreadGroup;
import org.perfectday.logicengine.core.Game;

/**
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class Main implements Runnable {

    public static void main(String args[] )throws Exception{
        Thread t = new Thread(DashBoardThreadGroup.getInstance(),new Main(),"DashBoard-Main");
        t.start();
        t.join();
    }

    public Main() {
    }

    @Override
    public void run() {
        Game.getInstance_();
        new DashBoard().setVisible(true);
    }

}
