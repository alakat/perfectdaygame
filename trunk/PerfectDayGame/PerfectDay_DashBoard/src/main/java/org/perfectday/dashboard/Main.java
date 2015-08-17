/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.perfectday.dashboard;

import es.bitsonfire.PDMinisDatabase;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.perfectday.dashboard.gui.MainMenu;
import org.perfectday.dashboard.threads.DashBoardThreadGroup;
import org.perfectday.logicengine.core.configuration.Configuration;

/**
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class Main implements Runnable {

    static {
        System.out.println("PERFECTDAY 0.9.1");
        System.out.println("::" + new File(".").getAbsolutePath());
        Properties logsProperties = new Properties();
        //load log4j properties
        try {
            logsProperties.load(Main.class.getClassLoader().getResourceAsStream("assets/log4j.properties"));
            PropertyConfigurator.configure(logsProperties);
            LogManager.getLogger(Main.class).info("LOGGING OK");
        } catch (IOException ex) {
            System.out.println("No log configurado");
        }

        //TEST
        String quest="defensa";
        System.out.println(
            PDMinisDatabase.class.getResourceAsStream(Configuration.QUEST_PATH+
                            Configuration.QUEST_TOKEN_SEPARATOR+quest+"/icon_"+quest+".jpeg")==null);
        System.out.println(PDMinisDatabase.class.getResourceAsStream("/data/minis/minis/simplesoldier.properties"));
    }

    public static String userInit = new String("");
    public static String passInit = new String("");

    private static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String args[]) throws Exception {
        Main main = new Main();
        if (args.length == 2) {
            Main.userInit = args[0];
            Main.passInit = args[1];
        }
        Thread t = new Thread(DashBoardThreadGroup.getInstance(), main, "DashBoard-Main");
        t.start();
        t.join();
    }

    public Main() {
    }

    @Override
    public void run() {

        Logger.getLogger(Main.class).info("LOADING PERFECTDAY");
        new MainMenu().PDshow();
    }

}
