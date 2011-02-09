/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.nutroptima.soft.model;

import es.nutroptima.soft.database.NConnector;
import es.nutroptima.soft.model.factories.ItemsFactory;
import es.nutroptima.soft.model.factories.ProductoFactory;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Miguel Angel López Montellano <mlopez@nutroptima.es>
 */
public class Producto implements TableModel {

    public static final int ID_NOT_VALID = -1;
    private int id;
    private String titulo;
    private double hidratosCarbono;
    private double kilocalorias;
    private double proteinas;
    private double grasas;
    private Categoria categoria;
    private Usuario usuario;
    private List<MyVItem> items;


    public Producto(Usuario u) {
        this.id = ID_NOT_VALID;
        this.usuario = u;
    }

    public Producto(int id, String titulo, double hidratosCarbono, double kilocalorias, double proteinas, double grasas, Categoria categoria, Usuario usuario) {
        this.id = id;
        this.titulo = titulo;
        this.hidratosCarbono = hidratosCarbono;
        this.kilocalorias = kilocalorias;
        this.proteinas = proteinas;
        this.grasas = grasas;
        this.categoria = categoria;
        this.usuario = usuario;
    }

    

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
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the hidratosCarbono
     */
    public double getHidratosCarbono() {
        return hidratosCarbono;
    }

    /**
     * @param hidratosCarbono the hidratosCarbono to set
     */
    public void setHidratosCarbono(double hidratosCarbono) {
        this.hidratosCarbono = hidratosCarbono;
    }

    /**
     * @return the kilocalorias
     */
    public double getKilocalorias() {
        return kilocalorias;
    }

    /**
     * @param kilocalorias the kilocalorias to set
     */
    public void setKilocalorias(double kilocalorias) {
        this.kilocalorias = kilocalorias;
    }

    /**
     * @return the proteinas
     */
    public double getProteinas() {
        return proteinas;
    }

    /**
     * @param proteinas the proteinas to set
     */
    public void setProteinas(double proteinas) {
        this.proteinas = proteinas;
    }

    /**
     * @return the grasas
     */
    public double getGrasas() {
        return grasas;
    }

    /**
     * @param grasas the grasas to set
     */
    public void setGrasas(double grasas) {
        this.grasas = grasas;
    }

    /**
     * @return the categoria
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


    public boolean isAlrreadySave(){
        return this.id != ID_NOT_VALID;
    }

    private void insertThis() throws ClassNotFoundException, SQLException{
        Logger.getLogger(Producto.class.getName()).info("insert a product");
        int id = ProductoFactory.getInstance().getNextProductID();
        NConnector.getInstance().makePreparedStatement(this, id).execute();

    }

    private void updateThis() throws ClassNotFoundException, SQLException{
        Logger.getLogger(Producto.class.getName()).info("update a product");
        NConnector.getInstance().makeUpdateStatement(this).execute();
    }

    public void save() throws ClassNotFoundException, SQLException{

        Logger.getLogger(Producto.class.getName()).info("save a product");
        if(isAlrreadySave()){
            this.updateThis();
        }else{
            this.insertThis();
        }
    }

    public void delete() throws ClassNotFoundException, SQLException{
        if(isAlrreadySave()){
            Logger.getLogger(Producto.class.getName()).info("delete a product");
            NConnector.getInstance().makeDeleteStatement(this).execute();
            this.id=ID_NOT_VALID;
        }
    }

    public void setItems(List<MyVItem> items) {
        this.items = items;
    }

    public List<MyVItem> getItems() throws ClassNotFoundException, SQLException {
        if(items==null)
            ItemsFactory.getInstance().loadItems(this);
        return items;
    }

    public int getRowCount() {
        try {
            return this.getItems().size();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public int getColumnCount() {
       return 3;
    }

    public String getColumnName(int i) {
        String[] names =  {"Titulo","Cantidad","Unidad"};
        return names[i];
    }

    public Class<?> getColumnClass(int i) {
        return String.class;
    }

    public boolean isCellEditable(int i, int i1) {
        //todo change
        return false;
    }

    public Object getValueAt(int i, int i1) {
        try {
            MyVItem item = this.getItems().get(i);
            switch (i1) {
                case 0:
                    return item.getTitulo().getTitulo();
                case 1:
                    return item.getCantidad();
                case 2:
                    return item.getUnidad().getTitulo();
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "ERROR";
    }

    public void setValueAt(Object o, int i, int i1) {
    }

    public void addTableModelListener(TableModelListener tl) {
    }

    public void removeTableModelListener(TableModelListener tl) {
    }


}
