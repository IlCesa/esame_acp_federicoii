package dispatcher;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


import services.IDispatcher;

public class DispatcherSkeletonTcp implements IDispatcher{
    private DispatcherImpl di;
    public DispatcherSkeletonTcp(DispatcherImpl di){
        this.di = di;
    }
    public void runSkeleton(){
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(8080);
           
            while (true) {
                Socket s = ss.accept();
                //avvio thread
                DispatcherThread dt = new DispatcherThread(s, this);
                dt.start();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

   

    @Override
    public String sendDato(int year, int id) {
        return di.sendDato(year, id);
    }
}
