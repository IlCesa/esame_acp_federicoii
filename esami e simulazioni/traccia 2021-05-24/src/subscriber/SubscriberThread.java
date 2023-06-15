package subscriber;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import services.ISubscriber;

public class SubscriberThread extends Thread{

    private Socket s;
    private final ISubscriber si;//questa istanza è condivisa da piu' thread; non ho messo synz qui perche' uso il semaforo in subImpl
    public SubscriberThread(Socket s, final ISubscriber si){
        this.s = s;
        this.si = si;
    }

    @Override
    public void run(){
        try {
           // DataOutputStream out = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
            DataInputStream in = new DataInputStream(new BufferedInputStream(s.getInputStream()));
            String operation = in.readUTF();
            if(operation.compareTo("notify") == 0){
                
                int criticality = in.readInt();
                System.out.println("Ricevuto criticality tramite SocketTCP: " + criticality);
                si.notifyAlert(criticality);
                System.out.println("Evento gestisto con Successo!");
  
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }
}
