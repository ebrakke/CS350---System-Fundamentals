import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Erik on 2/29/2016.
 */
public class Schedule {
    public static ArrayList<Event> eventList = new ArrayList<Event>();

    public static Event GetNextEvent(){
        return eventList.remove(0);
    }

    public static void ScheduleNextEvent(Event e) {
        eventList.add(e);
        eventList.sort(arrivalComparator);
    }
    private static Comparator<Event> arrivalComparator = new Comparator<Event>() {
        @Override
        public int compare(Event o1, Event o2) {
            if(o1.arrivalTime < o2.arrivalTime) return -1;
            if(o1.arrivalTime > o2.arrivalTime) return  1;
            return 0;
        }
    };
}