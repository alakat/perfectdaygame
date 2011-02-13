/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.nutroptima.soft.model;

/**
 *
 * @author Miguel Angel López Montellano <mlopez@nutroptima.es>
 */
public class MyVTitulo {

    private int id;
    private String titulo;

    public MyVTitulo() {
    }

    public MyVTitulo(int id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    @Override
    public String toString() {
        return titulo;
    }

    
}
