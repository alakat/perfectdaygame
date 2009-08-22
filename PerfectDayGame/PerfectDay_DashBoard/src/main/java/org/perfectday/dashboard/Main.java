/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.dashboard;

import org.apache.log4j.Logger;
import org.perfectday.dashboard.gui.DashBoard;
import org.perfectday.dashboard.threads.DashBoardThreadGroup;

/**
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class Main implements Runnable {

    private static Logger logger = Logger.getLogger(Main.class);
    public static void main(String args[] )throws Exception{
        Thread t = new Thread(DashBoardThreadGroup.getInstance(),new Main(),"DashBoard-Main");
        t.start();
        t.join();
    }

    public Main() {
    }

    @Override
    public void run() {

        //TODO  eliminar despues de usar ThreadGroup
//        Game.getInstance_();
        new DashBoard().setVisible(true);
    }

}
