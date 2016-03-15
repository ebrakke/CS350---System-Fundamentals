/**
 * Created by Erik on 3/1/2016.
 */
public interface ISystem {
    public double getRequestsInBuffer();
    public double getRequestsInSystem();
    public boolean isFiniteQueue();
    public double generateServiceTime();
    public Event getLastEventToFinish();
    public int getBufferSize();
    public boolean addEvent(Event e);
    public Event processNextEvent();
    public double getArrivalRate();
}
