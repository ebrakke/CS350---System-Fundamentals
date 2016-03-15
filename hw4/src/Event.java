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
            // Try to add event to the system
            boolean added = sys.addEvent(this);
            Event lastToFinish = sys.getLastEventToFinish();
            if(added){
                // Because it's been added, schedule a death
                Schedule.ScheduleNextEvent(CreateDeath(lastToFinish));
            }
            // Schedule the next arrival of an event
            if (sys.entrySystem){
                Schedule.ScheduleNextEvent(CreateBirth());
            }
        }
        if (isDeath()) {
            // Process the event and log some stats
            Event e = sys.processNextEvent();
            sys.state.Tq.add(e.deathTime - e.arrivalTime);
            sys.state.Ts.add(e.serviceTime);
            sys.state.Tw.add(e.deathTime - e.arrivalTime - e.serviceTime);

            //Question 3 code
            Schedule.ScheduleNextEvent(CreateBirth());
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
            Schedule.ScheduleNextEvent(CreateMonitor());
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

    public Event CreateBirth(){
        // See which system to create a birth in
        double nextBirthTime;
        Sys nextSys = Simulator.getNextSystem(sys);
        if (nextSys == null){
            // This means that the prev request left the system
            // Create a birth for the current system
            nextBirthTime = sys.getNextArrival();
            return new Event(Simulator.currentTime + nextBirthTime, "Birth", sys);
        }
        // Otherwise, we need to pass the request onto the next system once it dies
        return new Event(Simulator.currentTime, "Birth", nextSys);
    }

    public Event CreateDeath(Event last) {
        if(last == null) {
            // Ready to be served immediately
            deathTime = Simulator.currentTime + serviceTime;
        } else {
            // Will be served after the last request is served
            deathTime = last.deathTime + serviceTime;
        }
        return new Event(deathTime, "Death", sys);
    }

    public Event CreateMonitor(){
        double nextTime = Rand.ExpGenerate(Simulator.monitorLambda);
        return new Event(Simulator.currentTime + nextTime, "Monitor", sys);
    }
}

