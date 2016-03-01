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
        // -1 because the first event in the buffer is actually being processed
        int size = buffer.size() - 1;
        return size < 0 ? 0 : size;
    }

    public double getRequestsInSystem(){
        return buffer.size();
    }

    public boolean isFiniteQueue(){
        return bufferSize != -1;
    }

    public double generateServiceTime(){
        return serviceTime.getServiceTime();
    }

    public Event getLastEvent(){
        if(getRequestsInSystem() < numberOfServers){
            return null;
        } else if(getRequestsInSystem() == numberOfServers){
            return buffer.peekFirst();
        }
        return buffer.peekLast();
    }

    public int getBufferSize(){
        return bufferSize;
    }

    public boolean addEventToBuffer(Event e){
        state.recievedPackets++;
        if(isFiniteQueue() && getRequestsInSystem() == bufferSize){
            state.droppedPackets++;
            return false;
        }
        buffer.add(e);
        return true;
    }

    public Event processNextEvent(){
        Event e = buffer.remove();
        return e;
    }

    public double getArrivalRate() {
        return arrivalRate;
    }

    public double getNextArrival(){
        return Rand.ExpGenerate(arrivalRate);
    }

}
