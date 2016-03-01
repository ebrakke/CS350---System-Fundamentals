/**
 * Created by Erik on 3/1/2016.
 */
public class ServiceTime {
    public String type = "";
    public double[] range;
    public double avgServiceRate;
    public double mean;
    public double std;

    public ServiceTime(String type, double[] range){
        this.type = type;
        this.range = range;
    }

    public ServiceTime(String type, double avg){
        this.type = type;
        avgServiceRate = avg;
    }
    public ServiceTime(String type, double mean, double std){
        this.mean = mean;
        this.std = std;
    }

    public double getServiceTime(){
        if(type.equals("M")){
            return Rand.ExpGenerate(1 / avgServiceRate);
        }
        if(type.equals("U")){
            return Rand.UniGenerate(range);
        }
        if(type.equals("N")){
            return Rand.NormGenerate(mean, std);
        }
        if(type.equals("F")){
            return avgServiceRate;
        }
        return avgServiceRate;
    }
}
