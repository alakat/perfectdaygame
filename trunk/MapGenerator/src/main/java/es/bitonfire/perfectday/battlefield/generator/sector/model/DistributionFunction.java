/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.bitonfire.perfectday.battlefield.generator.sector.model;

/**
 *
 * Genera una distribución en una matriz de posiciones
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public abstract class DistributionFunction {

    protected  int[][] data;
    protected  int max;

    public DistributionFunction(int x,int y, int max) {
        data = new int[x][y];
        this.max = max;
    }
    
    /**
     * En este array tenemos un 1 en la celda asignada para el luegar
     * y 0 en lugares donde la ocurrencia es nula
     * @return
     */
    public abstract int[][] generateDistribution();

    public void resert(){
    	for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				data[i][j]=0;
			}
		}
    }

}
