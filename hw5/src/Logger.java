import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Erik on 3/1/2016.
 */
public class Logger {
    private static FileWriter file;

    public static void OpenFile(String filename){
        try{
            Logger.file = new FileWriter(filename, true);
        } catch (IOException e){
            return;
        }
    }

    public static void CloseFile() {
        try {
            Logger.file.close();
        } catch (IOException e){
            return;
        }
    }

    public static void Log(String s) {
        try {
            Logger.file.write(s + '\n');
        } catch (IOException e){
            return;
        }
    }
}