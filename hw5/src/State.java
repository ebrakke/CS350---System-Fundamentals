/**
 * Created by Erik on 2/29/2016.
 */
import java.io.*;
import java.util.ArrayList;

public class State {
    // Class will include state variable
    public ArrayList<Double> Tq_IO = new ArrayList<Double>();
    public ArrayList<Double> Tq_CPU = new ArrayList<Double>();
    public ArrayList<Double> Tw_IO = new ArrayList<Double>();
    public ArrayList<Double> Tw_CPU = new ArrayList<Double>();
    public ArrayList<Double> Ts_IO = new ArrayList<Double>();
    public ArrayList<Double> Ts_CPU = new ArrayList<Double>();
    public ArrayList<Double> w = new ArrayList<Double>();
    public ArrayList<Double> q = new ArrayList<Double>();

    // Averages at each monitor event
    public static double Average(ArrayList<Double> a){
        double sum = 0.0;
        for(Double n : a){
            sum += n;
        }
        return sum / a.size();
    }


}
