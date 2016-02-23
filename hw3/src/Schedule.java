import java.util.*;

/**
 * Created by Erik on 2/15/2016.
 */
public class Schedule {
    public static ArrayList<Event> eventList = new ArrayList<Event>();

    private static Comparator<Event> arrivalComparator = new Comparator<Event>() {
        @Override
        public int compare(Event o1, Event o2) {
            if(o1.arrivalTime < o2.arrivalTime) return -1;
            if(o1.arrivalTime > o2.arrivalTime) return  1;
            return 0;
        }
    };

    public static Event GetNextEvent(){
        return eventList.remove(0);
    }

    public static void ScheduleNextEvent(Event e) {
        eventList.add(e);
        eventList.sort(arrivalComparator);
    }

    public static void main(String args[]){

    }
}
