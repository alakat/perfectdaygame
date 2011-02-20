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

    public static ProductoFactory getInstance(){
        return instance;
    }

    public List<Producto> getProductosByUsuario(Usuario usuario) throws ClassNotFoundException, SQLException{
        List<Producto> productos = new ArrayList<Producto>();

        Logger.getLogger(ProductoFactory.class.getName()).info("Load productos del usuario");
        Connection conn = NConnector.getInstance().getConn();
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("select * from productos where idUsuario="+usuario.getId()+" ORDER BY titulo desc ;");
        while (rs.next()) {
          Producto p = new Producto(usuario);
          p.setId(rs.getInt(1));
          p.setTitulo(rs.getString(2));
          p.setHidratosCarbono(rs.getDouble(3));
          p.setKilocalorias(rs.getDouble(4));
          p.setProteinas(rs.getDouble(5));
          p.setGrasas(rs.getDouble(6));
          p.setCategoria(CategoriasFactory.getInstance().getCategoria(rs.getInt(7)));
          p.setActualizado(false);
          productos.add(p);
        }
        usuario.setProductos(productos);
        return productos;
    }


    /**
     *  Obtiene el siguiente id para salvar
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public int getNextProductID() throws ClassNotFoundException, SQLException{
        Logger.getLogger(ProductoFactory.class.getName()).info("Load next id");
        Connection conn = NConnector.getInstance().getConn();
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("select max(id) from productos ;");
        int x = rs.getInt(1);
        return x+1;
    }

}
