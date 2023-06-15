package disk;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import services.ILogger;

public class LoggerProxy implements ILogger{
    private int porto;
    private byte[] buf = new byte[256];
    public LoggerProxy(int porto){
        this.porto = porto;
    }

    @Override
    public void registraDato(int dato) {
        buf = Integer.toString(dato).getBytes();
        try {
            DatagramSocket datagramSocket = new DatagramSocket();
            DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length, InetAddress.getByName("localhost"), porto);
            datagramSocket.send(datagramPacket);
            System.out.println("Inviato in udp.");
            datagramSocket.close();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
