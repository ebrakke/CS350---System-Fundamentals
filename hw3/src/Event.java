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
    public static int monitorCount = 1;

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
            Simulator.Tq.add(e.deathTime - e.arrivalTime);
            Simulator.Ts.add(e.serviceTime);
            Simulator.Tw.add(e.deathTime - e.serviceTime - e.arrivalTime);
        }
        if (isMonitor()) {
            Simulator.w.add((Simulator.buffer.size() - 1 < 0) ? 0.0 : (Simulator.buffer.size() - 1));
            Simulator.q.add((double)Simulator.buffer.size());

            String s = "Monitor " + Event.monitorCount + ": " +
                    "Buffer size: " + Simulator.Average(Simulator.w) +
                    " Queue size: " + Simulator.Average(Simulator.q) +
                    " Current Tq: " + Simulator.Average(Simulator.Tq) +
                    " Current Tw: " + Simulator.Average(Simulator.Tw) +
                    " Current Ts: " + Simulator.Average(Simulator.Ts);
            Log(s);
            Event.monitorCount++;

            Schedule.ScheduleNextEvent(CreateMonitor(Simulator.currentTime));
        }
    }

    public void Log(String s) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("log.txt", true));
            out.write(s + '\n');
            out.close();
        } catch (IOException e){
            return;
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
