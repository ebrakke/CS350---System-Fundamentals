/**
 * Created by Erik on 3/21/2016.
 */
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Simulation {
    public static int busyWaitCount;
    public static void main(String[] args){
        Logger.OpenFile("q4.csv");
        // part 1
//        Process[] p = new Process[2];
//        for(int i = 0; i < 2; i++) {
//            p[i] = new Process(i);
//            p[i].start();
//        }

//        ProcessEnhancement[] p = new ProcessEnhancement[2];
//        for(int i = 0; i < 2; i++){
//            p[i] = new ProcessEnhancement(i);
//            p[i].start();
//        }
        for (int j = 0; j < 1000; j++){
            ProcessPeterson[] p = new ProcessPeterson[2];
            for(int i = 0; i < 2; i++){
                p[i] = new ProcessPeterson(i);
                //Question 4 enhancement
                p[i].hideOutput = true;
                p[i].start();
            }
            //Wait for these two threads to finish before continuing
            for(int i = 0; i< p.length; i++){
                try{
                    p[i].join();
                } catch(InterruptedException e){
                    return;
                }
            }
            Logger.Log(Integer.toString(busyWaitCount) + ",");
            busyWaitCount = 0;
        }
        Logger.CloseFile();
    }
}
