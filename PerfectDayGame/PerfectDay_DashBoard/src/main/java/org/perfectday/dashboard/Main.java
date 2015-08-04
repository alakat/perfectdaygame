/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.dashboard;

import java.awt.Toolkit;
import java.io.File;
import java.net.URL;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.perfectday.dashboard.gui.DashBoard;
import org.perfectday.dashboard.threads.DashBoardThreadGroup;
import static org.perfectday.main.dummyengine.model.ActivationStackPanel.IMG_ACTIVATION;
import org.perfectday.main.dummyengine.model.CombatInformationPanel;

/**
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class Main implements Runnable {
    
    private String userInit = new String("");
    private String passInit = new String("");

    
    private static Logger logger = LogManager.getLogger(Main.class);
    public static void main(String args[] )throws Exception{
        Main main =new Main();
        if (args.length==2){
            main.userInit=args[0];
            main.passInit=args[1];
        }
        Thread t = new Thread(DashBoardThreadGroup.getInstance(),main,"DashBoard-Main");
        t.start();
        t.join();
    }

    public Main() {
    }

    @Override
    public void run() {

        //TODO  eliminar despues de usar ThreadGroup
//        Game.getInstance_();
        String url_="assets/"+IMG_ACTIVATION;
        URL url = CombatInformationPanel.class.getClassLoader().
                getResource(url_);
        System.out.println("url_="+url_);
        System.out.println(":::"+url);
        System.out.println("Resource:"+getClass().getClassLoader().getResourceAsStream(url_));
        Toolkit.getDefaultToolkit().getImage(
                url);
        Logger.getLogger(Main.class).info("LOADING PERFECTDAY");
        new DashBoard(userInit,passInit).setVisible(true);
    }

}
