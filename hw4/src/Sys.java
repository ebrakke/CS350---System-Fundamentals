import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Erik on 3/1/2016.
 */
public class Sys implements ISystem {
    private ServiceTime serviceTime;
    private double arrivalRate;
    public boolean entrySystem;
    private int bufferSize = -1;
    public int numberOfServers = 1;
    private LinkedList<Event> buffer = new LinkedList<Event>();
    private LinkedList<Event> processer = new LinkedList<Event>();

    public State state = new State();

    public Sys(double arrivalRate, ServiceTime serviceTime, int numberOfServers, boolean entry){
        this.serviceTime = serviceTime;
        this.numberOfServers = numberOfServers;
        this.arrivalRate = arrivalRate;
        this.entrySystem = entry;
    }

    public Sys(double arrivalRate,ServiceTime serviceTime, int numberOfServers, int bufferSize, boolean entry){
        this.arrivalRate = arrivalRate;
        this.numberOfServers = numberOfServers;
        this.bufferSize = bufferSize;
        this.entrySystem = entry;
        this.serviceTime = serviceTime;
    }

    public double getRequestsInBuffer() {
        return buffer.size();
    }

    public double getRequestsInSystem(){
        return buffer.size() + processer.size();
    }

    public boolean isFiniteQueue(){
        return bufferSize != -1;
    }

    public double generateServiceTime(){
        return serviceTime.getServiceTime();
    }

    public Event getLastEventToFinish(){
        if(processer.size() <= numberOfServers && buffer.size() == 0){
            // There's an open server, so serve request now
            return null;
        }
        if(processer.size() == numberOfServers && buffer.size() <= numberOfServers){
            return (Event)processer.toArray()[buffer.size() - 1];
        }
        return (Event)buffer.toArray()[buffer.size() - processer.size() - 1];
    }

    public int getBufferSize(){
        return bufferSize;
    }

    public boolean addEvent(Event e){
        state.recievedPackets++;
        if(isFiniteQueue() && getRequestsInBuffer() == bufferSize){
            state.droppedPackets++;
            return false;
        }
        if(buffer.size() == 0 && processer.size() < numberOfServers){
            processer.add(e);
            return true;
        }
        buffer.add(e);
        return true;
    }

    public Event processNextEvent(){
        Event e = processer.remove();
        if(buffer.size() > 0){
            processer.add(buffer.remove());
        }
        return e;
    }

    public double getArrivalRate() {
        return arrivalRate;
    }

    public double getNextArrival(){
        return Rand.ExpGenerate(arrivalRate);
    }

    public static void main(String[] args){
        Sys s = new Sys(40, new ServiceTime("M", 0.15), 2, true);
        Event[] events = new Event[10];
        for(int i = 0; i < 10; i++){
            events[i] = new Event(0, "Birth", s);
        }
        s.addEvent(events[0]);
        System.out.println(s.getLastEventToFinish() == null);
        s.addEvent(events[1]);
        System.out.println(s.getLastEventToFinish() == null);

        s.addEvent(events[2]);
        System.out.println(s.getLastEventToFinish().equals(events[0]));

        s.addEvent(events[3]);
        s.addEvent(events[4]);
        s.addEvent(events[5]);
        System.out.println(s.getLastEventToFinish().equals(events[3]));

        s.addEvent(events[6]);
        System.out.println(s.getLastEventToFinish().equals(events[4]));

        Sys s1 = new Sys(40, new ServiceTime("M", 0.15), 1, true);

        s1.addEvent(events[0]);
        s1.addEvent(events[1]);
        System.out.println(s1.getLastEventToFinish().equals(events[0]));
    }

}
