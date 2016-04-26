import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * Created by Erik on 4/5/2016.
 */
public class SemaphoreFIFO {
    public volatile int N = 0;
    public int max;
    // A list of binary semaphores
    public volatile LinkedList<Semaphore> B = new LinkedList<Semaphore>();
    public volatile Semaphore mutex = new Semaphore(1, false);

    public SemaphoreFIFO(int n) {
        max = n;
    }

    public void wait(int i) {
        try {
            mutex.acquire();
        }catch (InterruptedException e) {};
        N++;
        if (N > max) {
            try {
                Semaphore s = new Semaphore(0, false);
                B.add(s);
                B.peekLast().acquire();
            } catch (InterruptedException e) {}
        }
        mutex.release();
    }

    public void signal(int i) {
        N--;
        if(B.size() > 0) {
            // Find next process to release
            B.remove().release();
        }
    }

    public static void main(String[] args) {

    }
}
