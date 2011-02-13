/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.nutroptima.soft.model;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        return 6;
    }

    public String getColumnName(int i) {
        String[] names = {"id","Nombre","KiloCalorias","H.Carbono","Grasas","Proteinas"};
        return names[i];
    }

    public Class<?> getColumnClass(int i) {
        return "".getClass();
    }

    public boolean isCellEditable(int i, int i1) {
        return i1>=1;
    }

    public Object getValueAt(int i, int i1) {
        try {
            String[] names = {"getId", "getTitulo", "getKilocalorias", "getHidratosCarbono", "getGrasas", "getProteinas"};
            Method m = Producto.class.getMethod(names[i1], null);
            Object o = this.productos.get(i);
            Object r = m.invoke(o, null);

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
        }
        
    }

    public void addTableModelListener(TableModelListener tl) {
        //TODO
    }

    public void removeTableModelListener(TableModelListener tl) {
        //TODO
    }

    
}
