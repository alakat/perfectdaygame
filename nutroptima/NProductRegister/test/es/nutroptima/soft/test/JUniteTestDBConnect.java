/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.nutroptima.soft.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import es.nutroptima.soft.database.NConnector;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Miguel Angel López Montellano <mlopez@nutroptima.es>
 */
public class JUniteTestDBConnect {

    public JUniteTestDBConnect() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testConnect() {
        try {
            Logger.getLogger(JUniteTestDBConnect.class.getName()).info("INI");
            Connection conn = NConnector.getInstance().getConn();
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("select * from test;");
            while (rs.next()) {
              System.out.println("name = " + rs.getString("name"));
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JUniteTestDBConnect.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(JUniteTestDBConnect.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());

        }

    }

}