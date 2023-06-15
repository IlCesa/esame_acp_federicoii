package subscriber;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Semaphore;

import services.ISubscriber;

public class SubscriberImpl implements ISubscriber{
    private String filename;
    Semaphore s;

    public SubscriberImpl(String filename) {
        this.filename = filename;
        s = new Semaphore(1);
    }

    @Override
    public void notifyAlert(int criticality) {
        try {
            s.acquire();
            FileWriter f = new FileWriter(new File(filename), true); //true abilita l'append
            f.append(String.valueOf(criticality) + "\n");
            f.close();
            
            System.out.println("Salvo:" + criticality + "sul file: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            s.release();
        }
    }
    
}
