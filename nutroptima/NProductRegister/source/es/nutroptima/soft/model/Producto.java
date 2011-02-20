/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.nutroptima.soft.model;

import es.nutroptima.soft.database.NConnector;
import es.nutroptima.soft.model.factories.ItemsFactory;
import es.nutroptima.soft.model.factories.PaisFactory;
import es.nutroptima.soft.model.factories.ProductoFactory;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Miguel Angel López Montellano <mlopez@nutroptima.es>
 */
public class Producto extends ObjectoActualizable implements TableModel {

    public static final int ID_SPAIN = 0;
    private String titulo;
    private double hidratosCarbono;
    private double kilocalorias;
    private double proteinas;
    private double grasas;
    private Categoria categoria;
    private Pais pais;
    private Usuario usuario;
    private List<MyVItem> items;
    private List<TableModelListener> listeners = new ArrayList<TableModelListener>();

    public Producto(Usuario u) throws ClassNotFoundException, SQLException {
        super(ID_NOT_VALID);
        this.usuario = u;
        this.pais = PaisFactory.getInstance().getPais(ID_SPAIN);

    }

    public Producto(int id, String titulo, double hidratosCarbono, double kilocalorias, double proteinas, double grasas, Categoria categoria, Pais pais, Usuario usuario) {
        super(id);
        this.titulo = titulo;
        this.hidratosCarbono = hidratosCarbono;
        this.kilocalorias = kilocalorias;
        this.proteinas = proteinas;
        this.grasas = grasas;
        this.categoria = categoria;
        this.pais = pais;
        this.usuario = usuario;
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
        this.setActualizado(true);
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
        this.setActualizado(true);
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
        this.setActualizado(true);
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
        this.setActualizado(true);
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
        this.setActualizado(true);
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
        this.setActualizado(true);
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

    public boolean isAlrreadySave() {
        return this.id != ID_NOT_VALID;
    }

    private void insertThis() throws ClassNotFoundException, SQLException {
        Logger.getLogger(Producto.class.getName()).info("insert a product");
        int id = ProductoFactory.getInstance().getNextProductID();
        PreparedStatement s = NConnector.getInstance().makePreparedStatement(this, id);
        s.addBatch();
        s.executeBatch();
        NConnector.getInstance().commit();
        this.setId(id);
        this.setActualizado(false);

    }

    private void updateThis() throws ClassNotFoundException, SQLException {
        Logger.getLogger(Producto.class.getName()).info("update a product");
        PreparedStatement s = NConnector.getInstance().makeUpdateStatement(this);
        s.addBatch();
        s.executeBatch();
        NConnector.getInstance().commit();
        this.setActualizado(false);
    }

    public void save() throws ClassNotFoundException, SQLException {

        Logger.getLogger(Producto.class.getName()).info("save a product");
        if (isAlrreadySave()) {
            this.updateThis();
        } else {
            this.insertThis();
        }
    }

    public void delete() throws ClassNotFoundException, SQLException {
        if (isAlrreadySave()) {
            Logger.getLogger(Producto.class.getName()).info("delete a product");
            PreparedStatement s = NConnector.getInstance().makeDeleteStatement(this);
            this.id = ID_NOT_VALID;
            s.addBatch();
            s.executeBatch();
            NConnector.getInstance().commit();
            this.setActualizado(false);
        }
    }

    public void setItems(List<MyVItem> items) {
        this.items = items;
    }

    public List<MyVItem> getItems() throws ClassNotFoundException, SQLException {
        if (items == null) {
            ItemsFactory.getInstance().loadItems(this);
        }
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
        return 4;
    }

    public String getColumnName(int i) {
        String[] names = {"Titulo", "Cantidad", "Unidad", "SALVADO"};
        return names[i];
    }

    public Class<?> getColumnClass(int i) {
        switch (i) {
            case 0:
                return MyVTitulo.class;

            case 1:
                return String.class;
            case 2:
                return UnidadPeso.class;
            case 3:
                return Boolean.class;
        }
        return String.class;
    }

    public boolean isCellEditable(int i, int i1) {
        //todo change
        return i1 < 3;
    }

    public Object getValueAt(int i, int i1) {
        try {
            MyVItem item = this.getItems().get(i);
            switch (i1) {
                case 0:
                    return item.getTitulo();
                case 1:
                    return item.getCantidad();
                case 2:
                    return item.getUnidad();
                case 3:
                    return new Boolean(!item.isActualizado());
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "ERROR";
    }

    public void setValueAt(Object o, int i, int i1) {
        try {
            MyVItem item = this.getItems().get(i);
            switch (i1) {
                case 0:
                    item.setTitulo((MyVTitulo) o);
                    break;
                case 1:
                    try {
                        double d = Double.parseDouble(o.toString());
                        item.setCantidad(d);
                    } catch (NumberFormatException ex) {
                    }
                    break;

                case 2:
                    item.setUnidad((UnidadPeso) o);
                    break;
            }
            TableModelEvent e = new TableModelEvent(this, TableModelEvent.UPDATE);
            for (TableModelListener tableModelListener : this.listeners) {
                tableModelListener.tableChanged(e);
            }
        } catch (Exception ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, "ERROR EN CONTROL DE micronutrientes (tablemodel):" + ex);
        }
    }

    public void addTableModelListener(TableModelListener tl) {
        //TODO
        listeners.add(tl);
    }

    public void removeTableModelListener(TableModelListener tl) {
        //TODO
        listeners.remove(tl);
    }

    /**
     * @return the pais
     */
    public Pais getPais() {
        return pais;
    }

    /**
     * @param pais the pais to set
     */
    public void setPais(Pais pais) {
        this.pais = pais;
    }
}
