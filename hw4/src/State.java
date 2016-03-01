/**
 * Created by Erik on 2/29/2016.
 */
import java.io.*;
import java.util.ArrayList;

public class State {
    // Class will include state variable
    public ArrayList<Double> Tq = new ArrayList<Double>();
    public ArrayList<Double> Tw = new ArrayList<Double>();
    public ArrayList<Double> Ts = new ArrayList<Double>();
    public ArrayList<Double> w = new ArrayList<Double>();
    public ArrayList<Double> q = new ArrayList<Double>();
    public ArrayList<Double> percentageDropped = new ArrayList<Double>();

    public double droppedPackets = 0;
    public double recievedPackets = 0;



    // Averages at each monitor event
    public static double Average(ArrayList<Double> a){
        double sum = 0.0;
        for(Double n : a){
            sum += n;
        }
        return sum / a.size();
    }


}
