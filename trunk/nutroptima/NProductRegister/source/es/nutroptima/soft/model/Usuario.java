/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.nutroptima.soft.model;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
public class Usuario implements TableModel {
    private int id;
    private String login;
    private String pass;
    private List<Producto> productos;
    private List<TableModelListener> listeners = new ArrayList<TableModelListener>();

    public Usuario(int id, String login, String pass) {
        this.id = id;
        this.login = login;
        this.pass = pass;
    }

    public Usuario() {
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
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * @param pass the pass to set
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public int getRowCount() {
        return this.productos.size();
    }

    public int getColumnCount() {
        return 8;
    }

    public String getColumnName(int i) {
        String[] names = {"id","Nombre","KiloCalorias","H.Carbono","Grasas","Proteinas","Categoria","SALVADO"};
        return names[i];
    }

    public Class<?> getColumnClass(int i) {
        if(i<6)
            return String.class;
        else if(i==6)
            return Categoria.class;
        else
            return Boolean.class;
    }

    public boolean isCellEditable(int i, int i1) {
        return (i1>=1) &&(i1<=6);
    }

    public Object getValueAt(int i, int i1) {
        try {
            String[] names = {"getId", "getTitulo", "getKilocalorias", "getHidratosCarbono", "getGrasas", "getProteinas","getCategoria","isActualizado"};
            Method m = Producto.class.getMethod(names[i1], null);
            Object o = this.productos.get(i);
            Object r = m.invoke(o, null);
            if(i1==7){
               Boolean b = (Boolean) r;
               boolean value = !(b.booleanValue());
               return new Boolean(value);
            }

            return r;
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            return "ERROR";
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            return "ERROR";
        } catch (InvocationTargetException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            return "ERROR";
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            return "ERROR";
        } catch (SecurityException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            return "ERROR";
        }
    }

    public void setValueAt(Object o, int i, int i1) {
        Producto p = this.getProductos().get(i);
        String s=null;
        switch(i1){
            case 1:
                p.setTitulo(o.toString());break;
            case 2:
                s = o.toString();
                try {
                    double d = Double.parseDouble(s);
                    p.setKilocalorias(d);
                } catch (Exception e) {
                }
                break;

            case 3:
                s = o.toString();
                try {
                    double d = Double.parseDouble(s);
                    p.setHidratosCarbono(d);
                } catch (Exception e) {
                }
                break;
            case 4:
                s = o.toString();
                try {
                    double d = Double.parseDouble(s);
                    p.setGrasas(d);
                } catch (Exception e) {
                }
                break;
            case 5:
                s = o.toString();
                try {
                    double d = Double.parseDouble(s);
                    p.setProteinas(d);
                } catch (Exception e) {
                }
                break;
            case 6:
                p.setCategoria((Categoria) o);
                break;
        }
        TableModelEvent e =  new TableModelEvent(this,TableModelEvent.UPDATE);
        for (TableModelListener tableModelListener : this.listeners) {
            tableModelListener.tableChanged(e);
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

    
}
