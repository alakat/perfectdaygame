/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.nutroptima.soft.model.factories;

import es.nutroptima.soft.database.NConnector;
import es.nutroptima.soft.model.Pais;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

/**
 *
 * @author Sergio Álvarez López <salvarez@nutroptima.es>
 */
public class PaisFactory {

    private static final PaisFactory instance = new PaisFactory();

    public Pais getPais(int id) throws ClassNotFoundException, SQLException{

        Logger.getLogger(PaisFactory.class.getName()).info("Load productos del usuario");
        Connection conn = NConnector.getInstance().getConn();
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("select * from pais where id="+id);
        while(rs.next()){
            Pais thepais = new Pais();
            thepais.setId(rs.getInt("id"));
            thepais.setNombre(rs.getString("nombre"));
            return thepais;
        }
        return null;
    }


    /**
     * @return the instance
     */
    public static PaisFactory getInstance() {
        return instance;
    }


    
}
