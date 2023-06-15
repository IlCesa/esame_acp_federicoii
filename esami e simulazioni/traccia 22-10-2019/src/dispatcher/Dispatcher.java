package dispatcher;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Dispatcher {
    public static void main(String[] args) {
        try {
            Registry rmi = LocateRegistry.getRegistry();
            DispatcherImpl dImpl = new DispatcherImpl();
            rmi.rebind("service", dImpl);
            System.out.println("Servizio avviato!");
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
