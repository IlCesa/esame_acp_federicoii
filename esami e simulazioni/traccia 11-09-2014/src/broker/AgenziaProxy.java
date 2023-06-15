package broker;


import java.io.BufferedOutputStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import services.IAgenzia;

public class AgenziaProxy implements IAgenzia{
    private String address;
    private int port;
    public AgenziaProxy(String address, int port){
        this.address = address;
        this.port = port;
    }

    @Override
    public void vendi(int quantita) {
        try {
            Socket s = new Socket(address, port);
            //DataInputStream dis = new DataInputStream(new BufferedInputStream(s.getInputStream()));
            DataOutputStream dis2 = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
            dis2.writeUTF("vendi#" + quantita);
            dis2.close();
            s.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void acquista(int quantita) {
        try {
            Socket s = new Socket(address, port);
            //DataInputStream dis = new DataInputStream(new BufferedInputStream(s.getInputStream()));
            DataOutputStream dis2 = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
            dis2.writeUTF("acquista#" + quantita);
            dis2.close();
            s.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
