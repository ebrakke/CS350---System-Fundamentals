/**
 * Created by Erik on 3/29/2016.
 */
public class ProcessQ3d extends Thread {
    public int id;
    public static volatile SemaphoreQ3d ps = new SemaphoreQ3d(5);

    public ProcessQ3d(int id) {
        this.id = id;
    }

    public void run() {
        for(int i = 0; i < 10; i++) {
            doCS();
        }
    }

    private void doCS() {
        ps.newWait(id);
        System.out.println("P" + id + " is in the CS");
        try{
            this.sleep(Rand.UniGenerate(new double[]{1, 1000}));
        } catch (InterruptedException e){};
        ps.newSignal(id);
    }
}
