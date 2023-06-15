package printer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import services.IPrinter;

public class PrinterThread extends Thread{
    private Socket s;
    private IPrinter p;
    public PrinterThread(Socket s, IPrinter p){
        this.s = s;
        this.p = p;
    }

    @Override
    public void run(){
        
        try {
            DataOutputStream ostream = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
            DataInputStream istream = new DataInputStream(new BufferedInputStream(s.getInputStream()));
            String operation = istream.readUTF();
            if(operation.equalsIgnoreCase("print")){
                String docName = istream.readUTF();
                boolean res = p.print(docName);
                ostream.writeBoolean(res);
                ostream.flush();
                System.out.println("Risposta mandata:" + res + " per " + docName);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
 
        
    }
    
}
