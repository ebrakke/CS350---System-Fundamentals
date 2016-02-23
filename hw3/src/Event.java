/**
 * Created by Erik on 2/15/2016.
 */
public class Event {
    public double arrivalTime;
    public double serviceTime;
    public String type;

    public Event(double arrivalTime, String type){
        this.arrivalTime = arrivalTime;
        this.serviceTime = ExpRand.Generate(1 / Simulator.serviceTime);
        this.type = type;
    }

    public void Process() {
        if (isBirth()) {
            Simulator.buffer.add(this);
            Schedule.ScheduleNextEvent(CreateBirth(Simulator.currentTime));
            Schedule.ScheduleNextEvent(CreateDeath(Simulator.currentTime));
        }
        if (isDeath()) {
            Event e = Simulator.buffer.remove();
        }
        if (isMonitor()) {
            System.out.println("Buffer size: " + Simulator.buffer.size());
            Schedule.ScheduleNextEvent(CreateMonitor(Simulator.currentTime));
        }
    }

    public boolean isBirth(){
        return type.equals("Birth");
    }

    public boolean isDeath(){
        return  type.equals("Death");
    }

    public boolean isMonitor(){
        return type.equals("Monitor");
    }

    public Event CreateBirth(double currentTime){
        double nextBirthTimeIat = ExpRand.Generate(Simulator.lambda);
        return new Event(currentTime + nextBirthTimeIat, "Birth");
    }

    public Event CreateDeath(double currentTime) {
        double deathTime = currentTime + serviceTime;
        return new Event(deathTime, "Death");
    }

    public Event CreateMonitor(double currentTime){
        double nextTime = ExpRand.Generate(Simulator.monitorLambda);
        return new Event(currentTime + nextTime, "Monitor");
    }


}
