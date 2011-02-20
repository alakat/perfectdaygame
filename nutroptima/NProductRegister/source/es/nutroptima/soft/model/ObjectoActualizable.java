/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.nutroptima.soft.model;

/**
 *
 * @author Miguel Angel López
 */
public class ObjectoActualizable {
    public static final int ID_NOT_VALID = -1;
    protected int id;
    protected boolean actualizado;

    public ObjectoActualizable(int id) {
        this.id = id;
        this.actualizado = false;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean isActualizado() {
        return actualizado;
    }

    public void setActualizado(boolean actualizado) {
        this.actualizado = actualizado;
    }

    

}
