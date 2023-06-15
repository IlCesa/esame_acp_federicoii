package sensor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import coda.Coda;

public class TExecutor extends Thread{

    private Coda coda;

    public TExecutor(Coda coda){
        this.coda = coda;
    }

    @Override
    public void run() {
        while(true){

            try {
                Thread.sleep(10 * 1000);
                String operations[] = coda.takeAll();
                FileWriter f = new FileWriter(new File("cmdLog.txt"), true);
                for(int i = 0; i<operations.length; i++){
                    f.append( operations[i] + "\n");
                }
                f.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

}
