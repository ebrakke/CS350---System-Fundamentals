/**
 * Created by Erik on 3/28/2016.
 */
public class Process extends Thread {
    public int id;
    public static volatile int[] ticket = new int[4];
    private boolean hideOutput = false;

    public Process(int id) { this.id = id; }

    public void run() {
        ticket[id] = 1;
        for(int i : ticket) {
            if (ticket[id] < i) ticket[id] = i;
        }
        if (id == 0){
            sleep(1000);
        }
        ticket[id]++;
        for(int j = 0; j < 4; j++){
            while(ticket[j] != 0 && (ticket[j] < ticket[id] ||(ticket[j] == ticket[id] && j < id))){}
        }
        CriticalSection(1);
        ticket[id] = 0;
    }

    public void CriticalSection(int i) {
        if (!hideOutput) {
            System.out.println("Thread " + id + " is starting iteration " + i);
        }
        try {
            this.sleep((Rand.UniGenerate(new double[]{0, 20})));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if (!hideOutput) {
            System.out.println("We hold these truths to be self-evident, that all men are created equal,");
        }
        try {
            this.sleep((Rand.UniGenerate(new double[]{0, 20})));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if (!hideOutput) {
            System.out.println("that they are endowed by their Creator with certain unalienable Rights,");
        }
        try {
            this.sleep((Rand.UniGenerate(new double[]{0, 20})));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if (!hideOutput) {
            System.out.println("that among these are Life, Liberty and the pursuit of Happiness.");
        }
        try {
            this.sleep((Rand.UniGenerate(new double[]{0, 20})));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if (!hideOutput) {
            System.out.println("Thread " + id + " is done with iteration " + i);
        }
    }

    public void sleep(int time) {
        try {
            this.sleep((Rand.UniGenerate(new double[]{0, 20})));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
