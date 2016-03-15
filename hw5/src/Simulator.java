/**
 * Created by Erik on 2/29/2016.
 */
import java.lang.*;

public class Simulator {
    public static double currentTime = 0.0;
    public static double monitorLambda;
    public static double simulationTime;
    public static Sys sys;

    public Simulator(double simulationTime, double monitorLambda){
        Simulator.simulationTime = simulationTime;
        Simulator.monitorLambda = monitorLambda;
        sys = new Sys(new ServiceTime("M", .3), new ServiceTime("M", .01), 3, 6, "RR");
    }

    public void Initialize(){
        //Create the first evens
        double firstCpuArrival = sys.getNextArrival(true);
        double firstIoArrival = sys.getNextArrival(false);
        Schedule.ScheduleNextEvent(new Event(firstCpuArrival, "Birth", sys, true));
        Schedule.ScheduleNextEvent(new Event(firstIoArrival, "Birth", sys, false));
        Schedule.ScheduleNextEvent(new Event(10, "Monitor", sys, false));
    }

    public void Simulate() {
        Initialize();

        //Put headers into the log file
        Logger.OpenFile("q2_rr.csv");
        Logger.Log("Tq_IO, Tq_CPU, Tw_IO, Tw_CPU, Ts_IO, Ts_CPU, q, w");

        while(Simulator.currentTime <= Simulator.simulationTime) {
            Event nextEvent = Schedule.GetNextEvent();
            Simulator.currentTime = nextEvent.arrivalTime;
            nextEvent.Process();
        }
        Logger.CloseFile();
    }

    public static void main(String[] args) {
        Simulator s = new Simulator(100, 15);
        s.Simulate();
    }

}
