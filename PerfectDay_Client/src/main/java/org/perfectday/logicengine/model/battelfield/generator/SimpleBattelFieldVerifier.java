/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.logicengine.model.battelfield.generator;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.perfectday.logicengine.model.battelfield.Field;
import org.perfectday.logicengine.model.battelfield.TypeField;

/**
 * Clase responsable de asegurarnos que no existe parte del terreno inaccesibles.
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class SimpleBattelFieldVerifier {

    private Field[][] battelField;
    private int xLen;
    private int yLen;
    private List<Field> verificados;

    public SimpleBattelFieldVerifier(Field[][] battelField) {
        this.battelField = battelField;
        xLen=this.battelField.length;
        yLen=this.battelField[0].length;
        this.verificados = new ArrayList<Field>();
    }
    
    /**
     * Verifica y corrige el Battelfield
     */
    public void verifier(){        
        for(int i =0; i<xLen;i++){
            for (int j = 0; j < yLen; j++) {
                //SI esta bloqueado y no verificado
                if(this.battelField[i][j].isBlock() 
                        && (!this.verificados.contains(this.battelField[i][j]))){
                    List<Field> fblocked = new ArrayList<Field>();
                    fblocked.add(this.battelField[i][j]);
                    verfier(fblocked);
                }
            }
        }
        
    }

    private List<Field> getVecinos(List<Field> fblocked) {
        List<Field> fvecinos = new ArrayList<Field>();
        int i = 0;
        int j=0;        
        for(;i<xLen;i++){
            for(;j<yLen;j++){
                Field f = battelField[i][j];
                if(fblocked.contains(f)){
                    fvecinos.addAll(getVecinos(i, j));
                }
            }
        }
        for(Field f: fblocked){
            if(fvecinos.contains(f))
                fvecinos.remove(f);
        }
        return fvecinos;
    }

    /**
     * Devuelve la lista de los vecinos bloqueantes
     * @param i
     * @param j
     * @return
     */
    private List<Field> getVecinos(int i, int j) {
        List<Field> fields = new ArrayList<Field>();
        
        if((i-1>0) && (j-1>0) && (this.battelField[i-1][j-1].isBlock()))
            fields.add(this.battelField[i-1][j-1]);
        if((i-1>0) && (j>0) && (this.battelField[i-1][j].isBlock()))
            fields.add(this.battelField[i-1][j]);
        if((i-1>0) && (j+1<yLen) && (this.battelField[i-1][j+1].isBlock()))
            fields.add(this.battelField[i-1][j+1]);
        if((i>0) && (j+1<yLen) && (this.battelField[i][j+1].isBlock()))
            fields.add(this.battelField[i][j+1]);
        if((i>0) && (j-1>0) && (this.battelField[i][j-1].isBlock()))
            fields.add(this.battelField[i][j-1]);
        if((i+1<xLen) && (j-1>0) && (this.battelField[i+1][j-1].isBlock()))
            fields.add(this.battelField[i+1][j-1]);
        if((i+1<xLen) && (j>0) && (this.battelField[i+1][j].isBlock()))
            fields.add(this.battelField[i+1][j]);
        if((i+1<xLen) && (j+1<yLen) && (this.battelField[i+1][j+1].isBlock()))
            fields.add(this.battelField[i+1][j+1]);       
        return fields;
    }

    private void verfier(List<Field> fblocked) {
        List<Field> vecinos = getVecinos(fblocked);

        for(int i=0;i<xLen;i++){
            for(int j=0;j<yLen;j++){
                if(vecinos.contains(this.battelField[i][j])){
                    //Por cada vecino
                    List<Field> fv = getVecinos(i, j);
                    int v=0;
                    for (Field field : fv) {
                        if(fblocked.contains(field)){
                            v++;
                        }
                    }
                    if(v>=2){
                        this.battelField[i][j].setTypeField(TypeField.GRASS);
                        Logger.getLogger(SimpleBattelFieldVerifier.class).info("Cambiamos a "+this.battelField[i][j]);
                        return;
                    }
                }
            }
        }

        for (Field field : vecinos) {
            this.verificados.add(field);
            fblocked.add(field);
            verfier(fblocked);
//            if(fblocked.contains(field))
//                fblocked.remove(field);                              

        }
    }

   
    
}
