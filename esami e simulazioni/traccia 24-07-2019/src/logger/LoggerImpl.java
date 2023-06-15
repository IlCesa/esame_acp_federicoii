package logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Semaphore;


public class LoggerImpl extends LoggerSkeleton{
    Semaphore sem;
    public LoggerImpl(){
        sem = new Semaphore(1);
    }

    @Override
    public void registraDato(int dato) {
        try {
            sem.acquire();
            FileWriter f = new FileWriter(new File("file.txt"),true);
            
            f.append(String.valueOf(dato) + "\n");
            f.close();
            sem.release();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        
    }
    
}
