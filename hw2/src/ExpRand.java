/**
 * Created by Erik on 2/6/2016.
 */


import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class ExpRand {

    public static double Generate(double lambda){
        Random normalRandom = new Random();
        double normalValue = normalRandom.nextDouble();
        double expValue = -1 * (Math.log(1 - normalValue)) / lambda;
        return expValue;
    }

    public static HashMap<Integer, Integer> Arrivals(double[] values){
        HashMap<Integer, Integer> chunks = new HashMap<Integer, Integer>();
        double secondCounter = 0.0;
        int count = 0;
        for(Double v: values){
            if(secondCounter >= 1){
                int current;
                if(chunks.get(count) == null){
                    current = 0;
                } else {
                    current = chunks.get(count);
                }
                chunks.put(count, ++current);
                count = 0;
                secondCounter = 0.0;
            }
            secondCounter += v;
            count++;
        }
        return chunks;
    }

    public static void main(String args[]){
         double[] values = new double[100];
        for(int i = 0; i < 100; i++){
            values[i] = Generate(4);
        }
        //Used for CDF
        //Arrays.sort(values);
        System.out.println(Arrivals(values).toString());

        try {
            FileWriter writer = new FileWriter("q3a.txt");
            for (Double v : values) {
                writer.write(v.toString());
                writer.write('\n');
            }
            writer.close();
        }
        catch (IOException e){
            System.out.println("There was an error");
        }


    }
}

