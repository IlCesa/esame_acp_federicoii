package manager;

import java.io.BufferedOutputStream;

import java.io.DataOutputStream;
import java.io.IOException;

import java.net.Socket;
import java.net.UnknownHostException;

import services.ISubscriber;

public class SubscriberProxy implements ISubscriber{
    private int port;
    
    public SubscriberProxy(int port) {
        this.port = port;
    }

    @Override
    public void notifyAlert(int criticality) {
        Socket s;
        try {
            s = new Socket("localhost", this.port);
            //DataInputStream in = new DataInputStream(new BufferedInputStream(s.getInputStream()));
            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
    
    
            out.writeUTF("notify");
            out.writeInt(criticality);
    
            out.close();//il close effettua anche il flush dei dati nel buffer;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
       
    }



    
}
