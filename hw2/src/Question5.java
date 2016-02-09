import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Erik on 2/8/2016.
 */
public class Question5 {

    public static double ZRand(){
        double sum = 0.0;
        for(int i = 0; i < 30; i++){
            sum += new Random().nextDouble();
        }
        return (sum - 15) / (Math.sqrt(30) * (1/Math.sqrt(12.0)));
    }

    public static void main(String[] args){

        //Part a
        double[] values = new double[100];
        for(int i = 0; i < 100; i++){
            values[i] = ZRand();
        }
        Arrays.sort(values);

        /*
        try {
            FileWriter writer = new FileWriter("q5a.txt");
            for (Double v : values) {
                writer.write(v.toString());
                writer.write('\n');
            }
            writer.close();
        }
        catch (IOException e){
            System.out.println("There was an error");
        }*/

        //Part b
        
    }
}
