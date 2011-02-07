/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.nutroptima.soft.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Miguel Angel López Montellano <mlopez@nutroptima.es>
 */
public class Producto {

    private int id;
    private String titulo;
    private double hidratosCarbono;
    private double kilocalorias;
    private double proteinas;
    private double grasas;
    private Categoria categoria;
    private Usuario usuario;
    private List<MyVItem> items;

    public Producto() {
        this.items = new ArrayList<MyVItem>();
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
        this.items = new ArrayList<MyVItem>();
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


}
