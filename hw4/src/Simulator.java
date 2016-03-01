/**
 * Created by Erik on 2/29/2016.
 */
import java.lang.*;

public class Simulator {
    public static double currentTime = 0.0;
    public static double monitorLambda;
    public static double simulationTime;

    //Question 2
    //public static Sys sys = new Sys(50, new ServiceTime("M", .020), 1, 3, true); //2a
    //public static Sys sys = new Sys(50, new ServiceTime("M", .015), 1, 3, true); //2b
    //public static Sys sys = new Sys(65, new ServiceTime("M", .015), 1, 3, true); //2c
    //public static Sys sys = new Sys(50, new ServiceTime("F", .015), 1, 3, true); //2d
    public static Sys sys = new Sys(65, new ServiceTime("F", .015), 1, 3, true); //2e

    //Question 3
    //public static Sys CPU = new Sys(40, new ServiceTime("U", new double[]{.001,.039}), 2, true);
    //public static Sys Disk = new Sys(0, new ServiceTime("N", 100, .030), 1, false);
    //public static Sys Network = new Sys(0,new ServiceTime("F", .035), 1, false);

    public Simulator(double simulationTime, double monitorLambda){
        Simulator.simulationTime = simulationTime;
        Simulator.monitorLambda = monitorLambda;
    }

    public void Initialize(){
        //Question 2
        double firstIat = sys.getNextArrival();
        Schedule.ScheduleNextEvent(new Event(firstIat, "Birth", sys));
        Schedule.ScheduleNextEvent(new Event(5, "Monitor", sys));

        //Question 3
        //double firstIat = CPU.getNextArrival();
        //Schedule.ScheduleNextEvent(new Event(firstIat, "Birth", CPU));
        //Schedule.ScheduleNextEvent(new Event(5, "Monitor", CPU));
    }

    public static Sys getNextSystem(Sys prev){
        // Question 2
        return prev;

        //Question 3
//        double r = Rand.UniGenerate(new double[]{0,1});
//        if(prev.equals(CPU)){
//            if (r < 0.1){
//                return Disk;
//            } else if(r >= 0.1 && r < .5) {
//                return Network;
//            }
//        }
//
//        if(prev.equals(Disk)){
//            if (r < 0.5){
//                return Network;
//            } else {
//                return Disk;
//            }
//        }
//
//        if(prev.equals(Network)){
//            return Disk;
//        }
//        return null;
    }

    public void Simulate() {
        Initialize();

        //Put headers into the log file
        Logger.OpenFile("testing_log.csv");
        Logger.Log("w,q,Tq,Tw,Ts,Pr(Sk)");

        while(Simulator.currentTime <= Simulator.simulationTime) {
            Event nextEvent = Schedule.GetNextEvent();
            Simulator.currentTime = nextEvent.arrivalTime;
            nextEvent.Process();
        }
        Logger.CloseFile();
    }

    public static void main(String[] args) {
        Simulator s = new Simulator(100, 100);
        s.Simulate();
    }

}