package logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import services.ILogger;

public abstract class LoggerSkeleton implements ILogger{
    private byte[] buf = new byte[256];
    public void runSkeleton(){
        //serverudp here
        try {
            DatagramSocket s = new DatagramSocket(3000);;
            boolean v = true;
            while(v){
                
                DatagramPacket p = new DatagramPacket(buf, buf.length);
                System.out.println("in attesa di richieste!");
                s.receive(p);
                String received = new String(p.getData(), 0, p.getLength());
                int dato = Integer.parseInt(received);
                System.out.println("Ricevuto:"+dato);
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        registraDato(dato);
                    }
                    
                }).start();
            }

            s.close();

            
            

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
