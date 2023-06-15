package dispatcher;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import services.IPrinter;
import services.Printer;

public class DispatcherProxy implements IPrinter{
    private Printer printer;
    DispatcherProxy(final Printer printer){
        this.printer = printer;
    }

    @Override
    public boolean print(String docName) {
        Socket soc;
        boolean res = false;
        try {
            soc = new Socket (printer.getHost(), printer.getPort());
        
            DataOutputStream ostream = new DataOutputStream (new BufferedOutputStream(soc.getOutputStream()));
            DataInputStream istream = new DataInputStream(new BufferedInputStream(soc.getInputStream()));
            ostream.writeUTF("print");
            ostream.writeUTF(docName);
            ostream.flush();
            res = istream.readBoolean();
            soc.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return res;
    }

    
}
