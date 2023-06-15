package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import services.IDispatcher;

public class DispatcherProxyUdp implements IDispatcher{
    byte buf[];
    public DispatcherProxyUdp(){
        buf = new byte[1024];
    }

    @Override
    public String sendDato(int year, int id) {

        try {
            DatagramSocket ds = new DatagramSocket();
            String s = "forecast#"+year+"#"+id;
            buf = s.getBytes();
            DatagramPacket p = new DatagramPacket(buf, buf.length, InetAddress.getLocalHost(), 8080);
            ds.send(p);
            ds.close();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    
}
