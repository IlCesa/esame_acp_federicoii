package subscriber;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import services.ISubscriber;

public class SubscriberSkeleton implements ISubscriber{
    private int port;
    private ServerSocket ss;
    private SubscriberImpl si;
    public SubscriberSkeleton(int port, String filename) {
        this.port = port;
        si = new SubscriberImpl(filename);
    }

    public void runSkeleton(){

        try {
            ss = new ServerSocket(this.port);
            while(true){
                Socket s = ss.accept();
                SubscriberThread st = new SubscriberThread(s, this); //gli passo il socket sul quale effettuare la comunicazione
                st.run();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void notifyAlert(int criticality) {
        si.notifyAlert(criticality);
    }
    
}
