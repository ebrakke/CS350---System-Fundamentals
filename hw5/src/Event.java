/**
 * Created by Erik on 3/8/2016.
 */
public class Event {
    public double arrivalTime;
    public double serviceTime;
    public String type;
    public boolean isCpuBound;
    public double deathTime;
    public Sys sys;
    public double remainingTime;
    public double firstArrivalTime;
    public final double RR_QUANTUM = .1;

    public Event(double arrivalTime, String type, Sys sys, boolean isCpuBound){
        this.arrivalTime = arrivalTime;
        this.serviceTime = sys.generateServiceTime(isCpuBound);
        this.type = type;
        this.sys = sys;
        this.isCpuBound = isCpuBound;

        // For RR
        this.remainingTime = serviceTime;
        this.firstArrivalTime = arrivalTime;
    }

    public void Process() {
        if (isBirth()) {
            String schedType = sys.getScheduleType();
            // Get the event that will finish last in the system
            Event lastToFinish = sys.getLastEventToFinish();
            // Try to add event to the system
            boolean added = sys.addEvent(this);
            if(added){
                if(schedType == "FCFS"){
                    // Because it's been added, schedule a death which we know because it's FCFS
                    Schedule.ScheduleNextEvent(CreateDeath(lastToFinish));
                    Schedule.ScheduleNextEvent(CreateBirth());
                    return;
                }
                if(schedType == "RR") {
                    Schedule.ScheduleNextEvent(CreateDeath(lastToFinish));
                    if(arrivalTime == firstArrivalTime) {
                        Schedule.ScheduleNextEvent(CreateBirth());
                    }
                }
                if(schedType == "SRTN"){
                    // sort the requests in the buffer by srtn
                    sys.SortBySRTN();
                    if(lastToFinish == null){
                        Schedule.ScheduleNextEvent(CreateDeath(null));
                    }
                }
            }
        }
        if (isDeath()) {
            double tq;
            double ts;
            double tw;
            // Process the event and log some stats
            Event e = sys.processNextEvent();
            if(sys.getScheduleType() == "SRTN"){
                // Now that an event has finished processing, we can get the next death
                Schedule.ScheduleNextEvent(CreateDeath(null));
            }
            tq = e.deathTime - e.arrivalTime;
            ts = e.serviceTime;
            tw = e.deathTime - e.arrivalTime - e.serviceTime;
            if(sys.getScheduleType() == "RR"){
                tq = e.deathTime - e.firstArrivalTime;
                tw = e.deathTime - e.firstArrivalTime - e.serviceTime;
            }
            if(e.isCpuBound){
                sys.state.Tq_CPU.add(tq);
                sys.state.Ts_CPU.add(ts);
                sys.state.Tw_CPU.add(tw);
            }
            else{
                sys.state.Tq_IO.add(tq);
                sys.state.Ts_IO.add(ts);
                sys.state.Tw_IO.add(tw);
            }
        }
        if (isMonitor()) {
            sys.state.w.add(sys.getRequestsInBuffer());
            sys.state.q.add(sys.getRequestsInSystem());

            String s = State.Average(sys.state.Tq_IO) +
                    "," + State.Average(sys.state.Tq_CPU) +
                    "," + State.Average(sys.state.Tw_IO) +
                    "," + State.Average(sys.state.Tw_CPU) +
                    "," + State.Average(sys.state.Ts_IO) +
                    "," + State.Average(sys.state.Ts_CPU) +
                    "," + State.Average(sys.state.q) +
                    "," + State.Average(sys.state.w);
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
        double nextBirthTime;
        // Create a birth for the current system
        nextBirthTime = sys.getNextArrival(isCpuBound);
        return new Event(Simulator.currentTime + nextBirthTime, "Birth", sys, isCpuBound);
    }

    public Event CreateDeath(Event last) {
        String schedType = sys.getScheduleType();
        if(last == null) {
            // Ready to be served immediately
            if (schedType == "RR" && remainingTime > RR_QUANTUM){
                deathTime = Simulator.currentTime + RR_QUANTUM;
            } else {
                deathTime = Simulator.currentTime + serviceTime;
            }
        } else {
            if (schedType == "RR" && remainingTime > RR_QUANTUM) {
                deathTime = last.deathTime + RR_QUANTUM;
            } else {
                // Will be served after the last request is served
                deathTime = last.deathTime + serviceTime;
            }
        }
        if(sys.getScheduleType() == "RR") {
            if((remainingTime - RR_QUANTUM) > RR_QUANTUM) {
                // We can create a new birth once this event is finished
                Event nextBirth = new Event(deathTime, "Birth", sys, isCpuBound);
                nextBirth.firstArrivalTime = firstArrivalTime;
                nextBirth.remainingTime = remainingTime - RR_QUANTUM;
                Schedule.ScheduleNextEvent(nextBirth);
            }
        }
        return new Event(deathTime, "Death", sys, isCpuBound);
    }

    public Event CreateMonitor(){
        double nextTime = Rand.ExpGenerate(Simulator.monitorLambda);
        return new Event(Simulator.currentTime + nextTime, "Monitor", sys, false);
    }
}