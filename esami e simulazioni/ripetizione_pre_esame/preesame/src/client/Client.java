package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import services.IClient;
import services.IDispatcher;

public class Client {
    public static void main(String[] args) {
        try {
            Registry rmiRegistry = LocateRegistry.getRegistry();
            
            IDispatcher disp = (IDispatcher) rmiRegistry.lookup("service");
            IClient client = new ClientImpl();
            disp.sottoscrivi(client);
            System.out.println("[CLIENT] Sottoscrizione avvenuta.");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

}
