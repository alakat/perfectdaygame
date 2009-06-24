/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.dashboard.gui.model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.apache.log4j.Logger;
import org.perfectday.gamebuilder.model.MiniDescription;
import org.perfectday.logicengine.model.minis.MiniLevel;

/**
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class ArmyTableModel extends AbstractTableModel {
    private static final Logger logger = Logger.getLogger(ArmyTableModel.class);

    private String[] columnNames = {"Tipo de Soldado","Level del Soldado","Coste* "};
    private List<Object[]> data = new ArrayList<Object[]>();;

    public ArmyTableModel() {

    }

    public List<MiniDescription> generateArmyList() {
        ArrayList result = new ArrayList<MiniDescription>();
        for (Object[] objects : data) {
            MiniDescription mini = new MiniDescription(((MiniDescription) objects[0]).getMini(),0,"" ); 
            mini.setLevel((MiniLevel) objects[1]);
            result.add(mini);
        }
        return result;
    }

        public List<Object[]> getData() {
            return data;
        }
        
        
        public ArmyTableModel(ArmyTableModel btm){
            for(Object[] obj: btm.getData())
                this.data.add(obj);
        }
        
        public int getRowCount() {
            return data.size();
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            return data.get(rowIndex)[columnIndex];
        }
        
        public void addRow(MiniDescription mini,MiniLevel level, Double cost){
            Object[] obj = {mini,level,cost};
            data.add(obj);
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            data.get(rowIndex)[columnIndex]=aValue;
        }
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        public void remove(int i){
            data.remove(i);
        }
        
        
        public String getColumnName(int col) {
         return columnNames[col];
        }
        
        public double getTotalCost(){
            double c=0;
            for(Object[] obj:data)
                c+=((Double)obj[2]).doubleValue();
            return c;
        }
}
