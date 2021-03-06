/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.nutroptima.soft.submodels;

import es.nutroptima.soft.model.MyVTitulo;
import es.nutroptima.soft.model.factories.ItemsFactory;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author Miguel Angel López Montellano <mlopez@nutroptima.es>
 */
public class MyVTitulosComboboxModel implements ComboBoxModel {

    private List<MyVTitulo> titulos ;
    private int index;

    public MyVTitulosComboboxModel() {
        titulos = new ArrayList<MyVTitulo>();
        titulos.addAll(ItemsFactory.getInstance().getMyvTitulos());
        this.index = 0;
    }
    public MyVTitulosComboboxModel(int idx) {
        titulos = new ArrayList<MyVTitulo>();
        titulos.addAll(ItemsFactory.getInstance().getMyvTitulos());
        this.index = idx;
    }

    public MyVTitulosComboboxModel(MyVTitulo t) {
        titulos = new ArrayList<MyVTitulo>();
        titulos.addAll(ItemsFactory.getInstance().getMyvTitulos());
        this.index = this.titulos.indexOf(t);
    }

    public void setSelectedItem(Object o) {
         
        this.index = this.titulos.indexOf(o);
    }

    public Object getSelectedItem() {
        if(index <0)
            return this.titulos.get(0);
        return this.titulos.get(index);
    }

    public int getSize() {
        return this.titulos.size();
    }

    public Object getElementAt(int i) {
        return this.titulos.get(i);
    }

    public void addListDataListener(ListDataListener ll) {
       //NO USE
    }

    public void removeListDataListener(ListDataListener ll) {
        //NO USE
    }

    @Override
    public String toString() {
       return this.getSelectedItem().toString();
    }



}
