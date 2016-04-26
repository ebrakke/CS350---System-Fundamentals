import java.util.Random;

/**
 * Created by Erik on 4/5/2016.
 */
public class Rand {
    public static long UniGenerate(double lo, double hi){
        double r = new Random().nextDouble() * (hi - lo) + lo;
        return (long)r;
    }

    public static int CoinFlip() {
        double r = new Random().nextDouble();
        if(r < .5) return 0;
        return 1;
    }
}
