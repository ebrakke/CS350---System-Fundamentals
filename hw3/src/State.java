import java.io.*;
import java.util.ArrayList;

/**
 * Created by Erik on 2/23/2016.
 */
public class State {
    // Class will include state variable
    public static ArrayList<Double> Tq = new ArrayList<Double>();
    public static ArrayList<Double> Tw = new ArrayList<Double>();
    public static ArrayList<Double> Ts = new ArrayList<Double>();
    public static ArrayList<Double> w = new ArrayList<Double>();
    public static ArrayList<Double> q = new ArrayList<Double>();
    public static FileWriter file;



    // Averages at each monitor event
    public static double Average(ArrayList<Double> a){
        double sum = 0.0;
        for(Double n : a){
            sum += n;
        }
        return sum / a.size();
    }

    public static void OpenFile(String filename){
        try{
            State.file = new FileWriter(filename, true);
        } catch (IOException e){
            return;
        }
    }

    public static void CloseFile() {
        try {
            file.close();
        } catch (IOException e){
            return;
        }
    }

    public static void Log(String s) {
        try {
            file.write(s + '\n');
        } catch (IOException e){
            return;
        }
    }

}
