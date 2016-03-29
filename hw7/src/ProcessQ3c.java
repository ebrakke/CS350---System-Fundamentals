/**
 * Created by Erik on 3/29/2016.
 */
public class ProcessQ3c extends Thread {
    public int id;
    public int priority;
    public static volatile SemaphoreQ3c ps = new SemaphoreQ3c(5);

    public ProcessQ3c(int id) {
        this.id = id;
        this.priority = id;
    }

    public void run() {
        if(id == 0) {
            doCS();
            try{ this.sleep(10000);} catch (InterruptedException e) {}
            doCS();
        }
        else {
            for(int i = 0; i < 2*(id) + 2; i++) {
                doCS();
            }
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
