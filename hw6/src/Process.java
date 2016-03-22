/**
 * Created by Erik on 3/21/2016.
 */
public class Process extends Thread {
    public int id;

    public Process(int id) {
        this.id = id;
    }

    public void run() {
        for(int i = 0; i < 5; i++){
            System.out.println("Thread " + id + " is starting iteration " + i);
            try{
                this.sleep((Rand.UniGenerate(new double[]{0, 20})));
            } catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }

            System.out.println("We hold these truths to be self-evident, that all men are created equal,");
            try{
                this.sleep((Rand.UniGenerate(new double[]{0, 20})));
            } catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }

            System.out.println("that they are endowed by their Creator with certain unalienable Rights,");
            try{
                this.sleep((Rand.UniGenerate(new double[]{0, 20})));
            } catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }

            System.out.println("that among these are Life, Liberty and the pursuit of Happiness.");
            try{
                this.sleep((Rand.UniGenerate(new double[]{0, 20})));
            } catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }

            System.out.println("Thread " + id + " is done with iteration " + i);
        }
    }

}
