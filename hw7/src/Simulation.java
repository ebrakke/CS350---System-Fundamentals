/**
 * Created by Erik on 3/28/2016.
 */
public class Simulation {
    public static void main(String[] args) {

        // Question 1
//        Process ps[] = new Process[4];
//        for(int i = 0; i < 4; i++) {
//            ps[i] = new Process(i);
//            ps[i].start();
//        }

        // Question 3b
//        ProcessQ3b ps[] = new ProcessQ3b[5];
//        for(int i = 0; i < 5; i++) {
//            ps[i] = new ProcessQ3b(i);
//            ps[i].start();
//        }

        // Question 3c
//        ProcessQ3c ps[] = new ProcessQ3c[5];
//        for(int i = 0; i < 5; i++) {
//            ps[i] = new ProcessQ3c(i);
//            ps[i].start();
//        }

        // Question 3d
        ProcessQ3d ps[] = new ProcessQ3d[5];
        for(int i = 0; i < 5; i++) {
            ps[i] = new ProcessQ3d(i);
            ps[i].start();
        }
    }
}
