/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.bitonfire.perfectday.battlefield.generator.sector.model;

import java.util.Random;

/**
 *
 * @author Miguel Angel Lopez Montellano (alakat@gmail.com)
 */
public class RandomDistributionFuction extends DistributionFunction {

    public RandomDistributionFuction(int x, int y, int max) {
        super(x, y, max);
    }

    @Override
    public int[][] generateDistribution() {
        Random r = new Random(System.currentTimeMillis());
        int ocurr = 0;
        while (ocurr < max) {
            for (int i = 0; i < data.length; i++) {
                int[] data_ = data[i];
                for (int j = 0; j < data_.length; j++) {
                    double prob = r.nextDouble();
                    if ((prob < 0.5) && (data_[j] == 0) && (ocurr<max)) {
                        data_[j] = 1;
                        ocurr++;
                    }
                }
            }
        }
        return data;
    }
}
