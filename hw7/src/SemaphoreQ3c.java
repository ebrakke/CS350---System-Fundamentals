import java.util.concurrent.Semaphore;

/**
 * Created by Erik on 3/29/2016.
 */
public class SemaphoreQ3c {
    // The list of priorities
    public  volatile int[] R;
    // array of waiting processes
    public volatile boolean[] W;
    // The number of processes in the system
    public volatile int N = 0;
    // A list of binary semaphores
    public volatile Semaphore[] B;


    public SemaphoreQ3c(int n){
        R = new int[n];
        B = new Semaphore[n];
        W = new boolean[n];
        // Initialize the binary semaphores
        for(int i = 0; i<n; i++) {
            B[i] = new Semaphore(0, false);
        }
    }

    public void newWait(int i) {
        System.out.println("P" + i + " is requesting the CS");
        W[i] = true;
        R[i]++;
        N++;
        if (N > 1) {
            try {
                B[i].acquire();
            } catch (InterruptedException e) {}
        }
    }

    public void newSignal(int i) {
        N--;
        W[i] = false;
        if(N > 0) {
            // Find next process to release
            int minIndex = -1;
            for(int j = 0; j < R.length; j++) {
                if(!W[j]) continue;
                else if(minIndex == -1) minIndex = j;
                else if(R[j] <= R[minIndex]) minIndex = j;
            }
            B[minIndex].release();
        }
        System.out.println("P" + i + " is leaving the CS");
    }
}
