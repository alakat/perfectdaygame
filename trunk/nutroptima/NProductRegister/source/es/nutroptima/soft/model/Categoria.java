/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.nutroptima.soft.model;

/**
 *
 * @author Miguel Angel López Montellano <mlopez@nutroptima.es>
 */
public class Categoria {

    private int id;
    private String titulo;

    public Categoria(int id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    public Categoria() {
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    
}
