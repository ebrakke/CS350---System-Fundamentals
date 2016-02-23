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

    //Monitor variables
    public static ArrayList<Double> Tq = new ArrayList<Double>();
    public static ArrayList<Double> Tw = new ArrayList<Double>();
    public static ArrayList<Double> Ts = new ArrayList<Double>();
    public static ArrayList<Double> w = new ArrayList<Double>();
    public static ArrayList<Double> q = new ArrayList<Double>();

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
        Schedule.ScheduleNextEvent(new Event(0, "Monitor"));
    }

    public static double Average(ArrayList<Double> a){
        double sum = 0.0;
        for(Double n : a){
            sum += n;
        }
        return sum / a.size();
    }

    public void Simulate() {
        Initialize();

        while(currentTime <= simulationTime) {
            Event nextEvent = Schedule.GetNextEvent();
            currentTime = nextEvent.arrivalTime;
            nextEvent.Process();
        }
        System.out.println("Average Buffer Size: " + Average(w));
    }

    public static void main(String[] args) {
        Simulator s = new Simulator(60, .015, 100, 10);
        s.Simulate();

    }

}
