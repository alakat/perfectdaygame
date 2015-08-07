/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.perfectday.dashboard;

import es.bitsonfire.PDMinisDatabase;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.perfectday.dashboard.gui.MainMenu;
import org.perfectday.dashboard.threads.DashBoardThreadGroup;
import static org.perfectday.logicengine.core.configuration.Configuration.DATA_MINI_PATH;
import org.perfectday.logicengine.core.industry.MiniFactory;
import org.perfectday.logicengine.core.industry.SpellFactory;
import org.perfectday.logicengine.core.industry.SupportFactory;
import org.perfectday.logicengine.core.industry.WeaponsFactory;

/**
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class Main implements Runnable {

    static {
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
        System.out.println("/data/minis/minis.properties");
        System.out.println(DATA_MINI_PATH+"minis.properties");
        InputStream is2 = PDMinisDatabase.class.getResourceAsStream("/data/minis/minis.properties");
        InputStream is = PDMinisDatabase.class.getResourceAsStream(DATA_MINI_PATH+"minis.properties");
        Properties minis = new Properties();
        try {
            minis.load(is);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Object key : minis.keySet()) {
            String value = minis.getProperty((String)key);
            System.out.println("Value:"+value);
            Properties p1 = new Properties();
            try {
                p1.load(PDMinisDatabase.class.getClassLoader().getResourceAsStream("data/minis/"+value));
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            for (Object key1 : p1.keySet()) {
                System.out.println(key1+","+p1.getProperty((String)key1));
            }
            
        }
        
        
        MiniFactory.getInstance();
        WeaponsFactory.getInstance();
        SupportFactory.getInstance();
        SpellFactory.getInstance();
        
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
        new MainMenu().setVisible(true);
    }

}
