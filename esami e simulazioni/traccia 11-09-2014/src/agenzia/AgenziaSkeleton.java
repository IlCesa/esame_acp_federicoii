package agenzia;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import services.IAgenzia;

public abstract class AgenziaSkeleton implements IAgenzia{
    
    public void runSkeleton(int port){
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(port);
            while (true) {
                Socket s = ss.accept();
                AgenziaThread t = new AgenziaThread(s, this);
                t.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
