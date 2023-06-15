package broker;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class BrokerServer {
    public static void main(String[] args) {
        try {
            Registry rmiRegistry = LocateRegistry.getRegistry();
            BrokerServerImpl bsi = new BrokerServerImpl();
            rmiRegistry.rebind("service", bsi);
            System.out.println("Servizio avviato con successo!");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
