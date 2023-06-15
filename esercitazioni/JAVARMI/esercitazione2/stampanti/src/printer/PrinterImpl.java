package printer;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.Semaphore;


public class PrinterImpl extends PrinterSkeleton{
    private Semaphore s;
    private String filename;
    PrinterImpl(String fileName){
        s = new Semaphore(1);
        this.filename = fileName;
    }

    @Override
    public boolean print(String docName) {
        //se invece restituisce false significa che l'acquire è stato effettuato, non bisogna assolutamente chiamarlo di nuovo (deadlock).
        if(!s.tryAcquire()){
            return false;
        }

        //regione critica
        try {
            PrintWriter p = new PrintWriter(this.filename);
            p.append(docName + "\n");
            p.close();//esegue anche il flush
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        int timeout = 5+((int)Math.random()*5);
        try {
            Thread.sleep(timeout * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        s.release();





        return true;
    }
    
}
