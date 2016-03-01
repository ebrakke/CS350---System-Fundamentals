/**
 * Created by Erik on 2/29/2016.
 */

public class Event implements IEvent{
    public double arrivalTime;
    public double serviceTime;
    public String type;
    public double deathTime;
    public Sys sys;

    public Event(double arrivalTime, String type, Sys sys){
        this.arrivalTime = arrivalTime;
        this.serviceTime = sys.generateServiceTime();
        this.type = type;
        this.sys = sys;
    }

    public void Process() {
        if (isBirth()) {
            Event last = sys.getLastEvent();
            boolean added = sys.addEventToBuffer(this);
            if(added){
                Schedule.ScheduleNextEvent(CreateDeath(Simulator.currentTime, last));
            }
            Schedule.ScheduleNextEvent(CreateBirth(Simulator.currentTime));
        }
        if (isDeath()) {
            Event e = sys.processNextEvent();
            sys.state.Tq.add(e.deathTime - e.arrivalTime);
            sys.state.Ts.add(e.serviceTime);
            sys.state.Tw.add(e.deathTime - e.arrivalTime - e.serviceTime);

            //Question 3 code
            //Schedule.ScheduleNextEvent(CreateBirth(Simulator.currentTime, true));
        }
        if (isMonitor()) {
            sys.state.w.add(sys.getRequestsInBuffer());
            sys.state.q.add(sys.getRequestsInSystem());
            sys.state.percentageDropped.add(sys.state.droppedPackets / sys.state.recievedPackets);

            String s = State.Average(sys.state.w) +
                    "," + State.Average(sys.state.q) +
                    "," + State.Average(sys.state.Tq) +
                    "," + State.Average(sys.state.Tw) +
                    "," + State.Average(sys.state.Ts) +
                    "," + State.Average(sys.state.percentageDropped);
            Logger.Log(s);
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
        Sys nextSys = Simulator.getNextSystem(sys);
        double nextBirthTimeIat = sys.getNextArrival();
        return new Event(currentTime + nextBirthTimeIat, "Birth", nextSys);
    }

    public Event CreateDeath(double currentTime, Event last) {
        if(last == null) {
            // This means others have to be processed first
            deathTime = currentTime + serviceTime;
        } else {
            deathTime = last.deathTime + serviceTime;
        }
        return new Event(deathTime, "Death", sys);
    }

    public Event CreateMonitor(double currentTime){
        double nextTime = Rand.ExpGenerate(Simulator.monitorLambda);
        return new Event(currentTime + nextTime, "Monitor", sys);
    }
}

