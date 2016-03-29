import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * Created by Erik on 3/29/2016.
 */
public class SemaphoreQ3d {
    public volatile LinkedList<Integer> queue = new LinkedList<Integer>();
    public volatile int N = 0;
    // A list of binary semaphores
    public volatile Semaphore[] B;


    public SemaphoreQ3d(int n){
        B = new Semaphore[n];
        // Initialize the binary semaphores
        for(int i = 0; i<n; i++) {
            B[i] = new Semaphore(0, false);
        }
    }

    public void newWait(int i) {
        System.out.println("P" + i + " is requesting the CS");
        N++;
        if (N > 1) {
            queue.add(i);
            try {
                B[i].acquire();
            } catch (InterruptedException e) {}
        }
    }

    public void newSignal(int i) {
        N--;
        if(N > 0) {
            // Find next process to release
            int next = queue.remove();
            B[next].release();
        }
        System.out.println("P" + i + " is leaving the CS");
    }
}
