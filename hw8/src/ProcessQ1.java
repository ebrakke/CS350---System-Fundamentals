import java.util.Random;

/**
 * Created by Erik on 4/5/2016.
 */
public class ProcessQ1 extends Thread {
    public static volatile int nInbound = 0;
    public static volatile int nOutbound = 0;
    public static volatile SemaphoreFIFO s = new SemaphoreFIFO(4);
    public int id;
    public String direction;

    public ProcessQ1(int id) {
        this.id = id;
        if (Rand.CoinFlip() == 0) direction = "Inbound";
        else direction = "Outbound";
    }

    public void run() {
        if(direction == "Inbound") doInbound();
        else doOutBound();
    }

    private void doInbound() {
        s.wait(id);
        while(nOutbound > 0) {}
        System.out.println("Inbound " + id + " entered");
        nInbound++;
        travel();
        nInbound--;
        System.out.println("Inbound " + id + " exited");
        s.signal(id);

    }

    private void doOutBound() {
        s.wait(id);
        while(nInbound > 0) {}
        System.out.println("Outbound " + id + " entered");
        nOutbound++;
        travel();
        nOutbound--;
        System.out.println("Outbound " + id + " exited");
        s.signal(id);
    }

    private void travel() {
        try {
            this.sleep(Rand.UniGenerate(100,1000));
        } catch(InterruptedException e) {};
    }
}
