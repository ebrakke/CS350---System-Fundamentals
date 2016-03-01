/**
 * Created by Erik on 3/1/2016.
 */
public interface ISystem {
    public double getRequestsInBuffer();
    public double getRequestsInSystem();
    public boolean isFiniteQueue();
    public double generateServiceTime();
    public Event getLastEvent();
    public int getBufferSize();
    public boolean addEventToBuffer(Event e);
    public Event processNextEvent();
    public double getArrivalRate();
}
