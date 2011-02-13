/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.nutroptima.soft.model;

import es.nutroptima.soft.database.NConnector;
import es.nutroptima.soft.model.factories.ItemsFactory;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Miguel Angel López Montellano <mlopez@nutroptima.es>
 */
public class MyVItem {
    public static final int ID_NOT_VALID = -1;
    private int id;
    private MyVTitulo titulo;
    private UnidadPeso unidad;
    private double cantidad;
    private Producto producto;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

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
    }

    public MyVItem(int id, MyVTitulo titulo, UnidadPeso unidad, double cantidad, Producto producto) {
        this.id = id;
        this.titulo = titulo;
        this.unidad = unidad;
        this.cantidad = cantidad;
        this.producto = producto;
    }

    public MyVItem( Producto p) {
        this.producto = p;
        this.id = ID_NOT_VALID;
    }



    public boolean isAlrreadySave(){
        return this.id != ID_NOT_VALID;
    }

    private void insertThis() throws ClassNotFoundException, SQLException{
        int id_ =ItemsFactory.getInstance().nextID();
        Logger.getLogger(Producto.class.getName()).info("insert a product");
        NConnector.getInstance().makeInsertNewItem(this,id_).execute();
        this.setId(id_);

    }

    private void updateThis() throws ClassNotFoundException, SQLException{
        Logger.getLogger(Producto.class.getName()).info("update a product");
        NConnector.getInstance().makeUpdateStatement(this).execute();
    }

    public void delete() throws ClassNotFoundException, SQLException{
        if(isAlrreadySave()){
            Logger.getLogger(Producto.class.getName()).info("delete a product");
            NConnector.getInstance().makeDeleteStatement(this).execute();
            this.id=ID_NOT_VALID;
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
