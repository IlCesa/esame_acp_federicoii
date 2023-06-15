package dispatcher;

import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class DispatcherServer {
    public static void main(String[] args) {
        
        try {
            DispatcherImpl disp = new DispatcherImpl();
            Registry rmi = LocateRegistry.getRegistry();
            rmi.rebind("service",disp);
            System.out.println("Dispatcher avviato con successo!");
        } catch (AccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
