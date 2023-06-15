package server;

import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import services.IGestoreSportello;

public class GestoreSportelloServer {
    public static void main(String[] args) {

        try {
            Registry rmi = LocateRegistry.getRegistry();
            IGestoreSportello gs = new GestoreSportelloImpl();
            rmi.rebind("gestoreservice", gs);
            System.out.println("GestoreSportello avviato e registrato con successo!");

        } catch (AccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
