package dispatcher;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import services.IDispatcher;
import services.Printer;

public class DispatcherImpl extends UnicastRemoteObject implements IDispatcher{

    Vector<Printer> printers;
    protected DispatcherImpl() throws RemoteException {
        super();
        printers = new Vector<>();
    }

    @Override
    public boolean printRequest(String docName) throws RemoteException {
        System.out.println("Ricevuta printRequest: " + docName);
        //qui chiamero lo stub/proxy di proxySkeleton;
        int i = 0;
        boolean res = false;
        while(!res && i<printers.size()){
            DispatcherProxy dp = new DispatcherProxy(printers.get(i));
            res = dp.print(docName);
            i++;
        }
        return res;
    }

    @Override
    public void addPrinter(Printer printer) throws RemoteException {
        printers.add(printer);
        System.out.println("Printer registrata con successo!" + printer.getHost() + ":"+printer.getPort());
        
    }
    
}
