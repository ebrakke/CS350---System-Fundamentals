import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Erik on 2/15/2016.
 */
public class Simulator {
    public static double currentTime = 0.0;
    public static double lambda;
    public static double serviceTime;
    public static double simulationTime;
    public static double monitorLambda;


    public static LinkedList<Event> buffer = new LinkedList<Event>();
    
    public Simulator(double lambda, double serviceTime, double simulationTime, double monitorLambda){
        Simulator.lambda = lambda;
        Simulator.serviceTime = serviceTime;
        Simulator.simulationTime = simulationTime;
        Simulator.monitorLambda = monitorLambda;
    }
    public void Initialize(){
        double firstIat = ExpRand.Generate(lambda);
        Schedule.ScheduleNextEvent(new Event(firstIat, "Birth"));
        Schedule.ScheduleNextEvent(new Event(50, "Monitor"));
    }

    public void Simulate() {
        Initialize();

        //Put headers into the log file
        State.OpenFile("q2d_log.csv");
        State.Log("w,q,Tq,Tw,Ts");

        while(currentTime <= simulationTime) {
            Event nextEvent = Schedule.GetNextEvent();
            currentTime = nextEvent.arrivalTime;
            nextEvent.Process();
        }
        State.CloseFile();
    }

    public static void main(String[] args) {
        //Simulator s = new Simulator(60, .015, 100, 120);
        //Simulator s = new Simulator(50, .015, 100, 100);
        //Simulator s = new Simulator(65, .015, 150, 130);
        Simulator s = new Simulator(65, .020, 150, 130);

        s.Simulate();

    }

}
