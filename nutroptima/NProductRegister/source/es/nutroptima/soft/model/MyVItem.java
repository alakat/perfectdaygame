/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.nutroptima.soft.model;

import es.nutroptima.soft.database.NConnector;
import es.nutroptima.soft.model.factories.ItemsFactory;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Miguel Angel López Montellano <mlopez@nutroptima.es>
 */
public class MyVItem extends ObjectoActualizable {
    public static final int ID_NOT_VALID = -1;
    private MyVTitulo titulo;
    private UnidadPeso unidad;
    private double cantidad;
    private Producto producto;

   

    /**
     * @return the titulo
     */
    public MyVTitulo getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(MyVTitulo titulo) {
        this.titulo = titulo;
        this.setActualizado(true);
    }

    /**
     * @return the unidad
     */
    public UnidadPeso getUnidad() {
        return unidad;
    }

    /**
     * @param unidad the unidad to set
     */
    public void setUnidad(UnidadPeso unidad) {
        this.unidad = unidad;
        this.setActualizado(true);
    }

    /**
     * @return the cantidad
     */
    public double getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
        this.setActualizado(true);
    }

    /**
     * @return the producto
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * @param producto the producto to set
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
        this.setActualizado(true);
    }

    public MyVItem(int id, MyVTitulo titulo, UnidadPeso unidad, double cantidad, Producto producto) {
        super(id);
        this.titulo = titulo;
        this.unidad = unidad;
        this.cantidad = cantidad;
        this.producto = producto;
    }

    public MyVItem( Producto p) {
        super(ID_NOT_VALID);
        this.producto = p;
        this.setActualizado(true);
    }



    public boolean isAlrreadySave(){
        return this.id != ID_NOT_VALID;
    }

    private void insertThis() throws ClassNotFoundException, SQLException{
        int id_ =ItemsFactory.getInstance().nextID();
        Logger.getLogger(Producto.class.getName()).info("insert a product");
        PreparedStatement s =  NConnector.getInstance().makeInsertNewItem(this,id_);
        s.addBatch();
        s.executeBatch();
        NConnector.getInstance().commit();
        this.setId(id_);
        this.setActualizado(false);
    }

    private void updateThis() throws ClassNotFoundException, SQLException{
        Logger.getLogger(Producto.class.getName()).info("update a product");
        PreparedStatement s=  NConnector.getInstance().makeUpdateStatement(this);
        s.addBatch();
        s.executeBatch();
        NConnector.getInstance().commit();
        this.setActualizado(false);
    }

    public void delete() throws ClassNotFoundException, SQLException{
        if(isAlrreadySave()){
            Logger.getLogger(Producto.class.getName()).info("delete a product");
            PreparedStatement s =  NConnector.getInstance().makeDeleteStatement(this);
            this.id=ID_NOT_VALID;
            s.addBatch();
            s.executeBatch();
            NConnector.getInstance().commit();
            this.setActualizado(false);
        }else{
            Logger.getLogger(MyVItem.class.getName()).log(Level.SEVERE, "¡ELIMINANDO OBJETO NO PERSISTIDO");
        }
    }


    public void save() throws ClassNotFoundException, SQLException{

        Logger.getLogger(Producto.class.getName()).info("save a product");
        if(isAlrreadySave()){
            this.updateThis();
        }else{
            this.insertThis();
        }
    }
    

}
