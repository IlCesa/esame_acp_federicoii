package client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import services.IDispatcher;

public class DispatcherProxyTcp implements IDispatcher{
    private  Socket s;
    public DispatcherProxyTcp(){
        try {
            s = new Socket("localhost", 8080);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String sendDato(int year, int id) {
        String val = "";
        try {
            DataOutputStream dataOut = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
            DataInputStream dataIn = new DataInputStream(new BufferedInputStream(s.getInputStream()));
            String s = "forecast#"+year+"#"+id;
            dataOut.writeUTF(s);
            dataOut.flush();
            val = dataIn.readUTF();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return val;
    }
    
}
