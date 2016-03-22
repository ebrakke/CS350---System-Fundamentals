import java.util.Random;

/**
 * Created by Erik on 3/21/2016.
 */
public class Rand {

    public static long UniGenerate(double[] range){
        double low = range[0];
        double hi = range[1];
        double r = new Random().nextDouble() * (hi - low) + low;
        return (long)r;
    }
}
