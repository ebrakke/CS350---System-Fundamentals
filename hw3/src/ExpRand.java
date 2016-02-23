/**
 * Created by Erik on 2/6/2016.
 */

import java.util.Random;

public class ExpRand {

    public static double Generate(double lambda){
        Random normalRandom = new Random();
        double normalValue = normalRandom.nextDouble();
        double expValue = -1 * (Math.log(1 - normalValue)) / lambda;
        return expValue;
    }
}

