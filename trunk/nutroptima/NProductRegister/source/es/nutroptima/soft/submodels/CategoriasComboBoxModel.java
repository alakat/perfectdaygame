/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.nutroptima.soft.submodels;

import es.nutroptima.soft.model.Categoria;
import es.nutroptima.soft.model.factories.CategoriasFactory;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author Miguel Angel López Montellano <mlopez@nutroptima.es>
 */
public class CategoriasComboBoxModel implements ComboBoxModel   {

    private List<Categoria> categorias;
    private int index;

    public CategoriasComboBoxModel() {
        categorias = new ArrayList<Categoria>();

        categorias.addAll(CategoriasFactory.getInstance().getCategorias());
        index =0;
    }

    public CategoriasComboBoxModel(String s) {
        categorias = new ArrayList<Categoria>();

        //Para validar
        categorias.add(new Categoria(-1, s));
        categorias.addAll(CategoriasFactory.getInstance().getCategorias());
        
        index =0;
    }

    public CategoriasComboBoxModel(int idx) {
        categorias = new ArrayList<Categoria>();
        categorias.addAll(CategoriasFactory.getInstance().getCategorias());
        index =idx;
    }

    public CategoriasComboBoxModel(Categoria c) {
        categorias = new ArrayList<Categoria>();
        categorias.addAll(CategoriasFactory.getInstance().getCategorias());
        index =this.categorias.indexOf(c);
    }


    public void setSelectedItem(Object o) {

        this.index = this.categorias.indexOf(o);
    }

    public Object getSelectedItem() {
        if(index <0)
            return this.categorias.get(0);
        return this.categorias.get(index);
    }

    public int getSize() {
        return categorias.size();
    }

    public Object getElementAt(int i) {
        return categorias.get(i);
    }

    public void addListDataListener(ListDataListener ll) {
        //NO USO
    }

    public void removeListDataListener(ListDataListener ll) {
        //NO USO
    }

    @Override
    public String toString() {
       return this.getSelectedItem().toString();
    }



}
