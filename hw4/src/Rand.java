/**
 * Created by Erik on 2/29/2016.
 */
import java.util.Random;

public class Rand {

    public static double ExpGenerate(double lambda){
        Random normalRandom = new Random();
        double normalValue = normalRandom.nextDouble();
        double expValue = -1 * (Math.log(1 - normalValue)) / lambda;
        return expValue;
    }

    public static double UniGenerate(double[] range){
        double low = range[0];
        double hi = range[1];
        return new Random().nextDouble() * (hi - low) + low;
    }

    public static double NormGenerate(double mean, double std){
        double sum = 0.0;
        for(int i = 0; i < 30; i++){
            sum += new Random().nextDouble();
        }
        return ((sum - 15) / (Math.sqrt(30) * (1/Math.sqrt(12.0)))) * std + mean;
    }

}
