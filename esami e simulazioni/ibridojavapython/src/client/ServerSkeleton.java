package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import services.IServer;

public abstract class ServerSkeleton implements IServer{
    byte buf[];
    public ServerSkeleton(){
        buf = new byte[1024];
    }
    public void runSkeleton(){
        DatagramSocket ds;
        try {

            ds = new DatagramSocket(8080, InetAddress.getByName("localhost"));
            while(true){
                DatagramPacket p = new DatagramPacket(buf, buf.length);
                ds.receive(p);
                String val = new String(p.getData());
                int v = Integer.valueOf(val.trim());
                this.sendResponse(v);
            }
            
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
