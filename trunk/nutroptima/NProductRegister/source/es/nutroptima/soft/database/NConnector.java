/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.nutroptima.soft.database;

import es.nutroptima.soft.model.Producto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Miguel Angel López Montellano <mlopez@nutroptima.es>
 */
public class NConnector {

    private static final String BASE_DE_DATOS = "./nutroptima.db";

    private static NConnector instance;
    private Connection conn;
    private PreparedStatement insertNewProduct;
    private PreparedStatement updateProduct;
    private PreparedStatement deleteProduct;
    private PreparedStatement insertNewItem;
    private PreparedStatement updateItem;
    private PreparedStatement deleteItem;


    public NConnector() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        this.conn = DriverManager.getConnection("jdbc:sqlite:"+BASE_DE_DATOS);
        //create table productos(id int primary key,titulo varchar(255),  hidratos_carbono ,kilocalorias , proteinas ,
        //grasas double, idCategoria int, idUsuario int);
        this.insertNewProduct = this.conn.prepareStatement("insert into productos values( ?,?,?,?,?,?,?,?);");
        this.updateProduct = this.conn.prepareStatement("update productos set titulo=?, "
                + "hidratos_carbono=?, kilocalorias=?,proteinas=?,grasas=?,idCategoria=? where id=?");
        this.deleteProduct = this.conn.prepareStatement("Delete from productos where id=?;");
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

    /**
     * Prepara una consulta para salvar un producto nuevo
     * @param p
     * @param nextId
     * @return
     * @throws SQLException
     */
    public PreparedStatement makePreparedStatement(Producto p, int nextId) throws SQLException{
        this.insertNewProduct.setInt(1,nextId );
        this.insertNewProduct.setString(2, p.getTitulo());
        this.insertNewProduct.setDouble(3, p.getHidratosCarbono());
        this.insertNewProduct.setDouble(4, p.getKilocalorias());
        this.insertNewProduct.setDouble(5, p.getProteinas());
        this.insertNewProduct.setDouble(6, p.getGrasas());
        this.insertNewProduct.setInt(7, p.getCategoria().getId());
        this.insertNewProduct.setInt(8, p.getUsuario().getId());
        return insertNewProduct;
    }


    /**
     * Prepara una consulta para actualizar un registro de productos
     * @param p
     * @return
     * @throws SQLException
     */
    public PreparedStatement makeUpdateStatement(Producto p) throws SQLException{

        this.updateProduct.setString(1, p.getTitulo());
        this.updateProduct.setDouble(2, p.getHidratosCarbono());
        this.updateProduct.setDouble(3, p.getKilocalorias());
        this.updateProduct.setDouble(4, p.getProteinas());
        this.updateProduct.setDouble(5, p.getGrasas());
        this.updateProduct.setInt(6, p.getCategoria().getId());
        this.updateProduct.setInt(7, p.getId());
        return updateProduct;
    }


    /**
     * Consulta de eliminación
     * @param p
     * @return
     */
    public PreparedStatement makeDeleteStatement(Producto p) throws SQLException{
        this.deleteProduct.setInt(1, p.getId());
        return deleteProduct;
    }


}
