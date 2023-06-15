package storage;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class StorageServer {

    public static void main(String[] args) {
        try {
            Registry rmiRegistry = LocateRegistry.getRegistry();
            StorageServerImpl ssi = new StorageServerImpl();
            rmiRegistry.rebind("service", ssi);
            System.out.println("Servizio avviato con successo.");
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
