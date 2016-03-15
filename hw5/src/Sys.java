import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Created by Erik on 3/1/2016.
 */
public class Sys {
    private double cpuArrivalRate;
    private double ioArrivalRate;
    private int bufferSize = -1;
    private ServiceTime cpuServiceTime;
    private ServiceTime ioServiceTime;
    private String scheduleType;
    private LinkedList<Event> buffer = new LinkedList<Event>();
    private LinkedList<Event> processer = new LinkedList<Event>();
    public State state = new State();

    public Sys(ServiceTime cpuServiceTime, ServiceTime ioServiceTime, double cpuArrivalRate, double ioArrivalRate, String scheduleType){
        this.cpuArrivalRate = cpuArrivalRate;
        this.ioArrivalRate = ioArrivalRate;
        this.cpuServiceTime = cpuServiceTime;
        this.ioServiceTime = ioServiceTime;
        this.scheduleType = scheduleType;
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

    public double generateServiceTime(boolean isCpuBound){
        if (isCpuBound){
            return cpuServiceTime.getServiceTime();
        }
        return ioServiceTime.getServiceTime();
    }

    public Event getLastEventToFinish(){
        if (processer.size() == 0 && buffer.size() == 0) {
            return null;
        }
        if (buffer.size() == 0){
            return processer.peekFirst();
        }
        return buffer.peekLast();
    }

    public int getBufferSize(){
        return bufferSize;
    }

    public boolean addEvent(Event e){
        if(buffer.size() == 0 && processer.size() == 0){
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

    public String getScheduleType(){
        return scheduleType;
    }

    public void SortBySRTN(){
        buffer.sort(new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                if(o1.serviceTime < o2.serviceTime) return -1;
                if(o2.serviceTime < o1.serviceTime) return 1;
                return 0;
            }
        });
    }
    public double getArrivalRate(boolean isCpuBound) {
        if (isCpuBound) {
            return cpuArrivalRate;
        }
        return ioArrivalRate;
    }

    public double getNextArrival(boolean isCpuBound){
        if (isCpuBound){
            return Rand.ExpGenerate(cpuArrivalRate);
        }
        return Rand.ExpGenerate(ioArrivalRate);
    }

}
