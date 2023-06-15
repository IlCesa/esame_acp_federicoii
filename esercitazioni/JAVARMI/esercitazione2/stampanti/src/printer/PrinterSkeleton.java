package printer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import services.IPrinter;

public abstract class PrinterSkeleton implements IPrinter{
    public void runSkeleton(int port){
        //qui avvio il server con le accept + gestione thread per servire la richiesta;
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(port);
            System.out.println("Server in ascolto sulla porta: " + port);
            while(true){
                Socket s = ss.accept();
                //credoThread
                Thread t = new PrinterThread(s, this);
                t.start();
            }
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
