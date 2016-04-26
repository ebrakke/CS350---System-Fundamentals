/**
 * Created by Erik on 4/5/2016.
 */
public class Simulation {
    public static void main(String[] args) {
        //Question 1
        ProcessQ1[] ps = new ProcessQ1[10];
        for(int i = 0; i < 10; i++) {
            ps[i] = new ProcessQ1(i);
            ps[i].start();
        }
    }
}
