/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.nutroptima.soft.model.factories;

import es.nutroptima.soft.database.NConnector;
import es.nutroptima.soft.model.Usuario;
import es.nutroptima.soft.model.exception.LoginException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

/**
 *
 * @author Miguel Angel López Montellano <mlopez@nutroptima.es>
 */
public class UsuarioFactory {

       private static final UsuarioFactory instance = new UsuarioFactory();

       public static UsuarioFactory getInstance(){
           return instance;
       }

       public Usuario makeUsuario(String login, String pass) throws ClassNotFoundException, SQLException, LoginException{
           Usuario u=null;
            Logger.getLogger(ProductoFactory.class.getName()).info("INI");
            Connection conn = NConnector.getInstance().getConn();
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("select * from usuario where login='"+login+"' and pass='"+pass+"' ;");
            while(rs.next()){
                if(u!=null)
                    throw new LoginException("Usuario no valido, usuario duplicado. Llama a Nutroptima, error en base de datos");
                u = new Usuario(rs.getInt("id"), rs.getString("login"), rs.getString("pass"));
            }
            if(u==null)
                throw new LoginException("No existe usuario con el login y password");
           ProductoFactory.getInstance().getProductosByUsuario(u);
           return u;
       }
}
