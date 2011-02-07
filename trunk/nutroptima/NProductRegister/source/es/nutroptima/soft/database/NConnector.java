/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.nutroptima.soft.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Miguel Angel López Montellano <mlopez@nutroptima.es>
 */
public class NConnector {

    private static final String BASE_DE_DATOS = "./nutroptima.db";

    private static NConnector instance;
    private Connection conn;

    public NConnector() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        this.conn = DriverManager.getConnection("jdbc:sqlite:"+BASE_DE_DATOS);
    }


    public static NConnector getInstance() throws ClassNotFoundException, SQLException{
        if(instance==null){
            instance = new NConnector();
        }
        return instance;
    }


    public boolean test(){
        return true;
    }

    public Connection getConn() {
        return conn;
    }




}
