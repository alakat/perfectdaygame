/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.nutroptima.soft.model.factories;

import es.nutroptima.soft.database.NConnector;
import es.nutroptima.soft.model.Producto;
import es.nutroptima.soft.model.Usuario;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Miguel Angel López Montellano <mlopez@nutroptima.es>
 */
public class ProductoFactory {

    private static final ProductoFactory instance = new ProductoFactory();

    public ProductoFactory getInstance(){
        return instance;
    }

    public List<Producto> getProductosByUsuario(Usuario usuario) throws ClassNotFoundException, SQLException{
        List<Producto> productos = new ArrayList<Producto>();

        Logger.getLogger(ProductoFactory.class.getName()).info("INI");
        Connection conn = NConnector.getInstance().getConn();
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("select * from producto where idUsuario="+usuario.getId()+";");
        while (rs.next()) {
          Producto p = new Producto();
          p.setId(rs.getInt(0));
          p.setTitulo(rs.getString(1));
          p.setHidratosCarbono(rs.getDouble(2));
          p.setKilocalorias(rs.getDouble(3));
          p.setProteinas(rs.getDouble(4));
          p.setGrasas(rs.getDouble(5));
          //TODO cargar categoria
        }
        return productos;
    }

}
