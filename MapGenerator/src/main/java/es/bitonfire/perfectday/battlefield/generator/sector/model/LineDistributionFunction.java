/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.bitonfire.perfectday.battlefield.generator.sector.model;

import java.util.Random;

/**
 *
 * Función de distribución de obstaculos de manerá lineal haciendo esquinas
 * o líneas rectas
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class LineDistributionFunction extends DistributionFunction{

    private static final int SQUARD_N_E=0;
    private static final int SQUARD_N_W=1;
    private static final int SQUARD_S_E=2;
    private static final int SQUARD_S_W=3;
    private static final int VERTICAL_LINE=4;
    private static final int HORIZONTAL_LINE=5;


    public LineDistributionFunction(int x, int y, int max) {
        super(x, y, max);
    }

    @Override
    public int[][] generateDistribution() {
        Random r = new Random(System.currentTimeMillis());
        int ocurr = 0;
        int xc = this.data.length/2;
        int yc = this.data[0].length/2;
        int direction = r.nextInt(6);
        int mx=0;
        int my=0;
        switch(direction){
            case SQUARD_N_E:
                mx=1;
                my=1;
                break;
            case SQUARD_N_W:
                mx=1;
                my=-1;
                break;
            case SQUARD_S_E:
                mx=-1;
                my=1;
                break;
            case SQUARD_S_W:
                mx=-1;
                my=-1;
                break;
            case VERTICAL_LINE:
                mx=1;
                break;
            case HORIZONTAL_LINE:
                my=1;
                break;
            default:
                 mx=0;
        }
        if (mx!=0) {
            for (int i = xc; i < data.length && i>=0; i = i + mx) {
                data[i][yc]=1;
            }
        }
        if (my!=0) {
            for (int i = yc; i < data[0].length && i>=0; i = i + my) {
                data[xc][i]=1;
            }
        }

        //Terminamos las líneas horizontales
        if (my==0) {
            for (int i = xc; i < data.length && i>=0; i = i + (mx*(-1))) {
                data[i][yc]=1;
            }
        }
        if (mx==0) {
            for (int i = yc; i < data[0].length && i>=0; i = i + (my*(-1))) {
                data[xc][i]=1;
            }
        }
        return data;
    }




}
