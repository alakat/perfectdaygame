/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.nutroptima.soft.model.factories;

import es.nutroptima.soft.database.NConnector;
import es.nutroptima.soft.model.Categoria;
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
public class CategoriasFactory {
    private List<Categoria> categorias;
    private static final CategoriasFactory instance = new CategoriasFactory();

    public CategoriasFactory() {
        categorias = new ArrayList<Categoria>();
        try {
            Logger.getLogger(CategoriasFactory.class.getName()).info("Load todas las categorias");
            Connection conn = NConnector.getInstance().getConn();
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("select * from categoria;");
            while (rs.next()) {
                Categoria c = new Categoria(rs.getInt("id"),rs.getString("titulo"));
                categorias.add(c);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CategoriasFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CategoriasFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    public static CategoriasFactory getInstance() {
        return instance;
    }

    public List<Categoria> getCategorias(){
        return categorias;
    }


    public Categoria getCategoria(int id){
        for (Categoria categoria : categorias) {
            if (categoria.getId()==id) {
                return categoria;
            }
        }
        return null;
    }
}
