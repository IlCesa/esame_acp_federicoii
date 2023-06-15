package dispatcher;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import services.IDispatcher;

public class DispatcherSkeletonUdp implements IDispatcher{
    byte buf[];
    DispatcherImpl di;
    public DispatcherSkeletonUdp(){
        buf = new byte[1024];
        //di = new DispatcherImpl();
    }

    public void runSkeleton(){
        try {
            DatagramSocket ds = new DatagramSocket(8080, InetAddress.getLocalHost());
            DatagramPacket p = new DatagramPacket(buf, buf.length);
            ds.receive(p);
            String s = new String(buf);
            //avvio il thread di gestione della richiesta
            System.out.println(s);
            ds.close();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String sendDato(int year, int id) {
        return di.sendDato(year, id);
    }
    
}
