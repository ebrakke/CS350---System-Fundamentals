/**
 * Created by Erik on 3/21/2016.
 */
public class ProcessEnhancement extends Thread{
    public int id;
    boolean hideOutput = false;
    public static volatile boolean[] flag = new boolean[2];
    public static volatile int turn = 1;

    public ProcessEnhancement(int id) {
        this.id = id;
    }

    public void run() {
        for(int i = 0; i < 5; i++){
            flag[id] = true;
            while(flag[(id + 1) % 2]){
                if (turn == (id + 1) % 2){
                    flag[id] = false;
                    //Part c
                    if(id == 0) {
                        try{
                            this.sleep(1000);
                        }catch(InterruptedException e){
                            Thread.currentThread().interrupt();
                        }
                    }
                    while(flag[(id + 1) % 2] == true) {};
                    flag[id] = true;
                }
            }
            CriticalSection(i);
            turn = (id + 1) % 2;
            flag[id] = false;
        }

    }

    public void CriticalSection(int i) {
        if(!hideOutput){
            System.out.println("Thread " + id + " is starting iteration " + i);
        }
        try{
            this.sleep((Rand.UniGenerate(new double[]{0, 20})));
        } catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
        if(!hideOutput) {
            System.out.println("We hold these truths to be self-evident, that all men are created equal,");
        }
        try{
            this.sleep((Rand.UniGenerate(new double[]{0, 20})));
        } catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
        if(!hideOutput){
            System.out.println("that they are endowed by their Creator with certain unalienable Rights,");
        }
        try{
            this.sleep((Rand.UniGenerate(new double[]{0, 20})));
        } catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
        if(!hideOutput){
            System.out.println("that among these are Life, Liberty and the pursuit of Happiness.");
        }
        try{
            this.sleep((Rand.UniGenerate(new double[]{0, 20})));
        } catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
        if(!hideOutput){
            System.out.println("Thread " + id + " is done with iteration " + i);
        }

    }
}
