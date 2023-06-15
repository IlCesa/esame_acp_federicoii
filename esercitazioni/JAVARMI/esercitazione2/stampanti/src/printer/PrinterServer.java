package printer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import services.IDispatcher;
import services.Printer;

public class PrinterServer {
    public static void main(String[] args) {

        try {
            int port = Integer.parseInt(args[0]);
            String fileName = args[1];
            Printer printer = new Printer("localhost",port);
            
            Registry rmi = LocateRegistry.getRegistry();
            IDispatcher idisp = (IDispatcher)rmi.lookup("service");
            idisp.addPrinter(printer);

            PrinterImpl pr = new PrinterImpl(fileName);
            pr.runSkeleton(port);
            
            
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {

            e.printStackTrace();
        }




    }
}
