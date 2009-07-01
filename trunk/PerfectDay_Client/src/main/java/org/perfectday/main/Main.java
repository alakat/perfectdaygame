/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.perfectday.main;

import java.util.List;
import org.perfectday.logicengine.core.MasterReferenceObject;
import org.perfectday.logicengine.model.battelfield.Field;
import org.perfectday.logicengine.model.battelfield.TypeField;
import org.perfectday.logicengine.model.battelfield.generator.SimpleMapGenerator;

/**
 *
 * @author Miguel Angel Lopez Montellano ( alakat@gmail.com )
 */
public class Main {


//    public static void main(String[] argvs){
//
//        SimpleMapGenerator simpleMapGenerator = new SimpleMapGenerator(13,13);
////        System.out.println("Go...");
//        long t0 = System.currentTimeMillis();
//        simpleMapGenerator.generateBattelField();
//        long t1 = System.currentTimeMillis();
////        System.out.println("end ["+(t1-t0)+"ms]");
//        Field[][] fields = simpleMapGenerator.getBattelfield();
//        for(int i=0;i<fields.length; i++){
//            for(int j=0; j<fields[i].length;j++){
//                printField(fields[i][j]);
//            }
////            System.out.println("");
//        }
//
//
//
//    }

    private static void printField(Field field) {
        String cad="";
        TypeField tf= field.getTypeField();
        switch(tf){
            case TREE: cad = "T"; break;
            case ROCK: cad = "R"; break;
            case GRASS: cad= " "; break;
        }
//        System.out.print(" "+cad);
    }
}
