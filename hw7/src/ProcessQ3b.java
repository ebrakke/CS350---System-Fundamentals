/**
 * Created by Erik on 3/29/2016.
 */
public class ProcessQ3b extends Thread{
    public int id;
    public int priority;
    public static volatile PrioritySemaphore ps = new PrioritySemaphore(5);

    public ProcessQ3b(int id) {
        this.id = id;
        this.priority = id;
    }

    public void run() {
        for(int i = 0; i < 10; i++) {
            ps.newWait(id, priority);
            System.out.println("P" + id + " is in the CS");
            try{
                this.sleep(Rand.UniGenerate(new double[]{1, 1000}));
            } catch (InterruptedException e){};
            ps.newSignal(id);
        }
    }


}
