package dispatcher;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


import services.IDispatcher;

public class DispatcherThread extends Thread{
    private IDispatcher id;
    private Socket s;


    public DispatcherThread(Socket s, IDispatcher id) {
        this.id = id;
        this.s = s;

    }


    @Override
    public void run() {
        
        try {
            DataOutputStream dataOut = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
            DataInputStream dataIn = new DataInputStream(new BufferedInputStream(s.getInputStream()));

            String operation = dataIn.readUTF();
            System.out.println(operation);
            String vals[] = operation.split("#");
            int year = Integer.valueOf(vals[1]);
            int id = Integer.valueOf(vals[2]);
    
            String result = this.id.sendDato(year, id);

            dataOut.writeUTF(result);
            dataOut.flush();
    
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
