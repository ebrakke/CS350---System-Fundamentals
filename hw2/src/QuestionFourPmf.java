import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by Erik on 2/8/2016.
 */
public class QuestionFourPmf {
    public static int QuestionFourRandom(){
        double rand = new Random().nextDouble();
        if (rand <= .5){
            return 1;
        }
        if (rand > .5 && rand <= .8){
            return 3;
        }
        if (rand > .8 && rand <= .94){
            return 10;
        }
        return 30;
    }

    public static void main (String[] args){

        //Testing code
        HashMap<Integer, Integer> values = new HashMap<Integer, Integer>();
        for(int i = 0; i < 100; i++){
            int value = QuestionFourRandom();
            int count = values.getOrDefault(value, 0);
            values.put(value, ++count);
        }
        System.out.print(values.toString());
    }
}
