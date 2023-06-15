package manager;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Manager {
    public static void main(String[] args) {

        try {
            Registry rmiRegistry = LocateRegistry.getRegistry();
            ManagerImpl mi = new ManagerImpl();
            rmiRegistry.rebind("service", mi);
            System.out.println("Servizio avviato.");
        } catch (RemoteException e) {
            e.printStackTrace();
        } 


    }
    
}
