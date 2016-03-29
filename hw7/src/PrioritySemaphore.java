/**
 * Created by Erik on 3/29/2016.
 */
import java.util.concurrent.Semaphore;

public class PrioritySemaphore {
    // The list of priorities
    public  volatile int[] R;
    // The number of processes in the system
    public volatile int N = 0;
    // A list of binary semaphores
    public volatile Semaphore[] B;


    public PrioritySemaphore(int n){
        R = new int[n];
        B = new Semaphore[n];
        // Initialize the binary semaphores
        for(int i = 0; i<n; i++) {
            B[i] = new Semaphore(0, false);
        }
    }

    public void newWait(int i, int priority) {
        System.out.println("P" + i + " is requesting the CS");
        R[i] = priority;
        N++;
        if (N > 1) {
            try {
                B[i].acquire();
            } catch (InterruptedException e) {}
        }
    }

    public void newSignal(int i) {
        R[i] = 0;
        N--;
        if(N > 0) {
            // Find next highest priority
            int maxIndex = 0;
            for (int j = 0; j < R.length; j++) {
                if (R[maxIndex] < R[j]) {
                    maxIndex = j;
                }
            }
            B[maxIndex].release();
        }
        System.out.println("P" + i + " is leaving the CS");
    }
}
