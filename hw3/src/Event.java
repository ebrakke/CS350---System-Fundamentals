import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by Erik on 2/15/2016.
 */
public class Event {
    public double arrivalTime;
    public double serviceTime;
    public String type;
    public double deathTime;

    public Event(double arrivalTime, String type){
        this.arrivalTime = arrivalTime;
        this.serviceTime = ExpRand.Generate(1 / Simulator.serviceTime);
        this.type = type;
    }

    public void Process() {
        if (isBirth()) {
            Event last = Simulator.buffer.peekLast();
            Simulator.buffer.add(this);
            Schedule.ScheduleNextEvent(CreateBirth(Simulator.currentTime));
            Schedule.ScheduleNextEvent(CreateDeath(Simulator.currentTime, last));
        }
        if (isDeath()) {
            Event e = Simulator.buffer.remove();
            State.Tq.add(e.deathTime - e.arrivalTime);
            State.Ts.add(e.serviceTime);
            State.Tw.add(e.deathTime - e.serviceTime - e.arrivalTime);
        }
        if (isMonitor()) {
            State.w.add((Simulator.buffer.size() - 1 < 0) ? 0.0 : (Simulator.buffer.size() - 1));
            State.q.add((double)Simulator.buffer.size());

            String s = State.Average(State.w) +
                    "," + State.Average(State.q) +
                    "," + State.Average(State.Tq) +
                    "," + State.Average(State.Tw) +
                    "," + State.Average(State.Ts);
            State.Log(s);
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

    public Event CreateDeath(double currentTime, Event last) {
        if(last != null) {
            // This means others have to be processed first
            deathTime = last.deathTime + serviceTime;
        } else {
            deathTime = currentTime + serviceTime;
        }
        return new Event(deathTime, "Death");
    }

    public Event CreateMonitor(double currentTime){
        double nextTime = ExpRand.Generate(Simulator.monitorLambda);
        return new Event(currentTime + nextTime, "Monitor");
    }


}
