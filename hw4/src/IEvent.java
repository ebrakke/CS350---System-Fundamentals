/**
 * Created by Erik on 2/29/2016.
 */
public interface IEvent {
    public void Process();
    public boolean isBirth();
    public boolean isDeath();
    public boolean isMonitor();
    public Event CreateBirth();
    public Event CreateDeath(Event last);
    public Event CreateMonitor();
}
