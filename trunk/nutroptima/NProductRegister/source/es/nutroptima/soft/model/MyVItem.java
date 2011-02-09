/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.nutroptima.soft.model;

/**
 *
 * @author Miguel Angel López Montellano <mlopez@nutroptima.es>
 */
public class MyVItem  {
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
    }

    

}
