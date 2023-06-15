package agenzia;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import services.IAgenzia;

public class AgenziaThread extends Thread{

    private Socket socket;
    private IAgenzia ia;
    public AgenziaThread(Socket socket, IAgenzia ia) {
        this.socket = socket;
        this.ia = ia;
    }

    @Override
    public void run() {
        
        DataInputStream dis;
        try {
            dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            String operations[] = dis.readUTF().split("#");
            int quantita = Integer.parseInt(operations[1]);
            //System.out.println(operations[0] + " "  + operations[1]);
            if(operations[0].compareTo("acquista") == 0){
                ia.acquista(quantita);
            }else{
                ia.vendi(quantita);
            }

            socket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

    }
    
}
